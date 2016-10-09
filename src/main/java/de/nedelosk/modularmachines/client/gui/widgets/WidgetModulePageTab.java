package de.nedelosk.modularmachines.client.gui.widgets;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModulePage;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class WidgetModulePageTab extends Widget<IModulePage> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	public boolean isDown;
	public int pageIndex;

	public WidgetModulePageTab(int xPosition, int yPosition, IModulePage provider) {
		super(xPosition, yPosition, 28, 21, provider);
		this.pageIndex = provider.getModuleState().getPages().indexOf(provider);
		this.isDown = pageIndex > 4;
	}

	@Override
	public void draw(IGuiProvider gui) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 1F);
		RenderUtil.bindTexture(guiTexture);
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x, gui.getGuiTop() + pos.y, provider.getModuleState().getModular().getCurrentPage().getPageID().equals(provider.getPageID()) ? 74 : 103,
				isDown ? 237 : 218, 29, 19);
		RenderUtil.bindTexture(new ResourceLocation("modularmachines:textures/gui/widgets.png"));
		gui.getGui().drawTexturedModalRect(gui.getGuiLeft() + pos.x + 6, gui.getGuiTop() + pos.y, 0, 18 + pageIndex * 18, 18, 18);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	@Override
	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiProvider gui) {
		IModularHandler modularHandler = (IModularHandler) gui.getHandler();
		if (!provider.getPageID().equals(provider.getModuleState().getModular().getCurrentPage().getPageID())) {
			modularHandler.getModular().setCurrentPage(provider.getPageID());
			PacketHandler.sendToServer(new PacketSelectModulePage(modularHandler, provider.getPageID()));
		}
	}

	@Override
	public List getTooltip(IGuiProvider gui) {
		return Arrays.asList(provider.getPageTitle());
	}
}
