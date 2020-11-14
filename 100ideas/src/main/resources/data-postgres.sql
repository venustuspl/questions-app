INSERT INTO categories (id,name) VALUES ('f2d8e16d-56c5-48fc-8603-2de2f9f4a119','książki'),('5e1cb7f1-49a1-43ff-b6ba-96833d02b570','filmy');
INSERT INTO categories (id,name,parent) VALUES('64497d0a-3e32-418e-a9fd-4851a13cf144','komedie','5e1cb7f1-49a1-43ff-b6ba-96833d02b570');
INSERT INTO categories (id,name) VALUES ('1167c74f-6259-4002-97c5-0cb42854941e','kosmetyki');


INSERT INTO questions (id,name,category_id,creation_date) VALUES
('84e1c33a-7ab0-4a17-b5a2-d23a592b46ca','Jaką komedię polecacie na weekend','64497d0a-3e32-418e-a9fd-4851a13cf144','2020-10-25 00:00:00.000')
,('e659d5b0-1ae6-4551-bb01-74c3db5d54c8','Książka na pochmurne dni','f2d8e16d-56c5-48fc-8603-2de2f9f4a119','2020-10-25 00:00:00.000')
,('3ade5e97-4264-4a67-8123-5027bda974fa','Jaki krem odmładzający polecasz?','1167c74f-6259-4002-97c5-0cb42854941e','2020-10-30 22:50:08.000');

INSERT INTO answers (id,body,question_id,creation_date) VALUES
('682463e4-56c7-4422-b6ba-2c4288584adf','Zgon na pogrzebie','84e1c33a-7ab0-4a17-b5a2-d23a592b46ca','2020-10-25 00:00:00.000')
,('f1f61982-5fe2-402a-bdbc-6394f6d385b5','Chłopaki nie płaczą','84e1c33a-7ab0-4a17-b5a2-d23a592b46ca','2020-10-25 00:00:00.000')
,('18718aa9-b47d-426e-865b-521e0e110cc5','Porąb i spal','e659d5b0-1ae6-4551-bb01-74c3db5d54c8','2020-10-25 00:00:00.000')
;