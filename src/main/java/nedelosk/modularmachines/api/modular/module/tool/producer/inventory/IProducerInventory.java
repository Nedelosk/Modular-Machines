package nedelosk.modularmachines.api.modular.module.tool.producer.inventory;

import java.util.List;

import nedelosk.forestday.api.guis.IContainerBase;
import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.gui.IProducerGui;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public interface IProducerInventory extends IProducerGui {

	List<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack);

	int getSizeInventory(ModuleStack stack);
	
	ItemStack transferStackInSlot(ModuleStack<IModule, IProducerInventory> stack, IModularTileEntity tile, EntityPlayer player, int slotID, Container container);

}
