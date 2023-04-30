package com.hardel.hyperios.common.block.entity;

import com.hardel.hyperios.setup.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MythrilCondenserBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);

    public MythrilCondenserBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MYTHRIL_CONDENSER.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.mythril_condenser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int playerId, @NotNull Inventory playerInventory, Player player) {
        return null;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
            return lazyItemHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        nbt.put("items", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("items"));
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < container.getContainerSize(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MythrilCondenserBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }
    }
}
