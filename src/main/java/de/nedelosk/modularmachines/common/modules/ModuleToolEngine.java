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
import net.minecraft.item.ItemStack;

public abstract class ModuleToolEngine extends ModuleTool {

	public ModuleToolEngine(int speedModifier, int size) {
		super(speedModifier, size);
	}

	@Override
	public IAssemblerGroup createGroup(IAssembler assembler, ItemStack stack, int groupID) {
		IAssemblerGroup group = new AssemblerGroup(assembler, groupID);
		IAssemblerSlot controllerSlot = new AssemblerSlot(group, 4, 4, assembler.getNextIndex(), "controller", ModuleTool.class);
		group.setControllerSlot(controllerSlot);
		group.addSlot(new AssemblerSlot(group, 4, 6, group.getAssembler().getNextIndex(), "engine", IModuleEngine.class, group.getControllerSlot()));
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
	public boolean canWork(IModuleState state){
		return state.getModular().getTile().getEnergyStored(null) > 0;
	}
}
