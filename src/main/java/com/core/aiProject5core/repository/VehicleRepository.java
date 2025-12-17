package com.core.aiProject5core.repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.core.aiProject5core.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	Optional<Vehicle> findById(Long id);
	
	// 모델 코드로 검색
	@Query("SELECT v FROM Vehicle v LEFT JOIN FETCH v.options WHERE v.modelCode = :modelCode")
	Optional<Vehicle> findWithOptionsByModelCode(@Param("modelCode") String modelCode);
	
	// 전체 차량 목록 조회 -> 메인화면을 위한 페이징 처리
	Page<Vehicle> findAll(Pageable pageable);
	
	// 필드별로 차량 목록을 조회하는 메서드 -> 페이징 처리를 함
	// 제조회사별 차량 목록
	Page<Vehicle> findByBrand(String brand, Pageable pageable);
		
	// 차량구분별 차량 목록
	Page<Vehicle> findByVehicleType(String vehicleType, Pageable pageable);
	
	// 연료타입별 차량 목록
	Page<Vehicle> findByFuelType(String fuelType, Pageable pageable);
	
	// 차량 목록 검색(차량명, 차종)
	Page<Vehicle> findByNameContainingOrVehicleTypeContaining(String name, String vehicleType, Pageable pageable);
	
	// 키워드, 차종, 연료타입별 조회 (사이드 메뉴 라디오 버튼)
	@Query("""
			SELECT v FROM Vehicle v
			WHERE (:keyword IS NULL OR v.name LIKE CONCAT('%', :keyword, '%'))
			AND (:type IS NULL OR v.vehicleType = :type)
			AND (:fuel IS NULL OR v.fuelType = :fuel)
			""")
	Page<Vehicle> searchVehicle(@Param("keyword") String keyword, 
								@Param("type") String type, 
								@Param("fuel") String fuel, Pageable pageable);
	
	// 상세보기에서 트림 선택 시 DB의 차량 트림과 가격을 변경
	@Transactional
	@Modifying
	@Query("UPDATE Vehicle v SET v.trim = :trim, v.finalPrice = :price WHERE v.id = :id")
	int updateTrimAndPrice(@Param("trim") String trim, @Param("price") int price, @Param("id") Long id);
	
}
