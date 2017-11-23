package modularmachines.api.modules.components;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.model.IModuleModelState;

/**
 * This component is used to provide the data about the model of the module.
 */
@SideOnly(Side.CLIENT)
public interface IModelComponent extends IModuleComponent {
	void setModelNeedReload(boolean modelNeedReload);
	
	/**
	 * @return If the model of the module needs a reload.
	 */
	boolean isModelNeedReload();
	
	void setModelState(IModuleModelState modelState);
	
	/**
	 * @return The state of the model. Used to cache {@link net.minecraft.client.renderer.block.model.IBakedModel}s.
	 */
	@Nullable
	IModuleModelState getModelState();
}
