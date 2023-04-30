package com.hardel.hyperios.common.item;

import com.hardel.hyperios.common.spell.Spell;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class MagicSpellItem extends Item {
    private final Spell spell;

    public MagicSpellItem(Properties properties, Spell spell) {
        super(properties);
        this.spell = spell;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        // search in list spell.action if contains a key named type == "damage" then store in variable

        return super.useOn(context);
    }
}
