package nedelosk.nedeloskcore.common.blocks.multiblocks;

import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.utils.misc.TileCache;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileMultiblockBase extends TileMachineBase {

	public char[][][] pattern;
	public TileCache cache;
	public int xSize;
	public int ySize;
	public int xSizeTwo;
	public int ySizeTwo;
	public boolean tested;
	public boolean isMaster;
	public boolean isMultiblock;
	public boolean isMultiblockSolid;
	
	public TileMultiblockBase(int slots, int xSize, int ySize, int xSizeTwo, int ySizeTwo) {
		super(slots);
		this.pattern = createPattern();
		this.xSize = xSize;
		this.ySize = ySize;
		this.xSizeTwo = xSizeTwo;
		this.cache = new TileCache(this);
		this.ySizeTwo = ySizeTwo;
	}
	
	public TileMultiblockBase master;

	public abstract String getMultiBlockName();
	
	@Override
	public String getMachineName() {
		return "multiblock." + getMultiBlockName();
	}
	
	public abstract char[][][] createPattern();
	
	@Override
	public void updateServer() {
		updateMultiblock();
		
	}
	
    protected boolean isStructureTile(TileEntity tile) {
        return tile != null && tile.getClass() == getClass();
    }
	
	public void updateMultiblock() {
		if(timer >= timerMax)
		{
		isMultiblock();
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		else
			timer++;
	}
	
	public abstract boolean testBlock();
	
	public void isMultiblock()
	{
		if(!tested)
		{
		if (testMultiblock()) {
			isMaster = true;
			for (int x = 0; x < xSize; x++) {
				for (int z = 0; z < xSize; z++) {
					for (int y = 0; y < ySize; y++) {
						TileEntity tile = this.worldObj.getTileEntity(this.xCoord - xSizeTwo + x, this.yCoord - ySizeTwo + y, this.zCoord - xSizeTwo + z);
						if (tile instanceof TileMultiblockBase) {
							if(((TileMultiblockBase) tile).testBlock())
							{
								tested = false;
								isMultiblock = false;
								return;
							}
							((TileMultiblockBase) tile).setMaster(this);
							((TileMultiblockBase) tile).tested = true;
						}
						else
							tested = false;
					}
				}
			}
			isMultiblock = true;
		}
		}
	}
	
    public void onBlockAdded() {
		if(worldObj.isRemote) return;
        onBlockChange();
    }

    public void onBlockRemoval() {
    	if(worldObj.isRemote) return;
        onBlockChange();
        isMaster = false;
        if(master != null)
        	if(!master.isMultiblockSolid)
        	master.isMultiblock = false;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
    	super.writeToNBT(nbt);
    	if(worldObj != null)
    	{
    	if(master != null && worldObj.getTileEntity(master.xCoord, master.yCoord,  master.zCoord) != null && worldObj.getTileEntity(master.xCoord, master.yCoord,  master.zCoord) == master)
    	{
    	nbt.setInteger("masterXCoord", master.xCoord);
    	nbt.setInteger("masterYCoord", master.yCoord);
    	nbt.setInteger("masterZCoord", master.zCoord);
    	
    	nbt.setBoolean("isMaster", isMaster);
    	nbt.setBoolean("isMultiblock", isMultiblock);
    	nbt.setBoolean("isMultiblockSolid", isMultiblockSolid);
    	}
    	}
    	
    	nbt.setBoolean("tested", tested);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
    	super.readFromNBT(nbt);
    	
    	if(nbt.hasKey("masterXCoord") || nbt.hasKey("masterYCoord") || nbt.hasKey("masterZCoord"))
    	{
    		if(worldObj == null) return;
    		if(worldObj.getTileEntity(nbt.getInteger("masterXCoord"), nbt.getInteger("masterYCoord"), nbt.getInteger("masterZCoord")) != null && worldObj.getTileEntity(nbt.getInteger("masterXCoord"), nbt.getInteger("masterYCoord"), nbt.getInteger("masterZCoord")) instanceof TileMachineBase)
    		this.master = (TileMultiblockBase) worldObj.getTileEntity(nbt.getInteger("masterXCoord"), nbt.getInteger("masterYCoord"), nbt.getInteger("masterZCoord"));
    	
    	this.isMaster = nbt.getBoolean("isMaster");
    	this.isMultiblock = nbt.getBoolean("isMultiblock");
    	this.isMultiblockSolid = nbt.getBoolean("isMultiblockSolid");
    	}
    	else if(master != null)
    	{
    		master = null;
    	}
    	
    	this.tested = nbt.getBoolean("tested");
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
    	if(worldObj.isRemote) return;
        tested = false;
    }

    private void onBlockChange() {
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tile = cache.getTileOnSide(side);
            if (isStructureTile(tile))
                ((TileMultiblockBase) tile).onBlockChange(12);
        }
    }

    private void onBlockChange(int depth) {
        depth--;
        if (depth < 0)
            return;
        if (tested) {
            tested = false;
            
            if(isMaster)
            	isMultiblock = false;

            TileMultiblockBase mBlock = getMasterBlock();
            if (mBlock != null) {
                mBlock.onBlockChange(12);
                return;
            }

            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity tile = cache.getTileOnSide(side);
                if (isStructureTile(tile))
                    ((TileMultiblockBase) tile).onBlockChange(depth);
            }
        }
    }
    
    public final TileMultiblockBase getMasterBlock() {
        if (master != null && !master.tested) {
            master = null;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        return master;
    }
	
	public boolean testMultiblock()
	{
		for(int x = 0;x < xSize;x++)
		{
			for(int z = 0;z < xSize;z++)
			{
				for(int y = 0;y < ySize;y++)
				{
				if(!isPatternBlockValid(this.xCoord - xSizeTwo + x,  this.yCoord - ySizeTwo + y, this.zCoord - xSizeTwo + z, pattern[y][z][x]))
				{
					return false;
				}
				}
			}
		}
		return true;
	}
	
	public abstract boolean isPatternBlockValid(int x, int y, int z, char pattern);
	
	public void setMaster(TileMultiblockBase tile)
	{
		master = tile;
	}

	public abstract void onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side);
	
}
