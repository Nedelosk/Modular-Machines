package de.nedelosk.modularmachines.common.modules.handlers;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.common.modules.IJEIPage;
import de.nedelosk.modularmachines.common.modules.IModuleJEI;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public abstract class NEIPage implements IJEIPage {

	public IGuiBase gui;
	public IRecipe recipe;
	public final IModuleJEI module;

	public NEIPage(IModuleJEI module) {
		this.module = module;
	}

	@Override
	public IGuiBase getGui() {
		return gui;
	}

	@Override
	public void setGui(IGuiBase gui) {
		this.gui = gui;
	}

	@Override
	public void updateGui(int mouseX, int mouseY) {
	}

	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
	}

	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
	}

	@Override
	public void drawBackground(int mouseX, int mouseY) {
	}

	@Override
	public void drawTooltips(int mouseX, int mouseY) {
	}

	@Override
	public void drawFrontBackground(int mouseX, int mouseY) {
	}

	@Override
	public void drawSlots() {
	}

	@Override
	public void drawPlayerInventory() {
	}

	@Override
	public int getXSize() {
		return 166;
	}

	@Override
	public int getYSize() {
		return 65;
	}

	@Override
	public int getPlayerInvPosition() {
		return 0;
	}

	@Override
	public ResourceLocation getGuiTexture() {
		return new ResourceLocation("forestmods:textures/gui/nei/nei_background");
	}

	@Override
	public void setRecipe(IRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public IRecipe getRecipe() {
		return recipe;
	}

	@Override
	public void addButtons(List buttons) {
	}

	@Override
	public void addWidgets(List widgets) {
	}
}
