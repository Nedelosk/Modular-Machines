package modularmachines.common.modules;

import java.util.function.Supplier;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleFactory;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.api.modules.positions.EnumRackPositions;
import modularmachines.client.model.module.ModelDataCasing;
import modularmachines.client.model.module.ModelDataDefault;
import modularmachines.client.model.module.ModelDataModuleRack;
import modularmachines.client.model.module.ModelDataWorking;
import modularmachines.common.ModularMachines;
import modularmachines.common.core.Constants;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.modules.data.ModuleData;
import modularmachines.common.modules.data.ModuleDataContainer;
import modularmachines.common.modules.data.ModuleDataContainerDamage;
import modularmachines.common.modules.heaters.ModuleHeaterBurning;
import modularmachines.common.modules.machine.boiler.ModuleBoiler;
import modularmachines.common.modules.machine.furnace.ModuleFurnace;
import modularmachines.common.modules.storages.items.ModuleChest;
import modularmachines.common.modules.storages.modules.ModuleCasing;
import modularmachines.common.modules.storages.modules.ModuleDataCasing;
import modularmachines.common.modules.storages.modules.ModuleDataRack;
import modularmachines.common.modules.storages.modules.ModuleModuleRack;
import modularmachines.common.modules.transfer.ModuleDataTransfer;
import modularmachines.common.modules.transfer.fluid.ModuleTransferFluid;
import modularmachines.common.modules.transfer.items.ModuleTransferItem;

public enum ModuleDefinition implements Supplier<Module> {
	CHEST(new ModuleDataHorizontal(), "chest", 4) {
		@Override
		public Module get() {
			return new ModuleChest();
		}
		
		@Override
		public void registerContainers() {
			register(new ItemStack(Blocks.CHEST));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataDefault.addModelData(data());
		}
	},
	
