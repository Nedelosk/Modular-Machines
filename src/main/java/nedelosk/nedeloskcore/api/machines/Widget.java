package nedelosk.nedeloskcore.api.machines;

import java.util.ArrayList;

import nedelosk.nedeloskcore.client.gui.GuiBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class Widget extends Gui {

	protected ResourceLocation widget;
	public int posX, posY;
	public int width, height;
	public Minecraft mc = Minecraft.getMinecraft();

	public Widget(int posX, int posY, int width, int height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
	
	public void draw(GuiBase gui) {
	}

	public ArrayList<String> getTooltip() {
		return null;
	}

	public boolean isMouseOver(int x, int y) {
		return x >= posX && y >= posY && x < posX + width && y < posY + height;
	}

	public void handleMouseClick(int mouseX, int mouseY, int mouseButton, GuiBase gui) {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		Widget w = (Widget) obj;
		if(w.posX == posX && w.posY == posY)
			return true;
		return false;
	}
	
}