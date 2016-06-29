package de.nedelosk.modularmachines.common.blocks;

import java.util.List;

import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.IColoredBlock;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMetalBlock extends BlockForest implements IColoredBlock, IItemModelRegister, IBlockWithMeta {

	public static final PropertyEnum<ComponentTypes> TYPE = PropertyEnum.create("type", ComponentTypes.class);

	public BlockMetalBlock() {
		super(Material.IRON, TabModularMachines.tabComponents);
		setUnlocalizedName("metal_blocks");
		setDefaultState(blockState.getBaseState().withProperty(TYPE, ComponentTypes.TIN));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return state.getValue(TYPE).color;
	}
	
	@Override
	public String getNameFromMeta(int meta) {
		return ComponentTypes.values()[meta].getName();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, ComponentTypes.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for(ComponentTypes type : ComponentTypes.values()){
			manager.registerItemModel(item, type.ordinal());
		}
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for(int i = 0; i < ComponentTypes.values().length; i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}

	public static enum ComponentTypes implements IStringSerializable{
		TIN(0xCACECF, "tin", "Tin"),
		COPPER(0xCC6410, "copper", "Copper"),
		BRONZE(0xCA9956, "bronze", "Bronze"),
		STEEL(0xA0A0A0, "steel", "Steel"),
		INVAR(0xA1A48C, "invar", "Invar");

		public int color;
		public String name;
		public String[] oreDict;
		private ComponentTypes(int color, String name, String... oreDict) {
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
