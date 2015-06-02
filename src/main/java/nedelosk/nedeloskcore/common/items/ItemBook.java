package nedelosk.nedeloskcore.common.items;

import nedelosk.forestbotany.common.core.ForestBotanyTab;
import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.nedeloskcore.common.book.BookData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBook extends ItemForestday {

	public BookData bookData;
	
	public ItemBook(String uln, BookData bookData) {
		super("book." + uln, Tabs.tabForestdayItems);
		this.setMaxStackSize(1);
		this.bookData = bookData;
		setCreativeTab(ForestBotanyTab.instance);
	}
	
	@Override
	public void registerIcons(IIconRegister p_94581_1_) {
		super.registerIcons(p_94581_1_);
	}
	
	public BookData getBookData() {
		return bookData;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.openGui(Forestday.instance, 0, world, 0, 0, 0);
		return stack;
	}
}
