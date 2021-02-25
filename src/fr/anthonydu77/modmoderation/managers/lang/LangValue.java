package fr.anthonydu77.modmoderation.managers.lang;

/**
 * Created by Anthonydu77 14/12/2020 inside the package - fr.anthonydu77.modmoderation.managers.lang
 */

public enum LangValue {
    PLAYER("player"),
    FACTION("faction"),
    KILLER("killer");

    private String name;

    LangValue(String name) {
        this.name = name;
    }

    public String toName(){
        return "{" + name + "}";
    }
}
