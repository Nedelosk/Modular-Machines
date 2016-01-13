package nedelosk.modularmachines.common.multiblock.cowper;

import java.util.HashSet;
import java.util.Set;

import nedelosk.forestcore.library.Log;
import nedelosk.forestcore.library.fluids.FluidTankSimple;
import nedelosk.forestcore.library.fluids.TankManager;
import nedelosk.forestcore.library.inventory.InventoryAdapter;
import nedelosk.forestcore.library.multiblock.IMultiblockPart;
import nedelosk.forestcore.library.multiblock.MultiblockControllerBase;
import nedelosk.forestcore.library.multiblock.MultiblockValidationException;
import nedelosk.forestcore.library.multiblock.RectangularMultiblockControllerBase;
import nedelosk.modularmachines.common.config.Config;
import nedelosk.modularmachines.common.multiblock.cowper.TileCowperFluidPort.PortType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class MultiblockCowper extends RectangularMultiblockControllerBase {

	private InventoryAdapter inventory = new InventoryAdapter(1, "Cowper", 64);
	private TankManager tankManager = new TankManager(new FluidTankSimple(16000), // Input,
			new FluidTankSimple(16000), // Fuel
			new FluidTankSimple(16000), // Output
			new FluidTankSimple(16000)); // Steam
	private Set<TileCowperAccessPort> attachedAccessPorts;
	private Set<TileCowperFluidPort> attachedFluidPortsInput;
	private Set<TileCowperFluidPort> attachedFluidPortsFuel;
	private Set<TileCowperFluidPort> attachedFluidPortsOutput;
	private Set<TileCowperFluidPort> attachedFluidPortsSteam;
	private Set<TileCowperBase> attachedMuffler;
	public FluidStack output;
	public int heat;
	public int heatTotal = Config.airHeatingPlantMaxHeat;
	private boolean isActive;

	public MultiblockCowper(World world) {
		super(world);
		attachedAccessPorts = new HashSet<TileCowperAccessPort>();
		attachedFluidPortsInput = new HashSet<TileCowperFluidPort>();
		attachedFluidPortsFuel = new HashSet<TileCowperFluidPort>();
		attachedFluidPortsOutput = new HashSet<TileCowperFluidPort>();
		attachedFluidPortsSteam = new HashSet<TileCowperFluidPort>();
		attachedMuffler = new HashSet<TileCowperBase>();
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		readFromNBT(data);
	}

	@Override
	protected void isMachineWhole() throws MultiblockValidationException {
		if (attachedFluidPortsInput.size() < 1) {
			throw new MultiblockValidationException("Not enough input fluid ports. Cowper require at least 1.");
		}
		if (attachedFluidPortsFuel.size() < 1) {
			throw new MultiblockValidationException("Not enough fluid fuel ports. Cowper require at least 1.");
		}
		if (attachedFluidPortsOutput.size() < 1) {
			throw new MultiblockValidationException("Not enough output ports. Cowper require at least 1.");
		}
		if (attachedFluidPortsSteam.size() < 1 && attachedMuffler.size() < 1) {
			throw new MultiblockValidationException("Not steam output. Cowper require at least 1.");
		}
		if (attachedAccessPorts.size() < 1) {
			throw new MultiblockValidationException("Not enough fuel ports. Cowper require at least 1.");
		}
		super.isMachineWhole();
	}

	@Override
	protected void onBlockAdded(IMultiblockPart part) {
		if (part instanceof TileCowperAccessPort) {
			attachedAccessPorts.add((TileCowperAccessPort) part);
		} else if (part instanceof TileCowperFluidPort) {
			TileCowperFluidPort port = (TileCowperFluidPort) part;
			if (port.getType() == PortType.INPUT) {
				attachedFluidPortsInput.add((TileCowperFluidPort) part);
			} else if (port.getType() == PortType.FUEL) {
				attachedFluidPortsFuel.add((TileCowperFluidPort) part);
			} else if (port.getType() == PortType.OUTPUT) {
				attachedFluidPortsOutput.add((TileCowperFluidPort) part);
			} else if (port.getType() == PortType.STEAM) {
				attachedFluidPortsSteam.add((TileCowperFluidPort) part);
			}
		} else if (part instanceof TileCowperBase && BlockCowper.isMuffler(part.getBlockMetadata())) {
			attachedMuffler.add((TileCowperBase) part);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart part) {
		if (part instanceof TileCowperAccessPort) {
			attachedAccessPorts.remove(part);
		} else if (part instanceof TileCowperFluidPort) {
			TileCowperFluidPort port = (TileCowperFluidPort) part;
			if (port.getType() == PortType.INPUT) {
				attachedFluidPortsInput.remove(part);
			} else if (port.getType() == PortType.FUEL) {
				attachedFluidPortsFuel.remove(part);
			} else if (port.getType() == PortType.OUTPUT) {
				attachedFluidPortsOutput.remove(part);
			} else if (port.getType() == PortType.STEAM) {
				attachedFluidPortsSteam.remove(part);
			}
		} else if (part instanceof TileCowperBase && BlockCowper.isMuffler(part.getBlockMetadata())) {
			attachedMuffler.remove(part);
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
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 34;
	}

	@Override
	protected int getMinimumXSize() {
		return 3;
	}

	@Override
	protected int getMinimumYSize() {
		return 4;
	}

	@Override
	protected int getMinimumZSize() {
		return 3;
	}

	@Override
	protected int getMaximumXSize() {
		return 3;
	}

	@Override
	protected int getMaximumZSize() {
		return 3;
	}

	@Override
	protected int getMaximumYSize() {
		return 6;
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase otherMachine) {
		if (!(otherMachine instanceof MultiblockCowper)) {
			Log.warn("[%s] Cowper @ %s is attempting to assimilate a non-Cowper machine! That machine's data will be lost!",
					worldObj.isRemote ? "CLIENT" : "SERVER", getReferenceCoord());
			return;
		}
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		this.attachedAccessPorts.clear();
		this.attachedFluidPortsInput.clear();
		this.attachedFluidPortsFuel.clear();
		this.attachedFluidPortsOutput.clear();
		this.attachedFluidPortsSteam.clear();
	}

	@Override
	protected boolean updateServer() {
		return false;
	}

	@Override
	protected void updateClient() {
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
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

	@Override
	public boolean isActive() {
		return isActive;
	}
}
