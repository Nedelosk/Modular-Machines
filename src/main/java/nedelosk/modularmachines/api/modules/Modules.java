package nedelosk.modularmachines.api.modules;

import nedelosk.modularmachines.api.modules.basic.ModuleBasic;
import nedelosk.modularmachines.api.modules.casing.ModuleCasing;
import nedelosk.modularmachines.api.modules.machines.ModuleMachine;

public class Modules {

	public static IModule MANAGER_TANK = new ModuleBasic("TankManager", "TankManager");
	public static IModule MANAGER_STORAGE = new ModuleBasic("StorageManager", "StorageManager");

	public static IModule CASING = new ModuleCasing("Casing");
	public static IModule TANK = new ModuleBasic("Tank", "Tank");
	public static IModule BATTERY = new ModuleBasic("Battery", "Battery");
	public static IModule CAPACITOR = new ModuleBasic("Capacitor", "Capacitor");
	public static IModule ENGINE = new ModuleBasic("Engine", "Engine");
	public static IModule CHEST = new ModuleBasic("Chest", "Storage");
	public static IModule FURNACE = new ModuleMachine("Furnarce");
	public static IModule ALLOYSMELTER = new ModuleMachine("AlloySmelter");
	public static IModule LATHE = new ModuleMachine("Lathe");
	public static IModule SAWMILL = new ModuleMachine("SawMill");
	public static IModule PULVERIZER = new ModuleMachine("Pulverizer");
	public static IModule CENTRIFUGE = new ModuleMachine("Centrifuge");
	public static IModule GENERATOR = new ModuleMachine("Generator");
	public static IModule BOILER = new ModuleMachine("Boiler");
}
