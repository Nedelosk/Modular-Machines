package de.nedelosk.forestmods.api.modules.gui;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.gui.Button;
import de.nedelosk.forestcore.gui.IGuiBase;
import de.nedelosk.forestcore.gui.Widget;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public interface IModuleGui<M extends IModule, S extends IModuleSaver> {

	int getGuiTop(IModular modular, ModuleStack<M, S> stack);

	String getModuleUID();

	String getCategoryUID();

	String getUID();

	ResourceLocation getCustomGui(IModular modular, ModuleStack<M, S> stack);

	void renderString(FontRenderer fontRenderer, int x, int y, int xM, int yM, ModuleStack<M, S> stack);

	void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<M, S> stack);

	void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<M, S> stack);

	boolean hasCustomInventoryName(ModuleStack<M, S> stack);

	String getInventoryName(ModuleStack<M, S> stack);

	void addButtons(IGuiBase gui, IModular modular, ModuleStack<M, S> stack, List<Button> buttons);

	void addWidgets(IGuiBase gui, IModular modular, ModuleStack<M, S> stack, List<Widget> widgets);
}
