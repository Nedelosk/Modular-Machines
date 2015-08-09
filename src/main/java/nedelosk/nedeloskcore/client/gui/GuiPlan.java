package nedelosk.nedeloskcore.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import nedelosk.nedeloskcore.common.blocks.tile.TilePlan;
import nedelosk.nedeloskcore.common.network.handler.PacketHandler;
import nedelosk.nedeloskcore.common.network.packets.PacketTilePlan;
import nedelosk.nedeloskcore.utils.RenderUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

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
	public void updateScreen() {
		super.updateScreen();
		if(tile instanceof TilePlan)
		{
			TilePlan plan = (TilePlan) tile;
			if(plan.closeGui)
			{
				plan.closedGui = true;
				plan.getWorldObj().markBlockForUpdate(plan.xCoord, plan.yCoord, plan.zCoord);
				PacketHandler.INSTANCE.sendToServer(new PacketTilePlan(plan));
				mc.displayGuiScreen(null);
				mc.setIngameFocus();
			}
		}
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
		this.drawItemStack(input2, xPosition, yPosition);
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		
		int tooltipY = (tooltip.size() - 1) * 10;
        if (inside) {
    		RenderUtils.renderTooltip(mx, my + tooltipY, tooltip);
        }
		}
	}
	
    private void drawItemStack(ItemStack stack, int x, int y)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }

	@Override
	protected String getModName() {
		return "nedeloskcore";
	}

}
