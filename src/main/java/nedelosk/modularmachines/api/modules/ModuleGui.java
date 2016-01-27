package nedelosk.modularmachines.api.modules;

import java.util.List;
import java.util.Locale;

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
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public abstract class ModuleGui<P extends IModule> implements IModuleGui<P> {

	protected final String moduleUID;
	protected final String categoryUID;

	public ModuleGui(String categoryUID, String moduleUID) {
		this.moduleUID = moduleUID;
		this.categoryUID = categoryUID;
	}

	@Override
	public int getGuiTop(IModular modular, ModuleStack<P> stack) {
		return 166;
	}

	@Override
	public String getModuleUID() {
		return moduleUID;
	}

	@Override
	public String getCategoryUID() {
		return categoryUID;
	}

	@Override
	public ResourceLocation getCustomGui(IModular modular, ModuleStack<P> stack) {
		return null;
	}

	@Override
	public void renderString(FontRenderer fontRenderer, int x, int y, int xM, int yM, ModuleStack<P> stack) {
		if (hasCustomInventoryName(stack)) {
			fontRenderer.drawString(getInventoryName(stack), 90 - (fontRenderer.getStringWidth(getInventoryName(stack)) / 2), 6, 4210752);
		}
	}

	@Override
	public String getInventoryName(ModuleStack<P> stack) {
		return StatCollector.translateToLocal("mm.modularmachine.bookmark." + stack.getModule().getName(stack).toLowerCase(Locale.ENGLISH) + ".name");
	}

	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<P> stack) {
	}

	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<P> stack) {
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular, ModuleStack<P> stack, List<Button> buttons) {
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack<P> stack, List<Widget> widgets) {
	}
}
