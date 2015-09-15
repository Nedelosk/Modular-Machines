package nedelosk.modularmachines.api.basic.techtree;

import java.lang.reflect.Type;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import nedelosk.modularmachines.client.techtree.utils.TechEntryData;
import nedelosk.nedeloskcore.utils.JsonUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class TechTreeEntry 
{
	public final String key;
	
	public final String category;
	
    public String[] parents = null;
    
    public String[] siblings = null;
    
    public final int displayColumn;

    public final int displayRow;
    
    public ItemStack icon_item;
    
    public ResourceLocation icon_resource;
    
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
    	return "mm.techtree_entry_name."+key;
    }
    
    public String getText()
    {
    	return "mm.techtree_entry_text."+key;
    }
    
    public String getTextTranslated()
    {
    	TechEntryData.checkLanguage();
    	if(TechEntryData.getTranslateData(key) != null)
    		return TechEntryData.getTranslateData(key).text;
    	else
    		return getText();
    }
    
    public String getNameTranslated()
    {
    	TechEntryData.checkLanguage();
    	if(TechEntryData.getTranslateData(key) != null)
    		return TechEntryData.getTranslateData(key).name;
    	else
    		return getName();
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
	
	public static class Deserializer implements JsonDeserializer<TechTreeEntry>{
		
		public TechTreeEntry parseEntry(JsonElement json){
			JsonObject object = json.getAsJsonObject();
			String key = object.get("key").getAsString();
			String category = object.get("category").getAsString();
			String[] parents;
			if(json.getAsJsonObject().has("parents"))
			{
				parents = new String[object.get("parents").getAsJsonArray().size()];
				for(int i = 0;i < parents.length;i++)
				{
					JsonElement element = object.get("parents").getAsJsonArray().iterator().next();
					parents[i] = element.getAsString();
				}
			}
			String[] siblings;
			if(json.getAsJsonObject().has("siblings"))
			{
				siblings = new String[object.get("siblings").getAsJsonArray().size()];
				for(int i = 0;i < siblings.length;i++)
				{
					JsonElement element = object.get("siblings").getAsJsonArray().iterator().next();
					siblings[i] = element.getAsString();
				}
			}
			int displayColumn = json.getAsJsonObject().get("column").getAsInt();
			int displayRow = json.getAsJsonObject().get("row").getAsInt();
			TechTreePage[] pages = null;
			if(json.getAsJsonObject().has("pages"))
			{
				pages = parsePages(json);
			}
			ItemStack iconItem = null;
			ResourceLocation icon = null;
			if(json.getAsJsonObject().has("iconItem"))
			{
				iconItem = JsonUtils.parseItem(json, "iconItem");
			}
			else if(json.getAsJsonObject().has("icon"))
			{
				icon = JsonUtils.parseLocation(json, "icon");
			}
			int techPoints = 0;
			if(json.getAsJsonObject().has("techpoints"))
			{
				techPoints = json.getAsJsonObject().get("techpoints").getAsInt();
			}
			TechPointTypes techPointType = null;
			if(json.getAsJsonObject().has("techpointTypes"))
			{
				techPoints = json.getAsJsonObject().get("techpoints").getAsInt();
			}
			if(techPointType == null)
			{
				techPointType = TechPointTypes.EASY;
			}
			boolean isAutoUnlock;
			if(json.getAsJsonObject().has("isAutoUnlock"))
			{
				isAutoUnlock = json.getAsJsonObject().get("isAutoUnlock").getAsBoolean();
			}
			else
				isAutoUnlock = false;
			boolean isConcealed;
			if(json.getAsJsonObject().has("isConcealed"))
			{
				isConcealed = json.getAsJsonObject().get("isConcealed").getAsBoolean();
			}
			else
				isConcealed = false;
			TechTreeEntry entry = new TechTreeEntry(key, category, techPoints, techPointType, displayColumn, displayRow, icon);
			if(isAutoUnlock)
				entry.isAutoUnlock();
			if(isConcealed)
				entry.isConcealed();
			if(iconItem != null)
				entry.icon_item = iconItem;
			if(pages != null)
				entry.setPages(pages);
			return entry;
		}
		
		public TechPointTypes parseTechPointType(JsonElement json)
		{
			for(int i = 0;i < TechPointTypes.values().length;i++)
			{
				TechPointTypes type = TechPointTypes.values()[i];
				if(type.name().toLowerCase().equals(json.getAsJsonObject().get("techpointTypes").getAsString()))
					return type;
			}
			return null;
		}
		
		public TechTreePage[] parsePages(JsonElement json){
			JsonObject object = json.getAsJsonObject();
			if(object.get("pages").isJsonArray())
			{
				JsonArray array = object.get("pages").getAsJsonArray();
				TechTreePage[] pages = new TechTreePage[array.size()];
				Iterator<JsonElement> iterator = array.iterator();
				for(int i = 0;i < pages.length;i++)
				{
					JsonElement element = iterator.next();
					pages[i] = parsePage(element, i);
				}
				return pages;
			}
			else
				return null;
		}
		
		public TechTreePage parsePage(JsonElement json, int pages){
			JsonObject object = json.getAsJsonObject();
			switch (object.get("type").getAsString()) {
			case "text":
				return new TechTreePage(object.get("text").getAsString(), pages);
			case "image":
				return new TechTreePage(JsonUtils.parseLocation(json, "image"), object.get("caption").getAsString(), pages);
			case "text_concealed":
				return new TechTreePage(object.get("entry").getAsString(), object.get("text").getAsString(), pages);
			case "smelting":
				return new TechTreePage(JsonUtils.parseItem(json, "input"), pages);
			case "crafting":
				return new TechTreePage(parseRecipe(json, "output"), pages);
			default:
				return null;
			}
		}
		
		public IRecipe parseRecipe(JsonElement json, String itemName){
            for (Object craft : CraftingManager.getInstance().getRecipeList()) {
                if (craft instanceof IRecipe) {
                    IRecipe theCraft = (IRecipe) craft;
                    if (theCraft.getRecipeOutput() != null && areEqual(theCraft.getRecipeOutput(), JsonUtils.parseItem(json, itemName))) {
                        return theCraft;
                    }
                }
            }
            return null;
		}
		
	    public static boolean areEqual(ItemStack stack, ItemStack stack2) {
	        if (stack == null || stack2 == null) return false;
	    	return stack.isItemEqual(stack2);
	    }
		
		@Override
		public TechTreeEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return parseEntry(json);
		}
	}
	
	public static class Serializer implements JsonSerializer<TechTreeEntry>{
		
		public JsonElement writeEntry(TechTreeEntry entry){
			JsonObject object = new JsonObject();
			object.addProperty("key", entry.key);
			object.addProperty("category", entry.category);
			if(entry.parents != null){
				JsonArray parents = new JsonArray();
				for(String parent : entry.parents){
					parents.add(new JsonPrimitive(parent));
				}
				object.add("parents", parents);
			}
			if(entry.siblings != null){
			JsonArray siblings = new JsonArray();
			for(String sibling : entry.siblings){
				siblings.add(new JsonPrimitive(sibling));
			}
			object.add("siblings", siblings);
			}
			object.addProperty("column", entry.displayColumn);
			object.addProperty("row", entry.displayRow);
			if(entry.pages != null)
				object.add("pages", writePages(entry.pages));
			if(entry.icon_item != null){
				object.add("iconItem", JsonUtils.writeItem(entry.icon_item));
			}
			else if(entry.icon_resource != null){
				object.add("icon", JsonUtils.writeLocation(entry.icon_resource));
			}
			object.addProperty("techpoints", entry.techPoints);
			object.add("techpointTypes", writeTechPointType(entry.techPointType));
			object.addProperty("isAutoUnlock", entry.isAutoUnlock ? entry.isAutoUnlock : false);
			object.addProperty("isConcealed", entry.isConcealed ? entry.isConcealed : false);
			return object;
		}
		
		public JsonElement writeTechPointType(TechPointTypes type)
		{
			return new JsonPrimitive(type.name().toLowerCase());
		}
		
		public JsonElement parseRecipe(IRecipe recipe){
            return JsonUtils.writeItem(recipe.getRecipeOutput());
            
		}
		
		public JsonArray writePages(TechTreePage[] pages){
			JsonArray array = new JsonArray();
			for(TechTreePage page : pages)
				array.add(writePage(page));
			return array;
		}
		
		public JsonElement writePage(TechTreePage page){
			JsonObject object = new JsonObject();
			String type;
			switch (page.type) {
			case TEXT:
				object.addProperty("text", page.text);
				type = "text";
				break;
			case IMAGE:
				object.add("text", JsonUtils.writeLocation(page.image));
				type = "image";
				break;
			case TEXT_CONCEALED:
				object.addProperty("text", page.text);
				type = "text_concealed";
				break;
			case SMELTING:
				object.add("input", JsonUtils.writeItem((ItemStack) page.recipe));
				type = "smelting";
				break;
			case NORMAL_CRAFTING:
				object.add("output", JsonUtils.writeItem(page.recipeOutput));
				type = "crafting";
				break;
			default:
				object.addProperty("text", page.text);
				type = "text";
				break;
			}
			JsonPrimitive primitive = new JsonPrimitive(type);
			object.add("type", primitive);
			return object;
		}
		
		@Override
		public JsonElement serialize(TechTreeEntry src, Type typeOfSrc, JsonSerializationContext context) {
			return writeEntry(src);
		}
	}
	
}