	FURNACE(new ModuleDataHorizontal(), "furnace", 1) {
		@Override
		public Module get() {
			return new ModuleFurnace(32);
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.FURNACE));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataWorking.addModelData(data());
		}
	},
	CASING_WOOD(new ModuleDataCasing(), "casing.wood", 0) {
		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(9);
		}
		
		@Override
		public Module get() {
			return new ModuleCasing();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 0));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocationBuilder(data()).addFolder("wood/casings"));
		}
		
	},
	CASING_BRONZE(new ModuleDataCasing(), "casing.bronze", 0) {
		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(18);
		}
		
		@Override
		public Module get() {
			return new ModuleCasing();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 1));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocationBuilder(data()).addFolder("bronze/casings"));
		}
		
	},
	CASING_IRON(new ModuleDataCasing(), "casing.iron", 0) {
		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(27);
		}
		
		@Override
		public Module get() {
			return new ModuleCasing();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 2));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocationBuilder(data()).addFolder("iron/casings"));
		}
		
	},
	CASING_STEEL(new ModuleDataCasing(), "casing.steel", 0) {
		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(36);
		}
		
		@Override
		public Module get() {
			return new ModuleCasing();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 3));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocationBuilder(data()).addFolder("steel/casings"));
		}
		
	},
	MODULE_RACK_WOOD(new ModuleDataRack(), "rack.wood", 1) {
		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(3);
		}
		
		@Override
		public Module get() {
			return new ModuleModuleRack();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorageLarge, 1, 0));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.initModelData(new ModelLocationBuilder(data()).addFolder("wood/module_storage"));
		}
		
	},
	MODULE_RACK_BRICK(new ModuleDataRack(), "rack.brick", 1) {
		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(3);
		}
		
		@Override
		public Module get() {
			return new ModuleModuleRack();
		}
		
		@Override
		protected void registerContainers(IModuleFactory factory, IModuleHelper helper) {
			helper.registerContainer(factory.createDamageContainer(new ItemStack(ItemManager.itemModuleStorageLarge, 1, 1), data()));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.initModelData(new ModelLocationBuilder(data()).addFolder("brick/module_storage"));
		}
		
	},
	MODULE_RACK_BRONZE(new ModuleDataRack(), "rack.bronze", 2) {
		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(6);
		}
		
		@Override
		public Module get() {
			return new ModuleModuleRack();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorageLarge, 1, 2));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.initModelData(new ModelLocationBuilder(data()).addFolder("bronze/module_storage"));
		}
		
	},
	MODULE_RACK_IRON(new ModuleDataRack(), "rack.iron", 3) {
		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(12);
		}
		
		@Override
		public Module get() {
			return new ModuleModuleRack();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorageLarge, 1, 3));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.initModelData(new ModelLocationBuilder(data()).addFolder("iron/module_storage"));
		}
		
	},
	MODULE_RACK_STEEL(new ModuleDataRack(), "rack.steel", 4) {
		@Override
		protected void initData(ModuleData data) {
			data.setAllowedComplexity(24);
		}
		
		@Override
		public Module get() {
			return new ModuleModuleRack();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorageLarge, 1, 4));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.initModelData(new ModelLocationBuilder(data()).addFolder("steel/module_storage"));
		}
		
	},
	TRANSFER_ITEM(new ModuleDataTransfer(), "transfer_item", 4) {
		@Override
		public Module get() {
			return new ModuleTransferItem();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.RAIL));
		}
	},
	TRANSFER_FLUID(new ModuleDataTransfer(), "transfer_fluid", 4) {
		@Override
		public Module get() {
			return new ModuleTransferFluid();
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.GOLDEN_RAIL));
		}
	},
	HEATER(new ModuleDataSide(), "heater", 4) {
		@Override
		public Module get() {
			return new ModuleHeaterBurning(150, 2);
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Items.BLAZE_ROD));
		}
	},
	BOILER(new ModuleDataSide(), "boiler", 4) {
		@Override
		public Module get() {
			return new ModuleBoiler(6);
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.BRICK_BLOCK));
		}
	},
	ENGINE(new ModuleData(EnumRackPositions.UP, EnumRackPositions.CENTER, EnumRackPositions.DOWN), "engine", 4) {
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemEngineSteam));
		}
		
		@Override
		public Module get() {
			return new Module();
		}
		
		@Override
		protected void initData(ModuleData data) {
			data.setWallModelLocation(new ResourceLocation(Constants.MOD_ID, "module/windows/bronze"));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataDefault.addModelData(data(), "s/bronze");
		}
	};
	
	private final ModuleData data;
	
	ModuleDefinition(ModuleData data, String registry, int complexity) {
		this.data = data;
		data.setRegistryName(registry);
		data.setComplexity(complexity);
		data.setUnlocalizedName(registry);
		data.setFactory(this);
		initData(data);
	}
	
	ModuleDefinition() {
		this.data = createData();
		this.data.setFactory(this);
		initData(data);
	}
	
	protected void initData(ModuleData data) {
		
	}
	
	protected void registerContainers(IModuleFactory factory, IModuleHelper helper) {
	}
	
	protected ModuleData createData() {
		return new ModuleData();
	}
	
	public void registerContainers() {
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModelData() {
	}
	
	@SubscribeEvent
	public static void onModuleRegister(RegistryEvent.Register<IModuleData> event) {
		for(ModuleDefinition definition : values()) {
			event.getRegistry().register(definition.data());
		}
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		ModularMachines.proxy.registerModuleModels();
	}
	
	public static void registerModuleContainers() {
		for (ModuleDefinition definition : values()) {
			definition.registerContainers();
			definition.registerContainers(ModuleManager.factory, ModuleManager.helper);
		}
	}
	
	public IModuleData data() {
		return data;
	}
	
	protected void register(ItemStack parent) {
		ModuleManager.helper.registerContainer(new ModuleDataContainer(parent, data()));
	}
	
	protected void registerDamage(ItemStack parent) {
		ModuleManager.helper.registerContainer(new ModuleDataContainerDamage(parent, data()));
	}
	
	protected void register(IModuleDataContainer container) {
		ModuleManager.helper.registerContainer(container);
	}
	
}
