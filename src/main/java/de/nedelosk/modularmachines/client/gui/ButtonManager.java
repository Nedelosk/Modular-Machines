package de.nedelosk.modularmachines.client.gui;

import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.Button;
import de.nedelosk.modularmachines.api.gui.IButtonManager;
import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;

public class ButtonManager<G extends IGuiProvider> implements IButtonManager<G> {

	public final G gui;
	public final Minecraft minecraft;
	protected final ArrayList<Button> buttons = new ArrayList<>();

	public ButtonManager(G gui) {
		this.gui = gui;
		this.minecraft = Minecraft.getMinecraft();
	}

	@Override
	public void add(Button button) {
		this.buttons.add(button);
		button.setGui(gui);
	}

	@Override
	public void add(Collection<Button> buttons) {
		for(Button button : buttons){
			add(button);
		}
	}

	@Override
	public void remove(Button button) {
		this.buttons.remove(button);
	}

	public void clear() {
		this.buttons.clear();
	}

	@Override
	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public void drawTooltip(int mX, int mY) {
		for(Button button : buttons) {
			if (button.isMouseOver(mX, mY) && button.enabled) {
				RenderUtil.renderTooltip(mX, mY, button.getTooltip());
			}
		}
	}

	protected Button getAtPosition(int mX, int mY) {
		for(Button button : buttons) {
			if (button.isMouseOver(mX, mY) && button.enabled) {
				return button;
			}
		}
		return null;
	}

	public void drawWidgets() {
		gui.setZLevel(100.0F);
		gui.getRenderItem().zLevel = 100.0F;
		for(Button button : buttons) {
			if(button.enabled){
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				button.drawButton(minecraft, 0, 0);
			}
		}
		gui.setZLevel(0.0F);
		gui.getRenderItem().zLevel = 0.0F;
	}

	@Override
	public G getGui() {
		return null;
	}
}
