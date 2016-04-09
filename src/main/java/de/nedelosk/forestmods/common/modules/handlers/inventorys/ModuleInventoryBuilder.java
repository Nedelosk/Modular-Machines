package de.nedelosk.forestmods.common.modules.handlers.inventorys;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.handlers.IContentFilter;
import de.nedelosk.forestmods.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.handlers.FilterWrapper;
import net.minecraft.item.ItemStack;

public class ModuleInventoryBuilder implements IModuleInventoryBuilder {

	protected IModular modular;
	protected ModuleStack moduleStack;
	protected FilterWrapper insertFilter = new FilterWrapper();
	protected FilterWrapper extractFilter = new FilterWrapper();
	protected String inventoryName;
	protected Map<Integer, Boolean> slots = Maps.newHashMap();

	public ModuleInventoryBuilder() {
	}

	@Override
	public void addInsertFilter(int index, IContentFilter<ItemStack>... filters) {
		insertFilter.add(index, filters);
	}

	@Override
	public void addExtractFilter(int index, IContentFilter<ItemStack>... filters) {
		extractFilter.add(index, filters);
	}

	@Override
	public void setModuleStack(ModuleStack moduleStack) {
		this.moduleStack = moduleStack;
	}

	@Override
	public void setModular(IModular modular) {
		this.modular = modular;
	}

	@Override
	public void setInventoryName(String name) {
		this.inventoryName = name;
	}

	@Override
	public void initSlot(int index, boolean isInput, IContentFilter<ItemStack>... filters) {
		slots.put(index, isInput);
		if (isInput) {
			addInsertFilter(index, filters);
		} else {
			addExtractFilter(index, filters);
		}
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
		return new ModuleInventory(size + 1, inputs, modular, moduleStack, new FilterWrapper(insertFilter.getSlotFilters()),
				new FilterWrapper(extractFilter.getSlotFilters()), inventoryName);
	}
}