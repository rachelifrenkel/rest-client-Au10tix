package com.au10tix.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class General {
    private Information information;
    private Quantities quantities;
}
