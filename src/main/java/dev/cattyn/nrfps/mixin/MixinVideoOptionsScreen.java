package dev.cattyn.nrfps.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.cattyn.nrfps.ducks.DuckGameOptions;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.InactivityFpsLimit;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VideoOptionsScreen.class)
public class MixinVideoOptionsScreen {
    @ModifyExpressionValue(
            method = "getOptions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/GameOptions;getInactivityFpsLimit()Lnet/minecraft/client/option/SimpleOption;"
            )
    )
    private static SimpleOption<?> getOptionsHook(SimpleOption<InactivityFpsLimit> original, @Local(argsOnly = true) GameOptions options) {
        return DuckGameOptions.nrfps$inactivityOption(options);
    }
}
