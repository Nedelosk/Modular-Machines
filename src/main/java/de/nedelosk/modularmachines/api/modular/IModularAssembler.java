package de.nedelosk.modularmachines.api.modular;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.EnumStoragePosition;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IModularAssembler extends INBTSerializable<NBTTagCompound> {

	@Nonnull
	IModularHandler getHandler();

	@Nullable
	IModular assemble() throws AssemblerException;

	@SideOnly(Side.CLIENT)
	GuiContainer createGui(IModularHandler tile, InventoryPlayer inventory);

	Container createContainer(IModularHandler tile, InventoryPlayer inventory);

	IItemHandlerModifiable getAssemblerHandler();

	int getComplexity(boolean withStorage, EnumStoragePosition position);

	int getAllowedComplexity(EnumStoragePosition position);

	IModularAssembler copy(IModularHandler handler);

}
