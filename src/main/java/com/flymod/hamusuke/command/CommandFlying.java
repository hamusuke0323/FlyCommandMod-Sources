package com.flymod.hamusuke.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandFlying extends CommandBase {

	@Override
	public String getName() {
		return "fly";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "command.fly.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if((sender instanceof EntityPlayer) && args[0].equalsIgnoreCase("allow")) {
			((EntityPlayer)sender).capabilities.allowFlying = true;
			((EntityPlayer)sender).sendPlayerAbilities();
			return;
		}

		if((sender instanceof EntityPlayer) && args[0].equalsIgnoreCase("noallow")) {
			((EntityPlayer)sender).capabilities.allowFlying = false;
			((EntityPlayer)sender).capabilities.isFlying = false;
			((EntityPlayer)sender).sendPlayerAbilities();
			return;
		}

		if((sender instanceof EntityPlayer) && args[0].equalsIgnoreCase("nogravity")) {
			((EntityPlayer)sender).setNoGravity(true);
			return;
		}

		if((sender instanceof EntityPlayer) && args[0].equalsIgnoreCase("gravity")) {
			((EntityPlayer)sender).setNoGravity(false);
			return;
		}

		if((sender instanceof EntityPlayer) && args[0].equalsIgnoreCase("setflyspeed")) {
			try {
				float flyspeed = Float.parseFloat(args[1]);
			}catch (NumberFormatException e) {
				sender.sendMessage(new TextComponentString(TextFormatting.RED + "invalid flyspeed,flyspeed must be decimals."));
				return;
			}

			((EntityPlayer)sender).capabilities.setFlySpeed(Float.parseFloat(args[1]));
			((EntityPlayer)sender).sendPlayerAbilities();
			return;
		}
	}
}
