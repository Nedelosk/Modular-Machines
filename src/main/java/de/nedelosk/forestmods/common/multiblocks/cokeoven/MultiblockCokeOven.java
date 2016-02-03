package de.nedelosk.forestmods.common.multiblocks.cokeoven;

import java.util.HashSet;
import java.util.Set;

import de.nedelosk.forestcore.fluids.FluidTankSimple;
import de.nedelosk.forestcore.fluids.TankManager;
import de.nedelosk.forestcore.inventory.InventoryAdapter;
import de.nedelosk.forestcore.multiblock.IMultiblockPart;
import de.nedelosk.forestcore.multiblock.MultiblockControllerBase;
import de.nedelosk.forestcore.multiblock.MultiblockValidationException;
import de.nedelosk.forestcore.multiblock.RectangularMultiblockControllerBase;
import de.nedelosk.forestcore.utils.Log;
import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenAccessPort;
import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenFluidPort;
import de.nedelosk.forestmods.common.blocks.tile.TileCokeOvenFluidPort.PortType;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.crafting.CokeOvenRecipeManager;
import de.nedelosk.forestmods.common.crafting.CokeOvenRecipeManager.CokeOvenRecipe;
import de.nedelosk.forestmods.common.multiblocks.blastfurnace.MultiblockBlastFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MultiblockCokeOven extends RectangularMultiblockControllerBase {

	private InventoryAdapter inventory = new InventoryAdapter(1, "CokeOven", 64);
	private TankManager tankManager = new TankManager(new FluidTankSimple(16000), // FUEL
			new FluidTankSimple(16000)); // OUTPUT
	public ItemStack output;
	private Set<TileCokeOvenAccessPort> attachedAccessPorts;
	private Set<TileCokeOvenFluidPort> attachedFluidPortsFuel;
	private Set<TileCokeOvenFluidPort> attachedFluidPortsOutput;
	private boolean isActive;
	private int burnTime;
	private int burnTimeTotal;
	private int heat;
	private int heatTotal;
	private static int heatMax = Config.bastFurnaceMaxHeat;

	public MultiblockCokeOven(World world) {
		super(world);
		attachedAccessPorts = new HashSet<TileCokeOvenAccessPort>();
		attachedFluidPortsFuel = new HashSet<TileCokeOvenFluidPort>();
		attachedFluidPortsOutput = new HashSet<TileCokeOvenFluidPort>();
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		readFromNBT(data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart part) {
		if (part instanceof TileCokeOvenAccessPort) {
			attachedAccessPorts.add((TileCokeOvenAccessPort) part);
		} else if (part instanceof TileCokeOvenFluidPort) {
			if (((TileCokeOvenFluidPort) part).getPortType() == PortType.FUEL) {
				attachedFluidPortsFuel.add((TileCokeOvenFluidPort) part);
			} else if (((TileCokeOvenFluidPort) part).getPortType() == PortType.OUTPUT) {
				attachedFluidPortsOutput.add((TileCokeOvenFluidPort) part);
			}
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart part) {
		if (part instanceof TileCokeOvenAccessPort) {
			attachedAccessPorts.remove(part);
		} else if (part instanceof TileCokeOvenFluidPort) {
			if (((TileCokeOvenFluidPort) part).getPortType() == PortType.FUEL) {
				attachedFluidPortsFuel.remove(part);
			} else if (((TileCokeOvenFluidPort) part).getPortType() == PortType.OUTPUT) {
				attachedFluidPortsOutput.remove(part);
			}
		}
	}

	@Override
	protected void onMachineAssembled() {
	}

	@Override
	protected void onMachineRestored() {
	}

	@Override
	protected void onMachinePaused() {
	}

	@Override
	protected void onMachineDisassembled() {
		isActive = false;
		heat = 0;
	}

	@Override
	protected void isMachineWhole() throws MultiblockValidationException {
		if (attachedFluidPortsFuel.size() < 1) {
			throw new MultiblockValidationException("Not enough fluid fuel ports. Coke Oven require at least 1.");
		}
		if (attachedFluidPortsOutput.size() < 1) {
			throw new MultiblockValidationException("Not enough output ports. Coke Oven require at least 1.");
		}
		if (attachedAccessPorts.size() < 1) {
			throw new MultiblockValidationException("Not enough fuel access ports. Coke Oven require at least 1.");
		}
		super.isMachineWhole();
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 26;
	}

	@Override
	protected int getMaximumXSize() {
		return 4;
	}

	@Override
	protected int getMaximumZSize() {
		return 4;
	}

	@Override
	protected int getMaximumYSize() {
		return 7;
	}

	@Override
	protected int getMinimumXSize() {
		return 3;
	}

	@Override
	protected int getMinimumYSize() {
		return 3;
	}

	@Override
	protected int getMinimumZSize() {
		return 3;
	}

	@Override
	protected void isBlockGoodForInterior(World world, int x, int y, int z) throws MultiblockValidationException {
		if (!world.isAirBlock(x, y, z)) {
			super.isBlockGoodForInterior(world, x, y, z);
		}
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase otherMachine) {
		if (!(otherMachine instanceof MultiblockBlastFurnace)) {
			Log.warn("[%s] Coke Oven @ %s is attempting to assimilate a non-Coke Oven machine! That machine's data will be lost!",
					worldObj.isRemote ? "CLIENT" : "SERVER", getReferenceCoord());
			return;
		}
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		this.attachedAccessPorts.clear();
		this.attachedFluidPortsFuel.clear();
		this.attachedFluidPortsOutput.clear();
	}

	@Override
	protected boolean updateServer() {
		if (heat >= heatTotal || heatTotal == 0) {
			if (burnTime >= burnTimeTotal || burnTimeTotal == 0) {
				ItemStack input = getInventory().getStackInSlot(0);
				if (output != null) {
					if (addToOutput(output, 0, 1)) {
						output = null;
					}
				} else if (input != null) {
					CokeOvenRecipe recipe = CokeOvenRecipeManager.getRecipe(input);
					if (recipe != null) {
						output = recipe.getOutput();
						burnTimeTotal = recipe.getBurntTime();
						return true;
					}
				}
			} else {
				burnTime++;
			}
		} else {
			if (heat < heatMax) {
				if (getTankManager().getTank(0).getFluid() != null && getTankManager().getTank(0).getFluid().amount > 0) {
					int heat = getTankManager().getTank(0).getFluid().getFluid().getTemperature()
							/ ((heatTotal / getTankManager().getTank(0).getFluid().getFluid().getTemperature()) / 30);
					if (getTankManager().getTank(0).drain(30, true) != null & getTankManager().getTank(0).drain(30, true).amount >= 30) {
						this.heat += heat;
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	protected void updateClient() {
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		NBTTagCompound nbtManager = new NBTTagCompound();
		tankManager.writeToNBT(nbtManager);
		data.setTag("TankManager", nbtManager);
		NBTTagCompound nbtInventory = new NBTTagCompound();
		inventory.writeToNBT(nbtInventory);
		data.setTag("Inventory", nbtInventory);
		if (output != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			output.writeToNBT(nbtTag);
			data.setTag("Output", nbtTag);
		}
		data.setInteger("Heat", heat);
		data.setInteger("HeatTotal", heatTotal);
		data.setInteger("BurnTime", burnTime);
		data.setInteger("BurnTimeTotal", burnTimeTotal);
		data.setBoolean("IsActive", isActive);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		NBTTagCompound nbtTank = data.getCompoundTag("TankManager");
		tankManager = new TankManager(nbtTank);
		NBTTagCompound nbtInventory = data.getCompoundTag("Inventory");
		inventory = new InventoryAdapter(1, "CokeOven", 16);
		inventory.readFromNBT(nbtInventory);
		if (data.hasKey("Output")) {
			NBTTagCompound nbtTag = data.getCompoundTag("Output");
			output = ItemStack.loadItemStackFromNBT(nbtTag);
		}
		heat = data.getInteger("Heat");
		heatTotal = data.getInteger("HeatTotal");
		burnTime = data.getInteger("BurnTime");
		burnTimeTotal = data.getInteger("BurnTimeTotal");
		isActive = data.getBoolean("IsActive");
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound data) {
		writeToNBT(data);
	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {
		readFromNBT(data);
	}

	public TankManager getTankManager() {
		return tankManager;
	}

	public InventoryAdapter getInventory() {
		return inventory;
	}

	public boolean addToOutput(ItemStack output, int slotMin, int slotMax) {
		if (output == null) {
			return true;
		}
		for ( int i = slotMin; i < slotMax; i++ ) {
			ItemStack itemStack = getInventory().getStackInSlot(i);
			if (itemStack == null) {
				getInventory().setInventorySlotContents(i, output);
				return true;
			} else {
				if (itemStack.getItem() == output.getItem() && itemStack.getItemDamage() == output.getItemDamage()) {
					if (itemStack.stackSize < itemStack.getMaxStackSize()) {
						int avaiableSpaceOnStack = itemStack.getMaxStackSize() - itemStack.stackSize;
						if (avaiableSpaceOnStack >= output.stackSize) {
							itemStack.stackSize = itemStack.stackSize + output.stackSize;
							getInventory().setInventorySlotContents(i, itemStack);
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return false;
	}
}
