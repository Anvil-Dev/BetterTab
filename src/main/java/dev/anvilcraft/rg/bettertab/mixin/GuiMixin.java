package dev.anvilcraft.rg.bettertab.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.anvilcraft.rg.bettertab.tabs.TabManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
abstract class GuiMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private PlayerTabOverlay tabList;

    @Inject(method = "extractTabList", at = @At(value = "RETURN"))
    private void renderTabList(
        GuiGraphicsExtractor guiGraphics,
        DeltaTracker deltaTracker,
        CallbackInfo ci,
        @Local Scoreboard scoreboard,
        @Local Objective objective
    ) {
        if (this.minecraft.player == null) return;
        if (
            this.minecraft.options.keyPlayerList.isDown() &&
                (
                    !this.minecraft.isLocalServer() ||
                        TabManager.shouldShow() ||
                        this.minecraft.player.connection.getListedOnlinePlayers().size() > 1 ||
                        objective != null
                )
        ) {
            this.tabList.setVisible(true);
            this.tabList.extractRenderState(guiGraphics, guiGraphics.guiWidth(), scoreboard, objective);
        } else {
            this.tabList.setVisible(false);
        }
    }
}
