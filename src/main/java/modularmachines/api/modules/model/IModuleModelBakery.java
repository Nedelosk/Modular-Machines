package modularmachines.api.modules.model;

import java.util.Collection;

import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;

public interface IModuleModelBakery {
	
	/**
	 * Creates a model for the given model data.
	 *
	 * @param modelInfo All information about the module and the model format.
	 * @param modelList A list that contains all child models of the current model.
	 */
	@SideOnly(Side.CLIENT)
	void bakeModel(IModule module, IModelInfo modelInfo, IModelList modelList);
	
	/**
	 * Returns all model locations that this model depends on.
	 * Assume that returned collection is immutable.
	 */
	@SideOnly(Side.CLIENT)
	Collection<ResourceLocation> getDependencies();
	
	/**
	 * @return True if the model has a part that needs this layer.
	 */
	@SideOnly(Side.CLIENT)
	default boolean canRenderInLayer(IModule module, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT;
	}
	
	/**
	 * @return True if the model contains the models of the children modules.
	 */
	@SideOnly(Side.CLIENT)
	default boolean handlesChildren() {
		return false;
	}
}
