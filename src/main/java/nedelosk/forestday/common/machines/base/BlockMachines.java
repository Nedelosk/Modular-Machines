package nedelosk.forestday.common.machines.base;

import nedelosk.forestday.api.Tabs;
import nedelosk.nedeloskcore.api.machines.INedeloskMachine;
import nedelosk.nedeloskcore.common.blocks.BlockContainerForest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockMachines extends BlockContainerForest implements INedeloskMachine {

	protected BlockMachines(Material mat) {
		super(mat, Tabs.tabForestdayBlocks);
	}

}
