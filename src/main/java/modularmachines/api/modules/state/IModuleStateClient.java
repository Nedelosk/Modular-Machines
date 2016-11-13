package modularmachines.api.modules.state;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.models.IModelHandler;

public interface IModuleStateClient<M extends IModule> extends IModuleState<M> {

	@Nullable
	@SideOnly(Side.CLIENT)
	IModelHandler getModelHandler();
}
