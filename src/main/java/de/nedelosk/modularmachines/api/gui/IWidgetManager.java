package de.nedelosk.modularmachines.api.gui;

import java.util.Collection;
import java.util.List;

import de.nedelosk.modularmachines.client.gui.Widget;

public interface IWidgetManager<G extends IGuiBase> {

	void add(Widget slot);

	void addAll(Collection<Widget> slots);

	void remove(Widget slot);

	Widget getWidgetAtMouse(int mouseX, int mouseY);

	List<Widget> getWidgets();

	G getGui();
}
