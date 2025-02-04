package dev.anvilcraft.rg.bettertab.tabs;

import dev.anvilcraft.rg.api.server.TranslationUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTickRateManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

public class TpsTab implements Tab {
    public @NotNull Component getMsg(@NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        double MSPT = ((double) server.getAverageTickTimeNanos()) / TimeUtil.NANOSECONDS_PER_MILLISECOND;
        ServerTickRateManager trm = server.tickRateManager();
        double TPS = 1000.0D / Math.max(trm.isSprinting() ? 0.0 : trm.millisecondsPerTick(), MSPT);
        if (trm.isFrozen()) TPS = 0.0D;
        ChatFormatting tpsColor = Tab.heatmapColor(MSPT, trm.millisecondsPerTick());
        return TranslationUtil.trans(
                "tab.bettertab.tps",
                Component.literal("%.1f".formatted(TPS)).withStyle(tpsColor),
                Component.literal("%.1f".formatted(MSPT)).withStyle(tpsColor)
            )
            .withStyle(ChatFormatting.GRAY);
    }
}
