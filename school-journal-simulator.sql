-- phpMyAdmin SQL Dump
-- version 5.3.0-dev+20221125.2e001c186a
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 05 Gru 2022, 01:28
-- Wersja serwera: 10.4.24-MariaDB
-- Wersja PHP: 8.1.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `school-journal-simulator`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `groups`
--

CREATE TABLE `groups` (
  `id_group` int(30) NOT NULL,
  `nameOfGroup` varchar(100) DEFAULT NULL,
  `maxStudents` int(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `groups`
--

INSERT INTO `groups` (`id_group`, `nameOfGroup`, `maxStudents`) VALUES
(1, 'class_1A', 20),
(2, 'class_2B', 30),
(3, 'class_3C', 22),
(10, 'class_2G', 20);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `students`
--

CREATE TABLE `students` (
  `id_student` int(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `SCondition` varchar(70) DEFAULT NULL,
  `dateOfBirth` varchar(70) DEFAULT NULL,
  `points` int(120) DEFAULT NULL,
  `id_g` int(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `students`
--

INSERT INTO `students` (`id_student`, `name`, `lastName`, `SCondition`, `dateOfBirth`, `points`, `id_g`) VALUES
(1, 'Piotr', 'Pogwizd', 'ODRABIAJACY', '09:08:2000', 50, 2),
(3, 'Krzysztof', 'Kowalski', 'NIEOBECNY', '01:01:2002', 98, 1),
(4, 'Ignacy', 'Szczypka', 'CHORY', '02:11:1999', 120, 1),
(18, 'Ania', 'Szczypula', 'CHORY', '02:03:2002', 14, 3),
(19, 'Czesiek', 'Rzepka', 'CHORY', '02:03:2000', 45, 1),
(20, 'anonim', 'anonim', 'NIEOBECNY', '05:05:2002', 22, 10),
(21, 'scacas', 'sacascasc', 'ODRABIAJACY', '02:02:2000', 22, 2),
(22, 'gsg', 'sgsg', 'sgsgsg', 'sgsg', 333, 10);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `_rating_`
--

CREATE TABLE `_rating_` (
  `id_rate` int(11) NOT NULL,
  `rating` int(5) DEFAULT NULL,
  `subject` varchar(50) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `id_s` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `_rating_`
--

INSERT INTO `_rating_` (`id_rate`, `rating`, `subject`, `comment`, `id_s`) VALUES
(1, 3, 'matematyka', NULL, 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id_group`);

--
-- Indeksy dla tabeli `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id_student`),
  ADD KEY `connect_with_group_by_ID` (`id_g`);

--
-- Indeksy dla tabeli `_rating_`
--
ALTER TABLE `_rating_`
  ADD PRIMARY KEY (`id_rate`),
  ADD KEY `id_student` (`id_s`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `groups`
--
ALTER TABLE `groups`
  MODIFY `id_group` int(30) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `students`
--
ALTER TABLE `students`
  MODIFY `id_student` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT dla tabeli `_rating_`
--
ALTER TABLE `_rating_`
  MODIFY `id_rate` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `connect_with_group_by_ID` FOREIGN KEY (`id_g`) REFERENCES `groups` (`id_group`);

--
-- Ograniczenia dla tabeli `_rating_`
--
ALTER TABLE `_rating_`
  ADD CONSTRAINT `_rating__ibfk_1` FOREIGN KEY (`id_s`) REFERENCES `students` (`id_student`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
