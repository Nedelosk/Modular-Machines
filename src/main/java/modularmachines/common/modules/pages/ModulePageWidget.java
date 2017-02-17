package modularmachines.common.modules.pages;

import java.util.List;

import javax.annotation.Nullable;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.client.gui.GuiModuleLogic;
import modularmachines.client.gui.WidgetManager;
import modularmachines.client.gui.widgets.Widget;
import modularmachines.client.gui.widgets.WidgetAssembleTab;
import modularmachines.client.gui.widgets.WidgetModuleTab;
import modularmachines.client.gui.widgets.WidgetPageTab;
import modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModulePageWidget<P extends Module> extends ModulePage<P> {

	protected static final ResourceLocation modularWdgets = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");

	@SideOnly(Side.CLIENT)
	@Nullable
	public WidgetManager widgetManager;
	
	public ModulePageWidget(P parent) {
		super(parent);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setGui(GuiContainer gui){
		super.setGui(gui);
		if(gui instanceof GuiModuleLogic){
			widgetManager = ((GuiModuleLogic) gui).getWidgetManager();
		}else{
			widgetManager = null;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(){
		List<Module> modulesWithPages = ModuleHelper.getPageModules(parent.getLogic());
		int i = 0;
		if (!modulesWithPages.isEmpty() && modulesWithPages.size() > 1) {
			for (i = 0; i < modulesWithPages.size(); i++) {
				Module module = modulesWithPages.get(i);
				boolean isRight = i >= 7;
				addWidget(new WidgetModuleTab(isRight ? getXSize() : -28, 8 + 22 * (isRight ? i - 7 : i), module, isRight));
			}
		}
		boolean isRight = i >= 7;
		Widget widget = new WidgetAssembleTab(isRight ? getXSize() : -28, 8 + 22 * (isRight ? i - 7 : i), isRight);
		addWidget(widget);
		List<ModulePage> pages = parent.getPages();
		if (!pages.isEmpty() && pages.size() > 1) {
			for (int pageIndex = 0; pageIndex < pages.size(); pageIndex++) {
				ModulePage page = pages.get(pageIndex);
				addWidget(new WidgetPageTab(pageIndex > 4 ? 12 + (pageIndex - 5) * 30 : 12 + pageIndex * 30, pageIndex > 4 ? getYSize() : -19, page));
			}
		}
	}
	
	public void addWidget(Widget widget){
		if(gui instanceof GuiModuleLogic){
			widgetManager.add(widget);
		}
	}
	
	@SideOnly(Side.CLIENT)
	protected ResourceLocation getGuiTexture(){
		return new ResourceLocation("modularmachines:textures/gui/modular_machine.png");
	}
	
	@SideOnly(Side.CLIENT)
	protected ResourceLocation getInventoryTexture() {
		return new ResourceLocation("modularmachines:textures/gui/inventory_player.png");
	}
	
	@SideOnly(Side.CLIENT)
	protected boolean renderPageTitle() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
		if (renderPageTitle() && getPageTitle() != null) {
			fontRenderer.drawString(getPageTitle(), gui.xSize / 2 - (fontRenderer.getStringWidth(getPageTitle()) / 2), 6, 4210752);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void drawBackground(int mouseX, int mouseY) {
		if (gui != null && getGuiTexture() != null) {
			Minecraft.getMinecraft().renderEngine.bindTexture(getGuiTexture());
			gui.drawTexturedModalRect(gui.getGuiLeft(), gui.getGuiTop(), 0, 0, getXSize(), getYSize());
		}
		drawPlayerInventory();
		drawSlots();
	}

	@SideOnly(Side.CLIENT)
	protected void drawSlots() {
		if (gui != null) {
			List<Slot> slots = container.inventorySlots;
			for (int slotID = 36; slotID < slots.size(); slotID++) {
				Slot slot = slots.get(slotID);
				drawSlot(slot);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	protected void drawSlot(Slot slot) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.texture(modularWdgets);
		gui.drawTexturedModalRect(gui.getGuiLeft() + slot.xPos - 1, gui.getGuiTop() + slot.yPos - 1, 56, 238, 18, 18);
	}

	@SideOnly(Side.CLIENT)
	protected void drawPlayerInventory() {
		if (gui != null && getInventoryTexture() != null && getPlayerInvPosition() >= 0) {
			GlStateManager.enableAlpha();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(getInventoryTexture());
			int invPosition = getPlayerInvPosition();
			gui.drawTexturedModalRect(gui.getGuiLeft() + 7, gui.getGuiTop() + invPosition, 0, 0, 162, 76);
			GlStateManager.disableAlpha();
		}
	}

}
