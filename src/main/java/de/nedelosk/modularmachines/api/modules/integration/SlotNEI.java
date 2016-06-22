package de.nedelosk.modularmachines.api.modules.integration;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class SlotNEI {

	public int x;
	public int y;
	public boolean isInput;

	public SlotNEI(int x, int y, boolean isInput) {
		this.x = x;
		this.y = y;
		this.isInput = isInput;
	}
}