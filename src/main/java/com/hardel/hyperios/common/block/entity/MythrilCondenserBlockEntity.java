package com.hardel.hyperios.common.block.entity;

import com.hardel.hyperios.HyperiosMod;
import com.hardel.hyperios.common.menu.gui.MythrilCondenserMenu;
import com.hardel.hyperios.common.recipe.MythrilCondenserRecipe;
import com.hardel.hyperios.setup.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MythrilCondenserBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<ItemStackHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);

    protected final ContainerData containerData;
    private int progress = 0;
    private int maxProgress = 100;

    public MythrilCondenserBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MYTHRIL_CONDENSER.get(), pos, state);
        this.containerData = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                    default -> throw new IndexOutOfBoundsException();
                }
            }

            @Override
            public int getCount() {
                return 8;
            }
        };
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container." + HyperiosMod.MODID + ".mythril_condenser").withStyle(style -> style.withColor(0xffffff));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int playerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new MythrilCondenserMenu(playerId, playerInventory, this, this.containerData);
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
        nbt.put("items", itemHandler.serializeNBT());
        nbt.putInt("mythril_condenser.progress", progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("items"));
        progress = nbt.getInt("mythril_condenser.progress");
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

        if (hasRecipe(entity)) {
            entity.progress++;
            setChanged(level, blockPos, blockState);

            if (entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }
        } else {
            entity.progress = 0;
            setChanged(level, blockPos, blockState);
        }
    }

    private static void craftItem(MythrilCondenserBlockEntity entity) {
        Level level = entity.getLevel();
        assert level != null;

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<MythrilCondenserRecipe> recipe = level.getRecipeManager().getRecipeFor(MythrilCondenserRecipe.Type.INSTANCE, inventory, level);
        if (hasRecipe(entity)) {
            for (int i = 0; i < 5; i++) {
                entity.itemHandler.extractItem(i, 1, false);
            }

            recipe.ifPresent(mythrilCondenserRecipe -> entity.itemHandler.insertItem(8, new ItemStack(mythrilCondenserRecipe.getResultItem(RegistryAccess.EMPTY).getItem(), 1), false));
            entity.progress = 0;
        }
    }

    private static boolean hasRecipe(MythrilCondenserBlockEntity entity) {
        Level level = entity.getLevel();
        assert level != null;

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<MythrilCondenserRecipe> recipe = level.getRecipeManager().getRecipeFor(MythrilCondenserRecipe.Type.INSTANCE, inventory, level);
        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem(RegistryAccess.EMPTY));
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(8).getItem() == stack.getItem() || inventory.getItem(8).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(8).getMaxStackSize() > inventory.getItem(8).getCount();
    }
}
