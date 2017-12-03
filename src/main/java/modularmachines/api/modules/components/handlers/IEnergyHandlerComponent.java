package modularmachines.api.modules.components.handlers;

import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.modules.IModule;

/**
 * This component can be used to add a energy storage to the module.
 * <p>
 * {@link modularmachines.api.modules.components.IModuleComponentFactory#addEnergyHandler(IModule, int)}} can be
 * used to add this component to a module.
 */
public interface IEnergyHandlerComponent extends IHandlerComponent, IEnergyStorage {
	
	int getScaledEnergyStored(int scale);
	
	void setEnergy(int energy);
	
	@Override
	ISaveHandler<IEnergyHandlerComponent> getSaveHandler();
}
