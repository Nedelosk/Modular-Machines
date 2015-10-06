package nedelosk.modularmachines.common.multiblocks;

import java.util.ArrayList;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.client.gui.multiblocks.GuiCokeOven;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.crafting.CokeOvenRecipeManager;
import nedelosk.modularmachines.common.crafting.CokeOvenRecipeManager.CokeOvenRecipe;
import nedelosk.modularmachines.common.inventory.multiblock.ContainerCokeOven;
import nedelosk.nedeloskcore.api.Material.MaterialType;
import nedelosk.nedeloskcore.api.multiblock.MultiblockPattern;
import nedelosk.nedeloskcore.api.multiblock.ITileMultiblock;
import nedelosk.nedeloskcore.api.multiblock.MultiblockModifierValveType.ValveType;
import nedelosk.nedeloskcore.client.TextureAtlasMap;
import nedelosk.nedeloskcore.common.blocks.BlockMultiblock;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import nedelosk.nedeloskcore.common.core.registry.NCBlockManager;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import nedelosk.nedeloskcore.utils.NBTUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
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
	public IIcon[] cokeOvenIcon;
	public IIcon[] cokeOvenOnIcon;
	
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
	public void registerBlockIcons(IIconRegister IIconRegister) {
		cokeOvenIcon = TextureAtlasMap.unstitchIcons(IIconRegister, "modularmachines:multiblocks/coke_oven", 3, 3);
		cokeOvenOnIcon = TextureAtlasMap.unstitchIcons(IIconRegister, "modularmachines:multiblocks/coke_oven_on", 3, 3);
	}

	@Override
	public ArrayList<MultiblockPattern> createPatterns() {
		ArrayList<MultiblockPattern> list = new ArrayList<MultiblockPattern>();
		char[][] layer_0_0_0 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		char[][] layer_0_0_1 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'J', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		char[][] layer_0_0_2 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'f', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		char[][] layer_0_0_3 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'V', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'e', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		char[][] layer_0_0_4 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'd', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		char[][] layer_0_0_5 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'F', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		list.add(new MultiblockPattern(new char[][][]{ layer_0_0_0, layer_0_0_1, layer_0_0_2, layer_0_0_3, layer_0_0_4, layer_0_0_5, layer_0_0_0}, 3, 1, 2));
		char[][] layer_0_1_1 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'J', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		char[][] layer_0_1_2 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'f', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		char[][] layer_0_1_3 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'e', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'V', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		char[][] layer_0_1_4 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'd', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'H', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		char[][] layer_0_1_5 = {
				{ 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'F', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O' }
		};
		list.add(new MultiblockPattern(new char[][][]{ layer_0_0_0, layer_0_1_1, layer_0_1_2, layer_0_1_3, layer_0_1_4, layer_0_1_5, layer_0_0_0}, 3, 1, 2));
		char[][] layer_0_2_0 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		char[][] layer_0_2_1 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'J', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		char[][] layer_0_2_2 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'H', 'H', 'H', 'f', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		char[][] layer_0_2_3 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'V', 'H', 'H', 'H', 'e', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		char[][] layer_0_2_4 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'H', 'H', 'H', 'd', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		char[][] layer_0_2_5 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'F', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		list.add(new MultiblockPattern(new char[][][]{ layer_0_2_0, layer_0_2_1, layer_0_2_2, layer_0_2_3, layer_0_2_4, layer_0_2_5, layer_0_2_0}, 2, 1, 3));
		char[][] layer_0_3_1 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'V', 'B', 'J', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		char[][] layer_0_3_2 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'f', 'H', 'H', 'H', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		char[][] layer_0_3_3 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'e', 'H', 'H', 'H', 'V', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		char[][] layer_0_3_4 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'd', 'H', 'H', 'H', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		char[][] layer_0_3_5 = {
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'F', 'B', 'B', 'O' },
				{ 'O', 'B', 'B', 'B', 'B', 'B', 'O' },
				{ 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
		};
		list.add(new MultiblockPattern(new char[][][]{ layer_0_2_0, layer_0_3_1, layer_0_3_2, layer_0_3_3, layer_0_3_4, layer_0_3_5, layer_0_2_0}, 2, 1, 3));
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
		if(tile instanceof TileMultiblockBase)
			multiblock = (TileMultiblockBase) tile;
		switch (pattern) {
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'B':
		{
            if (block != NCBlockManager.Multiblock.block() || multiblock == null || multiblock.material == null ||  multiblock.material.type != MaterialType.BRICK)
            {
                return false;
            }
            break;
		}
		case 'J':
		{
            if (block != NCBlockManager.Multiblock.block() || multiblock == null || multiblock.material == null ||  multiblock.material.type != MaterialType.BRICK)
            {
                return false;
            }
            break;
		}
		case 'F':
		{
            if (block != NCBlockManager.Multiblock_Valve.block() || multiblock == null || multiblock.material == null || multiblock.material.type != MaterialType.BRICK)
            {
                return false;
            }
            else
            {
            	multiblock.modifier.valveType = ValveType.OUTPUT;
            }
            break;
		}
		case 'V':
		{
            if (block != NCBlockManager.Multiblock_Valve.block() || multiblock == null || multiblock.material == null || multiblock.material.type != MaterialType.BRICK)
            {
                return false;
            }
            else
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
            if (block == NCBlockManager.Multiblock_Valve.block() || block == NCBlockManager.Multiblock.block() || tile instanceof TileMultiblockBase)
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
	public IIcon getIcon(int side, ITileMultiblock tile) {
		char c = tile.getPatternMarker();
		if(!tile.isWorking())
		{
			switch (c) {
			case 'a':
				BlockMultiblock.renderPass = 1;
				return cokeOvenIcon[0];
			case 'b':
				BlockMultiblock.renderPass = 1;
				return cokeOvenIcon[3];
			case 'c':
				BlockMultiblock.renderPass = 1;
				return cokeOvenIcon[6];
			case 'd':
				BlockMultiblock.renderPass = 1;
				return cokeOvenIcon[1];
			case 'e':
				BlockMultiblock.renderPass = 1;
				return cokeOvenIcon[4];
			case 'f':
				BlockMultiblock.renderPass = 1;
				return cokeOvenIcon[7];
			case 'g':
				BlockMultiblock.renderPass = 1;
				return cokeOvenIcon[2];
			case 'h':
				BlockMultiblock.renderPass = 1;
				return cokeOvenIcon[5];
			case 'i':
				BlockMultiblock.renderPass = 1;
				return cokeOvenIcon[8];
			default:
			return null;
			}
		}
		else
		{
			switch (c) {
			case 'a':
				BlockMultiblock.renderPass = 1;
				return cokeOvenOnIcon[0];
			case 'b':
				BlockMultiblock.renderPass = 1;
				return cokeOvenOnIcon[3];
			case 'c':
				BlockMultiblock.renderPass = 1;
				return cokeOvenOnIcon[6];
			case 'd':
				BlockMultiblock.renderPass = 1;
				return cokeOvenOnIcon[1];
			case 'e':
				BlockMultiblock.renderPass = 1;
				return cokeOvenOnIcon[4];
			case 'f':
				BlockMultiblock.renderPass = 1;
				return cokeOvenOnIcon[7];
			case 'g':
				BlockMultiblock.renderPass = 1;
				return cokeOvenOnIcon[2];
			case 'h':
				BlockMultiblock.renderPass = 1;
				return cokeOvenOnIcon[5];
			case 'i':
				BlockMultiblock.renderPass = 1;
				return cokeOvenOnIcon[8];
			default:
			return null;
			}
		}
	}

	@Override
	public void onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side) {
		player.openGui(ModularMachines.instance, 0, world, x, y, z);
	}

	@Override
	public Container getContainer(ITileMultiblock tile, InventoryPlayer inventory) {
		return new ContainerCokeOven((TileMultiblockBase<MultiblockCokeOven>) tile, inventory);
	}

	@Override
	public Object getGUIContainer(ITileMultiblock tile, InventoryPlayer inventory) {
		return new GuiCokeOven((TileMultiblockBase<MultiblockCokeOven>) tile, inventory);
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
	public void updateServer(ITileMultiblock tile) {
		TileMultiblockBase base = (TileMultiblockBase) tile;
			if(base.slots == null)
				base.slots = new ItemStack[2];
			if(tank == null)
				tank = new FluidTankNedelosk(32000);
			if(tankGas == null)
				tankGas = new FluidTankNedelosk(32000);
		if(heat >= heatTotal || heatTotal == 0)
		{
		if(base.burnTime >= base.burnTimeTotal || base.burnTimeTotal == 0)
		{
			ItemStack input = tile.getStackInSlot(0);
			if(output != null)
			{
				if(base.addToOutput(output, 0, 1))
					output = null;
			}
			else if(input != null)
			{
				CokeOvenRecipe recipe = CokeOvenRecipeManager.getRecipe(input);
				if(recipe != null)
				{
					output = recipe.getOutput();
					base.burnTimeTotal = recipe.getBurntTime();
					base.isWorking = true;;
				}
				else
					base.isWorking = false;
			}
			else
				base.isWorking = false;
		}
		else
		{
			base.burnTime++;
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
		base.getWorldObj().markBlockForUpdate(base.xCoord, base.yCoord, base.zCoord);
	}

	@Override
	public int fill(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doFill) {
		if(tile.master.isMultiblock())
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
		if(tile.master.isMultiblock())
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
		if(tile.master.isMultiblock())
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
		return tile.master != null && tile.master.isMultiblock() && tile.modifier.valveType == ValveType.INPUT;
	}

	@Override
	public boolean canDrain(TileMultiblockBase tile, ForgeDirection from, Fluid fluid) {
		return tile.master != null && tile.master.isMultiblock() && tile.modifier.valveType == ValveType.OUTPUT;
	}

	@Override
	public FluidTankInfo[] getTankInfo(TileMultiblockBase tile, ForgeDirection from) {
		if(tile.master.isMultiblock())
			return new FluidTankInfo[]{ tankGas.getInfo(), tank.getInfo()};
		return null;
	}

}
