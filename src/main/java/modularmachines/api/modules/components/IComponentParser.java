package modularmachines.api.modules.components;

import com.google.gson.JsonObject;

public interface IComponentParser {
	
	IComponentFactory parse(JsonObject json);
}
