package de.nedelosk.forestmods.common.multiblocks.cowper;

import java.util.HashSet;
import java.util.Set;

import de.nedelosk.forestmods.common.blocks.BlockCowper;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperAccessPort;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperBase;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperFluidPort;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperFluidPort.PortType;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.core.FluidManager;
import de.nedelosk.forestmods.library.ForestModsApi;
import de.nedelosk.forestmods.library.fluids.FluidTankSimple;
import de.nedelosk.forestmods.library.fluids.TankManager;
import de.nedelosk.forestmods.library.inventory.InventoryAdapter;
import de.nedelosk.forestmods.library.multiblock.IMultiblockPart;
import de.nedelosk.forestmods.library.multiblock.MultiblockControllerBase;
import de.nedelosk.forestmods.library.multiblock.MultiblockValidationException;
import de.nedelosk.forestmods.library.multiblock.RectangularMultiblockControllerBase;
import de.nedelosk.forestmods.library.utils.Log;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
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
	private Set<TileCowperBase> attachedAirInput;
	public FluidStack output;
	public int heat;
	public static final int heatMax = Config.cowperMaxHeat;
	public int fuelBurnTime;
	public int burnTime;
	public int burnTimeTotal;
	private boolean isActive;

	public MultiblockCowper(World world) {
		super(world);
		attachedAccessPorts = new HashSet<TileCowperAccessPort>();
		attachedFluidPortsInput = new HashSet<TileCowperFluidPort>();
		attachedFluidPortsFuel = new HashSet<TileCowperFluidPort>();
		attachedFluidPortsOutput = new HashSet<TileCowperFluidPort>();
		attachedFluidPortsSteam = new HashSet<TileCowperFluidPort>();
		attachedMuffler = new HashSet<TileCowperBase>();
		attachedAirInput = new HashSet<TileCowperBase>();
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		readFromNBT(data);
	}

	@Override
	protected void isMachineWhole() throws MultiblockValidationException {
		super.isMachineWhole();
		if (attachedFluidPortsInput.size() < 1) {
			if(attachedAirInput.size() < 1){
				throw new MultiblockValidationException("Not enough inputs. Cowper require a air input or a fluid input bus.");
			}
		}else{
			if(attachedAirInput.size() >= 1){
				throw new MultiblockValidationException("To many inputs. Cowper accepte only a air input or a fluid input port, not both.");
			}
		}

		if (attachedFluidPortsOutput.size() < 1) {
			throw new MultiblockValidationException("Not enough output ports. Cowper require at least 1.");
		}

		if (attachedFluidPortsSteam.size() < 1) {
			if(attachedMuffler.size() < 1){
				throw new MultiblockValidationException("Not enough steam output. Cowper require a steam output port or a muffler.");
			}
		}else{
			if(attachedMuffler.size() >= 1){
				throw new MultiblockValidationException("To many inputs. Cowper accepte only a steam output port or a muffler, not both.");
			}
		}
		if (attachedAccessPorts.size() < 1) {
			if(attachedFluidPortsFuel.size() < 1){
				throw new MultiblockValidationException("Not enough fuel input. Cowper require a fuel access port or a fluid fuel port.");
			}
		}else{
			if(attachedFluidPortsFuel.size() >= 1){
				throw new MultiblockValidationException("To many fuel input. Cowper accepte only a fuel access port or a fluid fuel port, not both.");
			}
		}
	}

	@Override
	protected void isBlockGoodForInterior(World world, int x, int y, int z) throws MultiblockValidationException {
		if (!world.isAirBlock(x, y, z)) {
			super.isBlockGoodForInterior(world, x, y, z);
		}
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
		} else if (part instanceof TileCowperBase && BlockCowper.isAirInput(part.getBlockMetadata())) {
			attachedAirInput.add((TileCowperBase) part);
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
		} else if (part instanceof TileCowperBase && BlockCowper.isAirInput(part.getBlockMetadata())) {
			attachedAirInput.remove(part);
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
	}

	@Override
	public boolean updateWhenDisassembled() {
		return true;
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
		return 5;
	}

	@Override
	protected int getMinimumZSize() {
		return 3;
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
		if(this.assemblyState == AssemblyState.Disassembled){
			if(updateOnInterval(30 + (connectedParts.size() - getMinimumNumberOfBlocksForAssembledMachine() / 4))){
				heat--;
			}
		}else{
			ItemStack fuelItem = inventory.getStackInSlot(0);
			FluidStack fuelFluid = tankManager.getTank(1).getFluid();
			if(fuelBurnTime > 0){
				fuelBurnTime--;
				if(heat < heatMax){
					if(updateOnInterval(30 + (connectedParts.size() - getMinimumNumberOfBlocksForAssembledMachine() / 4))){
						heat++;
					}
				}
			}else{
				if(fuelItem != null){
					if(TileEntityFurnace.getItemBurnTime(fuelItem) > 0){
						fuelBurnTime = TileEntityFurnace.getItemBurnTime(fuelItem);
						inventory.decrStackSize(0, 1);
					}
				}else if(fuelFluid != null){
					if(ForestModsApi.fluidFuel.containsKey(fuelFluid.getFluid())){
						FluidStack stack = tankManager.drain(ForgeDirection.UNKNOWN, new FluidStack(fuelFluid.getFluid(), 100), false);
						if(stack != null && stack.amount == 100){
							fuelBurnTime = ForestModsApi.fluidFuel.get(fuelFluid.getFluid());
							tankManager.getTank(1).drain(new FluidStack(fuelFluid.getFluid(), 100), true);
						}
					}
				}else{
					if(heat > 0){
						if(updateOnInterval(40 + (connectedParts.size() - getMinimumNumberOfBlocksForAssembledMachine() / 4))){
							heat--;
						}
					}else if(heat < 0){
						heat=0;
					}
				}
			}
			FluidStack stack = tankManager.getTank(2).getFluid();
			NBTTagCompound heatTag = new NBTTagCompound();
			heatTag.setInteger("Heat", heat);
			if(stack != null && stack.amount > 0){
				stack.tag = heatTag;
			}
			if(heat > 0){
				/*if(attachedFluidPortsInput.size() > 0){
					FluidStack inputFluid = tankManager.getTank(0).getFluid();
					if(inputFluid != null && inputFluid.amount > 0){

					}else{

					}
				}else*/ if(attachedAirInput.size() > 0){
					if(heat >= 150){
						if(tankManager.getTank(2).fill(new FluidStack(FluidManager.Air_Hot, 50, heatTag), false) == 50){
							if(heat > 0){
								heat-=2;
							}else if(heat < 0){
								heat=0;
							}
							tankManager.getTank(2).fill(new FluidStack(FluidManager.Air_Hot, 50, heatTag), true);
							markReferenceCoordForUpdate();
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	protected void updateClient() {
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		data.setInteger("Heat", heat);
		data.setInteger("FuelBurnTime", fuelBurnTime);
		data.setInteger("BurnTime", burnTime);
		data.setInteger("BurnTimeTotal", burnTimeTotal);
		data.setBoolean("IsActive", isActive);
		NBTTagCompound nbtTank = new NBTTagCompound();
		tankManager.writeToNBT(nbtTank);
		data.setTag("Tank", nbtTank);
		NBTTagCompound nbtInv = new NBTTagCompound();
		inventory.writeToNBT(nbtInv);
		data.setTag("Inv", nbtInv);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		heat = data.getInteger("Heat");
		fuelBurnTime = data.getInteger("FuelBurnTime");
		burnTime = data.getInteger("BurnTime");
		burnTimeTotal = data.getInteger("BurnTimeTotal");
		isActive = data.getBoolean("IsActive");
		NBTTagCompound nbtTank = data.getCompoundTag("Tank");
		tankManager.readFromNBT(nbtTank);
		NBTTagCompound nbtInv = data.getCompoundTag("Inv");
		inventory.readFromNBT(nbtInv);
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
