package nedelosk.forestday.common.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.Forestday;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.TabForestday;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;

public class BlockMetalBlocks extends BlockForestday {

	 public String[] textures = new String[] { "copper", "tin", "bronze", "red_alloy", "blue_alloy", "blue_dark_alloy", "yellow_alloy", "brown_alloy", "green_alloy", "steel", "light_steel", "dark_steel", "obsidian", "enderium" };
	 public IIcon[] icon;
	
	public BlockMetalBlocks() {
		super("metalBlock", Material.ground, Tabs.tabForestdayBlocks);
		this.setHardness(1.0f);
		this.setStepSound(Block.soundTypeMetal);
		this.setHarvestLevel("pickaxe", 1, 0);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return icon[meta];
    }
    
    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z,
    		int beaconX, int beaconY, int beaconZ) {
    	return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icon = new IIcon[textures.length];

        for (int i = 0; i < this.icon.length; ++i)
        {
            this.icon[i] = iconRegister.registerIcon("forestday:metal_blocks/" + textures[i] + "_block");
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < textures.length; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }

}
