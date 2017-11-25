package modularmachines.api.modules.errors;

public interface IErrorState {
	short getID();
	
	String getUniqueName();
	
	String getUnlocalizedDescription();
	
	String getUnlocalizedHelp();
}
