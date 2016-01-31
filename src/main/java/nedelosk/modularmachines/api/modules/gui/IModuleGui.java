package nedelosk.modularmachines.api.modules.gui;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.Button;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public interface IModuleGui<M extends IModule<S>, S extends IModuleSaver> {

	int getGuiTop(IModular modular, ModuleStack<M, S> stack);

	String getModuleUID();

	String getCategoryUID();

	ResourceLocation getCustomGui(IModular modular, ModuleStack<M, S> stack);

	void renderString(FontRenderer fontRenderer, int x, int y, int xM, int yM, ModuleStack<M, S> stack);

	void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<M, S> stack);

	void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<M, S> stack);

	boolean hasCustomInventoryName(ModuleStack<M, S> stack);

	String getInventoryName(ModuleStack<M, S> stack);

	void addButtons(IGuiBase gui, IModular modular, ModuleStack<M, S> stack, List<Button> buttons);

	void addWidgets(IGuiBase gui, IModular modular, ModuleStack<M, S> stack, List<Widget> widgets);
}
