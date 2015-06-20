package nedelosk.nedeloskcore.api;

import java.util.ArrayList;
import java.util.List;

import nedelosk.nedeloskcore.api.book.Knowledge;
import nedelosk.nedeloskcore.api.book.BookLevel;
import nedelosk.nedeloskcore.api.crafting.IPlanRecipe;
import nedelosk.nedeloskcore.api.crafting.OreStack;
import nedelosk.nedeloskcore.api.plan.IPlanEnum;
import nedelosk.nedeloskcore.common.book.BookData;
import nedelosk.nedeloskcore.common.plan.PlanRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;

public class NCoreApi {
	
	public static IPlanRecipe planRecipe;
	
	public static List<IPlanEnum> plans = new ArrayList<IPlanEnum>();
	
	public static List<Knowledge> knowledges = new ArrayList<Knowledge>();
	
	public static Knowledge basicKnowledge, dwarfKnowledge, elvenKnowledge;
	public static BookLevel traineeLevel, advancedtraineeLevel, trainedLevel, masterLevel, grandMasterLevel;
	
	public static Knowledge registerKnowledge(String unlocalizedName)
	{
		Knowledge knowledge = new Knowledge(unlocalizedName);
		knowledges.add(knowledge);
		return knowledge;
	}
	
	static{
		basicKnowledge = registerKnowledge("minecarft");
		dwarfKnowledge = registerKnowledge("ondrosch");
		elvenKnowledge = registerKnowledge("alfheim");
		
		traineeLevel = new BookLevel("trainee", EnumChatFormatting.GREEN);
		advancedtraineeLevel = new BookLevel("advancedtrainee", EnumChatFormatting.BLUE);
		trainedLevel = new BookLevel("trained", EnumChatFormatting.DARK_GREEN);
		masterLevel = new BookLevel("master", EnumChatFormatting.RED);
		grandMasterLevel = new BookLevel("grandMaster", EnumChatFormatting.DARK_RED);
	}
	
	public static void registerPlan(IPlanEnum plan)
	{
		plans.add(plan);
	}
	
	public static ItemStack setItemPlan(Item item, PlanRecipe plan)
	{
		ItemStack planStack = new ItemStack(item, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		for(int i = 0;i < plan.stages;i++)
		{
			NBTTagList list = new NBTTagList();
			for(ItemStack stack : plan.input[i])
			{
				NBTTagCompound tagNBT = new NBTTagCompound();
				stack.writeToNBT(tagNBT);
				list.appendTag(tagNBT);
			}
			tag.setTag("input" + i, list);
		}
		tag.setInteger("stages", plan.stages);
		NBTTagCompound nbtTag = new NBTTagCompound();
		plan.outputBlock.writeToNBT(nbtTag);
		tag.setTag("Output", nbtTag);
		if(plan.updateBlock != null){
		NBTTagCompound nbtTagU = new NBTTagCompound();
		plan.updateBlock.writeToNBT(nbtTagU);
		tag.setTag("UpdateBlock", nbtTagU);
		}
		planStack.setTagCompound(tag);
		return planStack;
	}
	
}
