package me.pajic.affogatocore.integration;

import archives.tater.penchant.client.gui.widget.EnchantmentSlotWidget;
import archives.tater.penchant.network.EnchantPayload;
import archives.tater.penchant.util.PenchantmentHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class PenchantLevelUpSlotWidget extends AbstractButton {

    public static final int LEVEL_UP_COLOR = 0xFFFF55FF;

    private final Holder<Enchantment> enchantment;
    private final Component text;
    private final Component costText;
    private final int baseColor;

    public PenchantLevelUpSlotWidget(
            int x, int y, Holder<Enchantment> enchantment, int level, int pendingLevels, boolean hasEnoughXp
    ) {
        super(x, y, EnchantmentSlotWidget.WIDTH, EnchantmentSlotWidget.HEIGHT, enchantment.value().description());
        this.enchantment = enchantment;
        text = enchantment.value().description();
        baseColor = enchantment.is(EnchantmentTags.CURSE) ?
                EnchantmentSlotWidget.CURSE_TEXT_COLOR :
                EnchantmentSlotWidget.BASE_TEXT_COLOR;

        int xpCost = PenchantmentHelper.getXpLevelCost(enchantment);
        int xpColor = hasEnoughXp ? EnchantmentSlotWidget.XP_COLOR : EnchantmentSlotWidget.INSUFFICIENT_COLOR;
        Component nextLevel = Component.translatable("enchantment.level." + (level + 1));

        costText = Component.empty()
                .append(Component.translatable("widget.affogatocore.penchant_level_up", nextLevel).withColor(LEVEL_UP_COLOR))
                .append(" ")
                .append(Component.literal(Integer.toString(xpCost)).withColor(xpColor));

        MutableComponent tooltip = PenchantmentHelper.getName(enchantment).copy()
                .append("\n")
                .append(Component.translatable("widget.affogatocore.penchant_level_up.tooltip", nextLevel).withColor(LEVEL_UP_COLOR));
        if (pendingLevels > 1) tooltip
                .append("\n")
                .append(Component.translatable("widget.affogatocore.penchant_level_up.tooltip.pending", pendingLevels).withStyle(ChatFormatting.GRAY));
        tooltip
                .append("\n")
                .append(Component.translatable("widget.penchant.enchantment_slot.tooltip.xp_cost", xpCost).withColor(xpColor));
        setTooltip(Tooltip.create(tooltip));

        active = hasEnoughXp;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                EnchantmentSlotWidget.TEXTURES.get(active, isHovered()),
                getX(), getY(), getWidth(), getHeight()
        );
        Font font = Minecraft.getInstance().font;
        graphics.text(
                font, text,
                getX() + 2, getY() + 2,
                active && isHovered() ? EnchantmentSlotWidget.HOVER_TEXT_COLOR : baseColor,
                false
        );
        graphics.text(
                font, costText,
                getX() + width - 2 - font.width(costText), getY() + 2,
                0xFF404040, true
        );
    }

    @Override
    public void onPress(@NotNull InputWithModifiers input) {
        ClientPlayNetworking.send(new EnchantPayload(enchantment));
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        defaultButtonNarrationText(narrationElementOutput);
    }
}
