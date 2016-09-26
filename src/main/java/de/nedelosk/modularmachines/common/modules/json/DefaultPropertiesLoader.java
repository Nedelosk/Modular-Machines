package de.nedelosk.modularmachines.common.modules.json;

import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.json.ICustomLoader;
import de.nedelosk.modularmachines.api.modules.position.EnumModulePositions;
import de.nedelosk.modularmachines.api.modules.properties.ModuleCasingProperties;
import de.nedelosk.modularmachines.api.modules.properties.ModuleControllerProperties;
import de.nedelosk.modularmachines.api.modules.properties.ModuleHeaterProperties;
import de.nedelosk.modularmachines.api.modules.properties.ModuleKineticProperties;
import de.nedelosk.modularmachines.api.modules.storage.module.ModuleModuleStorageProperties;
import de.nedelosk.modularmachines.api.modules.tools.properties.ModuleBoilerProperties;
import de.nedelosk.modularmachines.api.modules.tools.properties.ModuleMachineProperties;
import de.nedelosk.modularmachines.api.property.JsonUtils;
import de.nedelosk.modularmachines.common.core.Constants;
import net.minecraft.util.ResourceLocation;

public class DefaultPropertiesLoader {

	private DefaultPropertiesLoader() {
	}

	public static EnumModuleSizes getSize(JsonObject obj){
		int size = JsonUtils.getInt(obj.get("size"));
		if(size >= EnumModuleSizes.VALUES.length){
			size = EnumModuleSizes.VALUES.length - 1;
		}else if(size < 1){
			size = 1;
		}
		return EnumModuleSizes.values()[size];
	}

	public static class MachinePropertiesLoader implements ICustomLoader{
		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MODID) && name.getResourcePath().contains("machine");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			double maxSpeed = JsonUtils.getDouble(jsonObject.get("maxSpeed"));
			int workTimeModifier = JsonUtils.getInt(jsonObject.get("workTimeModifier"));
			return new ModuleMachineProperties(complexity, getSize(jsonObject), workTimeModifier, maxSpeed);
		}
	}

	public static class HeaterPropertiesLoader implements ICustomLoader{
		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MODID) && name.getResourcePath().contains("heater");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			double maxHeat = JsonUtils.getDouble(jsonObject.get("maxHeat"));
			int heatModifier = JsonUtils.getInt(jsonObject.get("heatModifier"));
			return new ModuleHeaterProperties(complexity, getSize(jsonObject), maxHeat, heatModifier);
		}
	}

	public static class KineticPropertiesLoader implements ICustomLoader{
		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MODID) && name.getResourcePath().contains("kinetic");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			double kineticModifier = JsonUtils.getDouble(jsonObject.get("kineticModifier"));
			int maxKineticEnergy = JsonUtils.getInt(jsonObject.get("maxKineticEnergy"));
			int materialPerWork = JsonUtils.getInt(jsonObject.get("materialPerWork"));
			return new ModuleKineticProperties(complexity, getSize(jsonObject), kineticModifier, maxKineticEnergy, materialPerWork);
		}
	}

	public static class BoilerPropertiesLoader implements ICustomLoader{
		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MODID) && name.getResourcePath().contains("boiler");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			int waterPerWork = JsonUtils.getInt(jsonObject.get("waterPerWork"));
			return new ModuleBoilerProperties(complexity, getSize(jsonObject), waterPerWork);
		}
	}

	public static class CasingPropertiesLoader implements ICustomLoader{
		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MODID) && name.getResourcePath().contains("casing");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			int allowedComplexity = JsonUtils.getInt(jsonObject.get("allowedComplexity"));
			int maxHeat = JsonUtils.getInt(jsonObject.get("maxHeat"));
			float resistance = JsonUtils.getFloat(jsonObject.get("resistance"));
			float hardness = JsonUtils.getFloat(jsonObject.get("hardness"));
			return new ModuleCasingProperties(complexity, getSize(jsonObject), allowedComplexity, maxHeat, resistance, hardness);
		}
	}

	public static class ModuleStoragePropertiesLoader implements ICustomLoader{
		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MODID) && name.getResourcePath().contains("modulestorage");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			int allowedComplexity = JsonUtils.getInt(jsonObject.get("allowedComplexity"));
			return new ModuleModuleStorageProperties(complexity, getSize(jsonObject), allowedComplexity, EnumModulePositions.SIDE);
		}
	}

	public static class ControllerPropertiesLoader implements ICustomLoader{
		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MODID) && name.getResourcePath().contains("controller");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			int allowedComplexity = JsonUtils.getInt(jsonObject.get("allowedComplexity"));
			return new ModuleControllerProperties(complexity, getSize(jsonObject), allowedComplexity);
		}
	}


}
