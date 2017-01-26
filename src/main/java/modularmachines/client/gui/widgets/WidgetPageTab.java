package modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import modularmachines.api.modules.IModuleGuiLogic;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.pages.ModulePage;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.utils.ModuleUtil;
import modularmachines.common.utils.RenderUtil;

public class WidgetPageTab extends Widget<IModuleLogic> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public final ModulePage page;
	public final boolean isDown;
	public final IModuleGuiLogic guiLogic;
	public final int currentIndex;
	public final int pageIndex;

	public WidgetPageTab(int xPosition, int yPosition, ModulePage page) {
		super(xPosition, yPosition, 28, 21);
		this.page = page;
		this.pageIndex = page.getIndex();
		this.isDown = page.getIndex() > 4;
		this.guiLogic = ModuleUtil.getClientGuiLogic();
		this.currentIndex = guiLogic.getCurrentModule().getIndex();
	}

	@Override
	public void draw(int guiLeft, int guiTop) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.texture(guiTexture);
		gui.drawTexturedModalRect(guiLeft + pos.x, guiTop + pos.y, pageIndex == currentIndex ? 74 : 103, isDown ? 237 : 218, 29, 19);
		RenderUtil.texture(new ResourceLocation("modularmachines:textures/gui/widgets.png"));
		gui.drawTexturedModalRect(guiLeft + pos.x + 6, guiTop + pos.y, 0, 18 + pageIndex * 18, 18, 18);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		if (pageIndex != currentIndex) {
			ModularMachines.proxy.playButtonClick();
			guiLogic.setCurrentPage(page, true);
		}
	}

	@Override
	public List getTooltip() {
		return Arrays.asList(page.getPageTitle());
	}
}
