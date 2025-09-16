package io.manuel.chatto.dto;

import java.util.Set;

public record ChatRequest(Set<Long> userIds) {

}
