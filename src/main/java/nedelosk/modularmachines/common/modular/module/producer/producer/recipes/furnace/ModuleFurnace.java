package nedelosk.modularmachines.common.modular.module.producer.producer.recipes.furnace;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.machines.basic.IModularTileEntity;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.api.parts.PartType.MachinePartType;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.inventory.slots.SlotModuleMachine;
import nedelosk.modularmachines.common.modular.module.producer.producer.recipes.ModuleProducer;
import nedelosk.nedeloskcore.api.machines.IContainerBase;
import nedelosk.nedeloskcore.api.machines.IGuiBase;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ModuleFurnace extends ModuleProducer {

	public ItemStack output;
	
	public ModuleFurnace() {
		super("Furnace");
	}
	
	public ModuleFurnace(String modifier) {
		super(modifier);
	}
	
	public ModuleFurnace(NBTTagCompound nbt) {
		super(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if (this.output != null){
			NBTTagList nbtTagList = new NBTTagList();
			NBTTagCompound item = new NBTTagCompound();
			this.output.writeToNBT(item);
			nbtTagList.appendTag(item);
			nbt.setTag("currentOutput", nbtTagList);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("currentOutput")){
			NBTTagList nbtTagList = nbt.getTagList("currentOutput", 10);
			NBTTagCompound item = nbtTagList.getCompoundTagAt(0);
			this.output = ItemStack.loadItemStackFromNBT(item);
		}else{
			this.output = null;
		}
	}
	
	@Override
	public void update(IModular modular) {
		IModularTileEntity<IModularInventory> tile = modular.getMachine();
		if(tile.getEnergyStored(null) > 0)
		{
		if(burnTime >= burnTimeTotal || burnTimeTotal == 0)
		{
			ItemStack input = tile.getModular().getInventoryManager().getStackInSlot(this.getName(), 0);
			if(output != null)
			{
				if(tile.getModular().getInventoryManager().addToOutput(output, 1, 2, this.getName()))
					output = null;
			}
			else if(input != null)
			{
				ItemStack recipeOutput  = FurnaceRecipes.smelting().getSmeltingResult(input);
				if(recipeOutput != null)
				{
					output = recipeOutput.copy();
					tile.getModular().getInventoryManager().decrStackSize(this.getName(), 0, 1);
					if(burnTimeTotal == 0)
						burnTimeTotal = getBurnTimeTotal(modular);
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
	public String getModuleName() {
		return "Producer";
	}

	@Override
	public ArrayList<Slot> addSlots(IContainerBase container, IModular modular) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModuleMachine(modular.getMachine(), 0, 56, 35, this.getName()));
		list.add(new SlotModuleMachine(modular.getMachine(), 1, 116, 35, this.getName()){
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
		});
		return list;
	}

	@Override
	public void addButtons(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public void addWidgets(IGuiBase gui, IModular modular) {
		
	}

	@Override
	public int getSpeedModifier() {
		return 30;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public PartType[] getRequiredComponents() {
		return new PartType[]{new MachinePartType(ItemRegistry.Burning_Chamber),
				  new MachinePartType(ItemRegistry.Module) };
	}

	@Override
	public ModuleStack creatModule(ItemStack stack) {
		return null;
	}

}
