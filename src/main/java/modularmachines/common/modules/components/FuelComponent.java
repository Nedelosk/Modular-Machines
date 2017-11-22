package modularmachines.common.modules.components;

import java.util.function.Function;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.modules.INBTReadable;
import modularmachines.api.modules.INBTWritable;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.ModuleComponent;
import modularmachines.common.tanks.FluidTankHandler;
import modularmachines.common.tanks.FluidTankModule;

public abstract class FuelComponent extends ModuleComponent implements INBTWritable, INBTReadable {
	protected int fuel;
	protected int fuelTotal;
	private final int fuelPerUse;
	
	public FuelComponent(int fuelPerUse) {
		this.fuelPerUse = fuelPerUse;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		fuel = compound.getInteger("Fuel");
		fuelTotal = compound.getInteger("FuelTotal");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Fuel", fuel);
		compound.setInteger("FuelTotal", fuelTotal);
		return compound;
	}
	
	public int getFuel() {
		return fuel;
	}
	
	public int getFuelTotal() {
		return fuelTotal;
	}
	
	public boolean hasFuel() {
		return fuel > 0;
	}
	
	public void removeFuel() {
		fuel -= fuelPerUse;
	}
	
	public abstract boolean updateFuel();
	
	public static class Items extends FuelComponent {
		private final Function<ItemStack, Integer> fuelGetter;
		private final int fuelSlot;
		
		public Items(int fuelPerUse, int fuelSlot) {
			this(fuelPerUse, TileEntityFurnace::getItemBurnTime, fuelSlot);
		}
		
		public Items(int fuelPerUse, Function<ItemStack, Integer> fuelGetter, int fuelSlot) {
			super(fuelPerUse);
			this.fuelGetter = fuelGetter;
			this.fuelSlot = fuelSlot;
		}
		
		@Override
		public boolean updateFuel() {
			ItemHandlerModule itemHandler = provider.getComponent(ItemHandlerModule.class);
			if (itemHandler == null) {
				return false;
			}
			ItemStack input = itemHandler.getStackInSlot(fuelSlot);
			if (input.isEmpty() || itemHandler.extractItemInternal(fuelSlot, 1, false).isEmpty()) {
				return false;
			}
			fuel = fuelGetter.apply(input);
			fuelTotal = fuel;
			return true;
		}
		
		@Override
		public void removeFuel() {
		
		}
	}
	
	public static class Fluids extends FuelComponent {
		private final Function<FluidStack, Integer> fuelGetter;
		private final int neededAmount;
		private final int fuelSlot;
		
		public Fluids(int fuelPerUse, Function<FluidStack, Integer> fuelGetter, int fuelSlot, int neededAmount) {
			super(fuelPerUse);
			this.fuelGetter = fuelGetter;
			this.fuelSlot = fuelSlot;
			this.neededAmount = neededAmount;
		}
		
		@Override
		public boolean updateFuel() {
			FluidTankHandler fluidHandler = provider.getComponent(FluidTankHandler.class);
			if (fluidHandler == null) {
				return false;
			}
			FluidTankModule tank = fluidHandler.getContainer(fuelSlot);
			if (tank == null) {
				return false;
			}
			FluidStack input = tank.getFluid();
			if (input == null) {
				return false;
			}
			input = tank.drainInternal(neededAmount, true);
			if (input == null) {
				return false;
			}
			fuel = fuelGetter.apply(input);
			fuelTotal = fuel;
			return true;
		}
	}
}
