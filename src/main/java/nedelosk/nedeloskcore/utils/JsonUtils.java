package nedelosk.nedeloskcore.utils;

import org.apache.logging.log4j.Level;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.nedeloskcore.api.Log;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class JsonUtils {

	public static ResourceLocation parseLocation(JsonElement json, String name){
		String[] location = json.getAsJsonObject().get(name).getAsString().split(":");
		return new ResourceLocation(location[0], "textures/" + location[1]);
	}
	
	public static ItemStack parseItem(JsonElement json, String itemName){
		String[] names = json.getAsJsonObject().get(itemName).getAsString().split(":", 4);
		Item item = GameRegistry.findItem(names[0], names[1]);
		int meta = (names.length >= 3 ? Integer.parseInt(names[2]) : 0);
		ItemStack stack = new ItemStack(item, 1, meta);
		if(names.length == 4)
		{
			try{
				stack.setTagCompound((NBTTagCompound) JsonToNBT.func_150315_a(names[3]));
			}catch(Exception e){
				Log.log("NedeloskCore", Level.ERROR, "Fail to parse : " + itemName);
			}
		}
		return stack;
	}
	
	public static JsonElement writeLocation(ResourceLocation location){
		return new JsonPrimitive(location.toString().replace("textures/", ""));
	}
	
	public static JsonElement writeItem(ItemStack item){
		String itemName = GameData.itemRegistry.getNameForObject(item.getItem()) + ":" + item.getItemDamage();
		if(item.hasTagCompound())
			itemName += ":" + item.getTagCompound().toString();
		return new JsonPrimitive(itemName);
	}
	
}
