package wolforce.hwell.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.hwell.Main;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileCore;
import wolforce.hwell.items.ItemShard;
import wolforce.hwell.recipes.RecipeCoring;
import wolforce.mechanics.Util;

public class BlockCore extends Block implements HasTE {

	public static boolean isCore(ItemStack stack) {

		// ItemStack stack2 = new ItemStack(Main.core_stone);
		// if (stack.getItem() == stack2.getItem())
		// return true;
		//
		// stack2 = new ItemStack(Main.core_anima);
		// if (stack.getItem() == stack2.getItem())
		// return true;
		//
		// stack2 = new ItemStack(Main.core_heat);
		// if (stack.getItem() == stack2.getItem())
		// return true;
		//
		// stack2 = new ItemStack(Main.core_green);
		// if (stack.getItem() == stack2.getItem())
		// return true;
		//
		// stack2 = new ItemStack(Main.core_sentient);
		// if (stack.getItem() == stack2.getItem())
		// return true;

		for (BlockCore customcore : Main.cores.values()) {
			if (stack.getItem() == new ItemStack(customcore).getItem())
				return true;
		}

		return false;
	}

	public static BlockCore getCore(ItemStack stack) {
		if (!Util.isValid(stack))
			return null;

		// = new ItemStack(Main.core_stone);
		// if (stack.getItem() == stack2.getItem())
		// return Main.core_stone;
		//
		// stack2 = new ItemStack(Main.core_anima);
		// if (stack.getItem() == stack2.getItem())
		// return Main.core_anima;
		//
		// stack2 = new ItemStack(Main.core_heat);
		// if (stack.getItem() == stack2.getItem())
		// return Main.core_heat;
		//
		// stack2 = new ItemStack(Main.core_green);
		// if (stack.getItem() == stack2.getItem())
		// return Main.core_green;
		//
		// stack2 = new ItemStack(Main.core_sentient);
		// if (stack.getItem() == stack2.getItem())
		// return Main.core_sentient;

		for (BlockCore core : Main.cores.values()) {
			if (stack.getItem() == new ItemStack(core).getItem())
				return core;
		}

		return null;
	}

	public static Block getGraft(Block coreBlock) {
		// if (coreBlock == Main.core_stone)
		// return Main.graft_stone;
		// if (coreBlock == Main.core_anima)
		// return Main.graft_anima;
		// if (coreBlock == Main.core_heat)
		// return Main.graft_heat;
		// if (coreBlock == Main.core_green)
		// return Main.graft_green;
		// if (coreBlock == Main.core_sentient)
		// return Main.graft_sentient;

		if (Main.custom_grafts.containsKey(coreBlock))
			return Main.custom_grafts.get(coreBlock);

		return null;
	}

	public static PropertyEnum<CoreType> SHARD = PropertyEnum.create("shard", CoreType.class);
	private boolean isToRegisterTileEntity;
	public int color1, color2;
	private boolean isCustom;

	// public BlockCore(String name, boolean isToRegister) {
	// this(name, isToRegister, 0, 0);
	// }

	public BlockCore(boolean isToRegisterTileEntity, int color1, int color2, boolean isCustom) {
		super(Material.CLAY);
		this.isToRegisterTileEntity = isToRegisterTileEntity;
		this.isCustom = isCustom;
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(blockState.getBaseState().withProperty(SHARD, CoreType.core_base));

		this.color1 = color1;
		this.color2 = color2;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand enumhand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (state.getProperties().get(SHARD) != CoreType.core_base)
			return false;

		Item hand = playerIn.getHeldItem(enumhand).getItem();

		if (!(hand instanceof ItemShard))
			return false;

		if (RecipeCoring.getResult(this, (ItemShard) hand) == null)
			return false;

		for (CoreType ctype : CoreType.values()) {
			if (hand.equals(ctype.getShard()))
				set(world, pos, ctype);
		}

		if (!playerIn.isCreative())
			playerIn.getHeldItem(enumhand).shrink(1);
		return true;
	}

	private void set(World world, BlockPos pos, CoreType coretype) {
		world.setBlockState(pos, getDefaultState().withProperty(SHARD, coretype));
	}

	public static enum CoreType implements IStringSerializable {
		core_base, core_c, core_ca, core_fe, core_o, core_au, core_h, core_p, core_n;

		@Override
		public String getName() {
			return name();
		}

		public ItemShard getShard() {
			switch (this) {
			case core_au:
				return Main.shard_au;
			case core_c:
				return Main.shard_c;
			case core_ca:
				return Main.shard_ca;
			case core_fe:
				return Main.shard_fe;
			case core_h:
				return Main.shard_h;
			case core_n:
				return Main.shard_n;
			case core_o:
				return Main.shard_o;
			case core_p:
				return Main.shard_p;
			default:
			}
			return null;
		}

	}

	//

	//

	// VISUALS

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT;
	}

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCore();
	}

	@Override
	public boolean isToRegisterTileEntity() {
		return isToRegisterTileEntity;
	}

	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHARD);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHARD, CoreType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Enum<CoreType>) state.getValue(SHARD)).ordinal();
	}

	public boolean isCustom() {
		return isCustom;
	}

}
