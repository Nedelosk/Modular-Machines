package nedelosk.modularmachines.client.gui.multiblock;

import nedelosk.forestcore.library.gui.GuiBase;
import nedelosk.forestcore.library.gui.WidgetFluidTank;
import nedelosk.modularmachines.common.multiblock.blastfurnace.TileBlastFurnaceFluidPort;
import nedelosk.modularmachines.common.multiblock.blastfurnace.TileBlastFurnaceFluidPort.PortType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBlastFurnaceFluidPort extends GuiBase<TileBlastFurnaceFluidPort> {

	public GuiBlastFurnaceFluidPort(TileBlastFurnaceFluidPort tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if (tile.getMultiblockController() != null) {
			if (tile.getType() == PortType.OUTPUT)
				widgetManager
						.add(new WidgetFluidTank(tile.getBlastFurnaceController().getTankManager().getTank(2), 79, 12));
			else if (tile.getType() == PortType.SLAG)
				widgetManager
						.add(new WidgetFluidTank(tile.getBlastFurnaceController().getTankManager().getTank(1), 79, 12));
			else if (tile.getType() == PortType.INPUT_AIR_HOT)
				widgetManager
						.add(new WidgetFluidTank(tile.getBlastFurnaceController().getTankManager().getTank(0), 79, 12));
			else if (tile.getType() == PortType.Gas_Blast_Furnace)
				widgetManager
						.add(new WidgetFluidTank(tile.getBlastFurnaceController().getTankManager().getTank(3), 79, 12));
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
			if (tile.getType() == PortType.OUTPUT)
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getBlastFurnaceController()
						.getTankManager().getTank(2);
			else if (tile.getType() == PortType.SLAG)
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getBlastFurnaceController()
						.getTankManager().getTank(1);
			else if (tile.getType() == PortType.INPUT_AIR_HOT)
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getBlastFurnaceController()
						.getTankManager().getTank(0);
			else if (tile.getType() == PortType.Gas_Blast_Furnace)
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getBlastFurnaceController()
						.getTankManager().getTank(3);
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
