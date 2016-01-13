package nedelosk.modularmachines.api.modular.assembler;

import java.util.List;

import org.lwjgl.util.Point;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;

public class AssemblerMachineInfo {

	public ItemStack machine;
	public List<Point> positions = Lists.newArrayList();

	public AssemblerMachineInfo() {
		this.machine = null;
	}

	public AssemblerMachineInfo(ItemStack stack) {
		this.machine = stack;
	}

	public void addSlotPosition(int x, int y) {
		positions.add(new Point(x, y));
	}
}
