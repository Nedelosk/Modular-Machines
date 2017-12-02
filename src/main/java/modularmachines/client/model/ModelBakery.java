package modularmachines.client.model;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.util.vector.Vector3f;

//AABB = AxisAlignedBoundingBox
@SideOnly(Side.CLIENT)
public class ModelBakery {
	private static final FaceBakery FACE_BAKERY = new FaceBakery();
	private final List<ModelFace> faces = new ArrayList<>();
	private final Vector3f from;
	private final Vector3f to;
	private final ModelBakerModel currentModel = new ModelBakerModel(ModelManager.getInstance().getDefaultBlockState());
	private int colorIndex = -1;
	
	public ModelBakery() {
		this(Block.FULL_BLOCK_AABB);
	}
	
	public ModelBakery(AxisAlignedBB modelBounds) {
		from = new Vector3f((float) modelBounds.minX * 16.0f, (float) modelBounds.minY * 16.0f, (float) modelBounds.minZ * 16.0f);
		to = new Vector3f((float) modelBounds.maxX * 16.0f, (float) modelBounds.maxY * 16.0f, (float) modelBounds.maxZ * 16.0f);
	}
	
	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}
	
	public void addModel(TextureAtlasSprite[] textures, int colorIndex) {
		setColorIndex(colorIndex);
		
		for (EnumFacing facing : EnumFacing.VALUES) {
			addFace(facing, textures[facing.ordinal()]);
		}
	}
	
	public void addModel(TextureAtlasSprite texture, int colorIndex) {
		addModel(new TextureAtlasSprite[]{texture, texture, texture, texture, texture, texture}, colorIndex);
	}
	
	public void addBlockModel(@Nullable BlockPos pos, TextureAtlasSprite[] sprites, int colorIndex) {
		setColorIndex(colorIndex);
		
		if (pos != null) {
			World world = Minecraft.getMinecraft().world;
			IBlockState blockState = world.getBlockState(pos);
			for (EnumFacing facing : EnumFacing.VALUES) {
				if (blockState.shouldSideBeRendered(world, pos, facing)) {
					addFace(facing, sprites[facing.ordinal()]);
				}
			}
		} else {
			for (EnumFacing facing : EnumFacing.VALUES) {
				addFace(facing, sprites[facing.ordinal()]);
			}
		}
	}
	
	private float[] getUvs(EnumFacing face, Vector3f to, Vector3f from) {
		float minU;
		float minV;
		float maxU;
		float maxV;
		switch (face) {
			case SOUTH: {
				minU = from.x;
				minV = from.y;
				maxU = to.x;
				maxV = to.y;
				break;
			}
			case NORTH: {
				minU = from.x;
				minV = from.y;
				maxU = to.x;
				maxV = to.y;
				break;
			}
			case WEST: {
				minU = from.z;
				minV = from.y;
				maxU = to.z;
				maxV = to.y;
				break;
			}
			case EAST: {
				minU = from.z;
				minV = from.y;
				maxU = to.z;
				maxV = to.y;
				break;
			}
			case UP: {
				minU = from.x;
				minV = from.z;
				maxU = to.x;
				maxV = to.z;
				break;
			}
			case DOWN: {
				minU = from.x;
				minV = from.z;
				maxU = to.x;
				maxV = to.z;
				break;
			}
			default: {
				minU = 0;
				minV = 0;
				maxU = 16;
				maxV = 16;
				break;
			}
		}
		if (minU < 0 || maxU > 16) {
			minU = 0;
			maxU = 16;
		}
		if (minV < 0 || maxV > 16) {
			minV = 0;
			maxV = 16;
		}
		minU = 16 - minU;
		minV = 16 - minV;
		maxU = 16 - maxU;
		maxV = 16 - maxV;
		return new float[]{minU, minV, maxU, maxV};
	}
	
	public void addFace(EnumFacing facing, TextureAtlasSprite sprite) {
		faces.add(new ModelFace(facing, colorIndex, sprite));
	}
	
	public ModelBakerModel bake(boolean flip) {
		ModelRotation modelRotation = ModelRotation.X0_Y0;
		
		if (flip) {
			modelRotation = ModelRotation.X0_Y180;
		}
		
		for (ModelFace face : faces) {
			EnumFacing myFace = face.face;
			float[] uvs = getUvs(myFace, to, from);
			
			BlockFaceUV uv = new BlockFaceUV(uvs, 0);
			BlockPartFace bpf = new BlockPartFace(myFace, face.colorIndex, "", uv);
			BakedQuad quad = FACE_BAKERY.makeBakedQuad(from, to, bpf, face.spite, myFace, modelRotation, null, true, true);
			currentModel.addQuad(myFace, quad);
		}
		
		return currentModel;
	}
	
	private class ModelFace {
		private final EnumFacing face;
		private final TextureAtlasSprite spite;
		private final int colorIndex;
		
		private ModelFace(EnumFacing face, int colorIndex, TextureAtlasSprite sprite) {
			this.colorIndex = colorIndex;
			this.face = face;
			this.spite = sprite;
		}
	}
}
