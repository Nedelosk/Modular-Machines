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
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class ModuleMachineRecipeModeGui<P extends IModuleMachineRecipeMode> extends ModuleMachineRecipeGui<P> {

	public ModuleMachineRecipeModeGui(String categoryUID, String guiName) {
		super(categoryUID, guiName);
	}

	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack<P> stack) {
		super.updateGui(base, x, y, modular, stack);
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		IModuleMachineRecipeModeSaver producerSaver = (IModuleMachineRecipeModeSaver) stack.getSaver();
		for ( Widget widget : widgets ) {
			if (widget instanceof WidgetButtonMode) {
				((WidgetButtonMode) widget).setMode(producerSaver.getMode());
			}
		}
	}

	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack<P> stack) {
		super.handleMouseClicked(tile, widget, mouseX, mouseY, mouseButton, stack);
		IModuleMachineRecipeMode producer = stack.getModule();
		IModuleMachineRecipeModeSaver producerSaver = (IModuleMachineRecipeModeSaver) stack.getSaver();
		if (widget instanceof WidgetButtonMode) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if (producerSaver.getMode().ordinal() == producer.getModeClass().getEnumConstants().length - 1) {
				producerSaver.setMode(producer.getModeClass().getEnumConstants()[0]);
				((WidgetButtonMode) widget).setMode(producerSaver.getMode());
			} else {
				producerSaver.setMode(producer.getModeClass().getEnumConstants()[producerSaver.getMode().ordinal() + 1]);
				((WidgetButtonMode) widget).setMode(producerSaver.getMode());
			}
			PacketHandler.INSTANCE.sendToServer(new PacketSwitchMode((TileEntity) tile, producerSaver.getMode()));
		}
	}
}
