package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.structure.base.gui.GuiRegulatorGrinding;
import nedelosk.forestday.structure.base.gui.GuiRegulatorHeat;
import nedelosk.forestday.structure.base.gui.container.ContainerRegulator;
import nedelosk.forestday.structure.base.gui.container.ContainerRegulatorGrinding;
import nedelosk.forestday.structure.base.items.ItemCoilGrinding;
import nedelosk.forestday.structure.base.items.ItemCoilHeat;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TileRegulatorGrinding extends TileRegulator {

	public TileRegulatorGrinding(int maxHeat, String uid) {
		super(maxHeat, 8, "regulator" + uid);
	}

	protected int roughnessRegulator;
	protected int coilRoughness;
	protected int roughness;

	public int getRoughnessRegulator() {
		return roughnessRegulator;
	}
	
	
	public boolean isStructure()
	{
		return false;
		
	}

	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiRegulatorGrinding(inventory, this);
	}
	
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerRegulatorGrinding(inventory, this);
	}
	
	public int getCoilRoughness() {
		return coilRoughness;
	}
	
	public int getRoughness() {
		return roughness;
	}
	
	public int getCoilMaxRoughness(){
		return 0;
		}


	@Override
	public String getMachineTileName() {
		return "structure.regulator.grinding";
	}


	@Override
	public void updateClient() {
		
	}


	@Override
	public void updateServer() {
		
	}

}
