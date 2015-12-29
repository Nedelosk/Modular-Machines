package nedelosk.modularmachines.api.producers.inventory;

import java.util.List;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.gui.IProducerGui;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public interface IProducerInventory extends IProducerGui {

	List<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack);

	int getSizeInventory(ModuleStack stack);
	
	ItemStack transferStackInSlot(ModuleStack<IModule, IProducerInventory> stack, IModularTileEntity tile, EntityPlayer player, int slotID, Container container);

}
