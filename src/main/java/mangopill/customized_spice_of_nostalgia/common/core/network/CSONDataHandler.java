package mangopill.customized_spice_of_nostalgia.common.core.network;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static mangopill.customized_spice_of_nostalgia.CustomizedSpiceOfNostalgia.*;

public class CSONDataHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            getCLoc("cson_data"),
            () -> PROTOCOL_VERSION,
            clientVersion -> PROTOCOL_VERSION.equals(clientVersion)
                    || NetworkRegistry.ACCEPTVANILLA.equals(clientVersion)
                    || NetworkRegistry.ABSENT.version().equals(clientVersion),
            serverVersion -> PROTOCOL_VERSION.equals(serverVersion)
                    || NetworkRegistry.ACCEPTVANILLA.equals(serverVersion)
                    || NetworkRegistry.ABSENT.version().equals(serverVersion)
    );

    private static int packetId = 0;

    public static void register() {
        INSTANCE.registerMessage(
                packetId++,
                PlayerFoodDataPack.class,
                PlayerFoodDataPack::encode,
                PlayerFoodDataPack::decode,
                PlayerFoodDataPack::handle
        );
    }
}
