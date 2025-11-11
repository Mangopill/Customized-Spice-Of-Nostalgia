package mangopill.customized_spice_of_nostalgia.common.core.network;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

public record PlayerFoodDataPack(Queue<Pair<Integer, Float>> recentAdd, float foodExpectation,
                                 float saturationExpectation) {
    public static void handle(PlayerFoodDataPack packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    HandleOnClient.handleOnClient(packet, context)
            );
        });
        context.get().setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(recentAdd.size());
        for (Pair<Integer, Float> pair : recentAdd) {
            buffer.writeInt(pair.getFirst());
            buffer.writeFloat(pair.getSecond());
        }
        buffer.writeFloat(foodExpectation);
        buffer.writeFloat(saturationExpectation);
    }

    public static PlayerFoodDataPack decode(FriendlyByteBuf buffer) {
        int queueSize = buffer.readInt();
        Queue<Pair<Integer, Float>> recentAdd = new LinkedList<>();
        for (int i = 0; i < queueSize; i++) {
            int first = buffer.readInt();
            float second = buffer.readFloat();
            recentAdd.add(Pair.of(first, second));
        }
        float foodExpectation = buffer.readFloat();
        float saturationExpectation = buffer.readFloat();
        return new PlayerFoodDataPack(recentAdd, foodExpectation, saturationExpectation);
    }
}