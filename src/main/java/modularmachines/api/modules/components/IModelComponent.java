package modularmachines.api.modules.components;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	
	void setModelKey(String key);
	
	@Nullable
	String getModelKey();
}
