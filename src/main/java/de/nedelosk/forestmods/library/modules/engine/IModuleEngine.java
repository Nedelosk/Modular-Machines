package de.nedelosk.forestmods.library.modules.engine;

import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.modules.ModuleUID;

public interface IModuleEngine extends IModule {

	float getProgress();

	void setProgress(float progress);

	void addProgress(float progress);

	boolean canWork();

	boolean isWorking();

	void setIsWorking(boolean isWorking);

	int getBurnTimeModifier();

	ModuleUID getMachineUID();

	void setMachineUID(ModuleUID machineUID);

	boolean removeMaterial(IModular modular, IModuleMachine machine);
}
