--작품 목록 더미데이터
INSERT INTO item(id, title, description, image_file_name) VALUES(1, '인사이드 아웃2', '인사이드 아웃2', '1718877390425.jpg')
INSERT INTO item(id, title, description, image_file_name) VALUES(2, '하이재킹', '하이재킹', '1718877422964.jpg')
INSERT INTO item(id, title, description, image_file_name) VALUES(3, '핸섬가이즈', '핸섬가이즈', '1718877427912.jpg')
INSERT INTO item(id, title, description, image_file_name) VALUES(4, '탈주', '탈주', '1718877434706.jpg')
INSERT INTO item(id, title, description, image_file_name) VALUES(5, '캣퍼슨', '캣퍼슨', '1718877438823.jpg')

--댓글 더미데이터
INSERT INTO comment(id, item_id, nickname, body, star) VALUES(1, 1, '사과', '재밌어요', 3.5)
INSERT INTO comment(id, item_id, nickname, body, star) VALUES(2, 1, '오렌지', '꿀잼', 4.5)
INSERT INTO comment(id, item_id, nickname, body, star) VALUES(3, 1, '참외', '노잼', 1.5)