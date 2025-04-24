package dev.anvilcraft.rg.bettertab;

import com.mojang.logging.LogUtils;
import dev.anvilcraft.rg.bettertab.tabs.TabManager;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(BetterTab.MODID)
public class BetterTab {
    public static final String MODID = "bettertab";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BetterTab(@NotNull @SuppressWarnings("unused") IEventBus modEventBus, @NotNull @SuppressWarnings("unused") ModContainer modContainer) {
        NeoForge.EVENT_BUS.addListener(BetterTab::onServerTick);
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.@NotNull Pre event) {
        MinecraftServer server = event.getServer();
        TabManager.sendToPlayer(server);
    }
}
