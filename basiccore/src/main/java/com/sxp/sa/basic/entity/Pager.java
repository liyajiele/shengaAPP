package com.sxp.sa.basic.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sxp
 * on 2017/1/3.
 */

@Getter
@Setter
public class Pager<T> {

    private List<T> content ;

    private Boolean first;

    private Boolean last;

    private Integer number;

    private Integer numberOfElements;

    private Integer size;

    private Long totalElements;

    private Integer totalPages;


}
