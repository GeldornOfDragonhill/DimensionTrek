package net.dragonhill.mc.dimensiontrek.util.observables;

import net.minecraft.network.PacketBuffer;

public class FloatObservableValue extends ObservableValueBase<FloatObservableValue> {
    private float value;

    public float get() {
        return this.value;
    }

    public void set(int value) {
        if(this.value != value) {
            this.setChanged();
            this.value = value;
        }
    }

    public FloatObservableValue() {
        this.value = 0f;
    }

    public FloatObservableValue(int value) {
        this.value = value;
    }

    @Override
    protected void serialize(PacketBuffer buffer) {
        buffer.writeFloat(this.value);
    }

    @Override
    protected void deserialize(PacketBuffer buffer) {
        this.value = buffer.readFloat();
        this.executeCallback(this);
    }
}
