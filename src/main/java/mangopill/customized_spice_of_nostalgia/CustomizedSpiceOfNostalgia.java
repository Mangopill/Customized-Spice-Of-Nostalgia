package mangopill.customized_spice_of_nostalgia;

import mangopill.customized_spice_of_nostalgia.common.CustomizedSpiceOfNostalgiaConfig;
import mangopill.customized_spice_of_nostalgia.common.core.network.CSONDataHandler;
import mangopill.customized_spice_of_nostalgia.integration.appleskin.AppleSkinEventHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(CustomizedSpiceOfNostalgia.MODID)
public class CustomizedSpiceOfNostalgia {
    public static final String MODID = "customized_spice_of_nostalgia";

    public CustomizedSpiceOfNostalgia() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CustomizedSpiceOfNostalgiaConfig.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CustomizedSpiceOfNostalgiaConfig.CLIENT_CONFIG);
        CSONDataHandler.register();
        if (FMLEnvironment.dist == Dist.CLIENT && ModList.get().isLoaded("appleskin")) {
            MinecraftForge.EVENT_BUS.register(new AppleSkinEventHandler());
        }
    }

    public static ResourceLocation getCLoc(String path) {
        return new ResourceLocation(MODID, path);
    }
}