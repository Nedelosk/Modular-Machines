package de.nedelosk.modularmachines.api.modules.state;

import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModuleStateClient<M extends IModule> extends IModuleState<M> {

	@Nullable
	@SideOnly(Side.CLIENT)
	IModelHandler getModelHandler();
}
