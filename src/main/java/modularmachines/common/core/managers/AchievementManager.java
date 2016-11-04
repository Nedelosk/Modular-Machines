package modularmachines.common.core.managers;

import modularmachines.common.core.Constants;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class AchievementManager {

	/*
	 * public static final Achievement craftChassis = new
	 * AchievementCrafting("achievement.craftChassis", "craftChassis", 0, 0, new
	 * ItemStack(ItemManager.itemChassis), null); public static final
	 * Achievement craftCasingWood = new
	 * AchievementCrafting("achievement.craftCasingWood", "craftCasingWood", 0,
	 * 2, new ItemStack(ItemManager.itemCasings), craftChassis); public static
	 * final Achievement craftCasingBronze = new
	 * AchievementCrafting("achievement.craftCasingBronze", "craftCasingBronze",
	 * 2, 2, new ItemStack(ItemManager.itemCasings, 1, 1), craftCasingWood);
	 * public static final Achievement craftCasingIron = new
	 * AchievementCrafting("achievement.craftCasingIron", "craftCasingIron", 2,
	 * 2, new ItemStack(ItemManager.itemCasings, 1, 2), craftCasingBronze);
	 * public static final Achievement craftCasingSttel = new
	 * AchievementCrafting("achievement.craftCasingIron", "craftCasingSteel", 2,
	 * 2, new ItemStack(ItemManager.itemCasings, 1, 2), craftCasingBronze);
	 * public static final Achievement craftDrawerWood = new
	 * AchievementCrafting("achievement.craftDrawerWood", "craftDrawerWood", 0,
	 * 4, new ItemStack(ItemManager.itemModuleStorageLarge, 1, 1),
	 * craftCasingWood); public static final Achievement craftDrawerBrick = new
	 * AchievementCrafting("achievement.craftDrawerBrick", "craftDrawerBrick",
	 * 2, 4, new ItemStack(ItemManager.itemModuleStorageLarge, 1, 1),
	 * craftDrawerWood); public static final Achievement craftDrawerBronze = new
	 * AchievementCrafting("achievement.craftDrawerBronze", "craftDrawerBronze",
	 * 4, 4, new ItemStack(ItemManager.itemModuleStorageLarge, 1, 2),
	 * craftDrawerBrick); public static final Achievement craftDrawerIron = new
	 * AchievementCrafting("achievement.craftDrawerIron", "craftDrawerIron", 6,
	 * 4, new ItemStack(ItemManager.itemModuleStorageLarge, 1, 3),
	 * craftDrawerBronze); public static final Achievement craftDrawerSteel =
	 * new AchievementCrafting("achievement.craftDrawerSteel",
	 * "craftDrawerSteel", 8, 4, new
	 * ItemStack(ItemManager.itemModuleStorageLarge, 1, 4), craftDrawerIron);
	 * public static final Achievement craftDrawerMagmarium = new
	 * AchievementCrafting("achievement.craftDrawerMagmarium",
	 * "craftDrawerMagmarium", 10, 4, new
	 * ItemStack(ItemManager.itemModuleStorageLarge, 1, 5), craftDrawerSteel);
	 */
	public static void registerPage() {
		AchievementPage.registerAchievementPage(new AchievementPage(
				Constants.NAME/*
				 * , craftChassis, craftCasingWood,
				 * craftCasingBronze, craftCasingIron,
				 * craftDrawerBrick, craftDrawerBronze,
				 * craftDrawerIron, craftDrawerSteel,
				 * craftDrawerMagmarium
				 */));
	}

	@SubscribeEvent
	public void onCraftItem(ItemCraftedEvent event) {
		Item item = event.crafting.getItem();
		int damage = event.crafting.getItemDamage();
		for(Achievement a : AchievementPage.getAchievementPage(Constants.NAME).getAchievements()) {
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
