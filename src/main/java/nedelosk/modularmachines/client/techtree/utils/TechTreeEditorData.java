package nedelosk.modularmachines.client.techtree.utils;

import java.util.ArrayList;
import java.util.HashMap;

import nedelosk.modularmachines.api.basic.techtree.TechPointTypes;
import nedelosk.modularmachines.api.basic.techtree.TechTreePage;
import nedelosk.modularmachines.client.techtree.utils.language.TechTreeEntryLanguageData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class TechTreeEditorData extends WorldSavedData {

	public String key = "";
	public String category = "";
	public int techPoints = 0;
	public String parents = "";
	public String siblings = "";
	public int column = 0;
	public int row = 0;
	public TechPointTypes pointType = TechPointTypes.VERY_EASY;
	public ItemStack icon_item;
	public boolean isAutoUnlock = false;
	public boolean isConcealed = false;
	public ArrayList<TechTreePage> pages = new ArrayList<TechTreePage>();
	public HashMap<String, TechTreeEntryLanguageData> languagData = new HashMap<String, TechTreeEntryLanguageData>(); 
	
	public String cIcon = "";
	public String cBackground = "";
	public String cKey = "";
	public String cKeyLanguag = "";
	
	public String languag = "en_US";
	
	public TechTreeEditorData() {
		super("techtree.editor");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtCompound) {
		nbtCompound.setString("key", key);
		nbtCompound.setString("category", category);
		nbtCompound.setInteger("techPoints", techPoints);
		nbtCompound.setString("parents", parents);
		nbtCompound.setString("siblings", siblings);
		nbtCompound.setInteger("column", column);
		nbtCompound.setInteger("row", row);
		if(pointType != null)
			nbtCompound.setInteger("pointType", pointType.ordinal());
		if(icon_item != null){
			NBTTagCompound item = new NBTTagCompound();
			icon_item.writeToNBT(item);
			nbtCompound.setTag("icon_item", item);
		}
		nbtCompound.setBoolean("isAutoUnlock", isAutoUnlock);
		nbtCompound.setBoolean("isConcealed", isConcealed);
		nbtCompound.setString("cIcon", cIcon);
		nbtCompound.setString("cBackground", cBackground);
		nbtCompound.setString("cKey", cKey);
		nbtCompound.setString("cKeyLanguag", cKeyLanguag);
		nbtCompound.setString("languag", languag);
		
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtCompound) {
		key = nbtCompound.getString("key");
		category = nbtCompound.getString("category");
		techPoints = nbtCompound.getInteger("techPoints");
		parents = nbtCompound.getString("parents");
		siblings = nbtCompound.getString("siblings");
		column = nbtCompound.getInteger("column");
		row = nbtCompound.getInteger("row");
		if(nbtCompound.hasKey("pointType"))
			pointType = TechPointTypes.values()[nbtCompound.getInteger("pointType")];
		if(nbtCompound.hasKey("icon_item")){
			NBTTagCompound item = nbtCompound.getCompoundTag("icon_item");
			icon_item = ItemStack.loadItemStackFromNBT(item);
		}
		isAutoUnlock = nbtCompound.getBoolean("isAutoUnlock");
		isConcealed = nbtCompound.getBoolean("isConcealed");
		cIcon = nbtCompound.getString("cIcon");
		cBackground = nbtCompound.getString("cBackground");
		cKey = nbtCompound.getString("cKey");
		cKeyLanguag = nbtCompound.getString("cKeyLanguag");
		languag = nbtCompound.getString("languag");
	}

}
