package modularmachines.api.modules.model;

import java.util.Collection;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IModuleModelState {
	void set(IModelProperty property, boolean value);
	
	default void set(IModelProperty property) {
		set(property, true);
	}
	
	boolean get(IModelProperty property);
	
	boolean has(IModelProperty property);
	
	Collection<IModelProperty> getProperties();
	
	boolean isEmpty();
}
