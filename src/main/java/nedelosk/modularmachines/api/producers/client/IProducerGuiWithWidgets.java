package nedelosk.modularmachines.api.producers.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.utils.ModuleStack;

public interface IProducerGuiWithWidgets extends IProducerGui {

	@SideOnly(Side.CLIENT)
	void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack);

}
