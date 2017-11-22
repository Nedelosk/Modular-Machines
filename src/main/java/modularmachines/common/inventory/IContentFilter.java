package modularmachines.common.inventory;

import javax.annotation.Nonnull;

import modularmachines.api.modules.IModule;

public interface IContentFilter<C, M extends IModule> {
	
	/**
	 * Test if a item valid for the index.
	 */
	boolean isValid(int index, @Nonnull C content, M module);
}
