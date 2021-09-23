package ru.mipt.bit.platformer;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import java.util.HashMap;


public class Player {
    private static final float MOVEMENT_SPEED = 0.4f;
    private final TextureRegion graphics;
    private final Rectangle rectangle;

    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;

    private float movementProgress = 1f;
    private float rotation;
    private final HashMap<Direction, Float> orientations;

    Player(Texture blueTankTexture) {
        this.graphics = new TextureRegion(blueTankTexture);
        this.rectangle = createBoundingRectangle(this.graphics);
        this.coordinates = new GridPoint2(1, 1);
        this.destinationCoordinates = new GridPoint2(1, 1);
        this.rotation = 0f;
        orientations = new HashMap<>();
        orientations.put(Direction.Up, 90f);
        orientations.put(Direction.Left, 180f);
        orientations.put(Direction.Down, 270f);
        orientations.put(Direction.Right, 0f);
    }

    TextureRegion getGraphics() {
        return this.graphics;
    }
    Rectangle getRectangle() {
        return this.rectangle;
    }
    GridPoint2 getCoordinates() {
        return this.coordinates;
    }

    float getMovementProgress() {
        return this.movementProgress;
    }
    float getRotation() {
        return this.rotation;
    }

    boolean hasMoved() {
        return isEqual(this.movementProgress, 1f);
    }

    GridPoint2 makeNewPoint(GridPoint2 point) {
        return new GridPoint2(point.x, point.y);
    }

    Direction getDiretion() {
        Direction direction = Direction.Default;
        direction = direction.getDirectionFromKey();
        return direction;
    }

    GridPoint2 getDestinationCoordinates() {
        return this.destinationCoordinates;
    }
    void updateDestinationCoordinates() {
        switch(getDiretion()) {
            case Default:
                break;
            case Up:
                this.destinationCoordinates.y++;
                break;
            case Left:
                this.destinationCoordinates.x--;
                break;
            case Down:
                this.destinationCoordinates.y--;
                break;
            case Right:
                this.destinationCoordinates.x++;
                break;
        }
    }

    void updateCoordinates() {
        this.coordinates.set(this.destinationCoordinates);
    }

    boolean canMove(GridPoint2 obstacleCoordinates) {
        return !obstacleCoordinates.equals(this.destinationCoordinates);
    }

    void makeMovement() {
        this.movementProgress = 0f;
    }

    void changeRotation(Direction direction) {
        //this.rotation = orientations.get(direction);
    }

    void move(float deltaTime) {
        updateDestinationCoordinates();
        if (this.hasMoved()) {
            changeRotation(getDiretion());
        }
        makeMovement();
        changeMovementProgress(deltaTime, MOVEMENT_SPEED);
        reachDestination();
    }

    void reachDestination() {
        if (this.hasMoved()) {
            this.updateCoordinates();
        }
    }

    void changeMovementProgress(float deltaTime, float speed) {
        this.movementProgress = continueProgress(this.movementProgress, deltaTime, speed);
    }
}