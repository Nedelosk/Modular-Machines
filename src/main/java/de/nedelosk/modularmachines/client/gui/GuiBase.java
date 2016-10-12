package de.nedelosk.modularmachines.client.gui;

import java.io.IOException;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.gui.IButtonManager;
import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.gui.IWidgetManager;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class GuiBase<H extends IGuiProvider> extends GuiContainer implements IGuiBase<H> {

	protected final ResourceLocation guiTexture;
	protected final H handler;
	protected final ButtonManager buttonManager;
	protected final WidgetManager widgetManager;
	protected final EntityPlayer player;

	public GuiBase(H tile, InventoryPlayer inventory) {
		super(tile.createContainer(inventory));
		this.handler = tile;
		this.player = inventory.player;
		widgetManager = new WidgetManager(this);
		buttonManager = new ButtonManager(this);

		if(getTextureModID() != null && getGuiTexture() != null){
			guiTexture = new ResourceLocation(getTextureModID(), "textures/gui/" + getGuiTexture() + ".png");
		}else{
			guiTexture = null;
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		initButtons();
	}

	protected void initButtons(){
		Iterator<GuiButton> buttonIter = buttonList.iterator();
		while(buttonIter.hasNext()){
			GuiButton button = buttonIter.next();
			if(button instanceof Button){
				buttonIter.remove();
			}
		}
		buttonManager.clear();
		addButtons();
		buttonList.addAll(buttonManager.getButtons());
	}

	protected void addButtons() {
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
	public H getHandler() {
		return handler;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
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
	protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException {
		if (!widgetManager.keyTyped(p_73869_1_, p_73869_2_)) {
			super.keyTyped(p_73869_1_, p_73869_2_);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button instanceof Button) {
			((Button) button).onButtonClick();
		}
	}

	protected void renderStrings(FontRenderer fontRenderer, int x, int y) {
	}

	protected void render() {
	}

	protected String getTextureModID() {
		return "modularmachines";
	}

	protected abstract String getGuiTexture();

	@Override
	public RenderItem getRenderItem() {
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

	@Override
	public void drawItemStack(ItemStack stack, int x, int y) {
		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableColorMaterial();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		this.itemRender.zLevel = 100.0F;
		this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		this.itemRender.zLevel = 0.0F;
		GlStateManager.disableLighting();

		GlStateManager.popMatrix();
		GlStateManager.disableDepth();
		GlStateManager.enableLighting();
		RenderHelper.disableStandardItemLighting();
	}
}
