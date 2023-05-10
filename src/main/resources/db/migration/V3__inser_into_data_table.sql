
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3444 (class 0 OID 17362)
-- Dependencies: 225
-- Data for Name: tbl_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_role (role_id, name) VALUES ('ADMIN', 'ROLE_ADMIN');
INSERT INTO public.tbl_role (role_id, name) VALUES ('USER', 'ROLE_USER');
INSERT INTO public.tbl_role (role_id, name) VALUES ('DRIVER', 'ROLE_DRIVER');


--
-- TOC entry 3433 (class 0 OID 17311)
-- Dependencies: 214
-- Data for Name: tbl_account; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_account (account_id, created_at, password, updated_at, username, role_id) VALUES ('7662e619-b22d-40f5-963a-894cf8139b3d', '2023-04-21 09:43:03.562', '$2a$10$Kkbn5Sx.8R40OVjCUBoW5e/ftIvJui1Z8FAeEWmCYHC6RmXt3QE12', NULL, 'tai', 'DRIVER');
INSERT INTO public.tbl_account (account_id, created_at, password, updated_at, username, role_id) VALUES ('b76d5751-f04b-4b82-b17f-3a5c06a545e5', '2023-04-21 09:39:39.773', '$2a$10$S7jFqTEmdLI.4tb24.trEOvFUdPkBQmJjtympfQlhSbUkX0YFT83u', NULL, 'admin', 'ADMIN');
INSERT INTO public.tbl_account (account_id, created_at, password, updated_at, username, role_id) VALUES ('24ba584c-f1c5-44a0-a0a4-a6d50c92bdab', '2023-04-21 09:44:22.404', '$2a$10$N9IHguvbQ/RFwvc6IDJrneH5HXvDPevkfPJdq4skxYTQgfkzfugn2', NULL, 'tai2', 'DRIVER');
INSERT INTO public.tbl_account (account_id, created_at, password, updated_at, username, role_id) VALUES ('26912360-eb14-4832-8cae-4ec6da48296c', '2023-04-21 09:45:18.481', '$2a$10$HlpbQP4KToxdu5Gc1lU2t.djAr85dwNTPdqD4ECjf.zmHmbjJa.PO', NULL, 'user', 'USER');
INSERT INTO public.tbl_account (account_id, created_at, password, updated_at, username, role_id) VALUES ('2449c3d9-1cf3-4d5c-af3c-1f9cce255d35', '2023-04-21 09:45:40.293', '$2a$10$wgeHCWfL5CD68MHAeaWdMucDHrKnFzi4zGK7DscnIRlBhWBu99eZu', NULL, 'user2', 'USER');
INSERT INTO public.tbl_account (account_id, created_at, password, updated_at, username, role_id) VALUES ('b0f3483c-1e19-43c7-b41f-30c2f7ebd839', '2023-04-21 09:46:28.936', '$2a$10$OW2pXX.QjmgOG4pOibDGbOQAFJ8Ir8zfGLZBrwp7DRYXumwSOXcjK', NULL, 'user3', 'USER');


INSERT INTO public.tbl_type_car(type_car_id, total_charis, type_car_name)
VALUES (1, 22, 'Limousine 22 VIP double rooms');
INSERT INTO public.tbl_type_car(type_car_id, total_charis, type_car_name)
VALUES (2, 30, 'Limousine 30 VIP double rooms');
INSERT INTO public.tbl_type_car(type_car_id, total_charis, type_car_name)
VALUES (3, 30, 'Limousine 30 seats');
INSERT INTO public.tbl_type_car(type_car_id, total_charis, type_car_name)
VALUES (4, 11, 'Limousine 11 seats');
INSERT INTO public.tbl_type_car(type_car_id, total_charis, type_car_name)
VALUES (5, 4, '4 seater seat');
INSERT INTO public.tbl_type_car(type_car_id, total_charis, type_car_name)
VALUES (6, 8, '8 seater seat');
--
-- TOC entry 3434 (class 0 OID 17316)
-- Dependencies: 215
-- Data for Name: tbl_car; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_car (car_id, car_number, created_at, status, update_at, type_car_id) VALUES ('924e7cef-da7b-45b6-b94d-deb50812d647', 7071, '2023-04-21 14:40:40.978', true, NULL,1);
INSERT INTO public.tbl_car (car_id, car_number, created_at, status, update_at, type_car_id) VALUES ('6e386c84-5713-4cb8-9ebd-484705fbdb06', 7072, '2023-04-21 14:40:45.402', true, NULL,2);


