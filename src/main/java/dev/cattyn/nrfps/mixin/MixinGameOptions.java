package dev.cattyn.nrfps.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.cattyn.nrfps.InactivityFpsLimitModes;
import dev.cattyn.nrfps.ducks.DuckGameOptions;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.InactivityFpsLimit;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;

@Mixin(GameOptions.class)
public class MixinGameOptions implements DuckGameOptions {
    @Unique
    private SimpleOption<InactivityFpsLimitModes> nrfps$inactivityFpsLimit = new SimpleOption<>(
            "options.inactivityFpsLimit",
            MixinGameOptions::nrfps$tooltips,
            SimpleOption.enumValueText(),
            nrfps$createCallbacks(),
            InactivityFpsLimitModes.NEVER,
            (inactivityFpsLimit) -> { }
    );

    @Override
    public SimpleOption<InactivityFpsLimitModes> nrfps$getOption() {
        return nrfps$inactivityFpsLimit;
    }

    @ModifyExpressionValue(
            method = "acceptProfiledOptions",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/option/GameOptions;inactivityFpsLimit:Lnet/minecraft/client/option/SimpleOption;"
            )
    )
    private SimpleOption<?> acceptProfiledOptionsHook(SimpleOption<InactivityFpsLimit> original) {
        return nrfps$inactivityFpsLimit;
    }

    @Unique
    private static Tooltip nrfps$tooltips(InactivityFpsLimitModes option) {
        return Tooltip.of(option.getTooltip());
    }

    @Unique
    private static SimpleOption.PotentialValuesBasedCallbacks<InactivityFpsLimitModes> nrfps$createCallbacks() {
        return new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(InactivityFpsLimitModes.values()), InactivityFpsLimitModes.CODEC);
    }
}
