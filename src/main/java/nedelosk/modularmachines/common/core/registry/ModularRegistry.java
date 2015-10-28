package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.modular.module.Modules;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.core.manager.MMItemManager;
import nedelosk.modularmachines.common.items.ItemProducers;
import nedelosk.modularmachines.common.modular.machines.modular.ModularMachine;
import nedelosk.modularmachines.common.modular.module.tool.producer.boiler.ProducerBurningBoiler;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerCapacitor;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerEngine;
import nedelosk.modularmachines.common.modular.module.tool.producer.fluids.ProducerTankManager;
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

	public static void preInit() {
		registerCasings();
		registerMachines();
		registerManagers();
		registerEnergy();
		registerStorage();
		registerFluids();

		registerModular();
	}

	public static void registerModular() {
		ModuleRegistry.registerModular(ModularMachine.class, "modular.machine");
	}

	public static void registerStorage() {
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.chest), Modules.CHEST, new ProducerChest("Chest", 27), Types.WOOD);
	}
	
	public static void registerFluids() {
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.BOILER, new ProducerBurningBoiler("Stone", 150), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.BOILER, new ProducerBurningBoiler("Iron", 125), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.BOILER, new ProducerBurningBoiler("Bronze", 100), Types.BRONZE, true)));
	}

	public static void registerEnergy() {
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Engine.item(), 1, 0), Modules.ENGINE, new ProducerEngine("iron_engine", 300, "Steam"), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Engine.item(), 1, 1), Modules.ENGINE, new ProducerEngine("bronze_engine", 225, "Steam"), Types.BRONZE);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Engine.item(), 1, 2), Modules.ENGINE, new ProducerEngine("steel_engine", 175, "Normal"), Types.STEEL);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Engine.item(), 1, 3), Modules.ENGINE, new ProducerEngine("magmarium_engine", 125, "Normal"), Types.MAGMARIUM);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 0), Modules.CAPACITOR, new ProducerCapacitor("metal_paper_capacitor", 10, 20), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 1), Modules.CAPACITOR, new ProducerCapacitor("electrolyte_niobium_capacitor", 20, 30), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 2), Modules.CAPACITOR, new ProducerCapacitor("electrolyte_tantalum_capacitor", 25, 40), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Capacitor.item(), 1, 3), Modules.CAPACITOR, new ProducerCapacitor("double_layer_capacitor", 40, 60), Types.BRONZE);
	}

	public static void registerManagers() {
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.MANAGER_TANK, new ProducerTankManager(1), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.MANAGER_TANK, new ProducerTankManager(1), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.MANAGER_TANK, new ProducerTankManager(2), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.MANAGER_STORAGE, new ProducerStorageManager(1), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.MANAGER_STORAGE, new ProducerStorageManager(1), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.MANAGER_STORAGE, new ProducerStorageManager(2), Types.IRON, true)));
	}

	public static void registerCasings() {
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.log, 1, 0), Modules.CASING, Types.WOOD);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.log, 1, 1), Modules.CASING, Types.WOOD);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.log, 1, 2), Modules.CASING, Types.WOOD);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.log, 1, 3), Modules.CASING, Types.WOOD);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.log2, 1, 0), Modules.CASING, Types.WOOD);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.log2, 1, 1), Modules.CASING, Types.WOOD);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.stone), Modules.CASING, Types.STONE);
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.iron_block), Modules.CASING, Types.IRON);
	}

	public static void registerMachines() {
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.FURNACE, new ProducerFurnace("Stone"), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.FURNACE, new ProducerFurnace("Iron"), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.FURNACE, new ProducerFurnace("Bronze"), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.ALLOYSMELTER,new ProducerAlloySmelter("Stone", 350), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.ALLOYSMELTER, new ProducerAlloySmelter("Iron", 300), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.ALLOYSMELTER,new ProducerAlloySmelter("Bronze", 250), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.SAWMILL, new ProducerSawMill("Stone", 350), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.SAWMILL, new ProducerSawMill("Iron", 300), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.SAWMILL, new ProducerSawMill("Bronze", 250), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.PULVERIZER, new ProducerPulverizer("Stone", 350), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.PULVERIZER, new ProducerPulverizer("Iron", 300), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.PULVERIZER, new ProducerPulverizer("Bronze", 250), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.CENTRIFUGE, new ProducerCentrifuge("Stone", 350), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.CENTRIFUGE, new ProducerCentrifuge("Iron", 300), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.CENTRIFUGE, new ProducerCentrifuge("Bronze", 250), Types.BRONZE, true)));
	}

	public static void postInit() {
		ModuleRegistry.registerModule(Modules.CASING);
		ModuleRegistry.registerModule(Modules.FURNACE);
		ModuleRegistry.registerModule(Modules.BOILER);
		ModuleRegistry.registerModule(Modules.ALLOYSMELTER);
		ModuleRegistry.registerModule(Modules.FURNACE);
		ModuleRegistry.registerModule(Modules.CENTRIFUGE);
		ModuleRegistry.registerModule(Modules.PULVERIZER);
		ModuleRegistry.registerModule(Modules.SAWMILL);
		ModuleRegistry.registerModule(Modules.MANAGER_TANK);
		ModuleRegistry.registerModule(Modules.MANAGER_STORAGE);
		ModuleRegistry.registerModule(Modules.CAPACITOR);
		ModuleRegistry.registerModule(Modules.ENGINE);
		ModuleRegistry.registerModule(Modules.BATTERY);
	}

}
