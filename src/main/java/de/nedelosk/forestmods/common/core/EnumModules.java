package de.nedelosk.forestmods.common.core;

import de.nedelosk.forestmods.api.material.EnumMaterials;
import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.modules.ModuleAlloySmelter;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasing;
import de.nedelosk.forestmods.common.modules.engine.ModuleEngine;
import net.minecraft.item.ItemStack;

public enum EnumModules {
	// CASINGS
	STONE_CASING(EnumMaterials.STONE, new ModuleUID("casings:default"), new ItemStack(BlockManager.blockCasings), ModuleCasing.class, 0, 0, 0),
	// ENGINES
	STONE_ENGINE(EnumMaterials.STONE, new ModuleUID("engins:default"), new ItemStack(ItemManager.itemEngine), ModuleEngine.class, 150),
	// Alloy Smelters
	STONE_ALLOYSMELTER(EnumMaterials.STONE, new ModuleUID("alloysmelters:default"), ModuleAlloySmelter.class) {

		@Override
		public ItemStack getStack() {
			return ModuleManager.moduleRegistry.createDefaultModuleItem(getModuleStack());
		}
	};

	private final IMaterial material;
	private final ModuleUID UID;
	private final IModule defaultModule;
	private final ItemStack defaultStack;

	EnumModules(IMaterial material, ModuleUID UID, Class<? extends IModule> moduleClass, Object... parameters) {
		this(material, UID, null, false, moduleClass, parameters);
	}

	EnumModules(IMaterial material, ModuleUID UID, boolean ignorNBT, Class<? extends IModule> moduleClass, Object... parameters) {
		this(material, UID, null, ignorNBT, moduleClass, parameters);
	}

	EnumModules(IMaterial material, ModuleUID UID, ItemStack defaultStack, Class<? extends IModule> moduleClass, Object... parameters) {
		this(material, UID, defaultStack, false, moduleClass, parameters);
	}

	EnumModules(IMaterial material, ModuleUID UID, ItemStack defaultStack, boolean ignorNBT, Class<? extends IModule> moduleClass, Object... parameters) {
		this.UID = UID;
		this.material = material;
		this.defaultStack = defaultStack;
		this.defaultModule = ModuleManager.moduleRegistry.registerModule(material, UID, moduleClass, parameters);
		ModuleManager.moduleRegistry.registerItemForModule(getStack(), getModuleStack());
	}

	public ItemStack getStack() {
		return defaultStack;
	}

	public IModule getDefaultModule() {
		return defaultModule;
	}

	public ModuleStack getModuleStack() {
		return new ModuleStack(UID, material);
	}

	public static void init() {
	}
}
