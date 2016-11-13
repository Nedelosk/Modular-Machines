package modularmachines.client.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

import modularmachines.api.gui.IGuiBase;
import modularmachines.api.gui.IWidgetManager;
import modularmachines.api.gui.Widget;
import modularmachines.common.utils.RenderUtil;

public class WidgetManager<G extends IGuiBase> implements IWidgetManager<G> {

	public final G gui;
	public final Minecraft minecraft;
	protected final List<Widget> widgets = new ArrayList<>();

	public WidgetManager(G gui) {
		this.gui = gui;
		this.minecraft = Minecraft.getMinecraft();
	}

	@Override
	public void add(Widget widget) {
		if (!widgets.contains(widget)) {
			this.widgets.add(widget);
		}
	}

	@Override
	public void addAll(Collection<Widget> widgets) {
		if (widgets == null) {
			return;
		}
		for (Widget widget : widgets) {
			if (!widgets.contains(widget)) {
				widgets.add(widget);
			}
		}
	}

	@Override
	public void remove(Widget widget) {
		this.widgets.remove(widget);
	}

	public void clear() {
		this.widgets.clear();
	}

	@Override
	public Widget getWidgetAtMouse(int mouseX, int mouseY) {
		for (Widget widget : widgets) {
			if (widget.isMouseOver(mouseX, mouseY)) {
				return widget;
			}
		}
		return null;
	}

	public void drawWidgets() {
		for (Widget slot : widgets) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			slot.draw(gui);
		}
		for (Widget slot : widgets) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			slot.drawStrings(gui);
		}
	}

	public boolean keyTyped(char keyChar, int keyCode) {
		/*
		 * for(Widget widget : widgets) { if (widget.keyTyped(keyChar, keyCode,
		 * gui)) { return true; } } return false;
		 */
		Widget focused = null;
		for (Widget widget : widgets) {
			if (widget.isFocused()) {
				focused = widget;
			}
		}
		// If esc is pressed
		if (keyCode == 1) {
			// If there is a focused text field unfocus it
			if (focused != null && keyCode == 1) {
				focused.setFocused(false);
				focused = null;
				return true;
			}
		}
		// If the user pressed tab, switch to the next text field, or unfocus if
		// there are none
		if (keyChar == '\t') {
			for (int i = 0; i < widgets.size(); i++) {
				Widget widget = widgets.get(i);
				if (widget.isFocused()) {
					widgets.get((i + 1) % widgets.size()).setFocused(true);
					widget.setFocused(false);
					return true;
				}
			}
		}
		// If there is a focused text field, attempt to type into it
		if (focused != null) {
			String old = focused.getText();
			if (focused.keyTyped(keyChar, keyCode, gui)) {
				gui.onTextFieldChanged(focused, old);
				return true;
			}
		}
		// More NEI behavior, f key focuses first text field
		if (keyChar == 'f' && focused == null && !widgets.isEmpty()) {
			focused = widgets.get(0);
			focused.setFocused(true);
		}
		return false;
	}

	public void drawTooltip(int mX, int mY) {
		for (Widget widget : widgets) {
			if (widget.isMouseOver(mX - gui.getGuiLeft(), mY - gui.getGuiTop())) {
				RenderUtil.renderTooltip(mX, mY, widget.getTooltip(gui));
			}
		}
	}

	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
		Widget widget = getWidgetAtMouse(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
		if (widget != null) {
			widget.handleMouseClick(mouseX, mouseY, mouseButton, gui);
		}
	}

	@Override
	public List<Widget> getWidgets() {
		return widgets;
	}

	@Override
	public G getGui() {
		return gui;
	}
}
