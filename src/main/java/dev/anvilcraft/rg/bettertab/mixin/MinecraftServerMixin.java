package dev.anvilcraft.rg.bettertab.mixin;

import dev.anvilcraft.rg.bettertab.tabs.TabManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "onTickRateChanged", at = @At("HEAD"))
    private void onTickRateChanged(CallbackInfo ci) {
        TabManager.sendToPlayer((MinecraftServer) (Object) this);
    }
}
