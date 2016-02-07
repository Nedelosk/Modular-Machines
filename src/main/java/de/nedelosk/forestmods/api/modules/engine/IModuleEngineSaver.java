package de.nedelosk.forestmods.api.modules.engine;

import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleEngineSaver extends IModuleSaver {

	float getProgress();

	void setProgress(float progress);

	void addProgress(float progress);

	int getBurnTime(ModuleStack stack);

	void setBurnTime(int burnTime);

	int getBurnTimeTotal(ModuleStack stack);

	void setBurnTimeTotal(int burnTimeTotal);

	boolean isWorking();

	void setIsWorking(boolean isWorking);

	void addBurnTime(int burntime);
}
