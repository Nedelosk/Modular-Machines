package de.nedelosk.modularmachines.common.modules;

import java.util.List;

import com.google.gson.JsonObject;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.PropertyMachineMode;
import de.nedelosk.modularmachines.api.modules.tool.IModuleToolAdvanced;
import de.nedelosk.modularmachines.api.recipes.IMachineMode;
import de.nedelosk.modularmachines.client.gui.Widget;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetButtonMode;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncMachineMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModuleToolAdvanced extends ModuleToolEngine implements IModuleToolAdvanced {

	public IMachineMode defaultMode;
	
	public PropertyMachineMode MODE = new PropertyMachineMode("mode", getModeClass());

	public ModuleToolAdvanced(int speed, int engines, IMachineMode defaultMode) {
		super(speed, engines);
		this.defaultMode = defaultMode;
	}
	
	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).add(MODE, defaultMode);
	}
	
	@Override
	public IMachineMode getDefaultMode(IModuleState state) {
		return defaultMode;
	}

	@Override
	public IMachineMode getCurrentMode(IModuleState state) {
		return (IMachineMode) state.get(MODE);
	}

	@Override
	public void setCurrentMode(IModuleState state, IMachineMode mode) {
		state.add(MODE, mode);
		this.defaultMode = mode;
	}

	@Override
	public Object[] getRecipeModifiers(IModuleState state) {
		return new Object[] { defaultMode };
	}

	@Override
	public abstract ModuleAdvancedPage[] createPages(IModuleState state);

	public static abstract class ModuleAdvancedPage extends ModulePage<IModuleToolAdvanced> {

		public ModuleAdvancedPage(int pageID, IModuleState<IModuleToolAdvanced> moduleState) {
			super(pageID, moduleState);
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
				PacketHandler.INSTANCE.sendToServer(new PacketSyncMachineMode((TileEntity) modular.getTile(), state));
			}
		}
	}
}
