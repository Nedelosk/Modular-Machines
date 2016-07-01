package de.nedelosk.modularmachines.api.modules.engine;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModuleDrive;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;

public interface IModuleEngine extends IModuleDrive {

	float getProgress(IModuleState state);

	void setProgress(IModuleState state, float progress);

	void addProgress(IModuleState state, float progress);

	boolean canWork(IModuleState state);

	boolean isWorking(IModuleState state);

	void setIsWorking(IModuleState state, boolean isWorking);

	int getBurnTimeModifier(IModuleState state);

	List<Integer> getMachineIndexes(IModuleState state);

	boolean removeMaterial(IModuleState state, IModuleState<IModuleTool> machineState);
}
