package nedelosk.forestcore.library.gui;

import java.awt.Rectangle;
import java.util.ArrayList;

import nedelosk.forestcore.library.inventory.IGuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Widget<I extends IGuiHandler> {

	protected ResourceLocation widget;
	protected Rectangle pos;
	public Minecraft mc = Minecraft.getMinecraft();

	public Widget(int posX, int posY, int width, int height) {
		pos = new Rectangle(posX, posY, width, height);
	}

	public void draw(IGuiBase<I> gui) {
	}

	public void drawStrings(IGuiBase<I> gui) {
	}

	public ArrayList<String> getTooltip(IGuiBase<I> gui) {
		return null;
	}

	public boolean isMouseOver(int x, int y) {
		return x >= pos.x && y >= pos.y && x < pos.x + pos.width && y < pos.y + pos.height;
	}

	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, IGuiBase<I> gui) {
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
}