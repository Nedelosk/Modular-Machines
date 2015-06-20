package nedelosk.nedeloskcore.common.items.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class ItemBlockPlan extends ItemBlock {

	public ItemBlockPlan(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}

	protected Block getBlock() {
		return field_150939_a;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.getBlock().getIcon(1, meta);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return  getBlock().getUnlocalizedName().replace("tile.", "");
	}

}
