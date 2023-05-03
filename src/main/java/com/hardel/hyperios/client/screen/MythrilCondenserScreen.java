package com.hardel.hyperios.client.screen;

import com.hardel.hyperios.HyperiosMod;
import com.hardel.hyperios.common.menu.gui.MythrilCondenserMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class MythrilCondenserScreen extends AbstractContainerScreen<MythrilCondenserMenu> {

    /**
     * StartX: 0
     * StartY: 0
     * TotalWidth: 256
     * TotalHeight: 256
     * ContainerWidth: 175
     * ContainerHeight: 245
     * ArrowPosition: 10, 23
     * ArrowSize: 10, 97
     * Arrow Texture: 177, 1
     * Arrow Texture Size: 10, 97
     */
    private static final ResourceLocation TEXTURE = new ResourceLocation(HyperiosMod.MODID, "textures/gui/mythril_condenser.png");

    public MythrilCondenserScreen(MythrilCondenserMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        imageWidth = 176;
        imageHeight = 246;
        this.inventoryLabelY = this.imageHeight - 94;

    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, delta);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull PoseStack poseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
        if (menu.isCrafting()) {
            int progress = menu.getArrowProgress();
            blit(poseStack, x + 11, y + 24 + 97 - progress, 177, 1 + 97 - progress, 10, progress);
        }
    }
}
