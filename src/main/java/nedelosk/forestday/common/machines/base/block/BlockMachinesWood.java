package nedelosk.forestday.common.machines.base.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMachinesWood extends BlockMachines {

	public BlockMachinesWood(String blockName, Class<? extends TileMachineBase>... tiles) {
		super(blockName, tiles);
		setStepSound(soundTypeWood);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
	}
	
	@Override
	public IIcon getIcon(int blockSide, int blockMeta) {
		return Blocks.iron_block.getIcon(0, 0);
	}
	
	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
		return Blocks.iron_block.getIcon(0, 0);
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random_)
    {
    	if(world.getTileEntity(x, y, z) instanceof TileCampfire && ((TileCampfire)world.getTileEntity(x, y, z)).isWorking)
    	{
            int l = world.getBlockMetadata(x, y, z);
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + random_.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = random_.nextFloat() * 0.6F - 0.3F;

                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1 + 0.3, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1 + 0.3, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1 + 0.3, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f3 - 0.5), (double)f1 + 0.3, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
    	}
    }
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
