package com.uralhalil.zapu.model.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlParam implements Comparable {

    private String name;

    private int priority;

    @Override
    public int compareTo(Object urlParam) {
        if (urlParam == null)
            return -1;
        UrlParam urlParam1 = (UrlParam) urlParam;
        if (priority > urlParam1.priority)
            return 1;
        else if (priority < urlParam1.priority)
            return -1;
        return 0;
    }
}
