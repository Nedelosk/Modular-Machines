package nedelosk.modularmachines.client.gui.multiblocks;

import nedelosk.forestday.client.gui.GuiBase;
import nedelosk.forestday.client.gui.widget.WidgetFluidTank;
import nedelosk.forestday.common.core.managers.FBlockManager;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.modularmachines.client.gui.widget.WidgetHeatBar;
import nedelosk.modularmachines.common.multiblocks.MultiblockBlastFurnace;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBlastFurnace extends GuiBase<TileMultiblockBase<MultiblockBlastFurnace>> {

	public GuiBlastFurnace(TileMultiblockBase<MultiblockBlastFurnace> tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if(tile.getBlockType() != FBlockManager.Multiblock.block() && tile.master != null && (tile.modifier != null))
		{
			if(tile.master.getModifier().filter == "output")
					widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tank, 79, 12));
			else if(tile.master.getModifier().filter == "slag")
					widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tankSlag, 79, 12));
			else if(tile.master.getModifier().filter == "gas.blastfurnace")
					widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tankGas, 79, 12));
			else if(tile.master.getModifier().filter == "air.hot")
					widgetManager.add(new WidgetFluidTank(tile.master.getMultiblock().tankAirHot, 79, 12));
		}
		if(tile.getBlockType() == FBlockManager.Multiblock.block())
		{
			widgetManager.add(new WidgetHeatBar(tile.master.getMultiblock().heat,tile.master.getMultiblock().heatTotal, 82, 8));
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
		if(tile.master.getModifier().filter == "output")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = tile.master.getMultiblock().tank;
		else if(tile.master.getModifier().filter == "slag")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = tile.master.getMultiblock().tankSlag;
		else if(tile.master.getModifier().filter == "gas.blastfurnace")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank =tile.master.getMultiblock().tankGas;
		else if(tile.master.getModifier().filter == "air.hot")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = tile.master.getMultiblock().tankAirHot;
		}
		
		if(widgetManager != null && widgetManager.getWidgets().size() > 0 && widgetManager.getWidgets().get(0) instanceof WidgetHeatBar)
		{
			if(tile.master.getMultiblock().heat != ((WidgetHeatBar)widgetManager.getWidgets().get(0)).heat)
				((WidgetHeatBar)widgetManager.getWidgets().get(0)).heat = tile.master.getMultiblock().heat;
			if(tile.master.getMultiblock().heatTotal != ((WidgetHeatBar)widgetManager.getWidgets().get(0)).heatTotal)
				((WidgetHeatBar)widgetManager.getWidgets().get(0)).heatTotal = tile.master.getMultiblock().heatTotal;
		}
	}

	@Override
	protected void renderProgressBar() {
		
	}

	@Override
	protected String getGuiName() {
		if(tile.modifier.filter != null && tile.getBlockType() == FBlockManager.Multiblock_Valve.block() || tile.getBlockType() == FBlockManager.Multiblock.block())
			return "gui_blastfurnace_fluid";
		return "gui_blastfurnace_item";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}

}
