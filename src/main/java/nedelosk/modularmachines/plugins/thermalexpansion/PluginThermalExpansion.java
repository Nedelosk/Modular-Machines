package nedelosk.modularmachines.plugins.thermalexpansion;

import static nedelosk.modularmachines.api.utils.ModuleRegistry.registerProducer;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestcore.library.plugins.APlugin;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.Modules;
import nedelosk.modularmachines.api.modules.basic.ModuleBasic;
import nedelosk.modularmachines.api.producers.energy.ProducerBattery;
import nedelosk.modularmachines.api.producers.energy.ProducerCapacitor;
import nedelosk.modularmachines.api.producers.fluids.IProducerTank;
import nedelosk.modularmachines.api.producers.fluids.ProducerTank;
import nedelosk.modularmachines.api.producers.storage.ProducerChest;
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
		registerProducer(new ItemStack(frame, 1, 0), Modules.CASING, Types.IRON);
		registerProducer(new ItemStack(frame, 1, 1), Modules.CASING, Types.Electrum);
		registerProducer(new ItemStack(frame, 1, 2), Modules.CASING, Types.Signalum);
		registerProducer(new ItemStack(frame, 1, 3), Modules.CASING, Types.Enderium);
		registerProducer(new ItemStack(tank, 1, 1), Modules.TANK, new ProducerTankTE("TankIron", 8000), Types.IRON);
		registerProducer(new ItemStack(tank, 1, 2), Modules.TANK, new ProducerTankTE("TankInvar", 32000), Types.Invar);
		registerProducer(new ItemStack(tank, 1, 3), Modules.TANK, new ProducerTankTE("TankObsidian", 128000), Types.OBSIDIAN);
		registerProducer(new ItemStack(tank, 1, 4), Modules.TANK, new ProducerTankTE("TankEnderium", 512000), Types.Enderium);
		registerProducer(new ItemStack(cell, 1, 1), Modules.BATTERY, new ProducerBattery("EnergyCellLeadstone", new EnergyStorage(100000, 100, 100)),
				Types.Lead);
		registerProducer(new ItemStack(cell, 1, 2), Modules.BATTERY, new ProducerBattery("EnergyCellHardened", new EnergyStorage(500000, 400, 400)),
				Types.Invar);
		registerProducer(new ItemStack(cell, 1, 3), Modules.BATTERY, new ProducerBattery("EnergyCellRedstone", new EnergyStorage(5000000, 4000, 4000)),
				Types.Electrum);
		registerProducer(new ItemStack(cell, 1, 4), Modules.BATTERY, new ProducerBattery("EnergyCellResonant", new EnergyStorage(20000000, 16000, 16000)),
				Types.Enderium);
		registerProducer(new ItemStack(strongBox, 1, 1), STRONGBOX, new ProducerChest("StrongBox", 18), Types.IRON);
		registerProducer(new ItemStack(strongBox, 1, 2), STRONGBOX, new ProducerChest("StrongBoxHardende", 36), Types.Invar);
		registerProducer(new ItemStack(strongBox, 1, 3), STRONGBOX, new ProducerChest("StrongBoxReinforced", 54), Types.OBSIDIAN);
		registerProducer(new ItemStack(strongBox, 1, 4), STRONGBOX, new ProducerChest("StrongBoxResonant", 72), Types.Enderium);
		registerProducer(new ItemStack(capacitor, 1, 1), Modules.CAPACITOR, new ProducerCapacitor("CapacitorWood", 7, 15), Types.WOOD);
		registerProducer(new ItemStack(capacitor, 1, 2), Modules.CAPACITOR, new ProducerCapacitor("CapacitorLead", 10, 20), Types.Lead);
		registerProducer(new ItemStack(capacitor, 1, 3), Modules.CAPACITOR, new ProducerCapacitor("CapacitorInvar", 15, 30), Types.Invar);
		registerProducer(new ItemStack(capacitor, 1, 4), Modules.CAPACITOR, new ProducerCapacitor("CapacitorElectrum", 20, 40), Types.Electrum);
		registerProducer(new ItemStack(capacitor, 1, 5), Modules.CAPACITOR, new ProducerCapacitor("CapacitorEnderium", 40, 80), Types.Enderium);
	}

	@Override
	public String getRequiredMod() {
		return "ThermalExpansion";
	}

	@Override
	public boolean getConfigOption() {
		return Config.pluginThermalExpansion;
	}

	private static class ProducerTankTE extends ProducerTank {

		public ProducerTankTE(String modifier, int capacity) {
			super(modifier, capacity);
		}

		public ProducerTankTE(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
			super(nbt, modular, stack);
		}

		@Override
		public void setStorageFluid(FluidStack stack, ModuleStack<IModule, IProducerTank> moduleStack, ItemStack itemStack) {
			if (stack != null) {
				NBTTagCompound nbtTag = new NBTTagCompound();
				NBTTagCompound fluidTag = new NBTTagCompound();
				stack.writeToNBT(fluidTag);
				nbtTag.setTag("Fluid", fluidTag);
				itemStack.setTagCompound(nbtTag);
			}
		}

		@Override
		public FluidStack getStorageFluid(ModuleStack<IModule, IProducerTank> moduleStack, ItemStack itemStack) {
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
