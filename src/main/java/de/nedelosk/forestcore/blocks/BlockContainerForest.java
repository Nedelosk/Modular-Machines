package de.nedelosk.forestcore.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
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
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
}
