package nedelosk.forestday.common.items.tools;

import java.util.List;

import nedelosk.forestday.common.config.ForestdayConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemBowAndStick extends ItemToolForestday {
	
	public int[] powerMin = ForestdayConfig.bowandstickPowerMin;
	
	public ItemBowAndStick(String name, int maxDamage, String nameTexture, int tier, Material material) {
		super(name, maxDamage, tier, material);
		this.setTextureName("forestday:tools/" + nameTexture);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(stack.getTagCompound().hasKey("Power"))
		{
			if(stack.getTagCompound().getInteger("Power") >= powerMin[tier - 1])
			{
				
			}
			else
			{
				stack.getTagCompound().setInteger("Power", (stack.getTagCompound().getInteger("Power") + 1));
			}
		}
		else
		{
			stack.getTagCompound().setInteger("Power", 1);
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
		if(aStack.hasTagCompound())
		aList.add(StatCollector.translateToLocal("forest.fd.tooltip.power") + " " + aStack.getTagCompound().getInteger("Power"));
	}

}
