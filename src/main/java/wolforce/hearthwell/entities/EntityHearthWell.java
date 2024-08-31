package wolforce.hearthwell.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.MapNode;
import wolforce.hearthwell.data.RecipePart;
import wolforce.hearthwell.data.recipes.RecipeFlare;
import wolforce.hearthwell.data.recipes.RecipeInfluence;
import wolforce.hearthwell.net.ClientProxy;
import wolforce.hearthwell.particles.ParticleEnergyData;
import wolforce.hearthwell.registries.Entities;
import wolforce.hearthwell.util.Util;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;
import static java.util.stream.Collectors.toList;
import static wolforce.hearthwell.data.MapData.DATA;

public class EntityHearthWell extends Entity {

	public static final String REG_ID = "entity_hearth_well";

	private static final EntityDataAccessor<Integer> RESEARCH = SynchedEntityData.defineId(EntityHearthWell.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> RESEARCH_NODE_X = SynchedEntityData.defineId(EntityHearthWell.class,
			EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> RESEARCH_NODE_Y = SynchedEntityData.defineId(EntityHearthWell.class,
			EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> HAS_ITEMS = SynchedEntityData.defineId(EntityHearthWell.class,
			EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<CompoundTag> UNLOCKED_NODES = SynchedEntityData
			.defineId(EntityHearthWell.class, EntityDataSerializers.COMPOUND_TAG);

	private static final int research_max_cooldown = 30;
	private int research_cooldown = research_max_cooldown;

	public EntityHearthWell(Level world) {
		this(Entities.entity_hearthwell.get(), world);
	}

	public EntityHearthWell(EntityType<EntityHearthWell> type, Level world) {
		super(type, world);
		init();
	}

	//
	//

	private void init() {

		unlockMapNode(DATA.getNode((byte) 0, (byte) 0));
	}

	@Override
	public void tick() {
		super.tick();

		Vec3 realPos = position();
		Vec3 centerPos = position().add(0, 0.65f, 0);
		double x = realPos.x;
		double y = realPos.y;
		double z = realPos.z;

//		{
//			AxisAlignedBB nearaabb = new AxisAlignedBB(x - 5, y - 5, z - 5, x + 5, y + 5, z + 5);
//			List<ItemEntity> nearItems = world.getEntitiesWithinAABB(ItemEntity.class, nearaabb);
//			for (ItemEntity ent : nearItems)
//				ent.setNoGravity(false);
//		}

		AABB faraabb = new AABB(x - 4, y - 4, z - 4, x + 4, y + 4, z + 4);
		List<ItemEntity> farItems = this.level().getEntitiesOfClass(ItemEntity.class, faraabb);
		for (ItemEntity itemEntity : farItems) {
			itemEntity.setNoGravity(false);
		}

		AABB nearaabb = new AABB(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2);
		List<ItemEntity> nearItems = this.level().getEntitiesOfClass(ItemEntity.class, nearaabb);
		List<ItemEntity> nearItemsGood = nearItems.stream().filter(e -> e.tickCount > 100).collect(toList());

		double f = sin((tickCount % 10000) / 10000.0 * 2 * PI);
		for (ItemEntity ent : nearItems) {
			Vec3 entPos = ent.position();
			if (entPos.add(ent.getDeltaMovement()).distanceTo(centerPos) > 2) {
				ent.setDeltaMovement(ent.getDeltaMovement().scale(0.5));
			}
			ent.setDeltaMovement(ent.getDeltaMovement().scale(0.9));
			ent.setNoGravity(true);
			Random random = new Random(ent.hashCode());
			double angle = PI * 2 * (random.nextFloat() + (ent.tickCount % 80) / 80.0);
			double addX = cos(angle) * f + sin(angle) * (1 - f);
			double addY = cos(angle) * f;
			double addZ = sin(angle) * f + cos(angle) * (1 - f);
			Vec3 orbitalPos = centerPos.add(addX, addY, addZ);

			Vec3 diff = orbitalPos.subtract(entPos);
			double dist = diff.length();
			if (dist > 0.1 || ent.getDeltaMovement().length() > 0.1) {
				Vec3 v = diff.normalize().scale(0.015);
				ent.push(v.x, v.y, v.z);
			}

			if (ent.tickCount > 100) {
				ent.setExtendedLifetime();
//				ent.setDefaultPickupDelay();
				this.level().addParticle(new ParticleEnergyData(0), entPos.x, entPos.y + 0.5, entPos.z, 0, 0, 0);
			}

		}

		if (!this.level().isClientSide) {

			CompoundTag unlockedNodes = getUnlockedNodes();

			if (!nearItemsGood.isEmpty() && this.level() instanceof ServerLevel && random.nextFloat() < 0.025) {

				for (RecipeFlare recipe : DATA.recipes_flare) {
					if (!recipe.isUnlocked(this))
						continue;

//					int[] matchesResult = recipe.matches(nearItemsGood.stream().map(e -> e.getItem()).collect(toList()));
//					int matches = matchesResult[0];
//					int nrOfMystDust = matchesResult[1];
					boolean matches = recipe.matchesAllInputs(nearItemsGood.stream().map(ItemEntity::getItem).toList());
					if (matches) {
						Vec3 flarePos = null;
						for (List<ItemStack> itemString : recipe.getInputStacks())
							flarePos = deleteItemOnceFrom(itemString, nearItemsGood);
						int extraDust = 0;
						for (ItemEntity itemEntity : nearItemsGood) {
							ItemStack nearStack = itemEntity.getItem();
							if (nearStack.getItem() == HearthWell.myst_dust.get()) {
								while (nearStack.getCount() > 0 && extraDust < 64) {
									nearStack.shrink(1);
									extraDust++;
								}
							}
						}
						if (flarePos == null)
							flarePos = new Vec3(x, y + 1, z);
						EntityFlare flareEntity = new EntityFlare(this.level());
						flareEntity.set(recipe.recipeId, recipe.color,
								/* uses */ (byte) Math.min(64, recipe.uses + extraDust), unlockedNodes);
						flareEntity.setRealPosition(flarePos.x, flarePos.y, flarePos.z);
						this.level().addFreshEntity(flareEntity);
					}
				}
			}

			MapNode researchNode = DATA.getNode(getResearchNode());
			if (researchNode != null && researchNode != DATA.getHwNode())
				tickResearch(researchNode, nearItemsGood);
			else
				setHasItems(true);
			if (Math.random() < 0.1)
				tickTransformationRecipes();

		} else { // CLIENT SIDE
			this.level().addParticle(new ParticleEnergyData(0), x, y + .75 + random.nextGaussian() * .1, z,
//			this.level().addParticle(new ParticleEnergyData(0), x, y + .5 + random.nextFloat() * .5, z, //
					random.nextGaussian() * .005, random.nextGaussian() * .005, random.nextGaussian() * .005);
		}

	}

	private Vec3 deleteItemOnceFrom(List<ItemStack> possibleInputs, List<ItemEntity> nearItemsGood) {
		for (ItemEntity itemEntity : nearItemsGood) {

			ItemStack entityStack = itemEntity.getItem();
			ItemStack inputStack = Util.stackListFind_ignoreNr(possibleInputs, entityStack);
			if (inputStack != null && entityStack.getCount() >= inputStack.getCount()) {
				entityStack.shrink(inputStack.getCount());
				itemEntity.tickCount = 0;
				Vec3 itemPos = itemEntity.position();
				((ServerLevel) this.level()).sendParticles(new ParticleEnergyData(0), itemPos.x, itemPos.y, itemPos.z, 20, 0,
						0, 0, 0.01);
				return itemPos;
			}
		}
		return null;
	}

	private void tickTransformationRecipes() {
		BlockPos pos = blockPosition().offset(//
                (int) max(-2, min(2, random.nextGaussian())), //
                (int) max(-3, min(3, random.nextGaussian())), //
                (int) max(-2, min(2, random.nextGaussian())) //
		);
		if (this.level().isEmptyBlock(pos))
			return;
		Block block = this.level().getBlockState(pos).getBlock();
		for (RecipeInfluence recipe : DATA.recipes_influence) {
			if (recipe.isUnlocked(this) && recipe.matches(block)) {
				Block outputBlock = recipe.getRandomOuputBlock();
				if (outputBlock != null)
					this.level().setBlockAndUpdate(pos, outputBlock.defaultBlockState());
				return;
			}
		}
	}

	private void tickResearch(MapNode node, List<ItemEntity> nearItems) {
		if (research_cooldown > 0)
			research_cooldown--;
		else {
			if (isUnlocked(node))
				return;
			if (hasItems(nearItems, node.requiredItems)) {
				research_cooldown = research_max_cooldown;
				addResearch(1);
				setHasItems(true);
				// make research consume random items
//				if (random.nextDouble() < 0.02) {
//					List<ItemEntity> listClone = nearItems.stream().collect(toList());
//					Collections.shuffle(listClone);
//					ITEMSEARCH: for (ItemEntity item : listClone) {
//						for (RecipePart part : node.requiredItems) {
//							List<ItemStack> stacks = part.stacks();
//							if (Util.stackListFind_moreOrEqualNr(getPickResult(), stacks)) {
//								item.getItem().shrink(1);
//								Vec3 pos = item.position();
//								item.teleportTo(pos.x, pos.y, pos.z);
//								((ServerLevel) this.level()).sendParticles(new ParticleEnergyData(0xFFFFFF), pos.x, pos.y + 0.66, pos.z, 40, 0,
//										random.nextGaussian() * 0.5, 0, 0.01);
//								break ITEMSEARCH;
//							}
//						}
//					}
//				}
			} else {
				setHasItems(false);
				((ServerLevel) this.level()).sendParticles(new ParticleEnergyData(0xFF0000), xOld, yOld + 0.66, zOld, 4, //
						0, random.nextGaussian(), 0, 0.01);
			}
		}
	}

	private boolean hasItems(List<ItemEntity> nearItems, LinkedList<RecipePart> requiredItems) {
		for (RecipePart recipePart : requiredItems)
			if (!hasItem(nearItems, recipePart.stacks()))
				return false;
		return true;
	}

	private boolean hasItem(List<ItemEntity> nearItems, List<ItemStack> tagItems) {
		for (ItemEntity itemEntity : nearItems) {
			ItemStack item = Util.stackListFind_moreOrEqualNr(itemEntity.getItem(), tagItems);
			if (Util.isValid(item))
				return true;
//			List<Item> tagItems = UtilTags.getTagItems(reqItem);
//			if ((tagItems != null && tagItems.contains(itemEntity.getItem().getItem())) //
//					|| itemEntity.getItem().getItem() == Util.tryGetItem(reqItem))
//				return true;
		}
		return false;
	}

	private CompoundTag getUnlockedNodes() {
		return entityData.get(UNLOCKED_NODES);
	}

	private void setUnlockedNodes(CompoundTag unlockedNodes) {
		entityData.set(UNLOCKED_NODES, null);
		entityData.set(UNLOCKED_NODES, unlockedNodes);
	}

	public void unlockMapNode(MapNode node) {
		CompoundTag unlockedNodes = getUnlockedNodes();
		unlockedNodes.putBoolean(node.hash() + "", true);
		setUnlockedNodes(unlockedNodes);
	}

	public boolean isUnlocked(MapNode node) {
		if (node == null)
			return false;
		if (node.x == 0 && node.y == 0)
			return true;
		return getUnlockedNodes().getBoolean(node.hash() + "");
	}

	public boolean isUnlockable(MapNode node) {
		for (String parent : node.parent_ids) {
			if (!isUnlocked(DATA.getNode(parent)))
				return false;
		}
		return true;
	}

	@Override
	public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
		if (player.level().isClientSide) {
			ClientProxy.openHearthWellScreen(this);
		}
		return InteractionResult.CONSUME;
	}

	//
	//
	// GETTERS AND SETTERS

	@Override
	protected void defineSynchedData() {
		entityData.define(RESEARCH, 0);
		entityData.define(RESEARCH_NODE_X, (byte) 0);
		entityData.define(RESEARCH_NODE_Y, (byte) 0);
		entityData.define(HAS_ITEMS, true);
		entityData.define(UNLOCKED_NODES, new CompoundTag());
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean isAttackable() {
		return false;
	}

	//
	//

	public int getResearch() {
		return entityData.get(RESEARCH);
	}

	public double getResearchPercent() {
		return getResearch() / (double) DATA.getNode(getResearchNode()).time;
	}

	public byte[] getResearchNode() {
		return new byte[] { entityData.get(RESEARCH_NODE_X), entityData.get(RESEARCH_NODE_Y) };
	}

	private boolean addResearch(int value) {
		MapNode node = DATA.getNode(getResearchNode());
		if (node == null)
			return false;
		int newValue = getResearch() + value;
		entityData.set(RESEARCH, newValue);
		if (newValue >= node.time) {
			unlockMapNode(node);
			if (!this.level().isClientSide && this.level() instanceof ServerLevel) {
				Vec3 pos = position();
				((ServerLevel) this.level()).sendParticles(new ParticleEnergyData(0), pos.x, pos.y + 0.66, pos.z, 240, 0, 0, 0,
						0.1);
			}
			return true;
		}
		return false;
	}

	public void setResearchPoint(byte x, byte y) {
		byte[] currNode = getResearchNode();
		if (x == currNode[0] && y == currNode[1])
			return;
		MapNode node = DATA.getNode(x, y);
		if (x == 0 && y == 0) {
			entityData.set(RESEARCH, 0);
			research_cooldown = research_max_cooldown;
			entityData.set(RESEARCH_NODE_X, (byte) 0);
			entityData.set(RESEARCH_NODE_Y, (byte) 0);
		}
		if (node == null || !isUnlockable(node))
			return;
		entityData.set(RESEARCH, 0);
		entityData.set(RESEARCH_NODE_X, x);
		entityData.set(RESEARCH_NODE_Y, y);
	}

	private void setHasItems(boolean b) {
		entityData.set(HAS_ITEMS, b);
	}

	public boolean hasItems() {
		return entityData.get(HAS_ITEMS);
	}

	//
	//

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putInt("research", getResearch());

		byte[] researchNode = getResearchNode();
		compound.putInt("research_node_x", researchNode[0]);
		compound.putInt("research_node_y", researchNode[1]);

		compound.put("unlockedNodes", getUnlockedNodes());
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		entityData.set(RESEARCH, compound.getInt("research"));
		byte research_node_x = compound.getByte("research_node_x");
		byte research_node_y = compound.getByte("research_node_y");
		if (DATA.getNode(research_node_x, research_node_y) != null) {
			entityData.set(RESEARCH_NODE_X, research_node_x);
			entityData.set(RESEARCH_NODE_Y, research_node_y);
		} else {
			entityData.set(RESEARCH_NODE_X, (byte) 0);
			entityData.set(RESEARCH_NODE_Y, (byte) 0);
		}

		setUnlockedNodes(compound.getCompound("unlockedNodes"));
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
