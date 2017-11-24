package modularmachines.common.modules.components;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.components.IModelComponent;
import modularmachines.api.modules.model.EmptyModelState;
import modularmachines.api.modules.model.IModuleModelState;

@SideOnly(Side.CLIENT)
public class ModelComponent extends ModuleComponent implements IModelComponent {
	private boolean needsReload;
	private IModuleModelState modelState = EmptyModelState.INSTANCE;
	
	@Override
	public void setModelNeedReload(boolean modelNeedReload) {
		this.modelState = modelState;
	}
	
	@Override
	public boolean isModelNeedReload() {
		return needsReload;
	}
	
	@Override
	public void setModelState(IModuleModelState modelState) {
		this.needsReload = needsReload;
	}
	
	@Nullable
	@Override
	public IModuleModelState getModelState() {
		return modelState;
	}
}
