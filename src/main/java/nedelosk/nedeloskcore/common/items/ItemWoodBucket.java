package nedelosk.nedeloskcore.common.items;

import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import nedelosk.nedeloskcore.common.core.registry.ObjectRegistry;
import net.minecraft.item.ItemBucket;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWoodBucket extends ItemBucket {

	public String name;
	
    public ItemWoodBucket(Block contents, String name)
    {
    	super(contents);
        this.name = name;
        this.setTextureName("nedeloskcore:" + "bucket.wood." + contents.getUnlocalizedName().replaceAll("tile.", ""));
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack arg0) {
    	return NRegistry.setUnlocalizedItemName(name, "nc");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        ItemStack result = super.onItemRightClick(itemStack, world, player);

        if(result.getItem() == Items.bucket) {

            return new ItemStack(ObjectRegistry.woodBucket);
        }
        
        if(result.getItem() == Items.water_bucket) return new ItemStack(ObjectRegistry.woodBucketWater);
        if(result.getItem() == Items.lava_bucket){
        	player.setFire(10);
        	return new ItemStack(ObjectRegistry.woodBucketWater, 0);
        }
        return result;
    }
}
