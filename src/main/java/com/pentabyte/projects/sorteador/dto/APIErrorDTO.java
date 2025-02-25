package com.pentabyte.projects.sorteador.dto;

import java.time.Instant;

public record APIErrorDTO(String error, Instant timestamp) {
}
