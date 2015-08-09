package nedelosk.modularmachines.common.core.registry;

import cpw.mods.fml.common.Loader;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.api.modular.module.IModuleGenerator;
import nedelosk.modularmachines.api.modular.module.IModuleProducer;
import nedelosk.modularmachines.api.modular.module.ModuleEntry;
import nedelosk.modularmachines.api.techtree.TechPointStack;
import nedelosk.modularmachines.api.techtree.TechPointTypes;
import nedelosk.modularmachines.common.modular.module.ModuleCasing;
import nedelosk.modularmachines.common.modular.module.energy.ModuleEngine;
import nedelosk.modularmachines.common.modular.module.manager.ModuleEnergyManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleFanManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleStorageManager;
import nedelosk.modularmachines.common.modular.module.manager.ModuleTankManager;
import nedelosk.modularmachines.common.modular.module.storage.ModuleChest;
import nedelosk.modularmachines.common.modular.module.tool.generator.ModuleGenerator;
import nedelosk.modularmachines.common.modular.module.tool.producer.ModuleFurnace;
import nedelosk.modularmachines.common.modular.module.tool.producer.alloysmelter.ModuleAlloySmelter;
import nedelosk.modularmachines.common.modular.module.tool.producer.centrifuge.ModuleCentrifuge;
import nedelosk.modularmachines.common.modular.module.tool.producer.pulverizer.ModulePulverizer;
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
		ModularMachinesApi.addModule(new ModuleAlloySmelter());
		ModularMachinesApi.addModule(new ModulePulverizer());
		ModularMachinesApi.addModule(new ModuleCentrifuge());
		ModularMachinesApi.addModule(new ModuleEngine("Normal", 300, 150, 50));
		ModularMachinesApi.addModuleItem(new ItemStack(Blocks.iron_block), new ModuleCasing(), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(Items.iron_axe), new ModuleCasing(), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(Blocks.chest), new ModuleChest("Normal", 27), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(Blocks.furnace), new ModuleGenerator(), 1);
		ModularMachinesApi.addBookmarkItem("Basic", new ItemStack(Blocks.iron_block));
		ModularMachinesApi.addBookmarkItem("Storage", new ItemStack(Blocks.chest));
		ModularMachinesApi.addBookmarkItem("Tool_Pruducer", new ItemStack(Items.iron_axe));
		
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
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 102 - 9, "Tool_Pruducer", "Tool", "Producer", "Generator"){
    		@Override
			public void onActivateItem(nedelosk.modularmachines.api.IModularAssembler assembler) {
    			if(assembler.getStackInSlot(page, ID) != null)
    			{
    				if(ModularMachinesApi.getModuleItem(assembler.getStackInSlot(page, ID)) != null)
    				{
    					IModule module = ModularMachinesApi.getModuleItem(assembler.getStackInSlot(page, ID)).getModule();
    					if(module instanceof IModuleGenerator)
    						assembler.getModuleEntry(page, 1).canActivate = false;
    					if(module instanceof IModuleProducer)
    						assembler.getModuleEntry(page, 1).activatedModuleNames[1] = false;
    				}
    			}
    		};
    		
    		@Override
			public void onDeactivateItem(nedelosk.modularmachines.api.IModularAssembler assembler) {
    			if(assembler.getStackInSlot(page, ID) == null)
    			{
    				assembler.getModuleEntry(page, 1).canActivate = true;
    				for(boolean activate : activatedModuleNames)
    					activate = true;
    			}
    		};
    	}.setParent(ModularMachinesApi.getModuleEntry("Basic", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 66 - 9, "Tool_Pruducer", "Tool", "Producer").setParent(ModularMachinesApi.getModuleEntry("Tool_Pruducer", 0)));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 138 - 9, "Tool_Pruducer", "FanManager").setParent(ModularMachinesApi.getModuleEntry("Tool_Pruducer", 0)));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(80 - 9, 138 - 9, "Tool_Pruducer", "Fan").setParent(ModularMachinesApi.getModuleEntry("Tool_Pruducer", 2)));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(152 - 9, 138 - 9, "Tool_Pruducer", "Fan").setParent(ModularMachinesApi.getModuleEntry("Tool_Pruducer", 2)));
    	//ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 174 - 9, "Tool_Pruducer", "Fan").setParent(ModularMachinesApi.getModuleEntry("Tool_Pruducer", 2)));
    	
    	//Fluid
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 102 - 9, "Fluid", "TankManager").setParent(ModularMachinesApi.getModuleEntry("Basic", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(80 - 9, 102 - 9, "Fluid", "Tank").setParent(ModularMachinesApi.getModuleEntry("Fluid", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(152 - 9, 102 - 9, "Fluid", "Tank").setParent(ModularMachinesApi.getModuleEntry("Fluid", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 138 - 9, "Fluid", "Tank").setParent(ModularMachinesApi.getModuleEntry("Fluid", 0)));
    	
    	//Storage
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(116 - 9, 102 - 9, "Storage", "StorageManager").setParent(ModularMachinesApi.getModuleEntry("Basic", 0)));
    	ModularMachinesApi.addModuleEntry(new ModuleEntry(152 - 9, 102 - 9, "Storage", "Storage").setParent(ModularMachinesApi.getModuleEntry("Storage", 0)));
    	
    	ModularMachinesApi.addTechPointsToItem(new ItemStack(Blocks.iron_block), new TechPointStack(10, TechPointTypes.EASY));
 
	}
	
}
