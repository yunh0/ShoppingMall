-- 판매자 계정 생성
INSERT INTO member (user_id, email, password, name, role_type, created_at, modified_at, created_by, modified_by)
VALUES ('test' ,'test@test.com', '{noop}qwer1234', 'John', 'SELLER', '2025-02-10 17:50:00', '2025-02-10 17:50:00', 'yunho', 'yunho');

INSERT INTO member (user_id, email, password, name, role_type, created_at, modified_at, created_by, modified_by)
VALUES ('test2' ,'test2@test2.com', '{noop}qwer1234', 'Yunho', 'SELLER', '2025-02-14 12:36:00', '2025-02-14 12:36:00', 'yunho', 'yunho');


-- 1. 대분류 (남성의류, 여성의류, 신발, 모자)
INSERT INTO category (name, parent_id, depth) VALUES
                                                ('남성의류', NULL, 1),  -- ID 1
                                                ('여성의류', NULL, 1),  -- ID 2
                                                ('신발', NULL, 1),  -- ID 3
                                                ('모자', NULL, 1);  -- ID 4

-- 2. 중분류 (상의, 하의, 신발, 모자 등)
INSERT INTO category (name, parent_id, depth) VALUES
                                                ('상의', 1, 2),  -- ID 5 (남성의류 - 상의)
                                                ('하의', 1, 2),  -- ID 6 (남성의류 - 하의)
                                                ('상의', 2, 2),  -- ID 7 (여성의류 - 상의)
                                                ('하의', 2, 2),  -- ID 8 (여성의류 - 하의)
                                                ('원피스', 2, 2),  -- ID 9 (여성의류 - 원피스)
                                                ('남성 신발', 3, 2),  -- ID 10
                                                ('여성 신발', 3, 2),  -- ID 11
                                                ('남성 모자', 4, 2),  -- ID 12
                                                ('여성 모자', 4, 2);  -- ID 13

-- 3. 소분류 (티셔츠, 청바지, 운동화, 캡모자 등)
INSERT INTO category (name, parent_id, depth) VALUES
-- 남성 상의
('티셔츠', 5, 3), ('셔츠', 5, 3), ('니트', 5, 3),
-- 남성 하의
('청바지', 6, 3), ('슬랙스', 6, 3), ('반바지', 6, 3),
-- 여성 상의
('블라우스', 7, 3), ('티셔츠', 7, 3), ('니트', 7, 3),
-- 여성 하의
('스커트', 8, 3), ('청바지', 8, 3), ('슬랙스', 8, 3),
-- 남성 신발
('운동화', 10, 3), ('구두', 10, 3), ('샌들', 10, 3),
-- 여성 신발
('운동화', 11, 3), ('구두', 11, 3), ('샌들', 11, 3),
-- 남성 모자
('캡모자', 12, 3), ('비니', 12, 3), ('페도라', 12, 3),
-- 여성 모자
('캡모자', 13, 3), ('비니', 13, 3), ('버킷햇', 13, 3);



-- 판매자 ID 가져오기 (판매자가 한 명뿐이므로 단순 조회)
SET @seller_id = (SELECT user_id FROM member WHERE email = 'test@test.com');

-- 상품 30개 추가
INSERT INTO product (product_name, price, info, count, category_id, seller_id, created_at, modified_at, created_by, modified_by)
VALUES
    ('남성 티셔츠 1', 19900, '편안한 착용감의 남성 티셔츠', 50, 14, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 티셔츠 2', 29900, '베이직한 디자인의 남성 티셔츠', 40, 14, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 셔츠 1', 39900, '세련된 디자인의 남성 셔츠', 30, 15, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 니트 1', 49900, '따뜻한 남성 니트', 20, 16, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 청바지 1', 59000, '슬림 핏 남성 청바지', 25, 17, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 슬랙스 1', 69000, '클래식한 남성 슬랙스', 30, 18, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 반바지 1', 49000, '시원한 여름 반바지', 35, 19, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    ('여성 블라우스 1', 39900, '우아한 디자인의 블라우스', 40, 20, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 티셔츠 1', 29900, '여성 베이직 티셔츠', 50, 21, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 니트 1', 49900, '여성용 따뜻한 니트', 20, 22, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 스커트 1', 59000, '플레어 디자인의 여성 스커트', 25, 23, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 청바지 1', 69000, '여성용 스키니 진', 30, 24, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 슬랙스 1', 59000, '오피스룩 슬랙스', 20, 25, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 원피스 1', 89000, '우아한 디자인의 원피스', 15, 9, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    ('남성 운동화 1', 79000, '편안한 남성용 운동화', 30, 26, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 구두 1', 129000, '고급스러운 남성용 구두', 20, 27, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 샌들 1', 59000, '여름용 샌들', 25, 28, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    ('여성 운동화 1', 89000, '스타일리시한 여성 운동화', 35, 29, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 구두 1', 139000, '고급 여성 구두', 20, 30, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 샌들 1', 69000, '여름 필수템 샌들', 25, 31, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    ('남성 캡모자 1', 29000, '캐주얼한 캡모자', 40, 32, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 비니 1', 39000, '따뜻한 남성 비니', 20, 33, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 페도라 1', 49000, '고급스러운 페도라', 15, 34, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    ('여성 캡모자 1', 29000, '베이직한 여성 캡모자', 40, 35, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 비니 1', 39000, '따뜻한 여성 비니', 20, 36, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 버킷햇 1', 49000, '트렌디한 버킷햇', 15, 37, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    ('남성 반바지 2', 59000, '여름용 남성 반바지', 30, 19, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 원피스 2', 99000, '땡땡이 원피스', 20, 9, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 운동화 2', 89000, '트렌디한 운동화', 25, 26, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 구두 2', 149000, '우아한 여성 구두', 20, 30, @seller_id, NOW(), NOW(), 'yunho', 'yunho');


-- 두번째 판매자 상품 9개 추가
SET @seller_id = (SELECT user_id FROM member WHERE email = 'test2@test2.com');

INSERT INTO product (product_name, price, info, count, category_id, seller_id, created_at, modified_at, created_by, modified_by)
VALUES
    ('티셔츠 1', 19900, '편안한 착용감의 남성 티셔츠', 50, 14, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('티셔츠 2', 29900, '베이직한 디자인의 남성 티셔츠', 40, 14, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('셔츠 1', 39900, '세련된 디자인의 남성 셔츠', 30, 15, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('니트 1', 49900, '따뜻한 남성 니트', 20, 16, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('청바지 1', 59000, '슬림 핏 남성 청바지', 25, 17, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('슬랙스 1', 69000, '클래식한 남성 슬랙스', 30, 18, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('바지 1', 49000, '시원한 여름 반바지', 35, 19, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('운동화 1', 89000, '트렌디한 운동화', 25, 26, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('구두 1', 149000, '우아한 여성 구두', 20, 30, @seller_id, NOW(), NOW(), 'yunho', 'yunho');