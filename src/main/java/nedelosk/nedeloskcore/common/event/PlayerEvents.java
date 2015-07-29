package nedelosk.nedeloskcore.common.event;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;

import akka.actor.FSM.Event;

import com.google.common.io.Files;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import nedelosk.forestday.common.core.ForestDay;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.book.BookCategory;
import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.common.book.BookData;
import nedelosk.nedeloskcore.common.book.BookDatas;
import nedelosk.nedeloskcore.common.book.BookManager;
import nedelosk.nedeloskcore.common.core.NedeloskCore;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

public class PlayerEvents {

	  @SubscribeEvent
	  public void loadPlayerBookData(PlayerEvent.LoadFromFile event)
	  {
	  EntityPlayer p = event.entityPlayer;
	  NedeloskCore.proxy.getPlayerData().removeData(p.getDisplayName());
	   
	       File file = getPlayerFile("book", event.playerDirectory, p.getDisplayName());
	       boolean legacy = false;
	       if (!file.exists())
	       {
	         File filep = event.getPlayerFile("book");
	         if (filep.exists())
	         {
	           try
	          {
	             Files.copy(filep, file);
	             legacy = true;
	             filep.delete();
	             File fb = event.getPlayerFile("bookback");
	             if (fb.exists()) {
	               fb.delete();
	             }
	           }
	          catch (IOException e) {}
	         }
	        /*else
	         {
	           File filet = getLegacyPlayerFile(p);
	           if (filet.exists()) {
	             try
	             {
	               Files.copy(filet, file);
	               legacy = true;
	             }
	             catch (IOException e) {}
	           }
	         }*/
	       }
	       BookManager.loadBookDataFromPlayer(p, file, getPlayerFile("bookback", event.playerDirectory, p.getDisplayName()));
	       
	       //Forestday.proxy.getBookManager().unlockEntry(p, "baseWood");
	       NedeloskCore.proxy.getBookManager().unlockKnowledge(p, NCoreApi.basicKnowledge.unlocalizedName);
	       
	     }
	  
	     @SubscribeEvent
	     public void playerSave(PlayerEvent.SaveToFile event)
	     {
	       EntityPlayer p = event.entityPlayer;
	       
	       BookManager.saveBookDataFromPlayer(p, getPlayerFile("book", event.playerDirectory, p.getDisplayName()), getPlayerFile("bookback", event.playerDirectory, p.getDisplayName()));
	     }
	     
	     @SubscribeEvent
	     public void stickDrop(BlockEvent.HarvestDropsEvent event)
	     {
	    	 Random r = new Random();
	    	 if(event.block == Blocks.leaves || event.block == Blocks.leaves2)
	    	 {
	    		 if(r.nextInt(16) == 1)
	    		 {
	    		 event.drops.add(new ItemStack(Items.stick));
	    		 }
	    	 }
	     }
	  
	     public File getPlayerFile(String suffix, File playerDirectory, String playername)
	     {
	       return new File(playerDirectory, playername + "." + suffix);
	     }
	     
	     /*public File getLegacyPlayerFile(EntityPlayer player)
	        {
	          try
	          {
	            File playersDirectory = new File(player.func_110142_aN()., "players");
	            return new File(playersDirectory, player.getDisplayName() + ".book");
	          }
	          catch (Exception e)
	          {
	            e.printStackTrace();
	          }
	          return null;
	        }*/
	
}
