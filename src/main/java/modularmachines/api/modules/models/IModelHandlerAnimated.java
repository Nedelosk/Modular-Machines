package modularmachines.api.modules.models;

import net.minecraftforge.common.animation.IEventHandler;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.state.IModuleState;

@SideOnly(Side.CLIENT)
public interface IModelHandlerAnimated<M extends IModule> extends IModelHandler<M>, IEventHandler<IModuleState> {

	IAnimationStateMachine getStateMachine(IModuleState state);
}
