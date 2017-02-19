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
import modularmachines.api.modules.model.ModelLocation;
import modularmachines.client.model.module.ModelDataCasing;
import modularmachines.client.model.module.ModelDataDefault;
import modularmachines.client.model.module.ModelDataModuleStorage;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.modules.heaters.ModuleHeaterBurning;
import modularmachines.common.modules.machine.boiler.ModuleBoiler;
import modularmachines.common.modules.storages.items.ModuleChest;
import modularmachines.common.modules.storages.items.ModuleDataChest;
import modularmachines.common.modules.storages.modules.ModuleCasing;
import modularmachines.common.modules.storages.modules.ModuleDataCasing;
import modularmachines.common.modules.storages.modules.ModuleDataRack;
import modularmachines.common.modules.transfer.items.ModuleTransferItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocation(data()).addFolder("wood/casings"));
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
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocation(data()).addFolder("bronze/casings"));
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
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocation(data()).addFolder("iron/casings"));
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
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocation(data()).addFolder("steel/casings"));
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
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleStorage.initModelData(new ModelLocation(data()).addFolder("wood/module_storage"));
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
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleStorage.initModelData(new ModelLocation(data()).addFolder("bronze/module_storage"));
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
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleStorage.initModelData(new ModelLocation(data()).addFolder("iron/module_storage"));
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
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleStorage.initModelData(new ModelLocation(data()).addFolder("steel/module_storage"));
		}
		
	},
	TRANSFER_ITEM(new ModuleDataCasingPosition(), "transfer_item", 4, EnumModuleSizes.LARGE){
		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleTransferItem(storage);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.RAIL));
		}
	},
	HEATER(new ModuleDataSide(), "heater", 4, EnumModuleSizes.LARGE){
		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleHeaterBurning(storage, 150, 2);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.FURNACE));
		}
	},
	BOILER(new ModuleDataSide(), "boiler", 4, EnumModuleSizes.LARGE){
		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleBoiler(storage, 6);
		}

		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.BRICK_BLOCK));
		}
	},
	CHEST(new ModuleDataChest(), "chest", 4, EnumModuleSizes.LARGEST){
		
		@Override
		public Module createModule(IModuleStorage storage) {
			return new ModuleChest(storage);
		}

		@Override
		public void registerContainers() {
			register(new ItemStack(Blocks.CHEST));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataDefault.initModelData(new ModelLocation(data()).addFolder("wood/chest").addSize());
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
	
	@SideOnly(Side.CLIENT)
	public void registerModelData(){
		
	}
	
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
