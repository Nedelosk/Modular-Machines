package de.nedelosk.modularmachines.common.modular.assembler;

import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import net.minecraft.item.ItemStack;

public class AssemblerSlotEngine extends AssemblerSlot{

	public AssemblerSlotEngine(IAssemblerGroup group, int xPos, int yPos, int index, String UID, Class<? extends IModule> moduleClass, IAssemblerSlot... parents) {
		super(group, xPos, yPos, index, UID, moduleClass, parents);
	}
	
	@Override
	public boolean onStatusChange(boolean isActive) {
		if(isActive){
			ItemStack engine = getStack();
			int engineSize = ((IModuleEngine)ModuleManager.getContainerFromItem(engine).getModule()).getSize().ordinal();
			
			IAssemblerSlot engine_0 = group.getSlot("engine_0");
			IAssemblerSlot engine_1 = group.getSlot("engine_1");
			int space = 3;
			if(engine_0 != null && engine_0.getStack() != null){
				space -= ((IModuleEngine)ModuleManager.getContainerFromItem(engine_0.getStack()).getModule()).getSize().ordinal();
				if(engine_1 != null && engine_1.getStack() != null){
					space -= ((IModuleEngine)ModuleManager.getContainerFromItem(engine_1.getStack()).getModule()).getSize().ordinal();
				}
			}
			return space != 0;
		}
		return isActive;
	}
	
	@Override
	public boolean canInsertItem(ItemStack stack) {
		ItemStack engine = getStack();
		int engineSize = ((IModuleEngine)ModuleManager.getContainerFromItem(engine).getModule()).getSize().ordinal();
		
		IAssemblerSlot engine_0 = group.getSlot("engine_0");
		IAssemblerSlot engine_1 = group.getSlot("engine_1");
		int space = 3;
		if(engine_0 != null && engine_0.getStack() != null){
			space -= ((IModuleEngine)ModuleManager.getContainerFromItem(engine_0.getStack()).getModule()).getSize().ordinal();
			if(engine_1 != null && engine_1.getStack() != null){
				space -= ((IModuleEngine)ModuleManager.getContainerFromItem(engine_1.getStack()).getModule()).getSize().ordinal();
			}
		}
		return space >= engineSize;
	}

}
