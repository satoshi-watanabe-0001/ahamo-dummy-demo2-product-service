CREATE TABLE smartphone_stock (
    smartphone_id VARCHAR(255) PRIMARY KEY,
    in_stock BOOLEAN NOT NULL DEFAULT true,
    quantity INTEGER NOT NULL DEFAULT 0,
    estimated_delivery VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE price_calculations (
    id VARCHAR(255) PRIMARY KEY,
    device_id VARCHAR(255) NOT NULL,
    data_plan_id VARCHAR(255),
    voice_option_id VARCHAR(255),
    oversea_option_id VARCHAR(255),
    total_price INTEGER NOT NULL,
    monthly_price INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO smartphone_stock (smartphone_id, in_stock, quantity, estimated_delivery) VALUES
('1', true, 15, '2-3営業日'),
('2', true, 8, '3-5営業日'),
('3', true, 12, '2-3営業日'),
('4', false, 0, '入荷待ち'),
('5', true, 20, '1-2営業日'),
('6', true, 5, '5-7営業日'),
('7', true, 3, '7-10営業日'),
('8', true, 10, '3-5営業日'),
('9', true, 25, '1-2営業日'),
('iphone-15', true, 15, '2-3営業日');
