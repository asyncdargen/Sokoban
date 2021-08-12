package ru.dargen.sokoban;

import lombok.val;
import ru.dargen.sokoban.render.Resource;
import ru.dargen.sokoban.game.map.MapContent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelManager {

    private final Map<Integer, MapContent> contents;
    private int last = 0;

    public LevelManager() {
        contents = new HashMap<>();
        for (int i = 1; ; i++) {
            try {
                val resource = Resource.getResource("levels/" + i);
                val reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
                List<String> pattern = new ArrayList<>();
                String s;
                while ((s = reader.readLine()) != null) {
                    pattern.add(s.replace(" ", ""));
                }
                contents.put(i, new MapContent("Уровень " + i, pattern.toArray(new String[0])));
            } catch (Throwable e) {
                break;
            }
        }
    }

    public MapContent getNextMap() {
        if (contents.size() == last) last = 0;
        return contents.get(++last);
    }
}
