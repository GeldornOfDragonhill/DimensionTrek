package net.dragonhill.mc.dimensiontrek.util.observables;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObservableValueList {
    private boolean wasUpdated = false;
    private final List<IObservableValue> observableValues = new ArrayList<>();

    public boolean getWasUpdated() {
        return this.wasUpdated;
    }

    public <V extends IObservableValue> V add(V value) {
        this.observableValues.add(value);
        return value;
    }

    public void createPayloadForChanged(Consumer<PacketBuffer> payloadConsumer) {
        boolean actionRequired = false;

        for (IObservableValue observableValue : this.observableValues) {
            if(observableValue.getChanged()) {
                actionRequired = true;
                break;
            }
        }

        if(actionRequired) {
            final PacketBuffer payloadBuffer = new PacketBuffer(Unpooled.buffer());

            for (IObservableValue observableValue : this.observableValues) {
                observableValue.writeToBuffer(payloadBuffer);
            }

            payloadConsumer.accept(payloadBuffer);
        }
    }

    public void updateFromPayload(PacketBuffer payload) {
        for (IObservableValue observableValue : this.observableValues) {
            observableValue.readFromBuffer(payload);
        }

        this.wasUpdated = true;
    }
}
