package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.materials.Stats;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import nedelosk.modularmachines.common.core.manager.MMItemManager;
import nedelosk.modularmachines.common.materials.MachineState;
import nedelosk.modularmachines.common.modular.machines.modular.ModularMachine;
import nedelosk.modularmachines.common.modular.module.basic.basic.ModuleCasing;
import nedelosk.modularmachines.common.modular.module.basic.energy.ModuleBattery;
import nedelosk.modularmachines.common.modular.module.basic.energy.ModuleCapacitor;
import nedelosk.modularmachines.common.modular.module.basic.energy.ModuleEngine;
import nedelosk.modularmachines.common.modular.module.basic.fluids.ModuleTankManager;
import nedelosk.modularmachines.common.modular.module.basic.storage.ModuleChest;
import nedelosk.modularmachines.common.modular.module.basic.storage.ModuleStorageManager;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.alloysmelter.ModuleAlloySmelter;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.centrifuge.ModuleCentrifuge;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.furnace.ModuleFurnace;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.pulverizer.ModulePulverizer;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.sawmill.ModuleSawMill;
import nedelosk.modularmachines.common.modular.utils.MaterialManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ModularRegistry {
	
	public static void preInit()
	{
		ModuleRegistry.addModule(new ModuleTankManager());
		ModuleRegistry.addModule(new ModuleStorageManager());
		ModuleRegistry.addModule(new ModuleFurnace());
		ModuleRegistry.addModule(new ModuleSawMill());
		ModuleRegistry.addModule(new ModuleAlloySmelter());
		ModuleRegistry.addModule(new ModulePulverizer());
		ModuleRegistry.addModule(new ModuleCentrifuge());
		ModuleRegistry.addModule(new ModuleEngine("Normal", 300, 150, 50));
		ModuleRegistry.addModuleStack(new ItemStack(Blocks.iron_block), new ModuleCasing(), 1);
		ModuleRegistry.addModuleStack(new ItemStack(Blocks.gold_block), new ModuleCasing(), 2);
		ModuleRegistry.addModuleStack(new ItemStack(Blocks.diamond_block), new ModuleCasing(), 3);
		ModuleRegistry.addModuleStack(new ItemStack(Blocks.chest), new ModuleChest("Normal", 27), 1);
		ModuleRegistry.addModuleStack(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 0), new ModuleCapacitor(10, 20), 1);
		ModuleRegistry.addModuleStack(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 1), new ModuleCapacitor(20, 30), 2);
		ModuleRegistry.addModuleStack(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 2), new ModuleCapacitor(25, 40), 2);
		ModuleRegistry.addModuleStack(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 3), new ModuleCapacitor(40, 60), 1);
		ModuleRegistry.registerModuleClass(ModuleBattery.class, "moduleBatteryBatteryModular");
		ModuleRegistry.registerModuleClass(ModuleEngine.class, "moduleEngineEngineModular");
		
		ModuleRegistry.registerModular(ModularMachine.class, "modular.machines");
		
        for(int i = 0;i < MMRegistry.materials.size();i++) {
            ItemStack stack = new ItemStack(MMBlockManager.Metal_Blocks.block(), 1, i);
            Material mat = MMRegistry.materials.get(i);
            if(mat.type == MaterialType.METAL){
            	MaterialManager.setMaterial(stack, mat);
            	if (MaterialManager.getMaterial(stack) != null){
            		ModuleRegistry.addModuleStack(stack, new ModuleCasing(), ((MachineState)mat.getStats(Stats.MACHINE)).tier());
            	}
            }
        }
		
    	//if(Loader.isModLoaded("appliedenergistics2"))
    		//ModularMachinesApi.registerBookmark("Storage_AE2");
	}
	
}
