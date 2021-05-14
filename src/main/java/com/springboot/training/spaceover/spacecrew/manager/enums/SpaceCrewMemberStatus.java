package com.springboot.training.spaceover.spacecrew.manager.enums;

public enum SpaceCrewMemberStatus {

    ACTIVE, INACTIVE;

    public static SpaceCrewMemberStatus fromName(String name) {
        for (SpaceCrewMemberStatus e : values()) {
            if (e.name().equals(name))
                return e;
        }
        return null;
    }

}
