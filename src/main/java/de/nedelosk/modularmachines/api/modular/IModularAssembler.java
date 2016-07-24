package de.nedelosk.modularmachines.api.modular;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public interface IModularAssembler extends INBTSerializable<NBTTagCompound> {

	EnumPosition getSelectedPosition();

	void setSelectedPosition(EnumPosition position);

	@Nonnull
	IModularHandler getHandler();

	@Nullable
	IModular assemble() throws AssemblerException;

	@SideOnly(Side.CLIENT)
	GuiContainer createGui(IModularHandler tile, InventoryPlayer inventory);

	Container createContainer(IModularHandler tile, InventoryPlayer inventory);

	IAssemblerLogic getLogic(EnumPosition pos);

	IItemHandler getAssemblerHandler();

	IModularAssembler copy(IModularHandler handler);

}
