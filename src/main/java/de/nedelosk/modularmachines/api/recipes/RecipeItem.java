package de.nedelosk.modularmachines.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class RecipeItem {

	public final FluidStack fluid;
	public final ItemStack item;
	public final OreStack ore;
	public final int chance;
	public int index;

	public RecipeItem(ItemStack item) {
		this.item = item;
		this.fluid = null;
		this.ore = null;
		this.index = 0;
		this.chance = -1;
	}

	public RecipeItem(FluidStack fluid) {
		this.fluid = fluid;
		this.item = null;
		this.ore = null;
		this.index = 0;
		this.chance = -1;
	}

	public RecipeItem(OreStack ore) {
		this.fluid = null;
		this.item = null;
		this.ore = ore;
		this.index = 0;
		this.chance = -1;
	}

	public RecipeItem(ItemStack item, int chance) {
		this.item = item;
		this.fluid = null;
		this.ore = null;
		this.index = 0;
		this.chance = chance;
	}

	public RecipeItem(FluidStack fluid, int chance) {
		this.fluid = fluid;
		this.item = null;
		this.ore = null;
		this.index = 0;
		this.chance = chance;
	}

	public RecipeItem(OreStack ore, int chance) {
		this.fluid = null;
		this.item = null;
		this.ore = ore;
		this.chance = chance;
	}

	public RecipeItem(int slotIndex, ItemStack item) {
		this.item = item;
		this.chance = -1;
		this.fluid = null;
		this.ore = null;
		this.index = slotIndex;
	}

	public RecipeItem(int slotIndex, FluidStack fluid) {
		this.fluid = fluid;
		this.chance = -1;
		this.item = null;
		this.ore = null;
		this.index = slotIndex;
	}

	public RecipeItem(int slotIndex, OreStack ore) {
		this.fluid = null;
		this.item = null;
		this.chance = -1;
		this.ore = ore;
		this.index = slotIndex;
	}

	public RecipeItem(int slotIndex, ItemStack item, int chance) {
		this.item = item;
		this.chance = chance;
		this.fluid = null;
		this.ore = null;
		this.index = slotIndex;
	}

	public RecipeItem(int slotIndex, FluidStack fluid, int chance) {
		this.fluid = fluid;
		this.chance = chance;
		this.item = null;
		this.ore = null;
		this.index = slotIndex;
	}

	public RecipeItem(int slotIndex, OreStack ore, int chance) {
		this.fluid = null;
		this.item = null;
		this.chance = chance;
		this.ore = ore;
		this.index = slotIndex;
	}

	public RecipeItem(int slotIndex, ItemStack item, FluidStack fluid, OreStack ore, int chance) {
		this.fluid = fluid;
		this.item = item;
		this.ore = ore;
		this.index = slotIndex;
		this.chance = chance;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("slotIndex", this.index);
		nbt.setInteger("chance", this.chance);
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
		int slotIndex = nbt.getInteger("slotIndex");
		int chance = nbt.getInteger("chance");
		if (nbt.hasKey("item")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("item");
			item = ItemStack.loadItemStackFromNBT(nbtTag);
		} else if (nbt.hasKey("fluid")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("fluid");
			fluid = FluidStack.loadFluidStackFromNBT(nbtTag);
		} else if (nbt.hasKey("ore")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("ore");
			ore = OreStack.loadOreStackFromNBT(nbtTag);
		}
		return new RecipeItem(slotIndex, item, fluid, ore, chance);
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

	public boolean isNull() {
		return !isFluid() && !isItem() && !isOre();
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
		return new RecipeItem(index, item, fluid, ore, chance);
	}
}
