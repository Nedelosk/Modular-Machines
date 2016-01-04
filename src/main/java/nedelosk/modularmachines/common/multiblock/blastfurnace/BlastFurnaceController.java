package nedelosk.modularmachines.common.multiblock.blastfurnace;

import java.util.HashSet;
import java.util.Set;

import nedelosk.forestcore.library.Log;
import nedelosk.forestcore.library.fluids.FluidTankFilter;
import nedelosk.forestcore.library.fluids.FluidTankSimple;
import nedelosk.forestcore.library.fluids.TankManager;
import nedelosk.forestcore.library.inventory.InventoryAdapter;
import nedelosk.forestcore.library.multiblock.IMultiblockPart;
import nedelosk.forestcore.library.multiblock.MultiblockControllerBase;
import nedelosk.forestcore.library.multiblock.MultiblockValidationException;
import nedelosk.forestcore.library.multiblock.rectangular.RectangularMultiblockControllerBase;
import nedelosk.forestday.api.crafting.OreStack;
import nedelosk.modularmachines.common.config.Config;
import nedelosk.modularmachines.common.crafting.BlastFurnaceRecipe;
import nedelosk.modularmachines.common.crafting.BlastFurnaceRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlastFurnaceController extends RectangularMultiblockControllerBase {

	private InventoryAdapter inventory = new InventoryAdapter(4, "BlastFurnace", 16);
	private TankManager tankManager = new TankManager(new FluidTankFilter(16000, "air.hot"),
			new FluidTankFilter(16000, "slag"), new FluidTankSimple(16000),
			new FluidTankFilter(16000, "gas.blastfurnace"));

	private Set<TileBlastFurnaceAccessPort> attachedAccessPorts;
	private Set<TileBlastFurnaceFluidPort> attachedFluidPorts;
	private int heat;
	private int heatTotal;
	private int burnTime;
	private int burnTimeTotal;
	private int heatMax = Config.bastFurnaceMaxHeat;
	private FluidStack[] outputs;

	public BlastFurnaceController(World world) {
		super(world);

		attachedAccessPorts = new HashSet<TileBlastFurnaceAccessPort>();
		attachedFluidPorts = new HashSet<TileBlastFurnaceFluidPort>();
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		readFromNBT(data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart part) {
		if (part instanceof TileBlastFurnaceAccessPort) {
			attachedAccessPorts.add((TileBlastFurnaceAccessPort) part);
		} else if (part instanceof TileBlastFurnaceFluidPort) {
			attachedFluidPorts.add((TileBlastFurnaceFluidPort) part);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart part) {
		if (part instanceof TileBlastFurnaceAccessPort) {
			attachedAccessPorts.remove(part);
		} else if (part instanceof TileBlastFurnaceFluidPort) {
			attachedFluidPorts.remove(part);
		}
	}

	@Override
	protected void isMachineWhole() throws MultiblockValidationException {
		// Ensure that there is at least one controller and control rod
		// attached.
		if (attachedFluidPorts.size() < 3) {
			throw new MultiblockValidationException("Not enough fluid ports. Blast Furnace require at least 3.");
		}

		if (attachedAccessPorts.size() < 1) {
			throw new MultiblockValidationException("Not enough access ports. Blast Furnace require at least 1.");
		}

		super.isMachineWhole();
	}

	@Override
	protected void onMachineAssembled() {
	}

	@Override
	protected void onMachineDisassembled() {
	}

	@Override
	protected void onMachineRestored() {
	}

	@Override
	protected void onMachinePaused() {
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 42;
	}

	@Override
	protected int getMaximumXSize() {
		return 5;
	}

	@Override
	protected int getMaximumZSize() {
		return 5;
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
	protected int getMinimumZSize() {
		return 3;
	}

	@Override
	protected int getMinimumYSize() {
		return 5;
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase otherMachine) {
		if (!(otherMachine instanceof BlastFurnaceController)) {
			Log.warn(
					"[%s] Blast Furnace @ %s is attempting to assimilate a non-Reactor machine! That machine's data will be lost!",
					worldObj.isRemote ? "CLIENT" : "SERVER", getReferenceCoord());
			return;
		}
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		this.attachedAccessPorts.clear();
		this.attachedFluidPorts.clear();
	}

	@Override
	protected boolean updateServer() {
		if (heat >= heatTotal || heatTotal == 0) {
			if (burnTime >= burnTimeTotal || burnTimeTotal == 0) {
				if (outputs != null) {
					if (outputs[0] != null)
						if (getTankManager().getTank(1).fill(outputs[0], true) >= outputs[0].amount)
							outputs[0] = null;
					if (outputs[1] != null)
						if (getTankManager().getTank(2).fill(outputs[1], true) >= outputs[1].amount)
							outputs[1] = null;
					if (outputs[0] == null && outputs[1] == null)
						outputs = null;
				} else {
					ItemStack[] inputs = new ItemStack[4];
					for (int i = 0; i < inputs.length; i++)
						inputs[i] = getInventory().getStackInSlot(i);
					if (inputs[0] != null) {
						BlastFurnaceRecipe recipe = BlastFurnaceRecipeManager.getRecipe(inputs);
						if (recipe != null) {
							for (int i = 0; i < recipe.getInput().length; i++) {
								if (recipe.getInput()[i] instanceof ItemStack)
									getInventory().decrStackSize(i, ((ItemStack) recipe.getInput()[i]).stackSize);
								if (recipe.getInput()[i] instanceof OreStack)
									getInventory().decrStackSize(i, ((OreStack) recipe.getInput()[i]).stackSize);
							}
							outputs = recipe.getOutput().clone();
							burnTimeTotal = recipe.getBurntTime();
							heatTotal = recipe.getHeat();
							return true;
						}
					}
				}
			} else {
				burnTime++;
				heat--;
			}
		} else {
			if (heat < heatMax)
				if (worldObj.rand.nextInt(40) == 20)
					if (getTankManager().getTank(0) != null && getTankManager().getTank(0).drain(50, true) != null
							&& getTankManager().getTank(0).drain(50, true).amount >= 50)
						if (getTankManager().getTank(3)
								.fill(new FluidStack(FluidRegistry.getFluid("gas.blastfurnace"), 150), true) >= 150) {
							heat += 15;
							return true;
						}
		}
		return false;
	}

	@Override
	protected void updateClient() {
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		NBTTagCompound nbtTag = new NBTTagCompound();
		tankManager.writeToNBT(nbtTag);
		data.setTag("TankManager", nbtTag);
		inventory.writeToNBT(data);

		data.setInteger("BurnTime", burnTime);
		data.setInteger("BurnTimeTotal", burnTimeTotal);
		data.setInteger("Heat", heat);
		data.setInteger("HeatTotal", heatTotal);

		if (outputs != null) {
			if (outputs[0] == null) {
				NBTTagCompound nbtTagOutput = new NBTTagCompound();
				outputs[0].writeToNBT(nbtTagOutput);
				data.setTag("Output1", nbtTagOutput);
			}
			if (outputs[1] == null) {
				NBTTagCompound nbtTagOutput = new NBTTagCompound();
				outputs[1].writeToNBT(nbtTagOutput);
				data.setTag("Output2", nbtTagOutput);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		NBTTagCompound nbtTank = data.getCompoundTag("TankManager");
		tankManager = new TankManager(nbtTank);
		inventory = new InventoryAdapter(4, "BlastFurnace", 16);
		inventory.readFromNBT(data);

		burnTime = data.getInteger("BurnTime");
		burnTimeTotal = data.getInteger("BurnTimeTotal");
		heat = data.getInteger("Heat");
		heatTotal = data.getInteger("HeatTotal");

		if (data.hasKey("Output2")) {
			outputs = new FluidStack[2];
		} else if (data.hasKey("Output1")) {
			outputs = new FluidStack[1];
		}
		if (data.hasKey("Output1")) {
			NBTTagCompound nbtTag = data.getCompoundTag("Output1");
			outputs[0] = FluidStack.loadFluidStackFromNBT(nbtTag);
		}
		if (data.hasKey("Output2")) {
			NBTTagCompound nbtTag = data.getCompoundTag("Output2");
			outputs[1] = FluidStack.loadFluidStackFromNBT(nbtTag);
		}
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

}
