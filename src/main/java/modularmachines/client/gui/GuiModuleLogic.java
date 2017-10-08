package modularmachines.client.gui;

import java.io.IOException;

import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.pages.IPage;
import modularmachines.common.containers.ContainerModuleLogic;

public class GuiModuleLogic extends GuiBase<IModuleLogic, IModuleLogic> {

	protected final IPage page;

	public GuiModuleLogic(IModuleLogic logic, InventoryPlayer inventory) {
		super(logic, logic, inventory);
		this.page = ((ContainerModuleLogic)inventorySlots).guiLogic.getCurrentComponent().createPage(this);
		page.addWidgets();
		ySize = page.getYSize();
		xSize = page.getXSize();
	}

	@Override
	public void initGui() {
		super.initGui();
		page.initGui();
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if(page.mouseReleased(mouseX, mouseY, state)){
			return;
		}
		super.mouseReleased(mouseX, mouseY, state);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		page.drawForeground(getFontRenderer(), mouseX, mouseY);
	}

	@Override
	protected String getGuiTexture() {
		return null;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		page.drawBackground(mouseX, mouseY);
		widgetManager.setMouseX(mouseX);
		widgetManager.setMouseY(mouseY);
		widgetManager.drawWidgets(guiLeft, guiTop);
		page.drawFrontBackground(mouseX, mouseY);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		page.updateGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float p_73863_3_) {
		super.drawScreen(mouseX, mouseY, p_73863_3_);
		page.drawTooltips(mouseX, mouseY);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		page.handleMouseClicked(mouseX, mouseY, mouseButton);
	}
	
	public IPage getPage() {
		return page;
	}
}