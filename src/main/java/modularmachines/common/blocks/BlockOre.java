package modularmachines.common.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import net.minecraft.block.SoundType;
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

import modularmachines.client.model.ModelManager;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.utils.content.IItemModelRegister;

public class BlockOre extends BlockForest implements IItemModelRegister, IBlockWithMeta {

	public static final PropertyEnum<OreType> TYPE = PropertyEnum.create("type", OreType.class);

	public BlockOre() {
		super(Material.GROUND, TabModularMachines.tabModularMachines);
		setHardness(2.0f);
		setResistance(3.0F);
		setUnlocalizedName("ores");
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 1);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, OreType.COPPER));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, OreType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return Collections.singletonList(new ItemStack(this, 1, getMetaFromState(state)));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(Item item, ModelManager manager) {
		for (OreType type : OreType.values()) {
			manager.registerItemModel(item, type.ordinal(), "ores/" + type.getName());
		}
	}

	@Override
	public String getNameFromMeta(int meta) {
		return OreType.values()[meta].getName();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < OreType.values().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	public static enum OreType implements IStringSerializable {
		COPPER, TIN, SILVER, LEAD, NICKEL, ALUMINIUM;

		@Override
		public String getName() {
			return name().toLowerCase(Locale.ENGLISH);
		}
	}
}
