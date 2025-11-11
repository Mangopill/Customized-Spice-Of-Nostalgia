package mangopill.customized_spice_of_nostalgia.common.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mangopill.customized_spice_of_nostalgia.CustomizedSpiceOfNostalgia;
import mangopill.customized_spice_of_nostalgia.common.core.PlayerFoodData;
import net.minecraft.commands.*;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static mangopill.customized_spice_of_nostalgia.common.CustomizedSpiceOfNostalgiaConfig.*;

@Mod.EventBusSubscriber(modid = CustomizedSpiceOfNostalgia.MODID)
public class CommandRegister {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandRegister.register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal(CustomizedSpiceOfNostalgia.MODID);
        builder.then(Commands.literal("recentAdd").requires(player -> player.hasPermission(2))
                .then(Commands.literal("clear")
                        .executes(context -> clearRecentAdd(context.getSource(), context.getSource().getPlayerOrException()))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> clearRecentAdd(context.getSource(), EntityArgument.getPlayer(context, "player"))))
                )
        );
        builder.then(Commands.literal("foodExpectation")
                .then(Commands.literal("get")
                        .executes(context -> getFoodExpectation(context.getSource(), context.getSource().getPlayerOrException()))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> getFoodExpectation(context.getSource(), EntityArgument.getPlayer(context, "player"))))
                )
                .then(Commands.literal("set").requires(player -> player.hasPermission(2))
                        .then(Commands.argument("value", DoubleArgumentType.doubleArg(0.0, 20.0))
                                .executes(context -> setFoodExpectation(context.getSource(), context.getSource().getPlayerOrException(), DoubleArgumentType.getDouble(context, "value")))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> setFoodExpectation(context.getSource(), EntityArgument.getPlayer(context, "player"), DoubleArgumentType.getDouble(context, "value"))))
                        )
                )
        );
        builder.then(Commands.literal("saturationExpectation")
                .then(Commands.literal("get")
                        .executes(context -> getSaturationExpectation(context.getSource(), context.getSource().getPlayerOrException()))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> getSaturationExpectation(context.getSource(), EntityArgument.getPlayer(context, "player"))))
                )
                .then(Commands.literal("set").requires(player -> player.hasPermission(2))
                        .then(Commands.argument("value", DoubleArgumentType.doubleArg(0.0, 20.0))
                                .executes(context -> setSaturationExpectation(context.getSource(), context.getSource().getPlayerOrException(), DoubleArgumentType.getDouble(context, "value")))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> setSaturationExpectation(context.getSource(), EntityArgument.getPlayer(context, "player"), DoubleArgumentType.getDouble(context, "value"))))
                        )
                )
        );
        builder.then(Commands.literal("resetExpectations").requires(player -> player.hasPermission(2))
                .executes(context -> resetExpectations(context.getSource(), context.getSource().getPlayerOrException()))
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(context -> resetExpectations(context.getSource(), EntityArgument.getPlayer(context, "player"))))
        );
        commandDispatcher.register(builder);
    }

    private static int clearRecentAdd(CommandSourceStack source, ServerPlayer player) {
        if (player.getFoodData() instanceof PlayerFoodData playerFoodData) {
            playerFoodData.getRecentAdd().clear();
            playerFoodData.sync();
            source.sendSuccess(() -> Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".clear_recent_add", player.getDisplayName()), true);
            return Command.SINGLE_SUCCESS;
        }
        source.sendFailure(Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".failure"));
        return 0;
    }

    private static int getFoodExpectation(CommandSourceStack source, ServerPlayer player) {
        if (player.getFoodData() instanceof PlayerFoodData playerFoodData) {
            float value = playerFoodData.getFoodExpectation();
            source.sendSuccess(() -> Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".get_food_expectation", player.getDisplayName(), value), false);
            return Command.SINGLE_SUCCESS;
        }
        source.sendFailure(Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".failure"));
        return 0;
    }

    private static int setFoodExpectation(CommandSourceStack source, ServerPlayer player, double value) {
        if (player.getFoodData() instanceof PlayerFoodData playerFoodData) {
            playerFoodData.setFoodExpectation((float) value);
            playerFoodData.sync();
            source.sendSuccess(() -> Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".set_food_expectation", value, player.getDisplayName()), true);
            return Command.SINGLE_SUCCESS;
        }
        source.sendFailure(Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".failure"));
        return 0;
    }

    private static int getSaturationExpectation(CommandSourceStack source, ServerPlayer player) {
        if (player.getFoodData() instanceof PlayerFoodData playerFoodData) {
            float value = playerFoodData.getSaturationExpectation();
            source.sendSuccess(() -> Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".get_saturation_expectation", player.getDisplayName(), value), false);
            return Command.SINGLE_SUCCESS;
        }
        source.sendFailure(Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".failure"));
        return 0;
    }

    private static int setSaturationExpectation(CommandSourceStack source, ServerPlayer player, double value) {
        if (player.getFoodData() instanceof PlayerFoodData playerFoodData) {
            playerFoodData.setSaturationExpectation((float) value);
            playerFoodData.sync();
            source.sendSuccess(() -> Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".set_saturation_expectation", value, player.getDisplayName()), true);
            return Command.SINGLE_SUCCESS;
        }
        source.sendFailure(Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".failure"));
        return 0;
    }

    private static int resetExpectations(CommandSourceStack source, ServerPlayer player) {
        if (player.getFoodData() instanceof PlayerFoodData playerFoodData) {
            playerFoodData.setFoodExpectation(INITIAL_FOOD_EXPECTATION.get().floatValue());
            playerFoodData.setSaturationExpectation(INITIAL_SATURATION_EXPECTATION.get().floatValue());
            playerFoodData.sync();
            source.sendSuccess(() -> Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".reset_expectations", player.getDisplayName()), true);
            return Command.SINGLE_SUCCESS;
        }
        source.sendFailure(Component.translatable("command." + CustomizedSpiceOfNostalgia.MODID + ".failure"));
        return 0;
    }
}