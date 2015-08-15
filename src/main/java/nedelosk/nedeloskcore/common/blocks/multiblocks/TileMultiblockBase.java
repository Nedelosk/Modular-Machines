package nedelosk.nedeloskcore.common.blocks.multiblocks;

import java.util.ArrayList;
import java.util.Map;

import nedelosk.nedeloskcore.api.Material;
import nedelosk.nedeloskcore.api.MultiblockModifierValveTypeString;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import nedelosk.nedeloskcore.utils.misc.TileCache;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileMultiblockBase extends TileMachineBase {

	public MultiblockModifierValveTypeString modifier;
	public MultiblockPattern pattern;
	public AbstractMultiblock multiblock;
	public TileCache cache;
	public boolean tested;
	public boolean isMaster;
	public boolean isMultiblock;
	public boolean isMultiblockSolid;
	public Material material;
	
	public TileMultiblockBase(int slots) {
		super(slots);
		this.cache = new TileCache(this);
		timerMax = 100;
		modifier = getModifier();
	}
	
	public TileMultiblockBase(Material material) {
		super();
		this.cache = new TileCache(this);
		timerMax = 100;
		modifier = getModifier();
		this.material = material;
	}
	
	public TileMultiblockBase() {
		super();
		this.cache = new TileCache(this);
		timerMax = 100;
		modifier = getModifier();
	}
	
	public MultiblockModifierValveTypeString getModifier()
	{
		return new MultiblockModifierValveTypeString();
	}
	
	public TileMultiblockBase master;

	public String getMultiblockName()
	{
		if( master != null && master.multiblock != null)
			return master.multiblock.getMultiblockName();
		return null;
	}
	
	@Override
	public String getMachineName() {
		return "multiblock." + getMultiblockName();
	}
	
	@Override
	public void updateServer() {
		updateMultiblock();
		if(isMaster && isMultiblock)
			master.multiblock.updateServer(this);
	}
	
    protected boolean isStructureTile(TileEntity tile) {
        return tile != null && tile.getClass() == getClass();
    }
	
	public void updateMultiblock() {
		if(timer >= timerMax)
		{
		testMultiblock();
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		timer = 0;
		}
		else
			timer++;
	}
	
	public void testMultiblock()
	{
		if(!tested)
		{
		if (testPatterns()) {
			isMaster = true;
	        int xWidth = pattern.getPatternWidthX();
	        int zWidth = pattern.getPatternWidthZ();
	        int height = pattern.getPatternHeight();

	        int xOffset = xCoord - pattern.getMasterOffsetX();
	        int yOffset = yCoord - pattern.getMasterOffsetY();
	        int zOffset = zCoord - pattern.getMasterOffsetZ();

	        for (int patX = 0; patX < xWidth; patX++) {
	            for (int patY = 0; patY < height; patY++) {
	                for (int patZ = 0; patZ < zWidth; patZ++) {
	                    int x = patX + xOffset;
	                    int y = patY + yOffset;
	                    int z = patZ + zOffset;
						TileEntity tile = this.worldObj.getTileEntity(x, y, z);
						char c = pattern.getPatternMarker(patX, patY, patZ);
						if(c != 'O' || c != 'H')
						{
							if(tile instanceof TileMultiblockBase) {
								if(multiblock.testBlock())
								{
									tested = false;
									isMultiblock = false;
									return;
								}
								((TileMultiblockBase) tile).setMaster(this);
								((TileMultiblockBase) tile).tested = true;
								worldObj.markBlockForUpdate(x, y, z);
							}
							else
								tested = false;
						}
					}
				}
			}
			isMultiblock = true;
		}
		}
	}
	
	public boolean testPatterns()
	{
		for(Map.Entry<String, ArrayList<MultiblockPattern>> entry : NCoreApi.getMutiblockPatterns().entrySet())
		{
			for(MultiblockPattern pattern : entry.getValue())
			{
				boolean isPattern = testPattern(pattern, NCoreApi.getMutiblock(entry.getKey()));
				if(isPattern)
				{
					this.pattern = pattern;
					multiblock = NCoreApi.getMutiblock(entry.getKey());
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					return true;
				}	
			}
		}
		return false;
	}
	
	public boolean testPattern(MultiblockPattern pattern, AbstractMultiblock multiblock)
	{
        int xWidth = pattern.getPatternWidthX();
        int zWidth = pattern.getPatternWidthZ();
        int height = pattern.getPatternHeight();

        int xOffset = xCoord - pattern.getMasterOffsetX();
        int yOffset = yCoord - pattern.getMasterOffsetY();
        int zOffset = zCoord - pattern.getMasterOffsetZ();

        for (int patX = 0; patX < xWidth; patX++) {
            for (int patY = 0; patY < height; patY++) {
                for (int patZ = 0; patZ < zWidth; patZ++) {
                    int x = patX + xOffset;
                    int y = patY + yOffset;
                    int z = patZ + zOffset;
					if(!multiblock.isPatternBlockValid(x,  y, z, pattern.getPatternMarker(patX, patY, patZ), this))
					{
						return false;
					}
                }
            }
        }
        return true;
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
    		if(master != null && worldObj.getTileEntity(master.xCoord, master.yCoord,  master.zCoord) != null && worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) == master)
    		{
    			nbt.setInteger("masterXCoord", master.xCoord);
    			nbt.setInteger("masterYCoord", master.yCoord);
    			nbt.setInteger("masterZCoord", master.zCoord);
    			
    			if(modifier != null)
    			{
    				NBTTagCompound nbtModifier = new NBTTagCompound();
    				modifier.writeToNBT(nbtModifier);
    				nbt.setTag("Modifier", nbtModifier);
    			}
    			
    			nbt.setInteger("Material", NCoreApi.getMaterials().indexOf(material));
    			
    			nbt.setBoolean("isMaster", isMaster);
    			nbt.setBoolean("isMultiblock", isMultiblock);
    			nbt.setBoolean("isMultiblockSolid", isMultiblockSolid);
    			if(isMaster)
    				if(multiblock != null)
    				{
    					nbt.setString("MultiblockName", multiblock.getMultiblockName());
    					nbt.setInteger("Pattern", NCoreApi.getMutiblockPatterns(multiblock.getMultiblockName()).indexOf(pattern));
    					NBTTagCompound nbtMultiblock = new NBTTagCompound();
    					multiblock.writeToNBT(nbtMultiblock);
    					nbt.setTag("Multiblock", nbtMultiblock);
    				}
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
    		this.material = NCoreApi.getMaterials().get(nbt.getInteger("Material"));
    		if(nbt.hasKey("Modifier"))
    		{
    			NBTTagCompound nbtModifier = nbt.getCompoundTag("Modifier");
    			modifier.readFromNBT(nbtModifier);
    		}
    		if(isMaster)
    		{
    			if(nbt.hasKey("Multiblock"))
    			{
    				multiblock = NCoreApi.getMutiblock(nbt.getString("MultiblockName"));
    				multiblock.readFromNBT(nbt.getCompoundTag("Multiblock"));
    				pattern = NCoreApi.getMutiblockPatterns(multiblock.getMultiblockName()).get(nbt.getInteger("Pattern"));
    			}
    		}
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
	
	public void setMaster(TileMultiblockBase tile)
	{
		master = tile;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if(isMaster)
			super.setInventorySlotContents(i, itemstack);
		else if(master != null)
			master.setInventorySlotContents(i, itemstack);
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(isMaster)
			return super.decrStackSize(slot, amount);
		else if(master != null)
			return master.decrStackSize(slot, amount);
		return  null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(isMaster)
			return super.getStackInSlotOnClosing(i);
		else if(master != null)
			return master.getStackInSlotOnClosing(i);
		return  null;
	}
	
	@Override
	public ItemStack getStackInSlot(int i) {
		if(isMaster)
			return super.getStackInSlot(i);
		else if(master != null)
			return master.getStackInSlot(i);
		return  null;
	}
	
	@Override
	public int getSizeInventory() {
		if(isMaster)
			return super.getSizeInventory();
		else if(master != null)
			return master.getSizeInventory();
		return  0;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return master.multiblock.getContainer(this, inventory);
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return master.multiblock.getGUIContainer(this, inventory);
	}

	@Override
	public void updateClient() {
		if(isMaster && isMultiblock)
			master.multiblock.updateClient(this);
	}
	
}
