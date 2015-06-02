package nedelosk.forestday.structure.base.gui;

import nedelosk.forestday.structure.base.blocks.tile.TileController;
import nedelosk.nedeloskcore.client.gui.GuiBase;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiController extends GuiBase {

	private TileController tile;
	private ResourceLocation guiTexture = new ResourceLocation("forestday", "textures/gui/controller.png");
	
	public GuiController(InventoryPlayer inventory, TileController tile) {
		super(tile, inventory);
		this.tile = tile;
	}
		
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {

    	fontRendererObj.drawString(StatCollector.translateToLocal("container.controller"), 8, ySize - 165, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    RenderUtils.bindTexture(guiTexture);
	    int sx = (width - xSize) / 2;
	    int sy = (height - ySize) / 2;
	    drawTexturedModalRect(sx, sy, 0, 0, xSize, ySize);
	    drawTexturedModalRect(sx + 11, sy + 14, 176, 0, 20, 20);
	    drawTexturedModalRect(sx + 12, sy + 14, 197, 0, 18, 18);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
		
	}

	@Override
	protected void renderProgressBar() {
		
	}

	@Override
	protected String getGuiName() {
		return null;
	}

}
