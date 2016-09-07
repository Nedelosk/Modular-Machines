package de.nedelosk.modularmachines.api.modules.json;

import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;

public interface ICustomLoader {

	boolean accepts(ResourceLocation name);

	Object loadFromJson(JsonObject jsonObject);

}
