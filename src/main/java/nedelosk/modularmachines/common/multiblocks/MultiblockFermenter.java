package nedelosk.modularmachines.common.multiblocks;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.FluidTankBasic;
import nedelosk.forestday.api.multiblocks.ITileMultiblock;
import nedelosk.forestday.api.multiblocks.MultiblockPattern;
import nedelosk.forestday.common.core.managers.FBlockManager;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.forestday.utils.NBTUtils;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.client.gui.multiblocks.GuiFermenter;
import nedelosk.modularmachines.client.renderers.tile.TileRendererMultiblockFermenter;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.crafting.FermenterRecipeManager;
import nedelosk.modularmachines.common.crafting.FermenterRecipeManager.FermenterRecipe;
import nedelosk.modularmachines.common.inventory.multiblock.ContainerFermenter;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class MultiblockFermenter extends MultiblockModularMachines {

	public FluidTankBasic tank;
	public FluidTankBasic tank2;
	public FluidTankBasic tankOut;
	public FluidStack output;

	public MultiblockFermenter() {
		super();
	}

	@Override
	public String getMultiblockName() {
		return "fermenter";
	}

	@Override
	public MultiblockPattern createPattern() {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer getRenderer() {
		return new TileRendererMultiblockFermenter();
	}

	@Override
	public ArrayList<MultiblockPattern> createPatterns() {
		ArrayList<MultiblockPattern> list = new ArrayList<MultiblockPattern>();
		char[][] layer_0_0_1 = { { 'O', 'O', 'O' }, { 'O', 'M', 'O' }, { 'O', 'J', 'O' }, { 'O', 'M', 'O' },
				{ 'O', 'O', 'O' } };
		char[][] layer_0_0_2 = { { 'O', 'O', 'O' }, { 'O', 'M', 'O' }, { 'O', 'M', 'O' }, { 'O', 'V', 'O' },
				{ 'O', 'O', 'O' } };
		char[][] layer_0_0_0 = { { 'O', 'O', 'O' }, { 'O', 'O', 'O' }, { 'O', 'O', 'O' }, { 'O', 'O', 'O' },
				{ 'O', 'O', 'O' } };
		list.add(new MultiblockPattern(new char[][][] { layer_0_0_0, layer_0_0_1, layer_0_0_2, layer_0_0_0 }, 2, 1, 1,
				0));
		char[][] layer_0_1_1 = { { 'O', 'O', 'O' }, { 'O', 'V', 'O' }, { 'O', 'M', 'O' }, { 'O', 'M', 'O' },
				{ 'O', 'O', 'O' } };
		list.add(new MultiblockPattern(new char[][][] { layer_0_0_0, layer_0_0_1, layer_0_1_1, layer_0_0_0 }, 2, 1, 1,
				1));
		char[][] layer_1_0_0 = { { 'O', 'O', 'O', 'O', 'O' }, { 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' } };
		char[][] layer_1_0_1 = { { 'O', 'O', 'O', 'O', 'O' }, { 'O', 'M', 'J', 'M', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' } };
		char[][] layer_1_0_2 = { { 'O', 'O', 'O', 'O', 'O' }, { 'O', 'V', 'M', 'M', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' } };
		list.add(new MultiblockPattern(new char[][][] { layer_1_0_0, layer_1_0_1, layer_1_0_2, layer_1_0_0 }, 1, 1, 2,
				2));
		char[][] layer_1_1_1 = { { 'O', 'O', 'O', 'O', 'O' }, { 'O', 'M', 'M', 'V', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' } };
		list.add(new MultiblockPattern(new char[][][] { layer_1_0_0, layer_1_0_1, layer_1_1_1, layer_1_0_0 }, 1, 1, 2,
				3));
		return list;
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
		case 'J':
		case 'M': {
			if (block != FBlockManager.Multiblock.block() || multiblock == null) {
				return false;
			}
			break;
		}
		case 'V': {
			if (block != FBlockManager.Multiblock_Valve.block() || multiblock == null) {
				return false;
			}
			break;
		}
		case 'O': {
			if (block == FBlockManager.Multiblock_Valve.block() || block == FBlockManager.Multiblock.block()
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
	public IIcon getIcon(int side, ITileMultiblock tile) {
		return null;
	}

	@Override
	public void onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side) {
		player.openGui(ModularMachines.instance, 0, world, x, y, z);
	}

	@Override
	public Container getContainer(ITileMultiblock tile, InventoryPlayer inventory) {
		return new ContainerFermenter((TileMultiblockBase<MultiblockFermenter>) tile, inventory);
	}

	@Override
	public Object getGUIContainer(ITileMultiblock tile, InventoryPlayer inventory) {
		return new GuiFermenter((TileMultiblockBase) tile, inventory);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTUtils.writeTankToNBT("Tank", nbt, tank);
		NBTUtils.writeTankToNBT("Tank2", nbt, tank2);
		NBTUtils.writeTankToNBT("TankOut", nbt, tankOut);
		if (output != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			output.writeToNBT(nbtTag);
			nbt.setTag("Output", nbtTag);
		}

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("Tank")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Tank");
			tank = new FluidTankBasic(nbtTag.getInteger("Capacity"));
			tank.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("Tank")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Tank2");
			tank2 = new FluidTankBasic(nbtTag.getInteger("Capacity"));
			tank2.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("TankOut")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("TankGas");
			tankOut = new FluidTankBasic(nbtTag.getInteger("Capacity"));
			tankOut.readFromNBT(nbtTag);
		}
		if (nbt.hasKey("Output")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Output");
			output = FluidStack.loadFluidStackFromNBT(nbtTag);
		}
	}

	@Override
	public void updateServer(ITileMultiblock tile) {
		TileMultiblockBase base = (TileMultiblockBase) tile;
		if (tank == null)
			tank = new FluidTankBasic(32000);
		if (tank2 == null)
			tank2 = new FluidTankBasic(32000);
		if (tankOut == null)
			tankOut = new FluidTankBasic(32000);
		if (base.burnTime >= base.burnTimeTotal || base.burnTimeTotal == 0) {
			FluidStack input = tank.getFluid();
			FluidStack inputFermenterFluid = tank2.getFluid();
			if (output != null) {
				if (tankOut.fill(output, true) > 0)
					output = null;
			} else if (input != null && inputFermenterFluid != null
					&& ModularMachinesApi.fermenterFluid.get(inputFermenterFluid.getFluid()) != null
					&& inputFermenterFluid.amount >= 150) {
				FermenterRecipe recipe = FermenterRecipeManager.getRecipe(input);
				if (recipe != null) {
					output = recipe.getOutput().copy();
					output.amount *= ModularMachinesApi.fermenterFluid.get(inputFermenterFluid.getFluid());
					tank2.drain(150, true);
					base.burnTimeTotal = recipe.getBurntTime();
					base.isWorking = true;
				} else
					base.isWorking = false;
			} else
				base.isWorking = false;
		} else {
			base.burnTime++;
		}
		base.getWorldObj().markBlockForUpdate(base.xCoord, base.yCoord, base.zCoord);
	}

	@Override
	public int fill(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doFill) {
		if (tile.master.isMultiblock() && tile.master.isTested()) {
			if (tile.master.getPattern().tier == 0) {
				switch (from) {
				case SOUTH:
					if (ModularMachinesApi.fermenterFluid.get(resource.getFluid()) != null)
						return tank2.fill(resource, doFill);
					break;
				case NORTH:
					if (FermenterRecipeManager.isFluidInput(resource))
						return tank.fill(resource, doFill);
					break;
				default:
					return 0;
				}
			} else if (tile.master.getPattern().tier == 1) {
				switch (from) {
				case NORTH:
					if (ModularMachinesApi.fermenterFluid.get(resource.getFluid()) != null)
						return tank2.fill(resource, doFill);
					break;
				case SOUTH:
					if (FermenterRecipeManager.isFluidInput(resource))
						return tank.fill(resource, doFill);
					break;
				default:
					return 0;
				}
			} else if (tile.master.getPattern().tier == 2) {
				switch (from) {
				case EAST:
					if (ModularMachinesApi.fermenterFluid.get(resource.getFluid()) != null)
						return tank2.fill(resource, doFill);
					break;
				case WEST:
					if (FermenterRecipeManager.isFluidInput(resource))
						return tank.fill(resource, doFill);
					break;
				default:
					return 0;
				}
			} else if (tile.master.getPattern().tier == 3) {
				switch (from) {
				case EAST:
					if (FermenterRecipeManager.isFluidInput(resource))
						return tank.fill(resource, doFill);
					break;
				case WEST:
					if (ModularMachinesApi.fermenterFluid.get(resource.getFluid()) != null)
						return tank2.fill(resource, doFill);
					break;
				default:
					return 0;
				}
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (tile.master.isMultiblock() && tile.master.isTested()) {
			if (tile.master.getPattern().tier == 0) {
				switch (from) {
				case EAST:
					return tankOut.drain(resource, doDrain);
				default:
					return null;
				}
			} else if (tile.master.getPattern().tier == 1) {
				switch (from) {
				case WEST:
					return tankOut.drain(resource, doDrain);
				default:
					return null;
				}
			} else if (tile.master.getPattern().tier == 2) {
				switch (from) {
				case SOUTH:
					return tankOut.drain(resource, doDrain);
				default:
					return null;
				}
			} else if (tile.master.getPattern().tier == 3) {
				switch (from) {
				case NORTH:
					return tankOut.drain(resource, doDrain);
				default:
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(TileMultiblockBase tile, ForgeDirection from, int maxDrain, boolean doDrain) {
		if (tile.master.isMultiblock() && tile.master.isTested()) {
			if (tile.master.getPattern().tier == 0) {
				switch (from) {
				case EAST:
					return tankOut.drain(maxDrain, doDrain);
				default:
					return null;
				}
			} else if (tile.master.getPattern().tier == 1) {
				switch (from) {
				case WEST:
					return tankOut.drain(maxDrain, doDrain);
				default:
					return null;
				}
			} else if (tile.master.getPattern().tier == 2) {
				switch (from) {
				case SOUTH:
					return tankOut.drain(maxDrain, doDrain);
				default:
					return null;
				}
			} else if (tile.master.getPattern().tier == 3) {
				switch (from) {
				case NORTH:
					return tankOut.drain(maxDrain, doDrain);
				default:
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public boolean canFill(TileMultiblockBase tile, ForgeDirection from, Fluid fluid) {
		return tile.master != null && tile.master.isMultiblock();
	}

	@Override
	public boolean canDrain(TileMultiblockBase tile, ForgeDirection from, Fluid fluid) {
		return tile.master != null && tile.master.isMultiblock();
	}

	@Override
	public FluidTankInfo[] getTankInfo(TileMultiblockBase tile, ForgeDirection from) {
		if (tile.master.isMultiblock() && tile.master.isTested())
			return new FluidTankInfo[] { tankOut.getInfo(), tank.getInfo(), tank2.getInfo() };
		return null;
	}

}
