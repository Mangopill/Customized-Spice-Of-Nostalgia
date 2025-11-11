package mangopill.customized_spice_of_nostalgia.common.core;

import mangopill.customized_spice_of_nostalgia.CustomizedSpiceOfNostalgia;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static mangopill.customized_spice_of_nostalgia.common.CustomizedSpiceOfNostalgiaConfig.*;

@Mod.EventBusSubscriber(modid = CustomizedSpiceOfNostalgia.MODID)
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
            playerFoodData.setFoodExpectation(Mth.clamp(playerFoodData.getFoodExpectation() - DECREASE_FOOD_EXPECTATION_WHEN_WAKE_UP.get().floatValue(), 0.0F, MAX_FOOD_EXPECTATION.get().floatValue()));
            playerFoodData.setSaturationExpectation(Mth.clamp(playerFoodData.getSaturationExpectation() - DECREASE_SATURATION_EXPECTATION_WHEN_WAKE_UP.get().floatValue(), 0.0F, MAX_SATURATION_EXPECTATION.get().floatValue()));
            playerFoodData.sync();
        }
    }
}
