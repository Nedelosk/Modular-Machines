package de.nedelosk.forestmods.client.gui.multiblocks;

import de.nedelosk.forestmods.client.gui.GuiForestBase;
import de.nedelosk.forestmods.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperBase;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCowper extends GuiForestBase<TileCowperBase> {

	public GuiCowper(TileCowperBase tile, InventoryPlayer inventory) {
		super(tile, inventory);
		widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(0), 7, 13));
		widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(1), 43, 13));
		widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(2), 115, 13));
		widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(3), 151, 13));
	}

	@Override
	protected void render() {
		if (widgetManager != null) {
			for(int i = 0; i < widgetManager.getWidgets().size(); i++) {
				((WidgetFluidTank) widgetManager.getWidgets().get(i)).tank = handler.getController().getTankManager().getTank(i);
			}
		}
	}

	@Override
	protected String getGuiTexture() {
		return "gui_cowper";
	}
}
