package nedelosk.modularmachines.api.techtree;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class TechTreeEntry 
{
	public final String key;
	
	public final String category;
	
    public String[] parents = null;
    
    public String[] siblings = null;
    
    public final int displayColumn;

    public final int displayRow;
    
    public final ItemStack icon_item;
    
    public final ResourceLocation icon_resource;
    
    private int techPoints;

    private TechPointTypes techPointType;
    
    private boolean isAutoUnlock;
    
    private boolean isConcealed;

	private TechTreePage[] pages = null;
	
	public TechTreeEntry(String key, String category)
    {
    	this.key = key;
    	this.category = category;   	
        this.icon_resource = null;
        this.icon_item = null;
        this.displayColumn = 0;
        this.techPoints = 1;
        this.displayRow = 0;
        this.techPointType = TechPointTypes.EASY;
        
    }
    
    public TechTreeEntry(String key, String category, int techPoints, TechPointTypes techPointType, int col, int row, ResourceLocation icon)
    {
    	this.key = key;
    	this.category = category;
        this.techPoints = techPoints;  	
        this.techPointType = techPointType;
        this.icon_resource = icon;
        this.icon_item = null;
        this.displayColumn = col;
        this.displayRow = row;
    }
    
    public TechTreeEntry(String key, String category, int techPoints, TechPointTypes techPointType, int col, int row, ItemStack icon)
    {
    	this.key = key;
    	this.category = category;
        this.techPoints = techPoints;  	
        this.techPointType = techPointType;
        this.icon_resource = null;
        this.icon_item = icon;
        this.displayColumn = col;
        this.displayRow = row;
    }
    
    public TechTreeEntry setParents(String... par)
    {
        this.parents = par;
        return this;
    }
    
    public TechTreeEntry setPages(TechTreePage... par)
    {
        this.pages = par;
        return this;
    }
    
    public TechTreePage[] getPages() {
		return pages;
	}

	public TechTreeEntry registerTechTreeEntry()
    {
        TechTreeCategories.addEntry(this);
        return this;
    }

    public String getName()
    {
    	return StatCollector.translateToLocal("mm.techtree_entry_name."+key);
    }
    
    public String getText()
    {
    	return StatCollector.translateToLocal("mm.techtree_entry_text."+key);
    }
    
    public boolean isAutoUnlock() {
		return isAutoUnlock;
	}
	
	public TechTreeEntry setAutoUnlock()
    {
        this.isAutoUnlock = true;
        return this;
    }
	
	public int getTechPoints() {
		return techPoints;
	}
	
	public TechPointTypes getTechPointType() {
		return techPointType;
	}
	
	public void setTechPointType(TechPointTypes techPointType) {
		this.techPointType = techPointType;
	}
	
	public TechTreeEntry setConcealed() {
        this.isConcealed = true;
        return this;
	}
	
	public boolean isConcealed() {
		return isConcealed;
	}
	
}
