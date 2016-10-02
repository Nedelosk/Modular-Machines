package de.nedelosk.modularmachines.common.plugins.cofh;

import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.modules.json.ICustomLoader;
import de.nedelosk.modularmachines.api.property.JsonUtils;
import de.nedelosk.modularmachines.common.core.Constants;
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
		int capacity = JsonUtils.getInt(jsonObject.get("capacity"));
		int maxExtract = JsonUtils.getInt(jsonObject.get("maxExtract"));
		int maxReceive = JsonUtils.getInt(jsonObject.get("maxReceive"));
		int tier = JsonUtils.getInt(jsonObject.get("tier"));
		return new ModuleRFBatteryProperties(complexity, capacity, maxExtract, maxReceive, tier);
	}
}
