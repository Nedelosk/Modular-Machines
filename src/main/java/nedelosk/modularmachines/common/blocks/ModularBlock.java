package nedelosk.modularmachines.common.blocks;

import nedelosk.modularmachines.common.core.tabs.TabModularMachines;
import nedelosk.nedeloskcore.common.blocks.BlockContainerForest;
import net.minecraft.block.material.Material;

public abstract class ModularBlock extends BlockContainerForest {

	protected ModularBlock(Material mat) {
		super(mat);
		setCreativeTab(TabModularMachines.instance);
	}

}
