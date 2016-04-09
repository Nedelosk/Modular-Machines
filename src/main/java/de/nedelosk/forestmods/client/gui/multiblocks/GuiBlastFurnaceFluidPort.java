package de.nedelosk.forestmods.client.gui.multiblocks;

import de.nedelosk.forestmods.client.gui.GuiForestBase;
import de.nedelosk.forestmods.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.forestmods.common.blocks.tile.TileBlastFurnaceFluidPort;
import de.nedelosk.forestmods.common.blocks.tile.TileBlastFurnaceFluidPort.PortType;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBlastFurnaceFluidPort extends GuiForestBase<TileBlastFurnaceFluidPort> {

	public GuiBlastFurnaceFluidPort(TileBlastFurnaceFluidPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if (tile.isConnected()) {
			if (tile.getType() == PortType.OUTPUT) {
				widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(2), 79, 12));
			} else if (tile.getType() == PortType.SLAG) {
				widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(1), 79, 12));
			} else if (tile.getType() == PortType.AIR) {
				widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(0), 79, 12));
			} else if (tile.getType() == PortType.GAS) {
				widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(3), 79, 12));
			}
		}
	}

	@Override
	protected void render() {
		if (widgetManager != null && widgetManager.getWidgets().size() > 0 && widgetManager.getWidgets().get(0) instanceof WidgetFluidTank) {
			if (tile.getType() == PortType.OUTPUT) {
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getController().getTankManager().getTank(2);
			} else if (tile.getType() == PortType.SLAG) {
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getController().getTankManager().getTank(1);
			} else if (tile.getType() == PortType.AIR) {
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getController().getTankManager().getTank(0);
			} else if (tile.getType() == PortType.GAS) {
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getController().getTankManager().getTank(3);
			}
		}
	}

	@Override
	protected String getGuiTexture() {
		return "gui_multiblock_fluid";
	}
}
