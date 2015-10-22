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
	
	/*@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		boolean isHarvestet = false;
        player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
        player.addExhaustion(0.025F);
        if(player.getCurrentEquippedItem().getItem() instanceof ItemWrench){
        	ItemWrench wrench = (ItemWrench) player.getCurrentEquippedItem().getItem();
	        TileEntity tile = world.getTileEntity(x, y, z);
	        if(tile instanceof IModularTileEntity){
	        	IModularTileEntity modularTile = (IModularTileEntity) tile;
	        	nedelosk.modularmachines.api.materials.Material mat = MaterialManager.getMaterial(player.getCurrentEquippedItem());
	        	if(mat != null && mat.hasStats(Stats.MACHINE)){
	        		int wrenchTier = ((MachineState)mat.getStats(Stats.MACHINE)).tier();
	        		int machineTier = modularTile.getModular().getTier();
	        		if(wrenchTier >= machineTier){
	                    ArrayList<ItemStack> items = Lists.newArrayList();
	                    ItemStack itemstack = this.createStackedBlock(meta);

	                    NBTTagCompound nbtTag = new NBTTagCompound();
	                    NBTTagCompound nbtTagMachine = new NBTTagCompound();
	                    modularTile.getModular().writeToNBT(nbtTagMachine);
	                    nbtTag.setTag("Machine", nbtTagMachine);
	                    nbtTag.setString("MachineName", modularTile.getModular().getName());
	                    itemstack.setTagCompound(nbtTag);
	                    
	                    if (itemstack != null)
	                    {
	                        items.add(itemstack);
	                    }

	                    ForgeEventFactory.fireBlockHarvesting(items, world, this, x, y, z, meta, 0, 1.0f, true, player);
	                    for (ItemStack is : items)
	                    {
	                        this.dropBlockAsItem(world, x, y, z, is);
	                    }
	                    isHarvestet = true;
	        		}
	        	}
	        }
        }
        if(!isHarvestet)
        {
	        TileEntity tile = world.getTileEntity(x, y, z);
	        if(tile instanceof IModularTileEntity){
	        	IModularTileEntity modularTile = (IModularTileEntity) tile;
	        	for(Vector<ModuleStack> stacks : modularTile.getModular().getModules().values()){
	        		for(ModuleStack stack : stacks){
	        			ItemStack itemStack = stack.getItem();
	        			if(!(world.rand.nextInt(100 / modularTile.getModular().getTier()) == 0))
	        				dropBlockAsItem(world, x, y, z, itemStack);
	        		}
	        	}
	        }
        }
	}*/
	
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
