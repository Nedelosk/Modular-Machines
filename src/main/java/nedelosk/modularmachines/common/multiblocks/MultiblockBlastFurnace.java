package nedelosk.modularmachines.common.multiblocks;

import nedelosk.forestcore.library.fluids.FluidTankSimple;
import nedelosk.forestcore.library.utils.NBTUtil;
import nedelosk.forestday.api.crafting.OreStack;
import nedelosk.forestday.api.multiblocks.ITileMultiblock;
import nedelosk.forestday.api.multiblocks.MultiblockModifierValveTypeString;
import nedelosk.forestday.api.multiblocks.MultiblockPattern;
import nedelosk.forestday.api.multiblocks.MultiblockModifierValveType.ValveType;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.forestday.modules.ModuleCore;
import nedelosk.modularmachines.client.gui.multiblock.GuiBlastFurnaceFluidPort;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.crafting.BlastFurnaceRecipe;
import nedelosk.modularmachines.common.crafting.BlastFurnaceRecipeManager;
import nedelosk.modularmachines.common.inventory.multiblocks.ContainerBlastFurnace;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class MultiblockBlastFurnace extends MultiblockModularMachines {

	public FluidTankSimple tankSlag;
	public FluidTankSimple tank;
	public FluidTankSimple tankAirHot;
	public FluidTankSimple tankGas;
	public FluidStack[] outputs;
	public int heat;
	public int heatTotal;

	public MultiblockBlastFurnace() {
		super();
	}

	@Override
	public String getMultiblockName() {
		return "blastfurnace";
	}

	@Override
	public MultiblockPattern createPattern() {
		char[][] bottom_layer_1 = { { 'B', 'B', 'B', 'B', 'B' }, { 'B', 'B', 'B', 'B', 'B' },
				{ 'B', 'B', 'J', 'B', 'B' }, { 'B', 'B', 'B', 'B', 'B' }, { 'B', 'B', 'B', 'B', 'B' } };
		char[][] bottom_layer_2 = { { 'B', 'V', 'V', 'V', 'B' }, { 'V', 'H', 'H', 'H', 'V' },
				{ 'V', 'H', 'H', 'H', 'V' }, { 'V', 'H', 'H', 'H', 'V' }, { 'B', 'V', 'V', 'V', 'B' } };
		char[][] bottom_layer_3 = { { 'B', 'S', 'S', 'S', 'B' }, { 'S', 'H', 'H', 'H', 'S' },
				{ 'S', 'H', 'H', 'H', 'S' }, { 'S', 'H', 'H', 'H', 'S' }, { 'B', 'S', 'S', 'S', 'B' } };
		char[][] center_layer_1 = { { 'M', 'M', 'M', 'M', 'M' }, { 'M', 'H', 'H', 'H', 'M' },
				{ 'M', 'H', 'H', 'H', 'M' }, { 'M', 'H', 'H', 'H', 'M' }, { 'M', 'M', 'M', 'M', 'M' } };
		char[][] center_layer_2 = { { 'M', 'M', 'F', 'M', 'M' }, { 'M', 'H', 'H', 'H', 'M' },
				{ 'F', 'H', 'H', 'H', 'F' }, { 'M', 'H', 'H', 'H', 'M' }, { 'M', 'M', 'F', 'M', 'M' } };
		char[][] top_layer_1 = { { 'M', 'M', 'F', 'M', 'M' }, { 'M', 'C', 'C', 'C', 'M' }, { 'M', 'C', 'I', 'C', 'M' },
				{ 'M', 'C', 'C', 'C', 'M' }, { 'M', 'M', 'F', 'M', 'M' } };
		return new MultiblockPattern(new char[][][] { bottom_layer_1, bottom_layer_2, bottom_layer_3, center_layer_1,
				center_layer_2, center_layer_1, center_layer_1, center_layer_1, top_layer_1 }, 2, 0, 2);
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
			if (block != ModuleCore.BlockManager.Multiblock.block() || multiblock == null) {
				return false;
			}
			break;
		}
		case 'J': {
			if (block != ModuleCore.BlockManager.Multiblock.block() || multiblock == null) {
				return false;
			}
			break;
		}
		case 'V': {
			if (block != ModuleCore.BlockManager.Multiblock_Valve.block()
					&& block != ModuleCore.BlockManager.Multiblock.block() || multiblock == null) {
				return false;
			} else if (block == ModuleCore.BlockManager.Multiblock_Valve.block()) {
				multiblock.modifier.filter = "output";
				multiblock.modifier.valveType = ValveType.OUTPUT;
			}
			break;
		}
		case 'S': {
			if (block != ModuleCore.BlockManager.Multiblock_Valve.block()
					&& block != ModuleCore.BlockManager.Multiblock.block() || multiblock == null) {
				return false;
			} else if (block == ModuleCore.BlockManager.Multiblock_Valve.block()) {
				multiblock.modifier.filter = "slag";
				multiblock.modifier.valveType = ValveType.OUTPUT;
			}
			break;
		}
		case 'H': {
			if (!base.getWorldObj().isAirBlock(x, y, z)) {
				return false;
			}
			break;
		}
		case 'C': {
			if (block != ModuleCore.BlockManager.Multiblock_Valve.block()
					&& block != ModuleCore.BlockManager.Multiblock.block() || multiblock == null) {
				return false;
			} else if (block == ModuleCore.BlockManager.Multiblock_Valve.block()) {
				multiblock.modifier.filter = "gas.blastfurnace";
				multiblock.modifier.valveType = ValveType.OUTPUT;
			}
			break;
		}
		case 'F': {
			if (block != ModuleCore.BlockManager.Multiblock_Valve.block()
					&& block != ModuleCore.BlockManager.Multiblock.block() || multiblock == null) {
				return false;
			} else if (block == ModuleCore.BlockManager.Multiblock_Valve.block()) {
				multiblock.modifier.filter = "air.hot";
				multiblock.modifier.valveType = ValveType.INPUT;
			}
			break;
		}
		case 'I': {
			if (block != ModuleCore.BlockManager.Multiblock_Valve.block()
					&& block != ModuleCore.BlockManager.Multiblock.block() || multiblock == null) {
				return false;
			} else if (block == ModuleCore.BlockManager.Multiblock_Valve.block()) {
				multiblock.modifier.valveType = ValveType.INPUT;
			}
			break;
		}
		case 'M': {
			if (block != ModuleCore.BlockManager.Multiblock.block() && multiblock == null) {
				return false;
			}
			break;
		}
		case 'O': {
			if (block == ModuleCore.BlockManager.Multiblock.block()
					|| block == ModuleCore.BlockManager.Multiblock_Valve.block()
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
		return new ContainerBlastFurnace((TileMultiblockBase<MultiblockBlastFurnace>) tile, inventory);
	}

	@Override
	public Object getGUIContainer(ITileMultiblock tile, InventoryPlayer inventory) {
		return new GuiBlastFurnaceFluidPort((TileMultiblockBase) tile, inventory);
	}

	@Override
	public void updateClient(ITileMultiblock tile) {

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {

		NBTUtil.writeTankToNBT("TankGas", nbt, tankGas);
		NBTUtil.writeTankToNBT("TankAirHot", nbt, tankAirHot);
		NBTUtil.writeTankToNBT("Tank", nbt, tank);
		NBTUtil.writeTankToNBT("TankSlag", nbt, tankSlag);

		if (outputs != null) {
			if (outputs[0] == null) {
				NBTTagCompound nbtTagOutput = new NBTTagCompound();
				outputs[0].writeToNBT(nbtTagOutput);
				nbt.setTag("Output1", nbtTagOutput);
			}
			if (outputs[1] == null) {
				NBTTagCompound nbtTagOutput = new NBTTagCompound();
				outputs[1].writeToNBT(nbtTagOutput);
				nbt.setTag("Output2", nbtTagOutput);
			}
		}
		nbt.setInteger("Heat", heat);
		nbt.setInteger("HeatTotal", heatTotal);

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("TankGas")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("TankGas");
			tankGas = new FluidTankSimple(nbtTag.getInteger("Capacity"));
			tankGas.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("TankAirHot")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("TankAirHot");
			tankAirHot = new FluidTankSimple(nbtTag.getInteger("Capacity"));
			tankAirHot.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("Tank")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Tank");
			tank = new FluidTankSimple(nbtTag.getInteger("Capacity"));
			tank.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("TankSlag")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("TankSlag");
			tankSlag = new FluidTankSimple(nbtTag.getInteger("Capacity"));
			tankSlag.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("Output1"))
			outputs = new FluidStack[1];
		if (nbt.hasKey("Output2"))
			outputs = new FluidStack[2];
		if (nbt.hasKey("Output1")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Output1");
			outputs[0] = FluidStack.loadFluidStackFromNBT(nbtTag);
		}
		if (nbt.hasKey("Output2")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Output2");
			outputs[1] = FluidStack.loadFluidStackFromNBT(nbtTag);
		}
		heat = nbt.getInteger("Heat");
		heatTotal = nbt.getInteger("HeatTotal");
	}

	@Override
	public void updateServer(ITileMultiblock tile) {
		TileMultiblockBase base = (TileMultiblockBase) tile;
		if (base.slots == null || base.slots.length == 0)
			base.slots = new ItemStack[4];
		if (tankGas == null)
			tankGas = new FluidTankSimple(32000);
		if (tankSlag == null)
			tankSlag = new FluidTankSimple(32000);
		if (tank == null)
			tank = new FluidTankSimple(24000);
		if (tankAirHot == null)
			tankAirHot = new FluidTankSimple(32000);
		if (heat >= heatTotal || heatTotal == 0) {
			if (base.burnTime >= base.burnTimeTotal || base.burnTimeTotal == 0) {
				ItemStack[] inputs = new ItemStack[4];
				inputs[0] = tile.getStackInSlot(0);
				inputs[1] = tile.getStackInSlot(1);
				inputs[2] = tile.getStackInSlot(2);
				inputs[3] = tile.getStackInSlot(3);
				if (outputs != null) {
					if (outputs[0] != null)
						if (tankSlag.fill(outputs[0], true) >= outputs[0].amount)
							outputs[0] = null;
					if (outputs[1] != null)
						if (tank.fill(outputs[1], true) >= outputs[1].amount)
							outputs[1] = null;
					if (outputs[0] == null && outputs[1] == null)
						outputs = null;
				} else if (inputs[0] != null) {
					BlastFurnaceRecipe recipe = BlastFurnaceRecipeManager.getRecipe(inputs);
					if (recipe != null) {
						for (int i = 0; i < recipe.getInput().length; i++) {
							if (recipe.getInput()[i] instanceof ItemStack)
								tile.decrStackSize(i, ((ItemStack) recipe.getInput()[i]).stackSize);
							if (recipe.getInput()[i] instanceof OreStack)
								tile.decrStackSize(i, ((OreStack) recipe.getInput()[i]).stackSize);
						}
						outputs = recipe.getOutput().clone();
						base.burnTimeTotal = recipe.getBurntTime();
						heatTotal = recipe.getHeat();
					}
				}
			} else {
				base.burnTime++;
			}
		} else {
			if (base.getWorldObj().rand.nextInt(40) == 20)
				if (tankAirHot != null && tankAirHot.drain(50, true) != null && tankAirHot.drain(50, true).amount >= 50)
					if (tankGas.fill(new FluidStack(FluidRegistry.getFluid("gas.blastfurnace"), 150), true) >= 150)
						heat++;
		}
		base.getWorldObj().markBlockForUpdate(base.xCoord, base.yCoord, base.zCoord);
	}

	@Override
	public int fill(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doFill) {
		if (tile.master.isMultiblock()) {
			if (tile.modifier != null && tile.modifier instanceof MultiblockModifierValveTypeString) {
				if (resource.getFluid() == FluidRegistry.getFluid("air.hot") && tile.modifier.filter == "air.hot")
					return tankAirHot.fill(resource, doFill);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (tile.master.isMultiblock()) {
			if (tile.modifier != null) {
				if (tile.modifier.valveType == ValveType.OUTPUT) {
					if (tile.modifier.filter == "slag")
						return tankSlag.drain(resource, doDrain);
					else if (tile.modifier.filter == "gas.blastfurnace")
						return tankGas.drain(resource, doDrain);
					if (tile.modifier.valveType == ValveType.OUTPUT)
						return tank.drain(resource, doDrain);
				}
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(TileMultiblockBase tile, ForgeDirection from, int maxDrain, boolean doDrain) {
		if (tile.master.isMultiblock()) {
			if (tile.modifier != null) {
				if (tile.modifier.valveType == ValveType.OUTPUT) {
					if (tile.modifier.filter == "slag")
						return tankSlag.drain(maxDrain, doDrain);
					else if (tile.modifier.filter == "gas.blastfurnace")
						return tankGas.drain(maxDrain, doDrain);
					if (tile.modifier.valveType == ValveType.OUTPUT)
						return tank.drain(maxDrain, doDrain);
				}
			}
		}
		return null;
	}

	@Override
	public boolean canFill(TileMultiblockBase tile, ForgeDirection from, Fluid fluid) {
		return tile.master.isMultiblock() && tile.modifier != null && tile.modifier.valveType == ValveType.INPUT;
	}

	@Override
	public boolean canDrain(TileMultiblockBase tile, ForgeDirection from, Fluid fluid) {
		return tile.master.isMultiblock() && tile.modifier != null && tile.modifier.valveType == ValveType.OUTPUT;
	}

	@Override
	public FluidTankInfo[] getTankInfo(TileMultiblockBase tile, ForgeDirection from) {
		if (tile.master.isMultiblock())
			return new FluidTankInfo[] { tankGas.getInfo(), tankAirHot.getInfo(), tank.getInfo(), tankSlag.getInfo() };
		return null;
	}

}
