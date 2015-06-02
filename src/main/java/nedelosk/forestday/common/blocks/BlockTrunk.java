package nedelosk.forestday.common.blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.TabForestday;
import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import nedelosk.forestday.common.registrys.ForestdayRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrunk extends BlockForestday {

	private IIcon iconSide;
	private IIcon iconTop;

	public BlockTrunk(String name) {
		super ("trunk." + name, Material.ground, Tabs.tabForestdayBlocks);
        this.setHardness(1.0F);
        this.setResistance(8.0F);
        this.setStepSound(soundTypeWood);
	
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister IIconRegister)
    {
            this.iconSide = IIconRegister.registerIcon("forestday:" + "log_trunk");
            this.iconTop = IIconRegister.registerIcon("forestday:" + "log_trunk_top");
    }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata){
		switch (side) {
		case 0:
			return iconTop;
		case 1:
			return iconTop;
		default:
			return iconSide;
		}
	}
	
	@Override
    public void setBlockBoundsBasedOnState (IBlockAccess iblockaccess, int x, int y, int z)
    {

        float minX;
        float minY;
        float minZ;
        float maxX;
        float maxY;
        float maxZ;
        
    	Block getBlock = iblockaccess.getBlock(x, y, z);
    	Block getBlock1 = iblockaccess.getBlock(x + 1, y, z);
    	Block getBlock2 = iblockaccess.getBlock(x, y, z + 1);
    	Block getBlock4 = iblockaccess.getBlock(x - 1, y, z);
    	Block getBlock5 = iblockaccess.getBlock(x, y, z - 1);
    	Block getBlock6 = iblockaccess.getBlock(x - 1, y, z - 1);

        if (getBlock == ForestdayBlockRegistry.trunkBig)
        {
        	if(getBlock1 == ForestdayBlockRegistry.trunkBig && getBlock2 == ForestdayBlockRegistry.trunkBig && getBlock4 == ForestdayBlockRegistry.trunkBig && getBlock5 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.0F;
                minZ = 0.0F;
                maxX = 1.0F;
                maxZ = 1.0F;
                minY = 0F;
                maxY = 1.0F;
        	}
            else if(getBlock1 == ForestdayBlockRegistry.trunkBig && getBlock4 == ForestdayBlockRegistry.trunkBig && getBlock5 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.0F;
                minZ = 0.0F;
                maxX = 1.0F;
                maxZ = 0.9F;
                minY = 0F;
                maxY = 1.0F;
        	}
            else if(getBlock1 == ForestdayBlockRegistry.trunkBig && getBlock2 == ForestdayBlockRegistry.trunkBig && getBlock4 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.0F;
                minZ = 0.1F;
                maxX = 1.0F;
                maxZ = 1.0F;
                minY = 0F;
                maxY = 1.0F;
        	}
            else if(getBlock1 == ForestdayBlockRegistry.trunkBig && getBlock2 == ForestdayBlockRegistry.trunkBig && getBlock5 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.1F;
                minZ = 0.0F;
                maxX = 1.0F;
                maxZ = 1.0F;
                minY = 0F;
                maxY = 1.0F;
        	}
            else if(getBlock2 == ForestdayBlockRegistry.trunkBig && getBlock4 == ForestdayBlockRegistry.trunkBig && getBlock5 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.0F;
                minZ = 0.0F;
                maxX = 0.9F;
                maxZ = 1.0F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock1 == ForestdayBlockRegistry.trunkBig && getBlock2 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.1F;
                minZ = 0.1F;
                maxX = 1.0F;
                maxZ = 1.0F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock1 == ForestdayBlockRegistry.trunkBig && getBlock4 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.0F;
                minZ = 0.1F;
                maxX = 1.0F;
                maxZ = 0.9F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock1 == ForestdayBlockRegistry.trunkBig && getBlock5 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.1F;
                minZ = 0.0F;
                maxX = 1.0F;
                maxZ = 0.9F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock2 == ForestdayBlockRegistry.trunkBig && getBlock4 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.0F;
                minZ = 0.1F;
                maxX = 0.9F;
                maxZ = 1.0F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock2 == ForestdayBlockRegistry.trunkBig && getBlock5 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.1F;
                minZ = 0.0F;
                maxX = 0.9F;
                maxZ = 1.0F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock4 == ForestdayBlockRegistry.trunkBig && getBlock5 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.0F;
                minZ = 0.0F;
                maxX = 0.9F;
                maxZ = 0.9F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock1 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.1F;
                minZ = 0.1F;
                maxX = 1.0F;
                maxZ = 0.9F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock2 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.1F;
                minZ = 0.1F;
                maxX = 0.9F;
                maxZ = 1.0F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock4 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.0F;
                minZ = 0.1F;
                maxX = 0.9F;
                maxZ = 0.9F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else if(getBlock5 == ForestdayBlockRegistry.trunkBig)
        	{
                minX = 0.1F;
                minZ = 0.0F;
                maxX = 0.9F;
                maxZ = 0.9F;
                minY = 0F;
                maxY = 1.0F;
        	}
        	else
        	{
        	minX = minZ = 0.1F;
            maxX = maxZ = 0.9F;
            minY = 0F;
            maxY = 1.0F;
        	}
        }
        else if(getBlock == ForestdayBlockRegistry.trunkMedium)
        {
            minX = 0.2F;
            minZ = 0.2F;
            maxX = 0.8F;
            maxZ = 0.8F;
            minY = 0F;
            maxY = 1.0F;
        }
        else if(getBlock == ForestdayBlockRegistry.trunkSmall)
        {
            minX = 0.3F;
            minZ = 0.3F;
            maxX = 0.7F;
            maxZ = 0.7F;
            minY = 0F;
            maxY = 1.0F;
        }
        else if(getBlock == ForestdayBlockRegistry.trunkTiny)
        {
            minX = 0.4F;
            minZ = 0.4F;
            maxX = 0.6F;
            maxZ = 0.6F;
            minY = 0F;
            maxY = 1.0F;
        }
        else
        {
            minX = 0.1F;
            maxX = 0.9F;
            minZ = 0.0F;
            maxZ = 1.0F;
            minY = 0.1F;
            maxY = 0.9F;
        }
        setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
}
