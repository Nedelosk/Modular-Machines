package de.nedelosk.modularmachines.api.property;

import com.google.gson.JsonArray;

import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.utils.JsonUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class PropertyRecipeItems extends PropertyBase<RecipeItem[], NBTTagCompound, IRecipe> implements IPropertyJson<RecipeItem[], NBTTagCompound, IRecipe, JsonArray> {

	public PropertyRecipeItems(String name) {
		super(name, RecipeItem[].class, new RecipeItem[1]);
	}

	@Override
	public JsonArray writeToJson(RecipeItem[] objects) {
		return JsonUtils.writeRecipeItem(objects);
	}

	@Override
	public RecipeItem[] readFromJson(JsonArray object) {
		return JsonUtils.parseRecipeItem(object);
	}

	@Override
	public NBTTagCompound writeToNBT(IRecipe state, RecipeItem[] value) {
		NBTTagCompound nbtTag = new NBTTagCompound();
		NBTTagList tagList = new NBTTagList();
		int maxIndex = 0;
		for(RecipeItem item : value){
			if(item != null){
				if(item.index > maxIndex){
					maxIndex=item.index;
				}
				NBTTagCompound nbtTagItem = new NBTTagCompound();
				item.writeToNBT(nbtTagItem);
				tagList.appendTag(nbtTagItem);
			}
		}
		nbtTag.setTag("Items", tagList);
		nbtTag.setInteger("MaxIndex", maxIndex);
		return nbtTag;
	}

	@Override
	public RecipeItem[] readFromNBT(NBTTagCompound nbt, IRecipe state) {
		NBTTagList tagList = nbt.getTagList("Items", 10);
		RecipeItem[] items = new RecipeItem[nbt.getInteger("MaxIndex") + 1];
		for(int i = 0;i < tagList.tagCount();i++){
			NBTTagCompound nbtTag = tagList.getCompoundTagAt(i);
			RecipeItem item = RecipeItem.loadFromNBT(nbtTag);
			items[item.index] = item;
		}
		return items;
	}
}
