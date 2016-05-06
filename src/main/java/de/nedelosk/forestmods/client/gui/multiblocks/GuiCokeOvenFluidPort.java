package de.nedelosk.forestmods.client.gui.multiblocks;

import de.nedelosk.forestmods.client.gui.GuiForestBase;
import de.nedelosk.forestmods.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenFluidPort;
import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenFluidPort.PortType;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCokeOvenFluidPort extends GuiForestBase<TileCokeOvenFluidPort> {

	public GuiCokeOvenFluidPort(TileCokeOvenFluidPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if (tile.getPortType() == PortType.FUEL) {
			widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(0), 79, 12));
		} else if (tile.getPortType() == PortType.OUTPUT) {
			widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(1), 79, 12));
		}
	}

	@Override
	protected void render() {
		if (widgetManager != null && widgetManager.getWidgets().size() > 0 && widgetManager.getWidgets().get(0) instanceof WidgetFluidTank) {
			if (handler.getPortType() == PortType.FUEL) {
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = handler.getController().getTankManager().getTank(0);
			} else if (handler.getPortType() == PortType.OUTPUT) {
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = handler.getController().getTankManager().getTank(1);
			}
		}
	}

	@Override
	protected String getGuiTexture() {
		return "gui_multiblock_fluid";
	}
}
