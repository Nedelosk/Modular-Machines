package de.nedelosk.modularmachines.client.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.core.ClientProxy.DefaultTextureGetter;
import de.nedelosk.modularmachines.client.core.ModelManager;
import de.nedelosk.modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import de.nedelosk.modularmachines.common.blocks.propertys.UnlistedBlockPos;
import net.minecraft.block.state.IBlockState;
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
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelModularMachine implements IBakedModel {

	private ItemOverrideList overrideList;
	private IBakedModel missingModel;

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

	@Override
	public boolean isAmbientOcclusion() {
		return false;
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

	private IBakedModel getModel(ICapabilityProvider provider, VertexFormat vertex){
		IModularHandler modularHandler = getModularHandler(provider);
		if(modularHandler != null && modularHandler.getModular() != null){
			IModelState modelState = ModelManager.getInstance().DEFAULT_BLOCK;
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
			Map<Predicate<IBlockState>, IBakedModel> models = new LinkedHashMap<>();
			List<IModuleModelHandler> modelHandlers = new ArrayList<>();
			boolean isEmpty = true;
			for(IModuleState moduleState : modularHandler.getModular().getModuleStates()){
				IModuleModelHandler modelHandler = moduleState.getModule().getModelHandler(moduleState);
				if(modelHandler != null){
					isEmpty = false;
					modelHandlers.add(modelHandler);
					models.put(IModuleModelHandler.createTrue(), modelHandler.getModel(moduleState, modelState, vertex, DefaultTextureGetter.INSTANCE, modelHandlers));
				}
			}
			if(!isEmpty){
				return new ModelModularMachineBaked(models);
			}
		}
		if(missingModel == null){
			missingModel = ModelLoaderRegistry.getMissingModel().bake(ModelManager.getInstance().DEFAULT_BLOCK, vertex, DefaultTextureGetter.INSTANCE);
		}
		return missingModel;
	}

	public static class ModelModularMachineBaked extends MultipartBakedModel{

		private final Collection<IBakedModel> models;

		public ModelModularMachineBaked(Map<Predicate<IBlockState>, IBakedModel> models) {
			super(models);

			this.models = models.values();
		}

		@Override
		public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
			List<BakedQuad> quads = new ArrayList<>();
			for (IBakedModel model : this.models){
				quads.addAll(model.getQuads(state, side, rand++));
			}
			return quads;
		}

	}

	public static IModularHandler getModularHandler(ICapabilityProvider provider){
		if(provider == null){
			return null;
		}
		if(provider.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)){
			return provider.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
		}
		return null;	
	}

	public class ItemOverrideListModular extends ItemOverrideList{

		public ItemOverrideListModular() {
			super(Collections.emptyList());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
			IBakedModel model = getModel(stack, DefaultVertexFormats.ITEM);
			return model != null ? model : super.handleItemState(originalModel, stack, world, entity);
		}

	}
}
