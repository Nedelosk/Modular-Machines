package nedelosk.modularmachines.client.gui.multiblocks;

import nedelosk.forestday.api.guis.GuiBase;
import nedelosk.forestday.api.guis.WidgetFluidTank;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.modularmachines.common.multiblocks.MultiblockFermenter;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFermenter extends GuiBase<TileMultiblockBase<MultiblockFermenter>> {

	public GuiFermenter(TileMultiblockBase<MultiblockFermenter> tile, InventoryPlayer inventory) {
		super(tile, inventory);

		if (!tile.isMaster) {
			widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tank, 7, 11));
			widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tank2, 79, 11));
			widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tankOut, 151, 11));
		} else {
			widgetManager.add(new WidgetFluidTank(tile.getMultiblock().tank, 7, 11));
			widgetManager.add(new WidgetFluidTank(tile.getMultiblock().tank2, 79, 11));
			widgetManager.add(new WidgetFluidTank(tile.getMultiblock().tankOut, 151, 11));
		}
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);

		if (!tile.isMaster) {
			((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.master.getMultiblock().tank;
			((WidgetFluidTank) widgetManager.getWidgets().get(1)).tank = tile.master.getMultiblock().tank2;
			((WidgetFluidTank) widgetManager.getWidgets().get(2)).tank = tile.master.getMultiblock().tankOut;
		} else {
			((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.getMultiblock().tank;
			((WidgetFluidTank) widgetManager.getWidgets().get(1)).tank = tile.getMultiblock().tank2;
			((WidgetFluidTank) widgetManager.getWidgets().get(2)).tank = tile.getMultiblock().tankOut;
		}
	}

	@Override
	protected void renderProgressBar() {

	}

	@Override
	protected String getGuiName() {
		return "gui_fermenter";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}

}
