-- 판매자 계정 생성
INSERT INTO member (user_id, email, password, name, role_type, created_at, modified_at, created_by, modified_by)
VALUES ('test' ,'test@test.com', '{noop}qwer1234', 'John', 'SELLER', '2025-02-10 17:50:00', '2025-02-10 17:50:00', 'yunho', 'yunho');

INSERT INTO member (user_id, email, password, name, role_type, created_at, modified_at, created_by, modified_by)
VALUES ('test2' ,'test2@test2.com', '{noop}qwer1234', 'Yunho', 'SELLER', '2025-02-14 12:36:00', '2025-02-14 12:36:00', 'yunho', 'yunho');


-- 1. 대분류 (남성, 여성)
INSERT INTO category (name, parent_id, depth) VALUES
                                                  ('남성', NULL, 1),  -- ID 1
                                                  ('여성', NULL, 1);  -- ID 2

-- 2. 중분류 (상의, 하의, 신발, 모자, 원피스)
INSERT INTO category (name, parent_id, depth) VALUES
                                                  -- 남성 중분류
                                                  ('상의', 1, 2),  -- ID 3
                                                  ('하의', 1, 2),  -- ID 4
                                                  ('신발', 1, 2),  -- ID 5
                                                  ('모자', 1, 2),  -- ID 6

                                                  -- 여성 중분류
                                                  ('상의', 2, 2),  -- ID 7
                                                  ('하의', 2, 2),  -- ID 8
                                                  ('신발', 2, 2),  -- ID 9
                                                  ('모자', 2, 2),  -- ID 10
                                                  ('원피스', 2, 2);  -- ID 11

-- 3. 소분류 (티셔츠, 청바지, 운동화, 모자 등)
INSERT INTO category (name, parent_id, depth) VALUES
                                                  ('티셔츠', 3, 3), -- ID 12
                                                  ('셔츠', 3, 3), -- ID 13
                                                  ('니트', 3, 3), -- ID 14

                                                  -- 남성 하의 (ID 4)
                                                  ('청바지', 4, 3), -- ID 15
                                                  ('슬랙스', 4, 3), -- ID 16
                                                  ('반바지', 4, 3), -- ID 17

                                                  -- 남성 신발 (ID 5)
                                                  ('운동화', 5, 3), -- ID 18
                                                  ('구두', 5, 3), -- ID 19
                                                  ('샌들', 5, 3), -- ID 20

                                                  -- 남성 모자 (ID 6)
                                                  ('캡모자', 6, 3), -- ID 21
                                                  ('비니', 6, 3), -- ID 22
                                                  ('페도라', 6, 3), -- ID 23

                                                  -- 여성 상의 (ID 7)
                                                  ('블라우스', 7, 3), -- ID 24
                                                  ('티셔츠', 7, 3), -- ID 25
                                                  ('니트', 7, 3), -- ID 26

                                                  -- 여성 하의 (ID 8)
                                                  ('스커트', 8, 3), -- ID 27
                                                  ('청바지', 8, 3), -- ID 28
                                                  ('슬랙스', 8, 3), -- ID 29

                                                  -- 여성 신발 (ID 9)
                                                  ('운동화', 9, 3), -- ID 30
                                                  ('구두', 9, 3), -- ID 31
                                                  ('샌들', 9, 3), -- ID 32

                                                  -- 여성 모자 (ID 10)
                                                  ('캡모자', 10, 3), -- ID 33
                                                  ('비니', 10, 3), -- ID 34
                                                  ('버킷햇', 10, 3), -- ID 35

                                                  -- 여성 원피스 (ID 11)
                                                  ('미니 원피스', 11, 3), -- ID 36
                                                  ('롱 원피스', 11, 3), -- ID 37
                                                  ('정장 원피스', 11, 3); -- ID 38



-- 첫 번째 판매자 ID 가져오기
SET @seller_id = (SELECT user_id FROM member WHERE email = 'test@test.com');

