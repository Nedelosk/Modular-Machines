package de.nedelosk.modularmachines.common.blocks;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.common.core.TabModularMachines;
import de.nedelosk.modularmachines.common.utils.IColoredBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockComponent extends BlockForest implements IColoredBlock {

	public ArrayList<List> metas = Lists.newArrayList();
	public String name;

	public BlockComponent(Material material, String name) {
		super(material, TabModularMachines.tabComponents);
		setUnlocalizedName("component." + name);
		this.name = name;
	}

	public BlockComponent addMetaData(int color, String name, String... oreDict) {
		metas.add(Lists.newArrayList(color, name, oreDict));
		return this;
	}
	
	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return (int) metas.get(getMetaFromState(state)).get(0);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
		for(int i = 0; i < metas.size(); i++) {
			subItems.add(new ItemStack(item, 1, i));
		}
	}
}
