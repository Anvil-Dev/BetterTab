package dev.anvilcraft.rg.bettertab.tabs;

import dev.anvilcraft.rg.bettertab.BetterTabServerRules;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TabManager {
    public static final Map<Supplier<Boolean>, Tab> TABS = new LinkedHashMap<>();

    static {
        TABS.put(() -> BetterTabServerRules.showTps, new TpsTab());
        TABS.put(() -> BetterTabServerRules.showMobcaps, new MobcapsTab());
        TABS.put(
            () -> !BetterTabServerRules.customTabLine.equals("none"),
            (s, p) -> Component.literal(BetterTabServerRules.customTabLine.replace("&&", "§"))
        );
    }

    public static boolean shouldShow() {
        for (Supplier<Boolean> supplier : TABS.keySet()) if (supplier.get()) return true;
        return false;
    }

    public static @NotNull Component getTabs(MinecraftServer server, ServerPlayer player) {
        MutableComponent component = Component.literal("");
        int length = 0;
        for (Map.Entry<Supplier<Boolean>, Tab> entry : TABS.entrySet()) {
            if (!entry.getKey().get()) continue;
            if (length != 0) component.append("\n");
            length++;
            component.append(entry.getValue().getMsg(server, player));
        }

        return component;
    }

    public static void sendToPlayer(@NotNull MinecraftServer server) {
        server.getPlayerList()
            .getPlayers()
            .forEach(player -> player.setTabListFooter(TabManager.getTabs(server, player)));
    }
}
