package nedelosk.forestday.common.items.base;

import nedelosk.forestday.common.core.managers.FItemManager;
import nedelosk.forestday.common.core.registry.FRegistry;
import net.minecraft.item.ItemBucket;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemWoodBucket extends ItemBucket {

	public ItemWoodBucket(Block contents, String name) {
		super(contents);
		setUnlocalizedName(FRegistry.setUnlocalizedItemName(name, "fd"));
		setTextureName("forestday:bucket.wood." + contents.getUnlocalizedName().replaceAll("tile.", ""));
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		int x = 0;
		int y = 0;
		int z = 0;
		Block block = null;
		int meta = 0;
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, this ==  FItemManager.Bucket_Wood.item());
        if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            x = movingobjectposition.blockX;
            y = movingobjectposition.blockY;
            z = movingobjectposition.blockZ;
            block = world.getBlock(x, y, z);
            meta = world.getBlockMetadata(x, y, z);
        }
		ItemStack result = super.onItemRightClick(itemStack, world, player);

		if (result.getItem() == Items.bucket) {

			return new ItemStack(FItemManager.Bucket_Wood.item());
		}
		else if (result.getItem() == Items.water_bucket)
			return new ItemStack(FItemManager.Bucket_Wood_Water.item());
		else if (result.getItem() == Items.lava_bucket) {
			player.setFire(10);
			return null;
		} else{
			if(block != null){
				if(!world.isRemote){
		            if (!world.canMineBlock(player, x, y, z)){
		                return new ItemStack(FItemManager.Bucket_Wood.item());
		            }
		            
		            if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, itemStack))
		            {
		                return new ItemStack(FItemManager.Bucket_Wood.item());
		            }
		            world.setBlock(x, y, z, block, meta, 2);
				}
			}
			return new ItemStack(FItemManager.Bucket_Wood.item());
		}
	}
}
