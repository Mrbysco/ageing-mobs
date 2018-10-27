package com.svennieke.AgeingMobs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.svennieke.AgeingMobs.config.AgeingMobsConfigGen;
import com.svennieke.AgeingMobs.handlers.AgeingHandler;
import com.svennieke.AgeingMobs.init.ModTiles;
import com.svennieke.AgeingMobs.lists.AgeList;
import com.svennieke.AgeingMobs.proxy.CommonProxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, 
	name = Reference.MOD_NAME, 
	version = Reference.VERSION, 
	acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)

public class AgeingMobs {
	@Instance(Reference.MOD_ID)
	public static AgeingMobs instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{	
		logger.info("Registering config / checking config");
		MinecraftForge.EVENT_BUS.register(new AgeingMobsConfigGen());

		ModTiles.register();
		
		proxy.Preinit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		logger.info("Registering Event Handler");
		MinecraftForge.EVENT_BUS.register(new AgeingHandler());
		

		logger.info("Registering Ageing List");
		AgeList.initializeAgeing();
		
		proxy.Init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.Postinit();
	}
}
