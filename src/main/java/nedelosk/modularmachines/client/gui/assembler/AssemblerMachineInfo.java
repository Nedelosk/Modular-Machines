package nedelosk.modularmachines.client.gui.assembler;

import java.util.List;

import org.lwjgl.util.Point;

import com.google.common.collect.Lists;

import nedelosk.modularmachines.api.basic.machine.part.IMachinePart;
import net.minecraft.item.ItemStack;

public class AssemblerMachineInfo {
	
	public ItemStack machine;
	public List<Point> positions = Lists.newArrayList();
	
	public AssemblerMachineInfo() {
		this.machine = null;
	}
	
	public AssemblerMachineInfo(ItemStack stack) {
		this.machine = ((IMachinePart)stack.getItem()).getMachine();
	}
	
	public void addSlotPosition(int x, int y) {
		positions.add(new Point(x, y));
	}
	
}
