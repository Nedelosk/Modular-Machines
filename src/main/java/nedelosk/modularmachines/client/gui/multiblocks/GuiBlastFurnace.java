package nedelosk.modularmachines.client.gui.multiblocks;

import nedelosk.modularmachines.client.gui.widget.WidgetHeatBar;
import nedelosk.modularmachines.common.multiblocks.MultiblockBlastFurnace;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.client.gui.widget.WidgetFluidTank;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.core.registry.NCBlocks;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiBlastFurnace extends GuiBase {

	public GuiBlastFurnace(TileMultiblockBase tile, InventoryPlayer inventory) {
		super(tile, inventory);
		if(tile.getBlockType() != NCBlocks.Multiblock.block() && tile.master != null && (tile.modifier != null))
		{
			if(tile.master.modifier.filter == "output")
					widgetManager.add(new WidgetFluidTank(((MultiblockBlastFurnace)tile.master.multiblock).tank, 79, 12));
			else if(tile.master.modifier.filter == "slag")
					widgetManager.add(new WidgetFluidTank(((MultiblockBlastFurnace)tile.master.multiblock).tankSlag, 79, 12));
			else if(tile.master.modifier.filter == "gas.blastfurnace")
					widgetManager.add(new WidgetFluidTank(((MultiblockBlastFurnace)tile.master.multiblock).tankGas, 79, 12));
			else if(tile.master.modifier.filter == "air.hot")
					widgetManager.add(new WidgetFluidTank(((MultiblockBlastFurnace)tile.master.multiblock).tankAirHot, 79, 12));
		}
		if(tile.getBlockType() == NCBlocks.Multiblock.block())
		{
			widgetManager.add(new WidgetHeatBar(((MultiblockBlastFurnace)tile.master.multiblock).heat,((MultiblockBlastFurnace)tile.master.multiblock).heatTotal, 82, 8));
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
		if(((TileMultiblockBase)tile).master.modifier.filter == "output")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = ((MultiblockBlastFurnace)((TileMultiblockBase)tile).master.multiblock).tank;
		else if(((TileMultiblockBase)tile).master.modifier.filter == "slag")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = ((MultiblockBlastFurnace)((TileMultiblockBase)tile).master.multiblock).tankSlag;
		else if(((TileMultiblockBase)tile).master.modifier.filter == "gas.blastfurnace")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank =((MultiblockBlastFurnace)((TileMultiblockBase)tile).master.multiblock).tankGas;
		else if(((TileMultiblockBase)tile).master.modifier.filter == "air.hot")
			((WidgetFluidTank)widgetManager.getWidgets().get(0)).tank = ((MultiblockBlastFurnace)((TileMultiblockBase)tile).master.multiblock).tankAirHot;
		}
		
		if(widgetManager != null && widgetManager.getWidgets().size() > 0 && widgetManager.getWidgets().get(0) instanceof WidgetHeatBar)
		{
			if(((MultiblockBlastFurnace)((TileMultiblockBase)tile).master.multiblock).heat != ((WidgetHeatBar)widgetManager.getWidgets().get(0)).heat)
				((WidgetHeatBar)widgetManager.getWidgets().get(0)).heat = ((MultiblockBlastFurnace)((TileMultiblockBase)tile).master.multiblock).heat;
			if(((MultiblockBlastFurnace)((TileMultiblockBase)tile).master.multiblock).heatTotal != ((WidgetHeatBar)widgetManager.getWidgets().get(0)).heatTotal)
				((WidgetHeatBar)widgetManager.getWidgets().get(0)).heatTotal = ((MultiblockBlastFurnace)((TileMultiblockBase)tile).master.multiblock).heatTotal;
		}
	}

	@Override
	protected void renderProgressBar() {
		
	}

	@Override
	protected String getGuiName() {
		if(((TileMultiblockBase)tile).modifier.filter != null && tile.getBlockType() == NCBlocks.Multiblock_Valve.block() || tile.getBlockType() == NCBlocks.Multiblock.block())
			return "gui_blastfurnace_fluid";
		return "gui_blastfurnace_item";
	}

	@Override
	protected String getModName() {
		return "modularmachines";
	}

}
