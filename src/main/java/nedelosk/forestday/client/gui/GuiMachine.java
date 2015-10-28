package nedelosk.forestday.client.gui;

import nedelosk.forestday.common.blocks.tiles.TileMachineBase;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class GuiMachine<T extends TileMachineBase> extends GuiBase<T> {

	public GuiMachine(T tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected String getModName() {
		return "forestday";
	}

}
