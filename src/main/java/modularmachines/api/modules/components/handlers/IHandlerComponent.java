package modularmachines.api.modules.components.handlers;

import javax.annotation.Nullable;

import modularmachines.api.IIOConfigurable;
import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.api.modules.components.IModuleComponent;

public interface IHandlerComponent extends IModuleComponent, INBTWritable, INBTReadable, IIOConfigurable {
	
	@Nullable
	ISaveHandler getSaveHandler();
	
	void setSaveHandler(ISaveHandler saveHandler);
}
