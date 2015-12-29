package nedelosk.modularmachines.api.producers.machines.recipe;

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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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
			PacketHandler.INSTANCE.sendToServer(new PacketSwitchMode((TileEntity) tile, getMode()));
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
