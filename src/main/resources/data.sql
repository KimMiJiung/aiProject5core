use 5core;

-- 차량 데이터 18대
INSERT INTO vehicle(
    model_code, name, brand, vehicle_type, trim, base_price, final_price, file_name,
    fuel_type, year, displacement, fuel_efficiency
)
VALUES
('avante', '아반떼', '현대', '세단', 'Standard', 20348000, 20348000, 'avante_01.png', '휘발유', '2025', 1598, 15.20),

('avante-n', '아반떼 N', '현대', '세단', 'Standard', 36176000, 36176000, 'avante_n_01.png', '휘발유', '2025', 1998, 10.50),

('sonata', '쏘나타', '현대', '세단', 'Standard', 28674000, 28674000, 'sonata_01.png', '휘발유', '2025', 1999, 13.80),

('sonata-hybrid', '쏘나타 하이브리드', '현대', '세단', 'Standard', 34000000, 34000000, 'sonata_hybrid_01.png', '하이브리드', '2025', 1999, 19.10),

('grandeur', '그랜저', '현대', '세단', 'Standard', 39638000, 39638000, 'grandeur_01.png', '휘발유', '2025', 2497, 11.20),

('grandeur-hybrid', '그랜저 하이브리드', '현대', '세단', 'Standard', 46179000, 46179000, 'grandeur_hybrid_01.png', '하이브리드', '2025', 2399, 18.00),

('tucson', '투싼', '현대', 'SUV', 'Standard', 32527000, 32527000, 'tucson_01.png', '휘발유', '2025', 1598, 12.60),

('tucson-hybrid', '투싼 하이브리드', '현대', 'SUV', 'Standard', 38772000, 38772000, 'tucson_hybrid_01.png', '하이브리드', '2025', 1598, 16.20),

('santafe', '싼타페', '현대', 'SUV', 'Standard', 41611000, 41611000, 'santafe_01.png', '디젤', '2025', 2199, 13.50),

('santafe-hybrid', '싼타페 하이브리드', '현대', 'SUV', 'Standard', 47916000, 47916000, 'santafe_hybrid_01.png', '하이브리드', '2025', 1598, 14.50),

('palisade', '팰리세이드', '현대', 'SUV', 'Standard', 50570000, 50570000, 'palisade_01.png', '디젤', '2025', 2199, 10.80),

('kona', '코나', '현대', 'SUV', 'Standard', 26844000, 26844000, 'kona_01.png', '휘발유', '2025', 1598, 13.90),

('casper', '캐스퍼', '현대', '경차', 'Standard', 14528000, 14528000, 'casper_01.png', '휘발유', '2025', 998, 14.30),

('venue', '베뉴', '현대', 'SUV', 'Standard', 22879000, 22879000, 'venue_01.png', '휘발유', '2025', 1598, 13.70),

-- 전기차 (displacement = PS / 연비 = km/kWh)
('kona-ev', '코나 EV', '현대', 'SUV', 'Standard', 48650000, 48650000, 'kona_ev_01.png', '전기', '2025', 204, 5.80),

('ioniq5', '아이오닉 5', '현대', 'SUV', 'Standard', 54790000, 54790000, 'ioniq5_01.png', '전기', '2025', 229, 5.10),

('ioniq6', '아이오닉 6', '현대', '세단', 'Standard', 52150000, 52150000, 'ioniq6_01.png', '전기', '2025', 229, 6.20),

('casper-ev', '캐스퍼 EV', '현대', '경차', 'Standard', 31290000, 31290000, 'casper_ev_01.png', '전기', '2025', 136, 5.60);


-- 모델 1페이지에 있는 차량 8대에 대한 색상 및 색상 이미지 데이터
INSERT INTO vehicle_color (color, image_color_url, vehicle_id)
VALUES
('white', 'grandeur_white.png', (select id from vehicle where model_code = 'grandeur')),
('black', 'grandeur_black.png', (select id from vehicle where model_code = 'grandeur')),
('silver', 'grandeur_silver.png', (select id from vehicle where model_code = 'grandeur')),
('blue', 'grandeur_blue.png', (select id from vehicle where model_code = 'grandeur')),

