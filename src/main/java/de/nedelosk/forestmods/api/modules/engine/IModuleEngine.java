package de.nedelosk.forestmods.api.modules.engine;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleAdvanced;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleEngine extends IModule {

	float getProgress();

	void setProgress(float progress);

	void addProgress(float progress);

	int getBurnTime();

	void setBurnTime(int burnTime);

	int getBurnTimeTotal();

	void setBurnTimeTotal(int burnTimeTotal);

	boolean isWorking();

	void setIsWorking(boolean isWorking);

	void addBurnTime(int burntime);

	int getSpeedModifier();

	boolean removeMaterial(IModular modular, ModuleStack<IModuleAdvanced> machineStack);

	int getBurnTimeTotal(IModular modular, int speedModifier, ModuleStack<IModuleAdvanced> machineStack);
}
