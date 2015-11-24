package nedelosk.modularmachines.common.modular.module.tool.producer.machine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.forestday.api.guis.Widget;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.IMachineMode;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerEngine;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.modular.utils.ModuleUtils;
import nedelosk.modularmachines.client.gui.widget.WidgetButtonMode;
import nedelosk.modularmachines.client.gui.widget.WidgetProgressBar;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.lathe.RecipeLathe.LatheModes;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularSwitchMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract class ProducerMachineRecipeMode extends ProducerMachineRecipe {
	
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
				((WidgetButtonMode)widget).mode = mode;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(IModularTileEntity tile, Widget widget, int mouseX, int mouseY, int mouseButton, ModuleStack stack) {
		super.handleMouseClicked(tile, widget, mouseX, mouseY, mouseButton, stack);
		if (widget instanceof WidgetButtonMode) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			if(mode.ordinal() == getModeClass().getEnumConstants().length - 1){
				mode = getModeClass().getEnumConstants()[0];
				((WidgetButtonMode) widget).mode = mode;
			}else{
				mode = getModeClass().getEnumConstants()[mode.ordinal()+1];
				((WidgetButtonMode) widget).mode = mode;
			}
			PacketHandler.INSTANCE.sendToServer(new PacketModularSwitchMode((TileModular) tile, mode));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		super.writeToNBT(nbt, modular, stack);
		nbt.setInteger("Mode", mode.ordinal());
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
		mode = getModeClass().getEnumConstants()[nbt.getInteger("Mode")];
	}
	
	@Override
	public Object[] readCraftingModifiers(NBTTagCompound nbt, IModular modular) {
		NBTTagCompound nbtCrafting = nbt.getCompoundTag("Crafting");
		IMachineMode mode = getModeClass().getEnumConstants()[nbtCrafting.getInteger("Mode")];
		return new Object[]{ mode };
	}
	
	@Override
	public Object[] getCraftingModifiers(IModular modular, ModuleStack stack) {
		return new Object[]{ mode };
	}
	
	public abstract Class<? extends IMachineMode> getModeClass();
	
}
