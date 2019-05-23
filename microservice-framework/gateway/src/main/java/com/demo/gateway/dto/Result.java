package com.demo.gateway.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    
    @NotBlank(message = "返回码不能为空")
    private String resultCode;
    private String resultMessage;
}
