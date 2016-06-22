package de.nedelosk.modularmachines.api.modules.engine;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;

public interface IModuleEngine extends IModule {

	float getProgress(IModuleState state);

	void setProgress(IModuleState state, float progress);

	void addProgress(IModuleState state, float progress);

	boolean canWork(IModuleState state);

	boolean isWorking(IModuleState state);

	void setIsWorking(IModuleState state, boolean isWorking);

	int getBurnTimeModifier(IModuleState state);

	List<Integer> getMachineIndexes(IModuleState state);
	
	EnumEnigneSize getSize();
	
	int getLayer(IModuleState state);
	
	int getPosition(IModuleState state);

	boolean removeMaterial(IModuleState state, IModuleState<IModuleTool> machineState);
}
