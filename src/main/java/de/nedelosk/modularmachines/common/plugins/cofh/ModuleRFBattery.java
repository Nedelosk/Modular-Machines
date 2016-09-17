package de.nedelosk.modularmachines.common.plugins.cofh;

import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerDefault;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modules.storages.ModuleBattery;
import net.minecraft.item.ItemStack;

public class ModuleRFBattery extends ModuleBattery {

	public ModuleRFBattery(String name) {
		super(name);
	}

	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		return Collections.singletonList(new ModelHandlerDefault(name, container, ModelHandler.getModelLocation(container, name, getSize(container))));
	}

	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		return new ModelHandlerDefault(name, state.getContainer(), ModelHandler.getModelLocation(state.getContainer(), name, getSize(state.getContainer())));
	}

	@Override
	protected boolean showName(IModuleContainer container) {
		return false;
	}

	@Override
	protected boolean showMaterial(IModuleContainer container) {
		return false;
	}

	@Override
	public void saveEnergy(IModuleState state, long energy, ItemStack itemStack) {
		IModuleProperties properties = state.getModuleProperties();
		if(properties instanceof ModuleRFBatteryProperties){
			ModuleRFBatteryProperties batteryProperties = (ModuleRFBatteryProperties) properties;
			if(batteryProperties.containerItem != null) {
				int oldEnergy = batteryProperties.containerItem.getEnergyStored(itemStack);
				int capa = batteryProperties.containerItem.getMaxEnergyStored(itemStack);
				if(oldEnergy > energy) {
					batteryProperties.containerItem.extractEnergy(itemStack, Long.valueOf(oldEnergy - energy).intValue(), false);
				}else if(oldEnergy < energy){
					batteryProperties.containerItem.receiveEnergy(itemStack, Long.valueOf(energy - oldEnergy).intValue(), false);
				}
				return;
			}
		}
	}

	@Override
	public long loadEnergy(IModuleState state, ItemStack itemStack) {
		IModuleProperties properties = state.getModuleProperties();
		if(properties instanceof ModuleRFBatteryProperties){
			ModuleRFBatteryProperties batteryProperties = (ModuleRFBatteryProperties) properties;
			if(batteryProperties.containerItem != null) {
				return batteryProperties.containerItem.getEnergyStored(itemStack);
			}
		}
		return 0;
	}
}
