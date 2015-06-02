package nedelosk.forestday.common.blocks;

import java.util.List;

import javax.swing.ImageIcon;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.registrys.ForestdayRegistry;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogResin extends BlockLog {
	
	public BlockLogResin() {
		this.setCreativeTab(Tabs.tabForestdayBlocks);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconSide;

	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
            this.iconSide = p_149651_1_.registerIcon("forestday:resin_log");
            this.iconTop = p_149651_1_.registerIcon("minecraft:log_spruce_top");
    }
	
	@Override
    public String getUnlocalizedName()
    {
        return ForestdayRegistry.setUnlocalizedBlockName("log.nature");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getSideIcon(int meta) {
		return this.iconSide;
	}

	@Override
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int meta)
    {
        return this.iconTop;
    }
	
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 0, 0));
    }

}