('white', 'grandeur_white.png', (select id from vehicle where model_code = 'grandeur-hybrid')),
('black', 'grandeur_black.png', (select id from vehicle where model_code = 'grandeur-hybrid')),
('silver', 'grandeur_silver.png', (select id from vehicle where model_code = 'grandeur-hybrid')),
('blue', 'grandeur_blue.png', (select id from vehicle where model_code = 'grandeur-hybrid')),

('white', 'venue_white.png', (select id from vehicle where model_code = 'venue')),
('black', 'venue_black.png', (select id from vehicle where model_code = 'venue')),
('red', 'venue_red.png', (select id from vehicle where model_code = 'venue')),
('blue', 'venue_blue.png', (select id from vehicle where model_code = 'venue')),

('white', 'santafe_white.png', (select id from vehicle where model_code = 'santafe')),
('black', 'santafe_black.png', (select id from vehicle where model_code = 'santafe')),
('brown', 'santafe_brown.png', (select id from vehicle where model_code = 'santafe')),
('blue', 'santafe_blue.png', (select id from vehicle where model_code = 'santafe')),

('white', 'santafe_white.png', (select id from vehicle where model_code = 'santafe-hybrid')),
('black', 'santafe_black.png', (select id from vehicle where model_code = 'santafe-hybrid')),
('brown', 'santafe_brown.png', (select id from vehicle where model_code = 'santafe-hybrid')),
('blue', 'santafe_blue.png', (select id from vehicle where model_code = 'santafe-hybrid')),

('white', 'sonata_white.png', (select id from vehicle where model_code = 'sonata')),
('black', 'sonata_black.png', (select id from vehicle where model_code = 'sonata')),
('red', 'sonata_red.png', (select id from vehicle where model_code = 'sonata')),
('blue', 'sonata_blue.png', (select id from vehicle where model_code = 'sonata')),

('white', 'sonata_white.png', (select id from vehicle where model_code = 'sonata-hybrid')),
('black', 'sonata_black.png', (select id from vehicle where model_code = 'sonata-hybrid')),
('red', 'sonata_red.png', (select id from vehicle where model_code = 'sonata-hybrid')),
('blue', 'sonata_blue.png', (select id from vehicle where model_code = 'sonata-hybrid')),

('white', 'avante_white.png', (select id from vehicle where model_code = 'avante')),
('black', 'avante_black.png', (select id from vehicle where model_code = 'avante')),
('silver', 'avante_silver.png', (select id from vehicle where model_code = 'avante')),
('blue', 'avante_blue.png', (select id from vehicle where model_code = 'avante'));

-- 모델 1페이지의 차량 8대에 대한 옵션 데이터
INSERT INTO vehicle_option(option_name, option_code, vehicle_id, trim_level) VALUES
-- 1. 그랜저 (id = 1)
('스마트 크루즈 컨트롤', 'smart_cruise', 1, 'Standard'),
('전방 충돌방지', 'front_assist', 1, 'Standard'),
('후측방 경고', 'rear_warning', 1, 'Standard'),
('8 에어백', 'airbag_8', 1, 'Standard'),
('후방 카메라', 'rear_camera', 1, 'Standard'),

('12.3인치 내비게이션', 'navigation_12', 1, 'Exclusive'),
('통풍 시트', 'ventilated_seat', 1, 'Exclusive'),

('파노라마 선루프', 'panoramic_roof', 1, 'Prestige'),
('Bose 프리미엄 사운드', 'bose_sound', 1, 'Prestige'),
('서라운드 뷰 모니터', 'surround_view', 1, 'Prestige'),

-- 2. 그랜저 하이브리드 (id = 2)
('스마트 크루즈 컨트롤', 'smart_cruise', 2, 'Standard'),
('전방 충돌방지', 'front_assist', 2, 'Standard'),
('후측방 경고', 'rear_warning', 2, 'Standard'),
('8 에어백', 'airbag_8', 2, 'Standard'),
('후방 카메라', 'rear_camera', 2, 'Standard'),

('12.3인치 내비게이션', 'navigation_12', 2, 'Exclusive'),
('통풍 시트', 'ventilated_seat', 2, 'Exclusive'),

('파노라마 선루프', 'panoramic_roof', 2, 'Prestige'),
('Bose 프리미엄 사운드', 'bose_sound', 2, 'Prestige'),
('서라운드 뷰 모니터', 'surround_view', 2, 'Prestige'),

