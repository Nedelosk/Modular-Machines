package de.nedelosk.modularmachines.api.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public abstract class Button<I extends IGuiHandler> extends GuiButton {

	protected RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
	public IGuiBase<I> gui;

	public Button(int ID, int xPosition, int yPosition, int width, int height, String displayString) {
		super(ID, xPosition, yPosition, width, height, displayString);
	}

	public boolean isMouseOver(int x, int y) {
		return x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height;
	}

	public List<String> getTooltip(IGuiBase<I> gui) {
		return null;
	}

	protected void drawItemStack(ItemStack stack, int x, int y) {
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableColorMaterial();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		this.itemRender.zLevel = 100.0F;
		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		this.itemRender.zLevel = 0.0F;
		GlStateManager.disableLighting();

		GlStateManager.popMatrix();
		GlStateManager.disableDepth();
		GlStateManager.enableLighting();
		RenderHelper.disableStandardItemLighting();
	}

	public void onButtonClick(IGuiBase<I> gui) {
	}
}
