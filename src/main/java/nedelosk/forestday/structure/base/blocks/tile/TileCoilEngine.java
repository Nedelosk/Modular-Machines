package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class TileCoilEngine extends TileStructureInventory {

	private FluidTankNedelosk tank = new FluidTankNedelosk(16000);
	
	public TileCoilEngine(int maxHeat, int slots, String uid) {
		super(maxHeat, slots, uid);
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return null;
	}
	
	public FluidTankNedelosk getTank() {
		return tank;
	}

	@Override
	public String getMachineTileName() {
		return null;
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
	}

}
