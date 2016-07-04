package de.nedelosk.modularmachines.api.property;

import java.util.List;

public interface IPropertyRegistryBuilder extends IPropertyBuilder {
	
	List<IProperty> getRegisteredProperties();
	
	IPropertyBuilder register(IProperty property);
}
