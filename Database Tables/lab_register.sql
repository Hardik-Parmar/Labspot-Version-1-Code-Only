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
-- Table structure for table `lab_register`
--

CREATE TABLE `lab_register` (
  `id` int(5) NOT NULL,
  `lab_name` varchar(100) NOT NULL,
  `lab_address` varchar(450) NOT NULL,
  `lab_city` varchar(50) NOT NULL,
  `lab_phone` varchar(15) NOT NULL,
  `lab_category` varchar(30) NOT NULL,
  `lab_established_year` varchar(5) NOT NULL,
  `lab_email` varchar(50) NOT NULL,
  `lab_password` varchar(50) NOT NULL,
  `lab_logo_name` varchar(50) NOT NULL,
  `lab_logo_url` varchar(250) NOT NULL,
  `lab_otp` varchar(10) NOT NULL,
  `lab_status` varchar(15) NOT NULL,
  `lab_forgot_otp` varchar(10) NOT NULL,
  `lab_forgot_otp_status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `lab_register`
--

INSERT INTO `lab_register` (`id`, `lab_name`, `lab_address`, `lab_city`, `lab_phone`, `lab_category`, `lab_established_year`, `lab_email`, `lab_password`, `lab_logo_name`, `lab_logo_url`, `lab_otp`, `lab_status`, `lab_forgot_otp`, `lab_forgot_otp_status`) VALUES
(1, 'Accurate Laboratory', 'G - 205, Manibhadra Arcade, opposite Rajasthan Hospital Gate, Shahibag, Ahmedabad, Gujarat - 380004', 'Ahmedabad', '9033044571', 'Food Testing', '2002', 'accuratelab@yopmail.com', 'Accurate@123', 'Accurate Lab.jpeg', 'http://192.168.0.5/labspot_image_server/lab_logo/Accurate Lab.jpeg', 'null', 'active', 'null', 'null'),
(2, 'Pollucon Laboratories Private Limited', 'G-16, Hera Shopping Complex, Near LP Savani Road, Adajan, Surat, Gujarat - 395009', 'Surat', '2612635750', 'Food Testing', '2005', 'polluconlab@yopmail.com', 'Pollucon@123', 'Pollucon Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Pollucon Lab.jpg', 'null', 'active', 'null', 'null'),
(3, 'Baroda Analytical Services', '102A,Taksh Complex, near ESIC Hospital, Gotri Road, Vadodara, Gujarat - 390021', 'Vadodara', '2652646622', 'Food Testing', '2003', 'barodaanalyticallab@yopmail.com', 'Baroda@123', 'Baroda Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Baroda Lab.jpg', 'null', 'active', 'null', 'null'),
(4, 'Envitro Laboratories Pvt Ltd', 'B - 148, Jasal Complex, Near Nanavati Chawk, Rajkot, Gujarat - 360007', 'Rajkot', '2812366430', 'Food Testing', '2004', 'envitrolab@yopmail.com', 'Envitro@123', 'Envitro Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Envitro Lab.jpg', 'null', 'active', 'null', 'null'),
(5, 'Food research laboratory', 'A - 204, Skyview Complex, Sector 22, Gandhinagar, Gujarat - 382024', 'Gandhinagar', '9727510766', 'Food Testing', '2002', 'foodresearchlab@yopmail.com', 'Food@123', 'Food Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Food Lab.jpg', 'null', 'active', 'null', 'null'),
(6, 'Biocare Research (India) Pvt Ltd', 'G - 17,Sruhad Complex, Near Devi Cinema, Naroda, Ahmedabad, Gujarat - 382330', 'Ahmedabad', '7926577901', 'Water Testing', '2001', 'biocarelab@yopmail.com', 'Biocare@123', 'Biocare Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Biocare Lab.jpg', 'null', 'INactive', 'null', 'null'),
(7, 'Stic Lab', 'U-2, Shakuntal Complex - II, Near Gujarat Gas Circle, Adajan, Surat, Gujarat - 395009', 'Surat', '9825809117', 'Water Testing', '2007', 'sticlab@yopmail.com', 'Stic@123', 'Stic Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Stic Lab.jpg', 'null', 'active', 'null', 'null'),
(8, 'Analytical & Environmental Services', '114, Gajanan Complex, Old Padra Road, Akota, Vadodara, Gujarat - 390015', 'Vadodara', '9825855942', 'Water Testing', '2012', 'analyticalenvironmentlab@yopmail.com', 'Analytical@123', 'AES Lab.jpeg', 'http://192.168.0.5/labspot_image_server/lab_logo/AES Lab.jpeg', 'null', 'active', 'null', 'null'),
(9, 'Krishna Water Testing Centre', '105, Garden Commercial Complex, Near Sadhu Vaswani Road, Rajkot, Gujarat - 360008', 'Rajkot', '2812571030', 'Water Testing', '2004', 'krishnawatertestinglab@yopmail.com', 'Krishna@123', 'Krishna Lab.jpeg', 'http://192.168.0.5/labspot_image_server/lab_logo/Krishna Lab.jpeg', 'null', 'active', 'null', 'null'),
(10, 'GEMI\'s Laboratory', '317, Shalin Complex, Near GH - 4 Circle, Sector 16, Gandhinagar, Gujarat - 382016', 'Gandhinagar', '9428739212', 'Water Testing', '2015', 'gemilab@yopmail.com', 'Gemi@123', 'GEMI\'s Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/GEMI\'s Lab.jpg', 'null', 'active', 'null', 'null'),
(11, 'Gujarat Laboratory', 'B - 203, Shivalik Yash Complex, Near Shashtri Nagar BRTS Stand, Naranpura, Ahmedabad, Gujarat - 380013', 'Ahmedabad', '7600069149', 'Pharmacy Testing', '2008', 'gujaratlab@yopmail.com', 'Gujarat@123', 'Gujarat Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Gujarat Lab.jpg', 'null', 'active', 'null', 'null'),
(12, 'Shaimil Laboratories', 'A - 104, Maruthi Complex, Near Nizampura Main Road, Nizampura, Vadodara, Gujarat - 390024', 'Vadodara', '2653098335', 'Pharmacy Testing', '2007', 'shaimillab@yopmail.com', 'Shaimil@123', 'Shaimil Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Shaimil Lab.jpg', 'null', 'active', 'null', 'null'),
(13, 'K.L.M Laboratories Private Limited', '109, Maan Sarovar Complex, Hazira - Sayan Road, Amroli, Surat, Gujarat - 394107', 'Surat', '2612350025', 'Pharmacy Testing', '2004', 'klmlab@yopmail.com', 'Klm@123', 'KLM Lab.jpeg', 'http://192.168.0.5/labspot_image_server/lab_logo/KLM Lab.jpeg', 'null', 'active', 'null', 'null'),
(14, 'Bios Lab Private Limited', 'D - 608,Sahayog Commercial Complex, Near Kanak Road, Rajkot, Gujarat - 360001', 'Rajkot', '2812465065', 'Pharmacy Testing', '2013', 'bioslab@yopmail.com', 'Bios@123', 'Bios Lab.jpeg', 'http://192.168.0.5/labspot_image_server/lab_logo/Bios Lab.jpeg', 'null', 'active', 'null', 'null'),
(15, 'Pharmace Research Laboratory', 'B - 293, Pramukh Plazza, Sector 20, Gandhinagar, Gujarat - 382020', 'Gandhinagar', '9925431004', 'Pharmacy Testing', '2019', 'pharmaceresearchlab@yopmail.com', 'Pharmace@123', 'Pharmace Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Pharmace Lab.jpg', 'null', 'active', 'null', 'null'),
(16, 'Botany Zone', '101, Shree Balaji Complex, Near Shivranjani Road, Satellite, Ahmedabad, Gujarat - 380005', 'Ahmedabad', '7283947790', 'Bootny Testing', '2003', 'botanyzonelab@yopmail.com', 'Botany@123', 'BotanyZone  Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/BotanyZone  Lab.jpg', 'null', 'active', 'null', 'null'),
(17, 'Flores Lab', 'B - 809,Nandanvan Complex, Opposite Nagina Wadi Socieity, Surat, Gujarat - 395006', 'Surat', '7041068208', 'Bootny Testing', '2015', 'floreslab@yopmail.com', 'Flores@123', 'Flores Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Flores Lab.jpg', 'null', 'active', 'null', 'null'),
(18, 'Biacroft Lab', 'F - 8, Maruti Complex, Opp Pragati Bank, Manjalpur, Vadodara, Gujarat - 390011', 'Vadodara', '9016739632', 'Bootny Testing', '2008', 'biacroftlab@yopmail.com', 'Biacroft@123', 'Biacroft Lab.jpg', 'http://192.168.0.5/labspot_image_server/lab_logo/Biacroft Lab.jpg', 'null', 'active', 'null', 'null'),
(19, 'Botany Desk', '107, Sadguru Sanidhya Complex, Shakti Industrial Zone, Rajkot, Gujarat - 360003', 'Rajkot', '9558638493', 'Bootny Testing', '2001', 'botanydesklab@yopmail.com', 'Botany@1234', 'BotanyDesk Lab.jpeg', 'http://192.168.0.5/labspot_image_server/lab_logo/BotanyDesk Lab.jpeg', 'null', 'active', 'null', 'null'),
(20, 'Plants Science  Laboratory', 'A - 204, Shiv Complex, GH Road, Behind ICICI Bank, Sector 16, Gandhinagar, Gujarat - 382016', 'Gandhinagar', '9925588326', 'Bootny Testing', '2005', 'plantsciencelab@yopmail.com', 'Plants@123', 'Plants  Lab.jpeg', 'http://192.168.0.5/labspot_image_server/lab_logo/Plants  Lab.jpeg', 'null', 'active', 'null', 'null');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `lab_register`
--
ALTER TABLE `lab_register`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `lab_register`
--
ALTER TABLE `lab_register`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
