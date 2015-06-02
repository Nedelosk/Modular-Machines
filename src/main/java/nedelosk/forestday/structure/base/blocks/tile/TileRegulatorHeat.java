package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.structure.base.gui.GuiRegulatorHeat;
import nedelosk.forestday.structure.base.gui.container.ContainerRegulator;
import nedelosk.forestday.structure.base.gui.container.ContainerRegulatorHeat;
import nedelosk.forestday.structure.base.items.ItemCoilHeat;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TileRegulatorHeat extends TileRegulator {

	public TileRegulatorHeat(int maxHeat, String uid) {
		super(maxHeat, 8, "regulator" + uid);
	}

	protected int heatRegulator;
	protected int coilHeat;
	protected int heat;

	public int getHeatRegulator() {
		return heatRegulator;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(isStructure())
		{
			ItemStack coilSlot0 = this.getStackInSlot(0);
			ItemStack coilSlot1 = this.getStackInSlot(1);
			ItemStack coilSlot2 = this.getStackInSlot(2);
			ItemStack coilSlot3 = this.getStackInSlot(3);
			ItemStack coilSlot4 = this.getStackInSlot(4);
			ItemStack coilSlot5 = this.getStackInSlot(5);
			ItemStack coilSlot6 = this.getStackInSlot(6);
			ItemStack coilSlot7 = this.getStackInSlot(7);
			if(coilSlot0 != null && coilSlot1 != null && coilSlot2 != null && coilSlot3 != null && coilSlot4 != null && coilSlot5 != null && coilSlot6 != null && coilSlot7 != null)
			{
				if(coilSlot0.getItem() instanceof ItemCoilHeat && coilSlot1.getItem() instanceof ItemCoilHeat && coilSlot2.getItem() instanceof ItemCoilHeat && coilSlot3.getItem() instanceof ItemCoilHeat && coilSlot4.getItem() instanceof ItemCoilHeat && coilSlot5.getItem() instanceof ItemCoilHeat && coilSlot6.getItem() instanceof ItemCoilHeat && coilSlot7.getItem() instanceof ItemCoilHeat)
				{
						Item item0 = coilSlot0.getItem();
						Item item1 = coilSlot1.getItem();
						Item item2 = coilSlot2.getItem();
						Item item3 = coilSlot3.getItem();
						Item item4 = coilSlot4.getItem();
						Item item5 = coilSlot5.getItem();
						Item item6 = coilSlot6.getItem();
						Item item7 = coilSlot7.getItem();
						int meta0 = coilSlot0.getItemDamage();
						int meta1 = coilSlot1.getItemDamage();
						int meta2 = coilSlot2.getItemDamage();
						int meta3 = coilSlot3.getItemDamage();
						int meta4 = coilSlot4.getItemDamage();
						int meta5 = coilSlot5.getItemDamage();
						int meta6 = coilSlot6.getItemDamage();
						int meta7 = coilSlot7.getItemDamage();
						int heat0 = ((ItemCoilHeat)item0).getCoilHeat(meta0);
						int heat1 = ((ItemCoilHeat)item1).getCoilHeat(meta1);
						int heat2 = ((ItemCoilHeat)item2).getCoilHeat(meta2);
						int heat3 = ((ItemCoilHeat)item3).getCoilHeat(meta3);
						int heat4 = ((ItemCoilHeat)item4).getCoilHeat(meta4);
						int heat5 = ((ItemCoilHeat)item5).getCoilHeat(meta5);
						int heat6 = ((ItemCoilHeat)item6).getCoilHeat(meta6);
						int heat7 = ((ItemCoilHeat)item7).getCoilHeat(meta7);
						heatRegulator = 0;
						int chancheHeat;
						chancheHeat = heat0 + heat1  + heat2 + heat3 + heat4 + heat5 + heat6 + heat7;
						heatRegulator = chancheHeat / 8;
						
				}
			}
			else
			{
				heatRegulator = 0;
			}
		}
		else
		{
			heatRegulator = 0;
		}
		
	}
	
	public boolean isStructure()
	{
		return false;
		
	}

	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiRegulatorHeat(inventory, this);
	}
	
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerRegulatorHeat(inventory, this);
	}
	
	public int getCoilHeat() {
		return coilHeat;
	}
	
	public int getHeat() {
		return heat;
	}
	
	public int getCoilMaxHeat(){
		return 0;
	}
	
	@Override
	public String getMachineTileName() {
		return "structure.regulator.heat";
	}


	@Override
	public void updateClient() {
		
	}


	@Override
	public void updateServer() {
		
	}

}
