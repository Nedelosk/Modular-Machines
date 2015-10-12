package nedelosk.modularmachines.api.materials.stats;

import java.util.List;

public interface IMaterialStats {

	String getIdentifier();
	
	int size();
	
	List<String> getLocalizedInfo();
	
}
