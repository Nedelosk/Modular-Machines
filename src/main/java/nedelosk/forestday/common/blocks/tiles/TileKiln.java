package nedelosk.forestday.common.blocks.tiles;

import nedelosk.forestcore.api.FluidTankBasic;
import nedelosk.forestday.client.gui.GuiKiln;
import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.common.crafting.KilnRecipe;
import nedelosk.forestday.common.crafting.KilnRecipeManager;
import nedelosk.forestday.common.inventory.ContainerKiln;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileKiln extends TileMachineBase implements IFluidHandler {

	private int heatTimer = 25;
	public int heat;

	private ItemStack currentOutput1;
	private ItemStack currentOutput2;

	public FluidTankBasic tank1 = new FluidTankBasic(16000);
	public FluidTankBasic tank2 = new FluidTankBasic(16000);
	public FluidTankBasic tankLava = new FluidTankBasic(4000);

	public TileKiln() {
		super(4);
		burnTimeTotal = ForestDayConfig.kilnBurnTime;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerKiln(inventory, this);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiKiln(inventory, this);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		switch (from) {
		case UP:
			return tank1.drain(resource, doDrain);
		case DOWN:
			return tank2.drain(resource, doDrain);
		default:
			return null;
		}
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		switch (from) {
		case UP:
			return tank1.drain(maxDrain, doDrain);
		case DOWN:
			return tank2.drain(maxDrain, doDrain);
		default:
			return null;
		}
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (this.currentOutput1 != null) {
			NBTTagCompound item = new NBTTagCompound();
			this.currentOutput1.writeToNBT(item);
			nbt.setTag("currentOutput1", item);
		}
		if (this.currentOutput2 != null) {
			NBTTagCompound item = new NBTTagCompound();
			this.currentOutput2.writeToNBT(item);
			nbt.setTag("currentOutput2", item);
		}
		if (this.tank1 != null) {
			NBTTagCompound fluid = new NBTTagCompound();
			fluid.setInteger("Capacity", tank1.getCapacity());
			this.tank1.writeToNBT(fluid);
			nbt.setTag("tank1", fluid);
		}
		if (this.tank2 != null) {
			NBTTagCompound fluid = new NBTTagCompound();
			fluid.setInteger("Capacity", tank2.getCapacity());
			this.tank2.writeToNBT(fluid);
			nbt.setTag("tank2", fluid);
		}
		if (this.tankLava != null) {
			NBTTagCompound fluid = new NBTTagCompound();
			fluid.setInteger("Capacity", tankLava.getCapacity());
			this.tankLava.writeToNBT(fluid);
			nbt.setTag("tankLava", fluid);
		}
		nbt.setInteger("Heat", heat);
		nbt.setInteger("HeatTimer", heatTimer);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("currentOutput1")) {
			NBTTagCompound item = nbt.getCompoundTag("currentOutput1");
			this.currentOutput1 = ItemStack.loadItemStackFromNBT(item);
		}
		if (nbt.hasKey("currentOutput2")) {
			NBTTagCompound item = nbt.getCompoundTag("currentOutput2");
			this.currentOutput2 = ItemStack.loadItemStackFromNBT(item);
		}
		if (nbt.hasKey("tank1")) {
			NBTTagCompound fluid = nbt.getCompoundTag("tank1");
			tank1 = new FluidTankBasic(fluid.getInteger("Capacity"));
			tank1.readFromNBT(fluid);
		}
		if (nbt.hasKey("tank2")) {
			NBTTagCompound fluid = nbt.getCompoundTag("tank2");
			tank2 = new FluidTankBasic(fluid.getInteger("Capacity"));
			tank2.readFromNBT(fluid);
		}
		if (nbt.hasKey("tankLava")) {
			NBTTagCompound fluid = nbt.getCompoundTag("tankLava");
			tankLava = new FluidTankBasic(fluid.getInteger("Capacity"));
			tankLava.readFromNBT(fluid);
		}
		heat = nbt.getInteger("Heat");
		heatTimer = nbt.getInteger("HeatTimer");
	}

	@Override
	public int getScaledProcess(int maxWidth) {
		return (this.burnTime > 0) ? (this.burnTime * maxWidth) / this.burnTimeTotal : 0;
	}

	public int getScaledHeat(int maxWidth) {
		return (this.heat > 0) ? (this.heat * maxWidth) / ForestDayConfig.kilnMinHeat : 0;
	}

	@Override
	public void updateClient() {

	}

	@Override
	public void updateServer() {
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		int lavaSources = 0;
		if (heat < ForestDayConfig.kilnMaxHeat) {
			if (!tankLava.isEmpty()) {
				if (heatTimer >= 25) {
					if (tankLava.drain(2, true) != null)
						heatTimer = 0;
				} else {
					heatTimer++;
				}
			} else {
				if (heatTimer >= 50) {
					heatTimer--;
				} else {
					heatTimer++;
				}
			}
		} else {
			if (!tankLava.isEmpty()) {
				if (heatTimer >= 50) {
					if (tankLava.drain(2, true) != null)
						heatTimer = 0;
				} else {
					heatTimer++;
				}
			} else {
				if (heatTimer >= 50) {
					heatTimer--;
				} else {
					heatTimer++;
				}
			}
		}
		if (!this.tank1.isFull() && !this.tank2.isFull()) {
			if (heat >= ForestDayConfig.kilnMinHeat) {
				if (burnTime < burnTimeTotal) {
					burnTime++;
				} else {
					if (this.currentOutput1 != null) {
						if (this.addToOutput(this.currentOutput1, 2, 4)
								&& this.addToOutput(this.currentOutput2, 2, 4)) {
							this.currentOutput1 = null;
							this.currentOutput2 = null;
							tank1.fill(new FluidStack(FluidRegistry.getFluid("tar"), 300), true);
							tank2.fill(new FluidStack(FluidRegistry.getFluid("resin"), 150), true);
						}
					} else {
						ItemStack itemStackInputSlot1 = this.getStackInSlot(0);
						ItemStack itemStackInputSlot2 = this.getStackInSlot(1);
						if (itemStackInputSlot1 != null && itemStackInputSlot2 != null) {
							KilnRecipe recipe = KilnRecipeManager.getRecipe(itemStackInputSlot1, itemStackInputSlot2);
							if (recipe != null) {
								if (itemStackInputSlot1.stackSize >= recipe.getInput1().stackSize
										&& itemStackInputSlot2.stackSize >= recipe.getInput2().stackSize) {
									this.decrStackSize(0, recipe.getInput1().stackSize);
									this.decrStackSize(1, recipe.getInput2().stackSize);
									this.currentOutput1 = recipe.getOutput1();
									this.currentOutput2 = recipe.getOutput2();
									this.burnTime = 0;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { tank1.getInfo(), tank2.getInfo() };
	}

	@Override
	public String getMachineName() {
		return "kiln";
	}

}
