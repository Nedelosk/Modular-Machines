package de.nedelosk.forestmods.library.gui;

import org.lwjgl.opengl.GL11;

import de.nedelosk.forestmods.library.inventory.IGuiHandler;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class GuiBase<T extends IGuiHandler> extends GuiContainer implements IGuiBase<T> {

	protected ResourceLocation guiTexture;
	protected T tile;
	protected ButtonManager buttonManager;
	protected WidgetManager widgetManager;
	protected EntityPlayer player;

	public GuiBase(T tile, InventoryPlayer inventory) {
		super(tile.getContainer(inventory));
		this.tile = tile;
		this.player = inventory.player;
		widgetManager = new WidgetManager(this);
		buttonManager = new ButtonManager(this);
		guiTexture = RenderUtil.getResourceLocation(getTextureModID(), getGuiTexture(), "gui");
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonManager.clear();
		addButtons();
		buttonList.addAll(buttonManager.getButtons());
	}

	public void addButtons() {
	}

	@Override
	public IButtonManager getButtonManager() {
		return buttonManager;
	}

	@Override
	public IWidgetManager getWidgetManager() {
		return widgetManager;
	}

	@Override
	public T getTile() {
		return tile;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		widgetManager.handleMouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		renderStrings(fontRendererObj, param1, param2);
	}

	@Override
	public void drawScreen(int param1, int param2, float p_73863_3_) {
		super.drawScreen(param1, param2, p_73863_3_);
		widgetManager.drawTooltip(param1, param2);
		buttonManager.drawTooltip(param1, param2);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture(guiTexture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		render();
		widgetManager.drawWidgets();
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		if (!widgetManager.keyTyped(p_73869_1_, p_73869_2_)) {
			super.keyTyped(p_73869_1_, p_73869_2_);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button instanceof Button) {
			((Button) button).onButtonClick(this);
		}
	}

	protected abstract void renderStrings(FontRenderer fontRenderer, int x, int y);

	protected abstract void render();

	protected abstract String getGuiTexture();

	protected abstract String getTextureModID();

	public static RenderItem getItemRenderer() {
		return itemRender;
	}

	@Override
	public void setZLevel(float zLevel) {
		this.zLevel = zLevel;
	}

	@Override
	public float getZLevel() {
		return zLevel;
	}

	@Override
	public int getGuiLeft() {
		return this.guiLeft;
	}

	@Override
	public int getGuiTop() {
		return this.guiTop;
	}

	@Override
	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}

	@Override
	public Gui getGui() {
		return this;
	}
}
