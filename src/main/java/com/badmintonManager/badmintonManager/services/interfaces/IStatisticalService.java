package com.badmintonManager.badmintonManager.services.interfaces;

import com.badmintonManager.badmintonManager.models.StatisticalDTO;
import com.badmintonManager.badmintonManager.models.StatisticalFilterDTO;

public interface IStatisticalService {
    StatisticalDTO getStatistical(StatisticalFilterDTO model);
}
