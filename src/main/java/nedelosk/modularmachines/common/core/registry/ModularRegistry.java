package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.modular.module.Modules;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import nedelosk.modularmachines.common.core.manager.MMItemManager;
import nedelosk.modularmachines.common.items.ItemProducers;
import nedelosk.modularmachines.common.modular.machines.modular.ModularMachine;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerCapacitor;
import nedelosk.modularmachines.common.modular.module.tool.producer.engine.ProducerEngineEnergy;
import nedelosk.modularmachines.common.modular.module.tool.producer.fluids.ProducerTankManager;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.alloysmelter.ProducerAlloySmelter;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.boiler.ProducerBurningBoiler;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.centrifuge.ProducerCentrifuge;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.generator.ProducerBurningGenerator;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.lathe.ProducerLathe;
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

		registerModular();
	}

	public static void registerModular() {
		ModuleRegistry.registerModular(ModularMachine.class, "modular.machine");
	}

	public static void registerStorage() {
		ModuleRegistry.addModuleItem(new ItemStack(Blocks.chest), Modules.CHEST, new ProducerChest("Chest", 27), Types.WOOD);
	}

	public static void registerEnergy() {
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.GENERATOR, new ProducerBurningGenerator(15, 5), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.GENERATOR, new ProducerBurningGenerator(13, 15), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.GENERATOR, new ProducerBurningGenerator(10, 45), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Engine.item(), 1, 0), Modules.ENGINE, new ProducerEngineEnergy("iron_engine", 60), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Engine.item(), 1, 1), Modules.ENGINE, new ProducerEngineEnergy("bronze_engine", 50), Types.BRONZE);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Engine.item(), 1, 2), Modules.ENGINE, new ProducerEngineEnergy("steel_engine", 45), Types.STEEL);
		ModuleRegistry.addModuleItem(new ItemStack(MMItemManager.Module_Item_Engine.item(), 1, 3), Modules.ENGINE, new ProducerEngineEnergy("magmarium_engine", 40), Types.MAGMARIUM);
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
		ModuleRegistry.addModuleItem(new ItemStack(MMBlockManager.Casings.block(), 1, 0), Modules.CASING, Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(MMBlockManager.Casings.block(), 1, 1), Modules.CASING, Types.BRONZE);
	}

	public static void registerMachines() {
		/*ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.FURNACE, new ProducerFurnace("Stone"), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.FURNACE, new ProducerFurnace("Iron"), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.FURNACE, new ProducerFurnace("Bronze"), Types.BRONZE, true)));*/
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.ALLOYSMELTER,new ProducerAlloySmelter(350), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.ALLOYSMELTER, new ProducerAlloySmelter(300), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.ALLOYSMELTER,new ProducerAlloySmelter(250), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.LATHE,new ProducerLathe(325), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.LATHE, new ProducerLathe(275), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.LATHE,new ProducerLathe(225), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.SAWMILL, new ProducerSawMill(350), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.SAWMILL, new ProducerSawMill(300), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.SAWMILL, new ProducerSawMill(250), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.PULVERIZER, new ProducerPulverizer(350), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.PULVERIZER, new ProducerPulverizer(300), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.PULVERIZER, new ProducerPulverizer(250), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.CENTRIFUGE, new ProducerCentrifuge(350), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.CENTRIFUGE, new ProducerCentrifuge(300), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.CENTRIFUGE, new ProducerCentrifuge(250), Types.BRONZE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.BOILER, new ProducerBurningBoiler(15, 100, 1000), Types.STONE, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.BOILER, new ProducerBurningBoiler(13, 250, 1500), Types.IRON, true)));
		ModuleRegistry.addModuleItem(ItemProducers.addModuleItem(new ModuleStack(null, Modules.BOILER, new ProducerBurningBoiler(10, 500, 2000), Types.BRONZE, true)));
	}

	public static void postInit() {
		ModuleRegistry.registerModule(Modules.CASING);
		ModuleRegistry.registerModule(Modules.FURNACE);
		ModuleRegistry.registerModule(Modules.ALLOYSMELTER);
		ModuleRegistry.registerModule(Modules.LATHE);
		ModuleRegistry.registerModule(Modules.GENERATOR);
		ModuleRegistry.registerModule(Modules.BOILER);
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
