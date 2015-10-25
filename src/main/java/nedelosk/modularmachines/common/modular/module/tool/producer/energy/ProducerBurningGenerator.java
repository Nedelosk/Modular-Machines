package nedelosk.modularmachines.common.modular.module.tool.producer.energy;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.forestday.api.guis.IGuiBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.tool.producer.energy.IProducerGenerator;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.NeiStack;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ProducerBurningGenerator extends ProducerGenerator implements IProducerGenerator {

	public ProducerBurningGenerator(String modifier) {
		super(modifier);
	}
	
	public ProducerBurningGenerator(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		return null;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 1;
	}

	@Override
	public int getSpeed(ModuleStack stack) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular, ModuleStack stack) {
		
	}

	@Override
	public ArrayList<NeiStack> addNEIStacks(ModuleStack stack) {
		return null;
	}

}
