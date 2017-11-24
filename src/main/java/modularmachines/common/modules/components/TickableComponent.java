package modularmachines.common.modules.components;

import net.minecraft.util.ITickable;

import modularmachines.common.utils.TickHelper;

public abstract class TickableComponent extends ModuleComponent implements ITickable {
	protected TickHelper tickHelper = new TickHelper();
	
	@Override
	public void update() {
		tickHelper.onTick();
	}
}
