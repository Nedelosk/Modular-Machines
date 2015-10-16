package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.tier.Tiers;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.core.manager.MMItemManager;
import nedelosk.modularmachines.common.modular.machines.modular.ModularMachine;
import nedelosk.modularmachines.common.modular.module.basic.ModuleCasing;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerBattery;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerCapacitor;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerEngine;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.alloysmelter.ProducerAlloySmelter;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.centrifuge.ProducerCentrifuge;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.furnace.ProducerFurnace;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.pulverizer.ProducerPulverizer;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.sawmill.ProducerSawMill;
import nedelosk.modularmachines.common.modular.module.tool.producer.storage.ProducerChest;
import nedelosk.modularmachines.common.modular.module.tool.producer.storage.ProducerStorageManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ModularRegistry {
	
	public static IModule CASING = new ModuleCasing();
	
	public static void preInit()
	{
		/*ModuleRegistry.addModule(new ModuleTankManager());
		ModuleRegistry.addModule(new ProducerStorageManager());
		ModuleRegistry.addModule(new ProducerFurnace());
		ModuleRegistry.addModule(new ProducerSawMill());
		ModuleRegistry.addModule(new ProducerAlloySmelter());
		ModuleRegistry.addModule(new ProducerPulverizer());
		ModuleRegistry.addModule(new ProducerCentrifuge());
		ModuleRegistry.addModule(new ProducerEngine("Normal", 300, 150, 50));
		ModuleRegistry.addModuleStack(new ItemStack(Blocks.iron_block), new ModuleCasing("iron"), 1);
		ModuleRegistry.addModuleStack(new ItemStack(Blocks.gold_block), new ModuleCasing("gold"), 2);
		ModuleRegistry.addModuleStack(new ItemStack(Blocks.diamond_block), new ModuleCasing("diamond"), 3);
		ModuleRegistry.addModuleStack(new ItemStack(Blocks.chest), new ProducerChest("Normal", 27), 1);
		ModuleRegistry.addModuleStack(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 0), new ProducerCapacitor(10, 20), 1);
		ModuleRegistry.addModuleStack(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 1), new ProducerCapacitor(20, 30), 2);
		ModuleRegistry.addModuleStack(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 2), new ProducerCapacitor(25, 40), 2);
		ModuleRegistry.addModuleStack(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 3), new ProducerCapacitor(40, 60), 1);*/
		
		ModuleRegistry.registerModular(ModularMachine.class, "modular.machines");
		
    	//if(Loader.isModLoaded("appliedenergistics2"))
    		//ModularMachinesApi.registerBookmark("Storage_AE2");
	}
	
	public static void registerCasings(){
		CASING.addTier(Tiers.WOOD);
		CASING.addTier(Tiers.STONE);
		CASING.addTier(Tiers.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.log), CASING, Tiers.WOOD);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.log2), CASING, Tiers.WOOD);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.stone), CASING, Tiers.STONE);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.iron_block), CASING, Tiers.IRON);
	}
	
}
