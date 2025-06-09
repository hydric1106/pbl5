-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 09, 2025 at 07:00 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pbl5`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `category_name`) VALUES
(1, 'Media'),
(2, 'Banking');

-- --------------------------------------------------------

--
-- Table structure for table `flashcards`
--

CREATE TABLE `flashcards` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `vocabulary_id` int(11) DEFAULT NULL,
  `word` varchar(99) NOT NULL,
  `definition` text NOT NULL,
  `phonetic` varchar(99) DEFAULT NULL,
  `example` text DEFAULT NULL,
  `type` varchar(99) DEFAULT NULL,
  `audio` text DEFAULT NULL,
  `flccategory_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `flashcards`
--

INSERT INTO `flashcards` (`id`, `user_id`, `vocabulary_id`, `word`, `definition`, `phonetic`, `example`, `type`, `audio`, `flccategory_id`) VALUES
(1, 3, NULL, 'Eat', 'a', '', '', 'verb', '', 7),
(2, 3, NULL, 'D', 'AAAA', '', '', 'verb', '', 7),
(3, 3, NULL, 'Drink', 'Uống', '', '', 'Verb', '', 8);

-- --------------------------------------------------------

--
-- Table structure for table `flashcards_categories`
--

CREATE TABLE `flashcards_categories` (
  `flccategory_id` int(11) NOT NULL,
  `flccategory_name` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `flashcards_categories`
--

INSERT INTO `flashcards_categories` (`flccategory_id`, `flccategory_name`) VALUES
(4, 'Day 1'),
(5, 'Day 2'),
(6, 'Day 3'),
(7, 'Day 4'),
(8, 'day5');

-- --------------------------------------------------------

--
-- Table structure for table `grammar_lessons`
--

CREATE TABLE `grammar_lessons` (
  `id` int(11) NOT NULL,
  `lesson_name` text NOT NULL,
  `type` varchar(99) NOT NULL,
  `description` text NOT NULL,
  `rules` text NOT NULL,
  `examples` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `grammar_lessons`
--

INSERT INTO `grammar_lessons` (`id`, `lesson_name`, `type`, `description`, `rules`, `examples`) VALUES
(1, 'Present Simple Tense', 'Tense', 'The present simple tense is used to express habits, general truths, repeated actions or unchanging situations, emotions and wishes.', 'Subject + V(s/es) + ...', 'She goes to school every day.');

-- --------------------------------------------------------

--
-- Table structure for table `levels`
--

CREATE TABLE `levels` (
  `id` int(11) NOT NULL,
  `level_name` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `levels`
--

INSERT INTO `levels` (`id`, `level_name`) VALUES
(1, 'A1'),
(2, 'A2'),
(3, 'B1'),
(4, 'B2'),
(5, 'C1');

-- --------------------------------------------------------

--
-- Table structure for table `options`
--

CREATE TABLE `options` (
  `id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `option_label` varchar(255) DEFAULT NULL,
  `option_text` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `options`
--

INSERT INTO `options` (`id`, `question_id`, `option_label`, `option_text`) VALUES
(1, 1, 'A', 'The woman is talking on the phone.'),
(2, 1, 'B', 'The woman is using her cellphone.'),
(3, 1, 'C', 'The woman is typing on her laptop.'),
(4, 1, 'D', 'The woman is writing in her notebook.'),
(5, 2, 'A', 'The man is using a screwdriver to screw a nail into the building frame.'),
(6, 2, 'B', 'The man is hammering something into a building frame.'),
(7, 2, 'C', 'The man is making the frame with his hand.'),
(8, 2, 'D', 'The man is wearing protective glasses.'),
(9, 3, 'A', 'choose'),
(10, 3, 'B', 'disseminate'),
(11, 3, 'C', 'link'),
(12, 3, 'D', 'thorough'),
(13, 4, 'A', 'disseminate'),
(14, 4, 'B', 'subscribe'),
(15, 4, 'C', 'decision'),
(16, 4, 'D', 'investigate'),
(17, 5, 'A', 'True'),
(18, 5, 'B', 'False');

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `question_text` varchar(255) DEFAULT NULL,
  `question_type` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `audio` varchar(255) DEFAULT NULL,
  `question_order` int(11) NOT NULL,
  `correct_option_label` varchar(255) DEFAULT NULL,
  `correct_answer_text` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`id`, `test_id`, `question_text`, `question_type`, `image`, `audio`, `question_order`, `correct_option_label`, `correct_answer_text`) VALUES
(1, 1, 'Listen to the audio and choose the correct answer', 'multiple_choice', 'https://estudyme.hoc102.com/legacy-data/kstoeic/images/5656089_1562638419203.png', 'https://storage.googleapis.com/estudyme/toeic/2024/10/08/94243671.mp3', 1, 'A', 'The woman is talking on the phone.'),
(2, 1, 'Listen to the audio and choose the correct answer', 'multiple_choice', 'https://estudyme.hoc102.com/legacy-data/kstoeic/images/5911589_1562638438001.png', 'https://storage.googleapis.com/estudyme/toeic/2024/10/08/66607451.mp3', 2, 'B', 'The man is hammering something into a building frame.'),
(3, 3, 'an association; a relationship', 'multiple_choice', 'https://estudyme.hoc102.com/legacy-data/kstoeic/images/ToeicVc_ev538.jpg', NULL, 1, 'C', 'link'),
(4, 3, 'to receive a periodical regularly on order', 'multiple_choice', 'https://fdn.gsmarena.com/imgroot/news/20/07/spotify-duo/-727/gsmarena_002.jpg', NULL, 2, 'B', 'subscribe'),
(5, 4, 'Sam Holden is the yoga teacher.', 'true_false', NULL, NULL, 1, 'B', 'False');

-- --------------------------------------------------------

--
-- Table structure for table `tests`
--

CREATE TABLE `tests` (
  `test_id` int(11) NOT NULL,
  `test_type` varchar(255) DEFAULT NULL,
  `test_name` varchar(255) DEFAULT NULL,
  `reading_text` text DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `level_id` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tests`
--

INSERT INTO `tests` (`test_id`, `test_type`, `test_name`, `reading_text`, `user_id`, `level_id`, `score`) VALUES
(1, 'Listening', 'Listening Test 1', NULL, 1, NULL, NULL),
(2, 'Listening', 'Listening Test 2', NULL, 1, NULL, NULL),
(3, 'Vocabulary', 'Topic Revision Media', NULL, 1, NULL, NULL),
(4, 'Reading', 'A poster at work', 'Come and join our lunchtime yoga class with experienced yoga teacher Divya Bridge!\n\nWhen? Every Tuesday at 1.30 p.m.\n\nWhere? Meeting Room 7\n\nHow much? £10 for four 30-minute classes.\n\nWhat to bring? Comfortable clothes. Divya will provide the yoga mats.\n\nHow to join? Write to Sam at Sam.Holden@example.com\n\n \n\nWe can only take a maximum of 20 in the room, so book now!', 1, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tests_categories`
--

CREATE TABLE `tests_categories` (
  `test_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `tests_categories`
--

INSERT INTO `tests_categories` (`test_id`, `category_id`) VALUES
(3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(99) NOT NULL,
  `password` varchar(99) NOT NULL,
  `email` varchar(99) NOT NULL,
  `image` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `image`) VALUES
(1, 'admin01', '123456', 'admin01@gmail.com', ''),
(2, 'Quang', '123456', 'quang1106@gmail.com', ''),
(3, 'quang555', '$2a$10$3Sse8gBlWFc3W5cKTLDDf.p/FzWRP.rZrU89OZjbYDHNV6UI9qbzu', 'quang555@gmail.com', '');

-- --------------------------------------------------------

--
-- Table structure for table `user_progress`
--

CREATE TABLE `user_progress` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `completion_status` enum('completed','in_progress') NOT NULL,
  `progress_percentage` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `question_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `user_progress`
--

INSERT INTO `user_progress` (`id`, `user_id`, `test_id`, `score`, `completion_status`, `progress_percentage`, `category_id`, `question_id`) VALUES
(1, 3, 3, 100, 'completed', 100, 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `user_test_results`
--

CREATE TABLE `user_test_results` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `selected_option_label` varchar(99) NOT NULL,
  `selected_answer_text` text NOT NULL,
  `is_correct` tinyint(1) NOT NULL,
  `score_obtained` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `user_test_results`
--

INSERT INTO `user_test_results` (`id`, `user_id`, `test_id`, `question_id`, `selected_option_label`, `selected_answer_text`, `is_correct`, `score_obtained`) VALUES
(1, 3, 3, 3, 'C', 'C', 1, 100),
(2, 3, 3, 4, 'B', 'B', 1, 100),
(15, 3, 3, 3, 'C', 'C', 1, 50),
(16, 3, 3, 4, 'B', 'B', 1, 50);

-- --------------------------------------------------------

--
-- Table structure for table `vocabulary`
--

CREATE TABLE `vocabulary` (
  `id` int(11) NOT NULL,
  `word` varchar(99) NOT NULL,
  `definition` text NOT NULL,
  `phonetic` varchar(99) NOT NULL,
  `example` text NOT NULL,
  `type` varchar(99) NOT NULL,
  `image` text NOT NULL,
  `audio` text NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `vocabulary`
--

INSERT INTO `vocabulary` (`id`, `word`, `definition`, `phonetic`, `example`, `type`, `image`, `audio`, `category_id`) VALUES
(1, 'accumulate', 'tích lũy hoặc thu thập dần dần', '/əˈkjuː.mjə.leɪt/', 'He managed to accumulate a fortune.', 'verb', '', '', 1),
(2, 'broadcast', 'to transmit a program or information by radio or television', '/ˈbrɔːd.kæst/', 'The interview was broadcast live across the country.', 'verb', '', '', 1),
(3, 'journalist', 'a person who writes for newspapers, magazines, or news websites', '/ˈdʒɜː.nə.lɪst/', 'She works as a journalist for a major news agency.', 'noun', '', '', 1),
(4, 'headline', 'the title of a newspaper article printed in large letters', '/ˈhed.laɪn/', 'The headline grabbed the reader’s attention immediately.', 'noun', '', '', 1),
(5, 'advertisement', 'a notice or announcement in a public medium promoting a product or service', '/ˌæd.vəˈtaɪz.mənt/', 'I saw an interesting advertisement for a new phone.', 'noun', '', '', 1),
(6, 'interview', 'a meeting where someone is asked questions for a job or for media purposes', '/ˈɪn.tə.vjuː/', 'The actor gave an exclusive interview after the movie release.', 'noun', '', '', 1),
(7, 'press', 'newspapers and magazines, or the people who write them', '/pres/', 'The event was widely covered by the press.', 'noun', '', '', 1),
(8, 'editor', 'a person who prepares content for publication by correcting, condensing, or modifying it', '/ˈed.ɪ.tər/', 'The editor reviewed the article before it was published.', 'noun', '', '', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `flashcards`
--
ALTER TABLE `flashcards`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `vocabulary_id` (`vocabulary_id`),
  ADD KEY `flccategory_id` (`flccategory_id`);

--
-- Indexes for table `flashcards_categories`
--
ALTER TABLE `flashcards_categories`
  ADD PRIMARY KEY (`flccategory_id`);

--
-- Indexes for table `grammar_lessons`
--
ALTER TABLE `grammar_lessons`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `levels`
--
ALTER TABLE `levels`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `options`
--
ALTER TABLE `options`
  ADD PRIMARY KEY (`id`),
  ADD KEY `question_id` (`question_id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `questions_ibfk_1` (`test_id`);

--
-- Indexes for table `tests`
--
ALTER TABLE `tests`
  ADD PRIMARY KEY (`test_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `level_id` (`level_id`);

--
-- Indexes for table `tests_categories`
--
ALTER TABLE `tests_categories`
  ADD PRIMARY KEY (`test_id`,`category_id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_progress`
--
ALTER TABLE `user_progress`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `question_id` (`question_id`),
  ADD KEY `user_progress_ibfk_2` (`test_id`);

--
-- Indexes for table `user_test_results`
--
ALTER TABLE `user_test_results`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `question_id` (`question_id`),
  ADD KEY `user_test_results_ibfk_2` (`test_id`);

--
-- Indexes for table `vocabulary`
--
ALTER TABLE `vocabulary`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `flashcards`
--
ALTER TABLE `flashcards`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `flashcards_categories`
--
ALTER TABLE `flashcards_categories`
  MODIFY `flccategory_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `grammar_lessons`
--
ALTER TABLE `grammar_lessons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `levels`
--
ALTER TABLE `levels`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `options`
--
ALTER TABLE `options`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user_progress`
--
ALTER TABLE `user_progress`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user_test_results`
--
ALTER TABLE `user_test_results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `vocabulary`
--
ALTER TABLE `vocabulary`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `flashcards`
--
ALTER TABLE `flashcards`
  ADD CONSTRAINT `flashcards_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `flashcards_ibfk_2` FOREIGN KEY (`vocabulary_id`) REFERENCES `vocabulary` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `flashcards_ibfk_3` FOREIGN KEY (`flccategory_id`) REFERENCES `flashcards_categories` (`flccategory_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `options`
--
ALTER TABLE `options`
  ADD CONSTRAINT `options_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`);

--
-- Constraints for table `tests`
--
ALTER TABLE `tests`
  ADD CONSTRAINT `tests_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tests_ibfk_2` FOREIGN KEY (`level_id`) REFERENCES `levels` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tests_categories`
--
ALTER TABLE `tests_categories`
  ADD CONSTRAINT `tests_categories_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`),
  ADD CONSTRAINT `tests_categories_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_progress`
--
ALTER TABLE `user_progress`
  ADD CONSTRAINT `user_progress_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_progress_ibfk_2` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`),
  ADD CONSTRAINT `user_progress_ibfk_3` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_progress_ibfk_4` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_test_results`
--
ALTER TABLE `user_test_results`
  ADD CONSTRAINT `user_test_results_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_test_results_ibfk_2` FOREIGN KEY (`test_id`) REFERENCES `tests` (`test_id`),
  ADD CONSTRAINT `user_test_results_ibfk_3` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vocabulary`
--
ALTER TABLE `vocabulary`
  ADD CONSTRAINT `vocabulary_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
