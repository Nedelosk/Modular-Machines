package nedelosk.forestday.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.blocks.tiles.TileKiln;
import nedelosk.forestday.common.items.materials.ItemCampfire;
import nedelosk.forestday.common.managers.FItemManager;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

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
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(FItemManager.curb.item(), 1, 0);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileCampfire){
			TileCampfire campfire = (TileCampfire) tile;
			ret.add(campfire.getStackInSlot(4));
			return ret;
		}
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
    	if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileCampfire && ((TileCampfire)world.getTileEntity(x, y, z)).isWorking)
    	{
            int l = world.getBlockMetadata(x, y, z);
            float f = x + 0.5F;
            float f1 = y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = z + 0.5F;
            float f3 = 0.52F;
            float f4 = random.nextFloat() * 0.6F - 0.3F;

                world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f + f3 - 0.5, f1 + 0.3, f2 + f4, 0.0D, 0.0D, 0.0D);
                
                world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f + f3 - 0.5, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                
                world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
    	}
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
    	if(player.getCurrentEquippedItem() != null){
	    	TileEntity tile = world.getTileEntity(x, y, z);
	    	if(tile instanceof TileKiln){
	    		TileKiln kiln = (TileKiln) tile;
	    		if(player.getCurrentEquippedItem().getItem() instanceof ItemBucket && !kiln.tankLava.isFull() && ((kiln.tankLava.getFluidAmount() + 1000) <= kiln.tankLava.getCapacity())){
	    			IFluidContainerItem container = (IFluidContainerItem) player.getCurrentEquippedItem().getItem();
	    			if(player.getCurrentEquippedItem().getItem() == Items.lava_bucket){
	    				if(kiln.tankLava.fill(new FluidStack(FluidRegistry.LAVA, 1000), true) > 0){
	    					player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
	    				}
	    			}
	    			return true;
	    		}
	    	}else if(tile instanceof TileCampfire){
	    		TileCampfire campfile = (TileCampfire) tile;
	    		if(player.getCurrentEquippedItem().getItem() instanceof ItemCampfire){
	    			player.inventory.setInventorySlotContents(player.inventory.currentItem, campfile.setCampfireItem(player.getCurrentEquippedItem()));
	    			return true;
	    		}
	    	}
    	}
    	return super.onBlockActivated(world, x, y, z, player, side, par7, par8, par9);
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
