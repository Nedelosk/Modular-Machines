package nedelosk.forestday.api.guis;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiButton;

public class Button extends GuiButton {

	public Button(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
	}

	public boolean isMouseOver(int x, int y) {
		return x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height;
	}

	public void handleMouseClick(int mouseX, int mouseY, int mouseButton) {
		
	}
	
	public ArrayList<String> getTooltip()
	{
		return null;
	}
	
	public void onButtonClick(IGuiBase gui)
	{
		
	}

}
