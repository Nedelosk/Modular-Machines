package de.nedelosk.forestmods.api.modules.engine;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModuleAddable;
import de.nedelosk.forestmods.api.modules.basic.IModuleUpdatable;
import de.nedelosk.forestmods.api.modules.basic.IModuleWithRenderer;
import de.nedelosk.forestmods.api.modules.integration.IModuleWaila;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachine;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;

public interface IModuleEngine extends IModuleAddable, IModuleUpdatable, IModuleWaila, IModuleWithRenderer {

	boolean removeMaterial(IModular modular, ModuleStack<IModuleEngine, IModuleEngineSaver> engineStack,
			ModuleStack<IModuleMachine, IModuleMachineRecipeSaver> machineStack);

	int getBurnTimeTotal(IModular modular, int speedModifier, ModuleStack<IModuleMachine, IModuleMachineRecipeSaver> stackMachine,
			ModuleStack<IModuleEngine, IModuleEngineSaver> stackEngine);
}
