package nedelosk.forestday.common.blocks.tiles;

import java.util.ArrayList;

import nedelosk.forestcore.utils.ItemUtils;
import nedelosk.forestday.api.crafting.ForestdayCrafting;
import nedelosk.forestday.api.crafting.ITool;
import nedelosk.forestday.client.gui.GuiWorkbench;
import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.common.crafting.WorkbenchRecipe;
import nedelosk.forestday.common.crafting.WorkbenchRecipeManager;
import nedelosk.forestday.common.inventory.ContainerWorkbench;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileWorkbench extends TileMachineBase {

	public boolean updateClient;
	public ItemStack output;
	private int timer;

	public TileWorkbench() {
		super(21);
		if (this.mode == null) {
			this.mode = Mode.stop_processing;
		}
	}

	public Mode mode;

	public static enum Mode {
		further_processing, stop_processing;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		if (this.mode != null) {
			nbt.setInteger("Mode", this.mode.ordinal());
		}
		if (this.output != null) {
			NBTTagCompound item = new NBTTagCompound();
			output.writeToNBT(item);
			nbt.setTag("Output", item);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.mode = Mode.values()[nbt.getInteger("Mode")];
		if (this.output != null) {
			NBTTagCompound item = nbt.getCompoundTag("Output");
			;
			output.readFromNBT(item);
		} else {
			output = null;
		}
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerWorkbench(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiWorkbench(this, inventory);
	}

	@Override
	public void updateClient() {
	}

	@Override
	public void updateServer() {
		if (updateClient) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			updateClient = false;
		}

		if (timer == 120 || isWorking) {
			updateRecipe();
			timer = 0;
		} else {
			timer++;
		}
	}

	public void setMode(Mode mode) {
		if (this.mode != mode) {
			this.mode = mode;
			updateClient = true;
		}
	}

	public Mode getMode() {
		return mode;
	}

	public void updateRecipe() {
		ItemStack stackSlotInput = getStackInSlot(0);
		ItemStack stackSlotTool = getStackInSlot(2);
		ItemStack stackSlotStorage = getStackInSlot(4);
		ItemStack stackSlotPattern = getStackInSlot(3);
		if (stackSlotStorage != null && stackSlotInput == null) {
			setInventorySlotContents(0, stackSlotStorage.copy());
			decrStackSize(4, stackSlotStorage.stackSize);
		} else if (stackSlotStorage != null && stackSlotInput != null) {
			if (stackSlotStorage.getItem() == stackSlotInput.getItem()
					&& stackSlotStorage.getItemDamage() == stackSlotInput.getItemDamage()
					&& ItemStack.areItemStackTagsEqual(stackSlotStorage, stackSlotInput)) {
				for (int i = stackSlotStorage.stackSize; i > 0; i--) {
					if (stackSlotInput.stackSize + i <= stackSlotInput.getMaxStackSize()) {
						stackSlotInput.stackSize = stackSlotInput.stackSize + i;
						decrStackSize(4, i);
					}
				}
			}
		}
		if (stackSlotTool != null) {
			if (burnTime < burnTimeTotal) {
				burnTime++;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			} else if (output != null) {
				if (mode == Mode.further_processing
						&& !containsStack(ForestdayCrafting.workbenchRecipe.getOutputs(), output)) {
					if (addToOutput(output, 4, 5)) {
						output = null;
						isWorking = false;
					}
				} else if (addToOutput(output, 1, 2)) {
					output = null;
					isWorking = false;
				}
			} else if (stackSlotInput != null) {
				WorkbenchRecipe recipe = WorkbenchRecipeManager.getRecipe(stackSlotInput, stackSlotTool, stackSlotPattern);
				if (recipe != null) {
					if (stackSlotTool.attemptDamageItem(((stackSlotTool.getItem() instanceof ITool) ? ((ITool) stackSlotTool.getItem()).getDamage() : 2), worldObj.rand)) {
						decrStackSize(2, 1);
					}
					decrStackSize(0, (recipe.getInput() != null) ? recipe.getInput().stackSize : recipe.getsInput().stackSize);
					output = recipe.getOutput();
					burnTime = 0;
					burnTimeTotal = (recipe.getBurnTime() == -1) ? ForestDayConfig.worktableBurnTime : recipe.getBurnTime();
					isWorking = true;
				}
			}
		}
	}

	public boolean containsStack(ArrayList<ItemStack> outputs, ItemStack stackOutput) {
		for (ItemStack stack : outputs) {
			if (ItemUtils.isItemEqual(stack, stackOutput))
				return true;
		}
		return false;
	}

	@Override
	public String getMachineName() {
		return "workbench";
	}

}
