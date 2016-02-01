package nedelosk.modularmachines.api.modular;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.integration.IWailaData;
import nedelosk.modularmachines.api.modular.integration.IWailaProvider;
import nedelosk.modularmachines.api.modular.managers.IModularModuleManager;
import nedelosk.modularmachines.api.modular.managers.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.utils.ModularException;
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
