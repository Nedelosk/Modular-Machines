package nedelosk.forestday.structure.blastfurnace.blocks.tile;

import nedelosk.forestday.api.structure.tile.ITileBlastFurnace;
import nedelosk.forestday.structure.airheater.gui.GuiAirHeaterController;
import nedelosk.forestday.structure.base.blocks.tile.TileController;
import nedelosk.forestday.structure.blastfurnace.gui.GuiBlastFurnaceController;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileBlastFurnaceController extends TileController implements ITileBlastFurnace {

	public TileBlastFurnaceController() {
		super(1000, "BlastFurnace");
	}
	
	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiBlastFurnaceController(inventory, this);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
    	if (this.worldObj.isRemote)return;
    	
    	this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    	if(time < 15)
    	{
    		time++;
    	}else{
    	getRegulatorData();
    	time = 0;
    	}
	}
	
	private int heatCoilmax;
	private int heatCoil;
	private int heatRegulator;
	private int heat;
	private int time;
	
	public void getRegulatorData()
	{
		TileEntity tile0 = this.worldObj.getTileEntity(this.xCoord + 2, this.yCoord - 1, this.zCoord);
		TileEntity tile1 = this.worldObj.getTileEntity(this.xCoord - 2, this.yCoord - 1, this.zCoord);
		TileEntity tile2 = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord - 2);
		TileEntity tile3 = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord + 2);
		if(tile0 instanceof TileBlastFurnaceRegulator)
		{
			heat = ((TileBlastFurnaceRegulator)tile0).getHeat();
			heatRegulator = ((TileBlastFurnaceRegulator)tile0).getHeatRegulator();
			heatCoil = ((TileBlastFurnaceRegulator)tile0).getCoilHeat();
			heatCoilmax = ((TileBlastFurnaceRegulator)tile0).getCoilMaxHeat();
		}
		else if(tile1 instanceof TileBlastFurnaceRegulator)
		{
			heat = ((TileBlastFurnaceRegulator)tile1).getHeat();
			heatRegulator = ((TileBlastFurnaceRegulator)tile1).getHeatRegulator();
			heatCoil = ((TileBlastFurnaceRegulator)tile1).getCoilHeat();
			heatCoilmax = ((TileBlastFurnaceRegulator)tile1).getCoilMaxHeat();
		}
		else if(tile2 instanceof TileBlastFurnaceRegulator)
		{
			heat = ((TileBlastFurnaceRegulator)tile2).getHeat();
			heatRegulator = ((TileBlastFurnaceRegulator)tile2).getHeatRegulator();
			heatCoil = ((TileBlastFurnaceRegulator)tile2).getCoilHeat();
			heatCoilmax = ((TileBlastFurnaceRegulator)tile2).getCoilMaxHeat();
		}
		else if(tile3 instanceof TileBlastFurnaceRegulator)
		{
			heat = ((TileBlastFurnaceRegulator)tile3).getHeat();
			heatRegulator = ((TileBlastFurnaceRegulator)tile3).getHeatRegulator();
			heatCoil = ((TileBlastFurnaceRegulator)tile3).getCoilHeat();
			heatCoilmax = ((TileBlastFurnaceRegulator)tile3).getCoilMaxHeat();
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
