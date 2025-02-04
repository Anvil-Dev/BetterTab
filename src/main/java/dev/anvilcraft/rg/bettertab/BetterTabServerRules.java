package dev.anvilcraft.rg.bettertab;

import dev.anvilcraft.rg.api.RGValidator;
import dev.anvilcraft.rg.api.Rule;

public class BetterTabServerRules {
    @Rule(
        allowed = {"true", "false"},
        categories = BetterTab.MODID,
        validator = RGValidator.BooleanValidator.class
    )
    public static boolean showTps = true;

    @Rule(
        allowed = {"true", "false"},
        categories = BetterTab.MODID,
        validator = RGValidator.BooleanValidator.class
    )
    public static boolean showMobcaps = false;

    @Rule(
        allowed = {"none", "custom"},
        categories = BetterTab.MODID,
        validator = RGValidator.StringValidator.class
    )
    public static String customTabLine = "none";
}
