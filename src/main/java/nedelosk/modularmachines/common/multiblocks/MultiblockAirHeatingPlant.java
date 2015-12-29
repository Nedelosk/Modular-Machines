package nedelosk.modularmachines.common.multiblocks;

import nedelosk.forestcore.library.FluidTankBasic;
import nedelosk.forestcore.library.utils.NBTUtil;
import nedelosk.forestday.api.multiblocks.ITileMultiblock;
import nedelosk.forestday.api.multiblocks.MultiblockPattern;
import nedelosk.forestday.api.multiblocks.MultiblockModifierValveType.ValveType;
import nedelosk.forestday.common.modules.ModuleCore;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.client.gui.multiblocks.GuiAirHeatingPlant;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.crafting.AirHeatingPlantRecipe;
import nedelosk.modularmachines.common.crafting.AirHeatingPlantRecipeManager;
import nedelosk.modularmachines.common.inventory.multiblock.ContainerAirHeatingPlant;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class MultiblockAirHeatingPlant extends MultiblockModularMachines {

	public FluidTankBasic tank;
	public FluidTankBasic tankInput;
	public FluidTankBasic tankGas;
	public FluidStack output;
	public int heat;
	public int heatTotal = 750;

	public MultiblockAirHeatingPlant() {
		super();
	}

	@Override
	public String getMultiblockName() {
		return "airheatingplant";
	}

	@Override
	public MultiblockPattern createPattern() {
		char[][] layer_1 = { { 'B', 'B', 'B' }, { 'B', 'J', 'B' }, { 'B', 'B', 'B' } };
		char[][] layer_2 = { { 'B', 'F', 'B' }, { 'F', 'H', 'F' }, { 'B', 'F', 'B' } };
		char[][] layer_3 = { { 'B', 'V', 'B' }, { 'V', 'H', 'V' }, { 'B', 'V', 'B' } };

		char[][] layer = { { 'B', 'B', 'B' }, { 'B', 'H', 'B' }, { 'B', 'B', 'B' } };
		char[][] layer_4 = { { 'B', 'B', 'B' }, { 'B', 'N', 'B' }, { 'B', 'B', 'B' } };
		return new MultiblockPattern(new char[][][] { layer_1, layer_2, layer, layer_3, layer, layer, layer_4 }, 1, 0,
				1);
	}

	@Override
	public boolean testBlock() {
		return false;
	}

	@Override
	public boolean isPatternBlockValid(int x, int y, int z, char pattern, ITileMultiblock tile2) {
		TileMultiblockBase base = (TileMultiblockBase) tile2;
		Block block = base.getWorldObj().getBlock(x, y, z);
		TileEntity tile = base.getWorldObj().getTileEntity(x, y, z);
		TileMultiblockBase multiblock = null;
		if (tile instanceof TileMultiblockBase)
			multiblock = (TileMultiblockBase) tile;
		switch (pattern) {
		case 'B': {
			if (block != ModuleCore.BlockManager.Multiblock.block()) {
				return false;
			}
			break;
		}
		case 'J': {
			if (block != ModuleCore.BlockManager.Multiblock.block()) {
				return false;
			}
			break;
		}
		case 'F': {
			if (block != ModuleCore.BlockManager.Multiblock_Valve.block() && block != ModuleCore.BlockManager.Multiblock.block()
					|| multiblock == null) {
				return false;
			} else if (block == ModuleCore.BlockManager.Multiblock_Valve.block()) {
				multiblock.modifier.filter = "fluid";
				multiblock.modifier.valveType = ValveType.OUTPUT;
			}
			break;
		}
		case 'V': {
			if (block != ModuleCore.BlockManager.Multiblock_Valve.block() && block != ModuleCore.BlockManager.Multiblock.block()
					|| multiblock == null) {
				return false;
			} else if (block == ModuleCore.BlockManager.Multiblock_Valve.block()) {
				multiblock.modifier.filter = "gas";
				multiblock.modifier.valveType = ValveType.OUTPUT;
			}
			break;
		}
		case 'N': {
			if (block != ModuleCore.BlockManager.Multiblock_Valve.block() && block != ModuleCore.BlockManager.Multiblock.block()
					|| multiblock == null) {
				return false;
			} else if (block == ModuleCore.BlockManager.Multiblock_Valve.block()) {
				multiblock.modifier.valveType = ValveType.INPUT;
			}
			break;
		}
		case 'H': {
			if (!base.getWorldObj().isAirBlock(x, y, z)) {
				return false;
			}
			break;
		}
		case 'O': {
			if (block == ModuleCore.BlockManager.Multiblock_Valve.block() || block == ModuleCore.BlockManager.Multiblock.block()
					|| tile instanceof TileMultiblockBase) {
				return false;
			}
			break;
		}
		}
		if (tile != null)
			base.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
		return true;
	}

	@Override
	public void onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side) {
		player.openGui(ModularMachines.instance, 0, world, x, y, z);
	}

	@Override
	public Container getContainer(ITileMultiblock tile, InventoryPlayer inventory) {
		return new ContainerAirHeatingPlant((TileMultiblockBase<MultiblockAirHeatingPlant>) tile, inventory);
	}

	@Override
	public Object getGUIContainer(ITileMultiblock tile, InventoryPlayer inventory) {
		return new GuiAirHeatingPlant((TileMultiblockBase) tile, inventory);
	}

	@Override
	public void updateClient(ITileMultiblock tile) {

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTUtil.writeTankToNBT("TankGas", nbt, tankGas);
		NBTUtil.writeTankToNBT("Tank", nbt, tank);
		NBTUtil.writeTankToNBT("TankInput", nbt, tankInput);
		if (output != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			output.writeToNBT(nbtTag);
			nbt.setTag("Output", nbtTag);
		}
		nbt.setInteger("Heat", heat);
		nbt.setInteger("HeatTotal", heatTotal);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("TankGas")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("TankGas");
			tankGas = new FluidTankBasic(nbtTag.getInteger("Capacity"));
			tankGas.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("Tank")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Tank");
			tank = new FluidTankBasic(nbtTag.getInteger("Capacity"));
			tank.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("TankInput")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("TankInput");
			tankInput = new FluidTankBasic(nbtTag.getInteger("Capacity"));
			tankInput.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("Output")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Output");
			output = FluidStack.loadFluidStackFromNBT(nbtTag);
		}

		heat = nbt.getInteger("Heat");
		heatTotal = nbt.getInteger("HeatTotal");
	}

	@Override
	public void updateServer(ITileMultiblock tile) {
		TileMultiblockBase base = (TileMultiblockBase) tile;
		if (tankGas == null)
			tankGas = new FluidTankBasic(32000);
		if (tank == null)
			tank = new FluidTankBasic(32000);
		if (tankInput == null)
			tankInput = new FluidTankBasic(32000);
		if (heat >= heatTotal || heatTotal == 0) {
			if (base.burnTime >= base.burnTimeTotal || base.burnTimeTotal == 0) {
				FluidStack input = tankInput.getFluid();
				if (output != null) {
					if (tank.fill(output, true) >= output.amount)
						output = null;
				} else if (input != null) {
					AirHeatingPlantRecipe recipe = AirHeatingPlantRecipeManager.getRecipe(input);
					if (recipe != null) {
						tankInput.drain(recipe.getInput(), true);
						output = recipe.getOutput().copy();
						base.burnTimeTotal = recipe.getBurntTime();
					}
				}
			} else {
				base.burnTime++;
			}
		} else {
			if (tankGas.getFluid() != null && tankGas.getFluid().amount > 0) {

				int heat = tankGas.getFluid().getFluid().getTemperature()
						/ ((heatTotal / tankGas.getFluid().getFluid().getTemperature()) / 30);
				if (tankGas.drain(30, true) != null & tankGas.drain(30, true).amount >= 30)
					heat += heat;
			}
		}
		base.getWorldObj().markBlockForUpdate(base.xCoord, base.yCoord, base.zCoord);
	}

	@Override
	public int fill(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doFill) {
		if (tile.isMultiblock) {
			if (tile.modifier.valveType == ValveType.INPUT) {
				if (tile.modifier.filter == "fluid") {
					if (AirHeatingPlantRecipeManager.isFluidInput(resource))
						return tankInput.fill(resource, doFill);
				} else if (tile.modifier.filter == "gas") {
					if (ModularMachinesApi.airHeatingPlantGas.contains(resource.getFluid()))
						return tankGas.fill(resource, doFill);
				}
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (tile.isMultiblock) {
			if (tile.modifier.valveType == ValveType.OUTPUT) {
				return tank.drain(resource, doDrain);
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(TileMultiblockBase tile, ForgeDirection from, int maxDrain, boolean doDrain) {
		if (tile.isMultiblock) {
			if (tile.modifier.valveType == ValveType.OUTPUT) {
				return tank.drain(maxDrain, doDrain);
			}
		}
		return null;
	}

	@Override
	public boolean canFill(TileMultiblockBase tile, ForgeDirection from, Fluid fluid) {
		return tile.isMultiblock && tile.modifier.valveType == ValveType.INPUT;
	}

	@Override
	public boolean canDrain(TileMultiblockBase tile, ForgeDirection from, Fluid fluid) {
		return tile.isMultiblock && tile.modifier.valveType == ValveType.OUTPUT;
	}

	@Override
	public FluidTankInfo[] getTankInfo(TileMultiblockBase tile, ForgeDirection from) {
		if (tile.isMultiblock)
			return new FluidTankInfo[] { tankGas.getInfo(), tank.getInfo(), tankInput.getInfo() };
		return null;
	}
}
