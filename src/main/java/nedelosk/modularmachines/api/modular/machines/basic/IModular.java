package nedelosk.modularmachines.api.modular.machines.basic;

import java.util.HashMap;
import java.util.Vector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.manager.IModularGuiManager;
import nedelosk.modularmachines.api.modular.machines.manager.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerBattery;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.IProducerFluidManager;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModular {
	
	int getTier();
	
	void update(boolean isServer);
	
	IModularTileEntity getMachine();
	
	String getName();
	
	void initModular();
	
	void readFromNBT(NBTTagCompound nbt) throws Exception;

	void writeToNBT(NBTTagCompound nbt) throws Exception;
	
	//Utils
	ModuleStack<IModule, IProducerBattery> getBattery();
	
	ModuleStack<IModuleCasing, IProducer> getCasing();

	ModuleStack<IModule, IProducerFluidManager> getTankManeger();
	
	boolean addModule(ModuleStack module);
	
	Vector<ModuleStack> getModule(String moduleName);
	
	ModuleStack getModule(String moduleName, int id);
	
	HashMap<String, Vector<ModuleStack>> getModules();
	
	void setModules(HashMap<String, Vector<ModuleStack>> modules);
	
	void setMachine(IModularTileEntity machine);
	
	IModularUtilsManager getManager();
	
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
