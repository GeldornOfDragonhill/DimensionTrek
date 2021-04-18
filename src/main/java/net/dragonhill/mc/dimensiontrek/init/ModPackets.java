package net.dragonhill.mc.dimensiontrek.init;

import net.dragonhill.mc.dimensiontrek.net.ModNetworkChannel;
import net.dragonhill.mc.dimensiontrek.ui.ScreenClientUpdateMessage;

public class ModPackets {
    public static void registerPackets() {
        ModNetworkChannel.registerMessage(ScreenClientUpdateMessage.class, ScreenClientUpdateMessage::new);
    }
}
