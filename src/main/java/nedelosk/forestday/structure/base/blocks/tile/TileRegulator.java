package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.structure.base.gui.GuiRegulatorHeat;
import nedelosk.forestday.structure.base.gui.container.ContainerRegulator;
import nedelosk.forestday.structure.base.items.ItemCoilHeat;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class TileRegulator extends TileStructureInventory {

	public TileRegulator(int maxHeat, int slots, String uid) {
		super(maxHeat, slots, "regulator" + uid);
	}
}