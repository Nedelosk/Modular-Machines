package nedelosk.modularmachines.common.items.parts;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.materials.Tags;
import nedelosk.modularmachines.api.parts.IMachinePart;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.core.TabModularMachines;
import nedelosk.modularmachines.common.modular.utils.MaterialManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public abstract class ItemMachinePart extends Item implements IMachinePart{

	public String name;
	public IIcon[] icons;
	public int renderPasses;
	protected PartType[] requiredComponents;
	
	public ItemMachinePart(PartType[] requiredComponents, String name) {
        this.setHasSubtypes(true);
		this.setUnlocalizedName("part" + name);
		this.setCreativeTab(TabModularMachines.components);
		
		this.name = name;
		if(requiredComponents != null)
			this.renderPasses = requiredComponents.length;
		else{
			this.renderPasses = 7;
		}
		this.requiredComponents = requiredComponents;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IItemRenderer getPartRenderer(){
		return null;
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return renderPasses;
	}
	
	@Override
	public void registerIcons(IIconRegister IIconRegister) {
		icons = new IIcon[renderPasses];
		for(int i = 0;i < renderPasses;i++)
			icons[i] = IIconRegister.registerIcon("modularmachines:machine_parts/" + name + "." + i);
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return icons[pass];
	}
	
	@Override
	public String getTagKey() {
		return Tags.TAG_MACHINE;
	}
	
	@Override
	public String getPartName() {
		return name;
	}
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return getMaterials(stack)[pass].primaryColor;
	}

	@Override
	public ItemStack buildItemFromStacks(ItemStack[] stacks) {
	    List<Material> materials = new ArrayList<Material>(stacks.length);

	    if(stacks.length != requiredComponents.length) {
	      return null;
	    }

	    // not a valid part arrangement for this tool
	    for(int i = 0; i < stacks.length; i++) {
	      if(!validComponent(i, stacks[i])) {
	        return null;
	      }

	      materials.add(MaterialManager.getMaterial(stacks[i]));
	    }

	    return buildItem(materials);
	}
	
	public ItemStack buildItem(List<Material> materials) {
		ItemStack tool = new ItemStack(this);
		tool.setTagCompound(buildItemNBT(materials));

		return tool;
	}

	@Override
	public boolean validComponent(int slot, ItemStack stack) {
	    if(slot > requiredComponents.length || slot < 0) {
	        return false;
	    }

	    return requiredComponents[slot].isValid(stack);
	}
	
	public NBTTagCompound buildItemNBT(List<Material> materials) {
		NBTTagCompound basetag = new NBTTagCompound();

		basetag.setTag(Tags.TAG_BASE, buildTag(materials));
		basetag.setTag(getTagKey(), buildData(materials));

		return basetag;
	}
	
	public NBTTagCompound buildTag(List<Material> materials){
	    NBTTagCompound base = new NBTTagCompound();
	    NBTTagList materialList = new NBTTagList();

	    for(Material material : materials) {
	      materialList.appendTag(new NBTTagString(material.identifier));
	    }

	    base.setTag(Tags.TAG_MATERIALS, materialList);

	    return base;
	}
	
	public abstract NBTTagCompound buildData(List<Material> materials);

	@Override
	public PartType[] getMachineComponents() {
		return requiredComponents;
	}

	@Override
	public Material[] getMaterials(ItemStack stack) {
		List<Material> listMaterial = new ArrayList();
		NBTTagList list = stack.getTagCompound().getCompoundTag(Tags.TAG_BASE).getTagList(Tags.TAG_MATERIALS, 8);
		for(int i = 0;i < list.tagCount();i++){
			String identifier = list.getStringTagAt(i);
			for(Material material : MMRegistry.materials){
				if(material.identifier.equals(identifier)){
					listMaterial.add(material);
					continue;
				}
			}
		}
		return listMaterial.toArray(new Material[listMaterial.size()]);
	}
	
	@Override
	public MaterialType getMaterialType() {
		return null;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
	    for(Material head : MMRegistry.materials) {
	        if(!head.hasStats(Tags.TAG_MACHINE))
	          continue;

	        List<Material> mats = new ArrayList<Material>(requiredComponents.length);

	        for(int i = 0; i < requiredComponents.length; i++) {
	          mats.add(head);
	        }

	        ItemStack tool = buildItem(mats);
	        subItems.add(tool);
	      }
	}

}
