package de.nedelosk.modularmachines.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockForest extends Block {

	protected BlockForest(Material mat, CreativeTabs tabForest) {
		super(mat);
		setCreativeTab(tabForest);
		setHardness(2.0F);
	}

	protected BlockForest(Material mat) {
		super(mat);
		setHardness(2.0F);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
}
