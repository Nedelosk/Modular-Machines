package de.nedelosk.modularmachines.api.modules.handlers.inventory;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.filters.FilterWrapper;
import de.nedelosk.modularmachines.api.modules.handlers.filters.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;

public class ModuleInventoryBuilder<M extends IModule> implements IModuleInventoryBuilder<M> {

	protected IModuleState<M> state;
	protected FilterWrapper insertFilter = new FilterWrapper(true);
	protected FilterWrapper extractFilter = new FilterWrapper(false);
	protected List<SlotInfo> contentInfos = new ArrayList<>();
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
		return addInventorySlot(isInput, xPosition, yPosition, null, filters);
	}

	@Override
	public int addInventorySlot(boolean isInput, int xPosition, int yPosition, String backgroundTexture, IContentFilter<ItemStack, M>... filters) {
		int index = contentInfos.size();
		contentInfos.add(new SlotInfo(index, xPosition, yPosition, isInput, backgroundTexture));
		if (isInput) {
			addInsertFilter(index, filters);
		} else {
			addExtractFilter(index, filters);
		}
		isEmpty = false;
		return index;
	}

	@Override
	public IModuleInventory build() {
		return new ModuleInventory(contentInfos.toArray(new SlotInfo[contentInfos.size()]), state, new FilterWrapper(insertFilter.getSlotFilters(), true),
				new FilterWrapper(extractFilter.getSlotFilters(), false));
	}

	@Override
	public boolean isEmpty() {
		return isEmpty;
	}
}