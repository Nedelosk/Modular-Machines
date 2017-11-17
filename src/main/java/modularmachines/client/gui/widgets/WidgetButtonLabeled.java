/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.client.gui.widgets;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

import modularmachines.common.ModularMachines;
import modularmachines.common.utils.RenderUtil;

public class WidgetButtonLabeled extends Widget {
	private boolean isActive;
	private final String label;
	public WidgetButtonLabeled(int posX, int posY, String label) {
		super(posX, posY, 18, 18);
		this.label = label;
	}
	
	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.texture(widgetTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, 148, isActive ? 18 : 0, pos.width, pos.height);
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.5, 0.5, 0.5);
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		fontRenderer.drawString(label, (guiLeft *2 + pos.x * 2 + pos.width - fontRenderer.getStringWidth(label) / 2) , (guiTop *2 + pos.y * 2 + pos.height - fontRenderer.FONT_HEIGHT / 2), isActive ? Color.WHITE.getRGB() : Color.LIGHT_GRAY.getRGB());
		GlStateManager.popMatrix();
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		ModularMachines.proxy.playButtonClick();
		setActive(!isActive);
	}
	
	public void setActive(boolean active) {
		isActive = active;
	}
	
	public boolean isActive() {
		return isActive;
	}
}
