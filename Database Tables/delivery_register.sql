-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2021 at 02:53 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `labspot`
--

-- --------------------------------------------------------

--
-- Table structure for table `delivery_register`
--

CREATE TABLE `delivery_register` (
  `id` int(5) NOT NULL,
  `delivery_name` varchar(50) NOT NULL,
  `delivery_address` varchar(450) NOT NULL,
  `delivery_city` varchar(50) NOT NULL,
  `delivery_phone` varchar(15) NOT NULL,
  `delivery_dob` varchar(20) NOT NULL,
  `delivery_email` varchar(50) NOT NULL,
  `delivery_password` varchar(50) NOT NULL,
  `delivery_image_name` varchar(50) NOT NULL,
  `delivery_image_url` varchar(250) NOT NULL,
  `delivery_otp` varchar(10) NOT NULL,
  `delivery_status` varchar(15) NOT NULL,
  `delivery_available` varchar(15) NOT NULL,
  `delivery_busy` varchar(15) NOT NULL,
  `delivery_forgot_otp` varchar(10) NOT NULL,
  `delivery_forgot_otp_status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `delivery_register`
--

INSERT INTO `delivery_register` (`id`, `delivery_name`, `delivery_address`, `delivery_city`, `delivery_phone`, `delivery_dob`, `delivery_email`, `delivery_password`, `delivery_image_name`, `delivery_image_url`, `delivery_otp`, `delivery_status`, `delivery_available`, `delivery_busy`, `delivery_forgot_otp`, `delivery_forgot_otp_status`) VALUES
(1, 'Lloyd Scott', 'B - 201, Shiv Shakti Society, Near Mother Dairy, Navrangpura,  Ahmedabad,  Gujarat - 380008', 'Ahmedabad', '7966190201', '13 / 10 / 1978', 'lloydscott13@yopmail.com', '1', 'Lloyd Scott.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Lloyd Scott.jpg', 'null', 'active', 'AVAILABLE', 'NOT BUSY', 'null', 'null'),
(2, 'Daniel Bryan', 'D - 182, Kashivishvanath Society Part II, Near Swasik Cross Road, Isanpur, Ahmedabad, Gujarat - 382443', 'Ahmedabad', '6352066081', '22 / 5 / 1987', 'danielbryan@yopmail.com', 'Daniel@123', 'Daniel Bryan.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Daniel Bryan.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(3, 'Matt Hardy', '21/3, Rajeswari Society, Near Judges Bunglow, Prahlad Nagar, Ahmedabad, Gujarat - 382443', 'Ahmedabad', '7926441933', '23 / 9 / 1995', 'matthardy23@yopmail.com', 'Matt@123', 'Mat Hardy.jpeg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Mat Hardy.jpeg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(4, 'Dolph Ziggler', 'A - 9, Nahar Colony, Near Chandrashekhar Azad Marg, Narotam Nagar, Surat, Gujarat - 395009', 'Surat', '2612409092', '27 / 7 / 1980', 'dolphziggler@yopmail.com', 'Dolph@123', 'Dolph Ziggler.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Dolph Ziggler.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(5, 'Shawn Michael', '116, Krishna Nagar Society, Choksi Wadi, Surat, Gujarat - 395009', 'Surat', '7567181230', '10 / 10 / 1984', 'shawnmichael10@yopmail.com', 'Shawn@123', 'Shawn Michaels.jpeg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Shawn Michaels.jpeg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(6, 'John Morrison', '271, Gomtinagar Society, Morarji Nagar, Rander, Surat, Gujarat - 395005', 'Surat', '7698359451', '15 / 1 / 1990', 'johnmorrison15@yopmail.com', 'John@123', 'John Morrison.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/John Morrison.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(7, 'Rick Barry', 'A - 308, Param Crest Apartment,Subhanpura Main Road, Vadodara,Gujarat - 390023', 'Vadodara', '7486960917', '21 / 1 / 1995', 'rickbarry21@yopmail.com', 'Rick@123', 'Rick Barry.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Rick Barry.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(8, 'Jimmy Adams', '20, Gokul Society, Lalbaug, Manjalpur, Vadodara, Gujarat - 390004', 'Vadodara', '7096865292', '12 / 12 / 1985', 'jimmyadams@yopmail.com', 'Jimmy@123', 'Jimmy Adam.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Jimmy Adam.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(9, 'Johnny Depp', 'A-5 , Krishna Society, Paris Nagar, Diwalipura, Vadodara, Gujarat - 390007', 'Vadodara', '7359669706', '19 / 6 / 1992', 'johnnydepp21@yopmail.com', 'Johnny@123', 'Johnny Depp.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Johnny Depp.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(10, 'Mick Jones', 'B-4 , Kedarnath Society, Jangleshwar, Rajkot, Gujarat - 360002', 'Rajkot', '6352066081', '3 / 3 / 1996', 'mickjones@yopmail.com', 'Mick@123', 'Mick Jones.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Mick Jones.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(11, 'Bray Wyatt', '6-67, Hari Ghawa Rd, Wodiyar Society, Murari Nagar, Rajkot, Gujarat - 360002', 'Rajkot', '7016872073', '7 / 5 / 1980', 'braywyatt@yopmail.com', 'Bray@123', 'Bray Wyatt.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Bray Wyatt.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(12, 'Finn Balor', '21/2 , New Sagar Society, Jangleshwar, Rajkot, Gujarat - 360002', 'Rajkot', '7985399677', '27 / 4 / 1989', 'finnbalor27@yopmail.com', 'Finn@123', 'Finn Balor.jpeg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Finn Balor.jpeg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(13, 'William Regal', '201,GH Road, Sector-12, Gandhinagar, Gujarat - 382016', 'Gandhinagar', '7778058546', '17 / 11 / 1993', 'williamregal@yopmail.com', 'William@123', 'William Regal.jpeg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/William Regal.jpeg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(14, 'Chris Jericho', '22 no. block , Zaverchand Meghani Cir, Sector 7, Gandhinagar, Gujarat - 382007', 'Gandhinagar', '9687806011', '8 / 11 / 1988', 'chrisjericho11@yopmail.com', 'Chris@123', 'Chris Jericho.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Chris Jericho.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null'),
(15, 'Seth Rollins', '6/C, sector 6 Gandhinagar, Gujarat - 382006', 'Gandhinagar', '8200703723', '29 / 1 / 1997', 'sethrollins30@yopmail.com', 'Seth@123', 'Seth Rollins.jpg', 'http://192.168.0.5/labspot_image_server/delivery_profile_picture/Seth Rollins.jpg', 'null', 'active', 'NOT AVAILABLE', 'NOT BUSY', 'null', 'null');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `delivery_register`
--
ALTER TABLE `delivery_register`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `delivery_register`
--
ALTER TABLE `delivery_register`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
