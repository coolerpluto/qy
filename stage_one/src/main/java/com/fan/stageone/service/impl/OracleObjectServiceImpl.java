package com.fan.stageone.service.impl;

import com.fan.stageone.entity.oracle.*;
import com.fan.stageone.service.OracleObjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OracleObjectServiceImpl implements OracleObjectService {
    @Override
    public List<OracleTableObject> getOracleTableObjectList() {
        return null;
    }

    @Override
    public List<OracleViewObject> getOracleViewObjectList() {
        return null;
    }

    @Override
    public List<OracleColumnObject> getOracleColumnObjectList() {
        return null;
    }

    @Override
    public List<OracleIndexObject> getOracleIndexObjectList() {
        return null;
    }

    @Override
    public List<OracleConstraintObject> getOracleConstraintObject() {
        return null;
    }
}
