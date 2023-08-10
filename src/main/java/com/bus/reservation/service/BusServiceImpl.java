package com.bus.reservation.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bus.reservation.dto.BusDTO;
import com.bus.reservation.entity.Bus;
import com.bus.reservation.repository.BusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService{
	
	//의존성 주입 (생성자 or @Autowired 등)
	private final BusRepository busRepository;
	
	/**
	 * ----------------------------------
	 * 			C / R / U / D
	 * ----------------------------------
	 */
	
	/*
	 * 버스 생성
	 */
    @Override
    public Bus createBus(BusDTO busDto) {
        // BusDao를 사용하여 데이터베이스에 버스 생성 로직 수행
    	Bus bus = dtoToEntity(busDto);
        return busRepository.save(bus);
    }
    
    /*
     * 버스 모든 정보 리스트
     */
    @Override
    public List<BusDTO> getBusList() {
        // BusDao를 사용하여 데이터베이스로부터 모든 버스 정보 조회 로직 수행
    	List<Bus> busList = busRepository.findAll();
    	List<BusDTO> busDTOList = busList.stream()
    								  .map(entity -> entityToDto(entity))
    								  .collect(Collectors.toList());
    	return busDTOList;
    }
    
    /*
     * 버스 한 개 가져오는 함수(상세보기)
     */
    @Override
    public BusDTO getBusById(Integer busNo) {
    	// BusDao를 사용하여 데이터베이스로부터 버스 정보 조회 로직 수행
    	Optional<Bus> bus = busRepository.findById(busNo);
    	@SuppressWarnings("unused")
		BusDTO busDTO = null;
    	if(bus.isPresent()) {
    		busDTO = entityToDto(bus.get());
    	}
        return bus.isPresent() ? entityToDto(bus.get()) : null;
    }
    
    /*
     * 버스 수정하는 함수
     */
    @Override
    public Bus updateBus(Bus bus) {
        // BusDao를 사용하여 데이터베이스에서 버스 업데이트 로직 수행
    	return busDao.updateBus(bus);
    }
    
    /*
     * 버스 삭제하는 함수
     */
    @Override
    public Boolean deleteBus(Integer busId) {
        // BusDao를 사용하여 데이터베이스에서 버스 삭제 로직 수행
    	return busDao.deleteBus(busId);
    }
    
   
}
