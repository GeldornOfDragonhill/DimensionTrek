package net.dragonhill.mc.dimensiontrek.util.observables;

import net.minecraft.network.PacketBuffer;

public class IntObservableValue extends ObservableValueBase<IntObservableValue> {
    private int value;

    public int get() {
        return this.value;
    }

    public void set(int value) {
        if(this.value != value) {
            this.setChanged();
            this.value = value;
        }
    }

    public IntObservableValue() {
        this.value = 0;
    }

    public IntObservableValue(int value) {
        this.value = value;
    }

    @Override
    protected void serialize(PacketBuffer buffer) {
        buffer.writeInt(this.value);
    }

    @Override
    protected void deserialize(PacketBuffer buffer) {
        this.value = buffer.readInt();
        this.executeCallback(this);
    }
}
