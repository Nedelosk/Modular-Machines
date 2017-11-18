package modularmachines.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.model.ModelManager;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.utils.content.IColoredBlock;
import modularmachines.common.utils.content.IItemModelRegister;

public class BlockMetalBlock extends BlockForest implements IColoredBlock, IItemModelRegister, IBlockWithMeta {
	
	public static final PropertyEnum<Metals> TYPE = PropertyEnum.create("type", Metals.class);
	
	public BlockMetalBlock() {
		super(Material.IRON, TabModularMachines.tabModularMachines);
		setUnlocalizedName("metal_blocks");
		setDefaultState(blockState.getBaseState().withProperty(TYPE, Metals.TIN));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return state.getValue(TYPE).color;
	}
	
	@Override
	public String getNameFromMeta(int meta) {
		return Metals.values()[meta].getName();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, Metals.values()[meta]);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(Item item, ModelManager manager) {
		for (Metals type : Metals.values()) {
			manager.registerItemModel(item, type.ordinal());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (int i = 0; i < Metals.values().length; i++) {
			subItems.add(new ItemStack(this, 1, i));
		}
	}
	
	public enum Metals implements IStringSerializable {
		TIN(0xCACECF, "tin", "Tin"), COPPER(0xCC6410, "copper", "Copper"), BRONZE(0xCA9956, "bronze", "Bronze"), STEEL(0xA0A0A0, "steel", "Steel"), INVAR(0xA1A48C, "invar", "Invar");
		
		public int color;
		public String name;
		public String[] oreDict;
		
		Metals(int color, String name, String... oreDict) {
			this.color = color;
			this.name = name;
			this.oreDict = oreDict;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}
}
