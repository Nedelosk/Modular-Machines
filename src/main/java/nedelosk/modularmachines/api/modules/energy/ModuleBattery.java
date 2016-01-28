package nedelosk.modularmachines.api.modules.energy;

import java.util.List;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.client.renderer.ModularMachineRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.handlers.EnergyHandler;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.modules.Module;
import nedelosk.modularmachines.api.utils.ModularUtils;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public class ModuleBattery<S extends IModuleBatterySaver> extends Module<S> implements IModuleBattery<S> {

	public final EnergyStorage storage;

	public ModuleBattery(String categoryUID, String moduleUID, EnergyStorage storage) {
		super(categoryUID, moduleUID);
		this.storage = storage;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
		IModuleBatterySaver saver = ((IModuleBatterySaver) stack.getSaver());
		if (saver.getBatteryCapacity() == 0) {
			saver.setBatteryCapacity(saver.getStorage(stack).getMaxEnergyStored());
		}
		int energyModifier = 0;
		int speedModifier = 0;
		if (ModularUtils.getCapacitors(modular) != null && ModularUtils.getCapacitors(modular).getStacks().size() > 0
				&& (ModularUtils.getCapacitors(modular).getStack(0) != null || ModularUtils.getCapacitors(modular).getStack(1) != null
						|| ModularUtils.getCapacitors(modular).getStack(2) != null)) {
			for ( ModuleStack<IModule> module : ModularUtils.getCapacitors(modular).getStacks() ) {
				if (module != null && module.getModule() != null && module.getModule() instanceof IModuleCapacitor) {
					IModuleCapacitor capacitor = (IModuleCapacitor) module.getModule();
					if (capacitor.canWork(modular, module)) {
						energyModifier += capacitor.getEnergyModifier();
						speedModifier += capacitor.getSpeedModifier();
					}
				}
			}
			saver.setSpeedModifier(speedModifier);
			saver.setEnergyModifier(energyModifier);
			int capacity = saver.getBatteryCapacity() + ((saver.getBatteryCapacity() * (energyModifier / 10)) / 10);
			if (((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().getMaxEnergyStored() != capacity) {
				((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().setCapacity(capacity);
			}
		} else {
			if (((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().getMaxEnergyStored() > saver.getBatteryCapacity()) {
				if (((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().getEnergyStored() > saver.getBatteryCapacity()) {
					((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().setEnergyStored(saver.getBatteryCapacity());
				}
				((EnergyHandler) modular.getManager().getEnergyHandler()).getStorage().setCapacity(saver.getBatteryCapacity());
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.BatteryRenderer(moduleStack, modular);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return new ModularMachineRenderer.BatteryRenderer(moduleStack, modular);
	}

	@Override
	public boolean onBuildModular(IModular modular, ModuleStack stack, List<String> moduleNames) {
		IModuleBatterySaver saver = (IModuleBatterySaver) stack.getSaver();
		modular.getManager().setEnergyHandler(new EnergyHandler(saver.getStorage(stack)));
		return super.onBuildModular(modular, stack, moduleNames);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui getGui(ModuleStack stack) {
		return new ModuleBatteryGui<>(getCategoryUID(), getModuleUID());
	}

	@Override
	public S getSaver(ModuleStack stack) {
		return (S) new ModuleBatterySaver(storage);
	}
}
