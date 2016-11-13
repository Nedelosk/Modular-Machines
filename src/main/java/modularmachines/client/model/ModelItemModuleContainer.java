package modularmachines.client.model;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import modularmachines.api.modules.ModuleManager;
import modularmachines.api.modules.containers.IModuleItemProvider;

public class ModelItemModuleContainer implements IBakedModel {

	private ItemOverrideList overrideList;

	public ModelItemModuleContainer() {
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return null;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
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
		return null;
	}

	@Override
	public ItemOverrideList getOverrides() {
		if (overrideList == null) {
			overrideList = new ModuleItemContainerOverrideList();
		}
		return overrideList;
	}

	private static class ModuleItemContainerOverrideList extends ItemOverrideList {

		public ModuleItemContainerOverrideList() {
			super(Collections.emptyList());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
			IModuleItemProvider moduleProvider = stack.getCapability(ModuleManager.MODULE_PROVIDER_CAPABILITY, null);
			if (moduleProvider != null && moduleProvider.getItemStack() != null) {
				return Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(moduleProvider.getItemStack(), world, entity);
			}
			return super.handleItemState(originalModel, stack, world, entity);
		}
	}
}
