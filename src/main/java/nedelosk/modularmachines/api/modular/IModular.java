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
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modular.type.Materials.Material;
import nedelosk.modularmachines.api.modules.fluids.IModuleWithFluid;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModular {

	Material getMaterial();

	void update(boolean isServer);

	IModularTileEntity getMachine();

	String getName();

	void initModular();

	void readFromNBT(NBTTagCompound nbt) throws Exception;

	void writeToNBT(NBTTagCompound nbt) throws Exception;

	boolean addModule(ModuleStack stack);

	IModuleContainer getModule(String categoryUID);

	ModuleStack getModuleFromUID(String UID);

	ISingleModuleContainer getSingleModule(String moduleName);

	IMultiModuleContainer getMultiModule(String moduleName);

	HashMap<String, IModuleContainer> getModuleContainers();

	List<ModuleStack> getModuleStacks();

	void setModules(HashMap<String, IModuleContainer> modules);

	void setMachine(IModularTileEntity machine);

	IModularUtilsManager getManager();

	List<ModuleStack<IModuleWithFluid>> getFluidProducers();

	// Item
	IModular buildItem(ItemStack[] stacks);

	// Renderer
	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ItemStack stack);

	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile);

	IWailaProvider getWailaProvider(IModularTileEntity tile, IWailaData data);
}
