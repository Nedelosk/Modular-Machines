package modularmachines.client.model.block;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.ModuleRegistry;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.client.model.ModelManager;
import modularmachines.client.model.TRSRBakedModel;
import modularmachines.client.model.module.BakedMultiModel;
import modularmachines.client.model.module.ModelLoader;
import modularmachines.client.model.module.ModelLoader.DefaultTextureGetter;
import modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import modularmachines.common.blocks.propertys.UnlistedBlockPos;
import modularmachines.common.utils.ModuleUtil;

@SideOnly(Side.CLIENT)
public class ModuleStorageModelBaked implements IBakedModel {
	
	private static final Cache<IModuleLogic, IBakedModel> inventoryCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
	private static final ItemOverrideList overrideList = new ItemOverrideListModular();
	private static IBakedModel missingModel;
	private static IBakedModel assemblerModel;
	
	@SubscribeEvent
	public static void onBakeModel(ModelBakeEvent event) {
		inventoryCache.invalidateAll();
		assemblerModel = null;
		missingModel = null;
	}
	
	@Nullable
	private static IBakedModel bakeModel(IModuleContainer container, VertexFormat vertex, @Nullable EnumFacing facing) {
		IModelState modelState = ModelManager.getInstance().getDefaultBlockState();
		List<IBakedModel> models = new ArrayList<>();
		models.add(bakeModel(container, modelState, vertex));
		if (!models.isEmpty()) {
			float rotation = 0F;
			if (facing != null) {
				if (facing == EnumFacing.SOUTH) {
					rotation = (float) Math.PI;
				} else if (facing == EnumFacing.WEST) {
					rotation = (float) Math.PI / 2;
				} else if (facing == EnumFacing.EAST) {
					rotation = (float) -(Math.PI / 2);
				}
			}
			return new PerspectiveMapWrapper(new TRSRBakedModel(new BakedMultiModel(models), 0F, 0F, 0F, 0F, rotation, 0F, 1F), modelState);
		}
		if (missingModel == null) {
			missingModel = ModelLoaderRegistry.getMissingModel().bake(modelState, vertex, DefaultTextureGetter.INSTANCE);
		}
		return missingModel;
	}
	
	private static IBakedModel bakeModel(IModuleProvider provider, IModelState modelState, VertexFormat vertex){
		List<IBakedModel> models = new ArrayList<>();
		for(Module module : provider.getHandler().getModules()){
			IBakedModel model = ModelLoader.getModel(module, modelState, vertex);
			if (model == null) {
				continue;
			}
			float rotation = module.getPosition().getRotation();
			if(rotation > 0.0F || rotation < 0.0F){
				model = new TRSRBakedModel(model, 0F, 0F, 0F, 0F, rotation, 0F, 1F);
			}
			models.add(model);
		}
		return new BakedMultiModel(models);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState stateExtended = (IExtendedBlockState) state;
			IBlockAccess world = stateExtended.getValue(UnlistedBlockAccess.BLOCKACCESS);
			BlockPos pos = stateExtended.getValue(UnlistedBlockPos.POS);
			if (pos != null && world != null) {
				IModuleContainer container = ModuleUtil.getContainer(pos, world);
				if(container != null){
					IBakedModel model = bakeModel(container, DefaultVertexFormats.BLOCK, container.getFacing());
					if (model != null) {
						return model.getQuads(state, side, rand);
					}
				}
			}
		}
		return Collections.emptyList();
	}

	private static class ItemOverrideListModular extends ItemOverrideList {

		public ItemOverrideListModular() {
			super(Collections.emptyList());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
			IModuleLogic logic = getModuleLogic(stack);
			IBakedModel bakedModel = inventoryCache.getIfPresent(logic);
			if (bakedModel == null) {
				if (stack.hasTagCompound()) {
					logic.readFromNBT(stack.getTagCompound());
				}
				if (logic != null) {
					//bakedModel = bakeModel(stack, DefaultVertexFormats.ITEM, null, true);
					inventoryCache.put(logic, bakedModel);
				}
			}
			if (bakedModel != null) {
				return bakedModel;
			}
			return super.handleItemState(originalModel, stack, world, entity);
		}
	}

	public static IModuleLogic getModuleLogic(ICapabilityProvider provider) {
		if (provider == null) {
			return null;
		}
		if (provider.hasCapability(ModuleRegistry.MODULE_LOGIC, null)) {
			return provider.getCapability(ModuleRegistry.MODULE_LOGIC, null);
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
		return overrideList;
	}
}
