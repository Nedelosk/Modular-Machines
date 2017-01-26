package modularmachines.api.modules.assemblers;

import net.minecraft.util.text.translation.I18n;

public class AssemblerError {

	public String error;
	
	public AssemblerError(String error) {
		this.error = error;
	}
	
	public String getLocalizedMessage(){
		return I18n.translateToLocal(error);
	}
	
}
