-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2021 at 02:54 PM
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
-- Table structure for table `user_register`
--

CREATE TABLE `user_register` (
  `id` int(5) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `user_address` varchar(450) NOT NULL,
  `user_city` varchar(50) NOT NULL,
  `user_phone` varchar(15) NOT NULL,
  `user_dob` varchar(20) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `user_image_name` varchar(50) NOT NULL,
  `user_image_url` varchar(250) NOT NULL,
  `user_otp` varchar(10) NOT NULL,
  `user_status` varchar(15) NOT NULL,
  `user_forgot_otp` varchar(10) NOT NULL,
  `user_forgot_otp_status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_register`
--

INSERT INTO `user_register` (`id`, `user_name`, `user_address`, `user_city`, `user_phone`, `user_dob`, `user_email`, `user_password`, `user_image_name`, `user_image_url`, `user_otp`, `user_status`, `user_forgot_otp`, `user_forgot_otp_status`) VALUES
(1, 'Amy Jackson', 'A/202, Surya Mandir Flates, Near Akash Tower, Bodakdev, Ahmedabad, Gujarat - 380015', 'Ahmedabad', '9727510766', '31 / 1 / 1992', 'amyjackson@yopmail.com', 'Amy@123', 'Amy Jackson.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Amy Jackson.jpg', 'null', 'active', 'null', 'null'),
(2, 'Cristiano Ronaldo', 'A/504, Pravah Flates, Near Mother Dairy, Bodakdev, Ahmedabad , Gujarat- 380027', 'Ahmedabad', '9428739212', '5 / 2 / 1985', 'cristianoronaldo07@yopmail.com', 'Cristiano@123', 'Cristiano Ronaldo.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Cristiano Ronaldo.jpg', 'null', 'active', 'null', 'null'),
(3, 'Adam Gilchrist', '1, Panna Park Society, Near Hanuman ji Temple, Navrangpura, Ahmedabad, Gujarat - 380009', 'Ahmedabad', '7283947790', '14 / 11 / 1971', 'adamgilchrist@yopmail.com', 'Adam@123', 'Adam Gilchrist.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Adam Gilchrist.jpg', 'null', 'active', 'null', 'null'),
(4, 'Eoin Morgan', 'A - 201, Sompad Row house, Vavol, Gandhinagar, Gujarat - 382016', 'Gandhinagar', '8469741908', '10 / 9 / 1986', 'eoinmorgan@yopmail.com', 'Eoin@123', 'Eoin Morgan.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Eoin Morgan.jpg', 'null', 'active', 'null', 'null'),
(5, 'Scarlett Johansson', 'A/609, Avni Row house, Sector 28 GIDC, Sector 28, Gandhinagar, Gujarat - 382041', 'Gandhinagar', '9106755871', '22 / 11 / 1984', 'scarlettjohansson22@yopmail.com', 'Scarlett@123', 'Scarlett Johansson.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Scarlett Johansson.jpg', 'null', 'active', 'null', 'null'),
(6, 'Ricky Ponting', '24/2, Kishan Nagar, Sector 26, Gandhinagar, Gujarat - 382028', 'Gandhinagar', '9714336112', '19 / 12 / 1974', 'rickyponting19@yopmail.com', 'Ricky@123', 'Ricky Ponting.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Ricky Ponting.jpg', 'null', 'active', 'null', 'null'),
(7, 'David Miller', '5, Swaminarayan Park, Jangleshwar, Rajkot, Gujarat - 360002', 'Rajkot', '9925588326', '6 / 6 / 1989', 'davidmiller10@yopmail.com', 'David@123', 'David Miller.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/David Miller.jpg', 'null', 'active', 'null', 'null'),
(8, 'Shane Watson', 'Street No. 4, Jasharaj Nagar 4, Mavdi, Rajkot, Gujarat - 360004', 'Rajkot', '9558638493', '17 / 6 / 1981', 'shanewatson17@yopmail.com', 'Shane@123', 'Shane Watson.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Shane Watson.jpg', 'null', 'active', 'null', 'null'),
(9, 'Kane Williamson', 'D - 301,Sadhana Society, Bhakti Nagar, Rajkot, Gujarat - 360002', 'Rajkot', '9724408140', '8 / 8 / 1990', 'kanewilliamson8@yopmail.com', 'Kane@123', 'Kane Williamson.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Kane Williamson.jpg', 'null', 'active', 'null', 'null'),
(10, 'Jos Buttler', 'C - 201, Surya Row house, Opp. Yoginagar Tower, Chhani, Vadodara, Gujarat - 391740', 'Vadodara', '7096865292', '8 / 9 / 1990', 'josbuttler@yopmail.com', 'Jos@123', 'Jos Buttler.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Jos Buttler.jpg', 'null', 'active', 'null', 'null'),
(11, 'Chris Morris', 'A/101, Gokul Society, Near Lalbaug Road, Manjalpur, Vadodara, Gujarat - 390004', 'Vadodara', '9773083835', '30 / 4 / 1987', 'chrismorris30@yopmail.com', 'Chris@123', 'Chris Morris.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Chris Morris.jpg', 'null', 'active', 'null', 'null'),
(12, 'Joe Root', 'A-12, Tirupati Nagar Society, Manjalpur, Vadodara, Gujarat - 390011', 'Vadodara', '7698359451', '30 / 12 / 1990', 'joeroot30@yopmail.com', 'Joe@123', 'Joe Root.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Joe Root.jpg', 'null', 'active', 'null', 'null'),
(13, 'Martin Guptill', '100, L N Park Society, Bhatena, Surat, Gujarat - 395002', 'Surat', '7984399677', '30 / 9 / 1986', 'martinguptill@yopmail.com', 'Martin@123', 'Martin Guptill.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Martin Guptill.jpg', 'null', 'active', 'null', 'null'),
(14, 'Colin Munro', 'B - 31, Aman Society, Bhatena, Surat, Gujarat - 395002', 'Surat', '7778058546', '11 / 3 / 1987', 'colinmunro11@yopmail.com', 'Colin@123', 'Colin Munro.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Colin Munro.jpg', 'null', 'active', 'null', 'null'),
(15, 'Emma watson', 'A - 21,Gayatri Society - II, Udhana Village, Udhna, Surat, Gujarat - 394210', 'Surat', '9687806011', '15 / 4 / 1990', 'emmawatson15@yopmail.com', 'Emma@123', 'Emma Watson.jpg', 'http://192.168.0.5/labspot_image_server/user_profile_picture/Emma Watson.jpg', 'null', 'active', 'null', 'null');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `user_register`
--
ALTER TABLE `user_register`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user_register`
--
ALTER TABLE `user_register`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
