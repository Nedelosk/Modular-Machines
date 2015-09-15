package nedelosk.modularmachines.common.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import nedelosk.modularmachines.api.basic.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategoryList;
import nedelosk.modularmachines.api.basic.techtree.TechTreeEntry;
import nedelosk.modularmachines.common.ModularMachines;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraftforge.common.config.Configuration;

public class TechTreeConfigs {
	
	public static HashMap<String, Boolean> activeLanguages = new HashMap<String, Boolean>();
	
	public static void load()
	{
		ModularMachines.configTechTree.load();
	}
	
	public static void init(){
		load();
		
		Configuration config = ModularMachines.configTechTree;
		
		Iterator iterator = Minecraft.getMinecraft().getLanguageManager().getLanguages().iterator();

		while (iterator.hasNext())
		{
			Language language = (Language) iterator.next();
			if(language.getLanguageCode().equals("en_US"))
				activeLanguages.put(language.getLanguageCode(), config.get("techtreepage languages", language.getLanguageCode(), true).getBoolean());
			activeLanguages.put(language.getLanguageCode(), config.get("techtreepage languages", language.getLanguageCode(), false).getBoolean());
		}
		save();
	}
	
	public static void postInit()
	{
		load();
		
		Configuration config = ModularMachines.configTechTree;
		
		for(Map.Entry<String, TechTreeCategoryList> entry : TechTreeCategories.entryCategoriesTemp.entrySet()){
			for(TechTreeEntry tEntry : entry.getValue().entrys.values()){
				if(config.get(tEntry.category, tEntry.key, true).getBoolean()){
					if(tEntry.parents != null)
					{
						for(String tParent : tEntry.parents){
							if(TechTreeCategories.entryCategoriesDisabled.contains(tParent)){
								TechTreeCategories.entryCategoriesDisabled.add(tEntry.key);
							}
						}
					}
					if(TechTreeCategories.entryCategories == null)
						TechTreeCategories.entryCategories = new LinkedHashMap<String, TechTreeCategoryList>();
					if(TechTreeCategories.getEntryList(tEntry.category) == null)
					{
						TechTreeCategoryList list = TechTreeCategories.entryCategoriesTemp.get(tEntry.category);
						TechTreeCategories.registerCategory(list.key, list.icon, list.background);
					}
					TechTreeCategories.addEntry(tEntry);
				}else{
					TechTreeCategories.entryCategoriesDisabled.add(tEntry.key);
				}
			}
		}
		
		save();
	}
	
	public static void save()
	{
		ModularMachines.configTechTree.save();
	}
	
}
