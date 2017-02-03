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
import modularmachines.common.modules.machine.pulverizer.ModulePulverizer;
import modularmachines.common.modules.storages.modules.ModuleCasing;
import modularmachines.common.modules.storages.modules.ModuleDataCasing;
import modularmachines.common.modules.storages.modules.ModuleDataRack;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum ModuleDefinition implements IModuleFactory {
	CASING_WOOD(new ModuleDataCasing(EnumModuleSizes.LARGEST), "casing.wood", 0, EnumModuleSizes.LARGE){

		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(9);
		}
		
		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleCasing(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 0));
		}
		
	},
	CASING_BRONZE(new ModuleDataCasing(EnumModuleSizes.LARGEST), "casing.bronze", 0, EnumModuleSizes.NONE){

		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(18);
		}
		
		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleCasing(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 1));
		}
		
	},
	CASING_IRON(new ModuleDataCasing(EnumModuleSizes.LARGEST), "casing.iron", 0, EnumModuleSizes.NONE){

		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(27);
		}
		
		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleCasing(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 2));
		}
		
	},
	CASING_STEEL(new ModuleDataCasing(EnumModuleSizes.LARGEST), "casing.steel", 0, EnumModuleSizes.NONE){

		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(36);
		}
		
		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleCasing(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 3));
		}
		
	},
	MODULE_RACK_WOOD(new ModuleDataRack(EnumModuleSizes.LARGE), "rack.wood", 1, EnumModuleSizes.NONE){

		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(3);
		}
		
		@Override
		public Module createModule(IModuleStorage storage) {
			return new Module(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorageLarge, 1, 0));
		}
		
	},
	MODULE_RACK_BRONZE(new ModuleDataRack(EnumModuleSizes.LARGE), "rack.bronze", 2, EnumModuleSizes.NONE){

		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(6);
		}
		
		@Override
		public Module createModule(IModuleStorage storage) {
			return new Module(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorageLarge, 1, 1));
		}
		
	},
	MODULE_RACK_IRON(new ModuleDataRack(EnumModuleSizes.LARGE), "rack.iron", 3, EnumModuleSizes.NONE){

		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(12);
		}
		
		@Override
		public Module createModule(IModuleStorage storage) {
			return new Module(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorageLarge, 1, 2));
		}
		
	},
	MODULE_RACK_STEEL(new ModuleDataRack(EnumModuleSizes.LARGE), "rack.steel", 4, EnumModuleSizes.NONE){

		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(24);
		}
		
		@Override
		public Module createModule(IModuleStorage storage) {
			return new Module(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorageLarge, 1, 3));
		}
		
	},
	PULVERIZER(new ModuleDataSide(), "pulverizer", 4, EnumModuleSizes.LARGE){
		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModulePulverizer(storage, 2, 500);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.FURNACE));
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
		initData(data);
		GameRegistry.register(data);
	}
	
	private ModuleDefinition() {
		this.data = createData();
		this.data.setFactory(this);
		initData(data);
		GameRegistry.register(data);
	}
	
	protected void initData(ModuleData data){
		
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
	
	protected void register(ItemStack parent){
		ModuleRegistry.registerContainer(new ModuleContainer(parent, data()));
	}
	
	protected void registerCapability(ItemStack parent){
		ModuleRegistry.registerContainer(new ModuleContainerCapability(parent, data()));
	}
	
	protected void registerNBT(ItemStack parent){
		ModuleRegistry.registerContainer(new ModuleContainerNBT(parent, data()));
	}
	
	protected void registerDamage(ItemStack parent){
		ModuleRegistry.registerContainer(new ModuleContainerDamage(parent, data()));
	}
	
	protected void register(IModuleContainer container){
		ModuleRegistry.registerContainer(container);
	}

}
