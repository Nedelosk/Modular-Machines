package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.client.gui.GuiModular;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonModulePageTab extends Button<IModularHandler> {

	protected ResourceLocation guiTextureOverlay = RenderUtil.getResourceLocation("modularmachines", "modular_machine", "gui");
	public IModulePage page;
	public boolean isDown;
	public int pageIndex;

	public ButtonModulePageTab(int buttonID, int xPos, int yPos, boolean isDown, IModulePage page, int pageIndex) {
		super(buttonID, xPos, yPos, 28, 21, null);
		this.isDown = isDown;
		this.page = page;
		this.pageIndex = pageIndex;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GuiModular machine = (GuiModular) mc.currentScreen;
		RenderUtil.bindTexture(guiTextureOverlay);
		machine.drawTexturedModalRect(xPosition, yPosition, page.getModuleState().getModular().getCurrentPage().getPageID().equals(page.getPageID()) ? 74 : 103,
				isDown ? 237 : 218, 29, 19);
		RenderUtil.bindTexture(RenderUtil.getResourceLocation("modularmachines", "widgets", "gui"));
		machine.drawTexturedModalRect(xPosition + 6, yPosition, 0, 18 + pageIndex * 18, 18, 18);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	@Override
	public void onButtonClick(IGuiBase<IModularHandler> gui) {
		IModularHandler tile = gui.getHandler();
		if (!page.getPageID().equals(page.getModuleState().getModular().getCurrentPage().getPageID())) {
			tile.getModular().setCurrentPage(page.getPageID());
		}
	}

	@Override
	public List<String> getTooltip(IGuiBase<IModularHandler> gui) {
		return Arrays.asList(page.getPageTitle());
	}
}
