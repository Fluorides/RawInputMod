package com.kuri0.rawinput;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = RawInput.MODID, version = RawInput.VERSION)
public class RawInput {
    public static final String MODID = "rawinput";
    public static final String VERSION = "1.1.1";

    public static Mouse mouse;
    public static Controller[] controllers;
    // Delta for mouse
    public static int dx = 0;
    public static int dy = 0;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new RescanCommand());
        ClientCommandHandler.instance.registerCommand(new ToggleCommand());
        Minecraft.getMinecraft().mouseHelper = new RawMouseHelper();
        controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        Thread inputThread = new Thread(() -> {
            while (true) {
                for (int i = 0; i < controllers.length && mouse == null; i++) {
                    if (controllers[i].getType() == Controller.Type.MOUSE) {
                        controllers[i].poll();
                        if (((Mouse) controllers[i]).getX().getPollData() != 0.0 || ((Mouse) controllers[i]).getY().getPollData() != 0.0)
                            mouse = (Mouse) controllers[i];
                    }
                }
                if (mouse != null) {
                    mouse.poll();

                    dx += (int) mouse.getX().getPollData();
                    dy += (int) mouse.getY().getPollData();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        inputThread.setName("inputThread");
        inputThread.start();
    }
}

