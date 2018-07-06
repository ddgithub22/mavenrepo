package com.bid.smc.service;

import java.util.List;

import com.bid.smc.model.bidsense.ModeEntity;

public interface ModeService {
  List<ModeEntity> findAllModes();
}
