package de.nedelosk.modularmachines.client.gui.buttons;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.client.gui.Button;
import de.nedelosk.modularmachines.client.gui.GuiModular;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;
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
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GuiModular machine = (GuiModular) mc.currentScreen;
		RenderUtil.bindTexture(guiTextureOverlay);
		machine.drawTexturedModalRect(xPosition, yPosition, page.getModuleState().getModular().getCurrentPage().getPageID().equals(page.getPageID()) ? 74 : 103,
				isDown ? 237 : 218, 29, 19);
		RenderUtil.bindTexture(RenderUtil.getResourceLocation("modularmachines", "widgets", "gui"));
		machine.drawTexturedModalRect(xPosition + 6, yPosition, 0, 18 + pageIndex * 18, 18, 18);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
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
		return Arrays.asList(page.getPageID());
	}
}
