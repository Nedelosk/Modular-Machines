package nedelosk.forestday.structure.old;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileStructure extends Tile
{
	/* If the structure is complete it is set to true*/
	private boolean structureCondition;
	
	/* The part id from this TileEntity, see ZStructures*/
	private short structurePartID;

	public TileStructure(short structurePartID){
		this.structureCondition = false;
		this.structurePartID = structurePartID;
	}
	
	@Override
	public void updateEntity(){
		if (this.worldObj.isRemote)
			return;
		super.updateEntity();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setBoolean("structureCondition", this.structureCondition);
		nbt.setShort("structurePartID", this.structurePartID);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		this.structureCondition = nbt.getBoolean("structureCondition");
		this.structurePartID = nbt.getShort("structurePartID");
	}
	
	/**
	 * @return Structure condition is complete?
	 */
	public boolean getStructureCondition(){
		return this.structureCondition;
	}
	
	/**
	 * @return Is TileEntity available for a structure, or
	 * is it used by another structure?
	 */
	public boolean isAvaiable(){
		return true;//!this.structureComplete;
	}
	
	/**
	 * @return Returns the ZStructure BlockID of this TileEntity
	 */
	public short getStructurePartID(){
		return this.structurePartID;
	}
	
	/**
	 * Updates the structure condition
	 * @param structureCondition Is the structure complete?
	 */
	public void setStructureCondition(boolean structureCondition){
		this.structureCondition = structureCondition;
	}
	
	/**
	 * Called when the block is removed from the world.
	 */
	@Override
	public void updateContainingBlockInfo(){
		this.setStructureCondition(false); 
		super.updateContainingBlockInfo();
	}

}
