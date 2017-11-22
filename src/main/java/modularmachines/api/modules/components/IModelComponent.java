package modularmachines.api.modules.components;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.model.IModuleModelState;

@SideOnly(Side.CLIENT)
public interface IModelComponent extends IModuleComponent {
	void setModelNeedReload(boolean modelNeedReload);
	
	boolean isModelNeedReload();
	
	void setModelState(IModuleModelState modelState);
	
	@Nullable
	IModuleModelState getModelState();
}
