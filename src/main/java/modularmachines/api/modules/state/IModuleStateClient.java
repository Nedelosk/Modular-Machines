package modularmachines.api.modules.state;

import javax.annotation.Nullable;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.models.IModelHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModuleStateClient<M extends IModule> extends IModuleState<M> {

	@Nullable
	@SideOnly(Side.CLIENT)
	IModelHandler getModelHandler();
}
