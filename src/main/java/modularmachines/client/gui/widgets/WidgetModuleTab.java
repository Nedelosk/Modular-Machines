package modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import modularmachines.api.IGuiProvider;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.client.gui.WidgetManager;
import modularmachines.common.containers.ContainerModuleLogic;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.utils.RenderUtil;

public class WidgetModuleTab extends Widget<IModuleLogic> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public final Module module;
	public final boolean onRightSide;
	public final int moduleIndex;
	public IModuleGuiLogic guiLogic;
	public int currentIndex;

	public WidgetModuleTab(int xPosition, int yPosition, Module module, boolean onRightSide) {
		super(xPosition, yPosition, 28, 21);
		this.onRightSide = onRightSide;
		this.module = module;
		this.moduleIndex = module.getIndex();
	}
	
	@Override
	public void setManager(WidgetManager<IGuiProvider, IModuleLogic> manager) {
		super.setManager(manager);
		ContainerModuleLogic logic = (ContainerModuleLogic) gui.inventorySlots;
		this.guiLogic = logic.getGuiLogic();
		this.currentIndex = guiLogic.getCurrentModule().getIndex();
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.texture(guiTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, (moduleIndex == currentIndex) ? 0 : 28, onRightSide ? 214 : 235, 28, 21);
		gui.drawItemStack(module.getParentItem(), guiLeft + pos.x + (onRightSide ? 5 : 7), guiTop + pos.y + 2);
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		if (moduleIndex != currentIndex) {
			ModularMachines.proxy.playButtonClick();
			guiLogic.setCurrentPage(module.getComponent(0), true);
		}
	}

	@Override
	public List<String> getTooltip() {
		return Arrays.asList(module.getData().getDisplayName());
	}
}
