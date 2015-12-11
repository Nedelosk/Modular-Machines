package nedelosk.modularmachines.client.gui.multiblocks;

import nedelosk.forestcore.api.gui.GuiBase;
import nedelosk.forestcore.api.gui.WidgetFluidTank;
import nedelosk.forestday.api.multiblocks.MultiblockModifierValveType.ValveType;
import nedelosk.forestday.common.core.managers.FBlockManager;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.modularmachines.client.gui.widget.WidgetHeatBar;
import nedelosk.modularmachines.common.multiblocks.MultiblockAirHeatingPlant;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiAirHeatingPlant extends GuiBase<TileMultiblockBase<MultiblockAirHeatingPlant>> {

	public GuiAirHeatingPlant(TileMultiblockBase<MultiblockAirHeatingPlant> tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if (tile.getBlockType() != FBlockManager.Multiblock.block() && tile.master != null) {
			if (tile.modifier.valveType == ValveType.INPUT)
				widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tankInput, 79, 12));
			if (tile.modifier.filter == "gas")
				widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tankGas, 79, 12));
			else if (tile.modifier.filter == "fluid")
				widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tank, 79, 12));
		} else if (tile.getBlockType() == FBlockManager.Multiblock.block()) {
			widgetManager.add(
					new WidgetHeatBar(tile.master.getMultiblock().heat, tile.master.getMultiblock().heatTotal, 82, 8));
		}
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

	}

	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);

		if (tile.master == null)
			return;
		if (widgetManager != null && widgetManager.getWidgets().size() > 0
				&& widgetManager.getWidgets().get(0) instanceof WidgetFluidTank) {
			if (tile.modifier.valveType == ValveType.INPUT)
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.master.getMultiblock().tankInput;
			if (tile.modifier.filter == "gas")
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.master.getMultiblock().tankGas;
			if (tile.modifier.filter == "fluid")
				((WidgetFluidTank) widgetManager.getWidgets().get(0)).tank = tile.master.getMultiblock().tank;
		}

		if (widgetManager != null && widgetManager.getWidgets().size() > 0
				&& widgetManager.getWidgets().get(0) instanceof WidgetHeatBar) {
			if (tile.master.getMultiblock().heat != ((WidgetHeatBar) widgetManager.getWidgets().get(0)).heat)
				((WidgetHeatBar) widgetManager.getWidgets().get(0)).heat = tile.master.getMultiblock().heat;
			if (tile.master.getMultiblock().heatTotal != ((WidgetHeatBar) widgetManager.getWidgets().get(0)).heatTotal)
				((WidgetHeatBar) widgetManager.getWidgets().get(0)).heatTotal = tile.master.getMultiblock().heatTotal;
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
