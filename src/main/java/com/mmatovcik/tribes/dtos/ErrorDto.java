package com.mmatovcik.tribes.dtos;

import java.util.List;

public record ErrorDto(Integer statusCode, List<Object> errorDetails) {}
