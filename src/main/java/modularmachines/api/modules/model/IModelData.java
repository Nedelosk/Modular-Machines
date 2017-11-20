package modularmachines.api.modules.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;

@SideOnly(Side.CLIENT)
public interface IModelData {
	
	void addModel(IModelList modelList, Module module, IModuleModelState modelState);
	
	default boolean handlesChildren() {
		return false;
	}
	
	default IModuleModelState createState(Module module) {
		return EmptyModelState.INSTANCE;
	}
	
	IModelLocations locations();
}
