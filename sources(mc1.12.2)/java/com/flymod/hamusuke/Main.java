package com.flymod.hamusuke;

import com.flymod.hamusuke.command.CommandFlying;
import com.flymod.hamusuke.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {
	public static final String MODID = "flycommand";
	public static final String MODNAME = "FlyCommandMod";
	public static final String VERSION = "4.0";
	public static final String COMMON = "com.flymod.hamusuke.proxy.CommonProxy";
	public static final String CLIENT = "com.flymod.hamusuke.proxy.ClientProxy";

	@SidedProxy(clientSide = CLIENT, serverSide = COMMON)
	public static CommonProxy proxy;

	@EventHandler
	public static void serverInit(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandFlying());
	}
}
