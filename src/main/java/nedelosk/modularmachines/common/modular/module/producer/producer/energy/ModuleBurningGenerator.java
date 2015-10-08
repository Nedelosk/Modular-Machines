package nedelosk.modularmachines.common.modular.module.producer.producer.energy;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.gui.IModuleGuiWithWidgets;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleBurningGenerator extends ModuleGenerator implements IModuleGuiWithWidgets{

	public ModuleBurningGenerator(NBTTagCompound nbt) {
		super(nbt);
	}
	
	public ModuleBurningGenerator(String modifier) {
		super(modifier);
	}

	@Override
	public PartType[] getRequiredComponents() {
		return null;
	}

	@Override
	public ModuleStack creatModule(ItemStack stack) {
		return null;
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		return null;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public int getSpeed() {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

}
