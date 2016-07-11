package de.nedelosk.modularmachines.client.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Predicate;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.IModelHandlerAnimated;
import de.nedelosk.modularmachines.client.core.ClientProxy.DefaultTextureGetter;
import de.nedelosk.modularmachines.client.core.ModelManager;
import de.nedelosk.modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import de.nedelosk.modularmachines.common.blocks.propertys.UnlistedBlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.block.model.MultipartBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.client.model.animation.Animation;
import net.minecraftforge.common.animation.Event;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelModular implements IBakedModel {

	private ItemOverrideList overrideList;
	private IBakedModel missingModel;

	private IBakedModel getModel(ICapabilityProvider provider, VertexFormat vertex){
		IModularHandler modularHandler = ModularManager.getModularHandler(provider);
		if(modularHandler != null){
			if(modularHandler.getModular() != null){
				IModelState modelState = ModelManager.getInstance().DEFAULT_BLOCK;
				Map<Predicate<IBlockState>, IBakedModel> models = new LinkedHashMap<>();
				List<IModelHandler> modelHandlers = new ArrayList<>();

				if(modularHandler instanceof IModularHandlerTileEntity){
					IModularHandlerTileEntity moduleHandlerTile = (IModularHandlerTileEntity) modularHandler;
					EnumFacing facing = moduleHandlerTile.getFacing();
					if(facing != null){
						ModelRotation rotation;
						if(facing == EnumFacing.SOUTH){
							rotation = ModelRotation.X0_Y180;
						}else if(facing == EnumFacing.WEST){
							rotation = ModelRotation.X0_Y270;
						}else if(facing == EnumFacing.EAST){
							rotation = ModelRotation.X0_Y90;
						}else{
							rotation = ModelRotation.X0_Y0;
						}
						modelState = new ModelStateComposition(modelState, rotation);
					}
				}

				for(IModuleState moduleState : modularHandler.getModular().getModuleStates()){
					IModelHandler modelHandler = ((IModuleStateClient)moduleState).getModelHandler();

					if(modelHandler != null){
						modelHandlers.add(modelHandler);
						if(modelHandler instanceof IModelHandlerAnimated){
							IModelHandlerAnimated modelHandlerAnimated = (IModelHandlerAnimated) modelHandler;
							Minecraft mc = Minecraft.getMinecraft();
							float time = Animation.getWorldTime(mc.theWorld, mc.getRenderPartialTicks());
							Pair<IModelState, Iterable<Event>> pair = modelHandlerAnimated.getStateMachine(moduleState).apply(time);
							((IModelHandlerAnimated)modelHandler).handleEvents(modelHandler, time, pair.getRight());

							IBakedModel model = modelHandler.bakeModel(moduleState, new ModelStateComposition(modelState, pair.getLeft()), vertex, DefaultTextureGetter.INSTANCE, modelHandlers);
							if(model != null){
								models.put(IModelHandler.createTrue(), model);
							}
						}else{
							IBakedModel model = modelHandler.bakeModel(moduleState, modelState, vertex, DefaultTextureGetter.INSTANCE, modelHandlers);
							if(model != null){
								models.put(IModelHandler.createTrue(), model);
							}
						}
					}
				}
				if(!models.isEmpty()){
					return new ModularBaked(models);
				}
			}
		}
		if(missingModel == null){
			missingModel = ModelLoaderRegistry.getMissingModel().bake(ModelManager.getInstance().DEFAULT_BLOCK, vertex, DefaultTextureGetter.INSTANCE);
		}
		return missingModel;
	}

	public static class ModularBaked extends MultipartBakedModel{
		private final Collection<IBakedModel> models;

		public ModularBaked(Map<Predicate<IBlockState>, IBakedModel> models) {
			super(models);

			this.models = models.values();
		}

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
			List<BakedQuad> quads = new ArrayList<>();
			for (IBakedModel model : this.models){
				if(model != null){
					quads.addAll(model.getQuads(state, side, rand++));
				}
			}
			return quads;
		}

	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if(state instanceof IExtendedBlockState){
			IExtendedBlockState stateExtended = (IExtendedBlockState) state;
			IBlockAccess world = stateExtended.getValue(UnlistedBlockAccess.BLOCKACCESS);
			BlockPos pos = stateExtended.getValue(UnlistedBlockPos.POS);
			TileEntity tile = world.getTileEntity(pos);

			IBakedModel model = getModel(tile, DefaultVertexFormats.BLOCK);
			if(model != null){
				return model.getQuads(state, side, rand);
			}
		}
		return Collections.emptyList();
	}

	public class ItemOverrideListModular extends ItemOverrideList{

		public ItemOverrideListModular() {
			super(Collections.emptyList());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
			IModularHandler modularHandler = ModularManager.getModularHandler(stack);
			
			if(modularHandler instanceof IModularHandlerItem){
				modularHandler.deserializeNBT(stack.getTagCompound());
			}
			IModular modular = modularHandler.getModular();
			if(modular != null){
				IBakedModel model = getModel(stack, DefaultVertexFormats.ITEM);
				if(model != null){
					return model;
				}
			}
			return super.handleItemState(originalModel, stack, world, entity);
		}
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return null;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides() {
		if(overrideList == null){
			overrideList = new ItemOverrideListModular();
		}
		return overrideList;
	}
}
