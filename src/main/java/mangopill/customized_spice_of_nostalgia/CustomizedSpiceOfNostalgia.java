package mangopill.customized_spice_of_nostalgia;

import mangopill.customized_spice_of_nostalgia.common.CustomizedSpiceOfNostalgiaConfig;
import mangopill.customized_spice_of_nostalgia.integration.appleskin.AppleSkinEventHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CustomizedSpiceOfNostalgia.MODID)
public class CustomizedSpiceOfNostalgia {
    public static final String MODID = "customized_spice_of_nostalgia";

    public CustomizedSpiceOfNostalgia(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, CustomizedSpiceOfNostalgiaConfig.COMMON_CONFIG);
        container.registerConfig(ModConfig.Type.CLIENT, CustomizedSpiceOfNostalgiaConfig.CLIENT_CONFIG);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
            if (ModList.get().isLoaded("appleskin")) {
                NeoForge.EVENT_BUS.register(new AppleSkinEventHandler());
            }
        }
    }

    public static ResourceLocation getCLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}