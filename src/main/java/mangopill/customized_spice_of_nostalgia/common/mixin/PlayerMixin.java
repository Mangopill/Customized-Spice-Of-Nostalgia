package mangopill.customized_spice_of_nostalgia.common.mixin;

import mangopill.customized_spice_of_nostalgia.common.core.PlayerFoodData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Redirect(method = "<init>", at = @At(value = "NEW", target = "net/minecraft/world/food/FoodData"))
    private FoodData createPlayerFoodData() {
        Player player = (Player)(Object)this;
        return new PlayerFoodData(player);
    }
}