package modularmachines.api.modules.model;

import net.minecraft.util.BlockRenderLayer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;

@SideOnly(Side.CLIENT)
public interface IModelData {
	
	void addModel(IModelList modelList, IModule module, IModuleModelState modelState, BlockRenderLayer layer);
	
	default boolean handlesChildren() {
		return false;
	}
	
	default IModuleModelState createState(IModule module) {
		return EmptyModelState.INSTANCE;
	}
	
	IModelLocations locations();
	
	default boolean canRenderInLayer(IModule module, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT;
	}
	
	default boolean cacheModel() {
		return true;
	}
}
