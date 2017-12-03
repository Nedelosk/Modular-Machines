package modularmachines.common.modules;

import javax.annotation.Nullable;

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

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleDefinition;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.components.IActivatableComponent;
import modularmachines.api.modules.components.IGuiFactory;
import modularmachines.api.modules.components.IModuleComponentFactory;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.api.modules.components.handlers.IItemHandlerComponent;
import modularmachines.api.modules.model.IModuleKeyGenerator;
import modularmachines.api.modules.model.IModuleModelBakery;
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
import modularmachines.common.modules.data.ModuleTypeNBT;
import modularmachines.common.modules.filters.ItemFliterFurnaceFuel;
import modularmachines.common.utils.Mod;
import modularmachines.common.utils.NBTUtil;

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
	/*FURNACE(new ModuleData(), "furnace", 1) {
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
	},*/
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
		protected IModuleModelBakery createBakery() {
			return new BakeryCasing("bronze");
		}
		
		@Override
		protected IModuleKeyGenerator getGenerator() {
			return CASING_GENERATOR;
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.9375F, 0.9375F));
			module.addComponent(new CasingComponent());
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
		protected IModuleModelBakery createBakery() {
			return new BakeryBase(new ResourceLocation(Constants.MOD_ID, "module/rack/brick"));
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new RackComponent());
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
		
		@Override
		protected boolean isActivatable() {
			return true;
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
		protected IModuleModelBakery createBakery() {
			return new BakeryLargeTank(new ResourceLocation(Constants.MOD_ID, "module/tank/large_storage"),
					new ResourceLocation(Constants.MOD_ID, "module/tank/window"));
		}
		
		@Override
		protected IModuleKeyGenerator getGenerator() {
			return TANK_GENERATOR;
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
		protected IModuleModelBakery createBakery() {
			return new BakeryBase(new ResourceLocation(Constants.MOD_ID, "module/engines/bronze"),
					new ResourceLocation(Constants.MOD_ID, "module/windows/bronze"));
		}
	},
	//Thermal Expansion
	MACHINE_FRAME("machine_frame", 0) {
		@Override
		protected void initData(IModuleData data) {
			data.setAllowedComplexity(18);
			data.setPositions(CasingPosition.CENTER);
		}
		
		@Override
		protected ItemStack createItemStack() {
			return new ItemStack(Mod.THERMAL_EXPANSION.getItem("frame"));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		protected IModuleModelBakery createBakery() {
			return new BakeryCasing("machine_frame");
		}
		
		@Override
		protected IModuleKeyGenerator getGenerator() {
			return CASING_GENERATOR;
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.9375F, 0.9375F));
			module.addComponent(new CasingComponent());
		}
		
		@Override
		protected Mod getRequiredMod() {
			return Mod.THERMAL_EXPANSION;
		}
	},
	PORTABLE_BASIC("portable_basic", 4) {
		@Override
		protected void initData(IModuleData data) {
			super.initData(data);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addFluidHandler(module).addTank(20000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		protected Mod getRequiredMod() {
			return Mod.THERMAL_EXPANSION;
		}
		
		@Override
		protected IModuleType createCustomType() {
			return new ModuleTypeNBT(NBTUtil.setByte(new ItemStack(Mod.THERMAL_EXPANSION.getItem("tank")), "Level", (byte) 0), data);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		protected IModuleModelBakery createBakery() {
			return new BakeryLargeTank(new ResourceLocation(Constants.MOD_ID, "module/tank/portable/basic"));
		}
		
		@Override
		protected IModuleKeyGenerator getGenerator() {
			return TANK_GENERATOR;
		}
	},
	PORTABLE_HARDENED("portable_hardened", 4) {
		@Override
		protected void initData(IModuleData data) {
			super.initData(data);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addFluidHandler(module).addTank(80000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		protected Mod getRequiredMod() {
			return Mod.THERMAL_EXPANSION;
		}
		
		@Override
		protected IModuleType createCustomType() {
			return new ModuleTypeNBT(NBTUtil.setByte(new ItemStack(Mod.THERMAL_EXPANSION.getItem("tank")), "Level", (byte) 1), data);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		protected IModuleModelBakery createBakery() {
			return new BakeryLargeTank(new ResourceLocation(Constants.MOD_ID, "module/tank/portable/hardened"));
		}
		
		@Override
		protected IModuleKeyGenerator getGenerator() {
			return TANK_GENERATOR;
		}
	},
	PORTABLE_REINFORCED("portable_reinforced", 4) {
		@Override
		protected void initData(IModuleData data) {
			super.initData(data);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addFluidHandler(module).addTank(180000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		protected Mod getRequiredMod() {
			return Mod.THERMAL_EXPANSION;
		}
		
		@Override
		protected IModuleType createCustomType() {
			return new ModuleTypeNBT(NBTUtil.setByte(new ItemStack(Mod.THERMAL_EXPANSION.getItem("tank")), "Level", (byte) 2), data);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		protected IModuleModelBakery createBakery() {
			return new BakeryLargeTank(new ResourceLocation(Constants.MOD_ID, "module/tank/portable/reinforced"));
		}
		
		@Override
		protected IModuleKeyGenerator getGenerator() {
			return TANK_GENERATOR;
		}
	},
	PORTABLE_SIGNALUM("portable_signalum", 4) {
		@Override
		protected void initData(IModuleData data) {
			super.initData(data);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addFluidHandler(module).addTank(320000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		protected Mod getRequiredMod() {
			return Mod.THERMAL_EXPANSION;
		}
		
		@Override
		protected IModuleType createCustomType() {
			return new ModuleTypeNBT(NBTUtil.setByte(new ItemStack(Mod.THERMAL_EXPANSION.getItem("tank")), "Level", (byte) 3), data);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		protected IModuleModelBakery createBakery() {
			return new BakeryLargeTank(new ResourceLocation(Constants.MOD_ID, "module/tank/portable/signalum"));
		}
		
		@Override
		protected IModuleKeyGenerator getGenerator() {
			return TANK_GENERATOR;
		}
	},
	PORTABLE_RESONANT("portable_resonant", 4) {
		@Override
		protected void initData(IModuleData data) {
			super.initData(data);
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addFluidHandler(module).addTank(500000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		protected Mod getRequiredMod() {
			return Mod.THERMAL_EXPANSION;
		}
		
		@Override
		protected IModuleType createCustomType() {
			return new ModuleTypeNBT(NBTUtil.setByte(new ItemStack(Mod.THERMAL_EXPANSION.getItem("tank")), "Level", (byte) 4), data);
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		protected IModuleModelBakery createBakery() {
			return new BakeryLargeTank(new ResourceLocation(Constants.MOD_ID, "module/tank/portable/resonant"));
		}
		
		@Override
		protected IModuleKeyGenerator getGenerator() {
			return TANK_GENERATOR;
		}
	};
	protected static final IModuleKeyGenerator DEFAULT_GENERATOR = ModuleRegistry.INSTANCE.getDefaultGenerator();
	protected static final IModuleKeyGenerator ACTIVATABLE_GENERATOR = m -> {
		IActivatableComponent component = m.getComponent(IActivatableComponent.class);
		String key = DEFAULT_GENERATOR.generateKey(m);
		return component == null ? key : key + (component.isActive() ? "_on" : "_off");
	};
	protected static final IModuleKeyGenerator CASING_GENERATOR = m -> {
		IActivatableComponent component = m.getComponent(IActivatableComponent.class);
		StringBuilder stringBuilder = new StringBuilder(DEFAULT_GENERATOR.generateKey(m));
		IModuleProvider moduleProvider = m.getComponent(IModuleProvider.class);
		if (moduleProvider == null) {
			return stringBuilder.toString();
		}
		IModuleHandler moduleHandler = moduleProvider.getHandler();
		IModule left = moduleHandler.getModule(CasingPosition.LEFT);
		IModule right = moduleHandler.getModule(CasingPosition.RIGHT);
		stringBuilder.append(":left=").append(left.isEmpty() || left.getData().isValidPosition(CasingPosition.FRONT)).append(',');
		stringBuilder.append("right=").append(right.isEmpty() || right.getData().isValidPosition(CasingPosition.FRONT));
		return stringBuilder.toString();
	};
	protected static final IModuleKeyGenerator TANK_GENERATOR = m -> {
		String defaultKey = DEFAULT_GENERATOR.generateKey(m);
		IFluidHandlerComponent component = m.getComponent(IFluidHandlerComponent.class);
		if (component == null) {
			return defaultKey;
		}
		IFluidHandlerComponent.ITank tank = component.getTank(0);
		if (tank == null) {
			return defaultKey;
		}
		FluidStack stack = tank.getFluid();
		if (stack == null) {
			return defaultKey;
		}
		return defaultKey + ":fluid=" + stack.hashCode();
	};
	
	
	protected final IModuleData data;
	private final String registryName;
	
	ModuleDefinition(String registryName, int complexity) {
		this(ModuleManager.factory.createData(), registryName, complexity);
	}
	
	ModuleDefinition(IModuleData data, String registryName, int complexity) {
		this.data = data;
		data.setRegistryName(new ResourceLocation(Constants.MOD_ID, registryName));
		data.setComplexity(complexity);
		data.setUnlocalizedName(registryName);
		data.setDefinition(this);
		initData(data);
		this.registryName = registryName;
	}
	
	protected void initData(IModuleData data) {
	}
	
	public void registerContainers() {
		IModuleType type = createCustomType();
		if (type != null) {
			ModuleManager.registry.registerType(type);
		}
		ItemStack itemStack = createItemStack();
		if (!itemStack.isEmpty()) {
			data.registerType(itemStack);
		}
	}
	
	public IModuleData data() {
		return data;
	}
	
	@Nullable
	protected IModuleType createCustomType() {
		return null;
	}
	
	protected ItemStack createItemStack() {
		return ItemStack.EMPTY;
	}
	
	protected void registerType(ItemStack parent) {
		data.registerType(parent);
	}
	
	@Nullable
	protected Mod getRequiredMod() {
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	protected IModuleModelBakery createBakery() {
		if (isActivatable()) {
			return new BakeryActivatable(new ResourceLocation(Constants.MOD_ID, "module/" + registryName + "_off"),
					new ResourceLocation(Constants.MOD_ID, "module/" + registryName + "_on"));
		}
		return new BakeryBase(new ResourceLocation(Constants.MOD_ID, "module/" + registryName));
	}
	
	protected boolean isActivatable() {
		return false;
	}
	
	protected IModuleKeyGenerator getGenerator() {
		if (isActivatable()) {
			return ACTIVATABLE_GENERATOR;
		}
		return DEFAULT_GENERATOR;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		data.setGenerator(getGenerator());
		data.setBakery(createBakery());
	}
	
	@SubscribeEvent
	public static void onDataRegister(RegistryEvent.Register<IModuleData> event) {
		for (ModuleDefinition definition : values()) {
			Mod mod = definition.getRequiredMod();
			if (mod == null || mod.active()) {
				event.getRegistry().register(definition.data());
			}
		}
	}
	
	public static void registerModuleContainers() {
		for (ModuleDefinition definition : values()) {
			Mod mod = definition.getRequiredMod();
			if (mod == null || mod.active()) {
				definition.registerContainers();
			}
		}
	}
}
