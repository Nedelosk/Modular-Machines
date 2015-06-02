package nedelosk.forestday.structure.macerator.gui;

import java.awt.Color;

import nedelosk.forestday.structure.base.gui.GuiController;
import nedelosk.forestday.structure.macerator.blocks.tile.TileMaceratorController;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiMaceratorController extends GuiController {

	private TileMaceratorController tile;
	private ResourceLocation guiTexture = new ResourceLocation("forestday", "textures/gui/controller.png");
	
	public GuiMaceratorController(InventoryPlayer inventory, TileMaceratorController tile) {
		super(inventory, tile);
		this.tile = tile;
	}
		
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {

    	fontRendererObj.drawString(StatCollector.translateToLocal("container.controller.macerator"), 8, ySize - 165, 4210752);
        
        String[] display = new String[]{
        	"Roughness: " + this.tile.getRoughness(),
        	"Regulator Roughness: " + this.tile.getRoughnessRegulator(),
        	"Coil Roughness: " + this.tile.getRoughnessCoil(),
        	"Max Coil Roughness: " + this.tile.getRoughnessCoilmax()
        };
      
        int x = 45;
        int y = ySize - 140;
        for(String s : display){
        	y+=RenderUtils.glDrawScaledString(fontRendererObj, s, x, y, 0.7f, Color.WHITE.getRGB());
        }
        
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
	    
	    drawTexturedModalRect(sx + 42, sy + 72, 0, 166, this.tile.getScaledProcess(92), 6);
	}

}
