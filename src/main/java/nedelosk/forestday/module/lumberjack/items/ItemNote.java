package nedelosk.forestday.module.lumberjack.items;

import java.util.Collection;
import java.util.List;

import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.items.base.ItemForestday;
import nedelosk.nedeloskcore.api.book.BookCategory;
import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.common.book.BookData;
import nedelosk.nedeloskcore.common.book.BookDatas;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import nedelosk.nedeloskcore.common.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemNote extends ItemForestday {

	public ItemNote() {
		super("note", /*NedeloskRegistry.tab*/ null);
		this.setTextureName("forestday:note");
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		Collection books = BookDatas.bookdatas.values();
		for(Object book : books)
		{
			Collection cats = ((BookData)book).map.values();
			for(Object cat : cats)
			{
				Collection entrys = ((BookCategory)cat).entrys.values();
				for(Object entry : entrys)
				{
						ItemStack stack = new ItemStack(item);
						setEntry(stack, (BookEntry)entry);
						list.add(stack);
				}
			}
		}
	}
	
	public void setEntry(ItemStack stack, BookEntry entry)
	{
		if(!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		if(!stack.getTagCompound().hasKey("Note"))
		{
			stack.getTagCompound().setTag("Note", new NBTTagCompound());
		}
		stack.getTagCompound().getCompoundTag("Note").setString("Key", entry.key);
	}
	
	public BookEntry getEntry(ItemStack stack)
	{
		if(stack.getTagCompound().getCompoundTag("Note").getString("Key") == null)
		{
			return null;
		}
		return BookDatas.getEntry(stack.getTagCompound().getCompoundTag("Note").getString("Key"));
		
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
	
		if(stack.getTagCompound() != null)
		{
			BookEntry entry = BookDatas.getEntry(stack.getTagCompound().getCompoundTag("Note").getString("Key"));
			if(!Forestday.proxy.getBookManager().isEntryUnlock(player.getDisplayName(), entry.key))
			{
				Forestday.proxy.getBookManager().unlockEntry(player, entry.key);
			}
		}
		return stack;
		
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean arg3) {
		NBTTagCompound tag = stack.getTagCompound();
		if(tag != null && tag.hasKey("Note"))
		{
			String entry = stack.getTagCompound().getCompoundTag("Note").getString("Key");
			list.add(EnumChatFormatting.GRAY + "nc.entry." + entry);
		}
	}

}
