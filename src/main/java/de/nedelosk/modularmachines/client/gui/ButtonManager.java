package de.nedelosk.modularmachines.client.gui;

import java.util.ArrayList;
import java.util.Collection;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.IButtonManager;
import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;

public class ButtonManager<G extends IGuiBase> implements IButtonManager<G> {

	public final G gui;
	public final Minecraft minecraft;
	protected final ArrayList<Button> buttons = new ArrayList<Button>();

	public ButtonManager(G gui) {
		this.gui = gui;
		this.minecraft = Minecraft.getMinecraft();
	}

	@Override
	public void add(Button slot) {
		this.buttons.add(slot);
		slot.gui = gui;
	}

	@Override
	public void add(Collection<Button> slots) {
		for(Button button : slots){
			add(button);
		}
	}

	@Override
	public void remove(Button slot) {
		this.buttons.remove(slot);
	}

	public void clear() {
		this.buttons.clear();
	}

	@Override
	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public void drawTooltip(int mX, int mY) {
		for(Button slot : buttons) {
			if (slot.isMouseOver(mX, mY)) {
				RenderUtil.renderTooltip(mX, mY, slot.getTooltip(gui));
			}
		}
	}

	protected Button getAtPosition(int mX, int mY) {
		for(Button slot : buttons) {
			if (slot.isMouseOver(mX, mY)) {
				return slot;
			}
		}
		return null;
	}

	public void drawWidgets() {
		gui.setZLevel(100.0F);
		gui.getRenderItem().zLevel = 100.0F;
		for(Button slot : buttons) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			slot.drawButton(minecraft, 0, 0);
		}
		gui.setZLevel(0.0F);
		gui.getRenderItem().zLevel = 0.0F;
	}

	@Override
	public G getGui() {
		return null;
	}
}
