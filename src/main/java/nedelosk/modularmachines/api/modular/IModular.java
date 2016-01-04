package nedelosk.modularmachines.api.modular;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.basic.managers.IModularGuiManager;
import nedelosk.modularmachines.api.modular.basic.managers.IModularUtilsManager;
import nedelosk.modularmachines.api.modular.integration.IWailaData;
import nedelosk.modularmachines.api.modular.integration.IWailaProvider;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.energy.IProducerBattery;
import nedelosk.modularmachines.api.producers.managers.fluids.IProducerTankManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IModular {

	int getTier();

	void update(boolean isServer);

	IModularTileEntity getMachine();

	String getName();

	void initModular();

	void readFromNBT(NBTTagCompound nbt) throws Exception;

	void writeToNBT(NBTTagCompound nbt) throws Exception;

	// Utils
	ModuleStack<IModule, IProducerBattery> getBattery();

	ModuleStack<IModuleCasing, IProducer> getCasing();

	ModuleStack<IModule, IProducerTankManager> getTankManeger();

	boolean addModule(ModuleStack module);

	Vector<ModuleStack> getModule(String moduleName);

	ModuleStack getModule(String moduleName, int id);

	HashMap<String, Vector<ModuleStack>> getModules();

	void setModules(HashMap<String, Vector<ModuleStack>> modules);

	void setMachine(IModularTileEntity machine);

	IModularUtilsManager getManager();

	List<ModuleStack> getFluidProducers();

	// Gui
	IModularGuiManager getGuiManager();

	// Renderer
	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ItemStack stack);

	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, IModularTileEntity tile);

	// Item
	IModular buildItem(ItemStack[] stacks);

	IWailaProvider getWailaProvider(IModularTileEntity tile, IWailaData data);

}
