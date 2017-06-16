package modularmachines.common.modules.logic;

import java.util.Random;

import net.minecraft.util.ITickable;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.logic.LogicComponent;

public class UpdateComponent extends LogicComponent{

	private static final Random rand = new Random();
	private int tickCount = rand.nextInt(256);
	
	@Override
	public void update(){
		if(logic != null){
			tickCount++;
			for(Module module : logic.getModules()){
				if(module instanceof ITickable){
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
