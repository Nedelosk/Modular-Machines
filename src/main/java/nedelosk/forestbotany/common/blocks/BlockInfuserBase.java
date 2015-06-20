package nedelosk.forestbotany.common.blocks;

import java.util.List;

import buildcraft.api.tools.IToolWrench;
import nedelosk.forestbotany.common.blocks.tile.TileInfuser;
import nedelosk.forestbotany.common.blocks.tile.TileInfuserBase;
import nedelosk.forestbotany.common.blocks.tile.TileInfuserChamber;
import nedelosk.forestbotany.common.core.ForestBotany;
import nedelosk.forestbotany.common.core.registrys.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BlockInfuserBase extends BlockForestBotany {

	public BlockInfuserBase(String name) {
		super(Material.iron, "infuser." + name);
		this.setStepSound(soundTypeMetal);
	}
	
	@Override
	public int damageDropped(int metadata) {
		if(metadata == 1)
			return 0;
		if(metadata == 3)
			return 2;
		return super.damageDropped(metadata);
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase p_149689_5_, ItemStack stack) {
    	int meta = stack.getItemDamage();
    	if(meta == 0)
    	{
    		if(!world.isRemote)
    		{
    		world.setBlock(x, y + 1, z, BlockRegistry.infuser.block(), 1, 3);
    		}
    	}
    	if(meta == 2)
    	{
    		if(!world.isRemote)
    		{
    		world.setBlock(x, y + 1, z, BlockRegistry.infuser.block(), 3, 3);
    		}
    	}
    	super.onBlockPlacedBy(world, x, y, z, p_149689_5_, stack);
    }
    
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z,
			Block block) {
		int meta = world.getBlockMetadata(x, y, z);
		if(!world.isRemote)
		{
		if (meta == 0 || meta == 2) {
			if (world.getBlock(x, y + 1, z) == Blocks.air) {
				world.setBlockToAir(x, y, z);
				world.markBlockForUpdate(x, y, z);
				return;
			}
			((TileInfuserBase)world.getTileEntity(x, y, z)).onNeighborBlockChange(world, x, y, z, block);
		} else if (meta == 1 || meta == 3) {
			if (world.getBlock(x, y - 1, z) == Blocks.air) {
				world.setBlockToAir(x, y, z);
				world.markBlockForUpdate(x, y, z);
				return;
			}
		}
		}

		super.onNeighborBlockChange(world, x, y, z, block);
	}
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
    	int meta = world.getBlockMetadata(x, y, z);
    	if(meta == 0 || meta == 2)
    	{
    		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
    	}
    	else if(meta == 1 || meta == 3)
    	{
    		setBlockBounds(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    	}
    	super.setBlockBoundsBasedOnState(world, x, y, z);
    }
	
    
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
    	
    	list.add(new ItemStack(item, 1, 0));
    	list.add(new ItemStack(item, 1, 2));
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		if(meta == 0 || meta == 1)
		{
		return new TileInfuser();
		}
		else if(meta == 2 || meta == 3)
		{
			return new TileInfuserChamber();
		}
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof IToolWrench)
		{
			if(tile instanceof TileInfuserBase)
			{
				if(side != 1)
				{
				((TileInfuserBase) tile).waterInput = ForgeDirection.values()[side];
				}
			}
		}
		else if(tile instanceof TileInfuserBase)
		{
			FMLNetworkHandler.openGui(player, ForestBotany.instance, 0, world, x, y, z);
			return true;
		}
		return false;
	}
    
}
