package nedelosk.forestday.structure.kiln.charcoal.blocks.tile;

import nedelosk.forestday.structure.base.blocks.tile.TileStructureInventory;
import nedelosk.forestday.structure.kiln.charcoal.blocks.BlockCharcoalKiln;
import nedelosk.forestday.structure.kiln.charcoal.gui.GuiCharcoalKiln;
import nedelosk.forestday.structure.kiln.charcoal.inventory.ContainerCharcoalKiln;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

public class TileCharcoalKiln extends TileStructureInventory {
	
	public boolean hasMaster;
	public boolean isMaster;
	public TileCharcoalKiln master;
	public KilnState state;
	public char[][][] pattern = createPattern();
	
    public enum KilnState {

        VALID, INVALID, UNKNOWN
    };
	
	public TileCharcoalKiln() {
		super(2000, 11, "structure.kiln.charcoal");
	}

	@Override
	public String getMachineTileName() {
		return "structure.kiln.charcoal";
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerCharcoalKiln(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return new GuiCharcoalKiln(this, inventory);
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		if(master == null)
		{
		isMultiblock();
		}
	}
	
	public void isMultiblock()
	{
		state = testMultiblock();
		if(state == KilnState.VALID)
		{
            isMaster = true;
			for (int d = 0; d < pattern[0][0].length; d++) {
				for (int t = 0; t < pattern[0].length; t++) {
					for (int z = 0; z < pattern.length; z++) {
						TileEntity tile = this.worldObj.getTileEntity(this.xCoord - 2 + d, this.yCoord - 1 + z, this.zCoord - 2 + t);
						if (tile instanceof TileCharcoalKiln) {
							TileCharcoalKiln kiln = (TileCharcoalKiln) tile;
							//if(kiln)
							kiln.setMaster(this);
							this.worldObj.markBlockForUpdate(kiln.xCoord, kiln.yCoord, kiln.zCoord);
						}
					}
				}
			}
		}
		else if(isMaster)
		{
			isMaster = false;
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	public static char[][][] createPattern()
	{
		
		char[][] middle = { { 'S', 'S', 'S', 'S', 'S' },
	                        { 'S', 'K', 'K', 'K', 'S' },
	                        { 'S', 'K', 'K', 'K', 'S' },
	                        { 'S', 'K', 'K', 'K', 'S' }, 
	                        { 'S', 'S', 'S', 'S', 'S' },
	                        };
		
		char[][] top = { { 'S', 'S', 'S', 'S', 'S' },
                         { 'S', 'S', 'S', 'S', 'S' },
                         { 'S', 'S', 'S', 'S', 'S' },
                         { 'S', 'S', 'S', 'S', 'S' }, 
                         { 'S', 'S', 'S', 'S', 'S' },
                         };
		return new char[][][] { top, middle, top, top, top };
	}
	
	public KilnState testMultiblock()
	{
		for(int d = 0;d < pattern[0][0].length;d++)
		{
			for(int t = 0;t < pattern[0].length;t++)
			{
				for(int z = 0;z < pattern.length;z++)
				{
					Block block;
					if(this.pattern[z][t][d] == 'K')
					{
						block = Blocks.dirt;
					}
					else
					{
						block = Blocks.log;
					}
				//this.worldObj.setBlock(this.xCoord + d - 2, this.yCoord + z - 1, this.zCoord + t - 2, block);
				
				if(!isPatternBlockValid(this.xCoord - 2 + d,  this.yCoord - 1 + z, this.zCoord - 2 + t, this.pattern[z][t][d]))
				{
					return KilnState.INVALID;
				}
				}
			}
		}
		return KilnState.VALID;
	}
	
	public boolean isPatternBlockValid(int x, int y, int z, char pattern)
	{
		Block block = this.worldObj.getBlock(x, y, z);
		switch (pattern) {
		case 'S':
            if (block instanceof BlockCharcoalKiln)
            {
            	System.err.print("Error S, ");
                return false;
            }
            break;
		case 'K':
            if (!(block instanceof BlockCharcoalKiln))
            {
            	System.err.print("Error K, ");
                return false;
            }
            break;
		}
		return true;
	}
	
	public void setMaster(TileCharcoalKiln master) {
		this.master = master;
	}

}
