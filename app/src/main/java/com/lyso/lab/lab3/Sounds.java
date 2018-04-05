package com.lyso.lab.lab3;

import android.content.Context;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Sound Manager
 * Ole Lysø 3.5.2018
 */
final class Sounds {

    private static final Map<Integer, Integer> sounds = new HashMap<>();
    private static SoundPool                   soundPool;

    /**
     * Sets up the sound pool.
     * @param resources List of resource IDs
     * @param context Context
     */
    public static void init(final ArrayList<Integer> resources, final Context context) {
        Sounds.soundPool = new SoundPool.Builder().setMaxStreams(2).build();

        for (Integer resID : resources)
            sounds.put(resID, Sounds.soundPool.load(context, resID, 1));
    }

    /**
     * Plays the selected sound effect.
     * NB! Must first be initialized with Sounds.init(ArrayList<Integer>, Context)
     * @param resourceID Resource ID
     */
    public static void playSound(final int resourceID) {
        if (Sounds.soundPool != null) {
            int id = sounds.get(resourceID);

            Sounds.soundPool.stop(id);
            Sounds.soundPool.play(id, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

}
