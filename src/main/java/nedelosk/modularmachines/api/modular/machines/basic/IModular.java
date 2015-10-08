package nedelosk.modularmachines.api.modular.machines.basic;

import java.util.HashMap;
import java.util.Vector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.manager.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleBattery;
import nedelosk.modularmachines.api.modular.module.basic.fluids.IModuleFluidManager;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.item.ItemStack;

public interface IModular extends INBTTagable {
	
	IModularUtilsManager getManager();
	
	int getTier();
	
	IModular buildItem(ItemStack[] stacks);
	
	HashMap<String, Vector<ModuleStack>> getModules();
	
	void setModules(HashMap<String, Vector<ModuleStack>> modules);
	
	void setMachine(IModularTileEntity machine);
	
	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ItemStack stack);
	
	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile);
	
	IModularTileEntity getMachine();
	
	ModuleStack<IModuleBattery> getBattery();
	
	ModuleStack<IModuleCasing> getCasing();

	ModuleStack<IModuleFluidManager> getTankManeger();
	
	void addModule(ModuleStack module);
	
	Vector<ModuleStack> getModule(String moduleName);
	
	ModuleStack getModule(String moduleName, int id);
	
	String getName();
	
	void initModular();
	
	int getStorageSlots();
	
	int getUsedStorageSlots();
	
	void setStorageSlots(int slots);
	
	void setUsedStorageSlots(int slots);
	
}
