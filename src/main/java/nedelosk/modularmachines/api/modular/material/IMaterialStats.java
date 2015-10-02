package nedelosk.modularmachines.api.modular.material;

import java.util.List;

public interface IMaterialStats {

	String getIdentifier();
	
	int size();
	
	List<String> getLocalizedInfo();
	
}
