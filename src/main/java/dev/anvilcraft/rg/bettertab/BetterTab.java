package dev.anvilcraft.rg.bettertab;

import com.mojang.logging.LogUtils;
import dev.anvilcraft.rg.api.RGAdditional;
import dev.anvilcraft.rg.api.server.ServerRGRuleManager;
import dev.anvilcraft.rg.api.server.TranslationUtil;
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
public class BetterTab implements RGAdditional {
    public static final String MODID = "bettertab";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BetterTab(@NotNull IEventBus modEventBus, @NotNull ModContainer modContainer) {
        NeoForge.EVENT_BUS.addListener(BetterTab::onServerTick);
        modContainer.registerExtensionPoint(RGAdditional.class, this);
    }

    @Override
    public void loadServerRules(@NotNull ServerRGRuleManager manager) {
        manager.register(BetterTabServerRules.class);
        TranslationUtil.loadLanguage(BetterTab.class, "bettertab", "zh_cn.json");
        TranslationUtil.loadLanguage(BetterTab.class, "bettertab", "en_us");
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.@NotNull Pre event) {
        MinecraftServer server = event.getServer();
        TabManager.sendToPlayer(server);
    }
}
