package com.mmatovcik.tribes.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
  private Integer statusCode;
  private List<Object> errorDetails;
}
