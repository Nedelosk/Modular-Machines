package nedelosk.modularmachines.api.modular;

import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.basic.container.module.IModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.IMultiModuleContainer;
import nedelosk.modularmachines.api.modular.basic.container.module.ISingleModuleContainer;
import nedelosk.modularmachines.api.modular.basic.managers.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.integration.IWailaData;
import nedelosk.modularmachines.api.modular.integration.IWailaProvider;
import nedelosk.modularmachines.api.modular.material.Materials.Material;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.utils.ModularException;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModular {

	/**
	 * @return The material from the casing of the machine
	 */
	Material getMaterial();

	void update(boolean isServer);

	void initModular();

	/**
	 * @return The name of the modular
	 */
	String getName();

	boolean addModule(ModuleStack stack);

	IModuleContainer getModule(String categoryUID);

	ModuleStack getModuleFromUID(String UID);

	ISingleModuleContainer getSingleModule(String moduleName);

	IMultiModuleContainer getMultiModule(String moduleName);

	/**
	 * @return All modules in a HashMap
	 */
	HashMap<String, IModuleContainer> getModuleContainers();

	/**
	 * @return All modules as ModuleStack
	 */
	List<ModuleStack> getModuleStacks();

	IModularTileEntity getMachine();

	void setMachine(IModularTileEntity machine);

	/**
	 * @return The urils manager of the modular
	 */
	IModularUtilsManager getUtilsManager();

	/* BUILD */
	void build() throws ModularException;

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
}
