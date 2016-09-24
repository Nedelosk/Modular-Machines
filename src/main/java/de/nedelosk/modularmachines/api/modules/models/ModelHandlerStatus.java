package de.nedelosk.modularmachines.api.modules.models;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHandlerStatus extends ModelHandler {

	public boolean status;
	protected ResourceLocation[] locations;

	public ModelHandlerStatus(ResourceLocation[] locations) {
		this.locations = locations;
	}

	@Override
	public void reload(IModuleState state, IStorage storage, IModelState modelState, VertexFormat format, Function bakedTextureGetter) {
		if(status){
			bakedModel = ModuleModelLoader.getModel(locations[0], format);
		}else{
			bakedModel = ModuleModelLoader.getModel(locations[1], format);
		}

	}
}
