package nedelosk.forestday.common.machines.base.furnace.alloysmelter;

import java.util.ArrayList;

import nedelosk.forestday.api.crafting.BurningMode;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class AlloySmelterRecipe {
	public ItemStack input;
	public ItemStack[] output;
	public FluidStack inputFluid;
	public int burnTime;
	public int energy;

	public AlloySmelterRecipe(ItemStack input, FluidStack inputFluid, int burnTime, int energy, ItemStack... output){
		this.input = input;
		this.inputFluid = inputFluid;
		this.burnTime = burnTime;
		this.energy = energy;
		this.output = output;
	}
	
	public AlloySmelterRecipe(ItemStack input, FluidStack inputFluid, int burnTime, ItemStack... output){
		this.input = input;
		this.inputFluid = inputFluid;
		this.burnTime = burnTime;
		this.output = output;
	}
	
	public AlloySmelterRecipe(ItemStack input, int burnTime, int energy, ItemStack... output){
		this.input = input;
		this.burnTime = burnTime;
		this.energy = energy;
		this.output = output;
	}
	
	public AlloySmelterRecipe(ItemStack input, int burnTime, ItemStack... output){
		this.input = input;
		this.burnTime = burnTime;
		this.output = output;
	}
	
}
