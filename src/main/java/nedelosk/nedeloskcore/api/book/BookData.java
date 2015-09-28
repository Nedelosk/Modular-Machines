package nedelosk.nedeloskcore.api.book;

import java.util.HashMap;
import java.util.Map;

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
