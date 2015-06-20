package nedelosk.forestday.common.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.Forestday;
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

public class BlockGravel extends BlockForestday {

	 public String[] textures = new String[] { "loam", "ore_gravel" };
	 public IIcon[] icon;
	
	public BlockGravel() {
		super("gravel", Material.ground, Tabs.tabForestdayBlocks);
		this.setHardness(1.0f);
		this.setStepSound(Block.soundTypeMetal);
		this.setHarvestLevel("shovel", 1, 0);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return icon[meta];
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.icon = new IIcon[textures.length];

        for (int i = 0; i < this.icon.length; ++i)
        {
            this.icon[i] = iconRegister.registerIcon("forestday:" + textures[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks (Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 1; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }

}
