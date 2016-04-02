package de.nedelosk.techtree.common.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import de.nedelosk.techtree.api.TechTreeCategories;
import de.nedelosk.techtree.api.TechTreeCategoryList;
import de.nedelosk.techtree.api.TechTreeEntry;
import de.nedelosk.techtree.common.TechTree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraftforge.common.config.Configuration;

public class TechTreeConfigs {

	public static HashMap<String, Boolean> activeLanguages = new HashMap<String, Boolean>();

	public static void load() {
		TechTree.config_TechTree.load();
	}

	public static void init() {
		load();
		Configuration config = TechTree.config_TechTree;
		Iterator iterator = Minecraft.getMinecraft().getLanguageManager().getLanguages().iterator();
		while (iterator.hasNext()) {
			Language language = (Language) iterator.next();
			if (language.getLanguageCode().equals("en_US")) {
				activeLanguages.put(language.getLanguageCode(), true);
			} else {
				activeLanguages.put(language.getLanguageCode(), config.get("techtreepage languages", language.getLanguageCode(), false).getBoolean());
			}
		}
		save();
	}

	public static void postInit() {
		load();
		Configuration config = TechTree.config_TechTree;
		for ( Map.Entry<String, TechTreeCategoryList> entry : TechTreeCategories.entryCategoriesTemp.entrySet() ) {
			for ( TechTreeEntry tEntry : entry.getValue().entrys.values() ) {
				if (config.get(tEntry.category, tEntry.key, true).getBoolean()) {
					if (tEntry.parents != null) {
						for ( String tParent : tEntry.parents ) {
							if (TechTreeCategories.entryCategoriesDisabled.contains(tParent)) {
								TechTreeCategories.entryCategoriesDisabled.add(tEntry.key);
							}
						}
					}
					if (TechTreeCategories.entryCategories == null) {
						TechTreeCategories.entryCategories = new LinkedHashMap<String, TechTreeCategoryList>();
					}
					if (TechTreeCategories.getEntryList(tEntry.category) == null) {
						TechTreeCategoryList list = TechTreeCategories.entryCategoriesTemp.get(tEntry.category);
						TechTreeCategories.registerCategory(list.key, list.icon, list.background);
					}
					TechTreeCategories.addEntry(tEntry);
				} else {
					TechTreeCategories.entryCategoriesDisabled.add(tEntry.key);
				}
			}
		}
		save();
	}

	public static void save() {
		TechTree.config_TechTree.save();
	}
}
