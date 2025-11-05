package mangopill.customized_spice_of_nostalgia.common.core.network;

import io.netty.buffer.ByteBuf;
import mangopill.customized_spice_of_nostalgia.common.core.PlayerFoodData;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static mangopill.customized_spice_of_nostalgia.CustomizedSpiceOfNostalgia.*;

public record CSONData(PlayerFoodData.PlayerFoodDataPack data) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CSONData> TYPE = new CustomPacketPayload.Type<>(getCLoc("cson_data"));

    public static final StreamCodec<ByteBuf, CSONData> STREAM_CODEC = StreamCodec.composite(
            PlayerFoodData.PlayerFoodDataPack.STREAM_CODEC,
            CSONData::data,
            CSONData::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
