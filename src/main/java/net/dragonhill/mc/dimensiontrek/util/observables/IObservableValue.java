package net.dragonhill.mc.dimensiontrek.util.observables;

import net.minecraft.network.PacketBuffer;

public interface IObservableValue {
    boolean getChanged();
    void writeToBuffer(PacketBuffer buffer);
    void readFromBuffer(PacketBuffer buffer);
}
