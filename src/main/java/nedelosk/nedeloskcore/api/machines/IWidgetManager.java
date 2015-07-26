package nedelosk.nedeloskcore.api.machines;

import java.util.ArrayList;

import nedelosk.nedeloskcore.client.gui.widget.Widget;

public interface IWidgetManager {

	void add(Widget slot);

	void remove(Widget slot);
	
	ArrayList<Widget> getWidgets();
	
}
