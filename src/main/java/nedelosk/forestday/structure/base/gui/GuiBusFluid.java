package nedelosk.forestday.structure.base.gui;

import nedelosk.forestday.structure.base.blocks.tile.TileBusFluid;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiBusFluid extends GuiBase {
	
	public GuiBusFluid(InventoryPlayer inventory, TileBusFluid tile) {
		super(tile, inventory);
		this.tile = tile;
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

    	fontRendererObj.drawString(StatCollector.translateToLocal("container.bus"), 8, ySize - 165, 4210752);
    	if(((TileBusFluid)tile).getTank().getFluid() != null)
    	{
    	fontRendererObj.drawString(StatCollector.translateToLocal(((TileBusFluid)tile).getTank().getFluid().getLocalizedName()) + ": " + ((TileBusFluid)tile).getTank().getCapacity() + " / " + ((TileBusFluid)tile).getTank().getFluid().amount, 37, ySize - 162, 4210752);
    	}
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void renderProgressBar() {
	    
		RenderUtils.bindBlockTexture();
		RenderUtils.renderGuiTank(((TileBusFluid)tile).getTank(), guiLeft + 80, guiTop + 15, zLevel, 16,52); 
	}

	@Override
	protected String getGuiName() {
		return "fluid_output";
	}

}
