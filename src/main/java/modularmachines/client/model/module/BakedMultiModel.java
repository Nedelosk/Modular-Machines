package modularmachines.client.model.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class BakedMultiModel implements IBakedModel {
	
	private final Collection<IBakedModel> models;
	protected final TextureAtlasSprite particleTexture;
	protected final ItemOverrideList overrides;
	
	public static IBakedModel create(Collection<IBakedModel> models){
		if(models.size() == 1){
			return models.iterator().next();
		}
		return  new BakedMultiModel(models);
	}
	
	public BakedMultiModel(Collection<IBakedModel> models) {
		IBakedModel ibakedmodel = models.iterator().next();
		this.models = models;
		this.particleTexture = ibakedmodel.getParticleTexture();
		this.overrides = ibakedmodel.getOverrides();
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		List<BakedQuad> quads = new ArrayList<>();
		for (IBakedModel model : this.models) {
			if (model != null) {
				quads.addAll(model.getQuads(state, side, rand++));
			}
		}
		return quads;
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
		return this.particleTexture;
	}
	
	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}
	
	@Override
	public ItemOverrideList getOverrides() {
		return this.overrides;
	}
}
