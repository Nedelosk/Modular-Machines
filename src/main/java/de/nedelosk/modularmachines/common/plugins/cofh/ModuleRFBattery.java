package de.nedelosk.modularmachines.common.plugins.cofh;

import java.util.Collections;
import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerDefault;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modules.storages.ModuleBattery;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleRFBattery extends ModuleBattery {

	public ModuleRFBattery(String name) {
		super(name);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<IModelInitHandler> getInitModelHandlers(IModuleContainer container) {
		return Collections.singletonList(new ModelHandlerDefault(name, container, ModelHandler.getModelLocation(container, name, getSize(container))));
	}

	@SideOnly(Side.CLIENT)
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
		if(itemStack.hasCapability(CapabilityEnergy.ENERGY, null)){
			IEnergyStorage storage = itemStack.getCapability(CapabilityEnergy.ENERGY, null);
			int oldEnergy = storage.getEnergyStored();
			int capa = storage.getMaxEnergyStored();
			if(oldEnergy > energy) {
				storage.extractEnergy(Long.valueOf(oldEnergy - energy).intValue(), false);
			}else if(oldEnergy < energy){
				storage.receiveEnergy(Long.valueOf(energy - oldEnergy).intValue(), false);
			}
		}else if(itemStack.getItem() instanceof IEnergyContainerItem){
			IEnergyContainerItem item = (IEnergyContainerItem) itemStack.getItem();
			int oldEnergy = item.getEnergyStored(itemStack);
			int capa = item.getMaxEnergyStored(itemStack);
			if(oldEnergy > energy) {
				item.extractEnergy(itemStack, Long.valueOf(oldEnergy - energy).intValue(), false);
			}else if(oldEnergy < energy){
				item.receiveEnergy(itemStack, Long.valueOf(energy - oldEnergy).intValue(), false);
			}
		}
	}

	@Override
	public long loadEnergy(IModuleState state, ItemStack itemStack) {
		if(itemStack.hasCapability(CapabilityEnergy.ENERGY, null)){
			return itemStack.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored();
		}else if(itemStack.getItem() instanceof IEnergyContainerItem){
			return ((IEnergyContainerItem) itemStack.getItem()).getEnergyStored(itemStack);
		}
		return 0;
	}
}
