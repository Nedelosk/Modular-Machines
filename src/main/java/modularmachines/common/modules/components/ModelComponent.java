package modularmachines.common.modules.components;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.components.IModelComponent;

@SideOnly(Side.CLIENT)
public class ModelComponent extends ModuleComponent implements IModelComponent {
	private boolean needsReload;
	@Nullable
	private String modelKey;
	
	@Override
	public void setModelNeedReload(boolean modelNeedReload) {
		this.needsReload = modelNeedReload;
	}
	
	@Override
	public boolean isModelNeedReload() {
		return needsReload;
	}
	
	@Nullable
	@Override
	public String getModelKey() {
		return modelKey;
	}
	
	@Override
	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}
}
