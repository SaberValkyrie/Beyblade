-- --------------------------------------------------------
-- Máy chủ:                      127.0.0.1
-- Server version:               10.4.28-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Phiên bản:           12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table beyblade.account
CREATE TABLE IF NOT EXISTS `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `is_ban` bit(1) DEFAULT NULL,
  `coint` double DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL,
  `vip_id` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.account: ~2 rows (approximately)
DELETE FROM `account`;
INSERT INTO `account` (`account_id`, `username`, `password`, `created_at`, `is_ban`, `coint`, `role`, `vip_id`) VALUES
	(30, 'admin', '$2a$10$UtnUX8HJG2UmKPQhzUfHNO5AyNS/n1KE9IhHtjlYMm8cz0bY38fs.', '2024-05-13 08:53:14.000000', b'0', 100000, 0, NULL),
	(31, 'admin1', '$2a$10$kIgBZoeNWvm7ilIE6m3AWOYy1BiOuNA4mSu/HTn/D2HH53kEhnEkC', '2024-05-13 15:06:32.000000', b'0', 9989940871, 0, NULL);

-- Dumping structure for table beyblade.address_info
CREATE TABLE IF NOT EXISTS `address_info` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `fullname` varchar(55) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `address` text NOT NULL,
  `is_default` int(11) NOT NULL DEFAULT 0,
  `phuongxa` varchar(255) DEFAULT NULL,
  `quanhuyen` varchar(255) DEFAULT NULL,
  `thanhpho` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `address_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.address_info: ~2 rows (approximately)
DELETE FROM `address_info`;
INSERT INTO `address_info` (`address_id`, `user_id`, `fullname`, `phone`, `address`, `is_default`, `phuongxa`, `quanhuyen`, `thanhpho`) VALUES
	(22, 30, 'Họ Và Tên', '01234556789', 'Địa Chỉ Chi Tiết', 1, 'Chọn Phường Xá', 'Chọn Quận Huyện', 'Chọn Thành Phố'),
	(23, 30, 'Họ Và Tên', '01234556789', 'Địa Chỉ Chi Tiết', 0, 'Chọn Phường Xá', 'Chọn Quận Huyện', 'Chọn Thành Phố');

