package nedelosk.forestday.common.machines.brick.base;

import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public abstract class TileBricks extends TileBaseInventory {

	public TileBricks(int slots) {
		super(slots);
	}
	
	public abstract String getMachineName();

	@Override
	public String getMachineTileName() {
		return "machine.brick." + getMachineName();
	}

}
