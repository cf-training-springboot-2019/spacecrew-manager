package com.springboot.training.spaceover.spacecrew.manager.utils.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpaceCrewManagerConstant {

    /**
     * Operations
     */
    public static final String GET_SPACE_CREW_MEMBERS_SERVICE_OPERATION = "getCrewMembers";
    public static final String GET_SPACE_CREW_MEMBER_SERVICE_OPERATION = "getCrewMember";
    public static final String CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION = "createCrewMember";
    public static final String PATCH_SPACE_CREW_MEMBER_SERVICE_OPERATION = "patchCrewMember";
    public static final String PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION = "putCrewMember";
    public static final String DELETE_SPACE_CREW_MEMBER_SERVICE_OPERATION = "deleteCrewMember";
    public static final String UNDEFINED_SERVICE_OPERATION = "Undefined";

    /**
     * MDC Keys
     */
    public static final String SERVICE_OPERATION = "operation";
    public static final String TRACE_ID = "trace-id";

    /**
     * Header Names
     */
    public static final String TRACE_ID_HEADER = "X-Trace-Id";
    public static final String SERVICE_OPERATION_HEADER = "Service-Operation";
    public static final String LINK_HEADER = "Link";
    public static final String PAGE_NUMBER_HEADER = "X-Page-Number";
    public static final String PAGE_SIZE_HEADER = "X-Page-Size";
    public static final String TOTAL_ELEMENTS_HEADER = "X-Total-Elements";
    public static final String TOTAL_PAGES_HEADER = "X-Total-Pages";

    /**
     * URIs
     */
    public static final String SPACE_CREW_MEMBERS_URI = "/crewmembers";
    public static final String ID_URI = "/{id}";

    /**
     * Messages
     */
    public static final String GET_SPACE_CREW_MEMBERS_REQUEST_MSG = "Getting crewmembers";
    public static final String GET_SPACE_CREW_MEMBERS_RESULT_MSG = "Got {} crewmembers out of {}";
    public static final String GET_SPACE_CREW_MEMBER_REQUEST_MSG = "Getting crewmember id::{}";
    public static final String GET_SPACE_CREW_MEMBER_RESULT_MSG = "Got crewmember id::{}";
    public static final String CREATE_SPACE_CREW_MEMBER_REQUEST_MSG = "Creating crewmember";
    public static final String CREATE_SPACE_CREW_MEMBER_RESULT_MSG = "Created crewmember id::{}";
    public static final String PATCH_SPACE_CREW_MEMBER_REQUEST_MSG = "Patching crewmember id::{}";
    public static final String PATCH_SPACE_CREW_MEMBER_RESULT_MSG = "Patched crewmember id::{}";
    public static final String PUT_SPACE_CREW_MEMBER_REQUEST_MSG = "Putting crewmember id::{}";
    public static final String PUT_SPACE_CREW_MEMBER_RESULT_MSG = "Put crewmember id::{}";
    public static final String DELETE_SPACE_CREW_MEMBER_REQUEST_MSG = "Deleting crewmember id::{}";
    public static final String DELETE_SPACE_CREW_MEMBER_RESULT_MSG = "Deleted crewmember id::{}";
    public static final String LOGGING_HANDLER_INBOUND_MSG = "Received HTTP {} Request to {} at {}";
    public static final String LOGGING_HANDLER_OUTBOUND_MSG = "Responded with Status {} at {}";
    public static final String LOGGING_HANDLER_PROCESS_TIME_MSG = "Total processing time {} ms";
    public static final String INVALID_MARKET_FIELD_MSG = "market field should match ISO 3166-1 alpha-2 specification";
    public static final String INVALID_EMPTY_OR_BLANK_STRING_MSG = "cannot be empty or blank";
    public static final String ENTITY_NOT_FOUND_MSG = "Entity %s id::{%s} not found.";

    /**
     * Fields
     */
    public static final String NAME_FIELD = "name";
    public static final String STATUS_FIELD = "status";
    public static final String ROLE_FIELD = "role";
    public static final String SPACESHIP_ID_FIELD = "spaceShipId";

    /**
     * OpenAPI
     */
    public static final String GET_SPACE_CREW_MEMBERS_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the retrieval of multiples persisted space crew member resource entries.";
    public static final String GET_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the retrieval of a single persisted space crew member resource entry.";
    public static final String CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the persistence of a single space crew member resource entry.";
    public static final String PATCH_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the partial update of a single space crew member persisted resource entry.";
    public static final String PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the full update of a single space crew member persisted resource entry.";
    public static final String DELETE_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the removal of a single space crew member persisted resource entry.";

    /**
     * Miscellaneous
     */
    public static final String FRONT_SLASH_DELIMITER = "/";
    public static final String COLON_WHITE_SPACE_DELIMITER = ", ";
    public static final String WHITE_SPACE_DELIMITER = " ";
    public static final String SEMI_COLON_DELIMITER = ";";
    public static final String SPACE_CREW_MEMBER_API_DESCRIPTION = "A public Restful Api that allows to manage the various crewmember resources.";
    public static final String ISO_3166_1_ALPHA_2_REGEX = "^[A-Z]{2}$";
    public static final String EMPTY_OR_BLANK_STRING_REGEX = "^(?!\\s*$).+";
    public static final String APPLICATION_JSON_PATCH = "application/json-patch+json";
    public static final String SPACE_CREW_MEMBERS = "crewmembers";
    public static final String SPACE_CREW_MEMBER = "crewmember";
    public static final String SPACESHIPS = "spaceships";
    public static final String SPACESHIP = "spaceship";


}
