package nedelosk.forestday.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestday.common.registrys.FBlocks;
import nedelosk.forestday.common.registrys.FItems;
import nedelosk.nedeloskcore.utils.ItemUtils;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCropCorn extends BlockBush implements IGrowable
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    private static final String __OBFID = "CL_00000222";

    public BlockCropCorn()
    {
        this.setTickRandomly(true);
        this.setCreativeTab((CreativeTabs)null);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
        this.setBlockName("corn");
    }

    @Override
	protected boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.farmland || block == this;
    }

    @Override
	public void updateTick(World world, int x, int y, int z, Random r)
    {
        super.updateTick(world, x, y, z, r);

        if (world.getBlockLightValue(x, y + 1, z) >= 9)
        {
            int l = world.getBlockMetadata(x, y, z);

            if (l < 2)
            {
                float f = this.func_149864_n(world, x, y, z);

                if (r.nextInt((int)(25.0F / f) + 1) == 0)
                {
                    ++l;
                    world.setBlockMetadataWithNotify(x, y, z, l, 2);
                    if(l == 2)
                    {
                    	world.setBlock(x, y + 1, z, FBlocks.Crop_Corn.block());
                    	world.setBlockMetadataWithNotify(x, y + 1, z, 3, 2);
                    }
                }
            }
            else if(world.getBlock(x, y - 1, z) == this)
            {
                float f = this.func_149864_n(world, x, y, z);

                if (r.nextInt((int)(25.0F / f) + 1) == 0)
                {
                    if(world.getBlockMetadata(x, y, z) != 5)
                    {
                    	++l;
                    	world.setBlockMetadataWithNotify(x, y, z, l, 2);
                    }
                }
            }
        }
    }

    public void func_149863_m(World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5);

        if(world.getBlock(x, y + 1, z) == this)
        {
            if (l > 5)
            {
                l = 5;
            }
        	world.setBlockMetadataWithNotify(x, y + 1, z, l, 2);
        	return;
        }
        else if(world.getBlock(x, y - 1, z) == this)
        {
            if (l > 5)
            {
                l = 5;
            }
        	world.setBlockMetadataWithNotify(x, y, z, l, 2);
        	return;
        }
        if (l > 2)
        {
            l = 2;
        }

        world.setBlockMetadataWithNotify(x, y, z, l, 2);
        if(l == 2)
        {
        	world.setBlock(x, y + 1, z, FBlocks.Crop_Corn.block());
        	world.setBlockMetadataWithNotify(x, y + 1, z, 3, 2);
        }
    }

    private float func_149864_n(World p_149864_1_, int p_149864_2_, int p_149864_3_, int p_149864_4_)
    {
        float f = 1.0F;
        Block block = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ - 1);
        Block block1 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ + 1);
        Block block2 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_);
        Block block3 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_);
        Block block4 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ - 1);
        Block block5 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ - 1);
        Block block6 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ + 1);
        Block block7 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ + 1);
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        for (int l = p_149864_2_ - 1; l <= p_149864_2_ + 1; ++l)
        {
            for (int i1 = p_149864_4_ - 1; i1 <= p_149864_4_ + 1; ++i1)
            {
                float f1 = 0.0F;

                if (p_149864_1_.getBlock(l, p_149864_3_ - 1, i1).canSustainPlant(p_149864_1_, l, p_149864_3_ - 1, i1, ForgeDirection.UP, this))
                {
                    f1 = 1.0F;

                    if (p_149864_1_.getBlock(l, p_149864_3_ - 1, i1).isFertile(p_149864_1_, l, p_149864_3_ - 1, i1))
                    {
                        f1 = 3.0F;
                    }
                }

                if (l != p_149864_2_ || i1 != p_149864_4_)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }

        return f;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block Block, int meta) {
    	super.breakBlock(world, x, y, z, Block, meta);
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int metadata = world.getBlockMetadata(x, y, z);
        Block block = world.getBlock(x, y + 1, z);
        if (world.getBlockMetadata(x, y - 1, z) == 2 && world.getBlock(x, y - 1, z) == this && metadata >= 5 || world.getBlock(x, y + 1, z) == this && world.getBlockMetadata(x, y + 1, z) >= 5)
        {
        	ret.add(new ItemStack(getFruit(), 3));
        }
        else if (world.getBlockMetadata(x, y - 1, z) == 2 && world.getBlock(x, y - 1, z) == this && !(metadata >= 5))
        {
        	ret.add(new ItemStack(getFruit(), 1));
        }
        else if(world.getBlock(x, y - 1, z) != this && world.getBlock(x, y + 1, z) != this)
        {
        	ret.add(new ItemStack(getFruit(), 1));
        }
        ItemUtils.dropItem(world, x, y, z, ret);
    	if(world.getBlock(x, y - 1, z) == this)
    		world.setBlockToAir(x, y - 1, z);
    	if(world.getBlock(x, y + 1, z) == this)
    		world.setBlockToAir(x, y + 1, z);
    }
    
    @Override
    public int damageDropped(int meta) {
    	return meta;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int meta) {
    	return icons[meta];
    }

    @Override
	public int getRenderType()
    {
        return 6;
    }

    protected Item getFruit()
    {
        return FItems.crop_corn.item();
    }

    @Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean p_149851_5_)
    {
        return world.getBlockMetadata(x, y, z) != 2 || world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) != 5;
    }

    @Override
	public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
    {
        return true;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return this.getFruit();
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.icons = new IIcon[6];

    	for(int i = 0; i < 6; ++i)
    		this.icons[i] = p_149651_1_.registerIcon("forestday:crops/corn_stage_" + i);
    }

    @Override
	public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
    {
        this.func_149863_m(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        return ret;
    }
}