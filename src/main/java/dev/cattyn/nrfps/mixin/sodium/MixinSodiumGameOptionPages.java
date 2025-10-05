package dev.cattyn.nrfps.mixin.sodium;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.cattyn.nrfps.InactivityFpsLimitModes;
import dev.cattyn.nrfps.ducks.DuckGameOptions;
import net.caffeinemc.mods.sodium.client.gui.SodiumGameOptionPages;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.control.CyclingControl;
import net.caffeinemc.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import net.minecraft.client.option.InactivityFpsLimit;
import net.minecraft.text.Text;
import net.minecraft.util.TranslatableOption;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Arrays;

@Pseudo
@Mixin(SodiumGameOptionPages.class)
public class MixinSodiumGameOptionPages {
    @Final
    @Shadow(remap = false)
    private static MinecraftOptionsStorage vanillaOpts;

    @ModifyExpressionValue(
            method = "performance",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/caffeinemc/mods/sodium/client/gui/options/OptionImpl$Builder;build()Lnet/caffeinemc/mods/sodium/client/gui/options/OptionImpl;",
                    ordinal = 7,
                    remap = false
            ),
            remap = false
    )
    private static OptionImpl<?, ?> preformanceHook(OptionImpl<?, ?> original) {
        return OptionImpl.createBuilder(InactivityFpsLimitModes.class, vanillaOpts)
                .setName(Text.translatable("options.inactivityFpsLimit"))
                .setTooltip(InactivityFpsLimitModes::getTooltip)
                .setControl(option -> new CyclingControl<>(option, InactivityFpsLimitModes.class, nrfps$getTranslatables()))
                .setBinding(
                        (opts, value) -> DuckGameOptions.nrfps$inactivityOption(opts).setValue(value),
                        opts -> DuckGameOptions.nrfps$inactivityOption(opts).getValue())
                .build();
    }

    @Unique
    private static Text[] nrfps$getTranslatables() {
        return Arrays.stream(InactivityFpsLimitModes.values())
                .map(TranslatableOption::getText)
                .toArray(Text[]::new);
    }
}
