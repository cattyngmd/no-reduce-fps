package dev.cattyn.nrfps.interfaces;

import dev.cattyn.nrfps.InactivityFpsLimitModes;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;

public interface DuckGameOptions {
    SimpleOption<InactivityFpsLimitModes> nrfps$getOption();

    default InactivityFpsLimitModes nrfps$getInactivityMode() {
        return nrfps$getOption().getValue();
    }

    static SimpleOption<InactivityFpsLimitModes> nrfps$inactivityOption(GameOptions options) {
        return ((DuckGameOptions) options).nrfps$getOption();
    }

    static InactivityFpsLimitModes nrfps$inactivityMode(GameOptions options) {
        return ((DuckGameOptions) options).nrfps$getInactivityMode();
    }
}
