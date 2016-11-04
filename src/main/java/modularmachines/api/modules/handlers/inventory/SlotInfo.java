package modularmachines.api.modules.handlers.inventory;

import modularmachines.api.modules.handlers.ContentInfo;

public class SlotInfo extends ContentInfo {

	public String backgroundTexture;

	public SlotInfo(int index, int xPosition, int yPosition, boolean isInput) {
		super(index, xPosition, yPosition, isInput);
	}

	public SlotInfo(int index, int xPosition, int yPosition, boolean isInput, String backgroundTexture) {
		super(index, xPosition, yPosition, isInput);
		this.backgroundTexture = backgroundTexture;
	}
}
