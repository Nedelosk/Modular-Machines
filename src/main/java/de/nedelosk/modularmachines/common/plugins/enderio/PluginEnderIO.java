package de.nedelosk.modularmachines.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.material.EnumMaterials;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.modules.registry.ModuleContainer;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PluginEnderIO extends APlugin {

	public static final String MOD_ID = "EnderIO";
	public static final ResourceLocation capacitorBankBasicUID = new ResourceLocation(MOD_ID, "capitorbank.basic");
	public static final ResourceLocation capacitorBankUID = new ResourceLocation(MOD_ID, "capitorbank.default");
	public static final ResourceLocation capacitorBankVibrantUID = new ResourceLocation(MOD_ID, "capitorbank.vibrant");
	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");
	public static ModuleCapitorBank moduleBasicCapacitorBank;
	public static ModuleCapitorBank moduleCapacitorBank;
	public static ModuleCapitorBank moduleVibrantCapacitorBank;
	// public IModuleTank moduleTankEnderIO;

	@Override
	public void preInit() {
		moduleBasicCapacitorBank = GameRegistry.register(new ModuleCapitorBank(new EnergyStorage(1000000, 1000)));
		moduleCapacitorBank = GameRegistry.register(new ModuleCapitorBank(new EnergyStorage(15000000, 5000)));
		moduleVibrantCapacitorBank = GameRegistry.register(new ModuleCapitorBank(new EnergyStorage(25000000, 25000)));
	}
	
	@Override
	public void init() {
		// moduleTankEnderIO = ModuleRegistry.registerModule(new
		// ModuleTankEnderIO("TankEnderIO"));
	}

	@Override
	public void postInit() {
		GameRegistry.register(new ModuleContainer(moduleBasicCapacitorBank, new ItemStack(capacitorBank, 1, 1), EnumMaterials.IRON, true));
		GameRegistry.register(new ModuleContainer(moduleCapacitorBank, new ItemStack(capacitorBank, 1, 2), EnumMaterials.BRONZE, true));
		GameRegistry.register(new ModuleContainer(moduleVibrantCapacitorBank, new ItemStack(capacitorBank, 1, 3), EnumMaterials.STEEL, true));
		/*
		 * addModuleToItem(new ItemStack(capacitorBank, 1, 1),
		 * moduleCapitorBank, new ModuleBatteryType(new EnergyStorage(1000000,
		 * 1000)), Materials.IRON, true); addModuleToItem(new
		 * ItemStack(capacitorBank, 1, 2), moduleCapitorBank, new
		 * ModuleBatteryType(new EnergyStorage(5000000, 5000)),
		 * Materials.BRONZE, true); addModuleToItem(new ItemStack(capacitorBank,
		 * 1, 3), moduleCapitorBank, new ModuleBatteryType(new
		 * EnergyStorage(25000000, 25000)), Materials.STEEL, true);
		 */
		// addModuleToItem(new ItemStack(capacitor, 1, 0), new
		// ModuleCapacitor("CapacitorBasic", 10, 20), Materials.IRON);
		// addModuleToItem(new ItemStack(capacitor, 1, 1), new
		// ModuleCapacitor("CapacitorDoubleLayer", 20, 40), Materials.BRONZE);
		// addModuleToItem(new ItemStack(capacitor, 1, 2), new
		// ModuleCapacitor("CapacitorVibrant", 40, 60), Materials.STEEL);
		// addModuleToItem(new ItemStack(tanks), moduleTankEnderIO, new
		// ModuleTankType(16000), Materials.IRON);
		// addModuleToItem(new ItemStack(tanks, 1, 1), moduleTankEnderIO, new
		// ModuleTankType(32000), Materials.BRONZE);ModuleStack<IModule>
	}

	@Override
	public String getRequiredMod() {
		return MOD_ID;
	}

	@Override
	public boolean getConfigOption() {
		return Config.pluginEnderIO;
	}
}
