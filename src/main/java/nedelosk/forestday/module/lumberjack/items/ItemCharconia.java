package nedelosk.forestday.module.lumberjack.items;

import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.common.book.BookData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCharconia extends ItemForestday {
	
	public ItemCharconia() {
		super("charconia", Tabs.tabForestdayItems);
		this.setMaxStackSize(1);
		this.setTextureName("forestday:book_charconia");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		//player.openGui(Forestday.instance, 10, world, 0, 0, 0);
		Forestday.proxy.getBookManager().unlockEntry(player, "baseWood");
		Forestday.proxy.getBookManager().unlockKnowledge(player, NCoreApi.basicKnowledge.unlocalizedName);
		return stack;
	}
}
