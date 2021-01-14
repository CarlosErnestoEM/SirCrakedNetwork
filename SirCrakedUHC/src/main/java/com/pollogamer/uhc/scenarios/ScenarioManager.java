package com.pollogamer.uhc.scenarios;

import java.util.ArrayList;
import java.util.List;

public class ScenarioManager {

    public List<AbstractScenerio> scenarios = new ArrayList<>();

    public ScenarioManager() {
        int slot = 0;

    }

    public void registerScenerio(AbstractScenerio scenerio) {
        scenarios.add(scenerio);
        //log to console
    }

}
