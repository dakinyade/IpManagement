package com.test.ipmanagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceState {

    private int id;
    private String desc;
    private String state;
}
