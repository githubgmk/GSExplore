package com.gmk.geisa.model;

import java.io.Serializable;

/**
 * Created by kenjinsan on 7/25/17.
 */

public class mUnit implements Serializable {
    private String UnitId;
    private String UnitName;
    private int Status;

    public mUnit(String unitId, String unitName, int status) {
        UnitId = unitId;
        UnitName = unitName;
        Status = status;
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
