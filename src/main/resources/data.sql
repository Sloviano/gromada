-- Seed admin user (password: admin123, BCrypt encoded)
INSERT INTO app_user (id, username, password, email, full_name, phone, role, created_at, active)
VALUES
(1, 'admin', '$2b$10$XNBY.kmwAFeeEEWGbCTBkeG8w1egvoldZ2E.oGwPWElh4OSpgjfsi', 'admin@gromada.ua', 'System Admin', '+380670000000', 'ADMIN', CURRENT_TIMESTAMP, true)
ON CONFLICT DO NOTHING;

-- Seed demo citizen
INSERT INTO app_user (id, username, password, email, full_name, phone, role, created_at, active)
VALUES
(2, 'alice', '$2b$10$XNBY.kmwAFeeEEWGbCTBkeG8w1egvoldZ2E.oGwPWElh4OSpgjfsi', 'alice@gromada.ua', 'Alice Koval', '+380671111111', 'CITIZEN', CURRENT_TIMESTAMP, true)
ON CONFLICT DO NOTHING;

-- Seed demo business owner
INSERT INTO app_user (id, username, password, email, full_name, phone, role, created_at, active)
VALUES
(3, 'cafe_owner', '$2b$10$XNBY.kmwAFeeEEWGbCTBkeG8w1egvoldZ2E.oGwPWElh4OSpgjfsi', 'cafe@gromada.ua', 'Oleg Bondar', '+380672222222', 'BUSINESS_OWNER', CURRENT_TIMESTAMP, true)
ON CONFLICT DO NOTHING;

-- Seed businesses
INSERT INTO business (id, name, description, category, phone, location, rating, likes, dislikes, verified, created_at, owner_id)
VALUES
(1, 'Кафе Червона коза', 'Домашня кухня та обіди щодня', 'Кафе', '+380671234567', 'Почаїв, Центральна 12', 4.5, 0, 0, true, CURRENT_TIMESTAMP, 3)
ON CONFLICT DO NOTHING;
INSERT INTO business (id, name, description, category, phone, location, rating, likes, dislikes, verified, created_at, owner_id)
VALUES
(2, 'Перукарня "Стиль"', 'Зачіски та стрижки', 'Перукарня', '+380672345678', 'Почаїв, Миру 7', 5.0, 0, 0, true, CURRENT_TIMESTAMP, 3)
ON CONFLICT DO NOTHING;
INSERT INTO business (id, name, description, category, phone, location, rating, likes, dislikes, verified, created_at, owner_id)
VALUES
(3, 'Аптека "Здоров''я"', 'Ліки, вітаміни та товари для здоров''я', 'Аптека', '+380675678901', 'Почаїв, Соборна 8', 4.8, 0, 0, true, CURRENT_TIMESTAMP, 3)
ON CONFLICT DO NOTHING;
INSERT INTO business (id, name, description, category, phone, location, rating, likes, dislikes, verified, created_at, owner_id)
VALUES
(4, 'Пекарня "Смачний хліб"', 'Свіжа випічка, хліб та кондитерські вироби щодня', 'Пекарня', '+380676789012', 'Почаїв, Шевченка 14', 4.6, 0, 0, false, CURRENT_TIMESTAMP, 3)
ON CONFLICT DO NOTHING;
INSERT INTO business (id, name, description, category, phone, location, rating, likes, dislikes, verified, created_at, owner_id)
VALUES
(5, 'Будівельна компанія "МайстерБуд"', 'Будівництво, ремонт та оздоблювальні роботи', 'Будівництво', '+380677890123', 'Почаїв, Заводська 2', 4.4, 0, 0, true, CURRENT_TIMESTAMP, 3)
ON CONFLICT DO NOTHING;
INSERT INTO business (id, name, description, category, phone, location, rating, likes, dislikes, verified, created_at, owner_id)
VALUES
(6, 'Магазин техніки ТехноСвіт', 'Продаж смартфонів, ноутбуків та побутової техніки', 'Електроніка', '+380673456789', 'Почаїв, Незалежності 5', 4.3, 0, 0, true, CURRENT_TIMESTAMP, 3)
ON CONFLICT DO NOTHING;
INSERT INTO business (id, name, description, category, phone, location, rating, likes, dislikes, verified, created_at, owner_id)
VALUES
(7, 'Автосервіс Авто-Плюс', 'Ремонт та обслуговування автомобілів усіх марок', 'Автосервіс', '+380674567890', 'Почаїв, Промислова 3', 4.7, 0, 0, true, CURRENT_TIMESTAMP, 3)
ON CONFLICT DO NOTHING;

-- Seed product categories
INSERT INTO product_category (id, name, description)
VALUES
(1, 'Їжа', 'Продукти харчування')
ON CONFLICT DO NOTHING;
INSERT INTO product_category (id, name, description)
VALUES
(2, 'Послуги', 'Різні послуги')
ON CONFLICT DO NOTHING;

-- Seed service categories
INSERT INTO service_category (id, name, description)
VALUES
(1, 'Репетиторство', 'Навчання та підготовка')
ON CONFLICT DO NOTHING;
INSERT INTO service_category (id, name, description)
VALUES
(2, 'Ремонт', 'Ремонт техніки та побутових приладів')
ON CONFLICT DO NOTHING;
INSERT INTO service_category (id, name, description)
VALUES
(3, 'Краса та здоров''я', 'Косметологія, масаж, фітнес')
ON CONFLICT DO NOTHING;

-- Seed sample products for Кафе Червона коза
INSERT INTO product (id, name, description, price, stock_quantity, unit, available, created_at, business_id, category_id)
VALUES
(1, 'Борщ', 'Традиційний український борщ', 85.00, 50, 'порція', true, CURRENT_TIMESTAMP, 1, 1)
ON CONFLICT DO NOTHING;
INSERT INTO product (id, name, description, price, stock_quantity, unit, available, created_at, business_id, category_id)
VALUES
(2, 'Вареники', 'Вареники з картоплею', 75.00, 40, 'порція', true, CURRENT_TIMESTAMP, 1, 1)
ON CONFLICT DO NOTHING;

-- Sync IDENTITY sequences after explicit-ID inserts
SELECT setval('app_user_id_seq', (SELECT COALESCE(MAX(id), 1) FROM app_user));
SELECT setval('business_id_seq', (SELECT COALESCE(MAX(id), 1) FROM business));
SELECT setval('product_category_id_seq', (SELECT COALESCE(MAX(id), 1) FROM product_category));
SELECT setval('service_category_id_seq', (SELECT COALESCE(MAX(id), 1) FROM service_category));
SELECT setval('product_id_seq', (SELECT COALESCE(MAX(id), 1) FROM product));
