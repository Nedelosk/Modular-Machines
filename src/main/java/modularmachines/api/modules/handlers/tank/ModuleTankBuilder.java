package modularmachines.api.modules.handlers.tank;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.handlers.ContentInfo;
import modularmachines.api.modules.handlers.filters.FilterWrapper;
import modularmachines.api.modules.handlers.filters.IContentFilter;
import modularmachines.api.modules.state.IModuleState;
import net.minecraftforge.fluids.FluidStack;

public class ModuleTankBuilder<M extends IModule> implements IModuleTankBuilder<M> {

	protected IModuleState<M> state;
	protected FilterWrapper<FluidStack, M> insertFilter = new FilterWrapper(true);
	protected FilterWrapper<FluidStack, M> extractFilter = new FilterWrapper(false);
	protected Map<FluidTankAdvanced, ContentInfo> tankInfos = new HashMap<>();
	protected boolean isEmpty = true;

	public ModuleTankBuilder() {
	}

	@Override
	public void addInsertFilter(int index, IContentFilter<FluidStack, M>... filters) {
		insertFilter.add(index, filters);
	}

	@Override
	public void addExtractFilter(int index, IContentFilter<FluidStack, M>... filters) {
		extractFilter.add(index, filters);
	}

	@Override
	public void setModuleState(IModuleState<M> state) {
		this.state = state;
	}

	@Override
	public int addFluidTank(int capacity, boolean isInput, int xPosition, int yPosition, IContentFilter<FluidStack, M>... filters) {
		int index = tankInfos.size();
		tankInfos.put(new FluidTankAdvanced(capacity, null, index), new ContentInfo(index, xPosition, yPosition, isInput));
		if (isInput) {
			addInsertFilter(index, filters);
		} else {
			addExtractFilter(index, filters);
		}
		isEmpty = false;
		return index;
	}

	@Override
	public IModuleTank build() {
		FluidTankAdvanced[] tanks = new FluidTankAdvanced[tankInfos.size()];
		ContentInfo[] contentInfos = new ContentInfo[tankInfos.size()];
		for(Entry<FluidTankAdvanced, ContentInfo> entry : tankInfos.entrySet()) {
			FluidTankAdvanced tank = entry.getKey();
			tanks[tank.index] = tank;
			contentInfos[tank.index] = entry.getValue();
		}
		return new ModuleTank(tanks, contentInfos, state, new FilterWrapper(insertFilter.getSlotFilters(), true), new FilterWrapper(extractFilter.getSlotFilters(), false));
	}

	@Override
	public boolean isEmpty() {
		return isEmpty;
	}
}
