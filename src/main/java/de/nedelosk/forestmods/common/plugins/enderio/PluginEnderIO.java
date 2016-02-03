package de.nedelosk.forestmods.common.plugins.enderio;

import static de.nedelosk.forestmods.api.utils.ModuleRegistry.addModuleToItem;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.plugins.APlugin;
import de.nedelosk.forestmods.api.modular.material.Materials;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasing;
import de.nedelosk.forestmods.common.modules.storage.ModuleBattery;
import de.nedelosk.forestmods.common.modules.storage.ModuleCapacitor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginEnderIO extends APlugin {

	public ItemStack chassis = GameRegistry.findItemStack(getRequiredMod(), "itemMachinePart", 1);
	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");

	@Override
	public void postInit() {
		addModuleToItem(chassis, new ModuleCasing("chassis"), Materials.IRON);
		addModuleToItem(new ItemStack(capacitorBank, 1, 1), new ModuleBattery("CapacitorBankBasic", new EnergyStorage(250000, 1000, 500)), Materials.IRON,
				true);
		addModuleToItem(new ItemStack(capacitorBank, 1, 2), new ModuleBattery("CapacitorBank", new EnergyStorage(1250000, 2500, 2500)), Materials.BRONZE, true);
		addModuleToItem(new ItemStack(capacitorBank, 1, 3), new ModuleBattery("CapacitorBankVibrant", new EnergyStorage(6250000, 12500, 12500)),
				Materials.STEEL, true);
		addModuleToItem(new ItemStack(capacitor, 1, 0), new ModuleCapacitor("CapacitorBasic", 10, 20), Materials.IRON);
		addModuleToItem(new ItemStack(capacitor, 1, 1), new ModuleCapacitor("CapacitorDoubleLayer", 20, 40), Materials.BRONZE);
		addModuleToItem(new ItemStack(capacitor, 1, 2), new ModuleCapacitor("CapacitorVibrant", 40, 60), Materials.STEEL);
		addModuleToItem(new ItemStack(tanks), new ModuleTankEnderIO("TankFluid", 16000), Materials.IRON);
		addModuleToItem(new ItemStack(tanks, 1, 1), new ModuleTankEnderIO("TankFluidPressurized", 32000), Materials.BRONZE);
	}

	@Override
	public String getRequiredMod() {
		return "EnderIO";
	}

	@Override
	public boolean getConfigOption() {
		return Config.pluginEnderIO;
	}
}
