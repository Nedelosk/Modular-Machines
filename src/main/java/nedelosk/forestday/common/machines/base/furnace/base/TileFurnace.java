package nedelosk.forestday.common.machines.base.furnace.base;

import nedelosk.forestday.client.machines.base.gui.GuiFurnace;
import nedelosk.forestday.common.machines.base.tile.TileHeatBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileFurnace extends TileHeatBase {
	
	public ItemStack output;
	
	public TileFurnace() {
		super(2);
	}

	@Override
	public String getMachineName() {
		return "furnace";
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerFurnace(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiFurnace(inventory, this);
	}

	@Override
	public void updateClient() {
		
	}
	
	@Override
	public void updateServer() {
		super.updateServer();
		if(heat >= 125)
		{
		if(burnTime> burnTimeTotal || burnTimeTotal == 0)
		{
			ItemStack input = getStackInSlot(0);
			if(output != null)
			{
				if(addToOutput(output, 1, 2))
					output = null;
			}
			else if(input != null)
			{
				ItemStack recipeOutput  = FurnaceRecipes.smelting().getSmeltingResult(input);
				if(recipeOutput != null)
				{
					output = recipeOutput.copy();
					decrStackSize(0, 1);
					burnTimeTotal = 200 / ((heat > 200) ? ((heat > 250) ? 3: 2) : ((heat > 250) ? 2: 1));
					burnTime = 0;
				}
			}
			if(timer > timerMax)
			{
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			else
			{
				timer++;
			}
		}
		else
		{
			if(timer > timerMax)
			{
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			else
			{
				timer++;
			}
			burnTime++;
		}
		}
	}

	public int getScaledHeat(int maxWidth) {
		return (this.heat > 0) ? (this.heat * maxWidth) / 125 : 0 ;
	}

}
