package io.github.luaprogrammer.poc.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Errors {
    private String msgUser;
    private String msgServer;
}