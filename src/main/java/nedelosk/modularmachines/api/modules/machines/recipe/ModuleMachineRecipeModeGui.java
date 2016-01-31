package nedelosk.modularmachines.api.modules.machines.recipe;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.modularmachines.api.client.widget.WidgetButtonMode;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.packets.PacketSwitchMode;
import nedelosk.modularmachines.api.recipes.IMachineMode;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModuleMachineRecipeModeGui<M extends IModuleMachineRecipeMode<S>, S extends IModuleMachineRecipeModeSaver> extends ModuleMachineRecipeGui<M, S> {

	public ModuleMachineRecipeModeGui(String categoryUID, String guiName) {
		super(categoryUID, guiName);
	}

	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<M, S> stack) {
		super.updateGui(base, x, y, modular, stack);
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		IModuleMachineRecipeModeSaver producerSaver = stack.getSaver();
		for ( Widget widget : widgets ) {
			if (widget instanceof WidgetButtonMode) {
				((WidgetButtonMode) widget).setMode(producerSaver.getMode());
			}
		}
	}

	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<M, S> stack) {
		super.handleMouseClicked(tile, widget, mouseX, mouseY, mouseButton, stack);
		IModuleMachineRecipeMode module = stack.getModule();
		IModuleMachineRecipeModeSaver moduleSaver = stack.getSaver();
		if (widget instanceof WidgetButtonMode) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if (moduleSaver.getMode().ordinal() == module.getModeClass().getEnumConstants().length - 1) {
				moduleSaver.setMode((IMachineMode) module.getModeClass().getEnumConstants()[0]);
				((WidgetButtonMode) widget).setMode(moduleSaver.getMode());
			} else {
				moduleSaver.setMode((IMachineMode) module.getModeClass().getEnumConstants()[moduleSaver.getMode().ordinal() + 1]);
				((WidgetButtonMode) widget).setMode(moduleSaver.getMode());
			}
			PacketHandler.INSTANCE.sendToServer(new PacketSwitchMode((TileEntity) tile, moduleSaver.getMode()));
		}
	}
}
