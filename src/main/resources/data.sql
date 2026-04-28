-- Seed admin user (password: admin123, BCrypt encoded)
INSERT INTO app_user (id, username, password, email, full_name, phone, role, created_at, active)
VALUES (1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@gromada.ua', 'System Admin', '+380670000000', 'ADMIN', CURRENT_TIMESTAMP, true)
ON CONFLICT (id) DO NOTHING;

-- Seed demo citizen
INSERT INTO app_user (id, username, password, email, full_name, phone, role, created_at, active)
VALUES (2, 'alice', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'alice@gromada.ua', 'Alice Koval', '+380671111111', 'CITIZEN', CURRENT_TIMESTAMP, true)
ON CONFLICT (id) DO NOTHING;

-- Seed demo business owner
INSERT INTO app_user (id, username, password, email, full_name, phone, role, created_at, active)
VALUES (3, 'cafe_owner', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'cafe@gromada.ua', 'Oleg Bondar', '+380672222222', 'BUSINESS_OWNER', CURRENT_TIMESTAMP, true)
ON CONFLICT (id) DO NOTHING;

-- Seed businesses
INSERT INTO business (id, name, description, category, phone, location, rating, likes, dislikes, verified, created_at, owner_id)
VALUES (1, 'Кафе Червона коза', 'Домашня кухня та обіди щодня', 'Кафе', '+380671234567', 'Почаїв, Центральна 12', 4.5, 0, 0, true, CURRENT_TIMESTAMP, 3)
ON CONFLICT (id) DO NOTHING;

INSERT INTO business (id, name, description, category, phone, location, rating, likes, dislikes, verified, created_at, owner_id)
VALUES (2, 'Перукарня "Стиль"', 'Зачіски та стрижки', 'Перукарня', '+380672345678', 'Почаїв, Миру 7', 5.0, 0, 0, true, CURRENT_TIMESTAMP, 3)
ON CONFLICT (id) DO NOTHING;

-- Seed product categories
INSERT INTO product_category (id, name, description)
VALUES (1, 'Їжа', 'Продукти харчування')
ON CONFLICT (id) DO NOTHING;

INSERT INTO product_category (id, name, description)
VALUES (2, 'Послуги', 'Різні послуги')
ON CONFLICT (id) DO NOTHING;

-- Seed service categories
INSERT INTO service_category (id, name, description)
VALUES (1, 'Репетиторство', 'Навчання та підготовка')
ON CONFLICT (id) DO NOTHING;

INSERT INTO service_category (id, name, description)
VALUES (2, 'Ремонт', 'Ремонт техніки та побутових приладів')
ON CONFLICT (id) DO NOTHING;

INSERT INTO service_category (id, name, description)
VALUES (3, 'Краса та здоров''я', 'Косметологія, масаж, фітнес')
ON CONFLICT (id) DO NOTHING;

-- Seed sample products
INSERT INTO product (id, name, description, price, stock_quantity, unit, available, created_at, business_id, category_id)
VALUES (1, 'Борщ', 'Традиційний український борщ', 85.00, 50, 'порція', true, CURRENT_TIMESTAMP, 1, 1)
ON CONFLICT (id) DO NOTHING;

INSERT INTO product (id, name, description, price, stock_quantity, unit, available, created_at, business_id, category_id)
VALUES (2, 'Вареники', 'Вареники з картоплею', 75.00, 40, 'порція', true, CURRENT_TIMESTAMP, 1, 1)
ON CONFLICT (id) DO NOTHING;
