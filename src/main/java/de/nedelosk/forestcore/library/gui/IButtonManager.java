package de.nedelosk.forestcore.library.gui;

import java.util.ArrayList;
import java.util.Collection;

public interface IButtonManager<G extends IGuiBase> {

	void add(Button slot);

	void add(Collection<Button> slots);

	void remove(Button slot);

	ArrayList<Button> getButtons();

	G getGui();
}
