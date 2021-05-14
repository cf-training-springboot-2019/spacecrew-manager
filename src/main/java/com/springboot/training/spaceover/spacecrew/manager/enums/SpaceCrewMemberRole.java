package com.springboot.training.spaceover.spacecrew.manager.enums;

public enum SpaceCrewMemberRole {

    COMMANDER_OFFICER, ENGINEER_OFFICER, SCIENCE_OFFICER, PILOT_OFFICER, SUPPORT_OFFICER;

    public static SpaceCrewMemberRole fromName(String name) {
        for (SpaceCrewMemberRole e : values()) {
            if (e.name().equals(name))
                return e;
        }
        return null;
    }

}
