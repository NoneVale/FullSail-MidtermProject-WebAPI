package me.nathancole.api.datasection;

import java.util.Map;

public interface Model {

    String getKey();

    Map<String, Object> serialize();
}
