package modularmachines.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import modularmachines.common.blocks.tile.TileModuleCrafter;

public class GuiModuleCrafter extends GuiBase<TileModuleCrafter> {

	protected static final ResourceLocation modularWdgets = new ResourceLocation("modularmachines", "textures/gui/modular_widgets.png");
	private static final int buttonSize = 20;
	private static final String nextLabel = ">";
	private static final String backLabel = "<";

	// private GuiButton nextButton;
	// private GuiButton backButton;
	public GuiModuleCrafter(TileModuleCrafter tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	public void initGui() {
		super.initGui();
		// buttonList.add(nextButton = new GuiButtonExt(0, guiLeft - 29, guiTop
		// + 135, buttonSize, buttonSize, nextLabel));
		// buttonList.add(backButton = new GuiButtonExt(1, guiLeft - 125, guiTop
		// + 135, buttonSize, buttonSize, backLabel));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 28, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
		// GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// RenderUtil.bindTexture(modularWdgets);
		// drawTexturedModalRect(this.guiLeft - 130, this.guiTop + 2, 0, 0, 126,
		// 158);
	}

	@Override
	protected String getGuiTexture() {
		return "module_crafter";
	}
}
