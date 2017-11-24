package modularmachines.client.model;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBakerModel implements IBakedModel {
	
	private boolean isGui3d;
	private boolean isAmbientOcclusion;
	private TextureAtlasSprite particleSprite;
	@Nullable
	private IModelState modelState;
	@Nullable
	private ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
	
	private final Map<EnumFacing, List<BakedQuad>> faceQuads;
	private final List<BakedQuad> generalQuads;
	private final List<Pair<IBlockState, IBakedModel>> models;
	private final List<Pair<IBlockState, IBakedModel>> modelsPost;
	
	private float[] rotation = getDefaultRotation();
	private float[] translation = getDefaultTranslation();
	private float[] scale = getDefaultScale();
	
	public ModelBakerModel(IModelState modelState) {
		models = new ArrayList<>();
		modelsPost = new ArrayList<>();
		faceQuads = new EnumMap<>(EnumFacing.class);
		generalQuads = new ArrayList<>();
		particleSprite = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
		isGui3d = true;
		isAmbientOcclusion = false;
		setModelState(modelState);
		
		for (EnumFacing face : EnumFacing.VALUES) {
			faceQuads.put(face, new ArrayList<>());
		}
	}
	
	private ModelBakerModel(List<Pair<IBlockState, IBakedModel>> models, List<Pair<IBlockState, IBakedModel>> modelsPost, Map<EnumFacing, List<BakedQuad>> faceQuads, List<BakedQuad> generalQuads, boolean isGui3d, boolean isAmbientOcclusion, IModelState modelState, float[] rotation, float[] translation, float[] scale, TextureAtlasSprite particleSprite) {
		this.models = models;
		this.modelsPost = modelsPost;
		this.faceQuads = faceQuads;
		this.generalQuads = generalQuads;
		this.isGui3d = isGui3d;
		this.isAmbientOcclusion = isAmbientOcclusion;
		this.rotation = rotation;
		this.translation = translation;
		this.scale = scale;
		this.particleSprite = particleSprite;
		setModelState(modelState);
	}
	
	public void setGui3d(boolean gui3d) {
		this.isGui3d = gui3d;
	}
	
	public boolean isGui3d() {
		return isGui3d;
	}
	
	public void setAmbientOcclusion(boolean ambientOcclusion) {
		this.isAmbientOcclusion = ambientOcclusion;
	}
	
	public boolean isAmbientOcclusion() {
		return isAmbientOcclusion;
	}
	
	public void setParticleSprite(TextureAtlasSprite particleSprite) {
		this.particleSprite = particleSprite;
	}
	
	public TextureAtlasSprite getParticleTexture() {
		return particleSprite;
	}
	
	public boolean isBuiltInRenderer() {
		return false;
	}
	
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}
	
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}
	
	private static float[] getDefaultRotation() {
		return new float[]{-80, -45, 170};
	}
	
	private static float[] getDefaultTranslation() {
		return new float[]{0, 1.5F, -2.75F};
	}
	
	private static float[] getDefaultScale() {
		return new float[]{0.375F, 0.375F, 0.375F};
	}
	
	public void setRotation(float[] rotation) {
		this.rotation = rotation;
	}
	
	public void setTranslation(float[] translation) {
		this.translation = translation;
	}
	
	public void setScale(float[] scale) {
		this.scale = scale;
	}
	
	public float[] getRotation() {
		return rotation;
	}
	
	public float[] getTranslation() {
		return translation;
	}
	
	public float[] getScale() {
		return scale;
	}
	
	public void setModelState(@Nullable IModelState modelState) {
		this.modelState = modelState;
		this.transforms = PerspectiveMapWrapper.getTransforms(modelState);
	}
	
	public void addModelQuads(Pair<IBlockState, IBakedModel> model) {
		this.models.add(model);
	}
	
	public void addModelQuadsPost(Pair<IBlockState, IBakedModel> model) {
		this.modelsPost.add(model);
	}
	
	public void addQuad(@Nullable EnumFacing facing, BakedQuad quad) {
		if (facing != null) {
			faceQuads.get(facing).add(quad);
		} else {
			generalQuads.add(quad);
		}
	}
	
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		List<BakedQuad> quads = new ArrayList<>();
		for (Pair<IBlockState, IBakedModel> model : this.models) {
			List<BakedQuad> modelQuads = model.getRight().getQuads(model.getLeft(), side, rand);
			if (!modelQuads.isEmpty()) {
				quads.addAll(modelQuads);
			}
		}
		if (side != null) {
			quads.addAll(faceQuads.get(side));
		}
		quads.addAll(generalQuads);
		for (Pair<IBlockState, IBakedModel> model : this.modelsPost) {
			List<BakedQuad> modelQuads = model.getRight().getQuads(model.getLeft(), side, rand);
			if (!modelQuads.isEmpty()) {
				quads.addAll(modelQuads);
			}
		}
		return quads;
	}
	
	public ModelBakerModel copy() {
		return new ModelBakerModel(models, modelsPost, faceQuads, generalQuads, isGui3d, isAmbientOcclusion, modelState, rotation, translation, scale, particleSprite);
	}
	
	
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		return PerspectiveMapWrapper.handlePerspective(this, transforms, cameraTransformType);
	}
}
