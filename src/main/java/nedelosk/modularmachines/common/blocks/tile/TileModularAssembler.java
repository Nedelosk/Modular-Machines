package nedelosk.modularmachines.common.blocks.tile;

import nedelosk.forestday.common.blocks.tiles.TileBaseInventory;
import nedelosk.modularmachines.client.gui.assembler.GuiModularAssembler;
import nedelosk.modularmachines.common.inventory.assembler.ContainerModularAssembler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileModularAssembler extends TileBaseInventory {
	
	public int tier;
	
	public TileModularAssembler() {
		super(9);
	}
	
	public TileModularAssembler(int tier) {
		super(9);
		this.tier = tier;
	}

	@Override
	public String getMachineTileName() {
		return "modular.assembler";
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerModularAssembler(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiModularAssembler(this, inventory);
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Tier", tier);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tier = nbt.getInteger("Tier");
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int p_102008_3_) 
	{
		return false;
	}

}
