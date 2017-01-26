package modularmachines.client.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import modularmachines.api.IGuiProvider;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.pages.ModulePage;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiModuleLogic<P extends IGuiProvider> extends GuiBase<P, IModuleLogic> {

	protected final ModulePage page;

	public GuiModuleLogic(P provider, IModuleLogic logic, InventoryPlayer inventory) {
		super(provider, logic, inventory);
		this.page = logic.getGuiLogic().getCurrentPage();
		page.setGui(this);
		page.addWidgets();
		ySize = page.getYSize();
		xSize = page.getXSize();
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		page.setGui(null);
	}

	@Override
	public void initGui() {
		page.setGui(this);
		super.initGui();
		page.initGui();
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
		widgetManager.drawWidgets();
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
	
	public ModulePage getPage() {
		return page;
	}
}