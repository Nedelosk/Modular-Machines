package nedelosk.forestday.api.guis;

import java.util.ArrayList;

public interface IButtonManager {

	void add(Button slot);

	void remove(Button slot);

	ArrayList<Button> getButtons();
}
