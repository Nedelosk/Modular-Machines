package de.nedelosk.forestmods.api.modules.engine;

import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.recipes.IRecipeManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleEngineSaver extends IModuleSaver {

	IRecipeManager getManager(ModuleStack stack);

	void setManager(IRecipeManager manager);

	float getProgress();

	void setProgress(float progress);

	void addProgress(float progress);

	int getBurnTime(ModuleStack stack);

	void setBurnTime(int burnTime);

	int getBurnTimeTotal(ModuleStack stack);

	void setBurnTimeTotal(int burnTimeTotal);

	boolean isWorking();

	void setIsWorking(boolean isWorking);

	int getTimer();

	void setTimer(int timer);

	int getTimerTotal();

	void addTimer(int timer);

	void addBurnTime(int burntime);
}
