package com.hardel.hyperios.common.spell.action;

import com.hardel.hyperios.common.spell.Action;

import java.util.List;

public class Damage extends Action {
    public int damage;
    public int radius;
    public List<Integer> center;

    @Override
    public void execute() {
        System.out.println("Damage action executed");
    }
}
