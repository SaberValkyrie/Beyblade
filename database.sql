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
  `tienmat` int(11) DEFAULT NULL,
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.account: ~4 rows (approximately)
DELETE FROM `account`;
INSERT INTO `account` (`account_id`, `username`, `password`, `created_at`, `is_ban`, `coint`, `role`, `tienmat`) VALUES
	(30, 'admin', '$2a$10$UtnUX8HJG2UmKPQhzUfHNO5AyNS/n1KE9IhHtjlYMm8cz0bY38fs.', '2024-05-13 08:53:14.000000', b'0', 22200, 0, 0),
	(31, 'admin1', '$2a$10$UtnUX8HJG2UmKPQhzUfHNO5AyNS/n1KE9IhHtjlYMm8cz0bY38fs.', '2024-05-13 15:06:32.000000', b'0', 1355155, 0, 19378000),
	(32, 'haia', '$2a$10$UtnUX8HJG2UmKPQhzUfHNO5AyNS/n1KE9IhHtjlYMm8cz0bY38fs.', '2024-05-17 07:36:01.000000', b'0', 0, 0, 0),
	(33, 'test', '$2a$10$BCvuv9OcUVbBWYIGCUSU.eVGYRVefoceIGrz3ZlOA3cC0K1DgdlAm', '2024-05-30 22:04:54.000000', b'0', 283480, 0, 345000);

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

