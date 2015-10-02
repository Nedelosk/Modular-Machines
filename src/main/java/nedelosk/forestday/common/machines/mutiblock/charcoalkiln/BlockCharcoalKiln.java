package nedelosk.forestday.common.machines.mutiblock.charcoalkiln;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.nedeloskcore.common.blocks.multiblocks.BlockMultiblockBase;
import nedelosk.nedeloskcore.utils.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCharcoalKiln extends BlockMultiblockBase {
	
	public BlockCharcoalKiln() {
		super(Material.wood);
		setHardness(1.0F);
		setBlockName("kiln.charcoal");
		setStepSound(soundTypeWood);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta == 0)
		{
			return new TileCharcoalKiln();
		}
		else
		{
			return new TileCharcoalAsh();
		}
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileCharcoalKiln){
			TileCharcoalKiln kiln = (TileCharcoalKiln) tile;
			if(kiln.isMaster && kiln.isMultiblock || kiln.master != null && kiln.master.isMultiblock()){
				return 6;
			}
		}
		return super.getLightValue(world, x, y, z);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		blockIcon = IIconRegister.registerIcon("forestday:machines/charcoal.kiln/burned");
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
    	if(world.getBlockMetadata(x, y, z) == 0)
    	{
    		if(((TileCharcoalKiln)world.getTileEntity(x, y, z)).master != null && ((TileCharcoalKiln)world.getTileEntity(x, y, z)).master.isMultiblock()  || ((TileCharcoalKiln)world.getTileEntity(x, y, z)).isMaster && ((TileCharcoalKiln)world.getTileEntity(x, y, z)).isMultiblock)
    		{
		    	if(((TileCharcoalKiln)world.getTileEntity(x, y, z)).isWorking() || ((TileCharcoalKiln)world.getTileEntity(x, y, z)).master != null && ((TileCharcoalKiln)world.getTileEntity(x, y, z)).master.isWorking())
		    	{
		            float f = x + 0.5F;
		            float f1 = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
		            float f2 = z + 0.5F;
		            float f3 = 0.52F;
		            float f4 = random.nextFloat() * 0.6F - 0.3F;
		
		            world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.5, f2 + f4, 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.5, f2 + f4, 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.5, f2 + f4, 0.0D, 0.0D, 0.0D);
		            
		            world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
		                
		            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
		    	}
    		}
    	}
    	else
    	{
            float f = x + 0.5F;
            float f1 = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = z + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;
                
            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
    	}
    }
    
    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileCharcoalAsh){
			TileCharcoalAsh ash = (TileCharcoalAsh) tile;
			if(ash.dropps != null)
			{
				list = ash.dropps;
			}
		}
		else if(tile instanceof TileCharcoalKiln)
		{			
			TileCharcoalKiln kiln = (TileCharcoalKiln) tile;
			if(!kiln.isConsumed)
				list.add(kiln.type.wood);
		}
		ItemUtils.dropItem(world, x, y, z, list);
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
