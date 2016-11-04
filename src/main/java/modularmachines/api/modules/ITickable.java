package modularmachines.api.modules;

import modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITickable {

	void updateServer(IModuleState<IModule> state, int tickCount);

	@SideOnly(Side.CLIENT)
	void updateClient(IModuleState<IModule> state, int tickCount);
}
