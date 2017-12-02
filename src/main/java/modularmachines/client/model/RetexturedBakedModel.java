package modularmachines.client.model;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BakedQuadRetextured;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class RetexturedBakedModel implements IBakedModel {
	protected final ImmutableList<BakedQuad> general;
	protected final IBakedModel original;
	
	public RetexturedBakedModel(IBakedModel original, TextureAtlasSprite sprite) {
		this.original = original;
		
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
		// face quads
		for (EnumFacing face : EnumFacing.values()) {
			if (!original.isBuiltInRenderer()) {
				for (BakedQuad quad : original.getQuads(null, face, 0)) {
					builder.add(new BakedQuadRetextured(quad, sprite));
				}
			}
		}
		
		// general quads
		if (!original.isBuiltInRenderer()) {
			for (BakedQuad quad : original.getQuads(null, null, 0)) {
				builder.add(new BakedQuadRetextured(quad, sprite));
			}
		}
		
		this.general = builder.build();
	}
	
	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		if (side != null) {
			return Collections.emptyList();
		}
		return general;
	}
	
	@Override
	public boolean isAmbientOcclusion() {
		return original.isAmbientOcclusion();
	}
	
	@Override
	public boolean isGui3d() {
		return original.isGui3d();
	}
	
	@Override
	public boolean isBuiltInRenderer() {
		return original.isBuiltInRenderer();
	}
	
	@Override
	public TextureAtlasSprite getParticleTexture() {
		return original.getParticleTexture();
	}
	
	@Override
	public ItemOverrideList getOverrides() {
		return original.getOverrides();
	}
}
