package modularmachines.api.ingredients;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import net.minecraftforge.oredict.OreDictionary;

public final class OreStack {
	private final int oreID;
	private final int amount;
	
	public OreStack(NBTTagCompound nbt) {
		this(nbt.getString("ore"), nbt.getInteger("amount"));
	}
	
	public OreStack(String oreDict) {
		this.oreID = OreDictionary.getOreID(oreDict);
		this.amount = 1;
	}
	
	public OreStack(int oreID, int stackSize) {
		this.oreID = oreID;
		this.amount = stackSize;
	}
	
	public OreStack(String oreDict, int stackSize) {
		this.oreID = OreDictionary.getOreID(oreDict);
		this.amount = stackSize;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public String getOreName() {
		return OreDictionary.getOreName(oreID);
	}
	
	public int getOreID() {
		return oreID;
	}
	
	public NonNullList<ItemStack> getOres() {
		return OreDictionary.getOres(getOreName());
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("ore", OreDictionary.getOreName(oreID));
		nbt.setInteger("amount", amount);
	}
	
	@Override
	public int hashCode() {
		return ((31) + oreID) * 31 + amount;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof OreStack)) {
			return false;
		}
		OreStack otherStack = (OreStack) obj;
		return otherStack.oreID == oreID && otherStack.amount == amount;
	}
}