-- 3. 베뉴 (id = 3)
('스마트 크루즈 컨트롤', 'smart_cruise', 3, 'Standard'),
('전방 충돌방지', 'front_assist', 3, 'Standard'),
('후측방 경고', 'rear_warning', 3, 'Standard'),
('8 에어백', 'airbag_8', 3, 'Standard'),
('후방 카메라', 'rear_camera', 3, 'Standard'),

('12.3인치 내비게이션', 'navigation_12', 3, 'Exclusive'),
('통풍 시트', 'ventilated_seat', 3, 'Exclusive'),

('파노라마 선루프', 'panoramic_roof', 3, 'Prestige'),

-- 4. 싼타페 (id = 4)
('스마트 크루즈 컨트롤', 'smart_cruise', 4, 'Standard'),
('전방 충돌방지', 'front_assist', 4, 'Standard'),
('후측방 경고', 'rear_warning', 4, 'Standard'),
('8 에어백', 'airbag_8', 4, 'Standard'),
('후방 카메라', 'rear_camera', 4, 'Standard'),

('12.3인치 내비게이션', 'navigation_12', 4, 'Exclusive'),
('통풍 시트', 'ventilated_seat', 4, 'Exclusive'),

('파노라마 선루프', 'panoramic_roof', 4, 'Prestige'),
('서라운드 뷰 모니터', 'surround_view', 4, 'Prestige'),

-- 5. 싼타페 하이브리드 (id = 5)
('스마트 크루즈 컨트롤', 'smart_cruise', 5, 'Standard'),
('전방 충돌방지', 'front_assist', 5, 'Standard'),
('후측방 경고', 'rear_warning', 5, 'Standard'),
('8 에어백', 'airbag_8', 5, 'Standard'),
('후방 카메라', 'rear_camera', 5, 'Standard'),

('12.3인치 내비게이션', 'navigation_12', 5, 'Exclusive'),
('통풍 시트', 'ventilated_seat', 5, 'Exclusive'),

('파노라마 선루프', 'panoramic_roof', 5, 'Prestige'),
('서라운드 뷰 모니터', 'surround_view', 5, 'Prestige'),

-- 6. 쏘나타 (id = 6)
('스마트 크루즈 컨트롤', 'smart_cruise', 6, 'Standard'),
('전방 충돌방지', 'front_assist', 6, 'Standard'),
('후측방 경고', 'rear_warning', 6, 'Standard'),
('8 에어백', 'airbag_8', 6, 'Standard'),
('후방 카메라', 'rear_camera', 6, 'Standard'),

('12.3인치 내비게이션', 'navigation_12', 6, 'Exclusive'),
('통풍 시트', 'ventilated_seat', 6, 'Exclusive'),

('파노라마 선루프', 'panoramic_roof', 6, 'Prestige'),
('Bose 프리미엄 사운드', 'bose_sound', 6, 'Prestige'),

-- 7. 쏘나타 하이브리드 (id = 7)
('스마트 크루즈 컨트롤', 'smart_cruise', 7, 'Standard'),
('전방 충돌방지', 'front_assist', 7, 'Standard'),
('후측방 경고', 'rear_warning', 7, 'Standard'),
('8 에어백', 'airbag_8', 7, 'Standard'),
('후방 카메라', 'rear_camera', 7, 'Standard'),

('12.3인치 내비게이션', 'navigation_12', 7, 'Exclusive'),
('통풍 시트', 'ventilated_seat', 7, 'Exclusive'),

('파노라마 선루프', 'panoramic_roof', 7, 'Prestige'),
('Bose 프리미엄 사운드', 'bose_sound', 7, 'Prestige'),

-- 8. 아반떼 (id = 8)
('스마트 크루즈 컨트롤', 'smart_cruise', 8, 'Standard'),
('전방 충돌방지', 'front_assist', 8, 'Standard'),
('후측방 경고', 'rear_warning', 8, 'Standard'),
('8 에어백', 'airbag_8', 8, 'Standard'),
('후방 카메라', 'rear_camera', 8, 'Standard'),

('12.3인치 내비게이션', 'navigation_12', 8, 'Exclusive'),
('통풍 시트', 'ventilated_seat', 8, 'Exclusive'),

('파노라마 선루프', 'panoramic_roof', 8, 'Prestige');
