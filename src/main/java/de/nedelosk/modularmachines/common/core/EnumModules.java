package de.nedelosk.modularmachines.common.core;

import java.lang.reflect.InvocationTargetException;

import de.nedelosk.modularmachines.api.material.EnumMaterials;
import de.nedelosk.modularmachines.api.material.IMaterial;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.common.modules.ModuleCasing;
import de.nedelosk.modularmachines.common.modules.engine.ModuleEngine;
import de.nedelosk.modularmachines.common.modules.heater.ModuleHeaterBurning;
import de.nedelosk.modularmachines.common.modules.registry.ModuleContainer;
import de.nedelosk.modularmachines.common.modules.tools.ModuleAlloySmelter;
import de.nedelosk.modularmachines.common.modules.tools.ModulePulverizer;
import de.nedelosk.modularmachines.common.modules.tools.ModuleSawMill;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;

public enum EnumModules {
	// CASINGS
	STONE_CASING(EnumMaterials.STONE, "casings:default", new ItemStack(BlockManager.blockCasings), ModuleCasing.class, ModuleContainer.class, 0, 0, 0, 2),
	IRON_CASING(EnumMaterials.IRON, new ModuleUID("casings:default"), new ItemStack(BlockManager.blockCasings, 1, 2), ModuleCasing.class, ModuleContainer.class, 0, 0, 0, 2),
	BRONZE_CASING(EnumMaterials.BRONZE, new ModuleUID("casings:default"), new ItemStack(BlockManager.blockCasings, 1, 3), ModuleCasing.class, ModuleContainer.class, 0, 0, 0, 2),
	// ENGINES
	STONE_ENGINE(EnumMaterials.STONE, new ModuleUID("engins:default"), new ItemStack(ItemManager.itemEngine, 1, 0), ModuleEngine.class, ModuleContainer.class, 1),
	IRON_ENGINE(EnumMaterials.IRON, new ModuleUID("engins:default"), new ItemStack(ItemManager.itemEngine, 1, 1), ModuleEngine.class, ModuleContainer.class, 2),
	BRONZE_ENGINE(EnumMaterials.BRONZE, new ModuleUID("engins:default"), new ItemStack(ItemManager.itemEngine, 1, 2), ModuleEngine.class, ModuleContainer.class, 3),
	STEEL_ENGINE(EnumMaterials.STEEL, new ModuleUID("engins:default"), new ItemStack(ItemManager.itemEngine, 1, 3), ModuleEngine.class, ModuleContainer.class, 4),
	MAGMARIUM_ENGINE(EnumMaterials.MAGMARIUM, new ModuleUID("engins:default"), new ItemStack(ItemManager.itemEngine, 1, 4), ModuleEngine.class, ModuleContainer.class, 5),
	// HEATERS
	STONE_HEATER(EnumMaterials.STONE, new ModuleUID("heaters:default"), new ItemStack(ItemManager.itemHeater, 1, 0), ModuleHeaterBurning.class, ModuleContainer.class, 250),
	IRON_HEATER(EnumMaterials.IRON, new ModuleUID("heaters:default"), new ItemStack(ItemManager.itemHeater, 1, 1), ModuleHeaterBurning.class, ModuleContainer.class, 175),
	BRONZE_HEATER(EnumMaterials.BRONZE, new ModuleUID("heaters:default"), new ItemStack(ItemManager.itemHeater, 1, 2), ModuleHeaterBurning.class, ModuleContainer.class, 150),
	STEEL_HEATER(EnumMaterials.STEEL, new ModuleUID("heaters:default"), new ItemStack(ItemManager.itemHeater, 1, 3), ModuleHeaterBurning.class, ModuleContainer.class, 100),
	MAGMARIUM_HEATER(EnumMaterials.MAGMARIUM, new ModuleUID("heaters:default"), new ItemStack(ItemManager.itemHeater, 1, 4), ModuleHeaterBurning.class, ModuleContainer.class, 50),
	// Alloy Smelters
	STONE_ALLOYSMELTER(EnumMaterials.STONE, new ModuleUID("alloysmelters:default"), ModuleAlloySmelter.class, ModuleContainer.class, 7, 1) {

		@Override
		public ItemStack getStack() {
			return ModuleManager.moduleRegistry.createDefaultModuleItem(getModuleContainer());
		}
	},
	// Saw Mill
	STONE_SAW_MILL(EnumMaterials.STONE, new ModuleUID("saw_mill:default"), ModuleSawMill.class, ModuleContainer.class, 7, 1) {

		@Override
		public ItemStack getStack() {
			return ModuleManager.moduleRegistry.createDefaultModuleItem(getModuleContainer());
		}
	},
	// Pulverizer
	STONE_PULVERIZER(EnumMaterials.STONE, new ModuleUID("pulverizer:default"), ModulePulverizer.class, ModuleContainer.class, 7, 1) {

		@Override
		public ItemStack getStack() {
			return ModuleManager.moduleRegistry.createDefaultModuleItem(getModuleContainer());
		}
	};

	private final IMaterial material;
	private final ModuleUID UID;
	private IModuleContainer container;
	private final ItemStack defaultStack;

	EnumModules(IMaterial material, ModuleUID UID, Class<? extends IModule> moduleClass, Class<? extends IModuleContainer> containerClass, Object... parameters) {
		this(material, UID, null, false, moduleClass, containerClass, parameters);
	}

	EnumModules(IMaterial material, ModuleUID UID, boolean ignorNBT, Class<? extends IModule> moduleClass, Class<? extends IModuleContainer> containerClass, Object... parameters) {
		this(material, UID, null, ignorNBT, moduleClass, containerClass, parameters);
	}

	EnumModules(IMaterial material, ModuleUID UID, ItemStack defaultStack, Class<? extends IModule> moduleClass, Class<? extends IModuleContainer> containerClass, Object... parameters) {
		this(material, UID, defaultStack, false, moduleClass, containerClass, parameters);
	}

	EnumModules(IMaterial material, ModuleUID UID, ItemStack defaultStack, boolean ignorNBT, Class<? extends IModule> moduleClass, Class<? extends IModuleContainer> containerClass, Object... parameters) {
		this.UID = UID;
		this.material = material;
		this.defaultStack = defaultStack;
		try {
			this.container = containerClass.getConstructor(ItemStack.class, ModuleUID.class, IMaterial.class, Class.class, boolean.class).newInstance(defaultStack, UID, material, moduleClass, ignorNBT);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			FMLCommonHandler.instance().raiseException(e, "", true);
			this.container = null;
		}
		ModuleManager.moduleRegistry.registerModule(material, UID, moduleClass, parameters);
		ItemStack stack = getStack();
		getModuleContainer().setItemStack(stack);
		ModuleManager.moduleRegistry.registerModuleContainer(stack, getModuleContainer());
	}

	public ItemStack getStack() {
		return defaultStack;
	}

	public IModuleContainer getModuleContainer() {
		return container;
	}

	public static void init() {
	}
}
