package de.nedelosk.modularmachines.common.modules.handlers.inventorys;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.modules.handlers.FilterWrapper;
import net.minecraft.item.ItemStack;

public class ModuleInventoryBuilder<M extends IModule> implements IModuleInventoryBuilder<M> {

	protected IModuleState<M> state;
	protected FilterWrapper insertFilter = new FilterWrapper();
	protected FilterWrapper extractFilter = new FilterWrapper();
	protected String inventoryName;
	protected Map<Integer, Boolean> slots = Maps.newHashMap();
	protected boolean isEmpty = true;

	public ModuleInventoryBuilder() {
	}

	@Override
	public void addInsertFilter(int index, IContentFilter<ItemStack, M>... filters) {
		insertFilter.add(index, filters);
	}

	@Override
	public void addExtractFilter(int index, IContentFilter<ItemStack, M>... filters) {
		extractFilter.add(index, filters);
	}

	@Override
	public void setModuleState(IModuleState<M> state) {
		this.state = state;
	}

	@Override
	public void setInventoryName(String name) {
		this.inventoryName = name;
	}

	@Override
	public int addInventorySlot(boolean isInput, IContentFilter<ItemStack, M>... filters) {
		int newIndex = slots.size();
		slots.put(newIndex, isInput);
		if (isInput) {
			addInsertFilter(newIndex, filters);
		} else {
			addExtractFilter(newIndex, filters);
		}
		isEmpty = false;
		return newIndex;
	}

	@Override
	public IModuleInventory build() {
		int size = 0;
		for(Entry<Integer, Boolean> entry : slots.entrySet()) {
			if (entry.getKey() > size) {
				size = entry.getKey();
			}
		}
		boolean[] inputs = new boolean[size + 1];
		for(Entry<Integer, Boolean> entry : slots.entrySet()) {
			inputs[entry.getKey()] = entry.getValue();
		}
		return new ModuleInventory(size + 1, inputs, state, new FilterWrapper(insertFilter.getSlotFilters()),
				new FilterWrapper(extractFilter.getSlotFilters()), inventoryName);
	}

	@Override
	public boolean isEmpty() {
		return isEmpty;
	}
}