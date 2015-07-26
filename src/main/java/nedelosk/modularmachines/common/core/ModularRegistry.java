package nedelosk.modularmachines.common.core;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.RendererSides;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.common.modular.module.ModuleCasing;
import nedelosk.modularmachines.common.modular.module.manager.ModuleEnergyManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleFanManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleStorageManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleTankManager;
import nedelosk.modularmachines.common.modular.module.storage.ModuleChest;
import nedelosk.modularmachines.common.modular.module.tool.producer.ModuleFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;

public class ModularRegistry {
	
	public static void preInit()
	{
		ModularMachinesApi.addModule(new ModuleEnergyManager());
		ModularMachinesApi.addModule(new ModuleTankManager());
		ModularMachinesApi.addModule(new ModuleStorageManager());
		ModularMachinesApi.addModule(new ModuleFanManager());
		ModularMachinesApi.addModule(new ModuleFurnace());
		ModularMachinesApi.addModuleItem(new ItemStack(Blocks.iron_block), new ModuleCasing(), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(Items.iron_axe), new ModuleCasing(), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(Blocks.chest), new ModuleChest(27), 1);
		ModularMachinesApi.addBookmarkItem("Basic", new ItemStack(Blocks.iron_block));
		
		ModularMachinesApi.addRequiredModule("Casing");
		ModularMachinesApi.addRequiredModule("Battery");
		ModularMachinesApi.addRequiredModule("EnergyManager");
		
		ModularMachinesApi.registerBookmark("Basic");
		ModularMachinesApi.registerBookmark("Energy");
		ModularMachinesApi.registerBookmark("Tool_Pruducer");
		ModularMachinesApi.registerBookmark("Fluid");
		ModularMachinesApi.registerBookmark("Storage");
    	if(Loader.isModLoaded("appliedenergistics2"))
    		ModularMachinesApi.registerBookmark("Storage_AE2");
    	
    	//Casing
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(0, 116 - 9, 102 - 9, "Casing", "Basic").setTier(1));
    	
    	//Energy
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(0, 116 - 9, 102 - 9, "Battery", ModularMachinesApi.getModuleEntry("Basic", 0), "Energy", RendererSides.EAST, RendererSides.NORTH, RendererSides.SOUTH));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(1, 116 - 9, 66 - 9, "EnergyAcceptor", ModularMachinesApi.getModuleEntry("Energy", 0), "Energy"));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(2, 152 - 9, 102 - 9, "Engine", ModularMachinesApi.getModuleEntry("Energy", 0), "Energy"));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(3, 116 - 9, 138 - 9, "EnergyManager", ModularMachinesApi.getModuleEntry("Energy", 0), "Energy"));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(4, 152 - 9, 138 - 9, "Capacitor", ModularMachinesApi.getModuleEntry("Energy", 3), "Energy", true, 0, RendererSides.WEST));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(5, 152 - 9, 156 - 9, "Capacitor", ModularMachinesApi.getModuleEntry("Energy", 3), "Energy", true, 1, RendererSides.WEST));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(6, 152 - 9, 174 - 9, "Capacitor", ModularMachinesApi.getModuleEntry("Energy", 3), "Energy", true, 2, RendererSides.WEST));
    	
    	//Tool
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(0, 116 - 9, 102 - 9, "Tool", "Producer", ModularMachinesApi.getModuleEntry("Basic", 0), "Tool_Pruducer", RendererSides.EAST, RendererSides.NORTH));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(1, 116 - 9, 66 - 9, "Tool", "Producer", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 0), "Tool_Pruducer"));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(2, 152 - 9, 102 - 9, "FanManager", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 0), "Tool_Pruducer"));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(3, 188 - 9, 102 - 9, "Fan", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 2), "Tool_Pruducer", true, 0, RendererSides.WEST));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(4, 188 - 9, 120 - 9, "Fan", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 2), "Tool_Pruducer", true, 1, RendererSides.WEST));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(5, 188 - 9, 138 - 9, "Fan", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 2), "Tool_Pruducer", true, 2, RendererSides.WEST));
    	
    	//Fluid
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(0, 116 - 9, 102 - 9, "TankManager", ModularMachinesApi.getModuleEntry("Basic", 0), "Fluid"));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(1, 152 - 9, 102 - 9, "Tank", ModularMachinesApi.getModuleEntry("Fluid", 0), "Fluid", true, 0, RendererSides.WEST));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(2, 152 - 9, 120 - 9, "Tank", ModularMachinesApi.getModuleEntry("Fluid", 0), "Fluid", true, 1, RendererSides.WEST));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(3, 152 - 9, 138 - 9, "Tank", ModularMachinesApi.getModuleEntry("Fluid", 0), "Fluid", true, 2, RendererSides.WEST));
    	
    	//Storage
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(0, 116 - 9, 102 - 9, "StorageManager", ModularMachinesApi.getModuleEntry("Basic", 0), "Storage", RendererSides.EAST));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(1, 152 - 9, 102 - 9, "Storage", ModularMachinesApi.getModuleEntry("Storage", 0), "Storage"));
	}
	
}
