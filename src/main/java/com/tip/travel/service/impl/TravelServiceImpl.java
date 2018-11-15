package com.tip.travel.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tip.travel.common.domain.Travel;
import com.tip.travel.common.service.TravelService;
import com.tip.travel.service.dao.TravelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TravelServiceImpl implements TravelService {

    @Autowired
    private TravelDao travelDao;

    @Override
    public Travel getTravelById(Long id) {
        return travelDao.findById(id);
    }

    @Override
    public Integer saveTravel(Travel travel) {
        return travelDao.createNewTravel(travel);
    }

    @Override
    public Integer deleteTravel(Long id) {
        return travelDao.deleteTravel(id);
    }
}
