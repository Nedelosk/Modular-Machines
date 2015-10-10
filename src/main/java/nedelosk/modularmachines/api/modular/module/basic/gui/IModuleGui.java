package nedelosk.modularmachines.api.modular.module.basic.gui;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import nedelosk.nedeloskcore.api.machines.Widget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public interface IModuleGui extends IModule {
	
	int getGuiTop(IModular modular);
	
	ResourceLocation getCustomGui(IModular modular);
	
	void renderString(FontRenderer fontRenderer, int x, int y, int xM, int yM);
	
	void updateGui(IGuiBase base, int x, int y);
	
	void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton);
	
}
