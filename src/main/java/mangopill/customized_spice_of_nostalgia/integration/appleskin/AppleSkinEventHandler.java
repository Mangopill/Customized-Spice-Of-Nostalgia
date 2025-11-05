package mangopill.customized_spice_of_nostalgia.integration.appleskin;

import mangopill.customized_spice_of_nostalgia.common.core.PlayerFoodData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import squeek.appleskin.api.event.FoodValuesEvent;

public class AppleSkinEventHandler {
    @SubscribeEvent
    public void onFoodValuesEvent(FoodValuesEvent event) {
        Player player = event.player;
        ItemStack stack = event.itemStack;
        FoodProperties properties = event.itemStack.getItem().getFoodProperties(stack, player);
        if (properties != null && player.getFoodData() instanceof PlayerFoodData foodData) {
            PlayerFoodData.ModifiedFoodData modifiedFoodData = foodData.getResult(properties.nutrition(), properties.saturation());
            FoodProperties.Builder builder = new FoodProperties.Builder()
                    .nutrition(modifiedFoodData.modifiedFoodLevel())
                    .saturationModifier(modifiedFoodData.modifiedSaturationLevel() / (modifiedFoodData.modifiedFoodLevel() * 2.0F));
            if (properties.eatSeconds() <= 0.8F) {
                builder.fast();
            }
            if (properties.canAlwaysEat()) {
                builder.alwaysEdible();
            }
            for (FoodProperties.PossibleEffect effect : properties.effects()) {
                builder.effect(effect.effectSupplier(), effect.probability());
            }
            if (properties.usingConvertsTo().isPresent()) {
                builder.usingConvertsTo(properties.usingConvertsTo().get().getItem());
            }
            event.modifiedFoodProperties = builder.build();
        }
    }
}