package de.nedelosk.modularmachines.common.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class AchievementManager {

	public static final Achievement craftCasingIron = new AchievementCrafting("achievement.craftCasingIron", "craftCasingIron", 0, 0,
			new ItemStack(BlockManager.blockCasings), null);
	public static final Achievement craftCasingBronze = new AchievementCrafting("achievement.craftCasingBronze", "craftCasingBronze", 2, 0,
			new ItemStack(BlockManager.blockCasings, 1, 1), craftCasingIron);

	public static void registerPage() {
		AchievementPage.registerAchievementPage(new AchievementPage("Modular Machine's", craftCasingIron, craftCasingBronze));
	}

	@SubscribeEvent
	public void onCraftItem(ItemCraftedEvent event) {
		Item item = event.crafting.getItem();
		int damage = event.crafting.getItemDamage();
		for(Achievement a : AchievementPage.getAchievementPage("Modular Machine's").getAchievements()) {
			if (a instanceof AchievementCrafting) {
				if (item.equals(a.theItemStack.getItem()) && damage == a.theItemStack.getItemDamage()) {
					event.player.addStat(a, 1);
				}
			}
		}
	}

	public static class AchievementCrafting extends Achievement {

		public AchievementCrafting(String p_i45300_1_, String p_i45300_2_, int p_i45300_3_, int p_i45300_4_, Item p_i45300_5_, Achievement p_i45300_6_) {
			super(p_i45300_1_, p_i45300_2_, p_i45300_3_, p_i45300_4_, p_i45300_5_, p_i45300_6_);
		}

		public AchievementCrafting(String p_i45301_1_, String p_i45301_2_, int p_i45301_3_, int p_i45301_4_, Block p_i45301_5_, Achievement p_i45301_6_) {
			super(p_i45301_1_, p_i45301_2_, p_i45301_3_, p_i45301_4_, p_i45301_5_, p_i45301_6_);
		}

		public AchievementCrafting(String p_i45302_1_, String p_i45302_2_, int p_i45302_3_, int p_i45302_4_, ItemStack p_i45302_5_, Achievement p_i45302_6_) {
			super(p_i45302_1_, p_i45302_2_, p_i45302_3_, p_i45302_4_, p_i45302_5_, p_i45302_6_);
		}
	}
}
