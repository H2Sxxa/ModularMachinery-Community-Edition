package hellfirepvp.modularmachinery.common.machine;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import java.util.HashMap;
import java.util.Map;

public enum RecipeFailureActions {
    RESET("reset"),
    STILL("still"),
    DECREASE("decrease");

    public String getName() {
        return name;
    }

    private final String name;

    RecipeFailureActions(String name) {
        this.name = name;
    }

    public static final RecipeFailureActions[] VALUES = RecipeFailureActions.values();
    public static final HashMap<String, RecipeFailureActions> NAME_MAP;

    static {
        NAME_MAP = new HashMap<>(VALUES.length);
        for (RecipeFailureActions value : VALUES) {
            NAME_MAP.put(value.name, value);
        }
    }

    private static final Map<ResourceLocation, RecipeFailureActions> REGISTRY_FAILURE_ACTIONS = new HashMap<>();
    private static RecipeFailureActions defaultAction = RecipeFailureActions.STILL;

    public static void loadFromConfig(Configuration cfg) {
        defaultAction = RecipeFailureActions.NAME_MAP.get(
                cfg.getString(
                        "default-failure-actions", "general", "still",
                        "Define what action is used when a recipe failed to run. [actions: reset, still, decrease]"));
        if (defaultAction == null) {
            defaultAction = RecipeFailureActions.NAME_MAP.get("still");
        }
    }

    public static RecipeFailureActions getFailureAction(String key) {
        RecipeFailureActions actions = NAME_MAP.get(key);
        if (actions != null) return actions;

        return defaultAction;
    }

    public static RecipeFailureActions getDefaultAction() {
        return defaultAction;
    }
}
