package de.nedelosk.forestmods.common.modules.handlers;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.api.modules.handlers.IModulePage;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.client.gui.buttons.ButtonModulePageTab;
import de.nedelosk.forestmods.client.gui.buttons.ButtonModuleTab;
import de.nedelosk.forestmods.client.gui.widgets.WidgetProgressBar;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public abstract class ModulePage<M extends IModule> implements IModulePage {

	protected int pageID;
	protected IModular modular;
	protected ModuleStack<M> moduleStack;
	protected M module;
	@SideOnly(Side.CLIENT)
	protected IGuiBase gui;

	public ModulePage(int pageID, IModular modular, ModuleStack<M> moduleStack) {
		this.pageID = pageID;
		this.modular = modular;
		this.moduleStack = moduleStack;
		this.module = moduleStack.getModule();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(int x, int y) {
		List<Widget> widgets = gui.getWidgetManager().getWidgets();
		for(Widget widget : widgets) {
			if (widget instanceof WidgetProgressBar) {
				IModuleEngine engine = ModularUtils.getEngine(modular).getModule();
				if (engine != null) {
					int burnTime = engine.getBurnTime();
					int burnTimeTotal = engine.getBurnTimeTotal();
					((WidgetProgressBar) widget).burntime = burnTime;
					((WidgetProgressBar) widget).burntimeTotal = burnTimeTotal;
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected boolean renderInventoryName() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		if (renderInventoryName() && !module.isHandlerDisabled(ModuleManager.inventoryType) && module.getInventory().hasCustomInventoryName()) {
			fontRenderer.drawString(module.getInventory().getInventoryName(), 90 - (fontRenderer.getStringWidth(module.getInventory().getInventoryName()) / 2),
					6, 4210752);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawBackground(int mouseX, int mouseY) {
		RenderUtil.bindTexture(getGuiTexture());
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft(), gui.getGuiTop(), 0, 0, getXSize(), getYSize());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawFrontBackground(int mouseX, int mouseY) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawSlots() {
		if (!module.isHandlerDisabled(ModuleManager.inventoryType) && module.getInventory() != null && gui.getGui() instanceof GuiContainer) {
			Container container = ((GuiContainer) gui).inventorySlots;
			for(int slotID = 36; slotID < container.inventorySlots.size(); slotID++) {
				Slot slot = ((ArrayList<Slot>) container.inventorySlots).get(slotID);
				if (slot.getSlotIndex() < module.getInventory().getSizeInventory()) {
					drawSlot(slot);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void drawSlot(Slot slot) {
		RenderUtil.bindTexture(getGuiTexture());
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + slot.xDisplayPosition - 1, gui.getGuiTop() + slot.yDisplayPosition - 1, 56, 238, 18, 18);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawPlayerInventory() {
		RenderUtil.bindTexture(getInventoryTexture());
		int invPosition = getPlayerInvPosition();
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + 7, gui.getGuiTop() + invPosition, 7, invPosition, 162, 76);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getXSize() {
		return 176;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getYSize() {
		return 166;
	}

	@Override
	public int getPlayerInvPosition() {
		return 83;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getGuiTexture() {
		return new ResourceLocation("forestmods:textures/gui/modular_machine.png");
	}

	@SideOnly(Side.CLIENT)
	protected ResourceLocation getInventoryTexture() {
		return new ResourceLocation("forestmods:textures/gui/inventory_player.png");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addButtons(List buttons) {
		List<ModuleStack> stacksWithGui = Lists.newArrayList();
		for(ModuleStack stack : modular.getModuleStacks()) {
			if (stack != null && stack.getModule() != null && !stack.getModule().isHandlerDisabled(ModuleManager.guiType)
					&& stack.getModule().getPages() != null) {
				stacksWithGui.add(stack);
			}
		}
		for(int i = 0; i < stacksWithGui.size(); i++) {
			ModuleStack stack = stacksWithGui.get(i);
			buttons.add(new ButtonModuleTab(i, (i >= 7) ? gui.getGuiLeft() + getXSize() : gui.getGuiLeft() - 28,
					(i >= 7) ? gui.getGuiTop() + 8 + 22 * (i - 7) : gui.getGuiTop() + 8 + 22 * i, stack, modular.getTile(), i >= 7));
		}
		for(int pageID = 0; pageID < module.getPages().length; pageID++) {
			IModulePage page = module.getPages()[pageID];
			buttons.add(new ButtonModulePageTab(gui.getButtonManager().getButtons().size(),
					pageID > 4 ? 12 + gui.getGuiLeft() + (pageID - 5) * 30 : 12 + gui.getGuiLeft() + pageID * 30,
					pageID > 4 ? gui.getGuiTop() + getYSize() : gui.getGuiTop() - 19, module.getModuleStack(), pageID > 4 ? true : false, pageID));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(List widgets) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawTooltips(int mouseX, int mouseY) {
	}

	@Override
	public int getPageID() {
		return pageID;
	}

	@Override
	public IModular getModular() {
		return modular;
	}

	@Override
	public ModuleStack<M> getModuleStack() {
		return moduleStack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IGuiBase getGui() {
		return gui;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setGui(IGuiBase gui) {
		this.gui = gui;
	}
}
