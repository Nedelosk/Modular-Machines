package de.nedelosk.modularmachines.api.gui;

import java.awt.Rectangle;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public abstract class Widget<I extends IGuiHandler> {

	protected ResourceLocation widgetTexture;
	protected Rectangle pos;
	public Minecraft mc = Minecraft.getMinecraft();
	public boolean showTooltip;

	public Widget(int posX, int posY, int width, int height) {
		widgetTexture = new ResourceLocation("modularmachines", "textures/gui/widgets.png");
		pos = new Rectangle(posX, posY, width, height);
		showTooltip = true;
	}

	public void draw(IGuiProvider<I> gui) {
	}

	public boolean keyTyped(char keyChar, int keyCode, IGuiProvider<I> gui) {
		return false;
	}

	public void drawStrings(IGuiProvider<I> gui) {
	}

	public ArrayList<String> getTooltip(IGuiProvider<I> gui) {
		return null;
	}

	public boolean isMouseOver(int x, int y) {
		return x >= pos.x && y >= pos.y && x < pos.x + pos.width && y < pos.y + pos.height;
	}

	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiProvider<I> gui) {
	}

	public Rectangle getPos() {
		return pos;
	}

	@Override
	public boolean equals(Object obj) {
		Widget w = (Widget) obj;
		if (w.pos.equals(pos)) {
			return true;
		}
		return false;
	}


	public Widget<I> setShowTooltip(boolean showTooltip) {
		this.showTooltip = showTooltip;
		return this;
	}
}