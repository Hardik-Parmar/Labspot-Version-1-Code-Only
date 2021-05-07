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
-- Table structure for table `test_transaction_details`
--

CREATE TABLE `test_transaction_details` (
  `id` int(5) NOT NULL,
  `applicant_name` varchar(50) NOT NULL,
  `applicant_address` varchar(450) NOT NULL,
  `test_transaction_city` varchar(15) NOT NULL,
  `applicant_phone` varchar(15) NOT NULL,
  `applicant_email` varchar(50) NOT NULL,
  `lab_name` varchar(100) NOT NULL,
  `lab_address` varchar(450) NOT NULL,
  `lab_phone` varchar(15) NOT NULL,
  `lab_email` varchar(50) NOT NULL,
  `test_name` varchar(50) NOT NULL,
  `test_description` varchar(500) NOT NULL,
  `test_price` varchar(5) NOT NULL,
  `first_50_percent_price` varchar(5) NOT NULL,
  `second_50_percent_price` varchar(5) NOT NULL,
  `date_time_of_test_order` varchar(30) NOT NULL,
  `lab_accept_order` varchar(15) NOT NULL,
  `date_time_of_order_accepted` varchar(30) NOT NULL,
  `delivery_boy_name_1` varchar(50) NOT NULL,
  `delivery_boy_phone_1` varchar(15) NOT NULL,
  `delivery_boy_email_1` varchar(50) NOT NULL,
  `delivery_boy_address_1` varchar(450) NOT NULL,
  `delivery_boy_accept_1` varchar(15) NOT NULL,
  `applicant_confirm_delivery_boy_otp_1` varchar(15) NOT NULL,
  `applicant_confirm_delivery_boy_otp_status_1` varchar(15) NOT NULL,
  `date_time_of_delivery_boy_arrive_1` varchar(30) NOT NULL,
  `applicant_first_50_percent_cod_payment_otp_1` varchar(15) NOT NULL,
  `applicant_first_50_percent_cod_payment_otp_status_1` varchar(15) NOT NULL,
  `lab_confirm_delivery_boy_otp_1` varchar(15) NOT NULL,
  `lab_confirm_delivery_boy_otp_status_1` varchar(15) NOT NULL,
  `date_time_of_delivery_boy_submit_sample_1` varchar(30) NOT NULL,
  `test_order_complete_by_lab` varchar(15) NOT NULL,
  `delivery_boy_name_2` varchar(50) NOT NULL,
  `delivery_boy_phone_2` varchar(15) NOT NULL,
  `delivery_boy_email_2` varchar(50) NOT NULL,
  `delivery_boy_address_2` varchar(450) NOT NULL,
  `delivery_boy_accept_2` varchar(15) NOT NULL,
  `lab_confirm_delivery_boy_otp_2` varchar(15) NOT NULL,
  `lab_confirm_delivery_boy_otp_status_2` varchar(15) NOT NULL,
  `date_time_of_delivery_boy_collect_report_2` varchar(30) NOT NULL,
  `applicant_confirm_delivery_boy_otp_2` varchar(15) NOT NULL,
  `applicant_confirm_delivery_boy_otp_status_2` varchar(15) NOT NULL,
  `date_time_of_delivery_boy_arrive_2` varchar(30) NOT NULL,
  `applicant_second_50_percent_cod_payment_otp_2` varchar(15) NOT NULL,
  `applicant_second_50_percent_cod_payment_otp_status_2` varchar(15) NOT NULL,
  `whole_cycle_completed` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `test_transaction_details`
--

INSERT INTO `test_transaction_details` (`id`, `applicant_name`, `applicant_address`, `test_transaction_city`, `applicant_phone`, `applicant_email`, `lab_name`, `lab_address`, `lab_phone`, `lab_email`, `test_name`, `test_description`, `test_price`, `first_50_percent_price`, `second_50_percent_price`, `date_time_of_test_order`, `lab_accept_order`, `date_time_of_order_accepted`, `delivery_boy_name_1`, `delivery_boy_phone_1`, `delivery_boy_email_1`, `delivery_boy_address_1`, `delivery_boy_accept_1`, `applicant_confirm_delivery_boy_otp_1`, `applicant_confirm_delivery_boy_otp_status_1`, `date_time_of_delivery_boy_arrive_1`, `applicant_first_50_percent_cod_payment_otp_1`, `applicant_first_50_percent_cod_payment_otp_status_1`, `lab_confirm_delivery_boy_otp_1`, `lab_confirm_delivery_boy_otp_status_1`, `date_time_of_delivery_boy_submit_sample_1`, `test_order_complete_by_lab`, `delivery_boy_name_2`, `delivery_boy_phone_2`, `delivery_boy_email_2`, `delivery_boy_address_2`, `delivery_boy_accept_2`, `lab_confirm_delivery_boy_otp_2`, `lab_confirm_delivery_boy_otp_status_2`, `date_time_of_delivery_boy_collect_report_2`, `applicant_confirm_delivery_boy_otp_2`, `applicant_confirm_delivery_boy_otp_status_2`, `date_time_of_delivery_boy_arrive_2`, `applicant_second_50_percent_cod_payment_otp_2`, `applicant_second_50_percent_cod_payment_otp_status_2`, `whole_cycle_completed`) VALUES
(1, 'Amy Jackson', 'A/202, Surya Mandir Flates, Near Akash Tower, Bodakdev, Ahmedabad, Gujarat - 380015', 'Ahmedabad', '9727510766', 'amyjackson@yopmail.com', 'Accurate Laboratory', 'G - 205, Manibhadra Arcade, opposite Rajasthan Hospital Gate, Shahibag, Ahmedabad, Gujarat - 380004', '9033044571', 'accuratelab@yopmail.com', 'Protein - Biuret solution', 'Proteins are needed for the growth and repair of our body.  Test if a food item contains protein Biuret solution is used to identify the presence of protein. Biuret reagent is a blue solution that, when it reacts with protein, will change color to pink-purple.', '2640', '1320', '1320', '02 / 04 / 2021 09 : 52 : 17', 'ACCEPTED', '02 / 04 / 2021 16 : 25 : 48', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
(2, 'Amy Jackson', 'A/202, Surya Mandir Flates, Near Akash Tower, Bodakdev, Ahmedabad, Gujarat - 380015', 'Ahmedabad', '9727510766', 'amyjackson@yopmail.com', 'Biocare Research (India) Pvt Ltd', 'G - 17,Sruhad Complex, Near Devi Cinema, Naroda, Ahmedabad, Gujarat - 382330', '7926577901', 'biocarelab@yopmail.com', 'pH Testing', 'pH is a measure of the concentration of hydrogen ions in a solution. The more of these hydrogen ions there are in a solution, the more acidic that water is. Acidity effects taste of water, but it can also affect how health water is to consume. Drinking water that\'s not neutral enough in acidity can make people sick.', '2760', '1380', '1380', '12 / 03 / 2021 10 : 54 : 40', 'ACCEPTED', 'null', 'Lloyd Scott', '7966190201', 'lloydscott13@yopmail.com', 'B - 201, Shiv Shakti Society, Near Mother Dairy, Navrangpura,  Ahmedabad,  Gujarat - 380008', 'ACCEPTED', '710952', 'OTP_VERIFIED', '21 / 04 / 2021 23 : 27 : 38', '835549', 'OTP_VERIFIED', '641697', 'OTP_VERIFIED', '21 / 04 / 2021 23 : 28 : 31', 'COMPLETED', 'Daniel Bryan', '6352066081', 'danielbryan@yopmail.com', 'D - 182, Kashivishvanath Society Part II, Near Swasik Cross Road, Isanpur, Ahmedabad, Gujarat - 382443', 'ACCEPTED', '024495', 'OTP_VERIFIED', '15 / 04 / 2021 11 : 25 : 05', '093947', 'OTP_VERIFIED', '15 / 04 / 2021 11 : 25 : 37', '858890', 'OTP_VERIFIED', 'COMPLETED'),
(3, 'Amy Jackson', 'A/202, Surya Mandir Flates, Near Akash Tower, Bodakdev, Ahmedabad, Gujarat - 380015', 'Ahmedabad', '9727510766', 'amyjackson@yopmail.com', 'Gujarat Laboratory', 'B - 203, Shivalik Yash Complex, Near Shashtri Nagar BRTS Stand, Naranpura, Ahmedabad, Gujarat - 380013', '7600069149', 'gujaratlab@yopmail.com', 'Drug Testing', 'A drug test looks for the presence of one or more illegal or prescription drugs in your urine, blood, saliva, hair, or sweat. Urine testing is the most common type of drug screening.', '2880', '1440', '1440', '13 / 04 / 2021 09 : 55 : 42', 'PENDING', 'null', 'Daniel Bryan', '6352066081', 'danielbryan@yopmail.com', 'D - 182, Kashivishvanath Society Part II, Near Swasik Cross Road, Isanpur, Ahmedabad, Gujarat - 382443', 'PENDING', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
(4, 'Amy Jackson', 'A/202, Surya Mandir Flates, Near Akash Tower, Bodakdev, Ahmedabad, Gujarat - 380015', 'Ahmedabad', '9727510766', 'amyjackson@yopmail.com', 'Botany Zone', '101, Shree Balaji Complex, Near Shivranjani Road, Satellite, Ahmedabad, Gujarat - 380005', '7283947790', 'botanyzonelab@yopmail.com', 'Leaves Testing', 'Iodine solution is used to test leaves for the presence of starch. Add the leaf to boiling ethanol in a water bath for a few minutes (the boiling ethanol dissolves the chlorophyll and removes the green colour from the leaf - it turns white so it is easy to see the change in colour).', '3360', '1680', '1680', '28 / 03 / 2021 13 : 56 : 00', 'PENDING', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', '16 / 03 / 2021 15 : 54 : 11', 'null', 'null', 'COMPLETED'),
(5, 'Amy Jackson', 'A/202, Surya Mandir Flates, Near Akash Tower, Bodakdev, Ahmedabad, Gujarat - 380015', 'Ahmedabad', '9727510766', 'amyjackson@yopmail.com', 'Biocare Research (India) Pvt Ltd', 'G - 17,Sruhad Complex, Near Devi Cinema, Naroda, Ahmedabad, Gujarat - 382330', '7926577901', 'biocarelab@yopmail.com', 'Mineral Tests', 'The list of mineral testing available could be a these is paper, for how many there are. A few important mineral tests include chlorine and chloride, nitrate and nitrite, lead, copper, iron, zinc, potassium, sodium. This wide range of mineral testing is essential and relevant in determining water quality, as different regions or areas of terrain may have more of a buildup of certain types of minerals, which informs what kind of mineral treatments the water actually needs in order to be purified.', '2880', '1440', '1440', '11 / 04 / 2021 10 : 01 : 08', 'PENDING', '12 / 04 / 2021 11 : 11 : 48', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
(6, 'Adam Gilchrist', '1, Panna Park Society, Near Hanuman ji Temple, Navrangpura, Ahmedabad, Gujarat - 380009', 'Ahmedabad', '7283947790', 'adamgilchrist@yopmail.com', 'Biocare Research (India) Pvt Ltd', 'G - 17,Sruhad Complex, Near Devi Cinema, Naroda, Ahmedabad, Gujarat - 382330', '7926577901', 'biocarelab@yopmail.com', 'Mineral Tests', 'The list of mineral testing available could be a these is paper, for how many there are. A few important mineral tests include chlorine and chloride, nitrate and nitrite, lead, copper, iron, zinc, potassium, sodium. This wide range of mineral testing is essential and relevant in determining water quality, as different regions or areas of terrain may have more of a buildup of certain types of minerals, which informs what kind of mineral treatments the water actually needs in order to be purified.', '2880', '1440', '1440', '05 / 04 / 2021 15 : 17 : 50', 'ACCEPTED', 'null', 'Daniel Bryan', '6352066081', 'danielbryan@yopmail.com', 'D - 182, Kashivishvanath Society Part II, Near Swasik Cross Road, Isanpur, Ahmedabad, Gujarat - 382443', 'ACCEPTED', '225565', 'OTP_VERIFIED', '15 / 04 / 2021 11 : 20 : 20', '158516', 'OTP_VERIFIED', '735784', 'OTP_VERIFIED', '15 / 04 / 2021 11 : 21 : 32', 'null', 'Daniel Bryan', '6352066081', 'danielbryan@yopmail.com', 'D - 182, Kashivishvanath Society Part II, Near Swasik Cross Road, Isanpur, Ahmedabad, Gujarat - 382443', 'PENDING', 'null', 'null', '07 / 04 / 2021 10 : 30 : 35', 'null', 'null', '07 / 04 / 2021 11 : 16 : 25', 'null', 'null', 'COMPLETED'),
(7, 'Amy Jackson', 'A/202, Surya Mandir Flates, Near Akash Tower, Bodakdev, Ahmedabad, Gujarat - 380015', 'Ahmedabad', '9727510766', 'amyjackson@yopmail.com', 'Biocare Research (India) Pvt Ltd', 'G - 17,Sruhad Complex, Near Devi Cinema, Naroda, Ahmedabad, Gujarat - 382330', '7926577901', 'biocarelab@yopmail.com', 'Organic chemicals tests', 'It generally performed only if there is reason to believe a specific contaminant has infiltrated the water system (such as pesticides entering the water supply). Industrial and petroleum contamination can also be found through organic chemical testing.', '2280', '1140', '1140', '16 / 04 / 2021 11 : 40 : 14', 'ACCEPTED', '16 / 04 / 2021 11 : 55 : 28', 'Matt Hardy', '7926441933', 'matthardy23@yopmail.com', '21/3, Rajeswari Society, Near Judges Bunglow, Prahlad Nagar, Ahmedabad, Gujarat - 382443', 'ACCEPTED', '787736', 'OTP_VERIFIED', '16 / 04 / 2021 11 : 59 : 02', '653018', 'OTP_VERIFIED', '261108', 'OTP_VERIFIED', '16 / 04 / 2021 12 : 01 : 41', 'COMPLETED', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null'),
(8, 'Amy Jackson', 'A/202, Surya Mandir Flates, Near Akash Tower, Bodakdev, Ahmedabad, Gujarat - 380015', 'Ahmedabad', '9727510766', 'amyjackson@yopmail.com', 'Accurate Laboratory', 'G - 205, Manibhadra Arcade, opposite Rajasthan Hospital Gate, Shahibag, Ahmedabad, Gujarat - 380004', '9033044571', 'accuratelab@yopmail.com', 'Starch and Sugar test-Benedict\'s solution', 'There are many types of carbohydrates. Benedict\'s solution is used to test for simple sugars, such as glucose. It is a clear blue solution of sodium and copper salts. In the presence of simple sugars, the blue solution changes color to green, yellow, and brick-red, depending on the amount of sugar.', '2280', '1140', '1140', '20 / 04 / 2021 17 : 23 : 21', 'PENDING', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null', 'null');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `test_transaction_details`
--
ALTER TABLE `test_transaction_details`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `test_transaction_details`
--
ALTER TABLE `test_transaction_details`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
