package nedelosk.forestday.module.lumberjack.items;

import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.nedeloskcore.common.book.BookData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemNoteofLumberjack extends ItemForestday {
	
	public ItemNoteofLumberjack() {
		super("lumberjack", Tabs.tabForestdayItems);
		this.setMaxStackSize(1);
		this.setTextureName("forestday:book_lumberjack");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.openGui(Forestday.instance, 11, world, 0, 0, 0);
		return stack;
	}
}
