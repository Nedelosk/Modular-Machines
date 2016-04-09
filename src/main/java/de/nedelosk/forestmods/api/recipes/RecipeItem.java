package de.nedelosk.forestmods.api.recipes;

import de.nedelosk.forestcore.utils.OreStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class RecipeItem {

	public final FluidStack fluid;
	public final ItemStack item;
	public final OreStack ore;
	public int index;

	public RecipeItem(ItemStack item) {
		this.item = item;
		this.fluid = null;
		this.ore = null;
		this.index = 0;
	}

	public RecipeItem(FluidStack fluid) {
		this.fluid = fluid;
		this.item = null;
		this.ore = null;
		this.index = 0;
	}

	public RecipeItem(OreStack ore) {
		this.fluid = null;
		this.item = null;
		this.ore = ore;
		this.index = 0;
	}

	public RecipeItem(int slotIndex, ItemStack item) {
		this.item = item;
		this.fluid = null;
		this.ore = null;
		this.index = slotIndex;
	}

	public RecipeItem(int slotIndex, FluidStack fluid) {
		this.fluid = fluid;
		this.item = null;
		this.ore = null;
		this.index = slotIndex;
	}

	public RecipeItem(int slotIndex, OreStack ore) {
		this.fluid = null;
		this.item = null;
		this.ore = ore;
		this.index = slotIndex;
	}

	public RecipeItem(int slotIndex, ItemStack item, FluidStack fluid, OreStack ore) {
		this.fluid = fluid;
		this.item = item;
		this.ore = ore;
		this.index = slotIndex;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("slotIndex", this.index);
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

	public static RecipeItem readFromNBT(NBTTagCompound nbt) {
		ItemStack item = null;
		FluidStack fluid = null;
		OreStack ore = null;
		int slotIndex = nbt.getInteger("slotIndex");
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
		return new RecipeItem(slotIndex, item, fluid, ore);
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
}
