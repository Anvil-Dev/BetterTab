package dev.anvilcraft.rg.bettertab.tabs;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

public interface Tab {
    @NotNull Component getMsg(@NotNull MinecraftServer server, @NotNull ServerPlayer player);

    static ChatFormatting heatmapColor(double actual, double reference) {
        ChatFormatting color = ChatFormatting.GRAY;
        if (actual >= 0.0D) color = ChatFormatting.DARK_GREEN;
        if (actual > 0.5D * reference) color = ChatFormatting.YELLOW;
        if (actual > 0.8D * reference) color = ChatFormatting.RED;
        if (actual > reference) color = ChatFormatting.LIGHT_PURPLE;
        return color;
    }

    static ChatFormatting creatureTypeColor(MobCategory type)
    {
        return switch (type)
        {
            case MONSTER -> ChatFormatting.DARK_RED;
            case CREATURE -> ChatFormatting.DARK_GREEN;
            case AMBIENT -> ChatFormatting.DARK_GRAY;
            case WATER_CREATURE -> ChatFormatting.DARK_BLUE;
            case WATER_AMBIENT -> ChatFormatting.DARK_AQUA;
            default -> ChatFormatting.WHITE; // missing MISC and UNDERGROUND_WATER_CREATURE
        };
    }
}