--
-- TOC entry 3435 (class 0 OID 17321)
-- Dependencies: 216
-- Data for Name: tbl_chair; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('3b337ba2-f1d1-4d71-8f7b-fc91d75bb6a8', 1, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('10807f97-178e-47c4-8456-1d8ed0c464ab', 2, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('aa45e3c9-f09f-4b8f-a6eb-8892ade8fabb', 3, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('36c64f55-e98a-4b00-a7cc-5bf37ed9fe49', 4, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('9df23be6-6179-43a0-aa1f-fede28866434', 5, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('bb254f57-f6ee-4fc6-8915-a66aa99a886f', 6, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('23e36317-ae64-426d-8913-6f7fc986bc2f', 7, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('03b432e1-d51a-429c-8989-03fdcacade04', 8, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('5026ebed-4ac7-47ea-b6ae-9d68262851ca', 9, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('73fea4b3-a597-4b18-8447-b925e0b1f0f6', 10, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('db670df9-ce97-48ba-b01b-9dac0c35f4b4', 11, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('a48a9e18-fee6-458c-8774-f98e87b09ffa', 12, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('59498287-71c8-4762-87ee-f182cf354654', 13, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('d6cf63cb-4985-4efe-a2b6-ec8158fecd91', 14, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('1b236045-4e8e-487d-8984-fd0a0d76fc59', 15, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('4f674ca5-3dde-49d9-84eb-75977119d684', 16, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('e67b1f4c-4076-445f-97c7-ec0c9adc9588', 17, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('17324bb4-4a02-4002-ab58-2bab53dc961b', 18, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('42936193-b6d7-4695-91cd-d23e8796c176', 19, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('99bf7933-bd8b-4be6-9221-53f787f202f6', 20, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('c589c21a-1a00-49d9-8710-b0a78b2e9ffc', 21, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('224f37bf-a2d9-49b8-9ae9-e5881f231b64', 22, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('7970ff5d-83e2-4bc7-8bbc-f87036e90ad6', 23, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('04b26c5e-9842-48d2-8559-cdbedd58afa1', 24, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('e1f6e43b-6046-4e3a-b418-910e8fcb5cd4', 25, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('ea8f51e1-7b00-4bfa-9125-b0fdbfdda4b3', 26, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('7cd86040-3f2b-4d4c-a57b-c203d26a8841', 27, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('ea3f3572-f517-4eda-b367-a664e61cdeef', 28, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('89e9cf03-a88c-4762-9a9c-3f6c6ce201e0', 29, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('f024ca4f-42cb-4e26-bb32-6db1c01a2753', 30, true, '924e7cef-da7b-45b6-b94d-deb50812d647');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('51310889-d1f2-40b2-94a0-18aafc10658c', 1, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('7791b867-3671-4002-9d7b-2bb328bffb1f', 2, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('007c5afe-87e9-4db6-b5c6-7a4948530c63', 3, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('c3ec9b26-0068-477c-8217-c907dc0d313e', 4, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('92d19d72-992d-4d24-952f-c69cd2b42559', 5, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('b640b3a3-b9c6-43ef-b782-6f2151d0053c', 6, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('93a2996c-0215-451f-a5fb-89fc890f4aca', 7, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('f513a19a-c911-41b4-8a31-3f63327ff1d2', 8, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('d3206950-6105-4b73-aee2-06e07fee676d', 9, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('059e747e-5b45-475e-b34d-c337f8974ce4', 10, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('bcff172a-4b5d-44eb-a33f-2ebced31684e', 11, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('8daf088e-eab2-4925-882d-f4972747ef1d', 12, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('034e6529-c41e-40ff-a15a-85f1411e4c16', 13, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('e725c970-8bfd-4165-b88a-6547ce58550f', 14, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('c4de497f-f9f4-4d2b-a8e8-4179f5ce5632', 15, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('5f0faa83-7e9a-45dc-b161-aa7a2da08ed4', 16, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('97c71311-1cfe-4b6d-8673-3b57bef4b872', 17, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('1bf9383a-b51c-4148-ab7c-4457015e3cff', 18, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('066bfe88-1967-4b4d-a3dd-74605324ce37', 19, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('f36bf68b-6a84-43cf-81a5-fbc94c60cc7a', 20, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('9d144322-ddb1-41e9-ab74-8e8fcce27140', 21, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');
INSERT INTO public.tbl_chair (chair_id, chair_number, status, car_id) VALUES ('3163957c-3c63-4e73-8177-d91c8e1f207c', 22, true, '6e386c84-5713-4cb8-9ebd-484705fbdb06');



--
-- TOC entry 3450 (class 0 OID 17394)
-- Dependencies: 231
-- Data for Name: tbl_userr; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_userr (user_id, address, created_at, email, full_name, phone_number, status, updated_at, account_id) VALUES ('c673f457-240e-4343-9834-88ac5846b1de', 'Đà Lạt', '2023-04-21 09:39:39.815', 'phongbuibsp3@gmail.com', 'CEO BusStation', '0762590239', true, NULL, 'b76d5751-f04b-4b82-b17f-3a5c06a545e5');
INSERT INTO public.tbl_userr (user_id, address, created_at, email, full_name, phone_number, status, updated_at, account_id) VALUES ('e325a2df-5f15-47bc-8bcd-5ee85821de88', 'Lâm Đồng', '2023-04-21 09:43:03.575', 'us@gmail.com', 'Nguyễn Lê Tài', '0762590231', true, NULL, '7662e619-b22d-40f5-963a-894cf8139b3d');
INSERT INTO public.tbl_userr (user_id, address, created_at, email, full_name, phone_number, status, updated_at, account_id) VALUES ('3a651aaf-b003-4baa-aece-c9379fed4ef7', 'Vĩnh Phúc', '2023-04-21 09:44:22.411', 'taixe2@gmail.com', 'Nguyễn Lê Tú', '0762590230', true, NULL, '24ba584c-f1c5-44a0-a0a4-a6d50c92bdab');
INSERT INTO public.tbl_userr (user_id, address, created_at, email, full_name, phone_number, status, updated_at, account_id) VALUES ('cb1f8b5e-f4a7-4426-8360-23df5e08733b', 'Khánh Hòa', '2023-04-21 09:45:18.496', 'loan@gmail.com', 'Nguyễn Loan', '0762590234', true, NULL, '26912360-eb14-4832-8cae-4ec6da48296c');
INSERT INTO public.tbl_userr (user_id, address, created_at, email, full_name, phone_number, status, updated_at, account_id) VALUES ('869b27a1-f289-482f-a353-06e520f218ea', 'Kiên Giang', '2023-04-21 09:45:40.373', 'duc@gmail.com', 'Lê Đức', '0762590238', true, NULL, '2449c3d9-1cf3-4d5c-af3c-1f9cce255d35');
INSERT INTO public.tbl_userr (user_id, address, created_at, email, full_name, phone_number, status, updated_at, account_id) VALUES ('4e4783b1-18b5-40cf-a4bc-eb01fc78222f', 'Hà Nội', '2023-04-21 09:46:28.939', 'hkhang@gmail.com', 'Hoàng Khang', '0762590222', true, NULL, 'b0f3483c-1e19-43c7-b41f-30c2f7ebd839');


--
-- TOC entry 3436 (class 0 OID 17326)
-- Dependencies: 217
-- Data for Name: tbl_employee; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_employee (employee_id, created_at, dob, update_at, yoe, car_id, user_id) VALUES ('2a87bc73-6955-4d2e-8497-37c1111a520c', '2023-04-21 14:23:26.548', '1995-06-01 07:00:00', NULL, 5, '6e386c84-5713-4cb8-9ebd-484705fbdb06', 'e325a2df-5f15-47bc-8bcd-5ee85821de88');
INSERT INTO public.tbl_employee (employee_id, created_at, dob, update_at, yoe, car_id, user_id) VALUES ('33c215b4-fdfc-4f00-a568-cbcdc4c936f6', '2023-04-21 14:23:48.927', '1995-06-01 07:00:00', NULL, 5, '924e7cef-da7b-45b6-b94d-deb50812d647', '3a651aaf-b003-4baa-aece-c9379fed4ef7');


--
-- TOC entry 3437 (class 0 OID 17331)
-- Dependencies: 218
-- Data for Name: tbl_leave; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3443 (class 0 OID 17356)
-- Dependencies: 224
-- Data for Name: tbl_province; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_province (province_id, name) VALUES (1, 'An Giang');
INSERT INTO public.tbl_province (province_id, name) VALUES (2, 'Bà Rịa – Vũng Tàu');
INSERT INTO public.tbl_province (province_id, name) VALUES (3, 'Bạc Liêu');
INSERT INTO public.tbl_province (province_id, name) VALUES (4, 'Bắc Giang');
INSERT INTO public.tbl_province (province_id, name) VALUES (5, 'Bắc Kạn');
INSERT INTO public.tbl_province (province_id, name) VALUES (6, 'Bắc Ninh');
INSERT INTO public.tbl_province (province_id, name) VALUES (7, 'Bến Tre');
INSERT INTO public.tbl_province (province_id, name) VALUES (8, 'Bình Dương');
INSERT INTO public.tbl_province (province_id, name) VALUES (9, 'Bình Định');
INSERT INTO public.tbl_province (province_id, name) VALUES (10, 'Bình Phước');
INSERT INTO public.tbl_province (province_id, name) VALUES (11, 'Bình Thuận');
INSERT INTO public.tbl_province (province_id, name) VALUES (12, 'Cà Mau');
INSERT INTO public.tbl_province (province_id, name) VALUES (13, 'Cao Bằng');
INSERT INTO public.tbl_province (province_id, name) VALUES (14, 'Cần Thơ');
INSERT INTO public.tbl_province (province_id, name) VALUES (15, 'Đà Nẵng');
INSERT INTO public.tbl_province (province_id, name) VALUES (16, 'Đắk Lắk');
INSERT INTO public.tbl_province (province_id, name) VALUES (17, 'Đắk Nông');
INSERT INTO public.tbl_province (province_id, name) VALUES (18, 'Điện Biên');
INSERT INTO public.tbl_province (province_id, name) VALUES (19, 'Đồng Nai');
INSERT INTO public.tbl_province (province_id, name) VALUES (20, 'Đồng Tháp');
INSERT INTO public.tbl_province (province_id, name) VALUES (21, 'Gia Lai');
INSERT INTO public.tbl_province (province_id, name) VALUES (22, 'Hà Giang');
INSERT INTO public.tbl_province (province_id, name) VALUES (23, 'Hà Nam');
INSERT INTO public.tbl_province (province_id, name) VALUES (24, 'Hà Nội');
INSERT INTO public.tbl_province (province_id, name) VALUES (25, 'Hà Tĩnh');
INSERT INTO public.tbl_province (province_id, name) VALUES (26, 'Hải Dương');
INSERT INTO public.tbl_province (province_id, name) VALUES (27, 'Hải Phòng');
INSERT INTO public.tbl_province (province_id, name) VALUES (28, 'Hậu Giang');
INSERT INTO public.tbl_province (province_id, name) VALUES (29, 'Hòa Bình');
INSERT INTO public.tbl_province (province_id, name) VALUES (30, 'Thành phố Hồ Chí Minh');
INSERT INTO public.tbl_province (province_id, name) VALUES (31, 'Hưng Yên');
INSERT INTO public.tbl_province (province_id, name) VALUES (32, 'Khánh Hòa');
INSERT INTO public.tbl_province (province_id, name) VALUES (33, 'Kiên Giang');
INSERT INTO public.tbl_province (province_id, name) VALUES (34, 'Kon Tum');
INSERT INTO public.tbl_province (province_id, name) VALUES (35, 'Lai Châu');
INSERT INTO public.tbl_province (province_id, name) VALUES (36, 'Lạng Sơn');
INSERT INTO public.tbl_province (province_id, name) VALUES (37, 'Lào Cai');
INSERT INTO public.tbl_province (province_id, name) VALUES (38, 'Lâm Đồng');
INSERT INTO public.tbl_province (province_id, name) VALUES (39, 'Long An');
INSERT INTO public.tbl_province (province_id, name) VALUES (40, 'Nam Định');
INSERT INTO public.tbl_province (province_id, name) VALUES (41, 'Nghệ An');
INSERT INTO public.tbl_province (province_id, name) VALUES (42, 'Ninh Bình');
INSERT INTO public.tbl_province (province_id, name) VALUES (43, 'Ninh Thuận');
INSERT INTO public.tbl_province (province_id, name) VALUES (44, 'Phú Thọ');
INSERT INTO public.tbl_province (province_id, name) VALUES (45, 'Phú Yên');
INSERT INTO public.tbl_province (province_id, name) VALUES (46, 'Quảng Bình');
INSERT INTO public.tbl_province (province_id, name) VALUES (47, 'Quảng Nam');
INSERT INTO public.tbl_province (province_id, name) VALUES (48, 'Quảng Ngãi');
INSERT INTO public.tbl_province (province_id, name) VALUES (49, 'Quảng Ninh');
INSERT INTO public.tbl_province (province_id, name) VALUES (50, 'Quảng Trị');
INSERT INTO public.tbl_province (province_id, name) VALUES (51, 'Sóc Trăng');
INSERT INTO public.tbl_province (province_id, name) VALUES (52, 'Sơn La');
INSERT INTO public.tbl_province (province_id, name) VALUES (53, 'Tây Ninh');
INSERT INTO public.tbl_province (province_id, name) VALUES (54, 'Thái Bình');
INSERT INTO public.tbl_province (province_id, name) VALUES (55, 'Thái Nguyên');
INSERT INTO public.tbl_province (province_id, name) VALUES (56, 'Thanh Hóa');
INSERT INTO public.tbl_province (province_id, name) VALUES (57, 'Thừa Thiên Huế');
INSERT INTO public.tbl_province (province_id, name) VALUES (58, 'Tiền Giang');
INSERT INTO public.tbl_province (province_id, name) VALUES (59, 'Trà Vinh');
INSERT INTO public.tbl_province (province_id, name) VALUES (60, 'Tuyên Quang');
INSERT INTO public.tbl_province (province_id, name) VALUES (61, 'Vĩnh Long');
INSERT INTO public.tbl_province (province_id, name) VALUES (62, 'Vĩnh Phúc');
INSERT INTO public.tbl_province (province_id, name) VALUES (63, 'Yên Bái');


--
-- TOC entry 3439 (class 0 OID 17339)
-- Dependencies: 220
-- Data for Name: tbl_location; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (1, 'Thành Phố Long Xuyên', 1);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (2, 'Thành Phố Bà Rịa', 2);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (3, 'Thành Phố Bạc Liêu', 3);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (4, 'Thành Phố Bắc Giang', 4);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (5, 'Thành Phố Bắc Kạn', 5);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (6, 'Thành Phố Bắc Ninh', 6);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (7, 'Thành Phố Bến Tre', 7);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (8, 'Thành Phố Thủ Dầu Một', 8);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (9, 'Thành Phố Quy Nhơn', 9);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (10, 'Thành Phố Đồng Xoài', 10);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (11, 'Thành Phố Phan Thiết', 11);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (12, 'Thành Phố Cà Mau', 12);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (13, 'Thành Phố Cao Bằng', 13);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (14, 'Quận Ninh Kiều', 14);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (15, 'Quận Hải Châu', 15);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (16, 'Thành Phố Buôn Ma Thuột', 16);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (17, 'Thành Phố Gia Nghĩa', 17);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (18, 'Thành Phố Điện Biên Phủ', 18);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (19, 'Thành Phố Biên Hòa', 19);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (20, 'Thành Phố Cao Lãnh', 20);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (21, 'Thành Phố Pleiku', 21);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (22, 'Thành Phố Hà Giang', 22);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (23, 'Thành Phố Phủ Lý', 23);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (24, 'Quận Hoàn Kiếm', 24);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (25, 'Thành Phố Hà Tĩnh', 25);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (26, 'Thành Phố Hải Dương', 26);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (27, 'Quận Hồng Bàng', 27);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (28, 'Thành Phố Vị Thanh', 28);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (29, 'Thành Phố Hòa Bình', 29);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (30, 'Quận 1', 30);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (31, 'Thành Phố Hưng Yên', 31);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (32, 'Thành Phố Nha Trang', 32);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (33, 'Thành Phố Rạch Giá', 33);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (34, 'Thành Phố Kon Tum', 34);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (35, 'Thành Phố Lai Châu', 35);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (36, 'Thành Phố Lạng Sơn', 36);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (37, 'Thành Phố Lào Cai', 37);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (38, 'Thành Phố Đà Lạt', 38);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (39, 'Thành Phố Tân An', 39);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (40, 'Thành Phố Nam Định', 40);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (41, 'Thành Phố Vinh', 41);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (42, 'Thành Phố Ninh Bình', 42);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (43, 'Thành Phố Phan Rang – Tháp Chàm', 43);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (44, 'Thành Phố Việt Trì', 44);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (45, 'Thành Phố Tuy Hòa', 45);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (46, 'Thành Phố Đồng Hới', 46);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (47, 'Thành Phố Tam Kỳ', 47);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (48, 'Thành Phố Quảng Ngãi', 48);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (49, 'Thành Phố Hạ Long', 49);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (50, 'Thành Phố Đông Hà', 50);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (51, 'Thành Phố Sóc Trăng', 51);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (52, 'Thành Phố Sơn La', 52);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (53, 'Thành Phố Tây Ninh', 53);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (54, 'Thành Phố Thái Bình', 54);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (55, 'Thành Phố Thái Nguyên', 55);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (56, 'Thành Phố Thanh Hóa', 56);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (57, 'Thành Phố Huế', 57);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (58, 'Thành Phố Mỹ Tho', 58);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (59, 'Thành Phố Trà Vinh', 59);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (60, 'Thành Phố Tuyên Quang', 60);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (61, 'Thành Phố Vĩnh Long', 61);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (62, 'Thành Phố Vĩnh Yên', 62);
INSERT INTO public.tbl_location (location_id, name, province_id) VALUES (63, 'Thành Phố Yên Bái', 63);


--
-- TOC entry 3447 (class 0 OID 17379)
-- Dependencies: 228
-- Data for Name: tbl_trip; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_trip (trip_id, created_at, province_end, province_start, status, time_start, update_at) VALUES ('a02888ca-26c8-4e63-9f90-199ad2b3f8e4', '2023-04-21 14:24:08.553', 'Thành Phố Bà Rịa', 'Thành Phố Long Xuyên', true, '2023-05-30 15:00:00', NULL);
INSERT INTO public.tbl_trip (trip_id, created_at, province_end, province_start, status, time_start, update_at) VALUES ('f443c4af-c4c9-4f05-94c0-65896b3808fb', '2023-04-21 14:24:21.68', 'Thành Phố Bà Rịa', 'Thành Phố Long Xuyên', true, '2023-05-30 16:30:00', NULL);


--
-- TOC entry 3440 (class 0 OID 17345)
-- Dependencies: 221
-- Data for Name: tbl_order; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_order (order_id, created_at, trip_id, user_id) VALUES ('TPBR2-L582MBNCY', '2023-04-23 12:26:02.928', 'a02888ca-26c8-4e63-9f90-199ad2b3f8e4', 'cb1f8b5e-f4a7-4426-8360-23df5e08733b');


--
-- TOC entry 3445 (class 0 OID 17367)
-- Dependencies: 226
-- Data for Name: tbl_ticket; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_ticket (ticket_id, address_end, address_start, drop_off_location, pick_up_location, price) VALUES ('e0259953-07c4-46a3-aef5-0fe194b470c4', 'Thành Phố Bà Rịa', 'Thành Phố Long Xuyên', 'Thành Phố Bà Rịa', 'Thành Phố Long Xuyên', 500000.00);


--
-- TOC entry 3441 (class 0 OID 17350)
-- Dependencies: 222
-- Data for Name: tbl_order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_order_detail (order_detail_id, created_at, status, update_at, chair_id, order_id, ticket_id) VALUES ('fa304f71-1da6-4bd6-b50f-7c2e0cda830b', '2023-04-23 12:26:02.976', true, NULL, '3b337ba2-f1d1-4d71-8f7b-fc91d75bb6a8', 'TPBR2-L582MBNCY', 'e0259953-07c4-46a3-aef5-0fe194b470c4');


--
-- TOC entry 3446 (class 0 OID 17372)
-- Dependencies: 227
-- Data for Name: tbl_token; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_token (token_id, expired, revoked, token, token_type, account_id) VALUES ('5c10aae9-df74-4aa3-8832-afb8fd0b93d6', false, false, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjgyNTg2NTg2fQ.TZLdBeR8K2jDKi8g6ewDwP6iSISqLUDlNGPxeX5aVYOIxHSZnLqv3lczXycbPq2PF3oOza-o7AHtFOcQTDfkEg', 'BEARER', '26912360-eb14-4832-8cae-4ec6da48296c');
INSERT INTO public.tbl_token (token_id, expired, revoked, token, token_type, account_id) VALUES ('2792256d-ed1a-4e43-8f61-8e998089839e', false, false, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0YWkiLCJleHAiOjE2ODI1ODc3NzF9.AAn8eBYaRU4TfyrDC41b_M73MR753VmLc4IQUsFM20NMS0cA4uAT5rKv2REZxjI2GZ0NlKaDhEaKc6O4fCUJhw', 'BEARER', '7662e619-b22d-40f5-963a-894cf8139b3d');
INSERT INTO public.tbl_token (token_id, expired, revoked, token, token_type, account_id) VALUES ('d4756aea-f9b2-463c-8bf9-8dbfc176b272', true, true, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4MjU4Nzg0OH0.M40hulLywrdJz57YvZx3oJlmKdSwvI4JebMaCMlsPmy4PsqnWRRurlFjOHmYjd6m0LR4G10DKL6QpO4Zr_a2Sw', 'BEARER', 'b76d5751-f04b-4b82-b17f-3a5c06a545e5');
INSERT INTO public.tbl_token (token_id, expired, revoked, token, token_type, account_id) VALUES ('ef5c1b99-4d6a-4169-a43f-52e5e169c72f', true, true, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4MjU4Nzg0OX0.QfW6viMGcBpQYK4K2gnjCbZ9xLfjWxDuwc1K4EJm61QUObWeMNrBCj0lc7FYfm10LUVK12bq3VZUgKdrdmT3FQ', 'BEARER', 'b76d5751-f04b-4b82-b17f-3a5c06a545e5');
INSERT INTO public.tbl_token (token_id, expired, revoked, token, token_type, account_id) VALUES ('24645189-880d-412f-85cc-3f2aee2ed1f7', false, false, 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4MjU4ODI2MX0.7O0QGKm9-SSOFwhmHoZl_MACKaNLrnhySBQeG1-0JNIHNX6GxcxNUHm_F2LGTD6-AuSdfuaoEtZplwTZBrIbYQ', 'BEARER', 'b76d5751-f04b-4b82-b17f-3a5c06a545e5');


--
-- TOC entry 3448 (class 0 OID 17384)
-- Dependencies: 229
-- Data for Name: tbl_trip_car; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_trip_car (car_id, trip_id) VALUES ('924e7cef-da7b-45b6-b94d-deb50812d647', 'a02888ca-26c8-4e63-9f90-199ad2b3f8e4');
INSERT INTO public.tbl_trip_car (car_id, trip_id) VALUES ('6e386c84-5713-4cb8-9ebd-484705fbdb06', 'f443c4af-c4c9-4f05-94c0-65896b3808fb');


--
-- TOC entry 3449 (class 0 OID 17389)
-- Dependencies: 230
-- Data for Name: tbl_trip_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tbl_trip_user (trip_id, user_id) VALUES ('a02888ca-26c8-4e63-9f90-199ad2b3f8e4', 'cb1f8b5e-f4a7-4426-8360-23df5e08733b');


--
-- TOC entry 3456 (class 0 OID 0)
-- Dependencies: 219
-- Name: tbl_location_location_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_location_location_id_seq', 63, true);


--
-- TOC entry 3457 (class 0 OID 0)
-- Dependencies: 223
-- Name: tbl_province_province_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_province_province_id_seq', 63, true);


-- Completed on 2023-04-24 10:22:06

--
-- PostgreSQL database dump complete
--


