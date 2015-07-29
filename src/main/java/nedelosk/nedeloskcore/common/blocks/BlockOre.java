package nedelosk.nedeloskcore.common.blocks;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.blocks.BlockForestday;
import nedelosk.forestday.common.core.ForestDay;
import nedelosk.forestday.common.core.TabForestday;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
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

public class BlockOre extends BlockForest {

	 public String[] ore = new String[] { "Copper", "Tin", "Silver", "Lead", "Nickel", "Ruby" };
	 public IIcon[] oreIcon;
	
	public BlockOre() {
		super(Material.ground, CreativeTabs.tabBlock);
		this.setHardness(2.0f);
		this.setResistance(3.0F);
		this.setBlockName("ore");
		this.setStepSound(Block.soundTypeStone);
		this.setHarvestLevel("pickaxe", 1);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta)
    {
        return oreIcon[meta];
    }
    
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
    	if(metadata == 5)
    	{
    		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
    		list.add(new ItemStack(NRegistry.gems, 1, world.rand.nextInt(2)));
    	}
    	return super.getDrops(world, x, y, z, metadata, fortune);
    }
    
    @Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
    	if(metadata == 5)
    		return false;
    	return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister iconRegister)
    {
        this.oreIcon = new IIcon[ore.length];

        for (int i = 0; i < this.oreIcon.length; ++i)
        {
            this.oreIcon[i] = iconRegister.registerIcon("nedeloskcore:ore" + ore[i]);
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
