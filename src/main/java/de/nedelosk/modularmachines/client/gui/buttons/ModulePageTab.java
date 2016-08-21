package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.client.gui.GuiPage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModulePage;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ModulePageTab extends Button<GuiPage<IModularHandler>> {

	protected static final ResourceLocation guiTexture = new ResourceLocation("modularmachines", "textures/gui/modular_machine.png");
	public IModulePage page;
	public boolean isDown;
	public int pageIndex;

	public ModulePageTab(int ID, int xPosition, int yPosition, boolean isDown, IModulePage page, int pageIndex) {
		super(ID, xPosition, yPosition, 28, 21, null);
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
		RenderUtil.bindTexture(guiTexture);
		getGui().getGui().drawTexturedModalRect(xPosition, yPosition, page.getModuleState().getModular().getCurrentPage().getPageID().equals(page.getPageID()) ? 74 : 103,
				isDown ? 237 : 218, 29, 19);
		RenderUtil.bindTexture(new ResourceLocation("modularmachines:textures/gui/widgets.png"));
		getGui().getGui().drawTexturedModalRect(xPosition + 6, yPosition, 0, 18 + pageIndex * 18, 18, 18);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	@Override
	public void onButtonClick() {
		IModularHandler tile = getGui().getHandler();
		if (!page.getPageID().equals(page.getModuleState().getModular().getCurrentPage().getPageID())) {
			tile.getModular().setCurrentPage(page.getPageID());
			PacketHandler.INSTANCE.sendToServer(new PacketSelectModulePage(tile, page.getPageID()));
		}
	}

	@Override
	public List<String> getTooltip() {
		return Arrays.asList(page.getPageTitle());
	}
}
