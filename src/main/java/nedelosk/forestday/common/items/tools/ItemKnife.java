package nedelosk.forestday.common.items.tools;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.forestday.common.registrys.BlockRegistry;
import nedelosk.forestday.common.registrys.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemKnife extends ItemToolCrafting {

	public ItemKnife(String name, int maxDamage, int tier, Material material) {
		super(name, maxDamage, tier, material, name, 5);
	}

}
