package de.nedelosk.techtree.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

public class TechTreeCategoryList {

	public int minDisplayColumn;
	public int minDisplayRow;
	public int maxDisplayColumn;
	public int maxDisplayRow;
	public ResourceLocation icon;
	public ResourceLocation background;
	public String key;

	public TechTreeCategoryList(String key, ResourceLocation icon, ResourceLocation background) {
		this.key = key;
		this.icon = icon;
		this.background = background;
	}

	public Map<String, TechTreeEntry> entrys = new HashMap<String, TechTreeEntry>();
}