-- Dumping structure for table beyblade.giftcode
CREATE TABLE IF NOT EXISTS `giftcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.giftcode: ~5 rows (approximately)
DELETE FROM `giftcode`;
INSERT INTO `giftcode` (`id`, `code`, `type`) VALUES
	(1, 'code1', 2),
	(2, '213', 2),
	(3, 'code', 2),
	(4, '3323', 2),
	(6, '4', 4);

-- Dumping structure for table beyblade.giftcode_history
CREATE TABLE IF NOT EXISTS `giftcode_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `giftcode` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK5kuhy6whj1dqlvygd6ddrts13` (`giftcode`),
  KEY `FKs8b6og19gl4x8qxrql9kchtf` (`user`),
  CONSTRAINT `FK5kuhy6whj1dqlvygd6ddrts13` FOREIGN KEY (`giftcode`) REFERENCES `giftcode` (`id`),
  CONSTRAINT `FKs8b6og19gl4x8qxrql9kchtf` FOREIGN KEY (`user`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.giftcode_history: ~3 rows (approximately)
DELETE FROM `giftcode_history`;
INSERT INTO `giftcode_history` (`id`, `giftcode`, `user`) VALUES
	(15, 3, 32),
	(16, 1, 32),
	(17, 6, 32);

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

-- Dumping structure for table beyblade.items
CREATE TABLE IF NOT EXISTS `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `beyblade` int(11) DEFAULT NULL,
  `hsd` timestamp NULL DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `vv` bit(1) DEFAULT NULL,
  `selected_bey` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKc2mjb6l1khi50ypvtkoepg9p0` (`user`),
  KEY `FK_items_list_bey` (`beyblade`),
  CONSTRAINT `FK_items_list_bey` FOREIGN KEY (`beyblade`) REFERENCES `list_bey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKc2mjb6l1khi50ypvtkoepg9p0` FOREIGN KEY (`user`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=409 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.items: ~128 rows (approximately)
DELETE FROM `items`;
INSERT INTO `items` (`id`, `user`, `beyblade`, `hsd`, `create_time`, `vv`, `selected_bey`) VALUES
	(281, 32, 57, '2024-06-01 12:04:28', '2024-05-31 19:04:28', b'0', b'0'),
	(282, 32, 79, '2024-06-01 12:06:43', '2024-05-31 19:06:43', b'0', b'0'),
	(283, 32, 130, '2024-06-03 12:06:55', '2024-05-31 19:06:55', b'0', b'0'),
	(284, 32, 139, '2024-06-01 12:07:02', '2024-05-31 19:07:02', b'0', b'0'),
	(285, 32, 129, '2024-06-04 12:07:25', '2024-05-31 19:07:25', b'1', b'0'),
	(286, 32, 122, '2024-06-02 12:09:04', '2024-05-31 19:09:04', b'0', b'0'),
	(287, 32, 24, '2024-06-05 12:17:24', '2024-05-31 19:17:24', b'0', b'0'),
	(288, 32, 61, '2024-06-01 12:17:26', '2024-05-31 19:17:26', b'1', b'0'),
	(289, 32, 80, '2024-06-01 12:17:27', '2024-05-31 19:17:27', b'0', b'0'),
	(290, 32, 134, '2024-06-02 12:17:29', '2024-05-31 19:17:29', b'0', b'0'),
	(291, 32, 117, '2024-06-02 12:17:30', '2024-05-31 19:17:30', b'0', b'0'),
	(292, 32, 23, '2024-06-03 12:17:31', '2024-05-31 19:17:31', b'1', b'0'),
	(293, 32, 36, '2024-06-02 12:17:32', '2024-05-31 19:17:32', b'0', b'0'),
	(294, 32, 89, '2024-06-01 12:17:34', '2024-05-31 19:17:34', b'0', b'0'),
	(295, 32, 171, '2024-06-01 12:17:34', '2024-05-31 19:17:34', b'0', b'0'),
	(296, 32, 74, '2024-06-02 12:17:35', '2024-05-31 19:17:35', b'1', b'0'),
	(297, 32, 201, '2024-06-03 12:17:37', '2024-05-31 19:17:37', b'1', b'0'),
	(298, 32, 2, '2024-06-03 12:17:38', '2024-05-31 19:17:38', b'0', b'0'),
	(299, 32, 3, '2024-06-02 12:17:40', '2024-05-31 19:17:40', b'0', b'0'),
	(300, 32, 105, '2024-06-03 12:17:41', '2024-05-31 19:17:41', b'0', b'0'),
	(301, 32, 21, '2024-06-05 12:17:42', '2024-05-31 19:17:42', b'0', b'0'),
	(302, 32, 191, '2024-06-04 12:17:43', '2024-05-31 19:17:43', b'0', b'0'),
	(303, 32, 164, '2024-06-02 12:17:43', '2024-05-31 19:17:43', b'1', b'0'),
	(304, 32, 48, '2024-06-03 12:17:43', '2024-05-31 19:17:43', b'0', b'0'),
	(305, 32, 15, '2024-06-02 12:17:44', '2024-05-31 19:17:44', b'0', b'0'),
	(306, 32, 97, '2024-06-01 12:17:44', '2024-05-31 19:17:44', b'0', b'0'),
	(307, 32, 167, '2024-06-03 12:17:44', '2024-05-31 19:17:44', b'0', b'0'),
	(308, 32, 99, '2024-06-04 12:17:45', '2024-05-31 19:17:45', b'0', b'0'),
	(309, 32, 104, '2024-06-02 12:17:45', '2024-05-31 19:17:45', b'0', b'0'),
	(310, 32, 86, '2024-06-01 12:17:46', '2024-05-31 19:17:46', b'0', b'0'),
	(311, 32, 106, '2024-06-03 12:17:46', '2024-05-31 19:17:46', b'0', b'0'),
	(312, 32, 34, '2024-06-03 12:17:46', '2024-05-31 19:17:46', b'0', b'0'),
	(313, 32, 75, '2024-06-01 12:17:46', '2024-05-31 19:17:46', b'1', b'0'),
	(314, 32, 22, '2024-06-03 12:17:47', '2024-05-31 19:17:47', b'0', b'0'),
	(315, 32, 148, '2024-06-03 12:17:47', '2024-05-31 19:17:47', b'0', b'0'),
	(316, 32, 197, '2024-06-02 12:31:18', '2024-05-31 19:31:18', b'0', b'0'),
	(317, 32, 56, '2024-06-01 12:31:20', '2024-05-31 19:31:20', b'0', b'0'),
	(318, 32, 17, '2024-06-02 12:31:22', '2024-05-31 19:31:22', b'1', b'0'),
	(319, 32, 125, '2024-06-02 12:31:44', '2024-05-31 19:31:44', b'1', b'0'),
	(320, 32, 188, '2024-06-02 12:31:45', '2024-05-31 19:31:45', b'1', b'0'),
	(321, 32, 176, '2024-06-02 12:31:46', '2024-05-31 19:31:46', b'1', b'0'),
	(322, 32, 163, '2024-06-02 12:31:47', '2024-05-31 19:31:47', b'1', b'0'),
	(323, 32, 155, '2024-06-03 12:31:48', '2024-05-31 19:31:48', b'1', b'0'),
	(324, 32, 62, '2024-06-02 12:31:48', '2024-05-31 19:31:48', b'1', b'0'),
	(325, 32, 154, '2024-06-01 12:31:48', '2024-05-31 19:31:48', b'1', b'0'),
	(326, 32, 203, '2024-06-03 12:31:48', '2024-05-31 19:31:48', b'1', b'0'),
	(327, 32, 28, '2024-06-02 12:31:49', '2024-05-31 19:31:49', b'1', b'0'),
	(328, 32, 148, '2024-06-03 12:31:49', '2024-05-31 19:31:49', b'1', b'0'),
	(329, 32, 79, '2024-06-03 12:31:49', '2024-05-31 19:31:49', b'1', b'0'),
	(330, 32, 71, '2024-06-01 12:31:49', '2024-05-31 19:31:49', b'1', b'0'),
	(331, 32, 145, '2024-06-01 12:31:49', '2024-05-31 19:31:49', b'1', b'0'),
	(332, 32, 122, '2024-06-01 12:31:50', '2024-05-31 19:31:50', b'1', b'0'),
	(333, 32, 170, '2024-06-03 12:31:50', '2024-05-31 19:31:50', b'1', b'0'),
	(334, 32, 120, '2024-06-02 12:31:50', '2024-05-31 19:31:50', b'1', b'0'),
	(335, 32, 86, '2024-06-02 12:31:50', '2024-05-31 19:31:50', b'1', b'0'),
	(336, 32, 151, '2024-06-02 12:31:50', '2024-05-31 19:31:50', b'1', b'0'),
	(337, 32, 87, '2024-06-02 12:31:51', '2024-05-31 19:31:51', b'1', b'0'),
	(338, 32, 198, '2024-06-03 12:31:51', '2024-05-31 19:31:51', b'1', b'0'),
	(339, 32, 192, '2024-06-02 12:31:51', '2024-05-31 19:31:51', b'1', b'0'),
	(340, 32, 42, '2024-06-03 12:31:51', '2024-05-31 19:31:51', b'1', b'0'),
	(341, 32, 159, '2024-06-03 12:31:51', '2024-05-31 19:31:51', b'1', b'0'),
	(342, 32, 73, '2024-06-02 12:32:32', '2024-05-31 19:32:32', b'1', b'0'),
	(343, 32, 125, '2024-06-01 12:32:32', '2024-05-31 19:32:32', b'1', b'0'),
	(344, 32, 173, '2024-06-01 12:32:33', '2024-05-31 19:32:33', b'1', b'0'),
	(345, 32, 97, '2024-06-01 12:32:33', '2024-05-31 19:32:33', b'1', b'0'),
	(346, 32, 23, '2024-06-03 12:32:34', '2024-05-31 19:32:34', b'1', b'0'),
	(347, 32, 66, '2024-06-01 12:32:34', '2024-05-31 19:32:34', b'1', b'0'),
	(348, 32, 130, '2024-06-03 12:32:35', '2024-05-31 19:32:35', b'1', b'0'),
	(349, 32, 92, '2024-06-01 12:32:36', '2024-05-31 19:32:36', b'1', b'0'),
	(350, 32, 125, '2024-06-02 12:32:36', '2024-05-31 19:32:36', b'1', b'0'),
	(351, 32, 194, '2024-06-03 12:32:37', '2024-05-31 19:32:37', b'1', b'0'),
	(352, 32, 126, '2024-06-01 12:32:37', '2024-05-31 19:32:37', b'1', b'0'),
	(353, 32, 169, '2024-06-03 12:32:37', '2024-05-31 19:32:37', b'1', b'0'),
	(354, 32, 175, '2024-06-03 12:32:37', '2024-05-31 19:32:37', b'1', b'0'),
	(355, 32, 176, '2024-06-01 12:32:37', '2024-05-31 19:32:37', b'1', b'0'),
	(356, 32, 102, '2024-06-03 12:32:38', '2024-05-31 19:32:38', b'1', b'0'),
	(357, 32, 169, '2024-06-03 12:32:38', '2024-05-31 19:32:38', b'1', b'0'),
	(358, 32, 42, '2024-06-03 12:32:38', '2024-05-31 19:32:38', b'1', b'0'),
	(359, 32, 170, '2024-06-03 12:32:38', '2024-05-31 19:32:38', b'1', b'0'),
	(360, 32, 193, '2024-06-01 12:32:38', '2024-05-31 19:32:38', b'1', b'0'),
	(361, 32, 139, '2024-06-03 12:32:39', '2024-05-31 19:32:39', b'1', b'0'),
	(362, 32, 169, '2024-06-03 12:32:39', '2024-05-31 19:32:39', b'1', b'0'),
	(363, 32, 111, '2024-06-03 12:32:39', '2024-05-31 19:32:39', b'1', b'0'),
	(364, 32, 21, '2024-06-01 12:32:39', '2024-05-31 19:32:39', b'1', b'0'),
	(365, 32, 160, '2024-06-01 12:32:39', '2024-05-31 19:32:39', b'1', b'0'),
	(366, 32, 189, '2024-06-01 12:32:40', '2024-05-31 19:32:40', b'1', b'0'),
	(367, 32, 150, '2024-06-03 12:32:40', '2024-05-31 19:32:40', b'1', b'0'),
	(368, 32, 80, '2024-06-02 12:32:40', '2024-05-31 19:32:40', b'1', b'0'),
	(369, 32, 194, '2024-06-03 12:32:40', '2024-05-31 19:32:40', b'1', b'0'),
	(370, 32, 122, '2024-06-02 12:32:40', '2024-05-31 19:32:40', b'1', b'0'),
	(371, 32, 35, '2024-06-01 12:32:41', '2024-05-31 19:32:41', b'1', b'0'),
	(372, 32, 49, '2024-06-03 12:32:41', '2024-05-31 19:32:41', b'1', b'0'),
	(373, 32, 115, '2024-06-01 12:32:41', '2024-05-31 19:32:41', b'1', b'0'),
	(374, 32, 185, '2024-06-03 12:32:41', '2024-05-31 19:32:41', b'1', b'0'),
	(375, 32, 184, '2024-06-02 12:32:41', '2024-05-31 19:32:41', b'1', b'0'),
	(376, 32, 189, '2024-06-02 12:32:42', '2024-05-31 19:32:42', b'1', b'0'),
	(377, 32, 139, '2024-06-02 12:32:42', '2024-05-31 19:32:42', b'1', b'0'),
	(378, 32, 42, '2024-06-02 12:32:42', '2024-05-31 19:32:42', b'1', b'0'),
	(379, 32, 185, '2024-06-03 12:32:42', '2024-05-31 19:32:42', b'1', b'0'),
	(380, 32, 181, '2024-06-03 12:32:42', '2024-05-31 19:32:42', b'1', b'0'),
	(381, 32, 167, '2024-06-01 12:32:43', '2024-05-31 19:32:43', b'1', b'0'),
	(382, 32, 154, '2024-06-01 12:32:43', '2024-05-31 19:32:43', b'1', b'0'),
	(383, 32, 157, '2024-06-02 12:32:43', '2024-05-31 19:32:43', b'1', b'0'),
	(384, 32, 37, '2024-06-02 12:32:43', '2024-05-31 19:32:43', b'1', b'0'),
	(385, 32, 23, '2024-06-03 12:32:43', '2024-05-31 19:32:43', b'1', b'0'),
	(386, 32, 37, '2024-06-02 12:32:57', '2024-05-31 19:32:57', b'1', b'0'),
	(387, 32, 150, '2024-06-02 12:32:57', '2024-05-31 19:32:57', b'1', b'0'),
	(388, 32, 125, '2024-06-01 12:32:57', '2024-05-31 19:32:57', b'1', b'0'),
	(389, 32, 74, '2024-06-01 12:32:57', '2024-05-31 19:32:57', b'1', b'0'),
	(390, 32, 125, '2024-06-01 12:32:57', '2024-05-31 19:32:57', b'1', b'0'),
	(391, 32, 155, '2024-06-01 12:32:57', '2024-05-31 19:32:57', b'1', b'0'),
	(392, 32, 131, '2024-06-02 12:32:58', '2024-05-31 19:32:58', b'1', b'0'),
	(393, 32, 37, '2024-06-02 12:32:58', '2024-05-31 19:32:58', b'1', b'0'),
	(394, 32, 127, '2024-06-02 12:32:58', '2024-05-31 19:32:58', b'1', b'0'),
	(395, 32, 62, '2024-06-02 12:32:58', '2024-05-31 19:32:58', b'1', b'0'),
	(396, 32, 133, '2024-06-01 12:32:58', '2024-05-31 19:32:58', b'1', b'0'),
	(397, 32, 100, '2024-06-01 12:32:59', '2024-05-31 19:32:59', b'1', b'0'),
	(398, 32, 118, '2024-06-01 12:32:59', '2024-05-31 19:32:59', b'1', b'0'),
	(399, 32, 115, '2024-06-01 12:32:59', '2024-05-31 19:32:59', b'1', b'0'),
	(400, 32, 66, '2024-06-02 12:32:59', '2024-05-31 19:32:59', b'1', b'0'),
	(401, 32, 202, '2024-06-02 12:32:59', '2024-05-31 19:32:59', b'1', b'0'),
	(402, 32, 12, '2024-06-02 12:33:00', '2024-05-31 19:33:00', b'1', b'0'),
	(403, 32, 21, '2024-06-01 12:33:00', '2024-05-31 19:33:00', b'1', b'0'),
	(404, 32, 197, '2024-06-02 12:33:00', '2024-05-31 19:33:00', b'1', b'0'),
	(405, 32, 103, '2024-06-03 12:33:00', '2024-05-31 19:33:00', b'1', b'0'),
	(406, 32, 160, '2024-06-01 12:33:01', '2024-05-31 19:33:01', b'1', b'0'),
	(407, 32, 174, '2024-06-02 12:33:01', '2024-05-31 19:33:01', b'1', b'1'),
	(408, 32, 2, '2024-06-01 12:33:01', '2024-05-31 19:33:01', b'1', b'0');

-- Dumping structure for table beyblade.list_bey
CREATE TABLE IF NOT EXISTS `list_bey` (
  `id` int(11) NOT NULL DEFAULT 0,
  `type` tinyint(4) DEFAULT NULL,
  `ti_le_ne_don` tinyint(3) DEFAULT NULL,
  `is_boss` bit(1) DEFAULT NULL,
  `price` int(11) DEFAULT 1000,
  `name` varchar(255) DEFAULT NULL,
  `images` text DEFAULT NULL,
  `power` int(11) DEFAULT 0,
  `hp` bigint(20) DEFAULT NULL,
  `crit` tinyint(4) DEFAULT NULL,
  `season` tinyint(4) DEFAULT NULL,
  `spin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_list_bey_type_bey` (`type`),
  CONSTRAINT `FK_list_bey_type_bey` FOREIGN KEY (`type`) REFERENCES `type_bey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.list_bey: ~132 rows (approximately)
