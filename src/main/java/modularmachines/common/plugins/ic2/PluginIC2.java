package modularmachines.common.plugins.ic2;

import static modularmachines.api.modules.ModuleManager.register;

import net.minecraft.item.ItemStack;

import ic2.api.item.IC2Items;
import modularmachines.api.material.EnumMetalMaterials;
import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleProperties;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.containers.ModuleContainer;
import modularmachines.api.modules.containers.ModuleItemContainer;
import modularmachines.api.modules.properties.ModuleCasingProperties;
import modularmachines.api.modules.storage.energy.IModuleBatteryProperties;
import modularmachines.api.modules.storage.energy.ModuleBatteryProperties;
import modularmachines.common.core.managers.ModuleManager;
import modularmachines.common.modules.storages.ModuleBattery;
import modularmachines.common.plugins.APlugin;

public class PluginIC2 extends APlugin {

	public static ItemStack batBox;
	public static ItemStack cesu;
	public static ItemStack mfe;
	public static ItemStack mfsu;
	public static ItemStack casing;
	public static ItemStack casingAdvanced;
	public static ModuleBattery moduleBattery;
	public static IModuleBatteryProperties[] moduleBatteryProperties = new IModuleBatteryProperties[4];
	public static IModuleItemContainer[] moduleBatteryContainers = new IModuleItemContainer[4];
	public static ModuleCasingProperties[] moduleCasingProperties = new ModuleCasingProperties[2];

	@Override
	public void preInit() {
		moduleBattery = new ModuleEUBattery();
		register(moduleBattery, "battery.ic2");
	}

	@Override
	public void init() {
		batBox = IC2Items.getItem("te", "batbox");
		cesu = IC2Items.getItem("te", "cesu");
		mfe = IC2Items.getItem("te", "mfe");
		mfsu = IC2Items.getItem("te", "mfsu");
		casing = IC2Items.getItem("resource", "machine");
		casingAdvanced = IC2Items.getItem("resource", "advanced_machine");
		moduleBatteryProperties[0] = new ModuleBatteryProperties(2, 80000, 320, 1);
		moduleBatteryProperties[1] = new ModuleBatteryProperties(4, 600000, 1280, 2);
		moduleBatteryProperties[2] = new ModuleBatteryProperties(6, 8000000, 5120, 3);
		moduleBatteryProperties[3] = new ModuleBatteryProperties(8, 80000000, 20480, 4);
		moduleBatteryContainers[0] = register(new ModuleItemContainer(batBox, EnumMetalMaterials.TIN, EnumModuleSizes.LARGE, true, new ModuleContainer(moduleBattery, moduleBatteryProperties[0])), "battery.ic2.batbox");
		moduleBatteryContainers[1] = register(new ModuleItemContainer(cesu, EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE, true, new ModuleContainer(moduleBattery, moduleBatteryProperties[1])), "battery.ic2.cesu");
		moduleBatteryContainers[2] = register(new ModuleItemContainer(mfe, EnumMetalMaterials.IRON, EnumModuleSizes.LARGE, true, new ModuleContainer(moduleBattery, moduleBatteryProperties[2])), "battery.ic2.mfe");
		moduleBatteryContainers[3] = register(new ModuleItemContainer(mfsu, EnumMetalMaterials.STEEL, EnumModuleSizes.LARGE, true, new ModuleContainer(moduleBattery, moduleBatteryProperties[3])), "battery.ic2.mfsu");
		moduleCasingProperties[0] = new ModuleCasingProperties(2, 12, 550, 7.0F, 1.5F);
		moduleCasingProperties[1] = new ModuleCasingProperties(4, 20, 700, 9.0F, 2.54F);
		IModule engine = ModuleManager.moduleEngineElectric;
		IModuleProperties properties = ModuleManager.moduleEngineElectricProperties[1];
		register(new ModuleItemContainer(IC2Items.getItem("crafting", "electric_motor"), EnumMetalMaterials.IRON, EnumModuleSizes.SMALL, false, new ModuleContainer(engine, properties)));
		register(new ModuleItemContainer(casing, EnumMetalMaterials.IRON, EnumModuleSizes.LARGEST, new ModuleContainer(ModuleManager.moduleCasing, moduleCasingProperties[0])), "casing.ic2.basic");
		register(new ModuleItemContainer(casingAdvanced, EnumMetalMaterials.STEEL, EnumModuleSizes.LARGEST, new ModuleContainer(ModuleManager.moduleCasing, moduleCasingProperties[1])), "casing.ic2.advanced");
	}

	@Override
	public String getRequiredMod() {
		return "IC2";
	}
}
