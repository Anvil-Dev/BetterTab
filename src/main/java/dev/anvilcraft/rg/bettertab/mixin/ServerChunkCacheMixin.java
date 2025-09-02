package dev.anvilcraft.rg.bettertab.mixin;

import dev.anvilcraft.rg.bettertab.tabs.MobcapsTab;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerChunkCache.class)
public class ServerChunkCacheMixin {
    @Shadow
    @Final
    public ServerLevel level;

    @Redirect(method = "tickChunks(Lnet/minecraft/util/profiling/ProfilerFiller;J)V", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/server/level/DistanceManager;getNaturalSpawnChunkCount()I"
    ))
    private int setupTracking(@NotNull DistanceManager chunkTicketManager) {
        int j = chunkTicketManager.getNaturalSpawnChunkCount();
        ResourceKey<Level> dim = this.level.dimension();
        MobcapsTab.CHUNK_COUNTS.put(dim, j);
        return j;
    }
}
