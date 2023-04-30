package com.hardel.hyperios.common.menu.gui;

import com.hardel.hyperios.common.block.entity.MythrilCondenserBlockEntity;
import com.hardel.hyperios.setup.MenuTypeRegister;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class MythrilCondenserMenu extends AbstractContainerMenu {
    public final MythrilCondenserBlockEntity blockEntity;
    private final Level level;
    private final ContainerData containerData;

    public MythrilCondenserMenu(int id, Inventory inventory, BlockEntity entity, ContainerData containerData) {
        super(MenuTypeRegister.MYTHRIL_CONDENSER_MENU.get(), id);
        blockEntity = (MythrilCondenserBlockEntity) entity;
        this.level = inventory.player.level;
        this.containerData = containerData;
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 56, 17));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 56, 53));
            this.addSlot(new SlotItemHandler(itemHandler, 2, 116, 35));
        });
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
