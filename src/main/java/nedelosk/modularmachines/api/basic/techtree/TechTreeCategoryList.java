package nedelosk.modularmachines.api.basic.techtree;

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
	
	public TechTreeCategoryList(ResourceLocation icon, ResourceLocation background) {
		this.icon = icon;
		this.background = background;
	}

	public Map<String, TechTreeEntry> entrys = new HashMap<String, TechTreeEntry>();
		
}
