package de.nedelosk.modularmachines.api.modules.models;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.IModelInitHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHandlerDefault extends ModelHandler implements IModelHandler, IModelInitHandler {

	protected ResourceLocation location;

	public ModelHandlerDefault(String modelFolder, IModuleContainer container, ResourceLocation location) {
		super(modelFolder, container);
		this.location = location;
	}

	@Override
	public void initModels(IModuleContainer container) {
		getModelOrDefault(location);
	}

	@Override
	public void reload(IModuleState state, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		bakedModel = getBakedModel(location, modelState, format, bakedTextureGetter);
	}
}
