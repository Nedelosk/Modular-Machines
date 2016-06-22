package de.nedelosk.modularmachines.common.modules.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;

public class FilterWrapper<C, M extends IModule> implements IContentFilter<C, M> {

	private final Map<Integer, List<IContentFilter<C, M>>> slotFilters;

	public FilterWrapper() {
		slotFilters = new HashMap();
	}

	public FilterWrapper(Map<Integer, List<IContentFilter<C, M>>> slotFilters) {
		this.slotFilters = Collections.unmodifiableMap(slotFilters);
	}

	@Override
	public boolean isValid(int index, C content, IModuleState<M> state) {
		if (slotFilters.size() == index) {
			return false;
		}
		for(IContentFilter<C, M> filter : slotFilters.get(index)) {
			if (filter.isValid(index, content, state)) {
				return true;
			}
		}
		return false;
	}

	public void add(int index, IContentFilter<C, M>[] filters) {
		if (!slotFilters.containsKey(index)) {
			slotFilters.put(index, new ArrayList());
		}
		for(IContentFilter<C, M> filter : filters) {
			slotFilters.get(index).add(filter);
		}
	}

	public Map<Integer, List<IContentFilter<C, M>>> getSlotFilters() {
		return slotFilters;
	}
}
