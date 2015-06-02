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
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;

public class BlockOre extends BlockForestday {

	 public String[] ore = new String[] { "copper", "tin", "lime" };
	 public IIcon[] oreIcon;
	
	public BlockOre() {
		super("ore", Material.ground, Tabs.tabForestdayBlocks);
		this.setHardness(2.0f);
		this.setStepSound(Block.soundTypeStone);
		this.setHarvestLevel("pickaxe", 1, 0);
		this.setHarvestLevel("pickaxe", 2, 1);
		this.setHarvestLevel("pickaxe", 1, 0);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return oreIcon[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.oreIcon = new IIcon[ore.length];

        for (int i = 0; i < this.oreIcon.length; ++i)
        {
            this.oreIcon[i] = iconRegister.registerIcon("forestday:ore_" + ore[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < ore.length; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }

}
