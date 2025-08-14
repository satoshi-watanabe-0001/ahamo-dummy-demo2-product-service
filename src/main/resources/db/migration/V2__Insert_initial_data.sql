INSERT INTO smartphone_products (id, name, brand, price, image_url, link, has_5g, sale_label, description) VALUES
('1', 'iPhone 16e', 'Apple', '43,670円〜', 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400&h=400&fit=crop&crop=center', '/smartphones/iphone16e', true, 'SALE!', 'いつでもカエドキプログラム適用時 お客様負担額'),
('2', 'iPhone 16 Pro', 'Apple', '96,470円〜', 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400&h=400&fit=crop&crop=center', '/smartphones/iphone16pro', true, null, 'いつでもカエドキプログラム（プラス対象商品）適用時 お客様負担額'),
('3', 'iPhone 16 Pro Max', 'Apple', '120,780円〜', 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400&h=400&fit=crop&crop=center', '/smartphones/iphone16promax', true, null, 'いつでもカエドキプログラム（プラス対象商品）適用時 お客様負担額'),
('4', 'iPhone 14', 'Apple', '98,340円', 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400&h=400&fit=crop&crop=center', '/smartphones/iphone14', true, null, null),
('5', 'iPhone SE（第3世代）', 'Apple', '42,680円〜', 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400&h=400&fit=crop&crop=center', '/smartphones/iphonese3', true, null, 'いつでもカエドキプログラム適用時 お客様負担額'),
('6', 'Galaxy S24', 'Samsung', '124,800円〜', 'https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?w=400&h=400&fit=crop&crop=center', '/smartphones/galaxys24', true, null, null),
('7', 'Xperia 1 VI', 'Sony', '139,800円〜', 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400&h=400&fit=crop&crop=center', '/smartphones/xperia1vi', true, null, null),
('8', 'Pixel 8 Pro', 'Google', '119,800円〜', 'https://images.unsplash.com/photo-1598300042247-d088f8ab3a91?w=400&h=400&fit=crop&crop=center', '/smartphones/pixel8pro', true, null, null),
('9', 'AQUOS sense8', 'Sharp', '59,800円〜', 'https://images.unsplash.com/photo-1567721913486-6585f069b332?w=400&h=400&fit=crop&crop=center', '/smartphones/aquossense8', true, null, null);

INSERT INTO color_options (smartphone_product_id, name, color_code) VALUES
('1', 'ホワイト', '#FFFFFF'),
('1', 'ブラック', '#000000'),
('2', 'ゴールド', '#FFD700'),
('2', 'スペースグレイ', '#8E8E93'),
('2', 'シルバー', '#C0C0C0'),
('2', 'ブラック', '#000000'),
('3', 'ゴールド', '#FFD700'),
('3', 'スペースグレイ', '#8E8E93'),
('3', 'シルバー', '#C0C0C0'),
('3', 'ブラック', '#000000'),
('4', 'ブラック', '#000000'),
('5', 'ブラック', '#000000'),
('6', 'ファントムブラック', '#000000'),
('6', 'ファントムシルバー', '#C0C0C0'),
('7', 'ブラック', '#000000'),
('7', 'ホワイト', '#FFFFFF'),
('8', 'オブシディアン', '#000000'),
('8', 'ポーセリン', '#F5F5DC'),
('9', 'ライトカッパー', '#CD7F32'),
('9', 'ペールグリーン', '#98FB98');

INSERT INTO features (smartphone_product_id, feature_text) VALUES
('1', 'いつでもカエドキプログラム適用時'),
('1', 'お客様負担額'),
('2', 'いつでもカエドキプログラム（プラス対象商品）適用時'),
('2', 'お客様負担額'),
('3', 'いつでもカエドキプログラム（プラス対象商品）適用時'),
('3', 'お客様負担額'),
('4', 'A15 Bionicチップ'),
('4', 'デュアルカメラシステム'),
('5', 'いつでもカエドキプログラム適用時'),
('5', 'お客様負担額'),
('6', 'AI機能搭載'),
('6', '200MPカメラ'),
('6', '高速充電'),
('7', '4K HDRディスプレイ'),
('7', 'プロカメラ機能'),
('7', '防水・防塵'),
('8', 'Tensor G3チップ'),
('8', 'AI写真編集'),
('8', '長時間バッテリー'),
('9', '省エネIGZO'),
('9', '大容量バッテリー'),
('9', 'おサイフケータイ');

INSERT INTO specifications (smartphone_product_id, specification_text) VALUES
('1', 'A16 Bionicチップ'),
('1', '48MPカメラシステム'),
('1', 'Face ID'),
('2', 'A17 Proチップ'),
('2', 'Pro 48MPカメラシステム'),
('2', 'ProMotionディスプレイ'),
('3', 'A17 Proチップ'),
('3', 'Pro 48MPカメラシステム'),
('3', '6.7インチディスプレイ'),
('4', 'A15 Bionicチップ'),
('4', '12MPデュアルカメラ'),
('4', 'Face ID'),
('5', 'A15 Bionicチップ'),
('5', 'Touch ID'),
('5', 'ホームボタン'),
('6', 'Snapdragon 8 Gen 3'),
('6', '200MPカメラ'),
('6', '120Hz ディスプレイ'),
('7', 'Snapdragon 8 Gen 3'),
('7', '4K 120Hz ディスプレイ'),
('7', 'Zeiss レンズ'),
('8', 'Google Tensor G3'),
('8', 'AI写真編集'),
('8', '50MPカメラ'),
('9', 'Snapdragon 6 Gen 1'),
('9', 'IGZO OLED'),
('9', '5000mAh バッテリー');

INSERT INTO data_plans (id, title, subtitle, price, description) VALUES
('30gb', '30GB', '2,970円/月', '2970', 'データ通信量'),
('110gb', '110GB 大盛り', '4,950円/月', '4950', '大盛りオプション(+80GB/月)を含む');

INSERT INTO voice_options (id, title, description, price) VALUES
('none', '申し込まない', '', '0'),
('voice', '申し込む', '2,200円/月', '2200');

INSERT INTO oversea_calling_options (id, title, description, price) VALUES
('none', '申し込まない', '1分/回無料', '0'),
('unlimited', '申し込む', '1,000円/月', '1000');
