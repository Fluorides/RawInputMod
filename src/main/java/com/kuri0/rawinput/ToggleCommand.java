package com.kuri0.rawinput;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MouseHelper;

public class ToggleCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "rawinput";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Toggles Raw Input (/rawinput)";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (Minecraft.getMinecraft().mouseHelper instanceof RawMouseHelper) {
            Minecraft.getMinecraft().mouseHelper = new MouseHelper();
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Toggled OFF."));
        } else {
            Minecraft.getMinecraft().mouseHelper = new RawMouseHelper();
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Toggled ON."));
        }
    }
    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
