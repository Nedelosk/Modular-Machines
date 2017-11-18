/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules;

import modularmachines.api.modules.IModuleType;

public enum ModuleType implements IModuleType {
	TRANSFER{
	/*	@Override
		public void isValidModuleCount(IAssembler assembler, int count, List<AssemblerError> errors) {
			if(count > 1){
				errors.add(new AssemblerError(Translator.translateToLocal("modular.assembler.error.type.transfer")));
			}
		}*/
	};
}
