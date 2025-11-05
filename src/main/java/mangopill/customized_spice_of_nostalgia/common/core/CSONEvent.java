package mangopill.customized_spice_of_nostalgia.common.core;

import mangopill.customized_spice_of_nostalgia.CustomizedSpiceOfNostalgia;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;

import static mangopill.customized_spice_of_nostalgia.common.CustomizedSpiceOfNostalgiaConfig.*;

@EventBusSubscriber(modid = CustomizedSpiceOfNostalgia.MODID)
public class CSONEvent {
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player.getFoodData() instanceof PlayerFoodData playerFoodData) {
            playerFoodData.sync();
        }
    }
    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();
        if (player.getFoodData() instanceof PlayerFoodData playerFoodData) {
            playerFoodData.setFoodExpectation(Mth.clamp(playerFoodData.getFoodExpectation() - DECREASE_FOOD_EXPECTATION_WHEN_WAKE_UP.get().floatValue(), 0.0F, 20.0F));
            playerFoodData.setSaturationExpectation(Mth.clamp(playerFoodData.getSaturationExpectation() - DECREASE_SATURATION_EXPECTATION_WHEN_WAKE_UP.get().floatValue(), 0.0F, 20.0F));
            playerFoodData.sync();
        }
    }
}
