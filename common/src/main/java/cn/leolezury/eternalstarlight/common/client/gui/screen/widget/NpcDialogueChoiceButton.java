package cn.leolezury.eternalstarlight.common.client.gui.screen.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

@Environment(EnvType.CLIENT)
public class NpcDialogueChoiceButton extends Button {
	public NpcDialogueChoiceButton(Component text, OnPress onPress) {
		super(0, 0, 0, 0, text, onPress, DEFAULT_NARRATION);
	}

	public int getIncrement(int width) {
		List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(getMessage(), width / 5 * 4);
		return Math.max(1, lines.size()) * Minecraft.getInstance().font.lineHeight + 10;
	}

	public void reposition(int x, int y, int width) {
		setRectangle(width, getIncrement(width), x, y);
	}

	@Override
	protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
		guiGraphics.fillGradient(getX(), getY(), getX() + width, getY() + height, 0x66000000, 0x66000000);
		List<FormattedCharSequence> lines = Minecraft.getInstance().font.split(getMessage(), width / 5 * 4);
		int y = 0;
		for (FormattedCharSequence sequence : lines) {
			guiGraphics.drawCenteredString(Minecraft.getInstance().font, sequence, getX() + width / 2, getY() + y + 5, isHovered() ? 0x2aacb8 : 0xffffff);
			y += Minecraft.getInstance().font.lineHeight;
		}
	}
}
