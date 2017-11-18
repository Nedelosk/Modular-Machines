package modularmachines.api.recipes;

import javax.annotation.Nullable;

public interface IMode {
	
	int ordinal();
	
	String getName();
	
	IMode next();
	
	IMode previous();
	
	@Nullable
	IMode getMode(int index);
}
