package com.hardel.hyperios.common.menu.gui;

import com.hardel.hyperios.api.containers.BaseContainerMenu;
import com.hardel.hyperios.common.block.entity.MythrilCondenserBlockEntity;
import com.hardel.hyperios.setup.MenuTypeRegister;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MythrilCondenserMenu extends BaseContainerMenu {
    public final MythrilCondenserBlockEntity blockEntity;
    private final Level level;
    private final ContainerData containerData;

    public MythrilCondenserMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory, inventory.player.level.getBlockEntity(buf.readBlockPos()), new SimpleContainerData(8));
    }

    public MythrilCondenserMenu(int id, Inventory inventory, BlockEntity entity, ContainerData containerData) {
        super(MenuTypeRegister.MYTHRIL_CONDENSER_MENU.get(), id);
        checkContainerSize(inventory, 9);
        blockEntity = (MythrilCondenserBlockEntity) entity;
        this.level = inventory.player.level;
        this.containerData = containerData;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 79, 16));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 130, 53));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 111, 111));
            this.addSlot(new SlotItemHandler(itemHandler, 3, 49, 111));
            this.addSlot(new SlotItemHandler(itemHandler, 4, 29, 54));
            this.addSlot(new SlotItemHandler(itemHandler, 5, 153, 51));
            this.addSlot(new SlotItemHandler(itemHandler, 6, 153, 68));
            this.addSlot(new SlotItemHandler(itemHandler, 7, 153, 85));
            this.addSlot(new SlotItemHandler(itemHandler, 8, 78, 67));
        });

        addDataSlots(containerData);
    }

    public boolean isCrafting() {
        return containerData.get(0) > 0;
    }

    public int getArrowProgress() {
        int progress = containerData.get(0);
        int maxProgress = containerData.get(1);
        int progressArrowSize = 97;

        return (int) (progressArrowSize * ((float) progress / (float) maxProgress));
    }

    @Override
    protected int getTileEntityInventorySlotCount() {
        return 9;
    }

    @Override
    protected int InventoryOffsetY() {
        return 77;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
    }
}
