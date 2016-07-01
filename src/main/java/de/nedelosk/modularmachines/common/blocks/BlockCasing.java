package de.nedelosk.modularmachines.common.blocks;

import java.util.List;
import java.util.Locale;

import de.nedelosk.modularmachines.common.core.TabModularMachines;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCasing extends BlockForest implements IItemModelRegister, IBlockWithMeta{

	public static final PropertyEnum<CasingType> TYPE = PropertyEnum.create("type", CasingType.class);

	public BlockCasing() {
		super(Material.GROUND, TabModularMachines.tabModules);
		setUnlocalizedName("casing");
		setHarvestLevel("pickaxe", 1);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, CasingType.STONE));
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for(int i = 0; i < CasingType.values().length; i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "casing/stone");
		manager.registerItemModel(item, 1, "casing/iron");
		manager.registerItemModel(item, 2, "casing/bronze");
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, CasingType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	public String getNameFromMeta(int meta) {
		return CasingType.values()[meta].getName();
	}

	public static enum CasingType implements IStringSerializable{
		STONE,
		IRON,
		BRONZE;

		@Override
		public String getName() {
			return name().toLowerCase(Locale.ENGLISH);
		}
	}
}
