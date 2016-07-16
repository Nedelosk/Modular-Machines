package de.nedelosk.modularmachines.common.modules.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.Translator;
import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.handlers.tank.FluidTankAdvanced;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleMachine;
import de.nedelosk.modularmachines.client.gui.buttons.ButtonModulePageTab;
import de.nedelosk.modularmachines.client.gui.buttons.ButtonModuleTab;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetFluidTank;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModulePage<M extends IModule> implements IModulePage {

	protected String pageID;
	protected String title;
	protected IModular modular;
	protected IModuleState<M> state;
	@SideOnly(Side.CLIENT)
	protected IGuiBase gui;

	public ModulePage(String pageID, String title, IModuleState<M> module) {
		this.pageID = pageID;
		this.title = title;
		this.modular = module.getModular();
		this.state = module;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(int x, int y) {
		List<Widget> widgets = gui.getWidgetManager().getWidgets();
		for(Widget widget : widgets) {
			if (widget instanceof WidgetProgressBar) {
				WidgetProgressBar widgetBar = (WidgetProgressBar) widget;
				if(state.getModule() instanceof IModuleMachine){
					IModuleMachine tool = (IModuleMachine) state.getModule();
					widgetBar.burntime = tool.getWorkTime(state);
					widgetBar.burntimeTotal = tool.getWorkTimeTotal(state);
				}
			}else if (widget instanceof WidgetFluidTank) {
				WidgetFluidTank widgetTank = (WidgetFluidTank) widget;
				IModuleContentHandler contentHandler = state.getContentHandler(IModuleTank.class);
				if(contentHandler != null && contentHandler instanceof IModuleTank){
					IModuleTank moduleTank = (IModuleTank) contentHandler;

					if(widgetTank.tank instanceof FluidTankAdvanced){
						FluidTankAdvanced tank = (FluidTankAdvanced) widgetTank.tank;
						widgetTank.tank = moduleTank.getTank(tank.index);
					}
				}
			}
		}
	}

	@Override
	public void createTank(IModuleTankBuilder tankBuilder) {
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
	}

	@SideOnly(Side.CLIENT)
	protected boolean renderPageTitle() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		IModuleInventory inventory = state.getContentHandler(IModuleInventory.class);
		if (renderPageTitle()) {
			fontRenderer.drawString(getPageTitle(), 90 - (fontRenderer.getStringWidth(getPageTitle()) / 2),
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
		IModuleInventory inventory = state.getContentHandler(IModuleInventory.class);
		if (inventory != null && gui.getGui() instanceof GuiContainer) {
			Container container = ((GuiContainer) gui).inventorySlots;
			for(int slotID = 36; slotID < container.inventorySlots.size(); slotID++) {
				Slot slot = ((ArrayList<Slot>) container.inventorySlots).get(slotID);
				if (slot.getSlotIndex() < inventory.getSlots()) {
					drawSlot(slot);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void drawSlot(Slot slot) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(getGuiTexture());
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + slot.xDisplayPosition - 1, gui.getGuiTop() + slot.yDisplayPosition - 1, 56, 238, 18, 18);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawPlayerInventory() {
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(getInventoryTexture());
		int invPosition = getPlayerInvPosition();
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + 7, gui.getGuiTop() + invPosition, 7, invPosition, 162, 76);
		GlStateManager.disableAlpha();
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
		return new ResourceLocation("modularmachines:textures/gui/modular_machine.png");
	}

	@SideOnly(Side.CLIENT)
	protected ResourceLocation getInventoryTexture() {
		return new ResourceLocation("modularmachines:textures/gui/inventory_player.png");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addButtons(List buttons) {
		List<IModuleState> modelsWithPages = getModulesWithPages(modular);

		for(int i = 0; i < modelsWithPages.size(); i++) {
			IModuleState module = modelsWithPages.get(i);
			buttons.add(new ButtonModuleTab(i, (i >= 7) ? gui.getGuiLeft() + getXSize() : gui.getGuiLeft() - 28,
					(i >= 7) ? gui.getGuiTop() + 8 + 22 * (i - 7) : gui.getGuiTop() + 8 + 22 * i, module, modular.getHandler(), i >= 7));
		}

		for(int pageIndex = 0; pageIndex < state.getPages().size(); pageIndex++) {
			IModulePage page = state.getPages().get(pageIndex);
			buttons.add(new ButtonModulePageTab(gui.getButtonManager().getButtons().size(),
					pageIndex > 4 ? 12 + gui.getGuiLeft() + (pageIndex - 5) * 30 : 12 + gui.getGuiLeft() + pageIndex * 30,
							pageIndex > 4 ? gui.getGuiTop() + getYSize() : gui.getGuiTop() - 19, pageIndex > 4 ? true : false, page, pageIndex));
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
	public String getPageID() {
		return pageID;
	}

	@Override
	public IModular getModular() {
		return modular;
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

	@Override
	public IModuleState getModuleState() {
		return state;
	}

	public static List<IModuleState> getModulesWithPages(IModular modular){
		List<IModuleState> modulesWithPages = Lists.newArrayList();
		for(IModuleState moduleState : modular.getModuleStates()) {
			if (moduleState != null && !moduleState.getPages().isEmpty()) {
				modulesWithPages.add(moduleState);
			}
		}
		return modulesWithPages;
	}

	@Override
	public String getPageTitle() {
		return Translator.translateToLocal("module.page." + title.toLowerCase(Locale.ENGLISH) + ".name");
	}

	@Override
	public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
	}
}
