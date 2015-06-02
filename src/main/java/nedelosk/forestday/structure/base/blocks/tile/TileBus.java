package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipe;
import nedelosk.forestday.structure.alloysmelter.crafting.AlloySmelterRecipeManager;
import nedelosk.forestday.structure.base.gui.GuiBusItem;
import nedelosk.forestday.structure.base.gui.container.ContainerBusItem;
import nedelosk.forestday.structure.base.items.ItemBus;
import nedelosk.forestday.structure.blastfurnace.crafting.BlastFurnaceRecipe;
import nedelosk.forestday.structure.blastfurnace.crafting.BlastFurnaceRecipeManager;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipe;
import nedelosk.forestday.structure.macerator.crafting.MaceratorRecipeManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public abstract class TileBus extends TileStructureInventory{
	
	public TileBus(int heat, String uid) {
		super(heat, 5, "bus" + uid);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
    	if (this.worldObj.isRemote)return;
    	
    	this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    	
    	ItemStack stackSlotMode = this.getStackInSlot(4);
    	
    	if(stackSlotMode != null && stackSlotMode.getItem() instanceof ItemBus)
    	{
    		if(stackSlotMode.getItemDamage() == 0)
    		{
    			setBusInput();
    		}
    		if(stackSlotMode.getItemDamage() == 1)
    		{
    			setBusOutput();	
    		}
    	}
	}
	
	protected Mode mode;
	protected Structures structure;
	
	public Mode getMode()
	{
		return this.mode;
	}
	
	public enum Mode
	{
		Input, Output;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(mode != null)
		{
		nbt.setString("Mode", this.mode.name());
		}
		if(structure != null)
		{
			nbt.setString("Structure", this.structure.name());
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.mode = nbt.getString("Mode") == Mode.Input.name() ? Mode.Input : Mode.Output;
		this.structure = nbt.getString("Structure") == Structures.Air_Heater.name() ? Structures.Air_Heater : nbt.getString("Structure") == Structures.Alloy_Smelter.name() ? Structures.Alloy_Smelter : nbt.getString("Structure") == Structures.Blast_Furnace.name() ? Structures.Blast_Furnace : nbt.getString("Structure") == Structures.Macerator.name() ? Structures.Macerator : nbt.getString("Structure") == Structures.Plant_Infuser.name() ? Structures.Plant_Infuser : Structures.None;
	}
	
	public TileEntity setBusOutput()
	{
		mode = Mode.Output;
		return this;
	}
	
	public TileEntity setBusInput()
	{
		mode = Mode.Input;
		return this;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		/*if(structure != null )
		{
			if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord) instanceof TileBusItem)
			{
			if(structure == Structures.Blast_Furnace)
			{
				switch (slot) {
				case 0:
					for(BlastFurnaceRecipe recipe : BlastFurnaceRecipeManager.getRecipe())
					{
						return stack.getItem() == recipe.getInput()[0].getItem() ? stack.getItemDamage() == recipe.getInput()[0].getItemDamage() : false;
					}
				case 1:
					for(BlastFurnaceRecipe recipe : BlastFurnaceRecipeManager.getRecipe())
					{
						return stack.getItem() == recipe.getInput()[1].getItem() ? stack.getItemDamage() == recipe.getInput()[1].getItemDamage() : false;
					}
				case 2:
					for(BlastFurnaceRecipe recipe : BlastFurnaceRecipeManager.getRecipe())
					{
						return stack.getItem() == recipe.getInput()[2].getItem() ? stack.getItemDamage() == recipe.getInput()[2].getItemDamage() : false;
					}
				case 3:
					for(BlastFurnaceRecipe recipe : BlastFurnaceRecipeManager.getRecipe())
					{
						return stack.getItem() == recipe.getInput()[3].getItem() ? stack.getItemDamage() == recipe.getInput()[3].getItemDamage() : false;
					}
				default:
					return false;
				}
			}
			else if(structure == Structures.Alloy_Smelter)
			{
				switch (slot) {
				case 0:
					for(AlloySmelterRecipe recipe : AlloySmelterRecipeManager.getRecipe())
					{
						return /*stack.getItem() == recipe.getInput1().getItem() ? stack.getItemDamage() == recipe.getInput1().getItemDamage() : true;
					}
				case 1:
					for(AlloySmelterRecipe recipe : AlloySmelterRecipeManager.getRecipe())
					{
						return /*stack.getItem() == recipe.getInput2().getItem() ? stack.getItemDamage() == recipe.getInput2().getItemDamage() : true;
					}
				default:
					return false;
				}
			}
			else if(structure == Structures.Macerator)
			{
				switch (slot) {
				case 0:
					return MaceratorRecipeManager.getRecipe(stack) != null;
				default:
					return false;
				}
			}
		}
		}
		return false;*/
		return true;
	}
	
	public void setStructure(Structures structure) {
		this.structure = structure;
	}
	
	public Structures getStructure() {
		return structure;
	}
	
}
