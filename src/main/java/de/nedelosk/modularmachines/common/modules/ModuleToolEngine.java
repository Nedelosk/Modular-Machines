package de.nedelosk.modularmachines.common.modules;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.heater.IModuleHeater;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public abstract class ModuleToolEngine extends ModuleTool {

	public ModuleToolEngine(int speedModifier, int size) {
		super(speedModifier, size);
	}
	
	@Override
	public boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state) {
		if(modular.getModules(IModuleBattery.class).isEmpty()){
			return false;
		}
		if(modular.getModules(IModuleEngine.class).isEmpty()){
			return false;
		}
		return true;
	}

	@Override
	public boolean canWork(IModuleState state){
		return state.getModular().getHandler().getEnergyStored(null) > 0;
	}
}
