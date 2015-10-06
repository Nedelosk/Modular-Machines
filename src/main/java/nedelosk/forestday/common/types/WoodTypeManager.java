package nedelosk.forestday.common.types;

import java.util.HashMap;

import com.google.common.collect.Maps;

import nedelosk.forestday.api.crafting.IWoodTypeManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class WoodTypeManager implements IWoodTypeManager {

	public static HashMap<WoodTypeStack, WoodType> woodTypes = Maps.newHashMap();

	@Override
	public void add(ItemStack wood, ItemStack... dropps) {
		woodTypes.put(new WoodTypeStack(Block.getBlockFromItem(wood.getItem()), wood.getItemDamage()), new WoodType(wood, dropps));
	}
	
	public static class WoodTypeStack{
		
		public Block block;
		public int damage;
		
		public WoodTypeStack(Block block, int damage) {
			this.block = block;
			this.damage = damage;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof WoodTypeStack){
				WoodTypeStack stack = (WoodTypeStack) obj;
				if(stack.block == block && stack.damage == damage)
					return false;
			}
			return false;
		}
		
	}
	
}
