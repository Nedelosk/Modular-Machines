package de.nedelosk.forestmods.common.core;

import de.nedelosk.forestmods.api.ForestModsApi;
import de.nedelosk.forestmods.api.material.EnumMaterials;
import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.IModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.ModuleAlloySmelter;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasing;
import de.nedelosk.forestmods.common.modules.engine.ModuleEngine;
import net.minecraft.item.ItemStack;

public enum EnumModules {
	STONE_CASING(EnumMaterials.STONE, "casings", new ModuleCasing("default", 500, 500, 500), new ItemStack(BlockManager.blockCasings)), STONE_ENGINE(
			EnumMaterials.STONE, "engins", new ModuleEngine("default", 150),
			new ItemStack(ItemManager.itemEngine)), STONE_ALLOYSMELTER(EnumMaterials.STONE, "alloysmelters", new ModuleAlloySmelter("default")) {

				@Override
				public ItemStack getStack() {
					return ForestModsApi.handler.addModuleToModuelItem(getModuleStack());
				}
			};

	private IModuleRegistry moduleRegistry = de.nedelosk.forestmods.api.utils.ModuleManager.moduleRegistry;
	private final IMaterial material;
	private final String category;
	private final IModule module;
	private final ItemStack defaultStack;

	EnumModules(IMaterial material, String category, IModule module) {
		this(material, category, module, null);
	}

	EnumModules(IMaterial material, String category, IModule module, ItemStack defaultStack) {
		this.category = category;
		this.material = material;
		this.module = module;
		this.defaultStack = defaultStack;
		moduleRegistry.registerModule(material, category, module);
		moduleRegistry.registerItemForModule(getStack(), getModuleStack());
	}

	public ItemStack getStack() {
		return defaultStack;
	}

	public IModule getModule() {
		return module;
	}

	public ModuleStack getModuleStack() {
		return new ModuleStack(category, material, module);
	}

	public static void init() {
	}
}
