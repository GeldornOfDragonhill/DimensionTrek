package net.dragonhill.mc.dimensiontrek.ui;

import net.dragonhill.mc.dimensiontrek.net.MessageBase;
import net.dragonhill.mc.dimensiontrek.util.observables.IObservableValueListHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

public class ScreenClientUpdateMessage extends MessageBase {
    private int containerId;
    private PacketBuffer payload;

    public ScreenClientUpdateMessage() { }

    public ScreenClientUpdateMessage(int containerId, PacketBuffer payload) {
        this.containerId = containerId;
        this.payload = payload;
    }

    @Override
    protected void onExecuteInSidedThread(NetworkEvent.Context context) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        Container currentContainer = player.containerMenu;

        if(currentContainer.containerId != this.containerId) {
            return;
        }

        if(currentContainer instanceof IObservableValueListHolder) {
            ((IObservableValueListHolder)currentContainer).getObservableValueList().updateFromPayload(this.payload);
        }
    }

    @Override
    protected void serialize(PacketBuffer buffer) {
        buffer.writeInt(this.containerId);
        buffer.writeBytes(this.payload);
    }

    @Override
    protected void deserialize(PacketBuffer buffer) {
        this.containerId = buffer.readInt();
        this.payload = buffer;
    }

    @Override
    protected LogicalSide getExecutionSide() {
        return LogicalSide.CLIENT;
    }
}
