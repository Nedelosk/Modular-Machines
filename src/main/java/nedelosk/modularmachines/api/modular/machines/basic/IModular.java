package nedelosk.modularmachines.api.modular.machines.basic;

import java.util.HashMap;
import java.util.Vector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.manager.IModularGuiManager;
import nedelosk.modularmachines.api.modular.machines.manager.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.energy.IModuleBattery;
import nedelosk.modularmachines.api.modular.module.basic.fluids.IModuleFluidManager;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.item.ItemStack;

public interface IModular extends INBTTagable {
	
	int getTier();
	
	void update();
	
	IModularTileEntity getMachine();
	
	String getName();
	
	void initModular();
	
	//Utils
	ModuleStack<IModuleBattery> getBattery();
	
	ModuleStack<IModuleCasing> getCasing();

	ModuleStack<IModuleFluidManager> getTankManeger();
	
	void addModule(ModuleStack module);
	
	Vector<ModuleStack> getModule(String moduleName);
	
	ModuleStack getModule(String moduleName, int id);
	
	HashMap<String, Vector<ModuleStack>> getModules();
	
	void setModules(HashMap<String, Vector<ModuleStack>> modules);
	
	void setMachine(IModularTileEntity machine);
	
	IModularUtilsManager getManager();
	
	//Storage
	int getStorageSlots();
	
	int getUsedStorageSlots();
	
	void setStorageSlots(int slots);
	
	void setUsedStorageSlots(int slots);
	
	//Gui
	IModularGuiManager getGuiManager();
	
	//Renderer
	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ItemStack stack);
	
	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile);
	
	//Item
	IModular buildItem(ItemStack[] stacks);
	
}
