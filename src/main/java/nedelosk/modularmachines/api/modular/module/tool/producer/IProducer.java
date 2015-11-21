package nedelosk.modularmachines.api.modular.module.tool.producer;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IProducer {

	void updateServer(IModular modular, ModuleStack stack);

	void updateClient(IModular modular, ModuleStack stack);

	void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception;

	void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception;

	String getName(ModuleStack stack);

	String getModifier(ModuleStack stack);

	@SideOnly(Side.CLIENT)
	IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack);

	@SideOnly(Side.CLIENT)
	IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile);

	List<String> getRequiredModules();

	boolean onBuildModular(IModular modular, ModuleStack stack, List<String> moduleNames);
	
	boolean hasFluids();

}
