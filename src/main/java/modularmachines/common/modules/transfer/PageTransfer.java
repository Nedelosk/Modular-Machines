package modularmachines.common.modules.transfer;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.pages.IModuleComponent;
import modularmachines.client.gui.widgets.Widget;
import modularmachines.client.gui.widgets.WidgetTransfer;
import modularmachines.client.gui.widgets.WidgetTransferButton;
import modularmachines.common.modules.pages.PageWidget;
import modularmachines.common.utils.RenderUtil;

@SideOnly(Side.CLIENT)
public abstract class PageTransfer<M extends ModuleTransfer<H>, H> extends PageWidget<M> {
	protected static final ResourceLocation WIDGETS = new ResourceLocation("modularmachines", "textures/gui/transfer_widgets.png");
	
	@Nullable
	public WidgetTransfer selectedWidget = null;
	
	public PageTransfer(IModuleComponent<M> component, GuiContainer gui) {
		super(component, gui);
	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY) {
		super.drawBackground(mouseX, mouseY);
		RenderUtil.texture(WIDGETS);
		gui.drawTexturedModalRect(gui.getGuiLeft() + 176, gui.getGuiTop() + 8, 0, 0, 110, 228);
	}
	
	@Override
	protected ResourceLocation getGuiTexture() {
		return new ResourceLocation("modularmachines:textures/gui/module_transfer.png");
	}
	
	@Override
	public int getYSize() {
		return 244;
	}
	
	@Override
	public boolean mouseReleased(int mouseX, int mouseY, int state) {
		int i = gui.getGuiLeft() + gui.getXSize();
		int j = gui.getGuiTop();
		return !(mouseX < i || mouseY < j || mouseX >= i + 110 || mouseY >= j + gui.getYSize());
	}
	
	@Override
	public void addWidgets() {
		super.addWidgets();
		int index = ((ModuleComponentTransfer) component).index;
		addWidget(new WidgetTransferButton(151, 62, true, this));
		addWidget(new WidgetTransferButton(151, 86, false, this));
		for (int i = 0; i < 6; i++) {
			List<ITransferCycle<H>> cycles = module.getTransferCycles();
			ITransferCycle<H> cycle = null;
			if (index + i < cycles.size()) {
				cycle = cycles.get(index + i);
			}
			if (cycle != null) {
				addWidget(new WidgetTransfer(8, 15 + i * 23, cycle));
			}
		}
		module.initWrappers();
	}
	
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		int posX = mouseX - gui.getGuiLeft();
		int posY = mouseY - gui.getGuiTop();
		Widget widget = widgetManager.getWidgetAtMouse(posX, posY);
		if (widget != null && widget instanceof WidgetTransfer) {
			if (selectedWidget != widget && selectedWidget != null) {
				selectedWidget.setSelected(false);
			}
			selectedWidget = (WidgetTransfer) widget;
			selectedWidget.setSelected(!selectedWidget.selected);
		}
	}
	
	public abstract void addCycle();
}
