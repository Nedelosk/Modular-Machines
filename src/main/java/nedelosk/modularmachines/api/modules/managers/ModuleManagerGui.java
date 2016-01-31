package nedelosk.modularmachines.api.modules.managers;

import java.util.List;

import nedelosk.forestcore.library.gui.Button;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.modularmachines.api.client.gui.ButtonManagerTab;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.gui.ModuleGuiDefault;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.util.ResourceLocation;

public abstract class ModuleManagerGui<M extends IModuleManager<S>, S extends IModuleManagerSaver> extends ModuleGuiDefault<M, S> {

	public ModuleManagerGui(String categoryUID, String guiName) {
		super(categoryUID, guiName);
	}

	@Override
	public ResourceLocation getCustomGui(IModular modular, ModuleStack stack) {
		return new ResourceLocation("modularmachines", "textures/gui/modular_manager.png");
	}

	@Override
	public int getGuiTop(IModular modular, ModuleStack stack) {
		return 196;
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<M, S> stack, List<Widget> widgets) {
		IModuleManagerSaver saver = stack.getSaver();
		for ( int ID = saver.getTab() * 3; ID < (saver.getTab() + 1) * 3; ID++ ) {
			addWidgets(ID, gui, stack, widgets);
		}
	}

	protected abstract void addWidgets(int tabID, IGuiBase<IModularTileEntity<IModular>> gui, ModuleStack<M, S> stack, List<Widget> widgets);

	@Override
	public void addButtons(IGuiBase gui, IModular modular, ModuleStack<M, S> stack, List<Button> buttons) {
		IModuleManager manager = stack.getModule();
		for ( int ID = 0; ID < manager.getMaxTabs(); ID++ ) {
			buttons.add(new ButtonManagerTab<M, S>(gui.getButtons().size() + gui.getButtonManager().getButtons().size(),
					ID > 4 ? 12 + gui.getGuiLeft() + (ID - 5) * 30 : 12 + gui.getGuiLeft() + ID * 30, ID > 4 ? 196 + gui.getGuiTop() : -19 + gui.getGuiTop(),
					stack, ID > 4 ? true : false, ID));
		}
	}
}
