package nedelosk.forestday.structure.macerator.blocks.tile;

import nedelosk.forestday.api.structure.tile.ITileAlloySmelter;
import nedelosk.forestday.api.structure.tile.ITileMacerator;
import nedelosk.forestday.structure.base.blocks.tile.TileController;
import nedelosk.forestday.structure.base.gui.GuiController;
import nedelosk.forestday.structure.base.gui.container.ContainerController;
import nedelosk.forestday.structure.macerator.gui.GuiMaceratorController;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileMaceratorController extends TileController implements ITileMacerator {

	public TileMaceratorController() {
		super(1000, "maceratorRegulator");
	}
	
	private int time;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
    	if (this.worldObj.isRemote)return;
    	
    	this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    	if(time < 3)
    	{
    		time++;
    	}else{
    	getRegulatorData();
    	time = 0;
    	}
	}
	
	private int roughnessCoilmax;
	private int roughnessCoil;
	private int roughnessRegulator;
	private int roughness;
	private int burnTime;
	private int burnTimeTotal;
	
	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiMaceratorController(inventory, this);
	}
	
	public void getRegulatorData()
	{
		TileEntity tile0 = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord - 1, this.zCoord);
		TileEntity tile1 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord - 1, this.zCoord);
		TileEntity tile2 = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord - 1);
		TileEntity tile3 = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord + 1);
		if(tile0 instanceof TileMaceratorRegulator)
		{
			roughness = ((TileMaceratorRegulator)tile0).getRoughness();
			roughnessRegulator = ((TileMaceratorRegulator)tile0).getRoughnessRegulator();
			roughnessCoil = ((TileMaceratorRegulator)tile0).getCoilRoughness();
			roughnessCoilmax = ((TileMaceratorRegulator)tile0).getCoilMaxRoughness();
			burnTime = ((TileMaceratorRegulator)tile0).getProgressTime();
			burnTimeTotal = ((TileMaceratorRegulator)tile0).getProgressTimeTotal();
		}
		else if(tile1 instanceof TileMaceratorRegulator)
		{
			roughness = ((TileMaceratorRegulator)tile1).getRoughness();
			roughnessRegulator = ((TileMaceratorRegulator)tile1).getRoughnessRegulator();
			roughnessCoil = ((TileMaceratorRegulator)tile1).getCoilRoughness();
			roughnessCoilmax = ((TileMaceratorRegulator)tile1).getCoilMaxRoughness();
			burnTime = ((TileMaceratorRegulator)tile1).getProgressTime();
			burnTimeTotal = ((TileMaceratorRegulator)tile1).getProgressTimeTotal();
		}
		else if(tile2 instanceof TileMaceratorRegulator)
		{
			roughness = ((TileMaceratorRegulator)tile2).getRoughness();
			roughnessRegulator = ((TileMaceratorRegulator)tile2).getRoughnessRegulator();
			roughnessCoil = ((TileMaceratorRegulator)tile2).getCoilRoughness();
			roughnessCoilmax = ((TileMaceratorRegulator)tile2).getCoilMaxRoughness();
			burnTime = ((TileMaceratorRegulator)tile2).getProgressTime();
			burnTimeTotal = ((TileMaceratorRegulator)tile2).getProgressTimeTotal();
		}
		else if(tile3 instanceof TileMaceratorRegulator)
		{
			roughness = ((TileMaceratorRegulator)tile3).getRoughness();
			roughnessRegulator = ((TileMaceratorRegulator)tile3).getRoughnessRegulator();
			roughnessCoil = ((TileMaceratorRegulator)tile3).getCoilRoughness();
			roughnessCoilmax = ((TileMaceratorRegulator)tile3).getCoilMaxRoughness();
			burnTime = ((TileMaceratorRegulator)tile3).getProgressTime();
			burnTimeTotal = ((TileMaceratorRegulator)tile3).getProgressTimeTotal();
		}
	}
	
	public int getRoughness() {
		return roughness;
	}
	
	public int getRoughnessCoil() {
		return roughnessCoil;
	}
	
	public int getRoughnessCoilmax() {
		return roughnessCoilmax;
	}
	
	public int getRoughnessRegulator() {
		return roughnessRegulator;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("roughness", roughness);
		nbt.setInteger("roughnessCoil", roughnessCoil);
		nbt.setInteger("roughnessCoilMax", roughnessCoilmax);
		nbt.setInteger("roughnessRegulator", roughnessRegulator);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("burnTimeTotal", burnTimeTotal);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.roughness = nbt.getInteger("roughness");
		this.roughnessCoil = nbt.getInteger("roughnessCoil");
		this.roughnessCoilmax = nbt.getInteger("roughnessCoilMax");
		this.roughnessRegulator = nbt.getInteger("roughnessRegulator");
		this.burnTime = nbt.getInteger("burnTime");
		this.burnTimeTotal = nbt.getInteger("burnTimeTotal");
	}
	
	public int getScaledProcess(int maxWidth) {
		return (this.burnTime > 0) ? (this.burnTime * maxWidth) / this.burnTimeTotal : 0;
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
