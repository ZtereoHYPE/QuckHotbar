package codes.ztereohype.quickhotbar.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Unique
	long lastTime = 0;

	@Unique
	int currentGroup = 0;

	@Shadow
	public LocalPlayer player;

	@Inject(at = @At(value = "FIELD",
					target = "Lnet/minecraft/world/entity/player/Inventory;selected:I",
					shift = At.Shift.AFTER),
			method = "handleKeybinds",
			locals = LocalCapture.CAPTURE_FAILHARD)
	private void testInjection(CallbackInfo ci, int i) {
		System.out.println(System.currentTimeMillis() - lastTime);
		if (i < 3) {
			if (System.currentTimeMillis() - lastTime > 250) {
				currentGroup = i;
			} else {
				this.player.getInventory().selected = currentGroup * 3 + i;
			}
			lastTime = System.currentTimeMillis();
		}
	}
}
