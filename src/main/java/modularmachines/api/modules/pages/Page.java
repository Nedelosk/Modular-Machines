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
public class Page implements IPage {
	protected final IModuleComponent component;
	protected final GuiContainer gui;
	protected final Container container;
	protected int xSize;
	protected int ySize;
	
	public Page(IModuleComponent component, GuiContainer gui) {
		this(component, gui, 176, 166);
	}
	
	public Page(IModuleComponent component, GuiContainer gui, int xSize, int ySize) {
		this.component = component;
		this.gui = gui;
		this.container = gui.inventorySlots;
		this.xSize = xSize;
		this.ySize = ySize;
		init();
	}
	
	@Override
	public IModuleComponent getComponent() {
		return component;
	}
	
	protected void init() {
	}
	
	@Override
	public void addWidgets() {
	
	}
	
	@Override
	@Nullable
	public GuiContainer getGui() {
		return gui;
	}
	
	@Override
	@Nullable
	public Container getContainer() {
		return container;
	}
	
	@Override
	public int getXSize() {
		return xSize;
	}
	
	@Override
	public int getYSize() {
		return ySize;
	}
	
	@Override
	public void updateGui() {
	}
	
	@Override
	public void initGui() {
	}
	
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
	}
	
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
	}
	
	@Override
	public boolean mouseReleased(int mouseX, int mouseY, int state) {
		return false;
	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY) {
	}
	
	@Override
	public void drawTooltips(int mouseX, int mouseY) {
	}
	
	@Override
	public void drawFrontBackground(int mouseX, int mouseY) {
	}
	
}
