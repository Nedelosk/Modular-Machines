package de.nedelosk.forestmods.client.gui;

import de.nedelosk.forestcore.library.gui.GuiBase;
import de.nedelosk.forestmods.common.blocks.tile.TileMachineBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class GuiMachine<T extends TileMachineBase> extends GuiBase<T> {

	public GuiMachine(T tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	@Override
	protected void renderProgressBar() {
	}

	@Override
	protected String getModName() {
		return "forestday";
	}
}
