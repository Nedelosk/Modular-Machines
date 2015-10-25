package nedelosk.modularmachines.common.blocks;

import nedelosk.forestday.common.blocks.BlockContainerForest;
import nedelosk.modularmachines.common.core.TabModularMachines;
import net.minecraft.block.material.Material;

public abstract class ModularBlock extends BlockContainerForest {

	protected ModularBlock(Material mat) {
		super(mat);
		setCreativeTab(TabModularMachines.core);
	}

}
