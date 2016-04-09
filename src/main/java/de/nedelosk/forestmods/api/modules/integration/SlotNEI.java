package de.nedelosk.forestmods.api.modules.integration;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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