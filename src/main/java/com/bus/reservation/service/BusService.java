package com.bus.reservation.service;

import java.util.List;

import com.bus.reservation.dto.BusDTO;
import com.bus.reservation.entity.Bus;

public interface BusService {
	
	 Bus createBus(BusDTO busDTO);			// 버스 생성
	 List<BusDTO> getBusList();			    // 버스 리스트
     BusDTO getBusByNo(Integer busNo);		// 버스 한 개 가져오는 함수(상세보기)
     Bus updateBus(BusDTO busDTO);			// 버스 수정하는 함수
     Boolean deleteBus(Integer busNo);		// 버스 삭제하는 함수
     
     /*
      * DTO --> Entity 전환을 위한 default 메소드
	  *  default 메소드는 기존의 인터페이스를 구현해서 사용하는 Impl클래스들이
 	  *  의무적으로 구현하지 않아도 오류가 발생하지 않는다.
	  *  
	  *  Dto를 파라미터로 받아서 Entity에 담아준다. 왜냐하면 화면에서 받아서
	  *  저장할 때는 Entity 형태로 save(entity)해야 하기 때문이다. 물론
	  *  안해도 되지만 일반적으로 영속 영역에는 Entity만 넣는게 좋다.
      */
     default Bus dtoToEntity(BusDTO busDto) {
    	 Bus entity = Bus.builder()
    			 		 .busNo(busDto.getBusNo())
    			 		 .busNumber(busDto.getBusNumber())
    			 		 .busDriver(busDto.getBusDriver())
    			 		 .busCompany(busDto.getBusCompany())
    			 		 .build();
    	return entity;
     }
     
     
     /*
      * Entity --> DTO 전환을 위한 default 메소드
      * default 메소드는 기존의 인터페이스를 구현해서 사용하는 Impl 클래스들이
	  *  의무적으로 구현하지 않아도 오류가 발생하지 않는다.
      */
     default BusDTO entityToDto(Bus busEntity) {
    	 
    	 BusDTO busDto = BusDTO.builder()
    			 			   .busNo(busEntity.getBusNo())
    			 			   .busNumber(busEntity.getBusNumber())
    			 			   .busDriver(busEntity.getBusDriver())
    			 			   .busCompany(busEntity.getBusCompany())
    			 			   .regDate(busEntity.getRegDate())
    			 			   .modDate(busEntity.getModDate())
    			 			   .build();

    	 return busDto;
     }
     
}
