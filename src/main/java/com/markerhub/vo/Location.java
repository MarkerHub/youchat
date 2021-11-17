package com.markerhub.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Location implements Serializable {

    String name;

    double distance;

    double lng;

    double lat;

}
