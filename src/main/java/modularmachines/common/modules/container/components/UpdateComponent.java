package modularmachines.common.modules.container.components;

import java.util.Random;

import net.minecraft.util.ITickable;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.container.ContainerComponent;

public class UpdateComponent extends ContainerComponent {
	
	private static final Random rand = new Random();
	private int tickCount = rand.nextInt(256);
	
	@Override
	public void update() {
		if (container != null) {
			tickCount++;
			for (Module module : container.getModules()) {
				if (module instanceof ITickable) {
					ITickable tickable = (ITickable) module;
					tickable.update();
				}
			}
		}
	}
	
	public boolean updateOnInterval(int tickInterval) {
		return tickCount % tickInterval == 0;
	}
	
	public int getTickCount() {
		return tickCount;
	}
	
}
