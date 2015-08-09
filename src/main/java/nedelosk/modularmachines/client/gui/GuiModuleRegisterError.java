package nedelosk.modularmachines.client.gui;

import cpw.mods.fml.common.Loader;
import nedelosk.modularmachines.api.ModularMachinesApi;
import net.minecraft.client.gui.GuiScreen;

public class GuiModuleRegisterError extends GuiScreen {

	@Override
	public void drawScreen(int mx, int my, float p_73863_3_) {
		drawDefaultBackground();
		int i = 0;
		fontRendererObj.drawString("The Module " + ModularMachinesApi.getRegisterFailed().getName() + " has been registered twice for the same name.", width / 2- 220, height / 2, 16777215);
		fontRendererObj.drawString("The ModID of the Mod, that the Module register is : " + Loader.instance().activeModContainer().getModId() +".", width / 2- 150, height / 2 + 12, 16777215);
		super.drawScreen(mx, my, p_73863_3_);
	}
	
}
