package de.nedelosk.techtree.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.ItemRenderer;

public abstract class GuiEditorBase {

	public GuiTechTreeEditor parent;
	public int left;
	public int top;
	public List<GuiButton> buttons;
	public ItemRenderer itemRenderer;
	public Minecraft mc = Minecraft.getMinecraft();

	public GuiEditorBase(GuiTechTreeEditor parent, int left, int top) {
		this.parent = parent;
		this.left = left;
		this.top = top;
	}

	public abstract void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_);

	public void initGui() {
		buttons = new ArrayList<GuiButton>();
	}

	public void addButtons() {
		parent.getButtonList().addAll(buttons);
	}

	public void addButton(GuiButton button) {
		buttons.add(button);
	}

	public GuiTechTreeEditor getParent() {
		return parent;
	}

	public void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
	}

	public void keyTyped(char key, int p_73869_2_) {
	}

	protected void actionPerformed(GuiButton button) {
	}

	public abstract void onGuiClose();
}
