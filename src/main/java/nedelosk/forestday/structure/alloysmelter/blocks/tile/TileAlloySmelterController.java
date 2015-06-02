package nedelosk.forestday.structure.alloysmelter.blocks.tile;

import nedelosk.forestday.api.structure.tile.ITileAlloySmelter;
import nedelosk.forestday.structure.alloysmelter.gui.GuiAlloySmelterController;
import nedelosk.forestday.structure.base.blocks.tile.TileController;
import nedelosk.forestday.structure.base.gui.GuiController;
import nedelosk.forestday.structure.base.gui.container.ContainerController;
import nedelosk.forestday.structure.macerator.blocks.tile.TileMaceratorRegulator;
import nedelosk.forestday.structure.macerator.gui.GuiMaceratorController;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileAlloySmelterController extends TileController implements ITileAlloySmelter {

	public TileAlloySmelterController() {
		super(1000, "alloyController");
	}
	
	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiAlloySmelterController(inventory, this);
	}
	
	private int heatCoilmax;
	private int heatCoil;
	private int heatRegulator;
	private int heat;
	private int time;
	
	public void getRegulatorData()
	{
		TileEntity tile0 = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord - 1, this.zCoord);
		TileEntity tile1 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord - 1, this.zCoord);
		TileEntity tile2 = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord - 1);
		TileEntity tile3 = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord + 1);
		if(tile0 instanceof TileAlloySmelterRegulator)
		{
			heat = ((TileAlloySmelterRegulator)tile0).getHeat();
			heatRegulator = ((TileAlloySmelterRegulator)tile0).getHeatRegulator();
			heatCoil = ((TileAlloySmelterRegulator)tile0).getCoilHeat();
			heatCoilmax = ((TileAlloySmelterRegulator)tile0).getCoilMaxHeat();
		}
		else if(tile1 instanceof TileAlloySmelterRegulator)
		{
			heat = ((TileAlloySmelterRegulator)tile1).getHeat();
			heatRegulator = ((TileAlloySmelterRegulator)tile1).getHeatRegulator();
			heatCoil = ((TileAlloySmelterRegulator)tile1).getCoilHeat();
			heatCoilmax = ((TileAlloySmelterRegulator)tile1).getCoilMaxHeat();
		}
		else if(tile2 instanceof TileAlloySmelterRegulator)
		{
			heat = ((TileAlloySmelterRegulator)tile2).getHeat();
			heatRegulator = ((TileAlloySmelterRegulator)tile2).getHeatRegulator();
			heatCoil = ((TileAlloySmelterRegulator)tile2).getCoilHeat();
			heatCoilmax = ((TileAlloySmelterRegulator)tile2).getCoilMaxHeat();
		}
		else if(tile3 instanceof TileAlloySmelterRegulator)
		{
			heat = ((TileAlloySmelterRegulator)tile3).getHeat();
			heatRegulator = ((TileAlloySmelterRegulator)tile3).getHeatRegulator();
			heatCoil = ((TileAlloySmelterRegulator)tile3).getCoilHeat();
			heatCoilmax = ((TileAlloySmelterRegulator)tile3).getCoilMaxHeat();
		}
	}
	
	public int getHeat() {
		return heat;
	}
	
	public int getHeatCoil() {
		return heatCoil;
	}
	
	public int getHeatCoilmax() {
		return heatCoilmax;
	}
	
	public int getHeatRegulator() {
		return heatRegulator;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("heat", heat);
		nbt.setInteger("heatCoil", heatCoil);
		nbt.setInteger("heatCoilMax", heatCoilmax);
		nbt.setInteger("heatRegulator", heatRegulator);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.heat = nbt.getInteger("heat");
		this.heatCoil = nbt.getInteger("heatCoil");
		this.heatCoilmax = nbt.getInteger("heatCoilMax");
		this.heatRegulator = nbt.getInteger("heatRegulator");
	}

	@Override
	public String getMachineTileName() {
		return "structure.controller.alloysmelter";
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
    	this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    	if(time < 15)
    	{
    		time++;
    	}else{
    	getRegulatorData();
    	time = 0;
    	}
	}

}
