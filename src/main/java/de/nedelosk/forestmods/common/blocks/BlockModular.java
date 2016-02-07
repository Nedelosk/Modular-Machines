package de.nedelosk.forestmods.common.blocks;

import de.nedelosk.forestcore.blocks.BlockContainerForest;
import de.nedelosk.forestmods.common.core.TabModularMachines;
import net.minecraft.block.material.Material;

public abstract class BlockModular extends BlockContainerForest {

	protected BlockModular(Material mat) {
		super(mat);
		setCreativeTab(TabModularMachines.tabForestMods);
	}
}
