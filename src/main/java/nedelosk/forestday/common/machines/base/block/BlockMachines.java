package nedelosk.forestday.common.machines.base.block;

import java.lang.reflect.Constructor;
import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.client.proxy.ClientProxy;
import nedelosk.forestday.common.core.ForestDay;
import nedelosk.nedeloskcore.common.blocks.BlockContainerForest;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.utils.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachines extends BlockContainerForest {

	public Class<? extends TileMachineBase>[] tiles;
	public String blockName;
	public IIcon[][][] icons;
	
	public BlockMachines(String blockName, Class<? extends TileMachineBase>... tiles) {
		super(Material.iron, Tabs.tabForestdayBlocks);
		setStepSound(soundTypeMetal);
		this.blockName = blockName;
		setBlockName(blockName);
		this.tiles = tiles;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
		if(!world.isRemote)
		{
			ItemUtils.dropItems(world, x, y, z);
		}
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
        try
        {
            TileMachineBase i = null;
            if (tiles[meta] != null)
            {
                Constructor<? extends TileMachineBase> itemCtor = tiles[meta].getConstructor();
                i = itemCtor.newInstance();
            }
            if (i != null)
            {
                return i;
            }
            return null;
        }
        catch (Exception e)
        {
            FMLLog.log(Level.ERROR, e, "Caught an exception during TileEntity registration in Forestday:BlockMachines " + tiles[meta].getName());
            throw new LoaderException(e);
        }
	}
	
	@Override
	public void registerBlockIcons(IIconRegister IIconRegister) {
		try{
		icons = new IIcon[tiles.length][2][6];
		for(int i = 0;i < tiles.length;i++)
		{
			for(int a = 0;a < 2;a++)
			{
			for(int r = 0;r < 6;r++)
			{
				icons[i][a][r] = IIconRegister.registerIcon("forestday:machines/" + tiles[0].getConstructor().newInstance().getMachineName() + "/" + getFileFromTier(i) + "/" + getNameFromSide(r) + ((a == 1) ? "_active" : ""));
					/*File file0 = new File(Loader.instance().getConfigDir().getPath() , "machines");
					File file1 = new File(file0 , tiles[0].getConstructor().newInstance().getMachineName());
					File file2 = new File(file1 , getFileFromTier(i));
					File file4 = new File(file2, getNameFromSide(r) + ((a == 1) ? "_active" : "") + ".png");
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("Int", i);
					try {
			            file4.getParentFile().mkdirs();
			            if (!file4.exists())
			            {
			                if (!file4.createNewFile())
			                    return;
			            }
					FileOutputStream fileoutputstream = new FileOutputStream(file4);
					CompressedStreamTools.writeCompressed(tag, fileoutputstream);
					fileoutputstream.close();
					}
					catch (Exception e) {
					}*/
			}
			}
		}
		}
		catch(Exception e){
			FMLLog.log(Level.ERROR, e, "ERROR Textures");
		}
	}
	
	public String getNameFromSide(int side)
	{
		switch (side) {
		case 0:
			return "down";
		case 1:
			return "top";
		case 2:
			return "back";
		case 3:
			return "front";
		case 4:
			return "side";
		case 5:
			return "side";
		default:
			return null;
		}
	}
	
	public String getFileFromTier(int tier)
	{
		switch (tier) {
		case 0:
			return "bricks";
		case 1:
			return "iron";
		case 2:
			return "base";
		case 3:
			return "steel";
		case 4:
			return "forest_steel";
		default:
			return "base";
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileMachineBase)
		{
		player.openGui(ForestDay.instance, 0, player.worldObj, x, y, z);
		return true;
		}
		GuiScreen.isShiftKeyDown();
		return false;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0;i < tiles.length;i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	  @Override
	  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
	    super.onBlockPlacedBy(world, x, y, z, player, stack);
	    int heading = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
	    TileMachineBase tile = (TileMachineBase) world.getTileEntity(x, y, z);
	    tile.facing = getFacingForHeading(heading);
	    if(world.isRemote) {
	      return;
	    }
	    world.markBlockForUpdate(x, y, z);
	  }
	  
	  @Override
	  @SideOnly(Side.CLIENT)
	  public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {

	    TileEntity tile = world.getTileEntity(x, y, z);
	    int facing = 0;
	    if(tile instanceof TileMachineBase) {
	    	TileMachineBase ma = (TileMachineBase) tile;
	      facing = ma.facing;
	    }
	    if(((TileMachineBase)world.getTileEntity(x, y, z)).isWorking) {
	      return icons[world.getBlockMetadata(x, y, z)][1][ClientProxy.sideAndFacingToSpriteOffset[blockSide][facing]];
	    } else {
	      return icons[world.getBlockMetadata(x, y, z)][0][ClientProxy.sideAndFacingToSpriteOffset[blockSide][facing]];
	    }
	  }

	  @Override
	  @SideOnly(Side.CLIENT)
	  public IIcon getIcon(int blockSide, int blockMeta) {
	    return icons[blockMeta][0][blockSide];
	  }

	  protected short getFacingForHeading(int heading) {
	    switch (heading) {
	    case 0:
	      return 2;
	    case 1:
	      return 5;
	    case 2:
	      return 3;
	    case 3:
	    default:
	      return 4;
	    }
	  }

	  @Override
	  public void onBlockAdded(World world, int x, int y, int z) {
	    super.onBlockAdded(world, x, y, z);
	    world.markBlockForUpdate(x, y, z);
	  }
}
