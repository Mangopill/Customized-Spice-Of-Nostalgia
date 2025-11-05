package mangopill.customized_spice_of_nostalgia.common.core.network;

import mangopill.customized_spice_of_nostalgia.common.core.PlayerFoodData;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    public static void handleDataOnMain(final CSONData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().getFoodData() instanceof PlayerFoodData foodData) {
                foodData.getRecentAdd().clear();
                foodData.getRecentAdd().addAll(data.data().recentAdd());
                foodData.setFoodExpectation(data.data().foodExpectation());
                foodData.setSaturationExpectation(data.data().saturationExpectation());
            }
        });
    }
}