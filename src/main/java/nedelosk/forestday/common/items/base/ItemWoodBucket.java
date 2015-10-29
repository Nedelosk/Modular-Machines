package nedelosk.forestday.common.items.base;

import nedelosk.forestday.common.core.managers.FItemManager;
import nedelosk.forestday.common.core.registry.FRegistry;
import net.minecraft.item.ItemBucket;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWoodBucket extends ItemBucket {

	public ItemWoodBucket(Block contents, String name) {
		super(contents);
		setUnlocalizedName(FRegistry.setUnlocalizedItemName(name, "fd"));
		setTextureName("forestday:" + "bucket.wood." + contents.getUnlocalizedName().replaceAll("tile.", ""));
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		ItemStack result = super.onItemRightClick(itemStack, world, player);

		if (result.getItem() == Items.bucket) {

			return new ItemStack(FItemManager.Bucket_Wood.item());
		}

		if (result.getItem() == Items.water_bucket)
			return new ItemStack(FItemManager.Bucket_Wood_Water.item());
		if (result.getItem() == Items.lava_bucket) {
			player.setFire(10);
			return null;
		}
		return result;
	}
}
