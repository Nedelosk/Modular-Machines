package modularmachines.client.model.block;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.model.IModelInfo;
import modularmachines.api.modules.model.IModuleModelBakery;
import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.client.model.ModelManager;
import modularmachines.client.model.TRSRBakedModel;
import modularmachines.client.model.module.BakedMultiModel;
import modularmachines.client.model.module.ModelInfo;
import modularmachines.client.model.module.ModuleModelLoader;
import modularmachines.common.blocks.propertys.UnlistedBlockAccess;
import modularmachines.common.blocks.propertys.UnlistedBlockPos;
import modularmachines.common.utils.ModuleUtil;

@SideOnly(Side.CLIENT)
public class ModuleContainerModelBaked implements IBakedModel {
	
	//private static final Cache<IModuleLogic, IBakedModel> inventoryCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();
	//private static final ItemOverrideList overrideList = new ItemOverrideListModular();
	
	@SubscribeEvent
	public static void onBakeModel(ModelBakeEvent event) {
		//inventoryCache.invalidateAll();
	}
	
	@Nullable
	private static IBakedModel bakeModel(IModuleContainer container, VertexFormat vertex, @Nullable EnumFacing facing) {
		IModelState modelState = ModelManager.getInstance().getDefaultBlockState();
		BlockRenderLayer layer = MinecraftForgeClient.getRenderLayer();
		int focusedModule = -1;
		//Damage Model
		if (layer == null) {
			RayTraceResult result = Minecraft.getMinecraft().objectMouseOver;
			if (result != null) {
				focusedModule = result.subHit;
			}
		}
		IBakedModel bakedModel = bakeModel(container, new ModelInfo(vertex, modelState, ModuleModelLoader.DefaultTextureGetter.INSTANCE, MinecraftForgeClient.getRenderLayer()), focusedModule);
		if (bakedModel != null) {
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
			return new PerspectiveMapWrapper(new TRSRBakedModel(bakedModel, 0F, 0F, 0F, 0F, rotation, 0F, 1F), modelState);
		}
		return null;
	}
	
	@Nullable
	private static IBakedModel bakeModel(IModuleProvider provider, IModelInfo modelInfo, int focusedModule) {
		List<IBakedModel> models = new ArrayList<>();
		for (IModule module : provider.getHandler().getAllModules()) {
			int focused = focusedModule;
			IBakedModel model = null;
			if (focused == -1 || focused == module.getIndex()) {
				focused = -1; //Add Children models to the damage model
				model = ModuleModelLoader.getModel(module, modelInfo);
			}
			IModuleModelBakery bakery = module.getData().getBakery();
			IModuleProvider moduleProvider = module.getComponent(IModuleProvider.class);
			if (moduleProvider != null && !bakery.handlesChildren()) {
				IBakedModel bakedModel = bakeModel(moduleProvider, modelInfo, focused);
				if (bakedModel != null) {
					if (model == null) {
						model = BakedMultiModel.create(ImmutableList.of(bakedModel));
					} else {
						model = BakedMultiModel.create(ImmutableList.of(model, bakedModel));
					}
				}
			}
			if (model == null) {
				continue;
			}
			IModulePosition position = module.getPosition();
			Vec3d offset = position.getOffset();
			float rotation = position.getRotationAngle();
			if (rotation > 0.0F || rotation < 0.0F || !Vec3d.ZERO.equals(offset)) {
				model = new TRSRBakedModel(model, (float) offset.x, (float) offset.y, (float) offset.z, 0F, rotation, 0F, 1F);
			}
			models.add(model);
		}
		if (models.isEmpty()) {
			return null;
		}
		return BakedMultiModel.create(models);
	}
	
	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if (state instanceof IExtendedBlockState && side == null) {
			IExtendedBlockState stateExtended = (IExtendedBlockState) state;
			IBlockAccess world = stateExtended.getValue(UnlistedBlockAccess.BLOCKACCESS);
			BlockPos pos = stateExtended.getValue(UnlistedBlockPos.POS);
			if (pos != null && world != null) {
				IModuleContainer container = ModuleUtil.getContainer(pos, world);
				if (container != null) {
					IBakedModel model = bakeModel(container, DefaultVertexFormats.BLOCK, container.getLocatable().getFacing());
					if (model != null) {
						return model.getQuads(state, side, rand);
					}
				}
			}
		}
		return Collections.emptyList();
	}

	/*private static class ItemOverrideListModular extends ItemOverrideList {

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
	}*/
	
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
		return ItemOverrideList.NONE;
	}
}
