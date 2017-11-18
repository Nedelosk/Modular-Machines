/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules.pages;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.pages.IModuleComponent;
import modularmachines.api.modules.pages.ModuleComponent;
import modularmachines.api.modules.pages.Page;
import modularmachines.client.gui.WidgetManager;
import modularmachines.client.gui.widgets.Widget;
import modularmachines.common.utils.RenderUtil;

@SideOnly(Side.CLIENT)
public class PageWidget<M extends Module> extends Page {
	protected static final ResourceLocation modularWdgets = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	
	@Nullable
	public WidgetManager widgetManager;
	public final M module;
	
	public PageWidget(IModuleComponent<M> component, GuiContainer gui) {
		super(component, gui);
		this.module = component.getParent();
	}
	
	public PageWidget(IModuleComponent<M> component, GuiContainer gui, int xSize, int ySize) {
		super(component, gui, xSize, ySize);
		this.module = component.getParent();
	}
	
	@Override
	public void init() {
		//if(gui instanceof GuiModuleLogic){
		//widgetManager = ((GuiModuleLogic) gui).getWidgetManager();
		//}else{
		widgetManager = null;
		//}
	}
	
	public M getModule() {
		return module;
	}
	
	@Override
	public void addWidgets() {
		List<Module> modulesWithPages = ModuleHelper.getModulesWithComponents(module.getContainer());
		int i = 0;
		if (!modulesWithPages.isEmpty() && modulesWithPages.size() > 1) {
			for (i = 0; i < modulesWithPages.size(); i++) {
				Module module = modulesWithPages.get(i);
				boolean isRight = i >= 7;
				//	addWidget(new WidgetModuleTab(isRight ? getXSize() : -28, 8 + 22 * (isRight ? i - 7 : i), module, isRight));
			}
		}
		boolean isRight = i >= 7;
		//Widget widget = new WidgetAssembleTab(isRight ? getXSize() : -28, 8 + 22 * (isRight ? i - 7 : i), isRight);
		//	addWidget(widget);
		List<ModuleComponent> pages = module.getComponents();
		if (!pages.isEmpty() && pages.size() > 1) {
			for (int pageIndex = 0; pageIndex < pages.size(); pageIndex++) {
				ModuleComponent page = pages.get(pageIndex);
				//	addWidget(new WidgetPageTab(pageIndex > 4 ? 12 + (pageIndex - 5) * 30 : 12 + pageIndex * 30, pageIndex > 4 ? getYSize() : -19, page));
			}
		}
	}
	
	public void addWidget(Widget widget) {
		//	if(gui instanceof GuiModuleLogic){
		//widgetManager.add(widget);
		//}
	}
	
	protected ResourceLocation getGuiTexture() {
		return new ResourceLocation("modularmachines:textures/gui/modular_machine.png");
	}
	
	protected ResourceLocation getInventoryTexture() {
		return new ResourceLocation("modularmachines:textures/gui/inventory_player.png");
	}
	
	protected boolean renderPageTitle() {
		return true;
	}
	
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		if (renderPageTitle() && component.getTitle() != null) {
			fontRenderer.drawString(component.getTitle(), gui.xSize / 2 - (fontRenderer.getStringWidth(component.getTitle()) / 2), 6, 4210752);
		}
	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY) {
		if (gui != null && getGuiTexture() != null) {
			Minecraft.getMinecraft().renderEngine.bindTexture(getGuiTexture());
			gui.drawTexturedModalRect(gui.getGuiLeft(), gui.getGuiTop(), 0, 0, getXSize(), getYSize());
		}
		drawPlayerInventory();
		drawSlots();
	}
	
	protected void drawSlots() {
		if (gui != null) {
			List<Slot> slots = container.inventorySlots;
			for (int slotID = 36; slotID < slots.size(); slotID++) {
				Slot slot = slots.get(slotID);
				drawSlot(slot);
			}
		}
	}
	
	protected void drawSlot(Slot slot) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.texture(modularWdgets);
		gui.drawTexturedModalRect(gui.getGuiLeft() + slot.xPos - 1, gui.getGuiTop() + slot.yPos - 1, 56, 238, 18, 18);
	}
	
	protected void drawPlayerInventory() {
		if (gui != null && getInventoryTexture() != null && component.getPlayerInvPosition() >= 0) {
			GlStateManager.enableAlpha();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(getInventoryTexture());
			int invPosition = component.getPlayerInvPosition();
			gui.drawTexturedModalRect(gui.getGuiLeft() + 7, gui.getGuiTop() + invPosition, 0, 0, 162, 76);
			GlStateManager.disableAlpha();
		}
	}
}
