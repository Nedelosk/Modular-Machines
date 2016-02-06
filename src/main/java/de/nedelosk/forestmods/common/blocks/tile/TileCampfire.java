package de.nedelosk.forestmods.common.blocks.tile;

import de.nedelosk.forestmods.client.gui.GuiCampfire;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.core.ItemManager;
import de.nedelosk.forestmods.common.crafting.CampfireRecipeManager;
import de.nedelosk.forestmods.common.crafting.CampfireRecipeManager.CampfireRecipe;
import de.nedelosk.forestmods.common.inventory.ContainerCampfire;
import de.nedelosk.forestmods.common.items.ItemCampfire;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileCampfire extends TileMachineBase {

	public int fuelStorage;
	public ItemStack output;

	public TileCampfire() {
		super(7);
		timerMax = 25;
	}

	@Override
	public String getMachineName() {
		return "campfire";
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Fuel", fuelStorage);
		if (output != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			output.writeToNBT(nbtTag);
			nbt.setTag("Output", nbtTag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.fuelStorage = nbt.getInteger("Fuel");
		if (nbt.hasKey("Output")) {
			NBTTagCompound Output = nbt.getCompoundTag("Output");
			output = ItemStack.loadItemStackFromNBT(Output);
		}
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerCampfire(this, inventory);
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return new GuiCampfire(this, inventory);
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
		if (burnTime >= burnTimeTotal || burnTimeTotal == 0) {
			if (output != null) {
				if (addToOutput(output, 3, 4)) {
					output = null;
					isWorking = false;
				}
			} else {
				ItemStack input = getStackInSlot(0);
				ItemStack input2 = getStackInSlot(1);
				if (input != null) {
					CampfireRecipe recipe = CampfireRecipeManager.getRecipe(input, input2,
							(getStackInSlot(6) != null) ? getStackInSlot(6).getItemDamage() + 1 : 0);
					if (recipe != null) {
						if (input.stackSize >= recipe.getInput().stackSize
								&& (recipe.getInput2() == null || input2 != null && input2.stackSize >= recipe.getInput2().stackSize)) {
							decrStackSize(0, recipe.getInput().stackSize);
							if (recipe.getInput2() != null) {
								decrStackSize(1, recipe.getInput2().stackSize);
							}
							output = recipe.getOutput().copy();
							isWorking = true;
							burnTimeTotal = recipe.getBurnTime();
							burnTime = 0;
						}
					}
				}
				if (output == null) {
					if (input2 != null) {
						CampfireRecipe recipe = CampfireRecipeManager.getRecipe(input2, input,
								(getStackInSlot(6) != null) ? getStackInSlot(6).getItemDamage() + 1 : 0);
						if (recipe != null) {
							if (input2.stackSize >= recipe.getInput().stackSize
									&& (recipe.getInput2() == null || input != null && input.stackSize >= recipe.getInput2().stackSize)) {
								decrStackSize(1, recipe.getInput().stackSize);
								if (recipe.getInput2() != null) {
									decrStackSize(0, recipe.getInput2().stackSize);
								}
								output = recipe.getOutput().copy();
								isWorking = true;
								burnTimeTotal = recipe.getBurnTime();
								burnTime = 0;
							}
						}
					}
				}
			}
		}
		if (getStackInSlot(6) != null) {
			if (getStackInSlot(5) == null) {
				EntityItem entityItem = new EntityItem(worldObj, xCoord, yCoord, zCoord, getStackInSlot(6));
				worldObj.spawnEntityInWorld(entityItem);
				setInventorySlotContents(6, null);
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		if (getCurbTier() == -1) {
			burnTime = 0;
			return;
		}
		ItemStack fuel = getStackInSlot(2);
		if (fuel != null) {
			if (TileEntityFurnace.getItemBurnTime(fuel) > 0) {
				if (fuelStorage < Config.campfireFuelStorageMax[getCurbTier()]
						&& !(TileEntityFurnace.getItemBurnTime(fuel) + fuelStorage > Config.campfireFuelStorageMax[getCurbTier()])) {
					fuelStorage = fuelStorage + TileEntityFurnace.getItemBurnTime(fuel);
					decrStackSize(2, 1);
					this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				} else if (fuelStorage > Config.campfireFuelStorageMax[getCurbTier()]) {
					fuelStorage = Config.campfireFuelStorageMax[getCurbTier()];
				}
			}
		}
		if (worldObj.isRaining()) {
			if (worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord)) {
				isWorking = false;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return;
			}
		}
		if (timer >= timerMax) {
			if (isWorking) {
				if (fuelStorage == 0) {
					isWorking = false;
				}
				if (output == null) {
					isWorking = false;
				}
				if (fuelStorage > 0) {
					fuelStorage -= 5;
					if (burnTime != burnTimeTotal) {
						burnTime++;
					}
					if (fuelStorage > Config.campfireFuelStorageMax[getCurbTier()]) {
						fuelStorage = Config.campfireFuelStorageMax[getCurbTier()];
					}
				}
				if (output != null) {
					isWorking = true;
				}
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				timer = 0;
			}
		} else {
			timer++;
		}
	}

	public int getCurbTier() {
		ItemStack curb = getStackInSlot(4);
		if (curb != null) {
			if (curb.getItem() instanceof ItemCampfire && ((ItemCampfire) curb.getItem()).itemName == "curb") {
				return curb.getItemDamage();
			}
		}
		return -1;
	}

	public ItemStack setCampfireItem(ItemStack stack) {
		int ID = 0;
		if (stack.getItem() == ItemManager.itemCampfireCurb) {
			ID = 0;
		} else if (stack.getItem() == ItemManager.itemCampfirePotHolder) {
			ID = 1;
		} else if (stack.getItem() == ItemManager.itemCampfirePot) {
			ID = 2;
		}
		ItemStack stackOld = getStackInSlot(4 + ID);
		setInventorySlotContents(ID + 4, stack);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return stackOld;
	}
}
