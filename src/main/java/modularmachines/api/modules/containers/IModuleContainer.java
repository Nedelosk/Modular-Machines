package modularmachines.api.modules.containers;

import javax.annotation.Nullable;

import modularmachines.api.modules.ModuleData;
import net.minecraft.item.ItemStack;

/**
 * An IModuleContainer provides informations about modules.
 */
public interface IModuleContainer {

	/**
	 * @return The stack that is used to display this container in a guit.
	 */
	ItemStack getParent();

	/**
	 * @return The {@link ModuleData} that this container contains.
	 */
	ModuleData getData();

	@Nullable
	boolean matches(ItemStack stack);
}
