package nedelosk.modularmachines.common.multiblocks;

import java.util.ArrayList;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.client.gui.multiblocks.GuiCokeOven;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.crafting.CokeOvenRecipe;
import nedelosk.modularmachines.common.crafting.CokeOvenRecipeManager;
import nedelosk.modularmachines.common.inventory.multiblock.ContainerCokeOven;
import nedelosk.nedeloskcore.api.Material.MaterialType;
import nedelosk.nedeloskcore.api.MultiblockModifierValveType.ValveType;
import nedelosk.nedeloskcore.common.blocks.multiblocks.MultiblockPattern;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.core.registry.NCBlocks;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import nedelosk.nedeloskcore.utils.NBTUtils;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class MultiblockCokeOven extends MultiblockModularMachines {
	
	public FluidTankNedelosk tank;
	public FluidTankNedelosk tankGas;
	public ItemStack output;
	public int heat;
	public int heatTotal = 1350;
	
	public MultiblockCokeOven() {
		super();
	}

	@Override
	public String getMultiblockName() {
		return "cokeoven";
	}
	
	@Override
	public MultiblockPattern createPattern() {
		return null;
	}

	@Override
	public ArrayList<MultiblockPattern> createPatterns() {
		ArrayList<MultiblockPattern> list = new ArrayList<MultiblockPattern>();
		char[][] layer_0_0 = {
				{ 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O'}};
		char[][] layer_0_1 = {
				{ 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'B', 'B', 'B', 'O'},
				{ 'O', 'B', 'J', 'B', 'O'},
				{ 'O', 'B', 'B', 'B', 'O'},
				{ 'O', 'O', 'O', 'O', 'O'}};
		char[][] layer_0_2 = {
				{ 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'B', 'V', 'B', 'O'},
				{ 'O', 'V', 'H', 'V', 'O'},
				{ 'O', 'B', 'V', 'B', 'O'},
				{ 'O', 'O', 'O', 'O', 'O'}};
		char[][] layer_0_3 = {
				{ 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'B', 'B', 'B', 'O'},
				{ 'O', 'B', 'F', 'B', 'O'},
				{ 'O', 'B', 'B', 'B', 'O'},
				{ 'O', 'O', 'O', 'O', 'O'}};
		list.add(new MultiblockPattern(new char[][][]{ layer_0_0, layer_0_1, layer_0_2, layer_0_3, layer_0_0}, 2, 1, 2));
		char[][] layer_1_0 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'}};
		char[][] layer_1_1 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O'},
				{ 'O', 'B', 'B', 'F', 'B', 'B', 'O'},
				{ 'O', 'B', 'F', 'J', 'F', 'B', 'O'},
				{ 'O', 'B', 'B', 'F', 'B', 'B', 'O'},
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'}};
		char[][] layer_1_2 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O'},
				{ 'O', 'B', 'H', 'H', 'H', 'B', 'O'},
				{ 'O', 'B', 'H', 'H', 'H', 'B', 'O'},
				{ 'O', 'B', 'H', 'H', 'H', 'B', 'O'},
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'}};
		char[][] layer_1_3 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'B', 'B', 'V', 'B', 'B', 'O'},
				{ 'O', 'B', 'H', 'H', 'H', 'B', 'O'},
				{ 'O', 'V', 'H', 'H', 'H', 'V', 'O'},
				{ 'O', 'B', 'H', 'H', 'H', 'B', 'O'},
				{ 'O', 'B', 'B', 'V', 'B', 'B', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'}};
		char[][] layer_1_4 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O'},
				{ 'O', 'B', 'B', 'F', 'B', 'B', 'O'},
				{ 'O', 'B', 'F', 'B', 'F', 'B', 'O'},
				{ 'O', 'B', 'B', 'F', 'B', 'B', 'O'},
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O'},
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O'}};
		list.add(new MultiblockPattern(new char[][][]{ layer_1_0, layer_1_1, layer_1_2, layer_1_3, layer_1_2, layer_1_4, layer_1_0}, 3, 1, 3));
		return list;
	}

	@Override
	public boolean testBlock() {
		return false;
	}

	@Override
	public boolean isPatternBlockValid(int x, int y, int z, char pattern, TileMultiblockBase base) {
		Block block = base.getWorldObj().getBlock(x, y, z);
		TileEntity tile = base.getWorldObj().getTileEntity(x, y, z);
		TileMultiblockBase multiblock = null;
		if(tile instanceof TileMultiblockBase)
			multiblock = (TileMultiblockBase) tile;
		switch (pattern) {
		case 'B':
		{
            if (block != NCBlocks.Multiblock.block() || multiblock == null)
            {
                return false;
            }
            break;
		}
		case 'J':
		{
            if (block != NCBlocks.Multiblock.block() || multiblock == null)
            {
                return false;
            }
            break;
		}
		case 'F':
		{
            if (block != NCBlocks.Multiblock_Valve.block() && block != NCBlocks.Multiblock.block() || multiblock == null || multiblock.material.type != MaterialType.BRICK)
            {
                return false;
            }
            else if(block == NCBlocks.Multiblock_Valve.block())
            {
            	multiblock.modifier.valveType = ValveType.OUTPUT;
            }
            break;
		}
		case 'V':
		{
            if (block != NCBlocks.Multiblock_Valve.block() && block != NCBlocks.Multiblock.block() || multiblock == null || multiblock.material.type != MaterialType.BRICK)
            {
                return false;
            }
            else if(block == NCBlocks.Multiblock_Valve.block())
            {
            	multiblock.modifier.valveType = ValveType.INPUT;
            }
            break;
		}
		case 'H':
		{
            if (!base.getWorldObj().isAirBlock(x, y, z))
            {
                return false;
            }
            break;
		}
		case 'O':
		{
            if (block == NCBlocks.Multiblock_Valve.block() || block == NCBlocks.Multiblock.block() || tile instanceof TileMultiblockBase)
            {
                return false;
            }
            break;
		}
		}
		if(tile != null)
			base.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
		return true;
	}

	@Override
	public void onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side) {
		player.openGui(ModularMachines.instance, 0, world, x, y, z);
	}

	@Override
	public Container getContainer(TileMultiblockBase tile, InventoryPlayer inventory) {
		return new ContainerCokeOven(tile, inventory);
	}

	@Override
	public Object getGUIContainer(TileMultiblockBase tile, InventoryPlayer inventory) {
		return new GuiCokeOven(tile, inventory);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTUtils.writeTankToNBT("Tank", nbt, tank);
		NBTUtils.writeTankToNBT("TankGas", nbt, tankGas);
		if(output != null)
		{
		NBTTagCompound nbtTag = new NBTTagCompound();
		output.writeToNBT(nbtTag);
		nbt.setTag("Output", nbtTag);
		}
		nbt.setInteger("Heat", heat);
		nbt.setInteger("HeatTotal", heatTotal);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
			if(nbt.hasKey("Tank"))
			{
				NBTTagCompound nbtTag = nbt.getCompoundTag("Tank");
				tank = new FluidTankNedelosk(nbtTag.getInteger("Capacity"));
				tank.readFromNBT(nbtTag);
			}
			if(nbt.hasKey("TankGas"))
			{
				NBTTagCompound nbtTag = nbt.getCompoundTag("TankGas");
				tankGas = new FluidTankNedelosk(nbtTag.getInteger("Capacity"));
				tankGas.readFromNBT(nbtTag);
			}
			if(nbt.hasKey("Output"))
			{
				NBTTagCompound nbtTag = nbt.getCompoundTag("Output");
				output = ItemStack.loadItemStackFromNBT(nbtTag);
			}
			
			heat = nbt.getInteger("Heat");
			heatTotal = nbt.getInteger("HeatTotal");
	}
	
	@Override
	public void updateServer(TileMultiblockBase tile) {
			if(tile.slots == null)
				tile.slots = new ItemStack[2];
			if(tank == null)
				tank = new FluidTankNedelosk(32000);
			if(tankGas == null)
				tankGas = new FluidTankNedelosk(32000);
		if(heat >= heatTotal || heatTotal == 0)
		{
		if(tile.burnTime >= tile.burnTimeTotal || tile.burnTimeTotal == 0)
		{
			ItemStack input = tile.getStackInSlot(0);
			if(output != null)
			{
				if(tile.addToOutput(output, 0, 1))
					output = null;
			}
			else if(input != null)
			{
				CokeOvenRecipe recipe = CokeOvenRecipeManager.getRecipe(input);
				if(recipe != null)
				{
					output = recipe.getOutput();
					tile.burnTimeTotal = recipe.getBurntTime();
				}
			}
		}
		else
		{
			tile.burnTime++;
		}
		}
		else
		{
			if(tankGas.getFluid() != null && tankGas.getFluid().amount > 0)
			{
				
				int heat = tankGas.getFluid().fluid.getTemperature() / ((heatTotal / tankGas.getFluid().fluid.getTemperature()) / 30);
				if(tankGas.drain(30, true) != null & tankGas.drain(30, true).amount >= 30)
					heat+= heat;
			}
		}
		tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
	}

	@Override
	public int fill(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doFill) {
		if(tile.master.isMultiblock)
		{
			if(tile.modifier.valveType == ValveType.INPUT)
			{
				if(ModularMachinesApi.airHeatingPlantGas.contains(resource.getFluid()))
					return tankGas.fill(resource, doFill);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(tile.master.isMultiblock)
		{
			if(tile.modifier.valveType == ValveType.OUTPUT)
			{
				return tank.drain(resource, doDrain);
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(TileMultiblockBase tile, ForgeDirection from, int maxDrain, boolean doDrain) {
		if(tile.master.isMultiblock)
		{
			if(tile.modifier.valveType == ValveType.OUTPUT)
			{
				return tank.drain(maxDrain, doDrain);
			}
		}
		return null;
	}

	@Override
	public boolean canFill(TileMultiblockBase tile, ForgeDirection from, Fluid fluid) {
		return tile.master != null && tile.master.isMultiblock && tile.modifier.valveType == ValveType.INPUT;
	}

	@Override
	public boolean canDrain(TileMultiblockBase tile, ForgeDirection from, Fluid fluid) {
		return tile.master != null && tile.master.isMultiblock && tile.modifier.valveType == ValveType.OUTPUT;
	}

	@Override
	public FluidTankInfo[] getTankInfo(TileMultiblockBase tile, ForgeDirection from) {
		if(tile.master.isMultiblock)
			return new FluidTankInfo[]{ tankGas.getInfo(), tank.getInfo()};
		return null;
	}

}
