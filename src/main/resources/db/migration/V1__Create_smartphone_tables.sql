CREATE TABLE smartphone_products (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    price VARCHAR(100) NOT NULL,
    image_url TEXT,
    link VARCHAR(500),
    has_5g BOOLEAN DEFAULT false,
    sale_label VARCHAR(100),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE color_options (
    id BIGSERIAL PRIMARY KEY,
    smartphone_product_id VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    color_code VARCHAR(7) NOT NULL,
    FOREIGN KEY (smartphone_product_id) REFERENCES smartphone_products(id) ON DELETE CASCADE
);

CREATE TABLE features (
    id BIGSERIAL PRIMARY KEY,
    smartphone_product_id VARCHAR(255) NOT NULL,
    feature_text VARCHAR(500) NOT NULL,
    FOREIGN KEY (smartphone_product_id) REFERENCES smartphone_products(id) ON DELETE CASCADE
);

CREATE TABLE specifications (
    id BIGSERIAL PRIMARY KEY,
    smartphone_product_id VARCHAR(255) NOT NULL,
    specification_text VARCHAR(500) NOT NULL,
    FOREIGN KEY (smartphone_product_id) REFERENCES smartphone_products(id) ON DELETE CASCADE
);

CREATE TABLE data_plans (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    subtitle VARCHAR(100),
    price VARCHAR(20) NOT NULL,
    description TEXT
);

CREATE TABLE voice_options (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    price VARCHAR(20) NOT NULL
);

CREATE TABLE oversea_calling_options (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    price VARCHAR(20) NOT NULL
);

CREATE INDEX idx_smartphone_products_brand ON smartphone_products(brand);
CREATE INDEX idx_color_options_smartphone_id ON color_options(smartphone_product_id);
CREATE INDEX idx_features_smartphone_id ON features(smartphone_product_id);
CREATE INDEX idx_specifications_smartphone_id ON specifications(smartphone_product_id);