-- Dumping structure for table beyblade.cart
CREATE TABLE IF NOT EXISTS `cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `product` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`),
  KEY `FK_cart_products` (`product`),
  CONSTRAINT `FK_cart_products` FOREIGN KEY (`product`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=371 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.cart: ~0 rows (approximately)
DELETE FROM `cart`;

-- Dumping structure for table beyblade.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` tinyint(4) NOT NULL DEFAULT 0,
  `name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.category: ~0 rows (approximately)
DELETE FROM `category`;

-- Dumping structure for table beyblade.images
CREATE TABLE IF NOT EXISTS `images` (
  `images_id` bigint(20) NOT NULL DEFAULT 0,
  `product` int(11) DEFAULT NULL,
  `is_default` tinyint(4) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  KEY `FK_images_products` (`product`),
  CONSTRAINT `FK_images_products` FOREIGN KEY (`product`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.images: ~0 rows (approximately)
DELETE FROM `images`;

-- Dumping structure for table beyblade.list_bey
CREATE TABLE IF NOT EXISTS `list_bey` (
  `id` int(11) NOT NULL DEFAULT 0,
  `type` tinyint(4) DEFAULT NULL,
  `is_boss` bit(1) DEFAULT NULL,
  `price` int(11) DEFAULT 1000,
  `name` varchar(255) DEFAULT NULL,
  `images` varchar(255) DEFAULT NULL,
  `power` int(11) DEFAULT 0,
  `hp` bigint(20) DEFAULT NULL,
  `ti_le_ne_don` tinyint(4) DEFAULT NULL,
  `crit` tinyint(4) DEFAULT NULL,
  `season` tinyint(4) DEFAULT NULL,
  `spin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_list_bey_type_bey` (`type`),
  CONSTRAINT `FK_list_bey_type_bey` FOREIGN KEY (`type`) REFERENCES `type_bey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.list_bey: ~129 rows (approximately)
DELETE FROM `list_bey`;
INSERT INTO `list_bey` (`id`, `type`, `is_boss`, `price`, `name`, `images`, `power`, `hp`, `ti_le_ne_don`, `crit`, `season`, `spin`) VALUES
	(1, 1, b'0', 800, 'Valkyrie Wing Accel ', 'https://static.wikia.nocookie.net/beyga/images/5/5c/B01ValkyrieWingAccel.jpg', 5000, 15000, 1, 30, 1, 'R'),
	(2, 4, b'0', 800, 'Spriggan Spread Fusion ', 'https://i.ebayimg.com/images/g/mxgAAOSwAg1hznyQ/s-l1200.jpg', 4000, 20000, 2, 30, 1, 'R'),
	(3, 3, b'0', 1020, 'Ragnaruk Heavy Survive', 'https://i.ebayimg.com/images/g/IR4AAOSwG3dh8mD5/s-l1200.webp', 200, 50000, 5, 3, 1, 'R'),
	(4, 2, b'0', 1050, 'Kerbeus Central Defense', 'https://ae01.alicdn.com/kf/Hf78bf01a0caf43ac91cefc3e10e7d012O/TAKARA-TOMY-Beyblade-B04-DX-Starter-Kerbeus-Central-Defense-Burst-System.jpg', 1500, 45000, 3, 3, 1, 'R'),
	(12, 1, b'0', 800, 'Deathsyther Oval Accel', 'https://yeepvn.sgp1.digitaloceanspaces.com/2022/03/50780-c334827bf78479896d661b8b033ebb3f4da6c657.jpeg', 2000, 30000, 1, 13, 1, 'R'),
	(14, 2, b'0', 200, 'Wyvern Armed Massive', 'https://cdn11.bigcommerce.com/s-iodt3qca/images/stencil/350x350/products/409/822/s-l1600__13222.1535682854.jpg', 1000, 5000, 30, 3, 1, 'R'),
	(15, 3, b'0', 800, 'Trident  Heavy Claw', 'https://i.ebayimg.com/images/g/Ho8AAOSwdjdaFkYJ/s-l500.jpg', 1000, 35000, 8, 10, 1, 'R'),
	(17, 1, b'0', 800, 'Odin Central Blow', 'https://images-na.ssl-images-amazon.com/images/I/71J5Skn5kCL.SS400.jpg', 500, 37500, 20, 1, 1, 'R'),
	(20, 3, b'0', 710, 'Horusood Spread Edge', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSveffg-M5Ki0BbfvxNSDURfH0UW5ogiERa3fc6Wpn3DlCNoIlJbnADuJ-6j-1q2J8xBOs&usqp=CAU', 100, 35000, 1, 3, 1, 'R'),
	(21, 1, b'0', 400, 'Minoboros Oval Quake', 'https://beyblade-store.com/cdn/shop/products/Beyblade-Minoboros-Oval-Quake-Beyblade-Store_1024x1024.jpg', 3000, 5000, 1, 50, 1, 'R'),
	(22, 2, b'0', 1300, 'Unicorn Ring Defense', 'https://static.wikia.nocookie.net/beyblade/images/a/ab/UrmoPjr.jpg', 3000, 50000, 1, 10, 1, 'R'),
	(23, 1, b'0', 1100, 'Xcalibur Force Xtreme', 'https://product.hstatic.net/200000504579/product/beyblade_b-23_con_quay_xcalibur_force_xtreme.1_65a854f776bc495594c9e52822a8c505_master.jpg', 7000, 20000, 1, 90, 1, 'R'),
	(24, 2, b'0', 1300, 'Evil-eye Wing Needle', 'https://cdn11.bigcommerce.com/s-iodt3qca/images/stencil/1280x1280/products/2002/8681/latest__72080.1686718640.png', 3000, 50000, 20, 3, 1, 'R'),
	(28, 4, b'0', 1300, 'Neptune Armed Zephyr', 'https://i.ebayimg.com/images/g/l4UAAOSwnNZl0-W6/s-l1600.jpg', 5000, 40000, 10, 3, 1, 'R'),
	(31, 3, b'0', 1100, 'Yggdrasil Ring Gyro', 'https://i.ebayimg.com/images/g/ERUAAOSwxXNmH~Ba/s-l1600.jpg', 1000, 50000, 1, 10, 1, 'R'),
	(34, 1, b'0', 1500, 'Victory Valkyrie Boost Variable', 'https://static.wikia.nocookie.net/beyblade/images/1/10/VV.B.V.png', 10000, 25000, 5, 50, 1, 'R'),
	(35, 4, b'0', 1500, 'Storm Spriggan Knuckle Unite', 'http://localhost:8080/files/storm.png', 7500, 37500, 10, 30, 1, 'R'),
	(36, 3, b'0', 1700, 'Rising Ragnaruk Gravity Revolve', 'https://i.ebayimg.com/images/g/E-EAAOSwwydkJvU-/s-l400.jpg', 2000, 75000, 20, 3, 1, 'R'),
	(37, 2, b'0', 1300, 'Kaiser Keberus Limited Press', 'https://static.wikia.nocookie.net/beyblade/images/1/1e/KaiserKerbeusLP.jpg', 3000, 50000, 5, 10, 1, 'R'),
	(41, 2, b'0', 1300, 'Wild Wyvern Vertical Orbit', 'https://ae01.alicdn.com/kf/H29feb077045f40b1a75f2bb8fd9d99e9H/B-X-TOUPIE-BURST-BEYBLADE-SPINNING-TOP-B191-B192-B-41-Starter-Wild-Wyvern-V-O.jpg', 3000, 50000, 30, 1, 1, 'R'),
	(42, 1, b'0', 1500, 'Dark Deathscyther Force Jaggy', 'https://ae01.alicdn.com/kf/Hf37e16ddc68a4250839160e8cf237c9cp.jpg', 10000, 25000, 7, 20, 1, 'R'),
	(44, 3, b'0', 1200, 'Holy Horusood Upper Claw', 'https://http2.mlstatic.com/D_NQ_NP_931091-MLB31225038077_062019-O.webp', 2000, 50000, 1, 3, 1, 'R'),
	(46, 1, b'0', 1500, 'Odin Triple Xtreme', 'https://salt.tikicdn.com/cache/w300/ts/product/02/ef/91/4fd062fc7291ab3ebeac7480d35034c7.jpg', 10000, 25000, 1, 25, 1, 'R'),
	(48, 1, b'0', 1700, 'Xeno Xcalibur Magnum Impact', 'https://cf.shopee.vn/file/c35dbf715fe5a5f883d6c45d6513c16e', 12000, 25000, 1, 80, 1, 'R'),
	(49, 3, b'0', 1200, 'Yaeger Yggdrasil Gravity Yielding', 'https://static.wikia.nocookie.net/beyblade/images/b/b5/Y2_gy.jpg', 2000, 50000, 20, 3, 1, 'R'),
	(56, 2, b'0', 1200, 'Unlock Unicorn Down Needle', 'https://i.ebayimg.com/images/g/AMoAAOSwkidkYRWS/s-l1200.webp', 2000, 50000, 1, 99, 1, 'R'),
	(57, 4, b'0', 1800, 'Nova Neptune Vertical Trans', 'https://salt.tikicdn.com/cache/w1200/ts/product/5e/a8/40/12d22bfaf11a12c8650d802f7b50b42a.jpg', 9000, 45000, 9, 9, 1, 'R'),
	(59, 3, b'0', 2500, 'Zillion Zeus Infinity Weight', 'https://i.ebayimg.com/images/g/vTsAAOSwLdtkIQbi/s-l400.jpg', 5000, 100000, 1, 20, 1, 'R'),
	(61, 2, b'0', 1300, 'Quad Quetzalcoatl Jerk Press', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWaNaQYyAxgB1rQ9pZd0azR4IJLDag2Z5xYbKJTo1cQw&s', 3000, 50000, 30, 3, 1, 'R'),
	(62, 1, b'0', 1500, 'Fang Fenrir Boost Jaggy', 'https://i.ebayimg.com/images/g/V9YAAOSw7k1iYvkk/s-l1600.jpg', 10000, 25000, 1, 3, 1, 'R'),
	(63, 1, b'0', 1900, 'Beast Behemoth Heavy Hold', 'https://static.wikia.nocookie.net/beyblade/images/e/e4/Beast_Behemoth_%28B-80_08_Ver%29.png', 10000, 45000, 1, 3, 1, 'R'),
	(64, 4, b'0', 2000, 'Inferno Ifrit Magnum Liner', 'https://static.wikia.nocookie.net/beyblade/images/0/00/I2_ml.jpg', 10000, 50000, 1, 1, 1, 'R'),
	(66, 1, b'1', 2800, 'Lost Longginus Nine Spriral', 'https://salt.tikicdn.com/cache/w1200/ts/product/92/59/c0/758f9728ceed7553ec017b9cdc27d6b3.jpg', 15000, 65000, 20, 99, 1, 'L'),
	(67, 4, b'0', 1600, 'Gigant Gaia .8U.Q', 'https://static.wikia.nocookie.net/beyblade/images/3/31/G2_qf.jpg', 8000, 40000, 1, 40, 1, 'R'),
	(69, 3, b'0', 1200, 'Jail Jormungand Infinity Cycle', 'https://i.ebayimg.com/00/s/ODAwWDgyMg==/z/PnsAAOSwoWdgnZ5x/$_12.JPG', 2000, 50000, 1, 3, 1, 'R'),
	(71, 3, b'0', 2200, 'Acid Anubis Yell Orbit', 'https://www.premiumtoy.my/images/sell_products/big/image_277.jpg', 2000, 100000, 1, 3, 1, 'R'),
	(73, 1, b'0', 2800, 'God Valkyrie 6vortex Reboot', 'https://http2.mlstatic.com/D_NQ_NP_756942-CBT48177332458_112021-O.webp', 15000, 65000, 1, 6, 2, 'R'),
	(74, 2, b'0', 2600, 'Kreis Satan 2glaive Loop', 'https://i.ebayimg.com/images/g/a74AAOSw32lkIkOb/s-l400.jpg', 6000, 100000, 1, 6, 2, 'R'),
	(75, 3, b'0', 2400, 'Blaze Ragnaruk 4Cross Flugel', 'https://i.ebayimg.com/images/g/dlcAAOSwriBhsgTM/s-l1200.webp', 4000, 100000, 40, 6, 2, 'R'),
	(79, 3, b'0', 6500, 'Drain Fafnir 8 Nothing', 'http://localhost:8080/files/fafnir.jpeg', 15000, 250000, 30, 30, 2, 'L'),
	(80, 2, b'0', 2600, 'Tornado Wyvern 4Glaive Atomic ', 'https://i.pinimg.com/736x/6b/bc/65/6bbc651c76e3d46b671c73b467918e24.jpg', 6000, 100000, 1, 6, 2, 'R'),
	(82, 4, b'0', 3000, 'Alter Chronos 6 Meteor Trans', 'https://salt.tikicdn.com/cache/w300/ts/product/3f/1f/c1/5154de6ded5fc338bc91f1212b354b78.jpg', 15000, 75000, 1, 6, 2, 'R'),
	(85, 1, b'0', 2500, 'Killer Deathscyther 2vortex Hunter', 'https://i.ebayimg.com/images/g/HjMAAOSw2o1kJNWS/s-l1200.webp', 15000, 50000, 1, 20, 2, 'R'),
	(86, 4, b'0', 4000, 'Legend Spriggan 7 Merge', 'https://i.ebayimg.com/images/g/UvgAAOSwVSRkIk2c/s-l1200.webp', 20000, 100000, 1, 44, 2, 'LR'),
	(87, 3, b'0', 2100, 'Maximum Garuda 8Flow Flugel', 'https://static.wikia.nocookie.net/beyblade/images/b/b2/MG_8f_fl.jpg', 1000, 100000, 50, 6, 2, 'R'),
	(89, 2, b'0', 2600, 'Blast Jinnius 5Glaive Guard', 'https://i.ebayimg.com/images/g/G~IAAOSwOvdkIQP0/s-l1200.webp', 6000, 100000, 1, 6, 2, 'R'),
	(90, 3, b'0', 2900, 'Galaxy Zeus', 'https://i.ebayimg.com/images/g/6lAAAOSwfpBaZK2a/s-l1200.jpg', 4000, 125000, 1, 9, 2, 'R'),
	(92, 1, b'0', 4000, 'Sieg Xcalibur 1 Iron', 'https://static.wikia.nocookie.net/beyblade/images/3/3d/SX_1_ir.jpg', 20000, 100000, 1, 50, 2, 'R'),
	(95, 4, b'0', 1010, 'Shelter Regulus 5Star Tower ', 'https://down-my.img.susercontent.com/file/d5a05e1f5c89e64313aec60419537b17', 10000, 500, 50, 1, 2, 'R'),
	(97, 1, b'0', 8000, 'Nightmare Longginus Destroy', 'https://m.media-amazon.com/images/I/71voo-QDwZL._AC_SX679_.jpg', 50000, 150000, 1, 20, 2, 'L'),
	(98, 2, b'0', 3600, 'Arc Bahamut 2Bump Atomic', 'https://salt.tikicdn.com/cache/w1200/ts/product/56/57/1f/d7c1f1104eb608bc2b79ce4440940606.jpg', 6000, 150000, 20, 1, 2, 'L'),
	(99, 3, b'0', 2000, 'Deep Chaos 4Flow Bearing', 'https://static.wikia.nocookie.net/beyblade/images/1/16/DC_4f_br.jpg', 1, 100000, 30, 1, 2, 'R'),
	(100, 4, b'1', 7000, 'Spriggan Requiem 0 Zeta', 'https://i.ebayimg.com/images/g/C4kAAOSwv~1aIXGD/s-l1200.jpg', 30000, 200000, 20, 44, 2, 'LR'),
	(101, 1, b'0', 2300, 'Beat Kukulcan.7U.Hn', 'https://i.ebayimg.com/images/g/TvEAAOSwV0VcASer/s-l1600.jpg', 15000, 40000, 33, 6, 2, 'R'),
	(102, 1, b'0', 2500, 'Twin Nemesis 3Hit Jaggy', 'https://m.media-amazon.com/images/I/717gay-QdaL._AC_UF350,350_QL80_.jpg', 15000, 50000, 1, 80, 2, 'R'),
	(103, 3, b'0', 2400, 'Screw Trident 8Bump Wedge', 'https://cdn11.bigcommerce.com/s-iodt3qca/images/stencil/1280x1280/products/532/1047/s-l1600__92847.1545775254.jpg', 4000, 100000, 20, 6, 2, 'R'),
	(104, 1, b'0', 8000, 'Winning Valkyrie 12 Volcanic', 'https://static.wikia.nocookie.net/beyblade/images/6/69/WV_.12.Vl.jpg', 50000, 150000, 10, 33, 3, 'R'),
	(105, 4, b'0', 6000, 'Z Achilles 11 Xtend', 'https://static.wikia.nocookie.net/beyblade/images/2/28/ZA_.11.Xt.jpg', 30000, 150000, 1, 90, 3, 'R'),
	(106, 2, b'0', 4400, 'Emperor Forneus 0 Yard', 'https://static.wikia.nocookie.net/beyblade/images/9/98/EF_.0.Yr.jpg', 9000, 175000, 1, 9, 3, 'R'),
	(110, 1, b'0', 4000, 'Bloody Longinus 13 Jolt', 'https://down-br.img.susercontent.com/file/e09673b7f5da7790b4ab5125990172cb', 30000, 50000, 1, 98, 3, 'L'),
	(111, 3, b'0', 5600, 'Crash Ragnaruk 11Reach Wedge', 'https://static.wikia.nocookie.net/beyblade/images/f/f1/CR_.11R.Wd.png', 6000, 250000, 25, 9, 3, 'R'),
	(113, 4, b'0', 5000, 'Hell Salamander 12 Operate', 'https://static.wikia.nocookie.net/beyblade/images/f/f8/HS_.12.Op_%2810_Blade_Mode%29.jpg', 25000, 125000, 25, 25, 3, 'L'),
	(115, 3, b'0', 3100, 'Archer Hercules.13.Et', 'https://i5.walmartimages.com/asr/93b196e2-7438-4704-a4bd-9c97b62f386a.761eb7ed01a9d2a498074504d46d6b75.jpeg', 6000, 125000, 25, 9, 3, 'R'),
	(117, 2, b'0', 4000, 'Revive Phoenix 10 Friction', 'https://static.wikia.nocookie.net/beyblade/images/4/40/Black_Revive_Phoenix_10_Friction.jpg', 15000, 125000, 30, 15, 3, 'R'),
	(118, 1, b'0', 2200, 'Vise Leopard 12Lift Destroy', 'https://static.wikia.nocookie.net/beyblade/images/2/24/VL_.12L.Ds.png', 12000, 50000, 1, 9, 3, 'R'),
	(120, 1, b'0', 3500, 'Buster Xcalibur 1\' Sword', 'http://localhost:8080/files/xca.png', 25000, 50000, 1, 90, 3, 'R'),
	(121, 2, b'0', 2900, 'Hazard Kerbeus 7 Atomic ', 'https://www.premiumtoy.my/images/sell_products/interactive/459/ex48ykvlwxfh7.jpg', 9000, 100000, 20, 9, 3, 'R'),
	(122, 3, b'0', 7600, 'Geist Fafnir 8\' Absorb', 'https://img.lazcdn.com/g/p/e22a1344b3cfa2a3cfd9f2c9c0ba8118.jpg_720x720q80.jpg', 26000, 250000, 25, 9, 3, 'L'),
	(125, 4, b'0', 9000, 'Dead Hades 11Turn Zephyr\' ', 'https://static.wikia.nocookie.net/beyblade/images/0/08/DH_front.png', 45000, 225000, 10, 20, 3, 'R'),
	(126, 4, b'0', 8500, 'Fusion Aether', 'https://i.redd.it/514ag4oi213b1.png', 40000, 225000, 10, 5, 3, 'R'),
	(127, 1, b'0', 8500, 'Cho-Z Valkyrie Zenith Evolution', 'https://http2.mlstatic.com/D_NQ_NP_732183-MLB42007054422_052020-O.webp', 50000, 175000, 1, 50, 3, 'R'),
	(128, 4, b'0', 10000, 'Cho-Z Spriggan 0Wall Zeta\'\r\n', 'https://static.wikia.nocookie.net/beyblade/images/1/18/Cho-Z_Spriggan_0W_Zt%27_Pic_2.jpg', 30000, 350000, 20, 9, 3, 'LR'),
	(129, 4, b'0', 9000, 'Cho-Z Achilles 00 Dimension\r\n', 'https://i.pinimg.com/736x/f5/91/95/f5919593e8cb9ff5fdfc26b624cc2648.jpg', 45000, 225000, 10, 20, 3, 'R'),
	(130, 3, b'0', 8010, 'Air Knight 12Expand Eternal', 'http://localhost:8080/files/a.jpeg', 100, 400000, 10, 1, 3, 'R'),
	(131, 2, b'1', 11000, 'Dead Phoenix 10 Friction', 'https://cdn.lojavirtuolpro.com/beyblader/produto//p/h/phoenix.jpg', 30000, 400000, 6, 20, 3, 'R'),
	(133, 1, b'0', 7000, 'Ace Dragon Sting Charge Zan', 'https://static.wikia.nocookie.net/beyblade/images/3/36/AD.St.Ch_1.jpg', 40000, 150000, 1, 19, 4, 'R'),
	(134, 1, b'0', 9000, 'Slash Valkyrie Blitz Power Retsu', 'https://static.wikia.nocookie.net/beyblade/images/4/4c/SV.Bl.Pw_1.jpg', 60000, 150000, 1, 33, 4, 'R'),
	(135, 2, b'0', 7200, 'Bushin Ashura Hurricane Keep Ten', 'https://static.wikia.nocookie.net/beyblade/images/c/c1/BA.Hr.Kp_1.jpg', 12000, 300000, 1, 6, 4, 'R'),
	(139, 3, b'0', 6800, 'Wizard Fafnir Ratchet Rise Sen', 'https://static.wikia.nocookie.net/beyblade/images/4/44/WF.Rt.Rs_Sen_Pic_1.jpg', 8000, 300000, 1, 33, 4, 'L'),
	(142, 4, b'0', 7000, 'Judgement Joker .00T.Tr Zan ', 'https://m.media-amazon.com/images/I/71Ib9GqO4WL._AC_UF1000,1000_QL80_.jpg', 40000, 150000, 1, 50, 4, 'R'),
	(144, 1, b'0', 8000, 'Zwei Longinus Drake Spiral\' Metsu', 'http://localhost:8080/files/z.jpeg', 50000, 150000, 1, 20, 4, 'L'),
	(145, 4, b'0', 9000, 'Venom Diabolos Vanguard Bullet', 'https://static.wikia.nocookie.net/beyblade/images/e/e7/VD.Vn.Bl_%28Silver_Dragon_Ver.%29.png', 45000, 225000, 20, 20, 4, 'LR'),
	(148, 3, b'0', 5300, 'Heaven Pegasus 10Proof Low Sen', 'https://static.wikia.nocookie.net/beyblade/images/0/0f/HP.10P.Lw_Sen_Pic_1.jpg', 8000, 225000, 33, 12, 4, 'R'),
	(149, 2, b'0', 1210, 'Dread Bahamut 7Wall Orbit Metal Gen', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiGF6TuURyg243S9AILznVaKZO9j3IH3Gjn01SdsXzOi_8Xr1FpP_Kvmta3XqNAq0FqVw&usqp=CAU', 12000, 500, 50, 12, 4, 'L'),
	(150, 4, b'0', 9000, 'Union Achilles Convert Xtend+ Retsu', 'https://static.wikia.nocookie.net/beyblade/images/c/cf/UA.Cn.Xt%2B_Retsu_1.jpg', 45000, 225000, 22, 22, 4, 'R'),
	(153, 1, b'0', 14900, 'Prime Apocalypse 0Dagger Ultimate Reboot\'', 'https://ae01.alicdn.com/kf/H96bc5d0411cb4d44ae1f58a978f54214a.jpg_Q90.jpg_.webp', 75000, 370000, 1, 50, 4, 'R'),
	(154, 1, b'0', 10000, 'Imperial Dragon Ignition\'', 'http://localhost:8080/files/dragon.png', 60000, 200000, 22, 12, 4, 'R'),
	(155, 4, b'0', 10000, 'Master Diabolos Generate', 'https://beybladesandmore.com/cdn/shop/products/17807_1200x1200.jpg?v=1655085430', 50000, 250000, 33, 33, 4, 'LR'),
	(157, 2, b'0', 13300, 'Bigbang Genesis 0 Yard Metal', 'http://localhost:8080/files/bg.png', 33000, 500000, 22, 50, 4, 'R'),
	(159, 1, b'0', 13000, 'Super Hyperion Xceed 1A', 'https://bglhobbies.com.au/cdn/shop/products/beyblade-burst-superking-b-159-booster-super-hyperionxc-1a-beyblade-takara-tomy-139236_grande.jpg?v=1658441617', 80000, 250000, 1, 33, 5, 'R'),
	(160, 4, b'0', 10000, 'King Helios Zone 1B', 'https://www.mykingdom.com.vn/cdn/shop/products/b-160-157199-3_f36bb42b-36ae-4f76-aff4-5a7ac35ba7c5.jpg?v=1707002694&width=1445', 50000, 250000, 1, 15, 5, 'L'),
	(161, 3, b'0', 11000, 'Glide Ragnaruk Wheel Revolve 1S', 'https://www.mykingdom.com.vn/cdn/shop/products/b-161-157205-3_80b6c96a-cddd-4469-8987-b7915a9ece15.jpg?v=1707002707&width=1445', 10000, 500000, 20, 15, 5, 'R'),
	(163, 1, b'0', 14000, 'Brave Valkyrie .Ev\' 2A', 'https://bizweb.dktcdn.net/100/405/289/products/48f0384f2fcec1362864e00aabeef735.jpg?v=1660831144697', 123456, 300000, 20, 50, 5, 'R'),
	(164, 2, b'0', 13000, 'Curse Satan Hurricane Universe 1D', 'http://localhost:8080/files/satan.png', 50000, 400000, 32, 15, 5, 'R'),
	(167, 3, b'0', 12444, 'Mirage Fafnir Nothing 2S ', 'https://m.media-amazon.com/images/I/71KjSSncnWL._AC_UF1000,1000_QL80_.jpg', 44444, 400000, 23, 15, 5, 'L'),
	(168, 1, b'0', 13000, 'Rage Longinus Destroy\' 3A', 'https://yeepvn.sgp1.digitaloceanspaces.com/2022/04/48806-dd903c10728dfe516f9c28b8ff596935.jpeg', 100000, 550000, 1, 60, 5, 'L'),
	(169, 2, b'0', 23000, 'Variant Lucifer Mobius 2D', 'https://images-na.ssl-images-amazon.com/images/I/71usbdoq2lL.jpg', 30000, 1000000, 25, 15, 5, 'R'),
	(170, 4, b'0', 15000, 'Death Diabolos 4Turn Merge\' 1D', 'https://www.beyburst.com/cdn/shop/products/Beyburst-B170-01-Death-Diabolos_2.jpg?v=1644494225', 75000, 375000, 12, 15, 5, 'LR'),
	(171, 1, b'0', 17000, 'Tempest Dragon Charge Metal 1A', 'https://img.ws.mms.shopee.com.my/2ecd8d105522fb41e35d4ebef7346933', 75000, 475000, 23, 15, 5, 'R'),
	(172, 4, b'0', 15000, 'World Spriggan Unite\' 2B', 'https://img.lazcdn.com/g/p/67dd71a4ca5de5a8c1d6cff5d255a028.jpg_960x960q80.jpg_.webp', 75000, 375000, 33, 15, 5, 'LR'),
	(173, 4, b'0', 14000, 'Infinite Achilles Dimension\' 1B', 'https://cdn11.bigcommerce.com/s-iodt3qca/images/stencil/1280x1280/products/1104/3282/b-173-01__68458.1604535356.jpg?c=2', 85000, 275000, 1, 30, 5, 'R'),
	(174, 1, b'0', 11000, 'Sức Mạnh Song Sinh ', 'http://localhost:8080/files/2h.png', 500000, 100000, 70, 70, 5, 'LR'),
	(175, 2, b'1', 6500, 'Lucifer The End Kou Drift', 'https://down-vn.img.susercontent.com/file/ea98861ce3acb553246f118255b84b06', 15000, 250000, 50, 15, 5, 'R'),
	(176, 1, b'0', 9000, 'Hollow Deathscyther 12Axe High Accel\' 4A', 'https://ae01.alicdn.com/kf/H26140a8c6dcb4eebacb6cde150803417a/B-X-TOUPIE-BURST-BEYBLADE-Spinning-Top-Limit-Break-DX-set-W-Box-B174-B-174.jpg', 80000, 50000, 50, 70, 5, 'R'),
	(177, 2, b'0', 9000, 'Jet Wyvern Around Just 1D', 'https://ae01.alicdn.com/kf/Hf7efbea5f33744e994d37594ebfd0635l/Genuine-TAKARA-TOMY-BEYBLADE-Burst-Gyro-Super-King-B177-Jet-Flying-Dragon-Battle-Toys.jpg', 15000, 375000, 50, 15, 5, 'R'),
	(179, 4, b'0', 10000, 'Death Solomon MF 2B', 'https://i.ebayimg.com/images/g/twgAAOSw9KhjVP7v/s-l400.jpg', 50000, 250000, 25, 25, 5, 'R'),
	(180, 1, b'0', 13000, 'Dynamite Belial Nexus Venture-2', 'https://i.ebayimg.com/images/g/3-AAAOSwF7BhIwNN/s-l1200.webp', 50000, 400000, 25, 33, 6, 'R'),
	(181, 3, b'0', 16200, 'Cyclone Ragnaruk Giga Never-6', 'https://down-vn.img.susercontent.com/file/dae3bbfa75ec4e2b447d47cec60e4c37', 12000, 750000, 20, 18, 6, 'R'),
	(185, 3, b'0', 15000, ' Vanish Fafnir.Tp.Kc-3', 'https://down-vn.img.susercontent.com/file/4ff89aed2884157260a1884a9b3433a0', 50000, 500000, 30, 20, 6, 'L'),
	(186, 2, b'0', 17000, 'Roar Bahamut Giga Moment-10 ', 'https://ae01.alicdn.com/kf/Hbc0b1bc2b16344a68e94ab86f49a1430O/GENUINE-Takara-Tomy-Burst-Dynamite-Random-Booster-Vol-26-Beyblade-B-186-B-194-random-1.jpg', 50000, 850000, 20, 1, 6, 'L'),
	(187, 1, b'0', 22000, 'Savior Valkyrie Shot-7', 'https://i.ebayimg.com/images/g/gT0AAOSwcPNg7774/s-l1200.jpg', 200000, 600000, 50, 50, 6, 'R'),
	(188, 4, b'0', 19800, 'Astral Spriggan Over Quattro-0', 'https://images-na.ssl-images-amazon.com/images/I/71H8Fq2w5XS.jpg', 299000, 495000, 1, 22, 6, 'LR'),
	(189, 1, b'1', 22000, 'Guilty Longinus Karma Metal Destroy-2', 'https://images-cdn.ubuy.co.in/635b867191f5134064149b62-beyblade-burst-b-189-booster-guilty.jpg', 250000, 550000, 30, 70, 6, 'L'),
	(190, 2, b'0', 18000, 'Promience Phoneix.Tp.MU -10', 'https://laz-img-sg.alicdn.com/p/e7499132f71f83fe37d24f2b0ee05ca8.jpg', 50000, 650000, 33, 18, 6, 'R'),
	(191, 1, b'0', 18000, 'Dangerous Belial Almight -2', 'https://http2.mlstatic.com/D_NQ_NP_858347-MLU73331820766_122023-O.webp', 200000, 250000, 1, 33, 6, 'R'),
	(192, 4, b'1', 30000, 'Greatest Raphael.Ov.HXt+\' ', 'https://m.media-amazon.com/images/I/71tBIgCnzZL._AC_SL1100_.jpg', 50000, 1000000, 50, 50, 6, 'R'),
	(193, 1, b'0', 29000, 'Ultimate Valkyrie.Lg.V’-9', 'https://beyblade-shop.com/cdn/shop/products/Ultimate-Valkyrie-Legacy-Variable-9-Beyblade-Shop_1200x1200.jpg?v=1636925530', 200000, 450000, 25, 33, 6, 'R'),
	(194, 1, b'1', 1000, 'Dynamite Belial full gear Holy King Ver', 'https://i.ebayimg.com/images/g/3-AAAOSwF7BhIwNN/s-l1200.webp', 20000, 1000000, 1, 1, 6, 'R'),
	(195, 2, b'0', 31111, 'Prominence Valkyrie.Ov.At’-0', 'https://i5.walmartimages.com/asr/c1aa5022-0eeb-4d06-8fee-1e90b0b2ac66.a0dd10e10e0063f0e76e49ac1fa16131.jpeg?odnHeight=768&odnWidth=768&odnBg=FFFFFF', 10000, 1200000, 1, 18, 6, 'R'),
	(197, 1, b'0', 3800, 'Divine Belial Nexus Adventure-3', 'http://localhost:8080/files/bel.png', 28000, 50000, 1, 21, 7, 'R'),
	(198, 2, b'0', 30000, 'Chain Kerbeus Fortress Yard\'-6', 'https://down-vn.img.susercontent.com/file/48be0fdd51d7d7c180f7ffe9ef2a2d78', 100000, 1000000, 1, 21, 7, 'R'),
	(199, 1, b'0', 30000, 'Gatling Dragon Karma Charge Metal\'-10', 'http://localhost:8080/files/gld.png', 100000, 1000000, 22, 21, 7, 'R'),
	(200, 1, b'0', 21000, 'Xiphoid Xcalibur Xanthus Sword\'-1 ', 'http://localhost:8080/files/xiphoid.png', 200000, 50000, 33, 50, 7, 'R'),
	(201, 4, b'0', 28000, 'Zest Achilles Illegal Quattro\'-4', 'https://i.ebayimg.com/images/g/wpUAAOSwxXxi9H8P/s-l1200.webp', 140000, 700000, 1, 21, 7, 'R'),
	(202, 3, b'0', 31400, 'Wind Knight Moon Bounce-6', 'https://i.ebayimg.com/images/g/DD4AAOSwHVNjIwi2/s-l1200.webp', 14000, 1500000, 22, 21, 7, 'R'),
	(203, 1, b'0', 24000, 'Super Hyperion MR', 'https://wafuu.com/cdn/shop/products/takara-tomy-beyblade-burst-b-203-ultimate-combine-dx-set-111634_1200x1200.jpg?v=1695256821', 140000, 500000, 1, 21, 7, 'R'),
	(204, 4, b'0', 18000, 'King Helios MR', 'https://wafuu.com/cdn/shop/products/takara-tomy-beyblade-burst-b-203-ultimate-combine-dx-set-606832_540x.jpg?v=1695256822', 90000, 450000, 1, 21, 7, 'L'),
	(205, 4, b'0', 24000, 'Burst Spriggan Spread\' Fusion\'-8', 'https://i.ebayimg.com/images/g/W8gAAOSwf-pjdFSh/s-l1200.jpg', 120000, 600000, 1, 21, 7, 'LR'),
	(206, 2, b'1', 32100, 'Badicade Lucifer.Il.BMb-10', 'https://m.media-amazon.com/images/I/71WgKP-cPSL._AC_UF1000,1000_QL80_DpWeblab_.jpg', 21000, 1500000, 22, 21, 7, 'R');

-- Dumping structure for table beyblade.list_boss
CREATE TABLE IF NOT EXISTS `list_boss` (
  `id` tinyint(4) NOT NULL DEFAULT 0,
  `buff` tinyint(4) DEFAULT NULL,
  `time` tinyint(4) DEFAULT NULL,
  `bey` int(11) DEFAULT NULL,
  KEY `FK_list_boss_list_bey` (`bey`),
  CONSTRAINT `FK_list_boss_list_bey` FOREIGN KEY (`bey`) REFERENCES `list_bey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.list_boss: ~0 rows (approximately)
DELETE FROM `list_boss`;

-- Dumping structure for table beyblade.message
CREATE TABLE IF NOT EXISTS `message` (
  `message_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `me` int(11) DEFAULT NULL,
  `other` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `time_chat` datetime(6) DEFAULT NULL,
  `violate` tinyint(1) DEFAULT 0,
  `status` bit(1) DEFAULT NULL,
  `user_chat` int(11) DEFAULT NULL,
  PRIMARY KEY (`message_id`) USING BTREE,
  KEY `FK_message_users` (`me`) USING BTREE,
  KEY `FK_message_users_2` (`other`) USING BTREE,
  KEY `FKmx36oy0dnj6by9r7i49o2ln2w` (`user_chat`),
  CONSTRAINT `FK24x7fsoxkncqylm3cg552wrddi4` FOREIGN KEY (`other`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_message_users` FOREIGN KEY (`me`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKmx36oy0dnj6by9r7i49o2ln2w` FOREIGN KEY (`user_chat`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=296 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.message: ~0 rows (approximately)
DELETE FROM `message`;

-- Dumping structure for table beyblade.notify
CREATE TABLE IF NOT EXISTS `notify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `note` text NOT NULL,
  `read_status` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKbxjvlvchwp0xwj7m97bsmajcb` (`user_id`),
  CONSTRAINT `FKbxjvlvchwp0xwj7m97bsmajcb` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.notify: ~0 rows (approximately)
DELETE FROM `notify`;

-- Dumping structure for table beyblade.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` datetime DEFAULT NULL,
  `order_key` varchar(255) DEFAULT NULL,
  `order_time_update` datetime DEFAULT NULL,
  `order_details_id` bigint(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK18up738eyx1ppn73qgvl5pdmv` (`order_details_id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK18up738eyx1ppn73qgvl5pdmv` FOREIGN KEY (`order_details_id`) REFERENCES `order_details` (`id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=514 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.orders: ~0 rows (approximately)
DELETE FROM `orders`;

-- Dumping structure for table beyblade.order_details
CREATE TABLE IF NOT EXISTS `order_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tracking_code` varchar(255) DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=470 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.order_details: ~0 rows (approximately)
DELETE FROM `order_details`;

-- Dumping structure for table beyblade.order_items
CREATE TABLE IF NOT EXISTS `order_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `quantity_order` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_items_orders` (`order_id`) USING BTREE,
  KEY `FK_order_items_products` (`product_id`),
  CONSTRAINT `FK_order_items_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FK_order_items_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=533 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.order_items: ~0 rows (approximately)
DELETE FROM `order_items`;

-- Dumping structure for table beyblade.order_status
CREATE TABLE IF NOT EXISTS `order_status` (
  `id` tinyint(4) NOT NULL DEFAULT 0,
  `name_status` varchar(50) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.order_status: ~0 rows (approximately)
DELETE FROM `order_status`;

-- Dumping structure for table beyblade.products
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `images` varchar(255) DEFAULT NULL,
  `images1` varchar(255) DEFAULT NULL,
  `images2` varchar(255) DEFAULT NULL,
  `images3` varchar(255) DEFAULT NULL,
  `sold` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `category_id` tinyint(4) DEFAULT NULL,
  `user_prize` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FK1cf90etcu98x1e6n9aks3tel3` (`category_id`),
  KEY `FKivnwhn631vxr9sfk13s7vfe5h` (`user_prize`),
  CONSTRAINT `FK1cf90etcu98x1e6n9aks3tel3` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKivnwhn631vxr9sfk13s7vfe5h` FOREIGN KEY (`user_prize`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=324 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.products: ~0 rows (approximately)
DELETE FROM `products`;

-- Dumping structure for table beyblade.register_seller
CREATE TABLE IF NOT EXISTS `register_seller` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `registration_date` varchar(255) DEFAULT NULL,
  `update_time` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_register_seller_users` (`user_id`),
  CONSTRAINT `FK_register_seller_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.register_seller: ~0 rows (approximately)
DELETE FROM `register_seller`;

-- Dumping structure for table beyblade.reviews
CREATE TABLE IF NOT EXISTS `reviews` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `images_review` tinytext DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `rating` tinyint(4) NOT NULL DEFAULT 0,
  `comment` tinytext DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_reviews_products` (`product_id`),
  KEY `FK_reviews_users` (`user_id`),
  KEY `FKqwgq1lxgahsxdspnwqfac6sv6` (`order_id`),
  CONSTRAINT `FK_reviews_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `FK_reviews_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.reviews: ~0 rows (approximately)
DELETE FROM `reviews`;

-- Dumping structure for table beyblade.token
CREATE TABLE IF NOT EXISTS `token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `code` varchar(50) NOT NULL,
  `expiry` datetime(6) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1l8br3dq53yfan1deqeb246os` (`account_id`),
  CONSTRAINT `FKlcffqvs57obcjd6a6rt2m07ka` FOREIGN KEY (`account_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table beyblade.token: ~0 rows (approximately)
DELETE FROM `token`;
INSERT INTO `token` (`id`, `create_time`, `update_time`, `code`, `expiry`, `account_id`) VALUES
	(140, '2024-05-13 15:06:32.000000', '2024-05-13 15:06:32.000000', '3bce3d30-2a0c-44a6-b493-c1d4ef4cfe44', '2024-05-14 15:06:32.000000', 30);

-- Dumping structure for table beyblade.transactions
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `transaction_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_transactions_users` (`user_id`),
  CONSTRAINT `FK_transactions_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.transactions: ~0 rows (approximately)
DELETE FROM `transactions`;

-- Dumping structure for table beyblade.type_bey
CREATE TABLE IF NOT EXISTS `type_bey` (
  `id` tinyint(4) NOT NULL DEFAULT 0,
  `name` varchar(255) DEFAULT NULL,
  `images` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.type_bey: ~4 rows (approximately)
DELETE FROM `type_bey`;
INSERT INTO `type_bey` (`id`, `name`, `images`) VALUES
	(1, 'Tấn Công', 'https://static.wikia.nocookie.net/beyblade/images/2/25/Hasbro_BB_type_icon_attack.png'),
	(2, 'Phòng Thủ', 'https://static.wikia.nocookie.net/beyblade/images/f/f4/Hasbro_BB_type_icon_defense.png'),
	(3, 'Bền Bỉ', 'https://static.wikia.nocookie.net/beyblade/images/a/a8/Hasbro_BB_type_logo_stamina.png'),
	(4, 'Cân Bằng', 'https://static.wikia.nocookie.net/beyblade/images/9/93/Hasbro_BB_type_icon_balance.png');

-- Dumping structure for table beyblade.users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `rank` tinyint(4) DEFAULT 0,
  `ma_xac_nhan` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK_users_account` (`account_id`),
  CONSTRAINT `FK_users_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.users: ~2 rows (approximately)
DELETE FROM `users`;
INSERT INTO `users` (`user_id`, `username`, `account_id`, `avatar`, `created_at`, `rank`, `ma_xac_nhan`) VALUES
	(29, 'admin', 30, '71a7fdb526584426b8bdfdc8e77100ef.jpg', '2024-05-13 08:53:14', 0, 'a9283319-a205-4bcc-be9c-3218f46ca5d8'),
	(30, 'admin1', 31, 'user_avatar.png', '2024-05-13 15:06:32', 0, '4e6905dc-59db-4411-ad86-855846e1553c');

-- Dumping structure for table beyblade.users_info
CREATE TABLE IF NOT EXISTS `users_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `fullname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `updated_time` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `shopee_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKt5dxwh90jeb9tglbiltv2s4nj` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.users_info: ~0 rows (approximately)
DELETE FROM `users_info`;
INSERT INTO `users_info` (`id`, `user_id`, `fullname`, `email`, `phone`, `gender`, `date`, `updated_time`, `shopee_name`) VALUES
	(312, 29, 'Nguyễn Đình Hải', 'beyblade@cc.cc', '201212121', 'Nam', '2024-05-16 00:00:00', '2024-05-13 08:00:55', 'shopee'),
	(313, 30, 'Họ Và Tên', 'beyblade@gmail.com', '01234556789', 'Khác', '2024-05-13 08:07:04', '2024-05-13 08:07:04', 'Nhập Tên Shopee của bạn (nếu có)');

-- Dumping structure for table beyblade.vip_by_user
CREATE TABLE IF NOT EXISTS `vip_by_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `vip_id` int(11) NOT NULL DEFAULT 0,
  `create_time` datetime(6) DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_vip_by_user_users` (`user_id`),
  KEY `FK_vip_by_user_vip_package` (`vip_id`),
  CONSTRAINT `FK_vip_by_user_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FK_vip_by_user_vip_package` FOREIGN KEY (`vip_id`) REFERENCES `vip_package` (`vip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.vip_by_user: ~0 rows (approximately)
DELETE FROM `vip_by_user`;

-- Dumping structure for table beyblade.vip_package
CREATE TABLE IF NOT EXISTS `vip_package` (
  `vip_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `days` bigint(20) DEFAULT NULL,
  `percent_off` double DEFAULT NULL,
  PRIMARY KEY (`vip_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.vip_package: ~0 rows (approximately)
DELETE FROM `vip_package`;

-- Dumping structure for table beyblade.vouchers
CREATE TABLE IF NOT EXISTS `vouchers` (
  `voucher_id` int(11) NOT NULL AUTO_INCREMENT,
  `voucher_code` varchar(50) DEFAULT NULL,
  `percent` double DEFAULT NULL,
  `gia_toi_thieu` double NOT NULL,
  `count_left` int(11) DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL,
  `apply_shop` int(11) DEFAULT NULL,
  `type` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`voucher_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.vouchers: ~0 rows (approximately)
DELETE FROM `vouchers`;

-- Dumping structure for table beyblade.vouchers_cua_toi
CREATE TABLE IF NOT EXISTS `vouchers_cua_toi` (
  `my_voucher_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `voucher_id` int(11) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `time_save` datetime DEFAULT NULL,
  PRIMARY KEY (`my_voucher_id`),
  KEY `user_id` (`user_id`),
  KEY `voucher_id` (`voucher_id`),
  CONSTRAINT `vouchers_cua_toi_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `vouchers_cua_toi_ibfk_2` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`voucher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.vouchers_cua_toi: ~0 rows (approximately)
DELETE FROM `vouchers_cua_toi`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
