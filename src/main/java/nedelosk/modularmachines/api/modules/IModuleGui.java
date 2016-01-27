package nedelosk.modularmachines.api.modules;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.Button;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public interface IModuleGui<M extends IModule> {

	int getGuiTop(IModular modular, ModuleStack<M> stack);

	String getModuleUID();

	String getCategoryUID();

	ResourceLocation getCustomGui(IModular modular, ModuleStack<M> stack);

	void renderString(FontRenderer fontRenderer, int x, int y, int xM, int yM, ModuleStack<M> stack);

	void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<M> stack);

	void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<M> stack);

	boolean hasCustomInventoryName(ModuleStack<M> stack);

	String getInventoryName(ModuleStack<M> stack);

	void addButtons(IGuiBase gui, IModular modular, ModuleStack<M> stack, List<Button> buttons);

	void addWidgets(IGuiBase gui, IModular modular, ModuleStack<M> stack, List<Widget> widgets);
}
