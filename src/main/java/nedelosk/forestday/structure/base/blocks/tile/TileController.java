package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.structure.base.blocks.BlockBricks;
import nedelosk.forestday.structure.base.gui.GuiController;
import nedelosk.forestday.structure.base.gui.container.ContainerController;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;

public abstract class TileController extends TileStructureInventory {

	public TileController(int maxHeat, String uid) {
		super(maxHeat, 1, "controlle"+ uid);
	}

	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerController(inventory, this);
	}
	
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiController(inventory, this);
	}
	

}
