package nedelosk.modularmachines.common.modular.module.tool.producer.machine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.basic.IMachineMode;
import nedelosk.modularmachines.api.modular.module.tool.producer.machine.IProducerMachineRecipeMode;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.client.gui.widget.WidgetButtonMode;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularSwitchMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public abstract class ProducerMachineRecipeMode extends ProducerMachineRecipe implements IProducerMachineRecipeMode {
	
	public IMachineMode mode;
	
	public ProducerMachineRecipeMode(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	public ProducerMachineRecipeMode(String modifier, int inputs, int outputs, int speed, IMachineMode mode) {
		super(modifier, inputs, outputs, speed);
		this.mode = mode;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui(IGuiBase base, int x, int y, IModular modular, ModuleStack stack) {
		super.updateGui(base, x, y, modular, stack);
		List<Widget> widgets = base.getWidgetManager().getWidgets();
		for (Widget widget : widgets) {
			if (widget instanceof WidgetButtonMode) {
				((WidgetButtonMode)widget).setMode(getMode());
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack stack) {
		super.handleMouseClicked(tile, widget, mouseX, mouseY, mouseButton, stack);
		if (widget instanceof WidgetButtonMode) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(getMode().ordinal() == getModeClass().getEnumConstants().length - 1){
				setMode(getModeClass().getEnumConstants()[0]);
				((WidgetButtonMode) widget).setMode(getMode());
			}else{
				setMode(getModeClass().getEnumConstants()[getMode().ordinal()+1]);
				((WidgetButtonMode) widget).setMode(getMode());
			}
			PacketHandler.INSTANCE.sendToServer(new PacketModularSwitchMode((TileModular) tile, getMode()));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("Mode", getMode().ordinal());
	}
	
	@Override
	public void writeCraftingModifiers(NBTTagCompound nbt, IModular modular, Object[] craftingModifiers) {
		IMachineMode mode = (IMachineMode) craftingModifiers[0];
		NBTTagCompound nbtCrafting = new NBTTagCompound();
		nbtCrafting.setInteger("Mode", mode.ordinal());
		nbt.setTag("Crafting", nbtCrafting);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.readFromNBT(nbt, modular, stack);
		setMode(getModeClass().getEnumConstants()[nbt.getInteger("Mode")]);
	}
	
	@Override
	public Object[] readCraftingModifiers(NBTTagCompound nbt, IModular modular) {
		NBTTagCompound nbtCrafting = nbt.getCompoundTag("Crafting");
		IMachineMode mode = getModeClass().getEnumConstants()[nbtCrafting.getInteger("Mode")];
		return new Object[]{ mode };
	}
	
	@Override
	public Object[] getCraftingModifiers(IModular modular, ModuleStack stack) {
		return new Object[]{ getMode() };
	}
	
	@Override
	public IMachineMode getMode() {
		return mode;
	}
	
	@Override
	public void setMode(IMachineMode mode) {
		this.mode = mode;
	}
	
}
