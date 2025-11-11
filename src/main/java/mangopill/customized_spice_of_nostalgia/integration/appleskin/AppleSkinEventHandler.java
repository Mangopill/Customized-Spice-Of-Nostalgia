package mangopill.customized_spice_of_nostalgia.integration.appleskin;

import mangopill.customized_spice_of_nostalgia.common.core.PlayerFoodData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.food.FoodValues;

public class AppleSkinEventHandler {
    @SubscribeEvent
    public void onFoodValuesEvent(FoodValuesEvent event) {
        Player player = event.player;
        ItemStack stack = event.itemStack;
        FoodProperties properties = event.itemStack.getItem().getFoodProperties(stack, player);
        if (properties != null && player.getFoodData() instanceof PlayerFoodData foodData) {
            PlayerFoodData.ModifiedFoodData modifiedFoodData = foodData.getResult(properties.getNutrition(), properties.getNutrition() * properties.getSaturationModifier() * 2.0F);
            event.modifiedFoodValues = new FoodValues(modifiedFoodData.modifiedFoodLevel(), modifiedFoodData.modifiedSaturationLevel() / (modifiedFoodData.modifiedFoodLevel() * 2.0F));
        }
    }
}