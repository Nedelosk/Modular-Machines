package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.structure.base.gui.GuiBusItem;
import nedelosk.forestday.structure.base.gui.container.ContainerBusItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.IFluidContainerItem;

public class TileBusItem extends TileBus {
	
	public TileBusItem() {
		super(5000, "busItem");
	}
	
	public TileBusItem(int heat, String uid, IIcon icon) {
		super(heat, "bus" + uid);
		this.overayIcon = icon;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int p_102008_3_) 
	{
		if(mode == Mode.Output)
		{
		return true;
		}
		return false;
	}
	
	 @Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3};
	}

	private IIcon overayIcon;
	
	public IIcon getOverlayTexture()
	{
		return overayIcon;
	}

	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerBusItem(inventory, this);
	}

	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiBusItem(inventory, this);
	} 
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
	}

	@Override
	public String getMachineTileName() {
		return "structure.bus.item";
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
	}
	
}
