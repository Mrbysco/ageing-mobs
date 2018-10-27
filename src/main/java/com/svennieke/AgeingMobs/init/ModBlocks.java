package com.svennieke.AgeingMobs.init;

import java.util.ArrayList;

import com.svennieke.AgeingMobs.block.BlockEndermite;
import com.svennieke.AgeingMobs.config.AgeingMobsConfigGen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class ModBlocks {
	public static BlockEndermite PURPUR_BLOCK_EGG;
	public static BlockEndermite PURPUR_PILLAR_EGG;
	public static BlockEndermite PURPUR_DOUBLESLAB_EGG;
	
	public static ArrayList<Block> BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        
        if(AgeingMobsConfigGen.general.endermiteshulker.endermiteToShulkerAgeing)
        {
            PURPUR_BLOCK_EGG = registerBlock(new BlockEndermite("endermite_egg_purpur", Blocks.PURPUR_BLOCK));
            PURPUR_PILLAR_EGG = registerBlock(new BlockEndermite("endermite_egg_pillar", Blocks.PURPUR_PILLAR));
            PURPUR_DOUBLESLAB_EGG = registerBlock(new BlockEndermite("endermite_egg_doubleslab", Blocks.PURPUR_DOUBLE_SLAB));
        }
        
        registry.registerAll(BLOCKS.toArray(new Block[0]));
    }
    
    public static <T extends Block> T registerBlock(T block)
    {
        return registerBlock(block, new ItemBlock(block));
    }
    
    public static <T extends Block> T registerBlock(T block, ItemBlock item)
    {
    	item.setRegistryName(block.getRegistryName());
    	ModItems.ITEMS.add(item);
        BLOCKS.add(block);
        return block;
    }
	
}
