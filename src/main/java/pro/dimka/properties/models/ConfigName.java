package pro.dimka.properties.models;

public enum ConfigName implements ConfigurableConfig {
    APPLICATION("custom"),
    MAIN_DB("main_db"),
    REDIS("redis"),
    SCHEDULER("scheduler"),
    TWOGIS("twogis");

    private String name;

    ConfigName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getResourcesFileName() {
        return name.concat(".properties");
    }

    public String getSystemVariableWithFileName() {
        return name.toUpperCase() + "_CONFIG_FILE";
    }

    @Override
    public String toString() {
        return this.name;
    }
}
