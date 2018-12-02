package modularmachines.common.compat.thermalexpansion;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

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
import modularmachines.api.modules.components.IModuleComponentFactory;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.api.modules.positions.CasingPosition;
import modularmachines.client.model.module.BakeryCasing;
import modularmachines.client.model.module.BakeryEnergyCell;
import modularmachines.client.model.module.BakeryLargeTank;
import modularmachines.common.modules.ModuleKeyGenerators;
import modularmachines.common.modules.components.CasingComponent;
import modularmachines.common.modules.components.block.FluidContainerInteraction;
import modularmachines.common.modules.components.handlers.SaveHandlers;
import modularmachines.common.modules.data.ModuleData;
import modularmachines.common.modules.data.ModuleTypeNBT;
import modularmachines.common.utils.Mod;
import modularmachines.common.utils.NBTUtil;

public enum TEModuleDefinition implements IModuleDefinition {
	MACHINE_FRAME("machine_frame", 0) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.CENTER);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addBoundingBox(module, new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.9375F, 0.9375F));
			module.addComponent(new CasingComponent());
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(data, getItem("frame"));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryCasing("machine_frame"), ModuleKeyGenerators.CASING_GENERATOR);
		}
		
	},
	CELL_BASIC("cell_basic", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addEnergyHandler(module, 2000000).setSaveHandler(SaveHandlers.THEnergy.INSTANCE);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.1250F, 0.5625F, 0.875F, 0.875F, 1F));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("cell"), "Level", (byte) 0), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryEnergyCell("module/cells/basic"), ModuleKeyGenerators.ENERGY_CELL_GENERATOR);
		}
	},
	CELL_HARDENED("cell_hardened", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addEnergyHandler(module, 8000000).setSaveHandler(SaveHandlers.THEnergy.INSTANCE);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.1250F, 0.5625F, 0.875F, 0.875F, 1F));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("cell"), "Level", (byte) 1), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryEnergyCell("module/cells/hardened"), ModuleKeyGenerators.ENERGY_CELL_GENERATOR);
		}
	},
	CELL_REINFORCED("cell_reinforced", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addEnergyHandler(module, 18000000).setSaveHandler(SaveHandlers.THEnergy.INSTANCE);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.1250F, 0.5625F, 0.875F, 0.875F, 1F));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("cell"), "Level", (byte) 2), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryEnergyCell("module/cells/reinforced"), ModuleKeyGenerators.ENERGY_CELL_GENERATOR);
		}
	},
	CELL_SIGNALUM("cell_signalum", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addEnergyHandler(module, 32000000).setSaveHandler(SaveHandlers.THEnergy.INSTANCE);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.1250F, 0.5625F, 0.875F, 0.875F, 1F));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("cell"), "Level", (byte) 3), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryEnergyCell("module/cells/signalum"), ModuleKeyGenerators.ENERGY_CELL_GENERATOR);
		}
	},
	CELL_RESONANT("cell_resonant", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.HORIZONTAL);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			factory.addEnergyHandler(module, 50000000).setSaveHandler(SaveHandlers.THEnergy.INSTANCE);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.1250F, 0.5625F, 0.875F, 0.875F, 1F));
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("cell"), "Level", (byte) 4), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryEnergyCell("module/cells/resonant"), ModuleKeyGenerators.ENERGY_CELL_GENERATOR);
		}
	},
	PORTABLE_BASIC("portable_basic", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			IFluidHandlerComponent component = factory.addFluidHandler(module);
			component.setSaveHandler(SaveHandlers.THTank.INSTANCE);
			component.addTank(20000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("tank"), "Level", (byte) 0), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryLargeTank("module/tank/portable/basic"), ModuleKeyGenerators.TANK_GENERATOR);
		}
	},
	PORTABLE_HARDENED("portable_hardened", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			IFluidHandlerComponent component = factory.addFluidHandler(module);
			component.setSaveHandler(SaveHandlers.THTank.INSTANCE);
			component.addTank(80000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("tank"), "Level", (byte) 1), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryLargeTank("module/tank/portable/hardened"), ModuleKeyGenerators.TANK_GENERATOR);
		}
	},
	PORTABLE_REINFORCED("portable_reinforced", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			IFluidHandlerComponent component = factory.addFluidHandler(module);
			component.setSaveHandler(SaveHandlers.THTank.INSTANCE);
			component.addTank(180000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("tank"), "Level", (byte) 2), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryLargeTank("module/tank/portable/reinforced"), ModuleKeyGenerators.TANK_GENERATOR);
		}
	},
	PORTABLE_SIGNALUM("portable_signalum", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			IFluidHandlerComponent component = factory.addFluidHandler(module);
			component.setSaveHandler(SaveHandlers.THTank.INSTANCE);
			component.addTank(320000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("tank"), "Level", (byte) 3), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryLargeTank("module/tank/portable/signalum"), ModuleKeyGenerators.TANK_GENERATOR);
		}
	},
	PORTABLE_RESONANT("portable_resonant", 4) {
		@Override
		protected void setProperties(IModuleDataBuilder data) {
			data.setPositions(CasingPosition.SIDES);
		}
		
		@Override
		public void addComponents(IModule module, IModuleComponentFactory factory) {
			IFluidHandlerComponent component = factory.addFluidHandler(module);
			component.setSaveHandler(SaveHandlers.THTank.INSTANCE);
			component.addTank(500000);
			factory.addBoundingBox(module, new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F));
			module.addComponent(new FluidContainerInteraction());
		}
		
		@Override
		public void registerTypes(IModuleRegistry registry) {
			registry.registerType(new ModuleTypeNBT(NBTUtil.setByte(getItem("tank"), "Level", (byte) 4), data));
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void registerModels(IModuleModelRegistry registry) {
			registry.registerModel(data, new BakeryLargeTank("module/tank/portable/resonant"), ModuleKeyGenerators.TANK_GENERATOR);
		}
	};
	protected final IModuleData data;
	
	TEModuleDefinition(String registryName, int complexity) {
		IModuleDataBuilder dataBuilder = new ModuleData.Builder()
				.setRegistryName(registryName)
				.setComplexity(complexity)
				.setTranslationKey(registryName)
				.setDefinition(this);
		setProperties(dataBuilder);
		this.data = dataBuilder.build();
	}
	
	protected void setProperties(IModuleDataBuilder data) {
	}
	
	protected ItemStack getItem(String registryName) {
		return new ItemStack(Mod.THERMAL_EXPANSION.getItem(registryName));
	}
	
	public static void preInit() {
		if (Mod.THERMAL_EXPANSION.active()) {
			MinecraftForge.EVENT_BUS.register(TEModuleDefinition.class);
		}
	}
	
	@SubscribeEvent
	public static void onDataRegister(RegistryEvent.Register<IModuleData> event) {
		for (TEModuleDefinition definition : values()) {
			event.getRegistry().register(definition.data);
		}
	}
}
