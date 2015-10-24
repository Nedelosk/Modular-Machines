package nedelosk.modularmachines.common.blocks;

import java.util.List;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ModularMachineBlock extends ModularBlock {

	public ModularMachineBlock() {
		super(Material.iron);
		setStepSound(soundTypeMetal);
		setHardness(2.0F);
		setHarvestLevel("pickaxe", 1);
		setBlockName("modular");
		setBlockTextureName("iron_block");
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

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileModular();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		player.openGui(ModularMachines.instance, 0, player.worldObj, x, y, z);
		return true;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs ptab, List list) {
	}
	
	@Override
	protected void dropBlockAsItem(World p_149642_1_, int p_149642_2_, int p_149642_3_, int p_149642_4_, ItemStack p_149642_5_) {
		super.dropBlockAsItem(p_149642_1_, p_149642_2_, p_149642_3_, p_149642_4_, p_149642_5_);
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

}
