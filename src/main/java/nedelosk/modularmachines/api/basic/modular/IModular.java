package nedelosk.modularmachines.api.basic.modular;

import java.util.ArrayList;

import nedelosk.modularmachines.api.basic.modular.module.IModuleEngine;
import nedelosk.modularmachines.api.basic.modular.module.IModuleGenerator;
import nedelosk.modularmachines.api.basic.modular.module.IModuleTank;
import nedelosk.modularmachines.api.basic.modular.module.ModuleStack;
import nedelosk.modularmachines.api.basic.modular.module.energy.IModuleBattery;
import nedelosk.modularmachines.api.basic.modular.module.energy.IModuleCapacitor;
import nedelosk.modularmachines.api.basic.modular.module.manager.IModuleEnergyManager;
import nedelosk.modularmachines.api.basic.modular.module.manager.IModuleTankManager;
import net.minecraftforge.fluids.IFluidHandler;

public interface IModular {
	
	IFluidHandler getFluidHandler();
	
	int getTier();
	
	IModuleBattery getBattery();
	
	IModuleCapacitor[] getCapacitors();
	
	IModuleTank[] getTanks();
	
	ModuleStack getProducer();
	
	IModuleEngine getEngine();
	
	IModuleGenerator getGenerator();
	
	ArrayList<ModuleStack> getModules();
	
	IModularTileEntity getMachine();
	
	IModuleEnergyManager getEnergyManger();
	
	IModuleTankManager getTankManeger();
	
	ModuleStack getStorage();
	
}
