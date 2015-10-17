package nedelosk.modularmachines.common.modular.module.tool.producer.machine.furnace;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.machines.basic.SlotModular;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.common.modular.module.tool.producer.machine.ProducerMachine;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ProducerFurnace extends ProducerMachine {

	public ItemStack output;
	
	public ProducerFurnace() {
		super("Furnace");
	}
	
	public ProducerFurnace(String modifier) {
		super(modifier);
	}
	
	public ProducerFurnace(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super(nbt, modular, stack);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.writeToNBT(nbt, modular, stack);
		
		if (this.output != null){
			NBTTagList nbtTagList = new NBTTagList();
			NBTTagCompound item = new NBTTagCompound();
			this.output.writeToNBT(item);
			nbtTagList.appendTag(item);
			nbt.setTag("currentOutput", nbtTagList);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular, ModuleStack stack) {
		super.readFromNBT(nbt, modular, stack);
		
		if (nbt.hasKey("currentOutput")){
			NBTTagList nbtTagList = nbt.getTagList("currentOutput", 10);
			NBTTagCompound item = nbtTagList.getCompoundTagAt(0);
			this.output = ItemStack.loadItemStackFromNBT(item);
		}else{
			this.output = null;
		}
	}
	
	@Override
	public void update(IModular modular, ModuleStack stack) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		if(tile.getEnergyStored(null) > 0)
		{
		if(burnTime >= burnTimeTotal || burnTimeTotal == 0)
		{
			ItemStack input = tile.getModular().getInventoryManager().getStackInSlot(this.getName(stack), 0);
			if(output != null)
			{
				if(tile.getModular().getInventoryManager().addToOutput(output, 1, 2, this.getName(stack)))
					output = null;
			}
			else if(input != null)
			{
				ItemStack recipeOutput  = FurnaceRecipes.smelting().getSmeltingResult(input);
				if(recipeOutput != null)
				{
					output = recipeOutput.copy();
					tile.getModular().getInventoryManager().decrStackSize(this.getName(stack), 0, 1);
					if(burnTimeTotal == 0)
						burnTimeTotal = getBurnTimeTotal(modular, stack);
					burnTime = 0;
				}
			}
			if(timer > timerTotal)
			{
			tile.getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
			timer = 0;
			}
			else
			{
				timer++;
			}
		}
		else
		{
			if(timer > timerTotal)
			{
				tile.getWorldObj().markBlockForUpdate(modular.getMachine().getXCoord(), modular.getMachine().getYCoord(), modular.getMachine().getZCoord());
				timer = 0;
			}
			else
			{
				timer++;
			}
			
			if(modular.getManager().getEnergyHandler().extractEnergy(null, 10, false) > 0)
				burnTime++;
		}
		}
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular, ModuleStack stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 56, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 1, 116, 35, stack){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		return list;
	}

	@Override
	public int getSpeed(ModuleStack stack) {
		return 30;
	}

	@Override
	public int getSizeInventory(ModuleStack stack) {
		return 2;
	}

	@Override
	public ArrayList<NeiStack> addNEIStacks(ModuleStack stack) {
		return null;
	}

}