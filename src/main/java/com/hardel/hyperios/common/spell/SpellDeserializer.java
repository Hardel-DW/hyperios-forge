package com.hardel.hyperios.common.spell;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

/**
 * Read JSON file and create a Spell object
 */
public class SpellDeserializer {
    Gson gson = new Gson();

    private void deserialisze() {

        try {
            FileReader reader = new FileReader("src/main/resources/spell.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
