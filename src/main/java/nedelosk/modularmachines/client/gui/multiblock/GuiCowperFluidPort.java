package nedelosk.modularmachines.client.gui.multiblock;

import nedelosk.forestcore.library.gui.GuiBase;
import nedelosk.forestcore.library.gui.WidgetFluidTank;
import nedelosk.modularmachines.common.multiblock.cowper.TileCowperFluidPort;
import nedelosk.modularmachines.common.multiblock.cowper.TileCowperFluidPort.PortType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCowperFluidPort extends GuiBase<TileCowperFluidPort> {

	public GuiCowperFluidPort(TileCowperFluidPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if (tile.isConnected()) {
			if (tile.getType() == PortType.INPUT)
				widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(0), 79, 12));
			else if (tile.getType() == PortType.FUEL)
				widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(1), 79, 12));
			else if (tile.getType() == PortType.OUTPUT)
				widgetManager.add(new WidgetFluidTank(tile.getController().getTankManager().getTank(2), 79, 12));
		}
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);

		if (widgetManager != null && widgetManager.getWidgets().size() > 0
				&& widgetManager.getWidgets().get(0) instanceof WidgetFluidTank) {
			if (tile.getType() == PortType.INPUT)
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getController().getTankManager()
						.getTank(0);
			else if (tile.getType() == PortType.FUEL)
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getController().getTankManager()
						.getTank(1);
			else if (tile.getType() == PortType.OUTPUT)
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getController().getTankManager()
						.getTank(2);
		}
	}

	@Override
	protected void renderProgressBar() {

	}

	@Override
	protected String getGuiName() {
		return "gui_blastfurnace_fluid";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}

}
