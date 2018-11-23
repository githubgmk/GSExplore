package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 4/22/17.
 */

public class mArea implements Serializable {
    private int AreaId;
    private String AreaCode;
    private String AreaName;
    private  int RegionId;

    public mArea() {
    }

    public mArea(int areaId, String areaCode, String areaName,int regionId) {
        AreaId = areaId;
        AreaCode = areaCode;
        AreaName = areaName;
        RegionId=regionId;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public int getRegionId() {
        return RegionId;
    }

    public void setRegionId(int regionId) {
        RegionId = regionId;
    }
}
