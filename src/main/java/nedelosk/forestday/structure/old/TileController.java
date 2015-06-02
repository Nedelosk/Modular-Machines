package nedelosk.forestday.structure.old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import scala.actors.threadpool.Arrays;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileController extends TileStructure{
	private int yMin, yMax, xMin, xMax, zMin, zMax;
	
	private Structure structure;
	private Hashtable<Short, Short> allowedElements; 
	private ArrayList<Short> allowedElementsInside; 
	
	private static final int ZYKLUS_TICKS_STRUCTURE_CONDITION = 100;
	private byte lastStructureConditionUpdate;
	
	public TileController(){
		super(Structure.UNKNOWN_ELEMENT);
	}
	
	public TileController(short structureControllerID){
		super(structureControllerID);
		try {
			this.structure = new Structure(structureControllerID);
			this.allowedElements = this.structure.getAllowedElements();
			this.allowedElementsInside = this.structure.getAllowedElementsInside();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		if (this.worldObj.isRemote)
			return;
		
		if (this.lastStructureConditionUpdate >= ZYKLUS_TICKS_STRUCTURE_CONDITION){
			boolean condition = this.verifiyStructureCondition();
			if (condition != this.getStructureCondition()){
				/* Condition changed, notify every part in this structure*/
				for(int y = this.yMin; y <= this.yMax; y++){
					for(int x = this.xMin; x <= this.xMax; x++){
						for(int z = this.zMin; z <= this.zMax; z++){
							TileEntity tileEntity = this.worldObj.getTileEntity(x, y, z);
							if (tileEntity != null && tileEntity instanceof TileStructure){
								((TileStructure)tileEntity).setStructureCondition(condition);
							}
						}
					}
				}
			}
			this.lastStructureConditionUpdate = 0;
		}else{
			this.lastStructureConditionUpdate++;
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		if (this.structure == null){
			try{	
				this.structure = new Structure(getStructurePartID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * Only the controller verify the condition of the structure!
	 * If the block will removed from the world, the function
	 * updateContainingBlockInfo is called by world, it will
	 * set the structure condition to false.
	 * @return 
	 */
	private boolean verifiyStructureCondition() {
		if (this.structure == null){
			System.err.println("Structure is null");
			return false;
		}
		
		if (!analyzeSize()) return false;
		
		/** 
		 * Structure check loop:
		 * 1. yMin to yMax
		 * 2. xMin to xMax
		 * 3. zMin to zMax
		 */
		for(int yCheck = yMin; yCheck <= yMax; yCheck++){
			for(int xCheck = xMin; xCheck <= xMax; xCheck++){
				for(int zCheck = zMin; zCheck <= zMax; zCheck++){
					TileEntity tileEntity = worldObj.getTileEntity(xCheck, yCheck, zCheck);
					if (tileEntity != null && tileEntity instanceof TileStructure){
						TileStructure tileEntityStructure = (TileStructure) tileEntity;
						short structureBlockID = tileEntityStructure.getStructurePartID();
						
						if (yCheck == yMin){
							/* Foundation*/
							if (structureBlockID != Structure.FOUNDATION){
								System.err.println(String.format("ErrFoundation at %d %d %d", xCheck, yCheck, zCheck)); 
								return false;
							}
						}else if (yCheck != yMin && ((xCheck == xMin && zCheck == zMin) || 
													(xCheck == xMin && zCheck == zMax) ||
													(xCheck == xMax && zCheck == zMin) ||
													(xCheck == xMax && zCheck == zMax))){
							//(xCheck == xMin || xCheck == xMax || zCheck == zMin || zCheck == zMax))
							/* Frame - Corner */
							if (structureBlockID != Structure.CASING_FULL){
								System.err.println(String.format("Frame-Corner id:%d at %d %d %d",structureBlockID, xCheck, yCheck, zCheck)); 
								return false;
							}
						}else if (yCheck == yMax && ((xCheck == xMin || xCheck == xMax) || (zCheck == zMin || zCheck == zMax))){
							/* Frame - Top */
							if (structureBlockID != Structure.CASING_FULL){
								System.err.println(String.format("Frame-Top id:%d at %d %d %d",structureBlockID, xCheck, yCheck, zCheck)); 
								return false;
							}
						}else if (((yCheck != yMin && yCheck != yMax) && (xCheck == xMin || xCheck == xMax)) || ((yCheck != yMin && yCheck != yMax) && (zCheck == zMin || zCheck == zMax))){
							/* Sides */
							if (!(structureBlockID != Structure.FOUNDATION && allowedElements.containsKey(structureBlockID))){
								System.err.println(String.format("Side id:%d at %d %d %d",structureBlockID, xCheck, yCheck, zCheck)); 
								return false;
							}
						}else if (yCheck == yMax && ((xCheck != xMin && xCheck != xMax) && zCheck != zMin && zCheck != zMax)){
							/* Top*/
							if (!(structureBlockID != Structure.FOUNDATION && allowedElements.containsKey(structureBlockID))){
								System.err.println(String.format("Top id:%d at %d %d %d",structureBlockID, xCheck, yCheck, zCheck)); 
								return false;
							}
						}else {
							/* Inside */
							if (!allowedElementsInside.contains(structureBlockID)){
								System.err.println(String.format("ErrInside at %d %d %d", xCheck, yCheck, zCheck)); 
								return false;
							}
						}		
					}else{
						if (yCheck > yMin && yCheck < yMax && xCheck > xMin && xCheck < xMax && zCheck > zMin && zCheck < zMax){
							/* Inside */
							System.err.println(String.format("ErrInside at %d %d %d", xCheck, yCheck, zCheck)); 
							if (!(tileEntity == null && structure.isSpaceInsideAllowed()))return false;
						}else{
							System.err.println(String.format("ErrEmptyNull at %d %d %d", xCheck, yCheck, zCheck)); 
							/* There is an empty or other TileEntity in the main-structure*/
							return false;
						}
						
					}		
				}
			}
		}
		return true;
	}
	
	/**
	 * Analyze the size of the structure outgoing of this TileEntity (the Controller)
	 * @return Is this size avaiable?
	 */
	private boolean analyzeSize(){

		/* Y-Axis*/
		this.yMin = this.yCoord;
		for(int y = this.yCoord; y >= 0; y--){
			if (this.isValidPart(this.xCoord, y, this.zCoord)){
				this.yMin = y;
			} else {
				break;
			}
		}
				
		this.yMax = this.yCoord;
		for(int y = this.yCoord; y <= worldObj.getActualHeight(); y++){
			if (this.isValidPart(this.xCoord, y, this.zCoord)){
				this.yMax = y;
			} else {
				break;
			}
		}
		
		int height = Math.abs(this.yMax-this.yMin+1);
		if (!(height >= this.structure.getMinHeight() && height <= this.structure.getMaxHeight())){
			System.err.println(String.format("Avaiable structure sizes are (width x width x height) %d-%dx%d-%dx%d-%d", this.structure.getMinWidth1(), this.structure.getMaxWidth1(), this.structure.getMinWidth2(), this.structure.getMaxWidth2(), this.structure.getMinHeight(), this.structure.getMaxHeight()));
			return false;
		}

		/* X-Axis*/
		this.xMin = this.xCoord;
		for(int x = this.xCoord; x >= this.xCoord-Structure.MAX_STRUCTURE_SIZE; x--){
			if (this.isValidPart(x, this.yMin, this.zCoord)){
				this.xMin = x;
			} else {
				break;
			}
		}
		
		this.xMax = this.xCoord;
		for(int x = this.xCoord; x <= this.xCoord+Structure.MAX_STRUCTURE_SIZE; x++){
			if (this.isValidPart(x, this.yMax, this.zCoord)){
				this.xMax = x;
			} else {
				break;
			}
		}
		
		/* Z-Axis*/
		this.zMin = this.zCoord;
		for(int z = this.zCoord; z >= this.zCoord-Structure.MAX_STRUCTURE_SIZE; z--){
			if (this.isValidPart(this.xCoord, this.yMin, z)){
				this.zMin = z;
			} else {
				break;
			}
		}
		
		this.zMax = this.zCoord;
		for(int z = this.zCoord; z <= this.zCoord+Structure.MAX_STRUCTURE_SIZE; z++){
			if (this.isValidPart(this.xCoord, yMax, z)){
				this.zMax = z;
			} else {
				break;
			}
		}
		
		/* Size X, Z*/
		int widthX = Math.abs(xMax-xMin+1);
    	int widthZ = Math.abs(zMax-zMin+1);
		
		/*
		 * There are two available width options:
		 * - widthX = width1 && widthZ = width2
		 * - widthZ = width1 && widthX = width2
		 */
		if ((widthX >= this.structure.getMinWidth1() && widthX <= this.structure.getMaxWidth1() && widthZ >= this.structure.getMinWidth2() && widthZ <= this.structure.getMaxWidth2()) || (widthZ >= this.structure.getMinWidth1() && widthZ <= this.structure.getMaxWidth1() && widthX >= this.structure.getMinWidth2() && widthX <= this.structure.getMaxWidth2())){
			return true;
		}else{
			System.err.println(String.format("Avaiable structure sizes are (width x width x height) %d-%dx%d-%dx%d-%d", this.structure.getMinWidth1(), this.structure.getMaxWidth1(), this.structure.getMinWidth2(), this.structure.getMaxWidth2(), this.structure.getMinHeight(), this.structure.getMaxHeight()));
			return false;
		}
	}

	
	/**
	 * Checks whether the TileEntity is a valid part of the structure.
	 * @param tileEntity TileEntiy which should be checked
	 * @return Is it a valid part?
	 */
	private boolean isValidPart(int x, int y, int z){
		TileEntity tileEntity = this.worldObj.getTileEntity(x, y, z);
		if (tileEntity != null && tileEntity instanceof TileStructure){
			TileStructure tileEntityStructure = (TileStructure) tileEntity;
			return (tileEntityStructure.isAvaiable() && this.allowedElements.containsKey(tileEntityStructure.getStructurePartID()));
		}else{
			return false;
		}
	}
	

}
