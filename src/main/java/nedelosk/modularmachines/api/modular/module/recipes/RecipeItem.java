package nedelosk.modularmachines.api.modular.module.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class RecipeItem {

	public final FluidStack fluid;
	public final ItemStack item;
	
	public RecipeItem(ItemStack item) {
		this.item = item;
		this.fluid = null;
	}
	
	public RecipeItem(FluidStack fluid) {
		this.fluid = fluid;
		this.item = null;
	}
	
	public RecipeItem(ItemStack item, FluidStack fluid) {
		this.fluid = fluid;
		this.item = item;
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		if(item != null)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			item.writeToNBT(nbtTag);
			nbt.setTag("item", nbtTag);
		}else if(fluid != null){
			NBTTagCompound nbtTag = new NBTTagCompound();
			fluid.writeToNBT(nbtTag);
			nbt.setTag("fluid", nbtTag);
		}
	}
	
	public static RecipeItem readFromNBT(NBTTagCompound nbt)
	{
		ItemStack item = null;
		FluidStack fluid = null;
		if(nbt.hasKey("item"))
		{
			NBTTagCompound nbtTag = nbt.getCompoundTag("item");
			item = ItemStack.loadItemStackFromNBT(nbtTag);
		}
		else if(nbt.hasKey("fluid"))
		{
			NBTTagCompound nbtTag = nbt.getCompoundTag("fluid");
			fluid = FluidStack.loadFluidStackFromNBT(nbtTag);
		}
		return new RecipeItem(item, fluid);
	}
	
	public boolean isFluid()
	{
		return fluid != null;
	}
	
}
