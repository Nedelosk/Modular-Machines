package nedelosk.modularmachines.api.basic.techtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.Level;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.nedeloskcore.api.Log;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class TechTreeCategories {
	
	public static LinkedHashMap <String, TechTreeCategoryList> entryCategories;
	public static final LinkedHashMap <String, TechTreeCategoryList> entryCategoriesTemp = new LinkedHashMap <String, TechTreeCategoryList>();
	public static final ArrayList<String> entryCategoriesDisabled = new ArrayList<String>();
	
	public static TechTreeCategoryList getEntryList(String key) {
		if(entryCategories != null)
			return entryCategories.get(key);
		return entryCategoriesTemp.get(key);
	}
	
	public static String getCategoryName(String key) {
		if(ModularMachinesApi.languageManager.getTranslateData(key) != null)
			return ModularMachinesApi.languageManager.getTranslateData(getEntryList(key)).getCatogory();
		return StatCollector.translateToLocal("mm.entry_category."+key);
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
			TechTreeCategoryList rl = new TechTreeCategoryList(key, icon, background);
			if(entryCategories == null)
				entryCategoriesTemp.put(key, rl);
			else
				entryCategories.put(key, rl);
		}
	}
	
	public static void addEntry(TechTreeEntry ri) {
		TechTreeCategoryList rl = getEntryList(ri.category);
		if(rl.entrys.containsKey(ri.key)){
			Log.log("ModularMachines", Level.INFO, "The Entry " + ri.key + " is Overwritten");
		}
		if (rl!=null) {
			
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
