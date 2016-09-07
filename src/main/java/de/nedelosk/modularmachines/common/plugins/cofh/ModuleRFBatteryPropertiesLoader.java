package de.nedelosk.modularmachines.common.plugins.cofh;

import com.google.gson.JsonObject;

import cofh.api.energy.IEnergyContainerItem;
import de.nedelosk.modularmachines.api.modules.json.ICustomLoader;
import de.nedelosk.modularmachines.api.property.JsonUtils;
import de.nedelosk.modularmachines.common.core.Constants;
import de.nedelosk.modularmachines.common.modules.json.DefaultPropertiesLoader;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ModuleRFBatteryPropertiesLoader implements ICustomLoader {

	public static ICustomLoader loader;

	@Override
	public boolean accepts(ResourceLocation name) {
		return name.getResourceDomain().contains(Constants.MODID) && name.getResourcePath().contains("battery");
	}

	@Override
	public Object loadFromJson(JsonObject jsonObject) {
		int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
		Item containerItem = JsonUtils.parseItem(jsonObject, "containerItem");
		int capacity = JsonUtils.getInt(jsonObject.get("capacity"));
		int maxExtract = JsonUtils.getInt(jsonObject.get("maxExtract"));
		int maxReceive = JsonUtils.getInt(jsonObject.get("maxReceive"));
		int tier = JsonUtils.getInt(jsonObject.get("tier"));
		if(!(containerItem instanceof IEnergyContainerItem)){
			return null;
		}
		return new ModuleRFBatteryProperties(complexity, DefaultPropertiesLoader.getSize(jsonObject), capacity, maxExtract, maxReceive, tier, (IEnergyContainerItem) containerItem);
	}
}
