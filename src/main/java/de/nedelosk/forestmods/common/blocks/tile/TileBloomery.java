package de.nedelosk.forestmods.common.blocks.tile;

import de.nedelosk.forestmods.common.config.Config;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

public class TileBloomery extends TileMachineBase {

	public boolean isBurned;

	public TileBloomery() {
		super(3);
		burnTimeTotal = Config.bloomeryBurningTime;
		burnTime = burnTimeTotal;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("isBurned", isBurned);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		isBurned = nbt.getBoolean("isBurned");
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
		if(isBurned){
		}else{
			if(burnTime <= 0){
				isBurned = true;
			}else{
				if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.fire){
					burnTime--;
				}
			}
		}
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public String getTitle() {
		return "Bloomery";
	}
}
