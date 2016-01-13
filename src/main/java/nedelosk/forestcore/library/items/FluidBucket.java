package nedelosk.forestcore.library.items;

import java.util.Locale;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class FluidBucket extends ItemBucket {

	public FluidBucket(Block fluid, String nameFluid) {
		super(fluid);
		this.setContainerItem(Items.bucket);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setTextureName(Loader.instance().activeModContainer().getModId().toLowerCase(Locale.ENGLISH) + ":bucket_" + nameFluid);
		this.setUnlocalizedName("bucket." + nameFluid);
	}
}
