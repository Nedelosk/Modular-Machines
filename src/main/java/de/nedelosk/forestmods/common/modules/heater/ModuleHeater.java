package de.nedelosk.forestmods.common.modules.heater;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.handlers.IModulePage;
import de.nedelosk.forestmods.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.modules.heater.IModuleHeater;
import de.nedelosk.forestmods.common.modules.Module;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class ModuleHeater extends Module implements IModuleHeater {

	protected int heat;
	protected int burnTime;

	public ModuleHeater(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		heat = nbt.getInteger("Heat");
		burnTime = nbt.getInteger("BurnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		super.writeToNBT(nbt, modular);
		nbt.setInteger("Heat", heat);
		nbt.setInteger("BurnTime", burnTime);
	}

	@Override
	public int getHeat() {
		return heat;
	}

	@Override
	public void setHeat(int heat) {
		this.heat = heat;
	}

	@Override
	public void addHeat(int heat) {
		this.heat += heat;
	}

	@Override
	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	@Override
	public void addBurnTime(int burnTime) {
		this.burnTime += burnTime;
	}

	@Override
	public void updateServer() {
		if (getBurnTime() > 0) {
			addHeat(1);
			addBurnTime(-10);
			PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), moduleStack));
		} else {
			IModuleInventory inventory = moduleStack.getModule().getInventory();
			ItemStack input = inventory.getStackInSlot(0);
			if (TileEntityFurnace.getItemBurnTime(input) > 0 && getHeat() < 100) {
				inventory.decrStackSize(0, 1);
				setBurnTime(TileEntityFurnace.getItemBurnTime(input));
			}
		}
	}

	@Override
	protected IModulePage[] createPages() {
		return null;
	}
}
