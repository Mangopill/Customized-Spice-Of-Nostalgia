package mangopill.customized_spice_of_nostalgia.common;

import mangopill.customized_spice_of_nostalgia.CustomizedSpiceOfNostalgia;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class CustomizedSpiceOfNostalgiaConfig {
    public static final String MOD_ID = CustomizedSpiceOfNostalgia.MODID;
    public static final ModConfigSpec COMMON_CONFIG;
    public static final ModConfigSpec CLIENT_CONFIG;

    public static final String EXPECTATION = "expectation";
    public static final ModConfigSpec.DoubleValue INITIAL_FOOD_EXPECTATION;
    public static final ModConfigSpec.DoubleValue INITIAL_SATURATION_EXPECTATION;
    public static final ModConfigSpec.DoubleValue FOOD_EXPECTATION_DECREASE_RATE;
    public static final ModConfigSpec.DoubleValue SATURATION_EXPECTATION_DECREASE_RATE;
    public static final ModConfigSpec.DoubleValue FOOD_EXPECTATION_GROWTH_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue SATURATION_EXPECTATION_GROWTH_MULTIPLIER;
    public static final ModConfigSpec.DoubleValue MAX_FOOD_EXPECTATION;
    public static final ModConfigSpec.DoubleValue MAX_SATURATION_EXPECTATION;
    public static final ModConfigSpec.IntValue MAX_RECENT_ADD_SIZE;
    public static final String PROTECTION_MECHANISM = "protection_mechanism";
    public static final ModConfigSpec.BooleanValue CANCEL_CALCULATION_WHEN_HUNGRY;
    public static final ModConfigSpec.DoubleValue DECREASE_FOOD_EXPECTATION_WHEN_WAKE_UP;
    public static final ModConfigSpec.DoubleValue DECREASE_SATURATION_EXPECTATION_WHEN_WAKE_UP;

    public static final String TOOLTIP = "tooltip";
    public static final ModConfigSpec.BooleanValue SHOW_FOOD_DATA_TOOLTIP;

    static {
        ModConfigSpec.Builder commonBuilder = new ModConfigSpec.Builder();

        commonBuilder.comment(EXPECTATION).push(EXPECTATION);
        INITIAL_FOOD_EXPECTATION = commonBuilder
                .comment("This value determines the player's initial food expectation.")
                .gameRestart()
                .translation(MOD_ID + ".config.initial_food_expectation")
                .defineInRange("initialFoodExpectation", 0.1D, 0.0D, 100.0D);
        INITIAL_SATURATION_EXPECTATION = commonBuilder
                .comment("This value determines the player's initial saturation expectation.")
                .gameRestart()
                .translation(MOD_ID + ".config.initial_saturation_expectation")
                .defineInRange("initialSaturationExpectation", 0.1D, 0.0D, 100.0D);
        FOOD_EXPECTATION_DECREASE_RATE = commonBuilder
                .comment("This value determines the rate at which food expectation decreases.")
                .gameRestart()
                .translation(MOD_ID + ".config.food_expectation_decrease_rate")
                .defineInRange("foodExpectationDecreaseRate", 0.0002D, 0.0D, 100.0D);
        SATURATION_EXPECTATION_DECREASE_RATE = commonBuilder
                .comment("This value determines the rate at which saturation expectation decreases.")
                .gameRestart()
                .translation(MOD_ID + ".config.saturation_expectation_decrease_rate")
                .defineInRange("saturationExpectationDecreaseRate", 0.0002D, 0.0D, 100.0D);
        FOOD_EXPECTATION_GROWTH_MULTIPLIER = commonBuilder
                .comment("Food expectation growth multiplier.")
                .gameRestart()
                .translation(MOD_ID + ".config.food_expectation_growth_multiplier")
                .defineInRange("foodExpectationGrowthMultiplier", 0.1D, 0.0D, 100.0D);
        SATURATION_EXPECTATION_GROWTH_MULTIPLIER = commonBuilder
                .comment("Saturation expectation growth multiplier.")
                .gameRestart()
                .translation(MOD_ID + ".config.saturation_expectation_growth_multiplier")
                .defineInRange("saturationExpectationGrowthMultiplier", 0.1D, 0.0D, 100.0D);
        MAX_FOOD_EXPECTATION = commonBuilder
                .comment("The maximum value for food expectation.")
                .gameRestart()
                .translation(MOD_ID + ".config.max_food_expectation")
                .defineInRange("maxFoodExpectation", 20.0D, 0.0D, 100.0D);
        MAX_SATURATION_EXPECTATION = commonBuilder
                .comment("The maximum value for saturation expectation.")
                .gameRestart()
                .translation(MOD_ID + ".config.max_saturation_expectation")
                .defineInRange("maxSaturationExpectation", 20.0D, 0.0D, 100.0D);
        MAX_RECENT_ADD_SIZE = commonBuilder
                .comment("This value determines the player's max recent add size.")
                .gameRestart()
                .translation(MOD_ID + ".config.max_recent_add_size")
                .defineInRange("maxRecentAddSize", 24, 0, 100);
        commonBuilder.pop();

        commonBuilder.comment(PROTECTION_MECHANISM).push(PROTECTION_MECHANISM);
        CANCEL_CALCULATION_WHEN_HUNGRY = commonBuilder
                .comment("When enabled, cancels the calculation when the player is enough hungry.")
                .gameRestart()
                .translation(MOD_ID + ".config.cancel_calculation_when_hungry")
                .define("cancelCalculationWhenHungry", true);
        DECREASE_FOOD_EXPECTATION_WHEN_WAKE_UP = commonBuilder
                .comment("The amount to decrease food expectation value when the player wakes up.")
                .gameRestart()
                .translation(MOD_ID + ".config.decrease_food_expectation_when_wake_up")
                .defineInRange("decreaseFoodExpectationWhenWakeUp", 4.5D, 0.0D, 100.0D);
        DECREASE_SATURATION_EXPECTATION_WHEN_WAKE_UP = commonBuilder
                .comment("The amount to decrease saturation expectation value when the player wakes up.")
                .gameRestart()
                .translation(MOD_ID + ".config.decrease_saturation_expectation_when_wake_up")
                .defineInRange("decreaseSaturationExpectationWhenWakeUp", 4.5D, 0.0D, 100.0D);
        commonBuilder.pop();

        COMMON_CONFIG = commonBuilder.build();

        ModConfigSpec.Builder clientBuilder = new ModConfigSpec.Builder();

        clientBuilder.comment(TOOLTIP).push(TOOLTIP);
        SHOW_FOOD_DATA_TOOLTIP = clientBuilder
                .comment("This boolean value corresponds to whether to show the player food data tooltip.")
                .translation(MOD_ID + ".config.show_food_data_tooltip")
                .define("showFoodDataTooltip", true);
        clientBuilder.pop();

        CLIENT_CONFIG = clientBuilder.build();
    }
}
