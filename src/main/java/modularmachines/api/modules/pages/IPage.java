/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.api.modules.pages;

import javax.annotation.Nullable;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IPage {
	
	IPageComponent getComponent();
	
	void addWidgets();
	
	@Nullable
	GuiContainer getGui();
	
	@Nullable
	Container getContainer();
	
	void initGui();
	
	void updateGui();
	
	void handleMouseClicked(int mouseX, int mouseY, int mouseButton);
	
	boolean mouseReleased(int mouseX, int mouseY, int state);
	
	void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY);
	
	void drawBackground(int mouseX, int mouseY);
	
	void drawTooltips(int mouseX, int mouseY);
	
	void drawFrontBackground(int mouseX, int mouseY);
	
	int getXSize();
	
	int getYSize();
}
