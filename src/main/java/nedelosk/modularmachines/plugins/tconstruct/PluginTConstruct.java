package nedelosk.modularmachines.plugins.tconstruct;

import static net.minecraft.util.EnumChatFormatting.AQUA;
import static net.minecraft.util.EnumChatFormatting.DARK_AQUA;
import static net.minecraft.util.EnumChatFormatting.DARK_GRAY;
import static net.minecraft.util.EnumChatFormatting.DARK_GREEN;
import static net.minecraft.util.EnumChatFormatting.DARK_PURPLE;
import static net.minecraft.util.EnumChatFormatting.DARK_RED;
import static net.minecraft.util.EnumChatFormatting.GOLD;
import static net.minecraft.util.EnumChatFormatting.GRAY;
import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.LIGHT_PURPLE;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.WHITE;
import static net.minecraft.util.EnumChatFormatting.YELLOW;

import nedelosk.modularmachines.api.basic.machine.part.MaterialType;
import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.items.ItemMachinePattern;
import nedelosk.nedeloskcore.plugins.basic.Plugin;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.crafting.StencilBuilder;
import tconstruct.library.util.IPattern;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.weaponry.TinkerWeaponry;

public class PluginTConstruct extends Plugin{

	@Override
	public void preInit() {
		MMRegistry.addMachineMaterial(MaterialType.METAL, "Cobalt", 4, 2, DARK_AQUA.toString(), 0x2376DD);
		MMRegistry.addMachineMaterial(MaterialType.METAL, "Ardite", 4, 0, DARK_RED.toString(), 0xA53000);
		MMRegistry.addMachineMaterial(MaterialType.METAL, "Manyullyn", 5, 0, DARK_PURPLE.toString(), 0x7338A5);
		MMRegistry.addMachineMaterial(MaterialType.METAL, "Alumite", 4, 2, LIGHT_PURPLE.toString(), 0xffa7e9);
		MMRegistry.addMachineMaterial(MaterialType.METAL, "PigIron", 3, 1, RED.toString(), 0xF0A8A4);
		
		int[] costs = new int[]{ 1 };
		String[] components = new String[]{ "connection_wires" };
		
		MMItems.WoodPattern.registerItem(new ItemMachinePattern("wood", components, costs).setCreativeTab(TConstructRegistry.materialTab));
		MMItems.MetalPattern.registerItem(new ItemMachinePattern("metal", components, costs).setCreativeTab(TConstructRegistry.materialTab));
	}
	
	@Override
	public void init() {
		Item[] patternOutputs = new Item[] { MMItems.Component_Connection_Wires.item() };
		int[] liquidDamage = new int[] { 2, 13, 10, 11, 12, 14, 15, 6, 16, 18 };
		
        LiquidCasting tableCasting = TConstructRegistry.getTableCasting();
        for (int i = 0; i < patternOutputs.length; i++) {
            ItemStack cast = new ItemStack(MMItems.MetalPattern.item(), 1, i);

            tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue), new ItemStack(patternOutputs[i], 1, Short.MAX_VALUE), false, 50);
            tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenGoldFluid, TConstruct.ingotLiquidValue * 2), new ItemStack(patternOutputs[i], 1, Short.MAX_VALUE), false, 50);

            for (int iterTwo = 0; iterTwo < TinkerSmeltery.liquids.length; iterTwo++) {
                Fluid fs = TinkerSmeltery.liquids[iterTwo].getFluid();
                int fluidAmount = ((IPattern)MMItems.MetalPattern.item()).getPatternCost(cast) * TConstruct.ingotLiquidValue / 2;
                ItemStack metalCast = new ItemStack(patternOutputs[i], 1, liquidDamage[iterTwo]);
                tableCasting.addCastingRecipe(metalCast, new FluidStack(fs, fluidAmount), cast, 50);
                Smeltery.addMelting(FluidType.getFluidType(fs), metalCast, 0, fluidAmount);
            }
        }
        for (int mat = 0; mat < 18; mat++)
        {
            for (int meta = 0; meta < patternOutputs.length; meta++)
            {
                if (patternOutputs[meta] != null)
                    TConstructRegistry.addPartMapping(MMItems.WoodPattern.item(), meta, mat, new ItemStack(patternOutputs[meta], 1, mat));
            }
        }
        StencilBuilder.registerStencil(28, MMItems.WoodPattern.item(), 0);

        PatternBuilder.instance.addToolPattern((IPattern) MMItems.WoodPattern.item());
	}
	
	@Override
	public void postInit() {
	}
	
	@Override
	public String getRequiredMod() {
		return "TConstruct";
	}
	
}
