package de.nedelosk.modularmachines.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.models.BakedMultiModel;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelHelper;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelHelper.DefaultTextureGetter;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleStorage;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelModular implements IBakedModel {

	private ItemOverrideList overrideList;
	private IBakedModel missingModel;

	private IBakedModel bakeModel(ICapabilityProvider provider, VertexFormat vertex){
		IModularHandler modularHandler = getModularHandler(provider);
		if(modularHandler != null){
			IModular modular = modularHandler.getModular();
			if(modular  != null){
				IModelState modelState = ModelManager.getInstance().DEFAULT_BLOCK;
				List<IBakedModel> models = new ArrayList<>();

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

				for(IStorage storage : modular.getStorages().values()){
					IBakedModel model = ModuleModelHelper.getModel(storage.getModule(), storage, modelState, vertex);
					if(model != null){
						//Rotate the storage module model
						if(!(storage instanceof IModuleStorage)){
							model = new TRSRBakedModel(model, 0F, 0F, 0F, 0F, storage.getPosition().getRotation(), 0F, 1F);
						}
						models.add(model);
					}
					/*if(storage instanceof IModuleStorage){
						List<IBakedModel> positionedModels = new ArrayList<>();
						EnumModuleSizes size = null;
						for(IModuleState moduleState : ((IModuleStorage)storage).getModules()){
							if(((IModuleStateClient)moduleState).getModelHandler() != null){
								IBakedModel model = getModel(moduleState, modelState, vertex);
								if(model != null){
									if(size == null){
										positionedModels.add(model);
									}else if(size == EnumModuleSizes.SMALL){
										positionedModels.add(new TRSRBakedModel(model, 0F, -0.25F, 0F, 1F));
									}else{
										positionedModels.add(new TRSRBakedModel(model, 0F, -0.5F, 0F, 1F));
									}
								}
								if(!(moduleState.getModule() instanceof IModuleModuleStorage)){
									size = EnumModuleSizes.getSize(size, moduleState.getModule().getSize(moduleState.getContainer()));
								}
							}
						}
						float rotation = 0F;
						IStoragePosition pos = storage.getPosition();
						if(pos == EnumStoragePositions.RIGHT){
							rotation = (float) (Math.PI / 2);
						}else if(pos == EnumStoragePositions.LEFT){
							rotation = -(float) (Math.PI / 2);
						}
						if(!positionedModels.isEmpty()){
							models.add(new TRSRBakedModel(new BakedMultiModel(positionedModels), 0F, 0F, 0F, 0F, rotation, 0F, 1F));
						}
					}*/
				}

				if(!models.isEmpty()){
					return new IPerspectiveAwareModel.MapWrapper(new BakedMultiModel(models), modelState);
				}
			}else if(modularHandler.getAssembler() != null){
				return ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("modularmachines:block/modular")).bake(ModelManager.getInstance().DEFAULT_BLOCK, vertex, DefaultTextureGetter.INSTANCE);
			}
		}
		if(missingModel == null){
			missingModel = ModelLoaderRegistry.getMissingModel().bake(ModelManager.getInstance().DEFAULT_BLOCK, vertex, DefaultTextureGetter.INSTANCE);
		}
		return missingModel;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if(state instanceof IExtendedBlockState){
			IExtendedBlockState stateExtended = (IExtendedBlockState) state;
			IBlockAccess world = stateExtended.getValue(UnlistedBlockAccess.BLOCKACCESS);
			BlockPos pos = stateExtended.getValue(UnlistedBlockPos.POS);
			if(pos != null && world != null){
				TileEntity tile = world.getTileEntity(pos);

				IBakedModel model = bakeModel(tile, DefaultVertexFormats.BLOCK);
				if(model != null){
					return model.getQuads(state, side, rand);
				}
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
			IModularHandler modularHandler = getModularHandler(stack);

			if(stack.hasTagCompound() && modularHandler instanceof IModularHandlerItem){
				modularHandler.deserializeNBT(stack.getTagCompound());
			}
			IModular modular = modularHandler.getModular();
			if(modular != null){
				IBakedModel model = bakeModel(stack, DefaultVertexFormats.ITEM);
				if(model != null){
					return model;
				}
			}
			return super.handleItemState(originalModel, stack, world, entity);
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
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("modularmachines:blocks/modular_chassis");
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
