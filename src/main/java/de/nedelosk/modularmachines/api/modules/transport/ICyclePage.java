package de.nedelosk.modularmachines.api.modules.transport;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.gui.IPage;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public interface ICyclePage<H> extends IPage {
	
	ITransportCycle<H> createCycle();
	
	IModulePage getParent();

	IModuleState<IModuleTansport> getModuleState();

	String getTabTitle();

	String getPageID();
}
