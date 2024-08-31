package wolforce.hearthwell.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.MapData;
import wolforce.hearthwell.data.recipes.RecipeHandItem;
import wolforce.hearthwell.data.recipes.RecipeTransformation;
import wolforce.hearthwell.particles.ParticleEnergyData;
import wolforce.hearthwell.registries.Entities;
import wolforce.hearthwell.util.Util;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntityFlare extends Entity {

	public static final int PLAYER_DISTANCE = 5;
	public static final String REG_ID = "entity_flare";
	private static PerlinSimplexNoise perlin = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)),
			IntStream.rangeClosed(-3, 0).boxed().collect(Collectors.toList()));

	private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(EntityFlare.class, EntityDataSerializers.INT);

	private double realY;
//	private UUID hwId = null;
	private String flareType = "";
	private byte uses = 1;
	private CompoundTag unlockedNodes = new CompoundTag();

	public EntityFlare(Level world) {
		this(Entities.entity_flare.get(), world);
	}

	public EntityFlare(EntityType<EntityFlare> type, Level world) {
		super(type, world);
	}

//	private EntityHearthWell hw() {
//		if (world instanceof ServerWorld) {
//			Entity entity = ((ServerWorld) world).getEntityByUuid(hwId);
//			if (entity != null && entity instanceof EntityHearthWell && entity.isAlive() && entity.isAddedToWorld())
//				return (EntityHearthWell) entity;
//		}
//		return null;
//	}

	public void set(String flareType, int color, byte uses, CompoundTag unlockedNodes) {
		this.flareType = flareType;
		this.setColor(color);
		this.uses = uses;
		this.unlockedNodes = unlockedNodes;
	}

	//
	//
	//

	@Override
	public void tick() {
		super.tick();

//		if (ticksExisted > maxAge)
//			setDead();

		// SERVER ONLY
		if (!this.level().isClientSide)
			serverTick();
		else
			clientTick();
	}

	private void serverTick() {
		BlockPos pos = blockPosition();
		Vec3 posVec = position();
		Player nearPlayer = findNearestPlayer();

		if (nearPlayer != null)
			moveToPlayer(nearPlayer);
		else
			moveToPos(new Vec3(posVec.x, realY, posVec.z));

		if (!this.level().isEmptyBlock(pos))
			checkRecipes(pos, this.level().getBlockState(pos));
	}

	private Player findNearestPlayer() {
		List<Player> players = this.level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(PLAYER_DISTANCE));

		for (Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
			Player playerEntity = (Player) iterator.next();
			if (playerEntity.getMainHandItem().getItem() != HearthWell.flare_torch.get() && matchesAnyRecipePlayer(playerEntity.getMainHandItem()) == null)
				iterator.remove();
		}

		if (players.isEmpty())
			return null;

		Vec3 pos = position();
		players.sort((o1, o2) -> {
            double d1 = o1.position().distanceTo(pos);
            double d2 = o2.position().distanceTo(pos);
            return Double.compare(d1, d2);
        });
		return players.get(0);
	}

	private RecipeHandItem matchesAnyRecipePlayer(ItemStack stack) {
		for (RecipeHandItem recipe : MapData.DATA.recipes_handitem) {
			if (recipe.isUnlocked(unlockedNodes) && recipe.flareType.equals(flareType) && recipe.matches(stack)) {
				return recipe;
			}
		}
		return null;
	}

	private void moveToPlayer(Player player) {

		boolean torch = player.getMainHandItem().getItem() == HearthWell.flare_torch.get();
		if (torch && !player.isShiftKeyDown()) {
			List<EntityFlare> flaresNearPlayer = this.level().getEntitiesOfClass(EntityFlare.class,
					new AABB(player.getEyePosition().add(-PLAYER_DISTANCE, -PLAYER_DISTANCE, -PLAYER_DISTANCE),
							player.getEyePosition().add(PLAYER_DISTANCE, PLAYER_DISTANCE, PLAYER_DISTANCE)));
			double d = position().distanceTo(player.position());
			for (EntityFlare entityFlare : flaresNearPlayer) {
				if (entityFlare.position().distanceTo(player.position()) < d)
					return;
			}
		}

		Vec3 pos = torch//
				? player.getEyePosition().add(player.getLookAngle().normalize().scale(2)) //
				: player.getEyePosition();

		Vec3 linearPos = new Vec3(getX(), realY, getZ());
		Vec3 difference = pos.subtract(linearPos);

		if (player.getMainHandItem().getItem() != HearthWell.flare_torch.get() && difference.length() < 0.25) {
			RecipeHandItem recipe = matchesAnyRecipePlayer(player.getMainHandItem());
			if (recipe != null) {
				player.getMainHandItem().shrink(1);
				List<ItemStack> stacks = recipe.getOutputStacksFlat();
				for (ItemStack stack : stacks)
					Util.spawnItem(this.level(), position(), stack);
				uses--;
				if (uses <= 0)
					kill();
			}
		}

		Vec3 v = difference.normalize().scale(.06 /* + Math.random() * .25 */);
		double dy = Math.max(-1, Math.min(1, 0.02 * perlin.getValue(this.tickCount / 30.0, 0, false)));

		realY += v.y;
		setPos(linearPos.x + v.x, realY + dy * 100, linearPos.z + v.z);
	}

	private void moveToPos(Vec3 pos) {

		Vec3 linearPos = new Vec3(getX(), realY, getZ());
		Vec3 difference = pos.subtract(linearPos);

		Vec3 v = difference.normalize().scale(.06 /* + Math.random() * .25 */);
		double dy = Math.max(-1, Math.min(1, 0.02 * perlin.getValue(this.tickCount / 30.0, 0, false)));

		realY += v.y;
		setPos(linearPos.x + v.x, realY + dy * 100, linearPos.z + v.z);
	}

	private void clientTick() {
		Vec3 pos = position();
		int color = getColor();

		for (int i = 0; i < 1; i++) {
			double x = random.nextGaussian() * .03;
			double y = random.nextGaussian() * .03;
			double z = random.nextGaussian() * .03;

			double vx = random.nextGaussian() * .002;
			double vy = random.nextGaussian() * .002;
			double vz = random.nextGaussian() * .002;

            //				world.addParticle(new ParticleEnergyData(-color), pos.x + x, pos.y + y, pos.z + z, vx, vy, vz);
            this.level().addParticle(new ParticleEnergyData(color), pos.x + x, pos.y + y, pos.z + z, vx, vy, vz);
//				double prob = world.getDayTime() > 12000 ? 0.75 : 0.25;
        }

	}

	private void checkRecipes(BlockPos pos, BlockState state) {
		Block block = state.getBlock();

		for (RecipeTransformation recipe : MapData.DATA.recipes_transformation) {
			if (recipe.isUnlocked(unlockedNodes) && recipe.flareType.equals(this.flareType) && recipe.matches(block)) {
//				if (recipe.getOuputBlock() != null)
//					world.setBlockState(pos, recipe.getOuputBlock().getDefaultState());
//				else {

				Block newBlock = recipe.getRandomOuputBlock();
				if (newBlock != null) {
					this.level().setBlockAndUpdate(pos, newBlock.defaultBlockState());
				} else {
					this.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					List<ItemStack> stacks = recipe.getOutputStacksFlat();
					for (ItemStack stack : stacks)
						Util.spawnItem(this.level(), pos, stack);
				}
				uses--;
				if (uses <= 0)
					kill();
			}
		}
	}

	public void setRealPosition(double x, double y, double z) {
		setPos(x, y, z);
		realY = y;
	}

	private void setColor(int color) {
		entityData.set(COLOR, color);
	}

	private int getColor() {
		return entityData.get(COLOR);
	}

	@Override
	protected void defineSynchedData() {
		entityData.define(COLOR, 0);
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putString("flareType", flareType);
		compound.putInt("color", getColor());
		compound.putByte("uses", uses);

		compound.put("unlockedNodes", unlockedNodes);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		flareType = compound.getString("flareType");
		setColor(compound.getInt("color"));
		uses = compound.getByte("uses");

		unlockedNodes = compound.getCompound("unlockedNodes");
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
