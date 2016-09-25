package de.nedelosk.modularmachines.client.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.nedelosk.modularmachines.api.gui.IGuiProvider;
import de.nedelosk.modularmachines.api.gui.IWidgetManager;
import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.common.utils.RenderUtil;
import net.minecraft.client.Minecraft;

public class WidgetManager<G extends IGuiProvider> implements IWidgetManager<G> {

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
		for(Widget widget : widgets) {
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
		for(Widget widget : widgets) {
			if (widget.isMouseOver(mouseX, mouseY)) {
				return widget;
			}
		}
		return null;
	}

	public void drawWidgets() {
		for(Widget slot : widgets) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			slot.draw(gui);
		}
		for(Widget slot : widgets) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			slot.drawStrings(gui);
		}
	}

	public boolean keyTyped(char keyChar, int keyCode) {
		for(Widget widget : widgets) {
			if (widget.keyTyped(keyChar, keyCode, gui)) {
				return true;
			}
		}
		return false;
	}

	public void drawTooltip(int mX, int mY) {
		for(Widget widget : widgets) {
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
