package nedelosk.modularmachines.common.core;

import cpw.mods.fml.common.Loader;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.RendererSides;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.common.modular.module.ModuleCasing;
import nedelosk.modularmachines.common.modular.module.energy.ModuleEngine;
import nedelosk.modularmachines.common.modular.module.manager.ModuleEnergyManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleFanManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleStorageManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleTankManager;
import nedelosk.modularmachines.common.modular.module.storage.ModuleChest;
import nedelosk.modularmachines.common.modular.module.tool.generator.ModuleGenerator;
import nedelosk.modularmachines.common.modular.module.tool.producer.ModuleFurnace;
import nedelosk.modularmachines.common.modular.module.tool.producer.sawmill.ModuleSawMill;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModularRegistry {
	
	public static void preInit()
	{
		ModularMachinesApi.addModule(new ModuleEnergyManager());
		ModularMachinesApi.addModule(new ModuleTankManager());
		ModularMachinesApi.addModule(new ModuleStorageManager());
		ModularMachinesApi.addModule(new ModuleFanManager());
		ModularMachinesApi.addModule(new ModuleFurnace());
		ModularMachinesApi.addModule(new ModuleSawMill());
		ModularMachinesApi.addModule(new ModuleEngine("Normal", 300, 150, 50));
		ModularMachinesApi.addModuleItem(new ItemStack(Blocks.iron_block), new ModuleCasing(), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(Items.iron_axe), new ModuleCasing(), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(Blocks.chest), new ModuleChest("Normal", 27), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(Blocks.furnace), new ModuleGenerator(), 1);
		ModularMachinesApi.addBookmarkItem("Basic", new ItemStack(Blocks.iron_block));
		ModularMachinesApi.addBookmarkItem("Storage", new ItemStack(Blocks.chest));
		
		ModularMachinesApi.addRequiredModule("Casing");
		ModularMachinesApi.addRequiredModule("Battery");
		ModularMachinesApi.addRequiredModule("EnergyManager");
		ModularMachinesApi.addRequiredModule("Engine");
		
		ModularMachinesApi.registerBookmark("Basic");
		ModularMachinesApi.registerBookmark("Energy");
		ModularMachinesApi.registerBookmark("Tool_Pruducer");
		ModularMachinesApi.registerBookmark("Fluid");
		ModularMachinesApi.registerBookmark("Storage");
    	if(Loader.isModLoaded("appliedenergistics2"))
    		ModularMachinesApi.registerBookmark("Storage_AE2");
    	
    	//Casing
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 102 - 9, "Basic", "Casing"));
    	
    	//Energy
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 102 - 9, "Energy", "Battery").setParent(ModularMachinesApi.getModuleEntry("Basic", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 66 - 9, "Energy", "EnergyAcceptor").setParent(ModularMachinesApi.getModuleEntry("Energy", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(152 - 9, 102 - 9, "Energy", "Engine").setParent(ModularMachinesApi.getModuleEntry("Energy", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 138 - 9, "Energy", "EnergyManager").setParent(ModularMachinesApi.getModuleEntry("Energy", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(80 - 9, 138 - 9, "Energy", "Capacitor").setParent(ModularMachinesApi.getModuleEntry("Energy", 3)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(152 - 9, 138 - 9, "Energy", "Capacitor").setParent(ModularMachinesApi.getModuleEntry("Energy", 3)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 174 - 9, "Energy", "Capacitor").setParent(ModularMachinesApi.getModuleEntry("Energy", 3)));
    	
    	//Tool
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(0, 116 - 9, 102 - 9, "Tool", "Producer", ModularMachinesApi.getModuleEntry("Basic", 0), "Tool_Pruducer", RendererSides.EAST, RendererSides.NORTH));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(1, 116 - 9, 66 - 9, "Tool", "Producer", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 0), "Tool_Pruducer"));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(2, 152 - 9, 102 - 9, "FanManager", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 0), "Tool_Pruducer"));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(3, 188 - 9, 102 - 9, "Fan", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 2), "Tool_Pruducer", true, 0, RendererSides.WEST));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(4, 188 - 9, 120 - 9, "Fan", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 2), "Tool_Pruducer", true, 1, RendererSides.WEST));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(5, 188 - 9, 138 - 9, "Fan", ModularMachinesApi.getModuleEntry("Tool_Pruducer", 2), "Tool_Pruducer", true, 2, RendererSides.WEST));
    	
    	//Fluid
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(0, 116 - 9, 102 - 9, "TankManager", ModularMachinesApi.getModuleEntry("Basic", 0), "Fluid"));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(1, 152 - 9, 102 - 9, "Tank", ModularMachinesApi.getModuleEntry("Fluid", 0), "Fluid", true, 0, RendererSides.WEST));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(2, 152 - 9, 120 - 9, "Tank", ModularMachinesApi.getModuleEntry("Fluid", 0), "Fluid", true, 1, RendererSides.WEST));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(3, 152 - 9, 138 - 9, "Tank", ModularMachinesApi.getModuleEntry("Fluid", 0), "Fluid", true, 2, RendererSides.WEST));
    	
    	//Storage
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 102 - 9, "Storage", "StorageManager").setParent(ModularMachinesApi.getModuleEntry("Basic", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(152 - 9, 102 - 9, "Storage", "Storage").setParent(ModularMachinesApi.getModuleEntry("Storage", 0)));
 
	}
	
}
