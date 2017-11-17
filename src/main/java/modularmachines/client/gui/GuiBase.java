package modularmachines.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import modularmachines.api.IGuiProvider;
import modularmachines.api.ILocatableSource;
import modularmachines.client.gui.widgets.Widget;
import modularmachines.common.utils.RenderUtil;

public abstract class GuiBase<P extends IGuiProvider, S extends ILocatableSource> extends GuiContainer {

	protected final ResourceLocation guiTexture;
	protected final S source;
	protected final WidgetManager widgetManager;
	protected final EntityPlayer player;

	public GuiBase(P porvider, S source, InventoryPlayer inventory) {
		super(porvider.createContainer(inventory));
		this.source = source;
		this.player = inventory.player;
		widgetManager = new WidgetManager(this);
		if (getTextureModID() != null && getGuiTexture() != null) {
			guiTexture = new ResourceLocation(getTextureModID(), "textures/gui/" + getGuiTexture() + ".png");
		} else {
			guiTexture = null;
		}
	}

	public WidgetManager getWidgetManager() {
		return widgetManager;
	}
	
	public S getSource(){
		return source;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		widgetManager.handleMouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		renderStrings(fontRenderer, param1, param2);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		widgetManager.drawTooltip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.texture(guiTexture);
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		render();
		widgetManager.drawWidgets(guiLeft, guiTop);
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException {
		if (!widgetManager.keyTyped(p_73869_1_, p_73869_2_)) {
			super.keyTyped(p_73869_1_, p_73869_2_);
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

	public RenderItem getRenderItem() {
		return itemRender;
	}

	public void setZLevel(float zLevel) {
		this.zLevel = zLevel;
	}

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

	public EntityPlayer getPlayer() {
		return player;
	}

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}

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

	public void onTextFieldChanged(Widget widget, String oldText) {
	}
}
