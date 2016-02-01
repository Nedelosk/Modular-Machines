package nedelosk.modularmachines.common.blocks;

import nedelosk.forestcore.library.blocks.BlockContainerForest;
import nedelosk.modularmachines.common.core.TabModularMachines;
import net.minecraft.block.material.Material;

public abstract class BlockModular extends BlockContainerForest {

	protected BlockModular(Material mat) {
		super(mat);
		setCreativeTab(TabModularMachines.core);
	}
}
