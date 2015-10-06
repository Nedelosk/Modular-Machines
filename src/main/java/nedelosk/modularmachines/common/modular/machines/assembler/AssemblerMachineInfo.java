package nedelosk.modularmachines.common.modular.machines.assembler;

import java.util.List;

import org.lwjgl.util.Point;

import com.google.common.collect.Lists;

import nedelosk.modularmachines.api.parts.IMachinePart;
import nedelosk.modularmachines.common.modular.utils.MachineBuilder.BuildMode;
import net.minecraft.item.ItemStack;

public class AssemblerMachineInfo {
	
	public ItemStack machine;
	public List<Point> positions = Lists.newArrayList();
	public BuildMode mode;
	
	public AssemblerMachineInfo() {
		this.machine = null;
		this.mode = BuildMode.MACHINE;
	}
	
	public AssemblerMachineInfo(ItemStack stack, BuildMode mode) {
		this.machine = ((IMachinePart)stack.getItem()).getMachine(stack);
		this.mode = mode;
	}
	
	public void addSlotPosition(int x, int y) {
		positions.add(new Point(x, y));
	}
	
}