DELETE FROM `list_bey`;
INSERT INTO `list_bey` (`id`, `type`, `ti_le_ne_don`, `is_boss`, `price`, `name`, `images`, `power`, `hp`, `crit`, `season`, `spin`) VALUES
	(1, 1, 1, b'0', 800, 'Valkyrie Wing Accel ', 'https://static.wikia.nocookie.net/beyga/images/5/5c/B01ValkyrieWingAccel.jpg', 5000, 15000, 30, 1, 'R'),
	(2, 4, 2, b'0', 800, 'Spriggan Spread Fusion ', 'https://i.ebayimg.com/images/g/mxgAAOSwAg1hznyQ/s-l1200.jpg', 4000, 20000, 30, 1, 'R'),
	(3, 3, 5, b'0', 1020, 'Ragnaruk Heavy Survive', 'https://i.ebayimg.com/images/g/IR4AAOSwG3dh8mD5/s-l1200.webp', 200, 50000, 3, 1, 'R'),
	(4, 2, 3, b'0', 1050, 'Kerbeus Central Defense', 'https://ae01.alicdn.com/kf/Hf78bf01a0caf43ac91cefc3e10e7d012O/TAKARA-TOMY-Beyblade-B04-DX-Starter-Kerbeus-Central-Defense-Burst-System.jpg', 1500, 45000, 3, 1, 'R'),
	(12, 1, 1, b'0', 800, 'Deathsyther Oval Accel', 'https://yeepvn.sgp1.digitaloceanspaces.com/2022/03/50780-c334827bf78479896d661b8b033ebb3f4da6c657.jpeg', 2000, 30000, 13, 1, 'R'),
	(14, 2, 30, b'0', 200, 'Wyvern Armed Massive', 'https://cdn11.bigcommerce.com/s-iodt3qca/images/stencil/350x350/products/409/822/s-l1600__13222.1535682854.jpg', 1000, 5000, 3, 1, 'R'),
	(15, 3, 8, b'0', 800, 'Trident  Heavy Claw', 'https://i.ebayimg.com/images/g/Ho8AAOSwdjdaFkYJ/s-l500.jpg', 1000, 35000, 10, 1, 'R'),
	(17, 1, 20, b'0', 800, 'Odin Central Blow', 'https://images-na.ssl-images-amazon.com/images/I/71J5Skn5kCL.SS400.jpg', 500, 37500, 1, 1, 'R'),
	(20, 3, 1, b'0', 710, 'Horusood Spread Edge', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSveffg-M5Ki0BbfvxNSDURfH0UW5ogiERa3fc6Wpn3DlCNoIlJbnADuJ-6j-1q2J8xBOs&usqp=CAU', 100, 35000, 3, 1, 'R'),
	(21, 1, 1, b'0', 400, 'Minoboros Oval Quake', 'https://beyblade-store.com/cdn/shop/products/Beyblade-Minoboros-Oval-Quake-Beyblade-Store_1024x1024.jpg', 3000, 5000, 50, 1, 'R'),
	(22, 2, 1, b'0', 1300, 'Unicorn Ring Defense', 'https://static.wikia.nocookie.net/beyblade/images/a/ab/UrmoPjr.jpg', 3000, 50000, 10, 1, 'R'),
	(23, 1, 1, b'0', 1100, 'Xcalibur Force Xtreme', 'https://product.hstatic.net/200000504579/product/beyblade_b-23_con_quay_xcalibur_force_xtreme.1_65a854f776bc495594c9e52822a8c505_master.jpg', 7000, 20000, 90, 1, 'R'),
	(24, 2, 20, b'0', 1300, 'Evil-eye Wing Needle', 'https://cdn11.bigcommerce.com/s-iodt3qca/images/stencil/1280x1280/products/2002/8681/latest__72080.1686718640.png', 3000, 50000, 3, 1, 'R'),
	(28, 4, 10, b'0', 1300, 'Neptune Armed Zephyr', 'https://i.ebayimg.com/images/g/l4UAAOSwnNZl0-W6/s-l1600.jpg', 5000, 40000, 3, 1, 'R'),
	(31, 3, 1, b'0', 1100, 'Yggdrasil Ring Gyro', 'https://i.ebayimg.com/images/g/ERUAAOSwxXNmH~Ba/s-l1600.jpg', 1000, 50000, 10, 1, 'R'),
	(34, 1, 5, b'0', 1500, 'Victory Valkyrie Boost Variable', 'https://static.wikia.nocookie.net/beyblade/images/1/10/VV.B.V.png', 10000, 25000, 50, 1, 'R'),
	(35, 4, 10, b'0', 1500, 'Storm Spriggan Knuckle Unite', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/440860244_1165114607954123_2262919544289196761_n.jpg?_nc_cat=100&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGp_phg-bFvQgLWETKOcxL16H_bDnniQ5rof9sOeeJDmtb9Jvk-lJhTkSixSlCdZqw3LlCur1nw5V6Df23u8D0O&_nc_ohc=FMGwwO-ZUKMQ7kNvgEH41A8&_nc_ht=scontent.fhan18-1.fna&oh=00_AYDqvA0ZQFBmo4wTt5ndICYAacomIcb3Q7Gj_YaglbjbBQ&oe=665FA83C', 7500, 37500, 30, 1, 'R'),
	(36, 3, 20, b'0', 1700, 'Rising Ragnaruk Gravity Revolve', 'https://i.ebayimg.com/images/g/E-EAAOSwwydkJvU-/s-l400.jpg', 2000, 75000, 3, 1, 'R'),
	(37, 2, 5, b'0', 1300, 'Kaiser Keberus Limited Press', 'https://static.wikia.nocookie.net/beyblade/images/1/1e/KaiserKerbeusLP.jpg', 3000, 50000, 10, 1, 'R'),
	(41, 2, 30, b'0', 1300, 'Wild Wyvern Vertical Orbit', 'https://ae01.alicdn.com/kf/H29feb077045f40b1a75f2bb8fd9d99e9H/B-X-TOUPIE-BURST-BEYBLADE-SPINNING-TOP-B191-B192-B-41-Starter-Wild-Wyvern-V-O.jpg', 3000, 50000, 1, 1, 'R'),
	(42, 1, 7, b'0', 1500, 'Dark Deathscyther Force Jaggy', 'https://ae01.alicdn.com/kf/Hf37e16ddc68a4250839160e8cf237c9cp.jpg', 10000, 25000, 20, 1, 'R'),
	(44, 3, 1, b'0', 1200, 'Holy Horusood Upper Claw', 'https://http2.mlstatic.com/D_NQ_NP_931091-MLB31225038077_062019-O.webp', 2000, 50000, 3, 1, 'R'),
	(46, 1, 1, b'0', 1500, 'Odin Triple Xtreme', 'https://salt.tikicdn.com/cache/w300/ts/product/02/ef/91/4fd062fc7291ab3ebeac7480d35034c7.jpg', 10000, 25000, 25, 1, 'R'),
	(48, 1, 1, b'0', 1700, 'Xeno Xcalibur Magnum Impact', 'https://cf.shopee.vn/file/c35dbf715fe5a5f883d6c45d6513c16e', 12000, 25000, 80, 1, 'R'),
	(49, 3, 20, b'0', 1200, 'Yaeger Yggdrasil Gravity Yielding', 'https://static.wikia.nocookie.net/beyblade/images/b/b5/Y2_gy.jpg', 2000, 50000, 3, 1, 'R'),
	(56, 2, 1, b'0', 1200, 'Unlock Unicorn Down Needle', 'https://i.ebayimg.com/images/g/AMoAAOSwkidkYRWS/s-l1200.webp', 2000, 50000, 99, 1, 'R'),
	(57, 4, 9, b'0', 1800, 'Nova Neptune Vertical Trans', 'https://salt.tikicdn.com/cache/w1200/ts/product/5e/a8/40/12d22bfaf11a12c8650d802f7b50b42a.jpg', 9000, 45000, 9, 1, 'R'),
	(59, 3, 1, b'0', 2500, 'Zillion Zeus Infinity Weight', 'https://i.ebayimg.com/images/g/vTsAAOSwLdtkIQbi/s-l400.jpg', 5000, 100000, 20, 1, 'R'),
	(61, 2, 30, b'0', 1300, 'Quad Quetzalcoatl Jerk Press', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWaNaQYyAxgB1rQ9pZd0azR4IJLDag2Z5xYbKJTo1cQw&s', 3000, 50000, 3, 1, 'R'),
	(62, 1, 1, b'0', 1500, 'Fang Fenrir Boost Jaggy', 'https://i.ebayimg.com/images/g/V9YAAOSw7k1iYvkk/s-l1600.jpg', 10000, 25000, 3, 1, 'R'),
	(63, 1, 1, b'0', 1900, 'Beast Behemoth Heavy Hold', 'https://static.wikia.nocookie.net/beyblade/images/e/e4/Beast_Behemoth_%28B-80_08_Ver%29.png', 10000, 45000, 3, 1, 'R'),
	(64, 4, 1, b'0', 2000, 'Inferno Ifrit Magnum Liner', 'https://static.wikia.nocookie.net/beyblade/images/0/00/I2_ml.jpg', 10000, 50000, 1, 1, 'R'),
	(66, 1, 20, b'1', 2800, 'Lost Longginus Nine Spriral', 'https://salt.tikicdn.com/cache/w1200/ts/product/92/59/c0/758f9728ceed7553ec017b9cdc27d6b3.jpg', 15000, 65000, 99, 1, 'L'),
	(67, 4, 1, b'0', 1600, 'Gigant Gaia .8U.Q', 'https://static.wikia.nocookie.net/beyblade/images/3/31/G2_qf.jpg', 8000, 40000, 40, 1, 'R'),
	(69, 3, 1, b'0', 1200, 'Jail Jormungand Infinity Cycle', 'https://i.ebayimg.com/00/s/ODAwWDgyMg==/z/PnsAAOSwoWdgnZ5x/$_12.JPG', 2000, 50000, 3, 1, 'R'),
	(71, 3, 1, b'0', 2200, 'Acid Anubis Yell Orbit', 'https://www.premiumtoy.my/images/sell_products/big/image_277.jpg', 2000, 100000, 3, 1, 'R'),
	(73, 1, 1, b'0', 2800, 'God Valkyrie 6vortex Reboot', 'https://http2.mlstatic.com/D_NQ_NP_756942-CBT48177332458_112021-O.webp', 15000, 65000, 6, 2, 'R'),
	(74, 2, 1, b'0', 2600, 'Kreis Satan 2glaive Loop', 'https://i.ebayimg.com/images/g/a74AAOSw32lkIkOb/s-l400.jpg', 6000, 100000, 6, 2, 'R'),
	(75, 3, 40, b'0', 2400, 'Blaze Ragnaruk 4Cross Flugel', 'https://i.ebayimg.com/images/g/dlcAAOSwriBhsgTM/s-l1200.webp', 4000, 100000, 6, 2, 'R'),
	(79, 3, 30, b'1', 6500, 'Drain Fafnir 8 Nothing', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/427957681_1165116307953953_3959228426518279249_n.jpg?stp=dst-jpg_p228x119&_nc_cat=101&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHPBcdszucYSRVNuIJTKeXZWQ7q1cISVvdZDurVwhJW93m-qqxb_8TYFGtSrjT-2ve1E2cHQjXZM8PMcqSG3YM1&_nc_ohc=qG2AS53AByEQ7kNvgFdTWYD&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCaX4TH5nMs1Fqho8q5Pv6LCdTv5r8KH7SQ7rLRUJHqLw&oe=665FB0A9', 15000, 250000, 30, 2, 'L'),
	(80, 2, 1, b'0', 2600, 'Tornado Wyvern 4Glaive Atomic ', 'https://i.pinimg.com/736x/6b/bc/65/6bbc651c76e3d46b671c73b467918e24.jpg', 6000, 100000, 6, 2, 'R'),
	(82, 4, 1, b'0', 3000, 'Alter Chronos 6 Meteor Trans', 'https://salt.tikicdn.com/cache/w300/ts/product/3f/1f/c1/5154de6ded5fc338bc91f1212b354b78.jpg', 15000, 75000, 6, 2, 'R'),
	(85, 1, 1, b'0', 2500, 'Killer Deathscyther 2vortex Hunter', 'https://i.ebayimg.com/images/g/HjMAAOSw2o1kJNWS/s-l1200.webp', 15000, 50000, 20, 2, 'R'),
	(86, 4, 1, b'0', 4000, 'Legend Spriggan 7 Merge', 'https://i.ebayimg.com/images/g/UvgAAOSwVSRkIk2c/s-l1200.webp', 20000, 100000, 44, 2, 'LR'),
	(87, 3, 50, b'0', 2100, 'Maximum Garuda 8Flow Flugel', 'https://static.wikia.nocookie.net/beyblade/images/b/b2/MG_8f_fl.jpg', 1000, 100000, 6, 2, 'R'),
	(89, 2, 1, b'0', 2600, 'Blast Jinnius 5Glaive Guard', 'https://i.ebayimg.com/images/g/G~IAAOSwOvdkIQP0/s-l1200.webp', 6000, 100000, 6, 2, 'R'),
	(90, 3, 1, b'0', 2900, 'Galaxy Zeus', 'https://i.ebayimg.com/images/g/6lAAAOSwfpBaZK2a/s-l1200.jpg', 4000, 125000, 9, 2, 'R'),
	(92, 1, 1, b'0', 4000, 'Sieg Xcalibur 1 Iron', 'https://static.wikia.nocookie.net/beyblade/images/3/3d/SX_1_ir.jpg', 20000, 100000, 50, 2, 'R'),
	(95, 4, 50, b'0', 1010, 'Shelter Regulus 5Star Tower ', 'https://down-my.img.susercontent.com/file/d5a05e1f5c89e64313aec60419537b17', 10000, 500, 1, 2, 'R'),
	(97, 1, 1, b'0', 8000, 'Nightmare Longginus Destroy', 'https://m.media-amazon.com/images/I/71voo-QDwZL._AC_SX679_.jpg', 50000, 150000, 20, 2, 'L'),
	(98, 2, 50, b'0', 3600, 'Arc Bahamut 2Bump Atomic', 'https://salt.tikicdn.com/cache/w1200/ts/product/56/57/1f/d7c1f1104eb608bc2b79ce4440940606.jpg', 6000, 150000, 1, 2, 'L'),
	(99, 3, 30, b'0', 2000, 'Deep Chaos 4Flow Bearing', 'https://static.wikia.nocookie.net/beyblade/images/1/16/DC_4f_br.jpg', 1, 100000, 1, 2, 'R'),
	(100, 4, 20, b'1', 7000, 'Spriggan Requiem 0 Zeta', 'https://i.ebayimg.com/images/g/C4kAAOSwv~1aIXGD/s-l1200.jpg', 30000, 200000, 44, 2, 'LR'),
	(101, 1, 33, b'0', 2300, 'Beat Kukulcan.7U.Hn', 'https://i.ebayimg.com/images/g/TvEAAOSwV0VcASer/s-l1600.jpg', 15000, 40000, 6, 2, 'R'),
	(102, 1, 1, b'0', 2500, 'Twin Nemesis 3Hit Jaggy', 'https://m.media-amazon.com/images/I/717gay-QdaL._AC_UF350,350_QL80_.jpg', 15000, 50000, 80, 2, 'R'),
	(103, 3, 20, b'0', 2400, 'Screw Trident 8Bump Wedge', 'https://cdn11.bigcommerce.com/s-iodt3qca/images/stencil/1280x1280/products/532/1047/s-l1600__92847.1545775254.jpg', 4000, 100000, 6, 2, 'R'),
	(104, 1, 10, b'0', 8000, 'Winning Valkyrie 12 Volcanic', 'https://static.wikia.nocookie.net/beyblade/images/6/69/WV_.12.Vl.jpg', 50000, 150000, 33, 3, 'R'),
	(105, 4, 1, b'0', 6000, 'Z Achilles 11 Xtend', 'https://static.wikia.nocookie.net/beyblade/images/2/28/ZA_.11.Xt.jpg', 30000, 150000, 90, 3, 'R'),
	(106, 2, 1, b'0', 4400, 'Emperor Forneus 0 Yard', 'https://static.wikia.nocookie.net/beyblade/images/9/98/EF_.0.Yr.jpg', 9000, 175000, 9, 3, 'R'),
	(110, 1, 1, b'0', 4000, 'Bloody Longinus 13 Jolt', 'https://down-br.img.susercontent.com/file/e09673b7f5da7790b4ab5125990172cb', 30000, 50000, 98, 3, 'L'),
	(111, 3, 25, b'0', 5600, 'Crash Ragnaruk 11Reach Wedge', 'https://static.wikia.nocookie.net/beyblade/images/f/f1/CR_.11R.Wd.png', 6000, 250000, 9, 3, 'R'),
	(113, 4, 25, b'0', 5000, 'Hell Salamander 12 Operate', 'https://static.wikia.nocookie.net/beyblade/images/f/f8/HS_.12.Op_%2810_Blade_Mode%29.jpg', 25000, 125000, 25, 3, 'L'),
	(115, 3, 25, b'0', 3100, 'Archer Hercules.13.Et', 'https://i5.walmartimages.com/asr/93b196e2-7438-4704-a4bd-9c97b62f386a.761eb7ed01a9d2a498074504d46d6b75.jpeg', 6000, 125000, 9, 3, 'R'),
	(117, 2, 30, b'0', 4000, 'Revive Phoenix 10 Friction', 'https://static.wikia.nocookie.net/beyblade/images/4/40/Black_Revive_Phoenix_10_Friction.jpg', 15000, 125000, 15, 3, 'R'),
	(118, 1, 1, b'0', 2200, 'Vise Leopard 12Lift Destroy', 'https://static.wikia.nocookie.net/beyblade/images/2/24/VL_.12L.Ds.png', 12000, 50000, 9, 3, 'R'),
	(120, 1, 1, b'0', 3500, 'Buster Xcalibur 1\' Sword', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/441901861_1165116477953936_5100017067934256710_n.jpg?stp=dst-jpg_s180x540&_nc_cat=107&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGCI-MhSWiuCh_AQzjCIlwHeUGSu-1Z9Jd5QZK77Vn0lwa7kzNsrMUbfmU4hVt00AkFTj-FA4T4TSo9VpwAhu8r&_nc_ohc=JQiB3NBmu8AQ7kNvgGBhU8P&_nc_ht=scontent.fhan18-1.fna&oh=00_AYAE4H0q9wii7wyyOonD2sWBH9ypbq6vsqT-OdVk4A_Leg&oe=665FA662', 25000, 50000, 90, 3, 'R'),
	(121, 2, 20, b'0', 2900, 'Hazard Kerbeus 7 Atomic ', 'https://www.premiumtoy.my/images/sell_products/interactive/459/ex48ykvlwxfh7.jpg', 9000, 100000, 9, 3, 'R'),
	(122, 3, 25, b'1', 7600, 'Geist Fafnir 8\' Absorb', 'https://img.lazcdn.com/g/p/e22a1344b3cfa2a3cfd9f2c9c0ba8118.jpg_720x720q80.jpg', 26000, 450000, 9, 3, 'L'),
	(125, 4, 10, b'1', 9000, 'Dead Hades 11Turn Zephyr\' ', 'https://static.wikia.nocookie.net/beyblade/images/0/08/DH_front.png', 45000, 325000, 20, 3, 'R'),
	(126, 4, 10, b'0', 8500, 'Fusion Aether', 'https://i.redd.it/514ag4oi213b1.png', 40000, 225000, 5, 3, 'R'),
	(127, 1, 1, b'0', 8500, 'Cho-Z Valkyrie Zenith Evolution', 'https://http2.mlstatic.com/D_NQ_NP_732183-MLB42007054422_052020-O.webp', 150000, 175000, 50, 3, 'R'),
	(128, 4, 20, b'0', 10000, 'Cho-Z Spriggan 0Wall Zeta\'\r\n', 'https://static.wikia.nocookie.net/beyblade/images/1/18/Cho-Z_Spriggan_0W_Zt%27_Pic_2.jpg', 30000, 350000, 9, 3, 'LR'),
	(129, 4, 10, b'0', 9000, 'Cho-Z Achilles 00 Dimension\r\n', 'https://i.pinimg.com/736x/f5/91/95/f5919593e8cb9ff5fdfc26b624cc2648.jpg', 45000, 225000, 20, 3, 'R'),
	(130, 3, 10, b'0', 8010, 'Air Knight 12Expand Eternal', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/445364881_1165116677953916_5109759097424833089_n.jpg?stp=dst-jpg_p75x225&_nc_cat=108&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeFXIFCjNgxKqWWhnkph8Jdf1EInFHZjaOrUQicUdmNo6jleQHADQbufK0HhM60L2Gterhn_lf5mi8Ds1_ZnX5iy&_nc_ohc=RAPwve_07bsQ7kNvgFbgxnK&_nc_ht=scontent.fhan18-1.fna&oh=00_AYB_j1uXLCG1s-ClnuY-hnrvzBK9-Ij4muxjQjWVQTjurQ&oe=665FB36F', 100, 400000, 1, 3, 'R'),
	(131, 2, 6, b'1', 11000, 'Dead Phoenix 10 Friction', 'https://cdn.lojavirtuolpro.com/beyblader/produto//p/h/phoenix.jpg', 30000, 500000, 20, 3, 'R'),
	(133, 1, 1, b'0', 7000, 'Ace Dragon Sting Charge Zan', 'https://static.wikia.nocookie.net/beyblade/images/3/36/AD.St.Ch_1.jpg', 40000, 150000, 19, 4, 'R'),
	(134, 1, 1, b'0', 9000, 'Slash Valkyrie Blitz Power Retsu', 'https://static.wikia.nocookie.net/beyblade/images/4/4c/SV.Bl.Pw_1.jpg', 60000, 150000, 33, 4, 'R'),
	(135, 2, 1, b'0', 7200, 'Bushin Ashura Hurricane Keep Ten', 'https://static.wikia.nocookie.net/beyblade/images/c/c1/BA.Hr.Kp_1.jpg', 12000, 300000, 6, 4, 'R'),
	(139, 3, 1, b'0', 6800, 'Wizard Fafnir Ratchet Rise Sen', 'https://static.wikia.nocookie.net/beyblade/images/4/44/WF.Rt.Rs_Sen_Pic_1.jpg', 8000, 300000, 33, 4, 'L'),
	(142, 4, 1, b'0', 7000, 'Judgement Joker .00T.Tr Zan ', 'https://m.media-amazon.com/images/I/71Ib9GqO4WL._AC_UF1000,1000_QL80_.jpg', 40000, 150000, 50, 4, 'R'),
	(144, 1, 1, b'0', 8000, 'Zwei Longinus Drake Spiral\' Metsu', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/444788813_1165116847953899_884974606915375409_n.jpg?stp=dst-jpg_p228x119&_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeEM6mgRxVUdWMVsHYFsMD1E2r2wp_yutHjavbCn_K60eJtGF5FfD-Y_DufFiGQC7ySeWr_qSrtMfHcSNsBCThw4&_nc_ohc=vHE3jRwxJJMQ7kNvgHTykym&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCCVeaU-zK1n3jRJeRSVz7xTVu8eVx1vs7hOuPMdGGuOw&oe=665FA8AF', 50000, 150000, 20, 4, 'L'),
	(145, 4, 20, b'0', 9000, 'Venom Diabolos Vanguard Bullet', 'https://static.wikia.nocookie.net/beyblade/images/e/e7/VD.Vn.Bl_%28Silver_Dragon_Ver.%29.png', 45000, 225000, 20, 4, 'LR'),
	(148, 3, 33, b'0', 5300, 'Heaven Pegasus 10Proof Low Sen', 'https://static.wikia.nocookie.net/beyblade/images/0/0f/HP.10P.Lw_Sen_Pic_1.jpg', 8000, 225000, 12, 4, 'R'),
	(149, 2, 99, b'0', 1210, 'Dread Bahamut 7Wall Orbit Metal Gen', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQiGF6TuURyg243S9AILznVaKZO9j3IH3Gjn01SdsXzOi_8Xr1FpP_Kvmta3XqNAq0FqVw&usqp=CAU', 12000, 500, 12, 4, 'L'),
	(150, 4, 22, b'0', 9000, 'Union Achilles Convert Xtend+ Retsu', 'https://static.wikia.nocookie.net/beyblade/images/c/cf/UA.Cn.Xt%2B_Retsu_1.jpg', 45000, 225000, 22, 4, 'R'),
	(151, 2, 10, b'1', 10000, 'Regalia Genesis.Hy', 'https://down-br.img.susercontent.com/file/c1ea2a268ea42aa0ed3be765a8075fe0', 66666, 500000, 100, 4, 'R'),
	(153, 1, 1, b'0', 14900, 'Prime Apocalypse 0Dagger Ultimate Reboot\'', 'https://ae01.alicdn.com/kf/H96bc5d0411cb4d44ae1f58a978f54214a.jpg_Q90.jpg_.webp', 75000, 370000, 100, 4, 'R'),
	(154, 1, 22, b'0', 10000, 'Imperial Dragon Ignition\'', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/445013929_1165117031287214_533324388854201610_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeFP7g27y1NDC--uiJSoUcQoqwdvwYbNHQerB2_Bhs0dB4nmdyjjXefUFigOKO8V3ccYt-f7_FMkZdqSNuCHDyzf&_nc_ohc=QrbRd4PgStYQ7kNvgEe8iJe&_nc_ht=scontent.fhan18-1.fna&oh=00_AYB41IrHH7JAEaqcYlvq4Bxz75eRRG7DtHWQrUohPxprmw&oe=665F9290', 60000, 200000, 12, 4, 'R'),
	(155, 4, 33, b'0', 10000, 'Master Diabolos Generate', 'https://beybladesandmore.com/cdn/shop/products/17807_1200x1200.jpg?v=1655085430', 50000, 250000, 33, 4, 'LR'),
	(157, 2, 22, b'1', 13300, 'Bigbang Genesis 0 Yard Metal', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/444752569_1165117237953860_8763869421341168552_n.jpg?stp=dst-jpg_p75x225&_nc_cat=100&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeEPL9Vjyw2m0b1MiEdwZgd9qRwkWDzF2v-pHCRYPMXa_3CakQx-XFURVHtGAc-iCdmqj6b9uR01uuyYzZwAp3WO&_nc_ohc=A_nIfyVJ5i0Q7kNvgF0Ff9e&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCZCvi19VD3NydXk2QFk86z8I33qa3u5ip1OURk-zjhDg&oe=665FBB28', 111111, 520000, 50, 4, 'R'),
	(159, 1, 1, b'0', 13000, 'Super Hyperion Xceed 1A', 'https://bglhobbies.com.au/cdn/shop/products/beyblade-burst-superking-b-159-booster-super-hyperionxc-1a-beyblade-takara-tomy-139236_grande.jpg?v=1658441617', 80000, 250000, 33, 5, 'R'),
	(160, 4, 1, b'0', 10000, 'King Helios Zone 1B', 'https://www.mykingdom.com.vn/cdn/shop/products/b-160-157199-3_f36bb42b-36ae-4f76-aff4-5a7ac35ba7c5.jpg?v=1707002694&width=1445', 50000, 250000, 15, 5, 'L'),
	(161, 3, 20, b'0', 11000, 'Glide Ragnaruk Wheel Revolve 1S', 'https://www.mykingdom.com.vn/cdn/shop/products/b-161-157205-3_80b6c96a-cddd-4469-8987-b7915a9ece15.jpg?v=1707002707&width=1445', 10000, 500000, 15, 5, 'R'),
	(162, 1, 40, b'1', 14000, 'RED Brave Valkyrie .Ev\' 2A', 'https://i.redd.it/egvkliymvlg71.jpg', 153456, 400000, 22, 5, 'R'),
	(163, 1, 20, b'1', 14000, 'Brave Valkyrie .Ev\' 2A', 'https://bizweb.dktcdn.net/100/405/289/products/48f0384f2fcec1362864e00aabeef735.jpg?v=1660831144697', 153456, 400000, 50, 5, 'R'),
	(164, 2, 32, b'0', 13000, 'Curse Satan Hurricane Universe 1D', 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/4gHYSUNDX1BST0ZJTEUAAQEAAAHIAAAAAAQwAABtbnRyUkdCIFhZWiAH4AABAAEAAAAAAABhY3NwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAA9tYAAQAAAADTLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlkZXNj', 50000, 400000, 15, 5, 'R'),
	(167, 3, 23, b'0', 12444, 'Mirage Fafnir Nothing 2S ', 'https://m.media-amazon.com/images/I/71KjSSncnWL._AC_UF1000,1000_QL80_.jpg', 44444, 500000, 15, 5, 'L'),
	(168, 1, 1, b'1', 13000, 'Rage Longinus Destroy\' 3A', 'https://yeepvn.sgp1.digitaloceanspaces.com/2022/04/48806-dd903c10728dfe516f9c28b8ff596935.jpeg', 100000, 550000, 60, 5, 'L'),
	(169, 2, 25, b'1', 23000, 'Variant Lucifer Mobius 2D', 'https://images-na.ssl-images-amazon.com/images/I/71usbdoq2lL.jpg', 30000, 1000000, 15, 5, 'R'),
	(170, 4, 12, b'1', 15000, 'Death Diabolos 4Turn Merge\' 1D', 'https://www.beyburst.com/cdn/shop/products/Beyburst-B170-01-Death-Diabolos_2.jpg?v=1644494225', 75000, 775000, 15, 5, 'LR'),
	(171, 1, 23, b'0', 17000, 'Tempest Dragon Charge Metal 1A', 'https://img.ws.mms.shopee.com.my/2ecd8d105522fb41e35d4ebef7346933', 75000, 475000, 15, 5, 'R'),
	(172, 4, 33, b'0', 15000, 'World Spriggan Unite\' 2B', 'https://img.lazcdn.com/g/p/67dd71a4ca5de5a8c1d6cff5d255a028.jpg_960x960q80.jpg_.webp', 75000, 375000, 15, 5, 'LR'),
	(173, 4, 1, b'1', 14000, 'Infinite Achilles Dimension\' 1B', 'https://cdn11.bigcommerce.com/s-iodt3qca/images/stencil/1280x1280/products/1104/3282/b-173-01__68458.1604535356.jpg?c=2', 85000, 775000, 30, 5, 'R'),
	(174, 1, 70, b'1', 11000, 'Sức Mạnh Song Sinh ', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/444770040_1165117394620511_6909976299634052548_n.jpg?stp=dst-jpg_p75x225&_nc_cat=107&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeF9CfR7kpCMUoJ-AjPeHiJNmV-pjZKTrs2ZX6mNkpOuzf-8HXZ-oFhopO1OwHhb3JjiAVX2zj2GXPqYbgARUX7M&_nc_ohc=DtAh2Hww7-EQ7kNvgEOa1QX&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBXWuGszS4-Q290LoGzlFg5r7zl9nhCdsB54FF0oOpHtA&oe=665FBDA7', 500000, 100000, 70, 5, 'LR'),
	(175, 2, 50, b'1', 6500, 'Lucifer The End Kou Drift', 'https://down-vn.img.susercontent.com/file/ea98861ce3acb553246f118255b84b06', 15000, 1250000, 15, 5, 'R'),
	(176, 1, 50, b'1', 9000, 'Hollow Deathscyther 12Axe High Accel\' 4A', 'https://ae01.alicdn.com/kf/H26140a8c6dcb4eebacb6cde150803417a/B-X-TOUPIE-BURST-BEYBLADE-Spinning-Top-Limit-Break-DX-set-W-Box-B174-B-174.jpg', 300000, 300000, 70, 5, 'R'),
	(177, 2, 50, b'0', 9000, 'Jet Wyvern Around Just 1D', 'https://ae01.alicdn.com/kf/Hf7efbea5f33744e994d37594ebfd0635l/Genuine-TAKARA-TOMY-BEYBLADE-Burst-Gyro-Super-King-B177-Jet-Flying-Dragon-Battle-Toys.jpg', 15000, 375000, 15, 5, 'R'),
	(179, 4, 25, b'0', 10000, 'Death Solomon MF 2B', 'https://i.ebayimg.com/images/g/twgAAOSw9KhjVP7v/s-l400.jpg', 50000, 250000, 25, 5, 'R'),
	(180, 1, 25, b'0', 13000, 'Dynamite Belial Nexus Venture-2', 'https://i.ebayimg.com/images/g/3-AAAOSwF7BhIwNN/s-l1200.webp', 50000, 400000, 33, 6, 'R'),
	(181, 3, 20, b'1', 30000, 'Cyclone Ragnaruk Giga Never-6', 'https://down-vn.img.susercontent.com/file/dae3bbfa75ec4e2b447d47cec60e4c37', 12000, 750000, 18, 6, 'R'),
	(184, 1, 60, b'1', 30000, 'RED Savior Valkyrie Shot-7', 'https://pbs.twimg.com/media/E39FKxVWYAY0a8q.jpg', 199000, 595000, 33, 6, 'R'),
	(185, 3, 30, b'1', 30000, ' Vanish Fafnir.Tp.Kc-3', 'https://down-vn.img.susercontent.com/file/4ff89aed2884157260a1884a9b3433a0', 50000, 700000, 20, 6, 'L'),
	(186, 2, 80, b'1', 30000, 'Roar Bahamut Giga Moment-10 ', 'https://ae01.alicdn.com/kf/Hbc0b1bc2b16344a68e94ab86f49a1430O/GENUINE-Takara-Tomy-Burst-Dynamite-Random-Booster-Vol-26-Beyblade-B-186-B-194-random-1.jpg', 30000, 850000, 10, 6, 'L'),
	(187, 1, 50, b'1', 30000, 'Savior Valkyrie Shot-7', 'https://i.ebayimg.com/images/g/gT0AAOSwcPNg7774/s-l1200.jpg', 300000, 600000, 50, 6, 'R'),
	(188, 4, 30, b'1', 30000, 'Astral Spriggan Over Quattro-0', 'https://images-na.ssl-images-amazon.com/images/I/71H8Fq2w5XS.jpg', 299000, 495000, 30, 6, 'LR'),
	(189, 1, 30, b'1', 22000, 'Guilty Longinus Karma Metal Destroy-2', 'https://images-cdn.ubuy.co.in/635b867191f5134064149b62-beyblade-burst-b-189-booster-guilty.jpg', 250000, 650000, 70, 6, 'L'),
	(190, 2, 33, b'0', 18000, 'Promience Phoneix.Tp.MU -10', 'https://laz-img-sg.alicdn.com/p/e7499132f71f83fe37d24f2b0ee05ca8.jpg', 50000, 650000, 18, 6, 'R'),
	(191, 1, 1, b'0', 18000, 'Dangerous Belial Almight -2', 'https://http2.mlstatic.com/D_NQ_NP_858347-MLU73331820766_122023-O.webp', 200000, 450000, 33, 6, 'R'),
	(192, 4, 50, b'1', 30000, 'Greatest Raphael.Ov.HXt+\' ', 'https://m.media-amazon.com/images/I/71tBIgCnzZL._AC_SL1100_.jpg', 50000, 1000000, 50, 6, 'R'),
	(193, 1, 25, b'1', 30000, 'Ultimate Valkyrie.Lg.V’-9', 'https://beyblade-shop.com/cdn/shop/products/Ultimate-Valkyrie-Legacy-Variable-9-Beyblade-Shop_1200x1200.jpg?v=1636925530', 200000, 650000, 33, 6, 'R'),
	(194, 1, 1, b'1', 30000, 'Dynamite Belial full gear', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/444770734_1165117521287165_6391701974813738612_n.jpg?stp=dst-jpg_p75x225&_nc_cat=110&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeHQBGD-eehBabIE201uU2cJajQ35jqWh6JqNDfmOpaHokz1nL2qwMLn8UKHxua6subyouM1_UI3XrpYJDAloHtC&_nc_ohc=uoHuTE7Am-QQ7kNvgFDYe-L&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBhxa7y1wafhuUS4JFurph1HpCg21O5C7MvNtGr4zqbiQ&oe=665FB409', 50000, 1000000, 99, 6, 'R'),
	(195, 2, 1, b'1', 30000, 'Prominence Valkyrie.Ov.At’-0', 'https://i5.walmartimages.com/asr/c1aa5022-0eeb-4d06-8fee-1e90b0b2ac66.a0dd10e10e0063f0e76e49ac1fa16131.jpeg?odnHeight=768&odnWidth=768&odnBg=FFFFFF', 70000, 1200000, 18, 6, 'R'),
	(197, 1, 1, b'0', 3800, 'Divine Belial Nexus Adventure-3', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/441901861_1165117657953818_6926253256143907901_n.jpg?stp=dst-jpg_p75x225&_nc_cat=105&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeGNt_hFyn6XQhLqlu43LC64oFzgLXSRjlOgXOAtdJGOU1Awz1irFhtTpDTG7VdvxaiUX_lg7nFASfb3aPEqiIhN&_nc_ohc=wU8heFfYqowQ7kNvgEgHyXb&_nc_ht=scontent.fhan18-1.fna&oh=00_AYBbQmhi4o7EFYN7YPFtbslNWSrR0g7kYv5gw6cVGwbbSg&oe=665F909C', 28000, 50000, 21, 7, 'R'),
	(198, 2, 1, b'1', 30000, 'Chain Kerbeus Fortress Yard\'-6', 'https://down-vn.img.susercontent.com/file/48be0fdd51d7d7c180f7ffe9ef2a2d78', 100000, 800000, 21, 7, 'R'),
	(199, 1, 22, b'1', 30000, 'Gatling Dragon Karma Charge Metal\'-10', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/446679940_1165117844620466_2485560388968917407_n.jpg?stp=dst-jpg_p75x225&_nc_cat=107&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeG7O-Gzs83u8FZrQBbSCvDVYVrR6MkAEkdhWtHoyQASR38MM7Y-Vr0bZNqGo40iQablSD_zP-AsKd8JPD88ipXy&_nc_ohc=y4AiBxS6uJUQ7kNvgGcbPpb&_nc_ht=scontent.fhan18-1.fna&oh=00_AYAMhwgHU3Q7rJNamHoEpYq6lA9t3Ppj6Pf9HiQXLkyrbQ&oe=665FA8DF', 10000, 1000000, 21, 7, 'R'),
	(200, 1, 33, b'0', 21000, 'Xiphoid Xcalibur Xanthus Sword\'-1 ', 'https://scontent.fhan18-1.fna.fbcdn.net/v/t39.30808-6/441520380_1165117974620453_8644172113346224456_n.jpg?_nc_cat=102&ccb=1-7&_nc_sid=5f2048&_nc_eui2=AeF4wMcVo30Jzx9M-Gizin-nPM-bxGkEGx88z5vEaQQbH8WXsUju-QO0S-qTKSv9j_sozRFMQFToq0tiu5cBWGAl&_nc_ohc=xItaBCu6P0YQ7kNvgE7SmqG&_nc_ht=scontent.fhan18-1.fna&oh=00_AYCSIl35SF-hlulsVHaJdsKh3-XWHEZ6lmOS60JivlAyyQ&oe=665FAB2E', 200000, 50000, 50, 7, 'R'),
	(201, 4, 1, b'0', 28000, 'Zest Achilles Illegal Quattro\'-4', 'https://i.ebayimg.com/images/g/wpUAAOSwxXxi9H8P/s-l1200.webp', 140000, 700000, 21, 7, 'R'),
	(202, 3, 22, b'1', 35000, 'Wind Knight Moon Bounce-6', 'https://i.ebayimg.com/images/g/DD4AAOSwHVNjIwi2/s-l1200.webp', 14000, 1500000, 21, 7, 'R'),
	(203, 1, 1, b'0', 24000, 'Super Hyperion MR', 'https://wafuu.com/cdn/shop/products/takara-tomy-beyblade-burst-b-203-ultimate-combine-dx-set-111634_1200x1200.jpg?v=1695256821', 140000, 500000, 21, 7, 'R'),
	(204, 4, 1, b'0', 18000, 'King Helios MR', 'https://wafuu.com/cdn/shop/products/takara-tomy-beyblade-burst-b-203-ultimate-combine-dx-set-606832_540x.jpg?v=1695256822', 90000, 450000, 21, 7, 'L'),
	(205, 4, 1, b'1', 30000, 'Burst Spriggan Spread\' Fusion\'-8', 'https://i.ebayimg.com/images/g/W8gAAOSwf-pjdFSh/s-l1200.jpg', 120000, 600000, 21, 7, 'LR'),
	(206, 2, 22, b'1', 40000, 'Badicade Lucifer.Il.BMb-10', 'https://m.media-amazon.com/images/I/71WgKP-cPSL._AC_UF1000,1000_QL80_DpWeblab_.jpg', 21000, 1500000, 21, 7, 'R');

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
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.message: ~8 rows (approximately)
DELETE FROM `message`;
INSERT INTO `message` (`message_id`, `me`, `other`, `text`, `time_chat`, `violate`, `status`, `user_chat`) VALUES
	(296, NULL, NULL, 'a', '2024-05-17 07:34:47.000000', 0, b'0', NULL),
	(297, NULL, NULL, 'âs', '2024-05-17 07:34:49.000000', 0, b'0', NULL),
	(298, NULL, NULL, 'sáa', '2024-05-17 07:34:50.000000', 0, b'0', NULL),
	(299, NULL, NULL, 's', '2024-05-17 07:34:50.000000', 0, b'0', NULL),
	(300, NULL, NULL, 'a', '2024-05-17 07:34:50.000000', 0, b'0', NULL),
	(301, NULL, NULL, 's', '2024-05-17 07:34:51.000000', 0, b'0', NULL),
	(302, NULL, NULL, 'alo', '2024-05-17 07:35:16.000000', 0, b'0', NULL),
	(303, NULL, NULL, 'nè', '2024-05-17 07:35:19.000000', 0, b'0', NULL),
	(304, NULL, NULL, 'hai', '2024-05-17 07:36:19.000000', 0, b'0', NULL);

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

-- Dumping structure for table beyblade.prize
CREATE TABLE IF NOT EXISTS `prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.prize: ~10 rows (approximately)
DELETE FROM `prize`;
INSERT INTO `prize` (`id`, `name`, `type`, `img`) VALUES
	(1, 'Vé Đổi Driver Ngẫu Nhiên 2nd', 1, 'https://i.pinimg.com/originals/a4/b1/cc/a4b1ccc515ff9547ae0260167c7f0797.gif'),
	(2, 'Vé Đổi Layer Ngẫu Nhiên 2hand', 1, 'https://i.pinimg.com/originals/a4/b1/cc/a4b1ccc515ff9547ae0260167c7f0797.gif'),
	(3, 'Vé Đổi Beyblade Ngẫu Nhiên 2hand', 1, 'https://i.pinimg.com/originals/a4/b1/cc/a4b1ccc515ff9547ae0260167c7f0797.gif'),
	(4, 'Hộp Quà Điểm Danh', 2, 'https://gifdb.com/images/high/bouncing-gift-box-o19ltvr8ldanpz66.gif'),
	(5, 'Hộp Quà VIP', 2, 'https://www.happyfruits.vn/themes/efruit/assets/img/gift.gif'),
	(6, 'Vé Tham Dự Giải Thưởng', 1, 'https://i.pinimg.com/originals/a4/b1/cc/a4b1ccc515ff9547ae0260167c7f0797.gif'),
	(7, 'Vé Tham Dự Giải THưởng VIP', 1, 'https://i.pinimg.com/originals/a4/b1/cc/a4b1ccc515ff9547ae0260167c7f0797.gif'),
	(8, 'Vé Đổi Base GT ngẫu nhiên 2nd', 1, 'https://i.pinimg.com/originals/a4/b1/cc/a4b1ccc515ff9547ae0260167c7f0797.gif'),
	(9, 'Vé Đổi Ring Superking ngẫu nhiên 2nd', 1, 'https://i.pinimg.com/originals/a4/b1/cc/a4b1ccc515ff9547ae0260167c7f0797.gif'),
	(10, 'Vé Đổi Blade DB/BU Ngẫu Nhiên 2nd', 1, 'https://i.pinimg.com/originals/a4/b1/cc/a4b1ccc515ff9547ae0260167c7f0797.gif'),
	(11, 'Hộp Quà Săn Boss', 2, 'https://heattransferstore.com/cdn/shop/products/mystery-box-775562_1200x1200_crop_center.gif?v=1706739175'),
	(12, 'Hộp Quà Shopee', 2, 'https://media2.giphy.com/media/YKao4rRQrTkUgfitld/giphy.gif');

-- Dumping structure for table beyblade.prize_cua_toi
CREATE TABLE IF NOT EXISTS `prize_cua_toi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `prize_id` int(11) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `time_save` timestamp NULL DEFAULT NULL,
  `soluong` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`),
  KEY `prize_id` (`prize_id`) USING BTREE,
  CONSTRAINT `FKhne86scg6be3ubglhpesb56sc` FOREIGN KEY (`prize_id`) REFERENCES `prize` (`id`),
  CONSTRAINT `prize_cua_toi_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.prize_cua_toi: ~12 rows (approximately)
DELETE FROM `prize_cua_toi`;
INSERT INTO `prize_cua_toi` (`id`, `user_id`, `prize_id`, `status`, `time_save`, `soluong`) VALUES
	(47, 30, 1, b'0', '2024-05-31 12:19:37', 13),
	(48, 30, 2, b'0', '2024-05-31 00:06:45', 3),
	(49, 30, 3, b'0', '2024-05-29 22:55:31', 2),
	(50, 30, 4, b'0', '2024-05-29 12:46:03', 0),
	(51, 30, 5, b'0', '2024-05-31 12:19:38', 88),
	(52, 30, 4, b'0', '2024-05-29 22:53:08', 0),
	(53, 30, 5, b'0', '2024-05-29 22:55:37', 0),
	(54, 32, 5, b'0', '2024-05-31 12:19:06', 57),
	(55, 32, 4, b'0', '2024-05-31 00:06:43', 0),
	(56, 32, 4, b'0', '2024-05-31 04:00:56', 0),
	(57, 32, 6, b'0', '2024-05-31 12:19:25', 1),
	(58, 32, 12, b'0', '2024-05-31 12:24:53', 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=armscii8 COLLATE=armscii8_bin;

-- Dumping data for table beyblade.token: ~2 rows (approximately)
DELETE FROM `token`;
INSERT INTO `token` (`id`, `create_time`, `update_time`, `code`, `expiry`, `account_id`) VALUES
	(155, '2024-05-24 15:26:16.000000', '2024-05-24 15:26:16.000000', '97b7e6dc-806e-4e91-b1de-51081057d29a', '2024-05-25 15:26:16.000000', 29),
	(158, '2024-05-30 22:52:31.000000', '2024-05-30 22:52:31.000000', '911afeb2-8471-4e94-819e-032aaa233820', '2024-05-31 22:52:31.000000', 32);

-- Dumping structure for table beyblade.top
CREATE TABLE IF NOT EXISTS `top` (
  `rank` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `select_bey` int(11) DEFAULT NULL,
  `buff` tinyint(4) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `end_buff` datetime DEFAULT NULL,
  `win` smallint(6) DEFAULT NULL,
  `lost` smallint(6) DEFAULT NULL,
  `top` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`rank`) USING BTREE,
  KEY `FKr6fqgo0wrfkbhlvd7rkn81jq7` (`user`),
  KEY `FK_top_list_bey` (`select_bey`),
  CONSTRAINT `FK_top_list_bey` FOREIGN KEY (`select_bey`) REFERENCES `list_bey` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKr6fqgo0wrfkbhlvd7rkn81jq7` FOREIGN KEY (`user`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.top: ~4 rows (approximately)
DELETE FROM `top`;
INSERT INTO `top` (`rank`, `user`, `select_bey`, `buff`, `created_at`, `end_buff`, `win`, `lost`, `top`) VALUES
	(2, 30, 1, 0, '2024-05-22 21:36:33', '2024-05-31 18:25:37', 49, 35, 4),
	(3, 29, 1, 0, '2024-05-22 21:41:30', '2024-05-22 21:41:30', 2, 3, 1),
	(4, 31, 1, 0, '2024-05-22 21:41:58', '2024-05-22 21:41:58', 3, 3, 3),
	(5, 32, 174, 5, '2024-05-30 22:05:00', '2024-06-01 07:17:34', 26, 0, 13);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `diem` int(11) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `diemdanh` bit(1) DEFAULT NULL,
  `diemdanhvip` bit(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK_users_account` (`account_id`),
  CONSTRAINT `FK_users_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.users: ~3 rows (approximately)
DELETE FROM `users`;
INSERT INTO `users` (`user_id`, `username`, `account_id`, `avatar`, `created_at`, `rank`, `ma_xac_nhan`, `diem`, `active`, `diemdanh`, `diemdanhvip`) VALUES
	(29, 'admin', 30, 'bau.jpeg', '2024-05-13 08:53:14', 0, '94947588-d885-401d-82e0-7f1545ce82d9', 0, b'0', b'0', b'0'),
	(30, 'admin1', 31, '646c083786a04be2b16ec1028ee8b3e3.png', '2024-05-13 15:06:32', 0, 'fe985ee1-ff37-4833-b65e-789f1bf48551', 0, b'0', b'0', b'0'),
	(31, 'haia', 32, 'user_avatar.png', '2024-05-17 07:36:01', 0, '71512816-2bc4-4e59-995b-0d81ca6f6c70', 0, b'0', b'0', b'0'),
	(32, 'test', 33, 'user_avatar.png', '2024-05-30 22:04:54', 0, 'd1124d7e-e9bd-4039-b93c-65ae333e84ca', 101, b'1', b'0', b'1');

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
) ENGINE=InnoDB AUTO_INCREMENT=315 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.users_info: ~0 rows (approximately)
DELETE FROM `users_info`;
INSERT INTO `users_info` (`id`, `user_id`, `fullname`, `email`, `phone`, `gender`, `date`, `updated_time`, `shopee_name`) VALUES
	(312, 29, 'Nguyễn Đình Hải', 'beyblade@cc.cc', '201212121', 'Nam', '2024-05-16 00:00:00', '2024-05-13 08:00:55', 'shopee'),
	(313, 30, 'Bảo Đẹp Trai', 'thebao2004@gmail.com', '808444534', 'Nam', '2004-01-13 00:00:00', '2024-05-27 13:38:46', 'concac'),
	(314, 32, 'Họ Và Tên', 'beyblade@gmail.com', '01234556789', 'Khác', '2024-05-30 15:52:50', '2024-05-30 15:52:50', 'Nhập Tên Shopee của bạn (nếu có)');

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
  CONSTRAINT `FK_vip_by_user_vip_package` FOREIGN KEY (`vip_id`) REFERENCES `giftcode` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table beyblade.vip_by_user: ~0 rows (approximately)
DELETE FROM `vip_by_user`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
