package de.nedelosk.modularmachines.api.modules;

import com.google.common.base.Predicate;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IModuleModelHandler<M extends IModule> {

	IBakedModel getModel(IModuleState<M> state);
	
	Predicate<IBlockState> getPredicate(IModuleState<M> state);
}
