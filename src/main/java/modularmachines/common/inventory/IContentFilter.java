package modularmachines.common.inventory;

import javax.annotation.Nonnull;

import modularmachines.api.modules.Module;

public interface IContentFilter<C, M extends Module> {
	
	/**
	 * Test if a item valid for the index.
	 */
	boolean isValid(int index, @Nonnull C content, M module);
}
