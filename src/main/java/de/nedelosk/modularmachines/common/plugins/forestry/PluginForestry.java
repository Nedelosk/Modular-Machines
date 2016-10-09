package de.nedelosk.modularmachines.common.plugins.forestry;

import static de.nedelosk.modularmachines.api.modules.ModuleManager.register;

import de.nedelosk.modularmachines.api.material.EnumVanillaMaterials;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.containers.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.ModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.storage.module.StorageModuleProperties;
import de.nedelosk.modularmachines.common.core.Constants;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import de.nedelosk.modularmachines.common.plugins.forestry.network.PacketBeeLogicActiveModule;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PluginForestry extends APlugin {

	public static final String MOD_ID = "forestry";
	public static IModule moduleBeeHouse;
	public static IModule moduleApiary;

	@Override
	public void preInit() {
		PacketHandler.registerClientPacket(new PacketBeeLogicActiveModule());

		moduleBeeHouse = new ModuleBeeHouse("bee_house", false);
		moduleBeeHouse.setRegistryName(new ResourceLocation(Constants.MODID, "bee_house"));
		GameRegistry.register(moduleBeeHouse);

		moduleApiary = new ModuleBeeHouse("apiary", true);
		moduleApiary.setRegistryName(new ResourceLocation(Constants.MODID, "apiary"));
		GameRegistry.register(moduleApiary);
	}

	@Override
	public void init() {
		register(new ModuleItemContainer(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MOD_ID, "bee_house"))), EnumVanillaMaterials.WOOD, EnumModuleSizes.LARGE, new ModuleContainer(moduleBeeHouse, new StorageModuleProperties(1, EnumModulePositions.SIDE))));
		register(new ModuleItemContainer(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(MOD_ID, "apiary"))), EnumVanillaMaterials.WOOD, EnumModuleSizes.LARGE, new ModuleContainer(moduleApiary, new StorageModuleProperties(2, EnumModulePositions.SIDE))));
	}

	@Override
	public String getRequiredMod() {
		return "forestry";
	}
}
