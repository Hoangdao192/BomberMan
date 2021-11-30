package Component;

import Map.Map;
import Utils.Vector2i;

import java.util.ArrayList;

public class MapManager {
    private ArrayList<String> mapPathList;
    private int level;
    private Map currentMap;

    private Vector2i mapCameraSize;

    public MapManager(int cameraWidth, int cameraHeight) {
        level = 1;
        mapPathList = new ArrayList<>();
        mapCameraSize = new Vector2i(cameraWidth, cameraHeight);
    }

    public void setCurrentLevel(int level) {
        if (this.level != level) {
            this.level = level;
            createMap(level);
        }
    }

    public Map loadCurrentLevel() {
        createMap(level);
        return currentMap;
    }

    public void createMap(int level) {
        currentMap = new Map(mapPathList.get(level - 1), mapCameraSize.x, mapCameraSize.y);
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public ArrayList<String> getMapPathList() {
        return mapPathList;
    }

    public boolean nextLevel() {
        if (level < mapPathList.size()) {
            ++level;
            //setCurrentLevel(level);
            return true;
        }
        return false;
    }

    public boolean isLastLevel() {
        return (level == mapPathList.size());
    }

    public int getLevel() {
        return level;
    }
}
