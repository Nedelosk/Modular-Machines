package nedelosk.nedeloskcore.common.items;

import nedelosk.nedeloskcore.common.core.registry.NCItemManager;
import nedelosk.nedeloskcore.common.core.registry.NCRegistry;
import net.minecraft.item.ItemBucket;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWoodBucket extends ItemBucket {
	
    public ItemWoodBucket(Block contents, String name)
    {
    	super(contents);
        setUnlocalizedName(NCRegistry.setUnlocalizedItemName(name, "nc"));
        this.setTextureName("nedeloskcore:" + "bucket.wood." + contents.getUnlocalizedName().replaceAll("tile.", ""));
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        ItemStack result = super.onItemRightClick(itemStack, world, player);

        if(result.getItem() == Items.bucket) {

            return new ItemStack(NCItemManager.Bucket_Wood.item());
        }
        
        if(result.getItem() == Items.water_bucket) return new ItemStack(NCItemManager.Bucket_Wood_Water.item());
        if(result.getItem() == Items.lava_bucket){
        	player.setFire(10);
        	return new ItemStack(NCItemManager.Bucket_Wood_Water.item(), 0);
        }
        return result;
    }
}
