package de.nedelosk.modularmachines.api.gui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
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
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		this.zLevel = 200.0F;
		itemRender.zLevel = 200.0F;
		itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		this.zLevel = 0.0F;
		itemRender.zLevel = 0.0F;
		RenderHelper.disableStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public void onButtonClick(IGuiBase<I> gui) {
	}
}
