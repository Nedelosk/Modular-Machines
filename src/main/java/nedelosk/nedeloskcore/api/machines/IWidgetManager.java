package nedelosk.nedeloskcore.api.machines;

import java.util.ArrayList;

public interface IWidgetManager<G extends IGuiBase> {

	void add(Widget slot);

	void remove(Widget slot);
	
	ArrayList<Widget> getWidgets();
	
	G getGui();
	
}
