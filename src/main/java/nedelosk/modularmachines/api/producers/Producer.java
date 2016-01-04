package nedelosk.modularmachines.api.producers;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.client.renderer.IModularRenderer;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.producers.client.IProducerWithRenderer;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Producer implements IProducer, IProducerWithRenderer {

	public String modifier;

	public Producer(String modifier) {
		this.modifier = modifier;
	}

	public Producer(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		try {
			readFromNBT(nbt, modular, stack);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		modifier = nbt.getString("Modifier");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) throws Exception {
		nbt.setString("Modifier", modifier);
	}

	@Override
	public String getName(ModuleStack stack) {
		return "producer" + modifier;
	}

	@Override
	public String getModifier(ModuleStack stack) {
		return modifier;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getItemRenderer(IModular modular, ModuleStack moduleStack, ItemStack stack) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return null;
	}

	@Override
	public void updateServer(IModular modular, ModuleStack stack) {
	}

	@Override
	public void updateClient(IModular modular, ModuleStack stack) {
	}

	@Override
	public List<String> getRequiredModules() {
		return new ArrayList<String>();
	}

	@Override
	public boolean onBuildModular(IModular modular, ModuleStack stack, List<String> moduleNames) {
		ArrayList<String> requiredModules = new ArrayList<>();
		requiredModules.addAll(getRequiredModules());
		for (String moduleName : getRequiredModules()) {
			if (moduleNames.contains(moduleName))
				requiredModules.remove(moduleName);
			else
				return false;
		}
		if (!requiredModules.isEmpty())
			return false;
		return true;
	}

}
