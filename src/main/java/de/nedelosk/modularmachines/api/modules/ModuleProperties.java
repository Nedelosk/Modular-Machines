package de.nedelosk.modularmachines.api.modules;

import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ModuleProperties implements IModuleProperties, IModulePropertiesConfigurable {

	protected final int defaultComplexity;
	protected int complexity;

	public ModuleProperties(int complexity) {
		this.defaultComplexity = complexity;
		this.complexity = defaultComplexity;
	}

	@Override
	public int getComplexity(IModuleContainer container){
		return complexity;
	}

	@Override
	public void processConfig(IModuleContainer container, Configuration config) {
		complexity = config.getInt("complexity", "modules." + container.getItemContainer().getRegistryName(), defaultComplexity, 0, 64, "The complexity of the controller.");
	}

	public double getDouble(Configuration config, String name, String category, double defaultValue, double minValue, double maxValue, String comment){
		return getDouble(config, name, category, defaultValue, minValue, maxValue, comment, name);
	}

	public double getDouble(Configuration config, String name, String category, double defaultValue, double minValue, double maxValue, String comment, String langKey){
		Property prop = config.get(category, name, defaultValue);
		prop.setLanguageKey(langKey);
		prop.setComment(comment + " [range: " + minValue + " ~ " + maxValue + ", default: " + defaultValue + "]");
		prop.setMinValue(minValue);
		prop.setMaxValue(maxValue);
		return prop.getDouble(defaultValue) < minValue ? minValue : (prop.getDouble(defaultValue) > maxValue ? maxValue : prop.getDouble(defaultValue));
	}
}
