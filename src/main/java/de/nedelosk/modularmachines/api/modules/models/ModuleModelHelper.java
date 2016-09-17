package de.nedelosk.modularmachines.api.modules.models;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.client.model.animation.Animation;
import net.minecraftforge.common.animation.Event;
import net.minecraftforge.common.model.IModelState;

public class ModuleModelHelper {

	public static enum DefaultTextureGetter implements Function<ResourceLocation, TextureAtlasSprite>{
		INSTANCE;

		@Override
		public TextureAtlasSprite apply(ResourceLocation location)
		{
			return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
		}
	} 

	public static IBakedModel getModel(IModuleState moduleState, IStorage storage, IModelState modelState, VertexFormat vertex){
		IModelHandler modelHandler = ((IModuleStateClient)moduleState).getModelHandler();

		if(modelHandler != null){
			IBakedModel model = modelHandler.getModel();
			if(modelHandler.needReload() || model == null){
				if(modelHandler instanceof IModelHandlerAnimated){
					IModelHandlerAnimated modelHandlerAnimated = (IModelHandlerAnimated) modelHandler;
					Minecraft mc = Minecraft.getMinecraft();
					float time = Animation.getWorldTime(mc.theWorld, mc.getRenderPartialTicks());
					Pair<IModelState, Iterable<Event>> pair = modelHandlerAnimated.getStateMachine(moduleState).apply(time);

					((IModelHandlerAnimated)modelHandler).handleEvents(modelHandler, time, pair.getRight());
					modelHandler.reload(moduleState, storage, new ModelStateComposition(modelState, pair.getLeft()), vertex, DefaultTextureGetter.INSTANCE);
					model = modelHandler.getModel();
				}else{
					modelHandler.reload(moduleState, storage, modelState, vertex, DefaultTextureGetter.INSTANCE);
					model = modelHandler.getModel();
				}
				modelHandler.setNeedReload(false);
			}
			if(model != null){
				return model;
			}
		}
		return null;
	}
}
