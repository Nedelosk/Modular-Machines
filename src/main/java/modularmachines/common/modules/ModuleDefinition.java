package modularmachines.common.modules;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleFactory;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IItemHandlerComponent;
import modularmachines.api.modules.components.IModuleComponentFactory;
import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.model.DefaultProperty;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.api.modules.positions.EnumRackPositions;
import modularmachines.client.model.module.ModelData;
import modularmachines.client.model.module.ModelDataCasing;
import modularmachines.client.model.module.ModelDataDefault;
import modularmachines.client.model.module.ModelDataModuleRack;
import modularmachines.client.model.module.ModelDataWorking;
import modularmachines.common.ModularMachines;
import modularmachines.common.core.Constants;
import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.modules.components.BoundingBoxComponent;
import modularmachines.common.modules.components.CasingComponent;
import modularmachines.common.modules.components.FuelComponent;
import modularmachines.common.modules.components.HeaterComponent;
import modularmachines.common.modules.components.RackComponent;
import modularmachines.common.modules.data.ModuleData;
import modularmachines.common.modules.data.ModuleDataContainer;
import modularmachines.common.modules.data.ModuleDataContainerDamage;
import modularmachines.common.modules.filters.ItemFliterFurnaceFuel;

public enum ModuleDefinition implements IModuleDefinition {
	CHEST(new ModuleDataHorizontal(), "chest", 4) {
		
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
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.FURNACE));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataWorking.addModelData(data());
		}
	},
	CASING_BRONZE("casing.bronze", 0) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(18);
			data.setPositions(EnumCasingPositions.CENTER);
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocationBuilder(data()).addFolder("bronze/casings"));
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.9375F, 0.9375F));
			module.addComponent(new CasingComponent());
		}
	},
	CASING_IRON("casing.iron", 0) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(27);
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 1));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocationBuilder(data()).addFolder("iron/casings"));
		}
		
	},
	CASING_STEEL("casing.steel", 0) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(36);
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemCasings, 1, 2));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.initModelData(new ModelLocationBuilder(data()).addFolder("steel/casings"));
		}
		
	},
	MODULE_RACK_BRICK("rack.brick", 1) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(3);
			data.setPositions(EnumCasingPositions.LEFT, EnumCasingPositions.RIGHT);
		}
		
		@Override
		protected void registerContainers(IModuleFactory factory, IModuleHelper helper) {
			helper.registerContainer(factory.createDamageContainer(new ItemStack(ItemManager.itemModuleStorage), data()));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.initModelData(new ModelLocationBuilder(data()).addFolder("brick/module_storage"));
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			module.addComponent(new BoundingBoxComponent(new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F)));
			module.addComponent(new RackComponent());
		}
	},
	MODULE_RACK_BRONZE("rack.bronze", 2) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(6);
			data.setPositions(EnumCasingPositions.LEFT, EnumCasingPositions.RIGHT);
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorage, 1, 1));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.initModelData(new ModelLocationBuilder(data()).addFolder("bronze/module_storage"));
		}
		
	},
	MODULE_RACK_IRON("rack.iron", 3) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(12);
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorage, 1, 2));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.initModelData(new ModelLocationBuilder(data()).addFolder("iron/module_storage"));
		}
		
	},
	MODULE_RACK_STEEL("rack.steel", 4) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(24);
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(ItemManager.itemModuleStorage, 1, 3));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.initModelData(new ModelLocationBuilder(data()).addFolder("steel/module_storage"));
		}
		
	},
	TRANSFER_ITEM("transfer_item", 4) {
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.RAIL));
		}
	},
	TRANSFER_FLUID("transfer_fluid", 4) {
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Blocks.GOLDEN_RAIL));
		}
	},
	HEATER(new ModuleDataSide(), "heater", 4) {
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			IItemHandlerComponent itemHandler = factory.addItemHandler(module);
			int index = itemHandler.addSlot().setFilter(ItemFliterFurnaceFuel.INSTANCE).getIndex();
			module.addComponent(itemHandler);
			module.addComponent(new FuelComponent.Items(25, index));
			module.addComponent(new HeaterComponent(150, 2));
		}
		
		@Override
		public void registerContainers() {
			registerDamage(new ItemStack(Items.BLAZE_ROD));
		}
	},
	BOILER(new ModuleDataSide(), "boiler", 4) {
		
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
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			module.addComponent(new BoundingBoxComponent(new AxisAlignedBB(3.0F / 16.0F, 10.0F / 16.0F, 15.0F / 16F, 13.0F / 16.0F, 13.0F / 16.0F, 1.0F)));
		}
		
		@Override
		protected void initData(IModuleData data) {
			data.setWallModelLocation(new ResourceLocation(Constants.MOD_ID, "module/windows/bronze"));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			//	ModelDataDefault.addModelData(data(), "s/bronze");
			ModelData model = new ModelData();
			model.add(DefaultProperty.FIRST, new ResourceLocation(Constants.MOD_ID, "module/" + data.getRegistryName().getResourcePath() + /*(fileName != null ? fileName : "")*/"s/bronze"));
			if (data.getWallModelLocation() != null) {
				model.add(DefaultProperty.SECOND, data.getWallModelLocation());
			}
			data.setModel(model);
		}
	},
	ENERGY_INPUT("energy_input", 4) {
		@Override
		protected void initData(IModuleData data) {
			super.initData(data);
			data.setPositions(EnumCasingPositions.LEFT, EnumCasingPositions.RIGHT, EnumCasingPositions.BACK, EnumCasingPositions.FRONT);
		}
		
		@Override
		protected void registerContainers(IModuleFactory factory, IModuleHelper helper) {
			helper.registerContainer(factory.createContainer(new ItemStack(Items.APPLE), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			super.registerModelData();
			ModelDataDefault.addModelData(data);
		}
	};
	
	protected final IModuleData data;
	
	ModuleDefinition(String name, int complexity) {
		this(ModuleManager.factory.createData(), name, complexity);
	}
	
	ModuleDefinition(IModuleData data, String registry, int complexity) {
		this.data = data;
		data.setRegistryName(new ResourceLocation(Constants.MOD_ID, registry));
		data.setComplexity(complexity);
		data.setUnlocalizedName(registry);
		data.setDefinition(this);
		initData(data);
	}
	
	protected void initData(IModuleData data) {
		
	}
	
	
	protected void registerContainers(IModuleFactory factory, IModuleHelper helper) {
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
	
	@Override
	public void addComponents(IModule module, IModuleComponentFactory factory) {
	
	}
}
