package de.nedelosk.modularmachines.api.gui;

import java.util.ArrayList;
import java.util.Collection;

import de.nedelosk.modularmachines.client.gui.Button;

public interface IButtonManager<G extends IGuiBase> {

	void add(Button slot);

	void add(Collection<Button> slots);

	void remove(Button slot);

	ArrayList<Button> getButtons();

	G getGui();
}
