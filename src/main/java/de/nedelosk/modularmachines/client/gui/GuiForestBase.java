package de.nedelosk.modularmachines.client.gui;

import de.nedelosk.modularmachines.api.inventory.IGuiHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class GuiForestBase<T extends IGuiHandler> extends GuiBase<T> {

	public GuiForestBase(T tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	@Override
	protected void render() {
	}

	@Override
	protected String getTextureModID() {
		return "forestmods";
	}
}
