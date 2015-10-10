package nedelosk.modularmachines.common.modular.module.basic.basic;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularRenderer;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.module.basic.basic.IModuleCasing;
import nedelosk.modularmachines.api.modular.module.basic.inventory.ModuleInventory;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.client.renderers.modules.ModularMachineRenderer;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

public class ModuleCasing extends ModuleInventory implements IModuleCasing {

	public ModuleCasing() {
	}
	
	public ModuleCasing(NBTTagCompound nbt) {
		super(nbt);
	}
	
	public ModuleCasing(String modifier) {
		super(modifier);
	}
	
	@Override
	public String getModuleName() {
		return "Casing";
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 143, 17, getName()));
		list.add(new SlotModular(modular.getMachine(), 1, 143, 35, getName()));
		list.add(new SlotModular(modular.getMachine(), 2, 143, 53, getName()));
		return list;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}
	
	@Override
	public IModularRenderer getMachineRenderer(IModular modular, ModuleStack moduleStack, IModularTileEntity tile) {
		return new ModularMachineRenderer.CasingRenderer(moduleStack);
	}

}
