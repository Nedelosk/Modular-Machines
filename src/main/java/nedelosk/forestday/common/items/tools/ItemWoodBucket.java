package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import nedelosk.forestday.common.registrys.ForestdayRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWoodBucket extends ItemBucket {

	public String name;
	
    public ItemWoodBucket(Block contents, String name)
    {
    	super(contents);
        this.name = name;
        this.setTextureName("forestday:buckets/" + "bucket.wood." + contents.getUnlocalizedName().replaceAll("tile.", ""));
        this.setCreativeTab(Tabs.tabForestdayItems);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack arg0) {
    	return ForestdayRegistry.setUnlocalizedItemName(name);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        ItemStack result = super.onItemRightClick(itemStack, world, player);

        if(result.getItem() == Items.bucket) {

            return new ItemStack(ForestdayItemRegistry.woodBucket);
        }
        
        if(result.getItem() == Items.water_bucket) return new ItemStack(ForestdayItemRegistry.woodBucketWater);
        if(result.getItem() == Items.lava_bucket){
        	player.setFire(10);
        	return new ItemStack(ForestdayItemRegistry.woodBucketWater, 0);
        }
        return result;
    }
}
