package com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationErrorResponse {

    private int code;
    private String reason;
    private String message;
    private int status;
    private String referenceError;

}