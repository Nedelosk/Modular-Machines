package modularmachines.common.items;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.client.model.ModelManager;
import modularmachines.common.core.Constants;
import modularmachines.common.core.Registry;
import modularmachines.common.core.TabModularMachines;
import modularmachines.common.utils.Translator;
import modularmachines.common.utils.capabilitys.EnergyStorageItem;
import modularmachines.common.utils.content.IEnergyItem;
import modularmachines.common.utils.content.IItemModelRegister;

public class ItemBattery extends Item implements IEnergyItem, IItemModelRegister {
	
	public static final int[] CAPACITY = new int[]{15000, 100000, 250000, 500000, 1000000, 2500000, 5000000};
	public static final String[] NAMES = new String[]{"wood", "iron", "invar", "steel", "", "", ""};
	
	public ItemBattery() {
		setCreativeTab(TabModularMachines.tabModules);
		setUnlocalizedName("battery");
		setRegistryName("battery");
		setHasSubtypes(true);
		setMaxStackSize(1);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedItemName(getUnlocalizedName().replace("item.", "") + "." + NAMES[stack.getItemDamage()]);
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		NBTTagCompound tagCombound = stack.getTagCompound();
		int energy = EnergyStorageItem.getEnergy(tagCombound);
		int capacity = getCapacity(stack);
		return MathHelper.hsvToRGB(Math.max(1.0F, 1 - (float) (capacity - energy) / capacity) / 3.0F, 1.0F, 1.0F);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return EnergyStorageItem.hasEnergy(stack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (!EnergyStorageItem.hasEnergy(tagCompound)) {
			return;
		}
		tooltip.add(Translator.translateToLocal("mm.tooltip.energy") + EnergyStorageItem.getEnergy(tagCompound) + " / " + getCapacity(stack));
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		NBTTagCompound tagCombound = stack.getTagCompound();
		if (!EnergyStorageItem.hasEnergy(tagCombound)) {
			return 0.0D;
		}
		double energy = EnergyStorageItem.getEnergy(tagCombound) * 100 / getCapacity(stack) * 100;
		return 1 - (energy / 10000);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if (isInCreativeTab(tab)) {
			for (int i = 0; i < CAPACITY.length; i++) {
				subItems.add(EnergyStorageItem.createItemStack(this, 1, i, true));
				subItems.add(EnergyStorageItem.createItemStack(this, 1, i, false));
			}
		}
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new EnergyStorageItem(stack, this);
	}
	
	@Override
	public int getCapacity(ItemStack itemStack) {
		return CAPACITY[itemStack.getMetadata()];
	}
	
	@Override
	public Item getItem() {
		return this;
	}
	
	@SideOnly(Side.CLIENT)
	private static ModelResourceLocation[][] LOCATIONS = new ModelResourceLocation[CAPACITY.length][2];
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModels(Item item, ModelManager manager) {
		for (int i = 0; i < CAPACITY.length; i++) {
			manager.registerItemModel(item, new BatteryMeshDefinition());
			ModelBakery.registerItemVariants(item, LOCATIONS[i][0] = new ModelResourceLocation(Constants.MOD_ID + ":battery_" + NAMES[i] + "_full"), LOCATIONS[i][1] = new ModelResourceLocation(Constants.MOD_ID + ":battery_" + NAMES[i] + "_empty"));
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static class BatteryMeshDefinition implements ItemMeshDefinition {
		
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			if (EnergyStorageItem.getEnergy(stack) >= EnergyStorageItem.getCapacity(stack)) {
				return LOCATIONS[stack.getItemDamage()][0];
			}
			return LOCATIONS[stack.getItemDamage()][1];
		}
	}
}
