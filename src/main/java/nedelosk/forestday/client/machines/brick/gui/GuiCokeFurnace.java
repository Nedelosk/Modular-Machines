package nedelosk.forestday.client.machines.brick.gui;

import nedelosk.forestday.common.machines.brick.furnace.coke.TileCokeFurnace;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiCokeFurnace extends GuiBase {
	
	public GuiCokeFurnace(InventoryPlayer inventory, TileCokeFurnace tile) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

		fontRenderer.drawString(StatCollector.translateToLocal("container.machine.furnace.coke"), 8, ySize - 163, 4210752);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void renderProgressBar() {
	    int sx = (width - xSize) / 2;
	    int sy = (height - ySize) / 2;
	    drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	    
	    RenderUtils.bindBlockTexture();
	    RenderUtils.renderGuiTank(((TileCokeFurnace)tile).getTank(), guiLeft + 148, guiTop + 15, zLevel, 16,52);
	}

	@Override
	protected String getGuiName() {
		return "coke_furnace";
	}

}
