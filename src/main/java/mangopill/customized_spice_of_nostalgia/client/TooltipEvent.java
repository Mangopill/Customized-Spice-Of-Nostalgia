package mangopill.customized_spice_of_nostalgia.client;

import mangopill.customized_spice_of_nostalgia.CustomizedSpiceOfNostalgia;
import mangopill.customized_spice_of_nostalgia.common.core.PlayerFoodData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static mangopill.customized_spice_of_nostalgia.common.CustomizedSpiceOfNostalgiaConfig.*;

@Mod.EventBusSubscriber(modid = CustomizedSpiceOfNostalgia.MODID, value = Dist.CLIENT)
public class TooltipEvent {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        List<Component> components = event.getToolTip();
        if (stack.getFoodProperties(null) == null || player == null || player.getFoodData() == null || !SHOW_FOOD_DATA_TOOLTIP.get()) {
            return;
        }
        if (player.getFoodData() instanceof PlayerFoodData foodData ) {
            components.add(Component.translatable("tooltip." + CustomizedSpiceOfNostalgia.MODID + ".food_data",
                    String.format("%.2f", foodData.diversityScore()), String.format("%.2f", foodData.getFoodExpectation()), String.format("%.2f", foodData.getSaturationExpectation()))
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
