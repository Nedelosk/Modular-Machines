package nedelosk.modularmachines.client.gui.multiblocks;

import nedelosk.modularmachines.client.gui.widget.WidgetHeatBar;
import nedelosk.modularmachines.common.multiblocks.MultiblockCokeOven;
import nedelosk.nedeloskcore.api.MultiblockModifierValveType.ValveType;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.core.registry.NCBlocks;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCokeOven extends GuiBase {

	public GuiCokeOven(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if(tile.getBlockType() == NCBlocks.Multiblock.block())
		{
			widgetManager.add(new WidgetHeatBar(((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).heat, ((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).heatTotal, 82, 8));
		}
		else if(((TileMultiblockBase)tile).master != null)
		{
			if(((TileMultiblockBase)tile).modifier.valveType == ValveType.OUTPUT)
					widgetManager.add(new WidgetFluidTank(((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).tank, 79, 12));
			else if(((TileMultiblockBase)tile).modifier.valveType == ValveType.INPUT)
					widgetManager.add(new WidgetFluidTank(((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).tankGas, 79, 12));
		}
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		
	}
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		
		if(widgetManager != null && widgetManager.getWidgets().size() > 0 && widgetManager.getWidgets().get(0) instanceof WidgetFluidTank)
		{
		if(((TileMultiblockBase)tile).modifier.valveType == ValveType.OUTPUT)
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = ((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).tank;
		else if(((TileMultiblockBase)tile).modifier.valveType == ValveType.INPUT)
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = ((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).tankGas;
		}
		
		if(widgetManager != null && widgetManager.getWidgets().size() > 0 && widgetManager.getWidgets().get(0) instanceof WidgetHeatBar)
		{
			if(((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).heat != ((WidgetHeatBar)widgetManager.getWidgets().get(0)).heat)
				((WidgetHeatBar)widgetManager.getWidgets().get(0)).heat = ((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).heat;
			if(((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).heatTotal != ((WidgetHeatBar)widgetManager.getWidgets().get(0)).heatTotal)
				((WidgetHeatBar)widgetManager.getWidgets().get(0)).heatTotal = ((MultiblockCokeOven)((TileMultiblockBase)tile).master.multiblock).heatTotal;
		}
	}

	@Override
	protected void renderProgressBar() {
		
	}

	@Override
	protected String getGuiName() {
		if(tile.getBlockType() == NCBlocks.Multiblock_Valve.block())
			return "gui_blastfurnace_fluid";
		return "gui_coke_oven";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}

}
