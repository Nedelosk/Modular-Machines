package nedelosk.forestday.common.machines.mutiblock.charcoalkiln;

import java.util.ArrayList;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.items.tools.ItemBowAndStick;
import nedelosk.forestday.common.registrys.FBlocks;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileCharcoalKiln extends TileMultiblockBase {
	
	public WoodType type;
	
	public TileCharcoalKiln() {
		super(0, 5, 4, 2, 1);
		burnTimeTotal = ForestdayConfig.charcoalKilnBurnTime;
	}

	@Override
	public String getMultiBlockName() {
		return "kiln.charcoal";
	}

	@Override
	public char[][][] createPattern()
	{
		char[][] charcoal = { {  'O', 'O', 'O', 'O', 'O', },
				              {'O', 'C', 'C', 'C', 'O', },
				              {  'O', 'C', 'C', 'C', 'O', },
				              {  'O', 'C', 'C', 'C', 'O', },
				              {  'O', 'O', 'O', 'O', 'O', },
				              };
		
		char[][] charcoal_O = { {  'O', 'O', 'O', 'O', 'O', },
								{  'O', 'O', 'O', 'O', 'O', },
								{  'O', 'O', 'O', 'O', 'O', },
								{  'O', 'O', 'O', 'O', 'O', },
								{  'O', 'O', 'O', 'O', 'O', },
	            				};
		
		char[][] charcoal_Master = { {  'O', 'O', 'O', 'O', 'O', },
				                     {  'O', 'C', 'C', 'C', 'O', },
	                                 {  'O', 'C', 'M', 'C', 'O', },
	                                 {  'O', 'C', 'C', 'C', 'O', },
	                                 {  'O', 'O', 'O', 'O', 'O', },
	                                 };
		
		return new char[][][] { charcoal_O, charcoal_Master, charcoal, charcoal_O };
	}


	@Override
	public boolean isPatternBlockValid(int x, int y, int z, char pattern) {
		Block block = this.worldObj.getBlock(x, y, z);
		TileEntity tile = this.worldObj.getTileEntity(x, y, z);
		switch (pattern) {
		case 'C':
            if (block != getBlockType() && !(tile instanceof TileCharcoalKiln))
            {
            	//System.err.print("C Error at " + x + " X, " + y + " Y, " + z + " Z,  ;");
                return false;
            }
            break;
		case 'O':
            if (block == getBlockType() && tile instanceof TileCharcoalKiln)
            {
            	//System.err.print("C Error at " + x + " X, " + y + " Y, " + z + " Z,  ;");
                return false;
            }
            break;
		}
		return true;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public void updateClient() {
		
	}
	
	@Override
	public void updateServer() {
		super.updateServer();
		if(isMaster && isMultiblock)
		{
			if(!isWorking) return;
			if(burnTime >= burnTimeTotal)
			{
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				for (int x = 0; x < 3; x++) {
					for (int z = 0; z < 3; z++) {
						for (int y = 0; y < 2; y++) {
							int xP = x + xCoord - 1;
							int zP = z + zCoord - 1;
							int yP = y + yCoord;

							if(this.xCoord == xP && this.yCoord == yP && this.zCoord == zP)
							{
								
							}
							else{
								for(ItemStack stack : ((TileCharcoalKiln)this.worldObj.getTileEntity(xP, yP, zP)).type.charcoalDropps)
								{
								items.add(stack);
								}
								this.worldObj.setBlock(xP, yP, zP, Blocks.air);
							}
						}
					}
				}
				for(ItemStack stack : ((TileCharcoalKiln)this.worldObj.getTileEntity(xCoord, yCoord, zCoord)).type.charcoalDropps)
				{
				items.add(stack);
				}
				worldObj.setBlock(xCoord, yCoord, zCoord, FBlocks.Multiblock_Charcoal_Kiln.block(), 1, 2);
				((TileCharcoalAsh)worldObj.getTileEntity(xCoord, yCoord, zCoord)).setDropps(items);
			}
			else
				burnTime++;
		}
	}
	
	public void setType(WoodType type) {
		this.type = type;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(type != null)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			type.writeToNBT(nbtTag);
			nbt.setTag("Wood", nbtTag);
		}
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if(nbt.hasKey("Wood"))
		{
			type = WoodType.loadFromNBT(nbt.getCompoundTag("Wood"));
		}
		
	}
	
	@Override
	public void onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side) {
		if(master != null)
		{
			if(!master.isWorking)
			{
		if(player.getCurrentEquippedItem() != null)
		{
			if(player.getCurrentEquippedItem().getItem() instanceof ItemBowAndStick)
			{
				ItemStack bow = player.getCurrentEquippedItem();
				if(bow.hasTagCompound() && bow.getTagCompound().hasKey("Power") && bow.getTagCompound().getInteger("Power") == ForestdayConfig.bowandstickPowerMin[bow.getItemDamage()])
				{
					bow.getTagCompound().setInteger("Power", 0);
					master.isWorking = true;
					
				}
			}
			else if(player.getCurrentEquippedItem().getItem() instanceof ItemFlintAndSteel)
			{
				player.getCurrentEquippedItem().damageItem(5, player);
				master.isWorking = true;
			}
		}
		}
		}
	}

	@Override
	public boolean testBlock() {
		return false;
	}

}
