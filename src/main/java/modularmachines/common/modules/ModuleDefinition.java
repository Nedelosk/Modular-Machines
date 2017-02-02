package modularmachines.common.modules;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.IModuleFactory;
import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.ModuleContainer;
import modularmachines.api.modules.containers.ModuleContainerCapability;
import modularmachines.api.modules.containers.ModuleContainerDamage;
import modularmachines.api.modules.containers.ModuleContainerNBT;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.modules.storages.modules.ModuleCasing;
import modularmachines.common.modules.storages.modules.ModuleDataCasing;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum ModuleDefinition implements IModuleFactory {
	CASING_WOOD(new ModuleDataCasing(EnumModuleSizes.LARGEST), "casing.wood", 0, EnumModuleSizes.LARGE){

		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleCasing(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 0), this);
		}
		
	},
	CASING_BRONZE(new ModuleDataCasing(EnumModuleSizes.LARGEST), "casing.bronze", 0, EnumModuleSizes.LARGE){

		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleCasing(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 1), this);
		}
		
	},
	CASING_IRON(new ModuleDataCasing(EnumModuleSizes.LARGEST), "casing.iron", 0, EnumModuleSizes.LARGE){

		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleCasing(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 2), this);
		}
		
	},
	CASING_STEEL(new ModuleDataCasing(EnumModuleSizes.LARGEST), "casing.steel", 0, EnumModuleSizes.LARGE){

		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleCasing(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 3), this);
		}
		
	};

	private final ModuleData data;
	
	private ModuleDefinition(ModuleData data, String registry, int complexity, EnumModuleSizes size) {
		this.data = data;
		data.setRegistryName(registry);
		data.setComplexity(complexity);
		data.setSize(size);
		data.setUnlocalizedName(registry);
		data.setFactory(this);
		GameRegistry.register(data);
	}
	
	private ModuleDefinition() {
		this.data = createData();
	}
	
	protected ModuleData createData(){
		return new ModuleData();
	}
	
	public abstract void registerContainers();
	
	public static void registerModuleContainers(){
		for(ModuleDefinition definition : values()){
			definition.registerContainers();
		}
	}
	
	public ModuleData data(){
		return data;
	}
	
	private static void register(ItemStack parent, ModuleDefinition definition){
		ModuleRegistry.registerContainer(new ModuleContainer(parent, definition.data()));
	}
	
	private static void registerCapability(ItemStack parent, ModuleDefinition definition){
		ModuleRegistry.registerContainer(new ModuleContainerCapability(parent, definition.data()));
	}
	
	private static void registerNBT(ItemStack parent, ModuleDefinition definition){
		ModuleRegistry.registerContainer(new ModuleContainerNBT(parent, definition.data()));
	}
	
	private static void registerDamage(ItemStack parent, ModuleDefinition definition){
		ModuleRegistry.registerContainer(new ModuleContainerDamage(parent, definition.data()));
	}
	
	private static void register(IModuleContainer container){
		ModuleRegistry.registerContainer(container);
	}

}
