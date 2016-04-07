package de.nedelosk.forestmods.common.modules.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.nedelosk.forestmods.api.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;

public class FilterWrapper<C> implements IContentFilter<C> {

	private final List<List<IContentFilter<C>>> slotFilters;

	public FilterWrapper() {
		slotFilters = new ArrayList();
	}

	public FilterWrapper(List<List<IContentFilter<C>>> slotFilters) {
		this.slotFilters = Collections.unmodifiableList(slotFilters);
	}

	@Override
	public boolean isValid(int index, C content, ModuleStack moduleStack, ForgeDirection facing) {
		if (slotFilters.size() == index) {
			return false;
		}
		for(IContentFilter<C> filter : slotFilters.get(index)) {
			if (filter.isValid(index, content, moduleStack, facing)) {
				return true;
			}
		}
		return false;
	}

	public void add(int index, IContentFilter<C> filter) {
		if (slotFilters.get(index) == null) {
			slotFilters.add(index, new ArrayList());
		}
		slotFilters.get(index).add(filter);
	}

	public List<List<IContentFilter<C>>> getSlotFilters() {
		return slotFilters;
	}
}
