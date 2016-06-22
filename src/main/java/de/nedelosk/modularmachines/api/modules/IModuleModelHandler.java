package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IModuleModelHandler<M extends IModule> {
	
	IBakedModel getModel(IModuleState<M> state);
}
