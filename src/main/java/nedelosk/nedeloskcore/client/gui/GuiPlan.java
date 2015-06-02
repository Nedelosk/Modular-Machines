package nedelosk.nedeloskcore.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.templates.crop.CropManager;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;

public class GuiPlan extends GuiBase {

	public GuiPlan(TileBaseInventory tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	@Override
	protected void renderProgressBar() {
		
	}
	
	@Override
	public void drawScreen(int x, int y, float p_73863_3_) {
		super.drawScreen(x, y, p_73863_3_);
		renderButtomInput(((TilePlan)tile).input[0],((TilePlan)tile).inputs[0], this.guiLeft + 8, this.guiTop + 7, x, y);
		renderButtomInput(((TilePlan)tile).input[1],((TilePlan)tile).inputs[1], this.guiLeft + 8, this.guiTop + 25, x, y);
		renderButtomInput(((TilePlan)tile).input[2],((TilePlan)tile).inputs[2], this.guiLeft + 8, this.guiTop + 43, x, y);
		renderButtomInput(((TilePlan)tile).input[3],((TilePlan)tile).inputs[3], this.guiLeft + 8, this.guiTop + 61, x, y);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
		
	    drawTexturedModalRect(this.guiLeft + 53, this.guiTop + 6, 0, ySize, ((TilePlan)tile).getScaledProcess(89), 4);
	}
	
	@Override
	protected String getGuiName() {
		return "plan";
	}
	
	public void renderButtomInput(ItemStack input, ItemStack input2, int xPosition, int yPosition, int mx, int my)
	{
		if(input2 != null)
		{
			boolean inside = mx >= xPosition && my >= yPosition
					&& mx < xPosition + 16 && my < yPosition + 16;
			
			List<String> tooltip = new ArrayList<String>();
			tooltip.add(input2.getDisplayName() + " " + ((input != null) ? input.stackSize : 0) + " / " + input2.stackSize);
		
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		if(!ForgeHooksClient.renderInventoryItem(RenderBlocks.getInstance(), Minecraft.getMinecraft().renderEngine, input2, false, zLevel +3.0F, (float) xPosition, (float)yPosition))
		{
			RenderItem.getInstance().renderItemIntoGUI(this.mc.fontRenderer, Minecraft.getMinecraft().renderEngine, input2, xPosition, yPosition);
		}
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		
		int tooltipY = (tooltip.size() - 1) * 10;
        if (inside) {
    		RenderUtils.renderTooltip(mx, my + tooltipY, tooltip);
        }
		}
	}

}
