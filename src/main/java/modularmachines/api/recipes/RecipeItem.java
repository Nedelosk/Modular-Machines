package modularmachines.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fluids.FluidStack;

public class RecipeItem {
	
	public final FluidStack fluid;
	public final ItemStack item;
	public final OreStack ore;
	public final float chance;
	
	public RecipeItem(ItemStack item) {
		this.item = item;
		this.fluid = null;
		this.ore = null;
		this.chance = -1;
	}
	
	public RecipeItem(FluidStack fluid) {
		this.fluid = fluid;
		this.item = null;
		this.ore = null;
		this.chance = -1;
	}
	
	public RecipeItem(OreStack ore) {
		this.fluid = null;
		this.item = null;
		this.ore = ore;
		this.chance = -1;
	}
	
	public RecipeItem(ItemStack item, float chance) {
		this.item = item;
		this.fluid = null;
		this.ore = null;
		this.chance = chance;
	}
	
	public RecipeItem(FluidStack fluid, float chance) {
		this.fluid = fluid;
		this.item = null;
		this.ore = null;
		this.chance = chance;
	}
	
	public RecipeItem(OreStack ore, float chance) {
		this.fluid = null;
		this.item = null;
		this.ore = ore;
		this.chance = chance;
	}
	
	public RecipeItem(ItemStack item, FluidStack fluid, OreStack ore, float chance) {
		this.fluid = fluid;
		this.item = item;
		this.ore = ore;
		this.chance = chance;
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("chance", this.chance);
		if (item != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			item.writeToNBT(nbtTag);
			nbt.setTag("item", nbtTag);
		} else if (fluid != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			fluid.writeToNBT(nbtTag);
			nbt.setTag("fluid", nbtTag);
		} else if (ore != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			ore.writeToNBT(nbtTag);
			nbt.setTag("ore", nbtTag);
		}
	}
	
	public static RecipeItem loadFromNBT(NBTTagCompound nbt) {
		ItemStack item = null;
		FluidStack fluid = null;
		OreStack ore = null;
		int chance = nbt.getInteger("chance");
		if (nbt.hasKey("item")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("item");
			item = new ItemStack(nbtTag);
		} else if (nbt.hasKey("fluid")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("fluid");
			fluid = FluidStack.loadFluidStackFromNBT(nbtTag);
		} else if (nbt.hasKey("ore")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("ore");
			ore = new OreStack(nbtTag);
		}
		return new RecipeItem(item, fluid, ore, chance);
	}
	
	public boolean isFluid() {
		return fluid != null;
	}
	
	public boolean isItem() {
		return item != null;
	}
	
	public boolean isOre() {
		return ore != null;
	}
	
	public boolean isEmpty() {
		return !isFluid() && !isItem() && !isOre();
	}
	
	public boolean canUseItem(float chance) {
		return this.chance == -1 || chance >= this.chance;
	}
	
	public RecipeItem copy() {
		ItemStack item = this.item;
		FluidStack fluid = this.fluid;
		OreStack ore = this.ore;
		if (item != null) {
			item = item.copy();
		}
		if (fluid != null) {
			fluid = fluid.copy();
		}
		if (ore != null) {
			ore = new OreStack(ore.oreDict, ore.stackSize);
		}
		return new RecipeItem(item, fluid, ore, chance);
	}
}
