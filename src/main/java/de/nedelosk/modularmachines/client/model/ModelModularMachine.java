package de.nedelosk.modularmachines.client.model;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Function;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
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
import net.minecraft.client.renderer.block.model.MultipartBakedModel;
import net.minecraft.client.renderer.block.model.MultipartBakedModel.Builder;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.ModelStateComposition;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelModularMachine implements IBakedModel {

	private ItemOverrideList overrideList;

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
		IModular modular = getModular(provider);
		if(modular != null){
			MultipartBakedModel.Builder builder = new Builder();
			boolean isEmpty = true;
			for(IModuleState moduleState : modular.getModuleStates()){
				IModuleModelHandler modelHandler = moduleState.getModule().getModelHandler(moduleState);
				if(modelHandler != null){
					isEmpty = false;
					builder.putModel(modelHandler.getPredicate(moduleState), modelHandler.getModel(moduleState, ModelManager.getInstance().DEFAULT_BLOCK, vertex, DefaultTextureGetter.INSTANCE));
				}
			}
			if(isEmpty){
				return ModelLoaderRegistry.getMissingModel().bake(ModelManager.getInstance().DEFAULT_BLOCK, vertex, DefaultTextureGetter.INSTANCE);
			}
			return builder.makeMultipartModel();
		}
		return null;
	}

	public static IModular getModular(ICapabilityProvider provider){
		if(provider == null){
			return null;
		}
		if(provider.hasCapability(ModuleManager.MODULAR_HANDLER_CAPABILITY, null)){
			return provider.getCapability(ModuleManager.MODULAR_HANDLER_CAPABILITY, null);
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
