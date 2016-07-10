package de.nedelosk.modularmachines.common.modules.handlers.inventorys;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.handlers.ContentInfo;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.common.modules.handlers.FilterWrapper;
import net.minecraft.item.ItemStack;

public class ModuleInventoryBuilder<M extends IModule> implements IModuleInventoryBuilder<M> {

	protected IModuleState<M> state;
	protected FilterWrapper insertFilter = new FilterWrapper(true);
	protected FilterWrapper extractFilter = new FilterWrapper(false);
	protected Map<Integer, ContentInfo> contentInfos = Maps.newHashMap();
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
	public int addInventorySlot(boolean isInput, int xPosition, int yPosition, IContentFilter<ItemStack, M>... filters) {
		int newIndex = contentInfos.size();
		contentInfos.put(newIndex, new ContentInfo(xPosition, yPosition, isInput));
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
		for(Entry<Integer, ContentInfo> entry : contentInfos.entrySet()) {
			if (entry.getKey() > size) {
				size = entry.getKey();
			}
		}
		ContentInfo[] contentInfos = new ContentInfo[size + 1];
		for(Entry<Integer, ContentInfo> entry : this.contentInfos.entrySet()) {
			contentInfos[entry.getKey()] = entry.getValue();
		}
		return new ModuleInventory(size + 1, contentInfos, state, new FilterWrapper(insertFilter.getSlotFilters(), true),
				new FilterWrapper(extractFilter.getSlotFilters(), false));
	}

	@Override
	public boolean isEmpty() {
		return isEmpty;
	}
}