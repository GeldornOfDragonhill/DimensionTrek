package net.dragonhill.mc.dimensiontrek.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static class ServerConfig {
        public final IntValue biomeAnalyzerWorkingTicks;

        private ServerConfig(final ForgeConfigSpec.Builder builder) {
            builder.comment("Biome analyzer settings")
                    .push("biomeAnalyzer");

            this.biomeAnalyzerWorkingTicks = builder
                    .comment("The number of ticks the biome analyzer need for a work cycle")
                    .defineInRange("biomeAnalyzerWorkingTicks", 60, 0, Integer.MAX_VALUE);

            builder.pop();
        }
    }

    private static final ForgeConfigSpec serverSpec;
    public static final ServerConfig SERVER;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        serverSpec = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.SERVER, serverSpec);
    }
}
