package modularmachines.api.modules.components.handlers;

import net.minecraftforge.energy.IEnergyStorage;

import modularmachines.api.IIOConfigurable;
import modularmachines.api.modules.IModule;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.IModuleComponent;

/**
 * This component can be used to add a energy storage to the module.
 * <p>
 * {@link modularmachines.api.modules.components.IModuleComponentFactory#addEnergyHandler(IModule, int)}} can be
 * used to add this component to a module.
 */
public interface IEnergyHandlerComponent extends IModuleComponent, IEnergyStorage, INBTReadable, INBTWritable, IIOConfigurable {
}
