package modularmachines.client.model;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;

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
					Transformer transformer = new Transformer(sprite, quad.getSprite(), quad.getFormat());
					quad.pipe(transformer);
					builder.add(transformer.build());
				}
			}
		}
		
		// general quads
		if (!original.isBuiltInRenderer()) {
			for (BakedQuad quad : original.getQuads(null, null, 0)) {
				Transformer transformer = new Transformer(sprite, quad.getSprite(), quad.getFormat());
				quad.pipe(transformer);
				builder.add(transformer.build());
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
	
	private static class Transformer extends VertexTransformer {
		private final TextureAtlasSprite sprite;
		private final TextureAtlasSprite oldSprite;
		
		public Transformer(TextureAtlasSprite sprite, TextureAtlasSprite oldSprite, VertexFormat format) {
			super(new UnpackedBakedQuad.Builder(format));
			this.sprite = sprite;
			this.oldSprite = oldSprite;
		}
		
		@Override
		public void put(int element, float... data) {
			VertexFormatElement vertexElement = parent.getVertexFormat().getElement(element);
			VertexFormatElement.EnumUsage usage = vertexElement.getUsage();
			if (usage == VertexFormatElement.EnumUsage.UV && data.length >= 3 && vertexElement.getIndex() == 0) {
				data[0] = sprite.getInterpolatedU((double) oldSprite.getUnInterpolatedU(data[0]));
				data[1] = sprite.getInterpolatedV((double) oldSprite.getUnInterpolatedV(data[1]));
			}
			super.put(element, data);
		}
		
		public UnpackedBakedQuad build() {
			return ((UnpackedBakedQuad.Builder) parent).build();
		}
	}
}
