package com.example.siternbackend.user.payload;

import java.util.List;

public record HealthCheckResponse(String appName, List<StatusResponse> services, Status status) {
}
