package de.nedelosk.modularmachines.common.plugins.thermalexpansion;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.material.EnumMaterials;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.storage.IModuleBattery;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.modules.ModuleCasing;
import de.nedelosk.modularmachines.common.modules.registry.ModuleContainer;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PluginThermalExpansion extends APlugin {

	public static Item cell;
	public static Item frame;
	// public static Item tank;
	// public static Item strongBox;
	// public static Item capacitor;
	public static final String MOD_ID = "ThermalExpansion";
	public static final ResourceLocation energyCellLeadstone = new ResourceLocation(MOD_ID, "energycell.basic");
	public static final ResourceLocation energyCellHardened = new ResourceLocation(MOD_ID, "energycell.hardened");
	public static final ResourceLocation energyCellReinforced = new ResourceLocation(MOD_ID, "energycell.reinforced");
	public static final ResourceLocation energyCellResonant = new ResourceLocation(MOD_ID, "energycell.resonant");
	public static final ResourceLocation frameBasic = new ResourceLocation(MOD_ID, "frame.leadstone");
	public static final ResourceLocation frameHardened = new ResourceLocation(MOD_ID, "frame.hardened");
	public static final ResourceLocation frameReinforced = new ResourceLocation(MOD_ID, "frame.redstone");
	public static final ResourceLocation frameResonant = new ResourceLocation(MOD_ID, "frame.resonant");
	public static IModuleBattery moduleEnergyCellLeadstone;
	public static IModuleBattery moduleEnergyCellHardened;
	public static IModuleBattery moduleEnergyCellReinforced;
	public static IModuleBattery moduleEnergyCellResonant;
	public static IModuleCasing moduleFrameBasic;
	public static IModuleCasing moduleFrameHardened;
	public static IModuleCasing moduleFrameReinforced;
	public static IModuleCasing moduleFrameResonant;
	// public static IModuleTank modulePortableTank;

	@Override
	public void preInit() {
		//Frames
		moduleFrameBasic = GameRegistry.register(new ModuleCasing(0, 0, 0, 2), frameBasic);
		moduleFrameHardened = GameRegistry.register(new ModuleCasing(0, 0, 0, 3), frameHardened);
		moduleFrameReinforced = GameRegistry.register(new ModuleCasing(0, 0, 0, 3), frameReinforced);
		moduleFrameResonant = GameRegistry.register(new ModuleCasing(0, 0, 0, 4), frameResonant);

		//Cells
		moduleEnergyCellLeadstone = GameRegistry.register(new ModuleEnergyCell(new EnergyStorage(400000, 200)), energyCellLeadstone);
		moduleEnergyCellHardened = GameRegistry.register(new ModuleEnergyCell(new EnergyStorage(2000000, 800)), energyCellHardened);
		moduleEnergyCellReinforced = GameRegistry.register(new ModuleEnergyCell(new EnergyStorage(20000000, 8000)), energyCellReinforced);
		moduleEnergyCellResonant = GameRegistry.register(new ModuleEnergyCell(new EnergyStorage(80000000, 32000)), energyCellResonant);
	}

	@Override
	public void postInit() {
		cell = ForgeRegistries.ITEMS.getValue(new ResourceLocation(MOD_ID, "Cell"));
		frame = ForgeRegistries.ITEMS.getValue(new ResourceLocation(MOD_ID, "Frame"));

		//Frames
		GameRegistry.register(new ModuleContainer(moduleFrameBasic, new ItemStack(frame, 1, 0), EnumMaterials.IRON));
		GameRegistry.register(new ModuleContainer(moduleFrameHardened, new ItemStack(frame, 1, 1), EnumMaterials.INVAR));
		GameRegistry.register(new ModuleContainer(moduleFrameReinforced, new ItemStack(frame, 1, 2), EnumMaterials.SIGNALUM));
		GameRegistry.register(new ModuleContainer(moduleFrameResonant, new ItemStack(frame, 1, 3), EnumMaterials.ENDERIUM));

		//Cells
		GameRegistry.register(new ModuleContainer(moduleEnergyCellLeadstone, new ItemStack(cell, 1, 1), EnumMaterials.LEAD, true));
		GameRegistry.register(new ModuleContainer(moduleEnergyCellHardened, new ItemStack(cell, 1, 2), EnumMaterials.LEAD, true));
		GameRegistry.register(new ModuleContainer(moduleEnergyCellReinforced, new ItemStack(cell, 1, 3), EnumMaterials.LEAD, true));
		GameRegistry.register(new ModuleContainer(moduleEnergyCellResonant, new ItemStack(cell, 1, 4), EnumMaterials.LEAD, true));
		// tank = GameRegistry.findItem(getRequiredMod(), "Tank");
		// strongBox = GameRegistry.findItem(getRequiredMod(), "Strongbox");
		// capacitor = GameRegistry.findItem(getRequiredMod(), "capacitor");
		/*
		 * registerItemForModule(new ItemStack(tank, 1, 1), modulePortableTank,
		 * new ModuleTankType(8000), Materials.IRON); registerItemForModule(new
		 * ItemStack(tank, 1, 2), modulePortableTank, new ModuleTankType(32000),
		 * Materials.Invar); registerItemForModule(new ItemStack(tank, 1, 3),
		 * modulePortableTank, new ModuleTankType(128000), Materials.OBSIDIAN);
		 * registerItemForModule(new ItemStack(tank, 1, 4), modulePortableTank,
		 * new ModuleTankType(512000), Materials.Enderium);
		 */
		// registerProducer(new ItemStack(strongBox, 1, 1), STRONGBOX, new
		// ModuleSimpleChest("StrongBox", 18), Materials.IRON);
		// registerProducer(new ItemStack(strongBox, 1, 2), STRONGBOX, new
		// ModuleSimpleChest("StrongBoxHardende", 36), Materials.Invar);
		// registerProducer(new ItemStack(strongBox, 1, 3), STRONGBOX, new
		// ModuleSimpleChest("StrongBoxReinforced", 54), Materials.OBSIDIAN);
		// registerProducer(new ItemStack(strongBox, 1, 4), STRONGBOX, new
		// ModuleSimpleChest("StrongBoxResonant", 72), Materials.Enderium);
		// addModuleToItem(new ItemStack(capacitor, 1, 1), new
		// ModuleCapacitor("CapacitorWood", 7, 15), Materials.WOOD);
		// addModuleToItem(new ItemStack(capacitor, 1, 2), new
		// ModuleCapacitor("CapacitorLead", 10, 20), Materials.Lead);
		// addModuleToItem(new ItemStack(capacitor, 1, 3), new
		// ModuleCapacitor("CapacitorInvar", 15, 30), Materials.Invar);
		// addModuleToItem(new ItemStack(capacitor, 1, 4), new
		// ModuleCapacitor("CapacitorElectrum", 20, 40), Materials.Electrum);
		// addModuleToItem(new ItemStack(capacitor, 1, 5), new
		// ModuleCapacitor("CapacitorEnderium", 40, 80), Materials.Enderium);
	}

	@Override
	public String getRequiredMod() {
		return MOD_ID;
	}

	@Override
	public boolean getConfigOption() {
		return Config.pluginThermalExpansion;
	}
}
