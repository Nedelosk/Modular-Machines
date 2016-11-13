package modularmachines.api.modules;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.state.IModuleState;

public interface ITickable {

	void updateServer(IModuleState<IModule> state, int tickCount);

	@SideOnly(Side.CLIENT)
	void updateClient(IModuleState<IModule> state, int tickCount);
}
