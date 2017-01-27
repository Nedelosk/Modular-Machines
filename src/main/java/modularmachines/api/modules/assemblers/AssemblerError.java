package modularmachines.api.modules.assemblers;

public class AssemblerError {

	public String error;
	
	public AssemblerError(String error) {
		this.error = error;
	}
	
	public String getMessage(){
		return error;
	}
	
}
