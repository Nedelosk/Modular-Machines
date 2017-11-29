package modularmachines.common.modules;

import java.util.function.Function;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IGuiFactory;
import modularmachines.api.modules.components.IModuleComponentFactory;
import modularmachines.api.modules.components.handlers.IItemHandlerComponent;
import modularmachines.api.modules.model.DefaultProperty;
import modularmachines.api.modules.model.IModelData;
import modularmachines.api.modules.model.ModelLocationBuilder;
import modularmachines.api.modules.positions.CasingPosition;
import modularmachines.api.modules.positions.RackPosition;
import modularmachines.client.gui.modules.GuiChestModule;
import modularmachines.client.model.module.ModelData;
import modularmachines.client.model.module.ModelDataActivatable;
import modularmachines.client.model.module.ModelDataCasing;
import modularmachines.client.model.module.ModelDataDefault;
import modularmachines.client.model.module.ModelDataLargeTank;
import modularmachines.client.model.module.ModelDataModuleRack;
import modularmachines.common.ModularMachines;
import modularmachines.common.containers.ContainerChestModule;
import modularmachines.common.core.Constants;
import modularmachines.common.core.managers.ModItems;
import modularmachines.common.items.ModuleItems;
import modularmachines.common.modules.components.BoilerComponent;
import modularmachines.common.modules.components.CasingComponent;
import modularmachines.common.modules.components.FireboxComponent;
import modularmachines.common.modules.components.FuelComponent;
import modularmachines.common.modules.components.RackComponent;
import modularmachines.common.modules.components.SteamConsumerComponent;
import modularmachines.common.modules.components.WaterIntakeComponent;
import modularmachines.common.modules.components.block.BoundingBoxComponent;
import modularmachines.common.modules.components.block.FluidContainerInteraction;
import modularmachines.common.modules.data.ModuleData;
import modularmachines.common.modules.filters.ItemFliterFurnaceFuel;

