package nedelosk.forestday.common.machines.brick.base;

import nedelosk.forestday.common.machines.base.BlockMachines;
import nedelosk.nedeloskcore.api.machines.INedeloskMachine;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBrickMachines extends BlockMachines implements INedeloskMachine{

	protected BlockBrickMachines(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

	@Override
	public int getTier() {
		return 0;
	}

}
