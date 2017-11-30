package modularmachines.common.utils;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleHelper {
	
	public static boolean addHitEffects(World world, RayTraceResult target, ParticleManager manager) {
		return addHitEffects(world, target, manager);
	}
	
	public static boolean addHitEffects(World world, RayTraceResult target, ParticleManager manager, @Nullable String texture) {
		BlockPos pos = target.getBlockPos();
		EnumFacing side = target.sideHit;
		Random rand = world.rand;
		IBlockState iblockstate = world.getBlockState(pos);
		if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			double f = 0.10000000149011612D;
			AxisAlignedBB boundingBox = iblockstate.getBoundingBox(world, pos);
			double particleX = (double) x + rand.nextDouble() * (boundingBox.maxX - boundingBox.minX - f * 2.0F) + f + boundingBox.minX;
			double particleY = (double) y + rand.nextDouble() * (boundingBox.maxY - boundingBox.minY - f * 2.0F) + f + boundingBox.minY;
			double particleZ = (double) z + rand.nextDouble() * (boundingBox.maxZ - boundingBox.minZ - f * 2.0F) + f + boundingBox.minZ;
			
			if (side == EnumFacing.DOWN) {
				particleY = (double) y + boundingBox.minY - 0.10000000149011612D;
			} else if (side == EnumFacing.UP) {
				particleY = (double) y + boundingBox.maxY + 0.10000000149011612D;
			} else if (side == EnumFacing.NORTH) {
				particleZ = (double) z + boundingBox.minZ - 0.10000000149011612D;
			} else if (side == EnumFacing.SOUTH) {
				particleZ = (double) z + boundingBox.maxZ + 0.10000000149011612D;
			} else if (side == EnumFacing.WEST) {
				particleX = (double) x + boundingBox.minX - 0.10000000149011612D;
			} else if (side == EnumFacing.EAST) {
				particleX = (double) x + boundingBox.maxX + 0.10000000149011612D;
			}
			Particle particle = manager.spawnEffectParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D, Block.getStateId(iblockstate));
			if (particle != null) {
				particle.multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F);
				if (texture != null) {
					particle.setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture));
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		return addDestroyEffects(world, pos, manager, null);
	}
	
	public static boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager, @Nullable String texture) {
		IBlockState state = world.getBlockState(pos);
		state = state.getActualState(world, pos);
		
		for (int xOffset = 0; xOffset < 4; ++xOffset) {
			for (int yOffset = 0; yOffset < 4; ++yOffset) {
				for (int zOffset = 0; zOffset < 4; ++zOffset) {
					double x = ((double) xOffset + 0.5D) / 4.0D;
					double y = ((double) yOffset + 0.5D) / 4.0D;
					double z = ((double) zOffset + 0.5D) / 4.0D;
					ParticleDigging particle = (ParticleDigging) manager.spawnEffectParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), (double) pos.getX() + x, (double) pos.getY() + y, (double) pos.getZ() + z, x - 0.5D, y - 0.5D, z - 0.5D, Block.getStateId(state));
					if (particle != null) {
						particle.setBlockPos(pos);
						if (texture != null) {
							particle.setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture));
						}
					}
				}
			}
		}
		return true;
	}
}
