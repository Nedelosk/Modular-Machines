package nedelosk.forestday.client.machines.base.gui;

import nedelosk.forestday.common.core.Defaults;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class GuiMachine<T extends TileMachineBase> extends GuiBase<T> {

	public GuiMachine(T tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected String getModName() {
		return Defaults.MOD_ID.toLowerCase();
	}

}
