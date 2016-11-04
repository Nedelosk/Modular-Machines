package modularmachines.api.gui;

import java.util.ArrayList;
import java.util.Collection;

public interface IButtonManager<G extends IGuiBase> {

	void add(Button button);

	void add(Collection<Button> buttons);

	void remove(Button button);

	ArrayList<Button> getButtons();

	G getGui();
}
