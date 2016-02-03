package de.nedelosk.forestmods.api.recipes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NeiStack {

	public int x;
	public int y;
	public boolean isInput;

	public NeiStack(int x, int y, boolean isInput) {
		this.x = x;
		this.y = y;
		this.isInput = isInput;
	}
}
