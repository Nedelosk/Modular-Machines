package nedelosk.modularmachines.api.techtree;

import java.util.Collection;
import java.util.LinkedHashMap;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class TechTreeCategories {
	
	public static LinkedHashMap <String, TechTreeCategoryList> entryCategories = new LinkedHashMap <String, TechTreeCategoryList>();
	
	public static TechTreeCategoryList getEntryList(String key) {
		return entryCategories.get(key);
	}
	
	public static String getCategoryName(String key) {
		return StatCollector.translateToLocal("tc.research_category."+key);
	}
	
	public static TechTreeEntry getEntry(String key) {
		Collection rc = entryCategories.values();
		for (Object cat:rc) {
			Collection rl = ((TechTreeCategoryList)cat).entrys.values();
			for (Object ri:rl) {
				if (((TechTreeEntry)ri).key.equals(key)) return (TechTreeEntry)ri;
			}
		}
		return null;
	}
	
	public static void registerCategory(String key, ResourceLocation icon, ResourceLocation background) {
		if (getEntryList(key)==null) {
			TechTreeCategoryList rl = new TechTreeCategoryList(icon, background);
			entryCategories.put(key, rl);
		}
	}
	
	public static void addEntry(TechTreeEntry ri) {
		TechTreeCategoryList rl = getEntryList(ri.category);
		if (rl!=null && !rl.entrys.containsKey(ri.key)) {
			
			rl.entrys.put(ri.key, ri);
			
			if (ri.displayColumn < rl.minDisplayColumn) 
	        {
	            rl.minDisplayColumn = ri.displayColumn;
	        }

	        if (ri.displayRow < rl.minDisplayRow)
	        {
	            rl.minDisplayRow = ri.displayRow;
	        }

	        if (ri.displayColumn > rl.maxDisplayColumn)
	        {
	            rl.maxDisplayColumn = ri.displayColumn;
	        }

	        if (ri.displayRow > rl.maxDisplayRow)
	        {
	            rl.maxDisplayRow = ri.displayRow;
	        }
	        		}
	}
}
