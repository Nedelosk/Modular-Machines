package nedelosk.forestday.structure.base.gui;

import nedelosk.forestday.structure.base.blocks.tile.TileCoilEngine;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiCoilEngine extends GuiBase {
	
	public GuiCoilEngine(InventoryPlayer inventory, TileCoilEngine tile) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {

    	fontRendererObj.drawString(StatCollector.translateToLocal("container.coil.engine"), 8, ySize - 165, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		
	}

	@Override
	protected void renderProgressBar() {
		RenderUtils.renderGuiTank(((TileCoilEngine)tile).getTank(), guiLeft + 134, guiTop + 17, zLevel, 16,52); 
		
	}

	@Override
	protected String getGuiName() {
		return "coil_engine";
	}

}
