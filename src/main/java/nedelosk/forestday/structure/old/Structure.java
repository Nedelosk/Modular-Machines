package nedelosk.forestday.structure.old;

import java.util.ArrayList;
import java.util.Hashtable;

import nedelosk.nedeloskcore.common.core.Log;

public class Structure {
	public static final short MAX_STRUCTURE_SIZE = 50;
	
	public static final short REQVALUE_SPECIAL_TREATMENT = -2;
	public static final short REQVALUE_VARIABLE = -1;
	
	public static final short UNKNOWN_ELEMENT = -1;
	
	public static final short FOUNDATION = 0;
	public static final short CASING_FULL = 1;
	public static final short CASING_GLAS = 2;
	public static final short FLUIDPORT = 3;
	public static final short POWERSUPPLY = 4;
	
	
	public static final short CONTROLLER_STEAMGENERATOR = 100;
	
		
	public static final short STEAMGENERATOR_COREPIPE = 101;
	

	private short controllerID;
	public Structure(short controllerID) throws Exception{
		this.controllerID = controllerID;
		if (!this.isStructureAvaiable(controllerID))throw new Exception(String.format("Structure id [%d] not avaiable", this.controllerID));
	}
	
	/**
	 * The structure can be a rectangle with different width's.
	 * @return Minimum width of the first side
	 */
	public short getMinWidth1(){
		switch(this.controllerID){
			case CONTROLLER_STEAMGENERATOR:
				return 4;
			default:
				Log.err(String.format("Structure id [%d] missing in getMinWidth1 - please write a bug report", this.controllerID)); 
				return 0;
		}
	}
	
	/**
	 * The structure can be a rectangle with different width's.
	 * @return Maximal width of the first side
	 */
	public short getMaxWidth1()
	{
		switch(this.controllerID)
		{
			case CONTROLLER_STEAMGENERATOR:
				return 12;
			default:
				Log.err(String.format("Structure id [%d] missing in getMaxWidth1 - please write a bug report", this.controllerID)); 
				return 0;
		}
	}
	
	/**
	 * The structure can be a rectangle with different width's.
	 * @return Minimum width of the first side
	 */
	public short getMinWidth2()
	{
		switch(this.controllerID)
		{
			case CONTROLLER_STEAMGENERATOR:
				return 4;
			default:
				Log.err(String.format("Structure id [%d] missing in getMinWidth2 - please write a bug report", this.controllerID)); 
				return 0;
		}
	}
	
	/**
	 * The structure can be a rectangle with different width's.
	 * @return Maximal width of the second side
	 */
	public short getMaxWidth2()
	{
		switch(this.controllerID)
		{
			case CONTROLLER_STEAMGENERATOR:
				return 6;
			default:
				Log.err(String.format("Structure id [%d] missing in getMaxWidth2 - please write a bug report", this.controllerID)); 
				return 0;
		}
	}
	
	/**
	 * The structure needs a consistent height.
	 * @return Minimal height
	 */
	public short getMinHeight()
	{
		switch(this.controllerID)
		{
			case CONTROLLER_STEAMGENERATOR:
				return 3;
			default:
				Log.err(String.format("Structure id [%d] missing in getMinHeight - please write a bug report", this.controllerID)); 
				return 0;
		}
	}
	
	/**
	 * The structure needs a consistent height.
	 * @return Maximal height
	 */
	public short getMaxHeight()
	{
		switch(this.controllerID)
		{
			case CONTROLLER_STEAMGENERATOR:
				return 9;
			default:
				Log.err(String.format("Structure id [%d] missing in getMaxHeight - please write a bug report", this.controllerID)); 
				return 0;
		}
	}
	
	/**
	 * Some structures requires elements inside the structure.
	 * @return Array with allowed ZStructure ID's inside the structure.
	 * If no elements are allowed inside it returns null.
	 */
	public ArrayList<Short> getAllowedElementsInside()
	{
		ArrayList<Short> result = new ArrayList();
		switch(this.controllerID)
		{
			case CONTROLLER_STEAMGENERATOR:
				result.add(STEAMGENERATOR_COREPIPE);
			default:
		}
		return result;
	}
	
	
	/** 
	 * @return Are empty blocks inside the structure allowed?
	 */
	public boolean isSpaceInsideAllowed()
	{
		switch(this.controllerID)
		{
			case CONTROLLER_STEAMGENERATOR:
				return false;
			default:
				return true;
		}
	}
	
	/**
	 * Structured requiring some fixed elements.
	 * Some may only occur once, others several times.
	 * @return The key is the ZStrucure block id which is required.
	 * The value the count the block should be placed in the structure. 
	 * If a block can be placed as many as the player want, but required are
	 * at least 1, its containing -1;
	 * If the value is -2, its a structure size dependent value,
	 * it requires a special treatment in the TileEntity.
	 */
	public Hashtable<Short, Short> getAllowedElements()
	{
		Hashtable<Short, Short> result = new Hashtable();
		
		/* The structure requires always a foundation and a 
		 * frame of full-casings except the bottom side*/
		result.put(FOUNDATION, REQVALUE_SPECIAL_TREATMENT);
		result.put(CASING_FULL, REQVALUE_SPECIAL_TREATMENT);
		result.put(CASING_GLAS, REQVALUE_SPECIAL_TREATMENT);
		result.put(this.controllerID, (short) 1);
		
		switch(this.controllerID)
		{
			case CONTROLLER_STEAMGENERATOR:
				result.put(FLUIDPORT, (short) 3);
				result.put(POWERSUPPLY, REQVALUE_VARIABLE);
				result.put(STEAMGENERATOR_COREPIPE, REQVALUE_SPECIAL_TREATMENT);
				break;
			default:
				Log.err(String.format(String.format("Structure id [%d] missing in getAllowedElements - please write a bug report", this.controllerID))); 
				return result;
		}
		return result;
		
	}
	
	
	private boolean isStructureAvaiable(short controllerID)
	{
		switch(controllerID)
		{
			case CONTROLLER_STEAMGENERATOR:
				return true;
		}
		return false;
	}
	
}
