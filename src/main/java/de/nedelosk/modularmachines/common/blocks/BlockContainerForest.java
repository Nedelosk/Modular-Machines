package de.nedelosk.modularmachines.common.blocks;

import de.nedelosk.modularmachines.common.core.TabModularMachines;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public abstract class BlockContainerForest extends BlockContainer {

	protected BlockContainerForest(Material mat, CreativeTabs tabForest) {
		super(mat);
		setCreativeTab(tabForest);
		setHardness(2.0F);
	}

	protected BlockContainerForest(Material mat) {
		super(mat);
		setHardness(2.0F);
		setCreativeTab(TabModularMachines.tabModularMachines);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
}
