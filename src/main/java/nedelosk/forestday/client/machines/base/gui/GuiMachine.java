package nedelosk.forestday.client.machines.base.gui;

import nedelosk.forestday.common.core.Defaults;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class GuiMachine extends GuiBase {

	public GuiMachine(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected String getModName() {
		return Defaults.MOD_ID.toLowerCase();
	}

}
