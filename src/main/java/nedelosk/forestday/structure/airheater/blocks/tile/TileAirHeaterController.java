package nedelosk.forestday.structure.airheater.blocks.tile;

import nedelosk.forestday.api.structure.tile.ITileAirHeater;
import nedelosk.forestday.api.structure.tile.ITileAlloySmelter;
import nedelosk.forestday.structure.airheater.gui.GuiAirHeaterController;
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

public class TileAirHeaterController extends TileController implements ITileAirHeater {

	public TileAirHeaterController() {
		super(1000, "AirHeater");
	}
	
	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiAirHeaterController(inventory, this);
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
		if(tile0 instanceof TileAirHeaterRegulator)
		{
			heat = ((TileAirHeaterRegulator)tile0).getHeat();
			heatRegulator = ((TileAirHeaterRegulator)tile0).getHeatRegulator();
			heatCoil = ((TileAirHeaterRegulator)tile0).getCoilHeat();
			heatCoilmax = ((TileAirHeaterRegulator)tile0).getCoilMaxHeat();
		}
		else if(tile1 instanceof TileAirHeaterRegulator)
		{
			heat = ((TileAirHeaterRegulator)tile1).getHeat();
			heatRegulator = ((TileAirHeaterRegulator)tile1).getHeatRegulator();
			heatCoil = ((TileAirHeaterRegulator)tile1).getCoilHeat();
			heatCoilmax = ((TileAirHeaterRegulator)tile1).getCoilMaxHeat();
		}
		else if(tile2 instanceof TileAirHeaterRegulator)
		{
			heat = ((TileAirHeaterRegulator)tile2).getHeat();
			heatRegulator = ((TileAirHeaterRegulator)tile2).getHeatRegulator();
			heatCoil = ((TileAirHeaterRegulator)tile2).getCoilHeat();
			heatCoilmax = ((TileAirHeaterRegulator)tile2).getCoilMaxHeat();
		}
		else if(tile3 instanceof TileAirHeaterRegulator)
		{
			heat = ((TileAirHeaterRegulator)tile3).getHeat();
			heatRegulator = ((TileAirHeaterRegulator)tile3).getHeatRegulator();
			heatCoil = ((TileAirHeaterRegulator)tile3).getCoilHeat();
			heatCoilmax = ((TileAirHeaterRegulator)tile3).getCoilMaxHeat();
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
