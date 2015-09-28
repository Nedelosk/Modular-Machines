package nedelosk.nedeloskcore.api.machines;

import java.util.ArrayList;

public interface IButtonManager {

	void add(Button slot);

	void remove(Button slot);
	
	ArrayList<Button> getButtons();
}
