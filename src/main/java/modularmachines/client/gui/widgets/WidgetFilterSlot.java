/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.client.gui.widgets;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.client.config.GuiUtils;

import modularmachines.client.gui.WidgetManager;

public class WidgetFilterSlot extends Widget {
	private ItemStack stack;
	public WidgetFilterSlot(int posX, int posY) {
		super(posX, posY, 16, 16);
		this.stack = ItemStack.EMPTY;
	}
	
	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if(!stack.isEmpty()) {
			gui.drawItemStack(stack, guiLeft + pos.x, guiTop + pos.y);
		}
		WidgetManager manager = gui.getWidgetManager();
		if(isMouseOver(manager.mouseX - guiLeft, manager.mouseY - guiTop)){
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			GlStateManager.colorMask(true, true, true, false);
			int x = guiLeft + pos.x;
			int y = guiTop + pos.y;
			GuiUtils.drawGradientRect(0, x, y, x + 16, y + 16, -2130706433, -2130706433);
			GlStateManager.colorMask(true, true, true, true);
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
		}
	}
	
	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		super.handleMouseClick(mouseX, mouseY, mouseButton);
		EntityPlayer player = Minecraft.getMinecraft().player;
		ItemStack itemStack = player.inventory.getItemStack();
		if(!itemStack.isEmpty()){
			stack = itemStack.copy();
		}else if(GuiScreen.isShiftKeyDown()){
			stack = ItemStack.EMPTY;
		}
	}
	
	@Override
	public List<String> getTooltip() {
		if(stack.isEmpty()){
			return Collections.emptyList();
		}
		return stack.getTooltip(Minecraft.getMinecraft().player, ITooltipFlag.TooltipFlags.ADVANCED);
	}
	
	public ItemStack getStack() {
		return stack;
	}
}
