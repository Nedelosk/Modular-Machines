package de.nedelosk.modularmachines.api.modules.handlers;


public class ContentInfo {

	public boolean isInput;
	public int xPosition;
	public int yPosition;

	public ContentInfo(int xPosition, int yPosition, boolean isInput) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.isInput = isInput;
	}
}
