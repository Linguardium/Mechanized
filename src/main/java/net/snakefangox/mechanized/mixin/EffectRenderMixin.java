package net.snakefangox.mechanized.mixin;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.client.util.math.MatrixStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.Nameable;
import net.snakefangox.mechanized.effects.HiddenEffect;

@Environment(EnvType.CLIENT)
@Mixin(AbstractInventoryScreen.class)
public abstract class EffectRenderMixin implements Inventory, Nameable {

	@SuppressWarnings("rawtypes")
	@Inject(at = @At(value = "HEAD"), method = "applyStatusEffectOffset", cancellable = true)
	public void checkStatusOffset(CallbackInfo info) {
		MinecraftClient client = MinecraftClient.getInstance();
		Collection effects = client.player.getStatusEffects();
		if (!effects.isEmpty()) {
			Iterator iterater = effects.iterator();
			boolean onlyHidden = true;
			while (iterater.hasNext()) {
				Object o = iterater.next();
				if (!(((StatusEffectInstance) o).getEffectType() instanceof HiddenEffect)) {
					onlyHidden = false;
					break;
				}
			}
			if (onlyHidden) {
				info.cancel();
			}
		}
	}

	@ModifyVariable(at = @At(value = "STORE"), method = "drawStatusEffects",name="collection")
	private Collection<StatusEffectInstance> hideEffects(Collection<StatusEffectInstance> collection) {
		Collection<StatusEffectInstance> c = collection;
		c.removeIf(eff -> (eff.getEffectType() instanceof HiddenEffect));
		return c;
	}
}
