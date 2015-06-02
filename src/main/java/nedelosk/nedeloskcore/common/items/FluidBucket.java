package nedelosk.nedeloskcore.common.items;

import nedelosk.forestday.api.Tabs;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class FluidBucket extends ItemBucket {

	public FluidBucket(Block fluid, String nameFluid) {
		super(fluid);
		this.setContainerItem(Items.bucket);
		this.setCreativeTab(Tabs.tabForestdayItems);
		this.setTextureName("forestday:bucket_" + nameFluid);
		this.setUnlocalizedName("bucket." + nameFluid);
	}

}
