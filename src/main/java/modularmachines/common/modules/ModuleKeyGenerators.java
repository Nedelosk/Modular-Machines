package modularmachines.common.modules;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.components.IActivatableComponent;
import modularmachines.api.modules.components.handlers.IEnergyHandlerComponent;
import modularmachines.api.modules.components.handlers.IFluidHandlerComponent;
import modularmachines.api.modules.model.IModuleKeyGenerator;
import modularmachines.api.modules.positions.CasingPosition;

public class ModuleKeyGenerators {
	public static final IModuleKeyGenerator DEFAULT_GENERATOR = m -> {
		IModuleData moduleData = m.getData();
		ResourceLocation registryName = moduleData.getRegistryName();
		return registryName == null ? "null" : registryName.getResourcePath();
	};
	
	public static final IModuleKeyGenerator ACTIVATABLE_GENERATOR = m -> {
		IActivatableComponent component = m.getComponent(IActivatableComponent.class);
		String key = DEFAULT_GENERATOR.generateKey(m);
		return component == null ? key : key + (component.isActive() ? "_on" : "_off");
	};
	
	public static final IModuleKeyGenerator CASING_GENERATOR = m -> {
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
	
	public static final IModuleKeyGenerator TANK_GENERATOR = m -> {
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
	
	public static final IModuleKeyGenerator ENERGY_CELL_GENERATOR = m -> {
		String defaultKey = DEFAULT_GENERATOR.generateKey(m);
		IEnergyHandlerComponent component = m.getComponent(IEnergyHandlerComponent.class);
		if (component == null) {
			return defaultKey;
		}
		int scaledEnergy = component.getScaledEnergyStored(8);
		return defaultKey + ":energy=" + scaledEnergy;
	};
}
