package nedelosk.modularmachines.common.network.packets.techtree.editor;

import java.io.File;
import java.io.IOException;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.api.basic.techtree.TechPointTypes;
import nedelosk.modularmachines.api.basic.techtree.TechTreeCategories;
import nedelosk.modularmachines.api.basic.techtree.TechTreeEntry;
import nedelosk.modularmachines.api.basic.techtree.TechTreePage;
import nedelosk.modularmachines.api.basic.techtree.TechTreePage.PageType;
import nedelosk.modularmachines.client.techtree.utils.TechTreeUtils.Writer;
import nedelosk.modularmachines.common.ModularMachines;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class PacketTechTreeEditor implements IMessage, IMessageHandler<PacketTechTreeEditor, IMessage>{

	public TechTreeEntry entry;
	
	public PacketTechTreeEditor() {
	}
	
	public PacketTechTreeEditor(TechTreeEntry entry) {
		this.entry = entry;
	}
	
	@Override
	public IMessage onMessage(PacketTechTreeEditor message, MessageContext ctx) {
		try {
			File categoryFile = new File(ModularMachines.configFolder + "/techtree/categorys", message.entry.category);
			if(!categoryFile.exists())
			{
				categoryFile.mkdirs();
			}
			Writer.writeEntry(message.entry, categoryFile);
			TechTreeCategories.addEntry(message.entry);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entry = new TechTreeEntry(ByteBufUtils.readUTF8String(buf), ByteBufUtils.readUTF8String(buf), buf.readInt(), TechPointTypes.values()[buf.readInt()], buf.readInt(), buf.readInt(), ByteBufUtils.readItemStack(buf));
		if(buf.readBoolean())
			entry.isAutoUnlock();
		if(buf.readBoolean())
			entry.isConcealed();
		if(buf.readBoolean()){
			int parent = buf.readInt();
			String[] parents = new String[parent];
			for(int i = 0;i < parent;i++){
				parents[i] = ByteBufUtils.readUTF8String(buf);
			}
			entry.setParents(parents);
		}
		if(buf.readBoolean()){
			int sibling = buf.readInt();
			String[] siblings = new String[sibling];
			for(int i = 0;i < sibling;i++){
				siblings[i] = ByteBufUtils.readUTF8String(buf);
			}
			entry.setSiblings(siblings);
		}
		if(buf.readBoolean()){
			int page = buf.readInt();
			TechTreePage[] pages = new TechTreePage[page];
			for(int i = 0;i < page;i++)
				pages[i] = pageFromBytes(buf);
			entry.setPages(pages);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, entry.key);
		ByteBufUtils.writeUTF8String(buf, entry.category);
		buf.writeInt(entry.getTechPoints());
		buf.writeInt(entry.getTechPointType().ordinal());
		buf.writeInt(entry.displayColumn);
		buf.writeInt(entry.displayRow);
		ByteBufUtils.writeItemStack(buf, entry.icon_item);
		buf.writeBoolean(entry.isAutoUnlock());
		buf.writeBoolean(entry.isConcealed());
		buf.writeBoolean(entry.parents != null);
		if(entry.parents != null){
			buf.writeInt(entry.parents.length);
			for(String parent : entry.parents)
				ByteBufUtils.writeUTF8String(buf, parent);
		}
		buf.writeBoolean(entry.siblings != null);
		if(entry.siblings != null){
			buf.writeInt(entry.siblings.length);
			for(String sibling : entry.siblings)
				ByteBufUtils.writeUTF8String(buf, sibling);
		}
		buf.writeBoolean(entry.getPages() != null);
		if(entry.getPages() != null){
			buf.writeInt(entry.getPages().length);
			for(TechTreePage page : entry.getPages())
				pageToBytes(buf, page);
		}
	}
	
	public TechTreePage pageFromBytes(ByteBuf buf){
		PageType type = PageType.values()[buf.readInt()];
		int ID = buf.readInt();
		switch (type) {
		case TEXT:
			return new TechTreePage(ByteBufUtils.readUTF8String(buf), ID);
		case TEXT_CONCEALED:
			String text = ByteBufUtils.readUTF8String(buf);
			String entry = ByteBufUtils.readUTF8String(buf);
			return new TechTreePage(entry, text, ID);
		case IMAGE:
			String caption = ByteBufUtils.readUTF8String(buf);
			ResourceLocation image = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
			return new TechTreePage(image, caption, ID);
		case NORMAL_CRAFTING:
			ItemStack recipeOutput = ByteBufUtils.readItemStack(buf);
			IRecipe recipe = null;
            for (Object craft : CraftingManager.getInstance().getRecipeList()) {
                if (craft instanceof IRecipe) {
                    IRecipe theCraft = (IRecipe) craft;
                    if (theCraft.getRecipeOutput() != null && areEqual(theCraft.getRecipeOutput(), ByteBufUtils.readItemStack(buf))) {
                       recipe = theCraft;
                    }
                }
            }
			return new TechTreePage(recipe, ID);
		case SMELTING:
			ItemStack recipeInput = ByteBufUtils.readItemStack(buf);
			return new TechTreePage(recipeInput, ID);
		default:
			return null;
		}
	}
	
    public static boolean areEqual(ItemStack stack, ItemStack stack2) {
        if (stack == null || stack2 == null) return false;
    	return stack.isItemEqual(stack2);
    }
	
	public void pageToBytes(ByteBuf buf, TechTreePage page){
		buf.writeInt(page.type.ordinal());
		buf.writeInt(page.ID);
		switch (page.type) {
		case TEXT:
			ByteBufUtils.writeUTF8String(buf, page.text);
			break;
		case TEXT_CONCEALED:
			ByteBufUtils.writeUTF8String(buf, page.text);
			ByteBufUtils.writeUTF8String(buf, page.entry);
			break;
		case IMAGE:
			ByteBufUtils.writeUTF8String(buf, page.text);
			ByteBufUtils.writeUTF8String(buf, page.image.toString());
			break;
		case NORMAL_CRAFTING:
			ByteBufUtils.writeItemStack(buf, page.recipeOutput);
			break;
		case SMELTING:
			ByteBufUtils.writeItemStack(buf, (ItemStack) page.recipe);
			break;
		default:
			break;
		}
	}

}
