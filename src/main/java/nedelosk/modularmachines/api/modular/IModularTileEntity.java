package nedelosk.modularmachines.api.modular;

import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public interface IModularTileEntity extends IInventory, INBTTagable {

	World getWorldObj();
	
	int getXCoord();
	
	int getYCoord();
	
	int getZCoord();
	
}
