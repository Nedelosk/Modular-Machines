package de.nedelosk.modularmachines.common.modules.storaged.tools;

import java.util.List;

import de.nedelosk.modularmachines.api.gui.Widget;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleMachineAdvanced;
import de.nedelosk.modularmachines.api.property.PropertyToolMode;
import de.nedelosk.modularmachines.api.recipes.IToolMode;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetButtonMode;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncMachineMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleMachineAdvanced extends ModuleMachine implements IModuleMachineAdvanced {

	public final PropertyToolMode MODE;

	public ModuleMachineAdvanced(String name, int complexity, int speedModifier, EnumModuleSize size, IToolMode defaultMode) {
		super(name, complexity, speedModifier, size);
		MODE = new PropertyToolMode("mode", getModeClass(), defaultMode);
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(MODE);
	}

	@Override
	public IToolMode getCurrentMode(IModuleState state) {
		return state.get(MODE);
	}

	@Override
	public void setCurrentMode(IModuleState state, IToolMode mode) {
		state.set(MODE, mode);
	}

	public static abstract class ModuleAdvancedPage extends ModulePage<IModuleMachineAdvanced> {

		public ModuleAdvancedPage(String pageID, String title, IModuleState<IModuleMachineAdvanced> moduleState) {
			super(pageID, title, moduleState);
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void updateGui(int x, int y) {
			super.updateGui(x, y);
			List<Widget> widgets = gui.getWidgetManager().getWidgets();
			for(Widget widget : widgets) {
				if (widget instanceof WidgetButtonMode) {
					((WidgetButtonMode) widget).setMode(state.getModule().getCurrentMode(state));
				}
			}
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
			Widget widget = gui.getWidgetManager().getWidgetAtMouse(mouseX - gui.getGuiLeft(), mouseY - gui.getGuiTop());
			if (widget instanceof WidgetButtonMode) {
				Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				if (state.getModule().getCurrentMode(state).ordinal() == state.getModule().getModeClass().getEnumConstants().length - 1) {
					state.getModule().setCurrentMode(state, state.getModule().getModeClass().getEnumConstants()[0]);
					((WidgetButtonMode) widget).setMode(state.getModule().getCurrentMode(state));
				} else {
					state.getModule().setCurrentMode(state, state.getModule().getModeClass().getEnumConstants()[state.getModule().getCurrentMode(state).ordinal() + 1]);
					((WidgetButtonMode) widget).setMode(state.getModule().getCurrentMode(state));
				}
				PacketHandler.INSTANCE.sendToServer(new PacketSyncMachineMode(modular.getHandler(), state));
			}
		}
	}
}
