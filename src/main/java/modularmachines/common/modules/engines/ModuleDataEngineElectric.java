package modularmachines.common.modules.engines;

import java.util.List;

import modularmachines.api.modules.assemblers.AssemblerError;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.common.modules.ModuleDataSide;
import modularmachines.common.utils.Translator;

public class ModuleDataEngineElectric extends ModuleDataSide{

	@Override
	public void canAssemble(IAssembler assembler, List<AssemblerError> errors) {
		super.canAssemble(assembler, errors);
		if (modular.getModules(IModuleBattery.class).isEmpty()) {
			errors.add(new AssemblerError(Translator.translateToLocal("modular.assembler.error.no.battery")));
		}
	}
	
}