-- 첫 번째 판매자 상품 추가 (각 소분류별 2개씩, 총 52개)
INSERT INTO product (product_name, price, info, count, category_id, seller_id, created_at, modified_at, created_by, modified_by)
VALUES
    -- 남성 상의 (티셔츠, 셔츠, 니트)
    ('남성 티셔츠 1', 19900, '설명1', 50, 12, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 티셔츠 2', 20900, '설명2', 40, 12, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 셔츠 1', 29900, '설명3', 30, 13, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 셔츠 2', 30900, '설명4', 25, 13, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 니트 1', 39900, '설명5', 20, 14, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 니트 2', 40900, '설명6', 15, 14, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    -- 남성 하의 (청바지, 슬랙스, 반바지)
    ('남성 청바지 1', 59000, '설명7', 25, 15, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 청바지 2', 62000, '설명8', 20, 15, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 슬랙스 1', 69000, '설명9', 30, 16, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 슬랙스 2', 72000, '설명10', 25, 16, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 반바지 1', 49000, '설명11', 35, 17, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 반바지 2', 52000, '설명12', 30, 17, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    -- 남성 신발 (운동화, 구두, 샌들)
    ('남성 운동화 1', 79000, '설명13', 30, 18, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 운동화 2', 81000, '설명14', 25, 18, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 구두 1', 129000, '설명15', 20, 19, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 구두 2', 131000, '설명16', 15, 19, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 샌들 1', 59000, '설명17', 25, 20, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 샌들 2', 61000, '설명18', 20, 20, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    -- 남성 모자 (캡모자, 비니, 페도라)
    ('남성 캡모자 1', 29000, '설명19', 40, 21, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 캡모자 2', 31000, '설명20', 35, 21, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 비니 1', 39000, '설명21', 20, 22, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 비니 2', 41000, '설명22', 15, 22, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 페도라 1', 49000, '설명23', 15, 23, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('남성 페도라 2', 51000, '설명24', 10, 23, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    -- 여성 상의 (블라우스, 티셔츠, 니트)
    ('여성 블라우스 1', 39900, '설명25', 40, 24, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 블라우스 2', 41900, '설명26', 35, 24, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 티셔츠 1', 29900, '설명27', 50, 25, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 티셔츠 2', 31900, '설명28', 45, 25, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 니트 1', 49900, '설명29', 20, 26, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 니트 2', 51900, '설명30', 18, 26, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    -- 여성 하의 (청바지, 슬랙스, 스커트)
    ('여성 청바지 1', 69000, '설명31', 30, 28, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 청바지 2', 71000, '설명32', 25, 28, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 슬랙스 1', 59000, '설명33', 20, 29, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 슬랙스 2', 61000, '설명34', 18, 29, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 스커트 1', 59000, '설명35', 25, 27, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 스커트 2', 61000, '설명36', 22, 27, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    -- 여성 신발 (운동화, 구두, 샌들)
    ('여성 운동화 1', 89000, '설명25', 30, 30, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 운동화 2', 91000, '설명26', 25, 30, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 구두 1', 139000, '설명27', 20, 31, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 구두 2', 141000, '설명28', 18, 31, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 샌들 1', 69000, '설명29', 25, 32, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 샌들 2', 71000, '설명30', 22, 32, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    -- 여성 모자 (캡모자, 비니, 버킷햇)
    ('여성 캡모자 1', 29000, '설명31', 40, 33, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 캡모자 2', 31000, '설명32', 35, 33, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 비니 1', 39000, '설명33', 20, 34, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 비니 2', 41000, '설명34', 18, 34, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 버킷햇 1', 49000, '설명35', 15, 35, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 버킷햇 2', 51000, '설명36', 12, 35, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),

    -- 여성 원피스 (미니 원피스, 롱 원피스, 정장 원피스)
    ('여성 미니 원피스 1', 89000, '설명37', 15, 36, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 미니 원피스 2', 91000, '설명38', 10, 36, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 롱 원피스 1', 93000, '설명39', 8, 37, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 롱 원피스 2', 95000, '설명40', 6, 37, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 1', 97000, '설명41', 4, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 2', 99000, '설명42', 2, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 3', 67000, '설명43', 100, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 4', 74000, '설명44', 55, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 5', 56000, '설명45', 50, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 6', 99000, '설명46', 90, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 7', 45000, '설명47', 200, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 8', 55000, '설명48', 103, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 9', 89000, '설명49', 54, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 10', 100000, '설명50', 32, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 11', 140000, '설명51', 66, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 12', 139000, '설명52', 100, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 13', 120000, '설명53', 98, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 14', 43000, '설명54', 91, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 15', 63000, '설명55', 13, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 16', 61000, '설명56', 12, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 17', 91000, '설명57', 18, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 18', 87000, '설명58', 22, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 19', 99900, '설명59', 29, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho'),
    ('여성 정장 원피스 20', 75000, '설명60', 2, 38, @seller_id, NOW(), NOW(), 'yunho', 'yunho');

