package dev.cattyn.nrfps;

import com.mojang.serialization.Codec;
import net.minecraft.client.option.InactivityFpsLimit;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.TranslatableOption;

public enum InactivityFpsLimitModes implements TranslatableOption, StringIdentifiable {
    NEVER(0, "never", "nrfps.options.inactivityFpsLimit.never"),
    MINIMIZED(1, "minimized", "options.inactivityFpsLimit.minimized"),
    AFK(2, "afk", "options.inactivityFpsLimit.afk");

    public static final Codec<InactivityFpsLimitModes> CODEC = StringIdentifiable.createCodec(InactivityFpsLimitModes::values);
    private final int ordinal;
    private final String name;
    private final String translationKey;

    InactivityFpsLimitModes(final int ordinal, final String name, final String translationKey) {
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

    public Text getTooltip() {
        return Text.translatable(getTranslationKey() + ".tooltip");
    }

    public InactivityFpsLimit limiter() {
        if (this == NEVER) return null;
        if (this == MINIMIZED) return InactivityFpsLimit.MINIMIZED;
        return InactivityFpsLimit.AFK;
    }
}
