package com.springboot.training.spaceover.spacecrew.manager.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import java.net.URI;
import lombok.SneakyThrows;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class SpaceOverGenericController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    protected SpaceCrewMember applyPatch(JsonPatch patch, SpaceCrewMember targetSpaceMission) {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetSpaceMission, JsonNode.class));
        return objectMapper.treeToValue(patched, SpaceCrewMember.class);
    }

    protected URI getResourceUri(Long id) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentRequest().pathSegment(id.toString()).toUriString());
    }

}
