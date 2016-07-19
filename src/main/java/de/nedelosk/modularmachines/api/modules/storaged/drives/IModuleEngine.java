package de.nedelosk.modularmachines.api.modules.storaged.drives;

import de.nedelosk.modularmachines.api.modules.energy.IModuleKinetic;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModuleEngine extends IModuleDrive, IModuleKinetic{

	@SideOnly(Side.CLIENT)
	float getProgress(IModuleState state);

	@SideOnly(Side.CLIENT)
	void setProgress(IModuleState state, float progress);

	@SideOnly(Side.CLIENT)
	void addProgress(IModuleState state, float progress);

	boolean isWorking(IModuleState state);
}
