package de.nedelosk.forestmods.common.modules.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.nedelosk.forestmods.api.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;

public class FilterWrapper<C> implements IContentFilter<C> {

	private final Map<Integer, List<IContentFilter<C>>> slotFilters;

	public FilterWrapper() {
		slotFilters = new HashMap();
	}

	public FilterWrapper(Map<Integer, List<IContentFilter<C>>> slotFilters) {
		this.slotFilters = Collections.unmodifiableMap(slotFilters);
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

	public void add(int index, IContentFilter<C>[] filters) {
		if (!slotFilters.containsKey(index)) {
			slotFilters.put(index, new ArrayList());
		}
		for(IContentFilter<C> filter : filters) {
			slotFilters.get(index).add(filter);
		}
	}

	public Map<Integer, List<IContentFilter<C>>> getSlotFilters() {
		return slotFilters;
	}
}
