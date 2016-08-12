package de.nedelosk.modularmachines.api.modules.storaged.drives;

import de.nedelosk.modularmachines.api.modules.IModuleWorking;
import de.nedelosk.modularmachines.api.modules.energy.IModuleKinetic;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModuleEngine extends IModuleDrive, IModuleKinetic{

	boolean isWorking(IModuleState state);
}
