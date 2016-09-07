package de.nedelosk.modularmachines.api.modules.handlers;


public class ContentInfo {

	public boolean isInput;
	public int index;
	public int xPosition;
	public int yPosition;

	public ContentInfo(int index, int xPosition, int yPosition, boolean isInput) {
		this.index = index;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.isInput = isInput;
	}
}
