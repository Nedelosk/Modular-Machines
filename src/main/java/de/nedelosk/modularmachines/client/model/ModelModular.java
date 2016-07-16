package de.nedelosk.modularmachines.client.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modular.ModularUtils;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.IModelHandlerAnimated;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
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
		IModularHandler modularHandler = ModularUtils.getModularHandler(provider);
		if(modularHandler != null){
			if(modularHandler.getModular() != null){
				IModelState modelState = ModelManager.getInstance().DEFAULT_BLOCK;
				List<IBakedModel> models = new ArrayList<>();
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

						IBakedModel model = modelHandler.getModel();
						if(modelHandler.needReload() || model == null){
							if(modelHandler instanceof IModelHandlerAnimated){
								IModelHandlerAnimated modelHandlerAnimated = (IModelHandlerAnimated) modelHandler;
								Minecraft mc = Minecraft.getMinecraft();
								float time = Animation.getWorldTime(mc.theWorld, mc.getRenderPartialTicks());
								Pair<IModelState, Iterable<Event>> pair = modelHandlerAnimated.getStateMachine(moduleState).apply(time);

								((IModelHandlerAnimated)modelHandler).handleEvents(modelHandler, time, pair.getRight());
								modelHandler.reload(moduleState, new ModelStateComposition(modelState, pair.getLeft()), vertex, DefaultTextureGetter.INSTANCE, modelHandlers);
								model = modelHandler.getModel();
							}else{
								modelHandler.reload(moduleState, modelState, vertex, DefaultTextureGetter.INSTANCE, modelHandlers);
								model = modelHandler.getModel();
							}
							modelHandler.setNeedReload(false);
						}
						if(model != null){
							models.add(model);
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

	public static class ModularBaked implements IBakedModel{
		private final Collection<IBakedModel> models;

		protected final boolean ambientOcclusion;
		protected final boolean gui3D;
		protected final TextureAtlasSprite particleTexture;
		protected final ItemCameraTransforms cameraTransforms;
		protected final ItemOverrideList overrides;

		public ModularBaked(Collection<IBakedModel> models){
			IBakedModel ibakedmodel = models.iterator().next();

			this.models = models;
			this.ambientOcclusion = ibakedmodel.isAmbientOcclusion();
			this.gui3D = ibakedmodel.isGui3d();
			this.particleTexture = ibakedmodel.getParticleTexture();
			this.cameraTransforms = ibakedmodel.getItemCameraTransforms();
			this.overrides = ibakedmodel.getOverrides();
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

		@Override
		public boolean isAmbientOcclusion(){
			return this.ambientOcclusion;
		}

		@Override
		public boolean isGui3d(){
			return this.gui3D;
		}

		@Override
		public boolean isBuiltInRenderer(){
			return false;
		}

		@Override
		public TextureAtlasSprite getParticleTexture(){
			return this.particleTexture;
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms(){
			return this.cameraTransforms;
		}

		@Override
		public ItemOverrideList getOverrides(){
			return this.overrides;
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
			IModularHandler modularHandler = ModularUtils.getModularHandler(stack);

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
