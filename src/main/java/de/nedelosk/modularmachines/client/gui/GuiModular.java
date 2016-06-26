package de.nedelosk.modularmachines.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiModular extends GuiForestBase<IModularHandler> {

	protected IModulePage currentPage;
	protected IModuleState state;

	public GuiModular(IModularHandler tile, InventoryPlayer inventory, IModulePage currentPage) {
		super(tile, inventory);
		this.currentPage = currentPage;
		this.state = currentPage.getModuleState();
		currentPage.setGui(this);
		List<Widget> widgets = new ArrayList();
		currentPage.addWidgets(widgets);
		widgetManager.addAll(widgets);
		ySize = currentPage.getYSize();
		xSize = currentPage.getXSize();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		currentPage.drawForeground(getFontRenderer(), mouseX, mouseY);
	}

	@Override
	public void addButtons() {
		List<Button> buttons = new ArrayList();
		currentPage.addButtons(buttons);
		buttonManager.add(buttons);
	}

	@Override
	protected String getGuiTexture() {
		return "modular_machine";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		currentPage.drawBackground(mouseX, mouseY);
		currentPage.drawSlots();
		currentPage.updateGui(mouseX, mouseY);
		widgetManager.drawWidgets();
		currentPage.drawPlayerInventory();
		currentPage.drawFrontBackground(mouseX, mouseY);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float p_73863_3_) {
		super.drawScreen(mouseX, mouseY, p_73863_3_);
		currentPage.drawTooltips(mouseX, mouseY);
	}

	@Override
	public Gui getGui() {
		return this;
	}
}