package ru.dargen.sokoban.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@ToString
@Getter @Setter
@AllArgsConstructor
public class V2 {

    public static final V2 ZERO = new V2(0, 0);

    private int x;
    private int y;

    public V2 clone() {
        return new V2(x, y);
    }

    public V2 add(V2 v2) {
        Objects.requireNonNull(v2, "add v2");
        x += v2.x;
        y += v2.y;
        return this;
    }

    public boolean equals(V2 v2) {
        return v2 != null && v2.x == x && v2.y == y;
    }

}
