package modularmachines.common.modules.json;

import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;

import modularmachines.api.modules.json.ICustomLoader;
import modularmachines.api.modules.position.EnumModulePositions;
import modularmachines.api.modules.properties.ModuleCasingProperties;
import modularmachines.api.modules.properties.ModuleControllerProperties;
import modularmachines.api.modules.properties.ModuleHeaterProperties;
import modularmachines.api.modules.properties.ModuleKineticProperties;
import modularmachines.api.modules.storage.module.ModuleModuleStorageProperties;
import modularmachines.api.modules.tools.properties.ModuleBoilerProperties;
import modularmachines.api.modules.tools.properties.ModuleMachineProperties;
import modularmachines.api.property.JsonUtils;
import modularmachines.common.core.Constants;

public class DefaultPropertiesLoader {

	private DefaultPropertiesLoader() {
	}

	public static class MachinePropertiesLoader implements ICustomLoader {

		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MOD_ID) && name.getResourcePath().contains("machine");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			double maxSpeed = JsonUtils.getDouble(jsonObject.get("maxSpeed"));
			int workTimeModifier = JsonUtils.getInt(jsonObject.get("workTimeModifier"));
			return new ModuleMachineProperties(complexity, workTimeModifier, maxSpeed);
		}
	}

	public static class HeaterPropertiesLoader implements ICustomLoader {

		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MOD_ID) && name.getResourcePath().contains("heater");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			double maxHeat = JsonUtils.getDouble(jsonObject.get("maxHeat"));
			int heatModifier = JsonUtils.getInt(jsonObject.get("heatModifier"));
			return new ModuleHeaterProperties(complexity, maxHeat, heatModifier);
		}
	}

	public static class KineticPropertiesLoader implements ICustomLoader {

		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MOD_ID) && name.getResourcePath().contains("kinetic");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			double kineticModifier = JsonUtils.getDouble(jsonObject.get("kineticModifier"));
			int maxKineticEnergy = JsonUtils.getInt(jsonObject.get("maxKineticEnergy"));
			int materialPerWork = JsonUtils.getInt(jsonObject.get("materialPerWork"));
			return new ModuleKineticProperties(complexity, kineticModifier, maxKineticEnergy, materialPerWork);
		}
	}

	public static class BoilerPropertiesLoader implements ICustomLoader {

		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MOD_ID) && name.getResourcePath().contains("boiler");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			int waterPerWork = JsonUtils.getInt(jsonObject.get("waterPerWork"));
			return new ModuleBoilerProperties(complexity, waterPerWork);
		}
	}

	public static class CasingPropertiesLoader implements ICustomLoader {

		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MOD_ID) && name.getResourcePath().contains("casing");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			int allowedComplexity = JsonUtils.getInt(jsonObject.get("allowedComplexity"));
			int maxHeat = JsonUtils.getInt(jsonObject.get("maxHeat"));
			float resistance = JsonUtils.getFloat(jsonObject.get("resistance"));
			float hardness = JsonUtils.getFloat(jsonObject.get("hardness"));
			return new ModuleCasingProperties(complexity, allowedComplexity, maxHeat, resistance, hardness);
		}
	}

	public static class ModuleStoragePropertiesLoader implements ICustomLoader {

		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MOD_ID) && name.getResourcePath().contains("modulestorage");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			int allowedComplexity = JsonUtils.getInt(jsonObject.get("allowedComplexity"));
			return new ModuleModuleStorageProperties(complexity, allowedComplexity, EnumModulePositions.SIDE);
		}
	}

	public static class ControllerPropertiesLoader implements ICustomLoader {

		@Override
		public boolean accepts(ResourceLocation name) {
			return name.getResourceDomain().contains(Constants.MOD_ID) && name.getResourcePath().contains("controller");
		}

		@Override
		public Object loadFromJson(JsonObject jsonObject) {
			int complexity = JsonUtils.getInt(jsonObject.get("complexity"));
			int allowedComplexity = JsonUtils.getInt(jsonObject.get("allowedComplexity"));
			return new ModuleControllerProperties(complexity, allowedComplexity);
		}
	}
}
