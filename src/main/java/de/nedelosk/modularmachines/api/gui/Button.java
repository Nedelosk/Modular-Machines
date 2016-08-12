package de.nedelosk.modularmachines.api.gui;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderItem;

public class Button<G extends IGuiProvider> extends GuiButton {

	protected RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
	private G gui;

	public Button(int ID, int xPosition, int yPosition, String displayString) {
		super(ID, xPosition, yPosition, displayString);
	}
	
	public Button(int ID, int xPosition, int yPosition, int width, int height, String displayString) {
		super(ID, xPosition, yPosition, width, height, displayString);
	}

	public boolean isMouseOver(int x, int y) {
		return x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height;
	}

	public List<String> getTooltip() {
		return Collections.emptyList();
	}

	public void onButtonClick() {
	}

	public void setGui(G gui) {
		this.gui = gui;
	}

	public G getGui() {
		return gui;
	}
}
