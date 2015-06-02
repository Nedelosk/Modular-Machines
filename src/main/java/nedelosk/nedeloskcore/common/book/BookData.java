package nedelosk.nedeloskcore.common.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nedelosk.nedeloskcore.api.book.BookCategory;
import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.api.book.Knowledge;
import nedelosk.nedeloskcore.api.book.BookLevel;
import nedelosk.nedeloskcore.common.core.Log;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class BookData {

	public Map<String, BookCategory> map = new HashMap();
	
	public final String tag;
	
	public String getCategoryName(String tag)
	{
		return "nc." + getUnlocalizedName() +".category." + tag;
	}
	
	public BookData(String unlocalizedName) {
		this.tag = unlocalizedName;
	}
	
	public BookCategory getCategory(String key) {
		return map.get(key);
	}
	
	public void registerCategory(String key, ResourceLocation icon)
	{
		map.put(key, new BookCategory(icon));
	}
	
	public String getUnlocalizedName() {
		return tag;
	}
	
}
