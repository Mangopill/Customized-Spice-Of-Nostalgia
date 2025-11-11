package mangopill.customized_spice_of_nostalgia.common.core.network;

import mangopill.customized_spice_of_nostalgia.common.core.PlayerFoodData;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HandleOnClient {
    public static void handleOnClient(PlayerFoodDataPack dataPack, Supplier<NetworkEvent.Context> context) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.getFoodData() instanceof PlayerFoodData foodData) {
            foodData.getRecentAdd().clear();
            foodData.getRecentAdd().addAll(dataPack.recentAdd());
            foodData.setFoodExpectation(dataPack.foodExpectation());
            foodData.setSaturationExpectation(dataPack.saturationExpectation());
        }
    }
}