package nedelosk.forestday.common.machines.mutiblock.charcoalkiln;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import nedelosk.forestday.common.machines.mutiblock.core.block.BlockMultiblockBase;
import nedelosk.forestday.common.machines.mutiblock.core.tile.TileMultiblockBase;
import nedelosk.forestday.common.registrys.FBlocks;
import nedelosk.forestday.common.registrys.FItems;
import nedelosk.nedeloskcore.common.blocks.BlockContainerForest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockCharcoalKiln extends BlockMultiblockBase {

	public TileCharcoalAsh ash;
	public TileCharcoalKiln kiln;
	
	public BlockCharcoalKiln() {
		super(Material.wood, TileCharcoalKiln.class);
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
	public void registerBlockIcons(IIconRegister IIconRegister) {
		blockIcon = IIconRegister.registerIcon("forestday:machines/charcoal.kiln/burned");
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random_)
    {
    	if(world.getBlockMetadata(x, y, z) == 0)
    	{
    		if(((TileCharcoalKiln)world.getTileEntity(x, y, z)).master != null && ((TileCharcoalKiln)world.getTileEntity(x, y, z)).master.isMultiblock  || ((TileCharcoalKiln)world.getTileEntity(x, y, z)).isMaster && ((TileCharcoalKiln)world.getTileEntity(x, y, z)).isMultiblock)
    		{
    	if(((TileCharcoalKiln)world.getTileEntity(x, y, z)).master.isWorking)
    	{
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + random_.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = random_.nextFloat() * 0.6F - 0.3F;

                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1 + 0.5, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1 + 0.5, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1 + 0.5, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1 + 0.3, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1 + 0.3, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1 + 0.3, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
    	}
    	}
    	}
    	else
    	{
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + random_.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = random_.nextFloat() * 0.6F - 0.3F;
                
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double)(f + f3 - 0.5), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
    	}
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileCharcoalAsh)
		{
				ash = (TileCharcoalAsh) tile;
		}
		else if(tile != null && tile instanceof TileCharcoalKiln)
		{
			kiln = (TileCharcoalKiln) tile;
		}
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		if(ash != null)
		{
			if(ash.dropps != null)
			{
			list = ash.dropps;
			}
			else
			{
				list = super.getDrops(world, x, y, z, metadata, fortune);
			}
		}
		else if(kiln != null)
		{
			list.add(kiln.type.wood);
			list.add(kiln.type.wood);
			list.add(kiln.type.wood);
			list.add(kiln.type.wood);
			list.add(kiln.type.wood);
			list.add(kiln.type.wood);
			list.add(new ItemStack(FBlocks.Gravel.item()));
			list.add(new ItemStack(FBlocks.Gravel.item()));
			list.add(new ItemStack(FBlocks.Gravel.item()));
		}
		else
		{
			list = super.getDrops(world, x, y, z, metadata, fortune);
		}
		return list;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
