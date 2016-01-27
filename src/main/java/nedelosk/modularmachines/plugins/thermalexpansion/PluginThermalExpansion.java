package nedelosk.modularmachines.plugins.thermalexpansion;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestcore.library.plugins.APlugin;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.type.Materials;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.energy.ModuleBattery;
import nedelosk.modularmachines.api.modules.energy.ModuleCapacitor;
import nedelosk.modularmachines.api.modules.fluids.IModuleTank;
import nedelosk.modularmachines.api.modules.fluids.ModuleTank;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.common.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class PluginThermalExpansion extends APlugin {

	public static IModule STRONGBOX = new ModuleBasic("Storage", "Storage");
	public Item cell;
	public Item frame;
	public Item tank;
	public Item strongBox;
	public Item capacitor;

	@Override
	public void init() {
		cell = GameRegistry.findItem(getRequiredMod(), "Cell");
		frame = GameRegistry.findItem(getRequiredMod(), "Frame");
		tank = GameRegistry.findItem(getRequiredMod(), "Tank");
		strongBox = GameRegistry.findItem(getRequiredMod(), "Strongbox");
		capacitor = GameRegistry.findItem(getRequiredMod(), "capacitor");
		registerProducer(new ItemStack(frame, 1, 0), Modules.CASING, Materials.IRON);
		registerProducer(new ItemStack(frame, 1, 1), Modules.CASING, Materials.Electrum);
		registerProducer(new ItemStack(frame, 1, 2), Modules.CASING, Materials.Signalum);
		registerProducer(new ItemStack(frame, 1, 3), Modules.CASING, Materials.Enderium);
		registerProducer(new ItemStack(tank, 1, 1), Modules.TANK, new ProducerTankTE("TankIron", 8000), Materials.IRON);
		registerProducer(new ItemStack(tank, 1, 2), Modules.TANK, new ProducerTankTE("TankInvar", 32000), Materials.Invar);
		registerProducer(new ItemStack(tank, 1, 3), Modules.TANK, new ProducerTankTE("TankObsidian", 128000), Materials.OBSIDIAN);
		registerProducer(new ItemStack(tank, 1, 4), Modules.TANK, new ProducerTankTE("TankEnderium", 512000), Materials.Enderium);
		registerProducer(new ItemStack(cell, 1, 1), Modules.BATTERY, new ModuleBattery("EnergyCellLeadstone", new EnergyStorage(100000, 100, 100)),
				Materials.Lead);
		registerProducer(new ItemStack(cell, 1, 2), Modules.BATTERY, new ModuleBattery("EnergyCellHardened", new EnergyStorage(500000, 400, 400)),
				Materials.Invar);
		registerProducer(new ItemStack(cell, 1, 3), Modules.BATTERY, new ModuleBattery("EnergyCellRedstone", new EnergyStorage(5000000, 4000, 4000)),
				Materials.Electrum);
		registerProducer(new ItemStack(cell, 1, 4), Modules.BATTERY, new ModuleBattery("EnergyCellResonant", new EnergyStorage(20000000, 16000, 16000)),
				Materials.Enderium);
		registerProducer(new ItemStack(strongBox, 1, 1), STRONGBOX, new ModuleSimpleChest("StrongBox", 18), Materials.IRON);
		registerProducer(new ItemStack(strongBox, 1, 2), STRONGBOX, new ModuleSimpleChest("StrongBoxHardende", 36), Materials.Invar);
		registerProducer(new ItemStack(strongBox, 1, 3), STRONGBOX, new ModuleSimpleChest("StrongBoxReinforced", 54), Materials.OBSIDIAN);
		registerProducer(new ItemStack(strongBox, 1, 4), STRONGBOX, new ModuleSimpleChest("StrongBoxResonant", 72), Materials.Enderium);
		registerProducer(new ItemStack(capacitor, 1, 1), Modules.CAPACITOR, new ModuleCapacitor("CapacitorWood", 7, 15), Materials.WOOD);
		registerProducer(new ItemStack(capacitor, 1, 2), Modules.CAPACITOR, new ModuleCapacitor("CapacitorLead", 10, 20), Materials.Lead);
		registerProducer(new ItemStack(capacitor, 1, 3), Modules.CAPACITOR, new ModuleCapacitor("CapacitorInvar", 15, 30), Materials.Invar);
		registerProducer(new ItemStack(capacitor, 1, 4), Modules.CAPACITOR, new ModuleCapacitor("CapacitorElectrum", 20, 40), Materials.Electrum);
		registerProducer(new ItemStack(capacitor, 1, 5), Modules.CAPACITOR, new ModuleCapacitor("CapacitorEnderium", 40, 80), Materials.Enderium);
	}

	@Override
	public String getRequiredMod() {
		return "ThermalExpansion";
	}

	@Override
	public boolean getConfigOption() {
		return Config.pluginThermalExpansion;
	}

	private static class ProducerTankTE extends ModuleTank {

		public ProducerTankTE(String modifier, int capacity) {
			super(modifier, capacity);
		}

		public ProducerTankTE(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
			super(nbt, modular, stack);
		}

		@Override
		public void setStorageFluid(FluidStack stack, ModuleStack<IModule, IModuleTank> moduleStack, ItemStack itemStack) {
			if (stack != null) {
				NBTTagCompound nbtTag = new NBTTagCompound();
				NBTTagCompound fluidTag = new NBTTagCompound();
				stack.writeToNBT(fluidTag);
				nbtTag.setTag("Fluid", fluidTag);
				itemStack.setTagCompound(nbtTag);
			}
		}

		@Override
		public FluidStack getStorageFluid(ModuleStack<IModule, IModuleTank> moduleStack, ItemStack itemStack) {
			if (!itemStack.hasTagCompound()) {
				return null;
			}
			if (!itemStack.getTagCompound().hasKey("Fluid")) {
				return null;
			}
			NBTTagCompound fluidTag = itemStack.getTagCompound().getCompoundTag("Fluid");
			return FluidStack.loadFluidStackFromNBT(fluidTag);
		}
	}
}
