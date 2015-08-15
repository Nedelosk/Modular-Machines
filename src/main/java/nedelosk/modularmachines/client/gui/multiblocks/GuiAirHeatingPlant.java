package nedelosk.modularmachines.client.gui.multiblocks;

import nedelosk.modularmachines.client.gui.widget.WidgetHeatBar;
import nedelosk.modularmachines.common.multiblocks.MultiblockAirHeatingPlant;
import nedelosk.nedeloskcore.api.MultiblockModifierValveType.ValveType;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.core.registry.NCBlocks;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiAirHeatingPlant extends GuiBase {

	public GuiAirHeatingPlant(TileMultiblockBase tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if(tile.getBlockType() != NCBlocks.Multiblock.block() && tile.master != null)
		{
			if(tile.modifier.valveType == ValveType.INPUT)
					widgetManager.add(new WidgetFluidTank(((MultiblockAirHeatingPlant)tile.master.multiblock).tankInput, 79, 12));
			if(tile.modifier.filter == "gas")
				widgetManager.add(new WidgetFluidTank(((MultiblockAirHeatingPlant)tile.master.multiblock).tankGas, 79, 12));
			else if(tile.modifier.filter == "fluid")
				widgetManager.add(new WidgetFluidTank(((MultiblockAirHeatingPlant)tile.master.multiblock).tank, 79, 12));
		}
		else if(tile.getBlockType() == NCBlocks.Multiblock.block())
		{
			widgetManager.add(new WidgetHeatBar(((MultiblockAirHeatingPlant)tile.master.multiblock).heat, ((MultiblockAirHeatingPlant)tile.master.multiblock).heatTotal, 82, 8));
		}
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		
		if(((TileMultiblockBase)tile).master == null)
			return;
		if(widgetManager != null && widgetManager.getWidgets().size() > 0 && widgetManager.getWidgets().get(0) instanceof WidgetFluidTank)
		{
			if(((TileMultiblockBase)tile).modifier.valveType == ValveType.INPUT)
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = ((MultiblockAirHeatingPlant)((TileMultiblockBase)tile).master.multiblock).tankInput;
			if(((TileMultiblockBase)tile).modifier.filter == "gas")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = ((MultiblockAirHeatingPlant)((TileMultiblockBase)tile).master.multiblock).tankGas;
			if(((TileMultiblockBase)tile).modifier.filter == "fluid")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = ((MultiblockAirHeatingPlant)((TileMultiblockBase)tile).master.multiblock).tank;
		}
		
		if(widgetManager != null && widgetManager.getWidgets().size() > 0 && widgetManager.getWidgets().get(0) instanceof WidgetHeatBar)
		{
			if(((MultiblockAirHeatingPlant)((TileMultiblockBase)tile).master.multiblock).heat != ((WidgetHeatBar)widgetManager.getWidgets().get(0)).heat)
				((WidgetHeatBar)widgetManager.getWidgets().get(0)).heat = ((MultiblockAirHeatingPlant)((TileMultiblockBase)tile).master.multiblock).heat;
			if(((MultiblockAirHeatingPlant)((TileMultiblockBase)tile).master.multiblock).heatTotal != ((WidgetHeatBar)widgetManager.getWidgets().get(0)).heatTotal)
				((WidgetHeatBar)widgetManager.getWidgets().get(0)).heatTotal = ((MultiblockAirHeatingPlant)((TileMultiblockBase)tile).master.multiblock).heatTotal;
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
