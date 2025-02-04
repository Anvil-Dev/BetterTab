package dev.anvilcraft.rg.bettertab.tabs;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MobcapsTab implements Tab {
    private static final MobCategory[] CACHED_MOBCATEGORY_VALUES = MobCategory.values();
    public static final HashMap<ResourceKey<Level>, Integer> CHUNK_COUNTS = new HashMap<>();
    public static final int MAGIC_NUMBER = (int) Math.pow(17.0D, 2.0D);

    @Override
    public @NotNull Component getMsg(@NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        //noinspection resource
        if (!(player.level() instanceof ServerLevel level)) return Component.empty();
        ResourceKey<Level> dimension = level.dimension();
        Component component;
        NaturalSpawner.SpawnState lastSpawner = level.getChunkSource().getLastSpawnState();
        Object2IntMap<MobCategory> dimCounts = null;
        if (lastSpawner != null) dimCounts = lastSpawner.getMobCategoryCounts();
        int chunkCount = CHUNK_COUNTS.getOrDefault(dimension, -1);
        if (dimCounts == null || chunkCount < 0) {
            component = Component.literal("--UNAVAILABLE--").withStyle(ChatFormatting.GRAY);
            return component;
        }
        MutableComponent literal = Component.literal("");
        int length = 0;
        for (MobCategory category : cachedMobCategories()) {
            int cur = dimCounts.getOrDefault(category, -1);
            int max = (int) (chunkCount * ((double) category.getMaxInstancesPerChunk() / MAGIC_NUMBER));
            ChatFormatting color = Tab.heatmapColor(cur, max);
            ChatFormatting mobColor = Tab.creatureTypeColor(category);
            if (length != 0) literal.append(Component.literal(",").withStyle(ChatFormatting.GRAY));
            literal.append(Component.literal("%s".formatted((cur < 0) ? "-" : cur)).withStyle(color))
                .append(Component.literal("/").withStyle(ChatFormatting.GRAY))
                .append(Component.literal("%s".formatted(max)).withStyle(mobColor));
            length++;
        }
        if (length > 0) {
            component = literal;
        } else {
            component = Component.literal("--UNAVAILABLE--").withStyle(ChatFormatting.GRAY);
        }
        return component;
    }

    public static MobCategory[] cachedMobCategories() {
        return CACHED_MOBCATEGORY_VALUES;
    }
}
