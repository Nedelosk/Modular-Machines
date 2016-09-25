package de.nedelosk.modularmachines.api.gui;

import java.awt.Rectangle;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class Widget<P> {

	protected ResourceLocation widgetTexture;
	protected Rectangle pos;
	protected final Minecraft mc = Minecraft.getMinecraft();
	protected P provider;

	public Widget(int posX, int posY, int width, int height) {
		this(posX, posY, width, height, null);
	}

	public Widget(int posX, int posY, int width, int height, P provider) {
		this.widgetTexture = new ResourceLocation("modularmachines", "textures/gui/widgets.png");
		this.pos = new Rectangle(posX, posY, width, height);
		this.provider = provider;
	}

	public void draw(IGuiProvider gui) {
	}

	public boolean keyTyped(char keyChar, int keyCode, IGuiProvider gui) {
		return false;
	}

	public void drawStrings(IGuiProvider gui) {
	}

	public List<String> getTooltip(IGuiProvider gui) {
		return null;
	}

	public boolean isMouseOver(int x, int y) {
		return x >= pos.x && y >= pos.y && x < pos.x + pos.width && y < pos.y + pos.height;
	}

	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiProvider gui) {
	}

	public Rectangle getPos() {
		return pos;
	}

	public void setProvider(P provider) {
		this.provider = provider;
	}

	public P getProvider() {
		return provider;
	}

	@Override
	public boolean equals(Object obj) {
		Widget w = (Widget) obj;
		if (w.pos.equals(pos)) {
			return true;
		}
		return false;
	}
}