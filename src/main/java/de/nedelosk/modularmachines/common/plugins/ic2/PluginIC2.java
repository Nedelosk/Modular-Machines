package de.nedelosk.modularmachines.common.plugins.ic2;

import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.storage.IModuleBatteryProperties;
import de.nedelosk.modularmachines.api.modules.storaged.storage.ModuleBatteryProperties;
import de.nedelosk.modularmachines.common.modules.storaged.storage.ModuleBattery;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PluginIC2 extends APlugin {

	public static ItemStack batBox; 
	public static ItemStack cesu; 
	public static ItemStack mfe; 
	public static ItemStack mfsu; 

	public static ModuleBattery moduleBattery;
	public static IModuleBatteryProperties[] moduleBatteryProperties = new IModuleBatteryProperties[4];
	public static IModuleContainer[] moduleBatteryContainers = new IModuleContainer[4];

	@Override
	public void preInit() {
		moduleBattery = new ModuleEUBattery();
		moduleBattery.setRegistryName(new ResourceLocation("modularmachines:battery.ic2"));
		GameRegistry.register(moduleBattery);
	}

	@Override
	public void init() {
		batBox = IC2Items.getItem("te", "batbox");
		cesu = IC2Items.getItem("te", "cesu");
		mfe = IC2Items.getItem("te", "mfe");
		mfsu = IC2Items.getItem("te", "mfsu");

		moduleBatteryProperties[0] = new ModuleBatteryProperties(2, EnumModuleSize.LARGE, 80000, 320);
		moduleBatteryProperties[1] = new ModuleBatteryProperties(4, EnumModuleSize.LARGE, 600000, 1280);
		moduleBatteryProperties[2] = new ModuleBatteryProperties(6, EnumModuleSize.LARGE, 8000000, 5120);
		moduleBatteryProperties[3] = new ModuleBatteryProperties(6, EnumModuleSize.LARGE, 80000000, 20480);

		moduleBatteryContainers[0] = GameRegistry.register(new ModuleContainer(moduleBattery, moduleBatteryProperties[0], batBox, EnumMetalMaterials.IRON, true));
		moduleBatteryContainers[1] = GameRegistry.register(new ModuleContainer(moduleBattery, moduleBatteryProperties[1], cesu, EnumMetalMaterials.BRONZE, true));
		moduleBatteryContainers[2] = GameRegistry.register(new ModuleContainer(moduleBattery, moduleBatteryProperties[2], mfe, EnumMetalMaterials.STEEL, true));
		moduleBatteryContainers[3] = GameRegistry.register(new ModuleContainer(moduleBattery, moduleBatteryProperties[3], mfsu, EnumMetalMaterials.MAGMARIUM, true));
	}

	@Override
	public String getRequiredMod() {
		return "IC2";
	}
}
