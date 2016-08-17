package de.nedelosk.modularmachines.api.property;

import java.util.Map;

public interface IPropertyRegistryBuilder extends IPropertyBuilder {

	Map<String, IProperty> getRegisteredProperties();

	IPropertyBuilder register(IProperty property);
}
