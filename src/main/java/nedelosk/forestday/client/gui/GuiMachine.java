package nedelosk.forestday.client.gui;

import nedelosk.forestcore.api.gui.GuiBase;
import nedelosk.forestday.common.blocks.tiles.TileMachineBase;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class GuiMachine<T extends TileMachineBase> extends GuiBase<T> {

	public GuiMachine(T tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}
	
	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {}
	
	@Override
	protected void renderProgressBar() {}

	@Override
	protected String getModName() {
		return "forestday";
	}

}
