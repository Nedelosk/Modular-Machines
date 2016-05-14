package de.nedelosk.forestmods.library.modules;

import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.integration.IModuleNEI;

public interface IModuleMachine extends IModuleNEI, IModuleController {

	Object[] getRecipeModifiers();

	String getRecipeCategory();

	boolean removeInput();

	boolean addOutput();

	IRecipeManager getRecipeManager();

	void setRecipeManager(IRecipeManager manager);

	int getBurnTime();

	void addBurnTime(int burntime);

	void setBurnTime(int burnTime);

	int getBurnTimeTotal();

	void setBurnTimeTotal(int burnTimeTotal);

	int getBurnTimeTotal(IModular modular, int speedModifier);

	int getSpeedModifier();
}
