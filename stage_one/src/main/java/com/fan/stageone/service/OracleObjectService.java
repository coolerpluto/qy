package com.fan.stageone.service;

import com.fan.stageone.entity.oracle.*;

import java.util.List;

public interface OracleObjectService {
    List<OracleTableObject> getOracleTableObjectList();

    List<OracleViewObject> getOracleViewObjectList();

    List<OracleColumnObject> getOracleColumnObjectList();

    List<OracleIndexObject> getOracleIndexObjectList();

    List<OracleConstraintObject> getOracleConstraintObject();
}
