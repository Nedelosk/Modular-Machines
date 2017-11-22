package modularmachines.api.modules.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;

@SideOnly(Side.CLIENT)
public interface IModelData {
	
	void addModel(IModelList modelList, IModule module, IModuleModelState modelState);
	
	default boolean handlesChildren() {
		return false;
	}
	
	default IModuleModelState createState(IModule module) {
		return EmptyModelState.INSTANCE;
	}
	
	IModelLocations locations();
}
