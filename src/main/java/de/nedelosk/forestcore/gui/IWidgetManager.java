package de.nedelosk.forestcore.gui;

import java.util.Collection;
import java.util.List;

public interface IWidgetManager<G extends IGuiBase> {

	void add(Widget slot);

	void add(Collection<Widget> slots);

	void remove(Widget slot);

	List<Widget> getWidgets();

	G getGui();
}
