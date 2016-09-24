package de.nedelosk.modularmachines.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.models.BakedMultiModel;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelLoader;
import de.nedelosk.modularmachines.api.modules.models.ModuleModelLoader.DefaultTextureGetter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.module.IModuleStorage;
import de.nedelosk.modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import de.nedelosk.modularmachines.common.blocks.propertys.UnlistedBlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
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
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelModular implements IBakedModel {

	private ItemOverrideList overrideList;
	private IBakedModel missingModel;
	private IBakedModel assemblerModel;

	public static Set<IModularHandlerTileEntity> modularHandlers = new HashSet();

	@SubscribeEvent
	public static void onBakeModel(ModelBakeEvent event) {
		for(IModularHandlerTileEntity modularHandler : modularHandlers){
			TileEntity tileEntity = modularHandler.getTile();
			if(tileEntity != null){
				if(tileEntity.isInvalid()){
					modularHandlers.remove(modularHandler);
				}else{
					if(modularHandler.getModular() != null){
						for(IModuleState state : modularHandler.getModular().getModules()){
							if(state instanceof IModuleStateClient){
								((IModuleStateClient) state).getModelHandler().setNeedReload(true);
							}
						}
					}
				}
			}
		}
	}

	private IBakedModel bakeModel(ICapabilityProvider provider, VertexFormat vertex){
		IModularHandler modularHandler = getModularHandler(provider);
		if(modularHandler != null){
			if(modularHandler instanceof IModularHandlerTileEntity){
				modularHandlers.add((IModularHandlerTileEntity) modularHandler);
			}
			IModular modular = modularHandler.getModular();
			if(modular  != null){
				IModelState modelState = ModelManager.getInstance().DEFAULT_BLOCK;
				List<IBakedModel> models = new ArrayList<>();

				for(IStorage storage : modular.getStorages().values()){
					IBakedModel model = ModuleModelLoader.getModel(storage.getModule(), storage, modelState, vertex);
					if(model != null){
						//Rotate the storage module model
						if(!(storage instanceof IModuleStorage)){
							model = new TRSRBakedModel(model, 0F, 0F, 0F, 0F, storage.getPosition().getRotation(), 0F, 1F);
						}
						models.add(model);
					}
				}

				if(!models.isEmpty()){
					float rotation = 0F;
					if(modularHandler instanceof IModularHandlerTileEntity){
						IModularHandlerTileEntity moduleHandlerTile = (IModularHandlerTileEntity) modularHandler;
						EnumFacing facing = moduleHandlerTile.getFacing();
						if(facing != null){
							if(facing == EnumFacing.SOUTH){
								rotation = (float) Math.PI;
							}else if(facing == EnumFacing.WEST){
								rotation = (float) Math.PI / 2;
							}else if(facing == EnumFacing.EAST){
								rotation = (float) -(Math.PI / 2);
							}
						}
					}
					return new IPerspectiveAwareModel.MapWrapper(new TRSRBakedModel(new BakedMultiModel(models), 0F, 0F, 0F, 0F, rotation, 0F, 1F), modelState);
				}
			}else if(modularHandler.getAssembler() != null){
				if(assemblerModel == null){
					return assemblerModel = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("modularmachines:block/modular")).bake(ModelManager.getInstance().DEFAULT_BLOCK, vertex, DefaultTextureGetter.INSTANCE);
				}
				return assemblerModel;
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
