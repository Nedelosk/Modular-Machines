package de.nedelosk.modularmachines.api.modules;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public interface IModuleLoader extends IForgeRegistryEntry<IModuleLoader>{

	IModule loadModuleFromJson(JsonObject jsonObject, Type typeOfT, JsonDeserializationContext context);

	IModuleContainer loadContainerFromJson(JsonObject jsonObject, Type typeOfT, JsonDeserializationContext context);
}
