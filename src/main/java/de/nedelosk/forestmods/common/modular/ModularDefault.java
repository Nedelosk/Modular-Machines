package de.nedelosk.forestmods.common.modular;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modular.managers.IModularGuiManager;
import de.nedelosk.forestmods.api.modular.managers.IModularInventoryManager;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.common.modular.managers.ModularGuiManager;
import de.nedelosk.forestmods.common.modular.managers.ModularInventoryManager;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ModularDefault extends Modular implements IModularDefault {

	@SideOnly(Side.CLIENT)
	protected IModularGuiManager guiManager;
	protected IModularInventoryManager inventoryManager;

	public ModularDefault() {
		inventoryManager = new ModularInventoryManager();
		inventoryManager.setModular(this);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			guiManager = new ModularGuiManager();
			guiManager.setModular(this);
		}
	}

	public ModularDefault(NBTTagCompound nbt, IModularTileEntity machine) {
		super(nbt, machine);
	}

	@Override
	public IModularInventoryManager getInventoryManager() {
		if (inventoryManager == null) {
			inventoryManager = new ModularInventoryManager();
			inventoryManager.setModular(this);
		}
		return inventoryManager;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModularGuiManager getGuiManager() {
		if (guiManager == null) {
			guiManager = new ModularGuiManager();
			guiManager.setModular(this);
		}
		return guiManager;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagCompound inventory = new NBTTagCompound();
		getInventoryManager().writeToNBT(inventory);
		nbt.setTag("InventoryManager", inventory);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			NBTTagCompound gui = new NBTTagCompound();
			getGuiManager().writeToNBT(gui);
			nbt.setTag("GuiManager", gui);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		getInventoryManager().readFromNBT(nbt.getCompoundTag("InventoryManager"));
		if (nbt.hasKey("GuiManager")) {
			getGuiManager().readFromNBT(nbt.getCompoundTag("GuiManager"));
		}
	}
}
