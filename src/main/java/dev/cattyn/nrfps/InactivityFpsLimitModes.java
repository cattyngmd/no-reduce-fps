package dev.cattyn.nrfps;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.TranslatableOption;

public enum InactivityFpsLimit implements TranslatableOption, StringIdentifiable {
    NONE(0, "none", "options.inactivityFpsLimit.none"),
    MINIMIZED(1, "minimized", "options.inactivityFpsLimit.minimized"),
    AFK(2, "afk", "options.inactivityFpsLimit.afk");

    public static final com.mojang.serialization.Codec<net.minecraft.client.option.InactivityFpsLimit> Codec = StringIdentifiable.createCodec(net.minecraft.client.option.InactivityFpsLimit::values);
    private final int ordinal;
    private final String name;
    private final String translationKey;

    InactivityFpsLimit(final int ordinal, final String name, final String translationKey) {
        this.ordinal = ordinal;
        this.name = name;
        this.translationKey = translationKey;
    }

    public int getId() {
        return this.ordinal;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public String asString() {
        return this.name;
    }
}
