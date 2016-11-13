package modularmachines.common.plugins.cofh;

import java.util.Collections;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import cofh.api.energy.IEnergyContainerItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandlerDefault;
import modularmachines.api.modules.models.ModuleModelLoader;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.modules.storages.ModuleBattery;

public class ModuleRFBattery extends ModuleBattery {

	public ModuleRFBattery(String name) {
		super(name);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModelHandler createModelHandler(IModuleState state) {
		IModuleItemContainer container = state.getContainer().getItemContainer();
		return new ModelHandlerDefault(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), name, container.getSize()));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Map<ResourceLocation, ResourceLocation> getModelLocations(IModuleItemContainer container) {
		return Collections.singletonMap(ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), container.getMaterial().getName(), name, container.getSize()),
				ModuleModelLoader.getModelLocation(getRegistryName().getResourceDomain(), "default", name, container.getSize()));
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
		if (itemStack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = itemStack.getCapability(CapabilityEnergy.ENERGY, null);
			int oldEnergy = storage.getEnergyStored();
			int capa = storage.getMaxEnergyStored();
			if (oldEnergy > energy) {
				storage.extractEnergy(Long.valueOf(oldEnergy - energy).intValue(), false);
			} else if (oldEnergy < energy) {
				storage.receiveEnergy(Long.valueOf(energy - oldEnergy).intValue(), false);
			}
		} else if (itemStack.getItem() instanceof IEnergyContainerItem) {
			IEnergyContainerItem item = (IEnergyContainerItem) itemStack.getItem();
			int oldEnergy = item.getEnergyStored(itemStack);
			int capa = item.getMaxEnergyStored(itemStack);
			if (oldEnergy > energy) {
				item.extractEnergy(itemStack, Long.valueOf(oldEnergy - energy).intValue(), false);
			} else if (oldEnergy < energy) {
				item.receiveEnergy(itemStack, Long.valueOf(energy - oldEnergy).intValue(), false);
			}
		}
	}

	@Override
	public long loadEnergy(IModuleState state, ItemStack itemStack) {
		if (itemStack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			return itemStack.getCapability(CapabilityEnergy.ENERGY, null).getEnergyStored();
		} else if (itemStack.getItem() instanceof IEnergyContainerItem) {
			return ((IEnergyContainerItem) itemStack.getItem()).getEnergyStored(itemStack);
		}
		return 0;
	}
}
