package modularmachines.common.modules;

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

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleDataBuilder;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleModelRegistry;
import modularmachines.api.modules.IModuleRegistry;
import modularmachines.api.modules.components.IGuiFactory;
import modularmachines.api.modules.components.IModuleComponentFactory;
import modularmachines.api.modules.components.handlers.IItemHandlerComponent;
import modularmachines.api.modules.positions.CasingPosition;
import modularmachines.api.modules.positions.RackPosition;
import modularmachines.client.gui.modules.GuiChestModule;
import modularmachines.client.model.module.BakeryActivatable;
import modularmachines.client.model.module.BakeryBase;
import modularmachines.client.model.module.BakeryCasing;
import modularmachines.client.model.module.BakeryLargeTank;
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
	CHEST("chest", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder builder) {
			builder.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryBase("chest"));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, new ItemStack(Blocks.CHEST));
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(2.0F / 16.0F, 1.0F / 16.0F, 15.0F / 16F, 14.0F / 16.0F, 14.0F / 16.0F, 1.0F));
			IItemHandlerComponent itemHandler = factory.addItemHandler(module);
			for (int i = 0; i < 27; i++) {
				itemHandler.addSlot();
			}
			factory.addGui(module, new IGuiFactory() {
				
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
	CASING_BRONZE("casing.bronze", 0) {
		@Override
		protected void setProperties(IModuleDataBuilder builder) {
			builder.setPositions(CasingPosition.CENTER);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryCasing("bronze"), ModuleKeyGenerators.CASING_GENERATOR);
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, new ItemStack(ModItems.itemCasings));
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.9375F, 0.9375F));
			module.addComponent(new CasingComponent());
		}
	},
	MODULE_RACK_BRICK("rack.brick", 1) {
		@Override
		protected void setProperties(IModuleDataBuilder builder) {
			builder.setPositions(CasingPosition.SIDES);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryBase(new ResourceLocation(Constants.MOD_ID, "module/rack/brick")));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, new ItemStack(ModItems.itemModuleRack));
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new RackComponent());
		}
	},
	FIREBOX("firebox", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder builder) {
			builder.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryActivatable("module/firebox_off", "module/firebox_on"), ModuleKeyGenerators.ACTIVATABLE_GENERATOR);
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, ModuleItems.FIREBOX.get());
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
	},
	TANK_LARGE("large_tank", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder builder) {
			builder.setPositions(CasingPosition.SIDES);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryLargeTank("module/tank/large_storage", "module/tank/window"), ModuleKeyGenerators.TANK_GENERATOR);
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, ModuleItems.LARGE_TANK.get());
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addFluidHandler(module).addTank(50000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
	},
	WATER_INTAKE("water_intake", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder builder) {
			builder.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryBase("module/engines/water_intake"));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, ModuleItems.WATER_INTAKE.get());
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			module.addComponent(new WaterIntakeComponent());
			factory.addBoundingBox(module, new AxisAlignedBB(2.0F / 16.0F, 2.0F / 16.0F, 15.0F / 16F, 14.0F / 16.0F, 14.0F / 16.0F, 1.0F));
		}
	},
	BOILER("boiler", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder builder) {
			builder.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryBase("module/engines/boiler"));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, ModuleItems.BOILER.get());
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			module.addComponent(new BoilerComponent());
			factory.addBoundingBox(module, new AxisAlignedBB(2.0F / 16.0F, 2.0F / 16.0F, 15.0F / 16F, 14.0F / 16.0F, 14.0F / 16.0F, 1.0F));
		}
	},
	ENGINE("engine", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder builder) {
			builder.setPositions(RackPosition.UP, RackPosition.CENTER, RackPosition.DOWN);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryBase("module/engines/bronze", "module/windows/bronze"));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, new ItemStack(ModItems.itemEngineSteam));
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			module.addComponent(new BoundingBoxComponent(new AxisAlignedBB(3.0F / 16.0F, 10.0F / 16.0F, 15.0F / 16F, 13.0F / 16.0F, 13.0F / 16.0F, 1.0F)));
			module.addComponent(new SteamConsumerComponent());
			factory.addEnergyHandler(module, 10000);
		}
	};
	protected final IModuleData data;
	
	ModuleDefinition(String registryName, int complexity) {
		IModuleDataBuilder dataBuilder = new ModuleData.Builder()
				.setRegistryName(registryName)
				.setComplexity(complexity)
				.setUnlocalizedName(registryName)
				.setDefinition(this);
		setProperties(dataBuilder);
		this.data = dataBuilder.build();
	}
	
	protected void setProperties(IModuleDataBuilder builder) {
	}
	
	public static void preInit() {
		MinecraftForge.EVENT_BUS.register(ModuleDefinition.class);
	}
	
	@SubscribeEvent
	public static void onDataRegister(RegistryEvent.Register<IModuleData> event) {
		for (ModuleDefinition definition : values()) {
			event.getRegistry().register(definition.data);
		}
	}
}
