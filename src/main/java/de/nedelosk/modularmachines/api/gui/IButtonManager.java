package de.nedelosk.modularmachines.api.gui;

import java.util.ArrayList;
import java.util.Collection;

public interface IButtonManager<G extends IGuiProvider> {

	void add(Button button);

	void add(Collection<Button> buttons);

	void remove(Button button);

	ArrayList<Button> getButtons();

	G getGui();
}
