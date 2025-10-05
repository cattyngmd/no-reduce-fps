package dev.cattyn.nrfps.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.cattyn.nrfps.InactivityFpsLimitModes;
import dev.cattyn.nrfps.ducks.DuckGameOptions;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.InactivityFpsLimiter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InactivityFpsLimiter.class)
public class MixinInactivityFpsLimiter {
    @Shadow @Final private GameOptions options;

    @SuppressWarnings("unchecked")
    @ModifyExpressionValue(
            method = "getLimitReason",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"
            )
    )
    private <T> T getLimitReasonHook(T original) {
        return (T) DuckGameOptions.nrfps$inactivityMode(options).limiter();
    }

    @Inject(
            method = "getLimitReason",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getLimitReasonHook(CallbackInfoReturnable<InactivityFpsLimiter.LimitReason> cir) {
        if (DuckGameOptions.nrfps$inactivityMode(options) == InactivityFpsLimitModes.NEVER) {
            cir.setReturnValue(InactivityFpsLimiter.LimitReason.NONE);
        }
    }
}