public enum ModuleDefinition implements IModuleDefinition {
	CHEST(new ModuleData(), "chest", 4) {
		@Override
		protected void initData(IModuleData data) {
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(Blocks.CHEST);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataDefault.addModelData(data());
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(2.0F / 16.0F, 1.0F / 16.0F, 15.0F / 16F, 14.0F / 16.0F, 14.0F / 16.0F, 1.0F));
			IItemHandlerComponent itemHandler = factory.addItemHandler(module);
			for (int i = 0; i < 27; i++) {
				itemHandler.addSlot();
			}
			factory.addGui(module, new IGuiFactory() {
				@SideOnly(Side.CLIENT)
				@Override
				public GuiContainer createGui(InventoryPlayer inventory) {
					return new GuiChestModule(module, inventory);
				}
				
				@Override
				public Container createContainer(InventoryPlayer inventory) {
					return new ContainerChestModule(module, inventory);
				}
				
				@Override
				public void onOpenGui(EntityPlayer player) {
					if (!(player instanceof EntityPlayerMP)) {
						return;
					}
					World world = ((EntityPlayerMP) player).world;
					world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
				}
			});
		}
	},
	FURNACE(new ModuleData(), "furnace", 1) {
		@Override
		protected void initData(IModuleData data) {
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(Blocks.FURNACE);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataActivatable.addModelData(data());
		}
	},
	CASING_BRONZE("casing.bronze", 0) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(18);
			data.setPositions(CasingPosition.CENTER);
		}
		
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(ModItems.itemCasings);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.addModelData(new ModelLocationBuilder(data()).addFolder("bronze/casings"));
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
			data.setPositions(CasingPosition.CENTER);
		}
		
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(ModItems.itemCasings, 1, 1);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.addModelData(new ModelLocationBuilder(data()).addFolder("iron/casings"));
		}
		
	},
	CASING_STEEL("casing.steel", 0) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(36);
			data.setPositions(CasingPosition.CENTER);
		}
		
		@Override
		public void registerContainers() {
			registerType(new ItemStack(ModItems.itemCasings, 1, 2));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataCasing.addModelData(new ModelLocationBuilder(data()).addFolder("steel/casings"));
		}
		
	},
	MODULE_RACK_BRICK("rack.brick", 1) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(3);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(ModItems.itemModuleRack);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.addModelData(new ModelLocationBuilder(data()).addFolder("brick/module_storage"));
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new RackComponent());
		}
	},
	MODULE_RACK_BRONZE("rack.bronze", 2) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(6);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(ModItems.itemModuleRack, 1, 1);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.addModelData(new ModelLocationBuilder(data()).addFolder("bronze/module_storage"));
		}
		
	},
	MODULE_RACK_IRON("rack.iron", 3) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(12);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(ModItems.itemModuleRack, 1, 2);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.addModelData(new ModelLocationBuilder(data()).addFolder("iron/module_storage"));
		}
		
	},
	MODULE_RACK_STEEL("rack.steel", 4) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(24);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(ModItems.itemModuleRack, 1, 3);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataModuleRack.addModelData(new ModelLocationBuilder(data()).addFolder("steel/module_storage"));
		}
		
	},
	FIREBOX("firebox", 4) {
		@Override
		protected void initData(IModuleData data) {
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			IItemHandlerComponent itemHandler = factory.addItemHandler(module);
			int index = itemHandler.addSlot().setFilter(ItemFliterFurnaceFuel.INSTANCE).getIndex();
			module.addComponent(itemHandler);
			module.addComponent(new FuelComponent.Items(75, index));
			module.addComponent(new FireboxComponent(225, 8));
			factory.addBoundingBox(module, new AxisAlignedBB(2.0F / 16.0F, 2.0F / 16.0F, 15.0F / 16F, 14.0F / 16.0F, 14.0F / 16.0F, 1.0F));
		}
		
		@Override
		protected ItemStack createItemStack() {
			return ModuleItems.FIREBOX.get();
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelDataActivatable.addModelData(data);
		}
	},
	TANK_LARGE("large_tank", 4) {
		@Override
		protected void initData(IModuleData data) {
			super.initData(data);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addFluidHandler(module).addTank(50000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		protected ItemStack createItemStack() {
			return ModuleItems.LARGE_TANK.get();
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			super.registerModelData();
			ModelDataLargeTank.addModelData(data);
		}
	},
	WATER_INTAKE("water_intake", 4) {
		@Override
		protected void initData(IModuleData data) {
			super.initData(data);
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			module.addComponent(new WaterIntakeComponent());
			factory.addBoundingBox(module, new AxisAlignedBB(2.0F / 16.0F, 2.0F / 16.0F, 15.0F / 16F, 14.0F / 16.0F, 14.0F / 16.0F, 1.0F));
		}
		
		@Override
		protected ItemStack createItemStack() {
			return ModuleItems.WATER_INTAKE.get();
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			super.registerModelData();
			ModelDataDefault.addModelData(data);
		}
	},
	BOILER(new ModuleData(), "boiler", 4) {
		@Override
		protected void initData(IModuleData data) {
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			module.addComponent(new BoilerComponent());
			factory.addBoundingBox(module, new AxisAlignedBB(2.0F / 16.0F, 2.0F / 16.0F, 15.0F / 16F, 14.0F / 16.0F, 14.0F / 16.0F, 1.0F));
		}
		
		@Override
		public void registerContainers() {
			registerType(ModuleItems.BOILER.get());
		}
	},
	ENGINE(new ModuleData(RackPosition.UP, RackPosition.CENTER, RackPosition.DOWN), "engine", 4) {
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(ModItems.itemEngineSteam);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			module.addComponent(new BoundingBoxComponent(new AxisAlignedBB(3.0F / 16.0F, 10.0F / 16.0F, 15.0F / 16F, 13.0F / 16.0F, 13.0F / 16.0F, 1.0F)));
			module.addComponent(new SteamConsumerComponent());
			factory.addEnergyHandler(module, 10000);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModelData() {
			ModelData model = new ModelData();
			model.add(DefaultProperty.FIRST, new ResourceLocation(Constants.MOD_ID, "module/" + data.getRegistryName().getResourcePath() + /*(fileName != null ? fileName : "")*/"s/bronze"));
			model.add(DefaultProperty.SECOND, new ResourceLocation(Constants.MOD_ID, "module/windows/bronze"));
			data.setModel(model);
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
	
	public void registerContainers() {
		ItemStack itemStack = createItemStack();
		if (!itemStack.isEmpty()) {
			data.registerType(itemStack);
		}
	}
	
	public IModuleData data() {
		return data;
	}
	
	protected ItemStack createItemStack() {
		return ItemStack.EMPTY;
	}
	
	protected void registerType(ItemStack parent) {
		data.registerType(parent);
	}
	
	protected void registerType(IModuleType container) {
		ModuleManager.registry.registerType(container);
	}
	
	@Override
	public void addComponents(IModule module, IModuleComponentFactory factory) {
	}
	
	@SideOnly(Side.CLIENT)
	protected Function<IModuleData, IModelData> getModelFactory() {
		return ModelDataDefault::addModelData;
	}
	
	@SideOnly(Side.CLIENT)
	protected void setModel(Function<IModuleData, IModelData> modelFactory) {
		data.setModel(modelFactory.apply(data));
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModelData() {
		setModel(getModelFactory());
	}
	
	@SubscribeEvent
	public static void onModuleRegister(RegistryEvent.Register<IModuleData> event) {
		for (ModuleDefinition definition : values()) {
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
		}
	}
}
