package de.nedelosk.forestmods.api.modular;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.client.IModularRenderer;
import de.nedelosk.forestmods.api.modular.integration.IWailaData;
import de.nedelosk.forestmods.api.modular.integration.IWailaProvider;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.modular.managers.IModularUtilsManager;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.utils.ModularException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModular {

	/**
	 * @return The tier from of the modular
	 */
	int getTier();

	void update(boolean isServer);

	void initModular();

	/**
	 * @return The name of the modular
	 */
	String getName();

	IModularTileEntity getMachine();

	void setMachine(IModularTileEntity machine);

	/**
	 * @return The urils manager of the modular
	 */
	IModularUtilsManager getUtilsManager();

	IModularModuleManager getModuleManager();

	/* BUILD */
	void assemble() throws ModularException;

	boolean isAssembled();

	/* NBT */
	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

	/* Renderer */
	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ItemStack stack);

	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile);

	// Waila
	IWailaProvider getWailaProvider(IModularTileEntity tile, IWailaData data);

	ModularException getLastException();
}
