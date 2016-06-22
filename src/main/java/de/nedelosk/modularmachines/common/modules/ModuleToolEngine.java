package de.nedelosk.modularmachines.common.modules;

import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModularLogic;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerGroup;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerSlot;
import de.nedelosk.modularmachines.common.modular.assembler.AssemblerSlotEngine;
import de.nedelosk.modularmachines.common.modules.engine.ModuleEngineStorage;
import net.minecraft.item.ItemStack;

public abstract class ModuleToolEngine extends ModuleTool {

	protected final int engines;

	public ModuleToolEngine(int speedModifier, int engines) {
		super(speedModifier);
		this.engines = engines;
	}

	@Override
	public IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID) {
		IAssemblerGroup group = new AssemblerGroup(assembler, groupID);
		IAssemblerSlot controllerSlot = new AssemblerSlot(group, 4, 4, assembler.getNextIndex(), "controller", ModuleTool.class);
		group.setControllerSlot(controllerSlot);
		IAssemblerSlot engine_0 = group.addSlot(new AssemblerSlotEngine(group, 2, 6, group.getAssembler().getNextIndex(), "engine_0", IModuleEngine.class, group.getControllerSlot()));
		IAssemblerSlot engine_1 = group.addSlot(new AssemblerSlotEngine(group, 4, 6, group.getAssembler().getNextIndex(), "engine_1", IModuleEngine.class, engine_0));	
		group.addSlot(new AssemblerSlotEngine(group, 6, 6, group.getAssembler().getNextIndex(), "engine_2", IModuleEngine.class, engine_1));
		return group;
	}
	
	@Override
	public boolean canAssembleGroup(IAssemblerGroup group) {
		IAssembler assembler = group.getAssembler();

		boolean hasBattery = false;
		for(IAssemblerGroup otherGroup : assembler.getGroups().values()){
			if(otherGroup != null){
				ItemStack stack = otherGroup.getControllerSlot().getStack();
				if(stack != null){
					IModuleContainer container = ModuleManager.getContainerFromItem(stack);
					if(IModuleBattery.class.isAssignableFrom(container.getModule().getClass())){
						hasBattery = true;
						break;
					}
				}
			}
		}
		if(!hasBattery){
			return false;
		}
		if(group.getSlot("engine_0").getStack() == null){
			return false;
		}
		return true;
	}
	
	@Override
	public List<IModularLogic> createLogic(IModuleState state) {
		return Collections.singletonList(new ModuleEngineStorage(state.getModular()));
	}

	@Override
	public boolean canWork(IModuleState state){
		return state.getModular().getTile().getEnergyStored(null) > 0;
	}
}
