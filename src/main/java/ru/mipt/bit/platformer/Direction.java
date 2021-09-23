package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

enum Direction {
    Up, Down, Left, Right, Default;

    Direction getDirectionFromKey() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return Up;
        }
        else if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return Left;
        }
        else if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return Down;
        }
        else if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return Right;
        }
        else {
            return Default;
        }
    }
}

