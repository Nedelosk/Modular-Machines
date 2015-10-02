package nedelosk.modularmachines.api.modular.machines.basic;

import java.util.HashMap;
import java.util.Vector;

import nedelosk.modularmachines.api.modular.machines.manager.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleEnergyManager;
import nedelosk.modularmachines.api.modular.module.basic.fluids.IModuleFluidManager;
import nedelosk.modularmachines.api.modular.module.utils.ModuleStack;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.item.ItemStack;

public interface IModular extends INBTTagable {
	
	IModularUtilsManager getManager();
	
	int getTier();
	
	IModular buildItem(ItemStack[] stacks);
	
	HashMap<String, Vector<ModuleStack>> getModules();
	
	void setModules(HashMap<String, Vector<ModuleStack>> modules);
	
	IModularTileEntity getMachine();
	
	ModuleStack<IModuleEnergyManager> getEnergyManger();
	
	ModuleStack<IModuleCasing> getCasing();

	ModuleStack<IModuleFluidManager> getTankManeger();
	
	void addModule(ModuleStack module);
	
	Vector<ModuleStack> getModule(String moduleName);
	
	ModuleStack getModule(String moduleName, int id);
	
	String getName();
	
}
