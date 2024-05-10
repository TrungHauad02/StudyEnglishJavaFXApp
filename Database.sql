CREATE DATABASE englishforkids
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
Use englishforkids;
CREATE TABLE ACCOUNT (
    IdAccount CHAR(10) PRIMARY KEY,
    Username VARCHAR(255) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Role ENUM('admin', 'student')
);

CREATE TABLE USER (
    IdUser CHAR(10) PRIMARY KEY,
    Fullname NVARCHAR(255),
    Birthday DATE,
    Status BOOLEAN,
    Avatar VARCHAR(255),
    School NVARCHAR(255),
    Class NVARCHAR(255),
    Address NVARCHAR(255),
    EmailParent VARCHAR(255) UNIQUE,
    Score INT DEFAULT 0,
    IdAccount CHAR(10),
    FOREIGN KEY (IdAccount) REFERENCES ACCOUNT(IdAccount)
);


CREATE TABLE REMEMBERACCOUNT (
    IdAccount CHAR(10) NOT NULL,
    IPAddress VARCHAR(255) NOT NULL,
    primary key(IdAccount, IPAddress),
    FOREIGN KEY (IdAccount) REFERENCES ACCOUNT(IdAccount)
);


CREATE TABLE LESSON (
    IdLesson CHAR(10) PRIMARY KEY,
    Name VARCHAR(255),
    Serial INT,
    Description VARCHAR(255),
    CreateDay DATETIME,
    Status ENUM('lock', 'unlock','hidden')
);

CREATE TABLE SUBMITLESSON (
    IdSubmitLesson CHAR(10) PRIMARY KEY,
    IdUser CHAR(10),
    IdLesson CHAR(10),
    Status ENUM('uncomplete', 'complete'),
    FOREIGN KEY (IdUser) REFERENCES USER(IdUser),
    FOREIGN KEY (IdLesson) REFERENCES LESSON(IdLesson)
);

CREATE TABLE LESSONPART (
    IdLessonPart CHAR(10) PRIMARY KEY,
    Type ENUM('vocabulary', 'listening','speaking','quiz', 'grammar'),
    IdLesson CHAR(10) NOT NULL,
    FOREIGN KEY (IdLesson) REFERENCES LESSON(IdLesson)
);

CREATE TABLE VOCABULARY (
    IdVocabulary CHAR(10) PRIMARY KEY,
    Word VARCHAR(255) NOT NULL,
    Mean VARCHAR(255) NOT NULL,
    Image LONGBLOB,
    Phonetic VARCHAR(255)
);

CREATE TABLE VOCABULARYPART (
    IdVocabulary CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART(IdLessonPart),
    FOREIGN KEY (IdVocabulary) REFERENCES VOCABULARY(IdVocabulary),
    PRIMARY KEY (IdVocabulary, IdLessonPart)
);

CREATE TABLE SYNONYMS (
    IdVocabulary CHAR(10),
    IdSynonyms CHAR(10),
    FOREIGN KEY (IdVocabulary) REFERENCES VOCABULARY(IdVocabulary),
    FOREIGN KEY (IdSynonyms) REFERENCES VOCABULARY(IdVocabulary),
    PRIMARY KEY (IdVocabulary, IdSynonyms)
);

CREATE TABLE ANTONYMS (
    IdVocabulary CHAR(10),
    IdAntonyms CHAR(10),
    FOREIGN KEY (IdVocabulary) REFERENCES VOCABULARY(IdVocabulary),
    FOREIGN KEY (IdAntonyms) REFERENCES VOCABULARY(IdVocabulary),
    PRIMARY KEY (IdVocabulary, IdAntonyms)
);

CREATE TABLE QUIZ (
    IdQuiz CHAR(10) PRIMARY KEY,
    Title VARCHAR(255),
    Status ENUM('lock', 'unlock','hidden')
);

CREATE TABLE QUIZPART (
    IdQuiz CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART(IdLessonPart),
    FOREIGN KEY (IdQuiz) REFERENCES QUIZ(IdQuiz),
    PRIMARY KEY (IdQuiz, IdLessonPart)
);

CREATE TABLE QUESTIONQUIZ (
    IdQuestionQuiz CHAR(10) PRIMARY KEY,
    Content VARCHAR(255),
    Serial int;
    IdQuiz CHAR(10),
    Image LONGBLOB,
    FOREIGN KEY (IdQuiz) REFERENCES QUIZ(IdQuiz)
);

CREATE TABLE ANSWERQUIZ (
    IdAnswerQuiz CHAR(10) PRIMARY KEY,
    Content VARCHAR(255),
    IsCorrect BOOLEAN,
    IdQuestionQuiz CHAR(10) NOT NULL,
    FOREIGN KEY (IdQuestionQuiz) REFERENCES QUESTIONQUIZ(IdQuestionQuiz)
);

CREATE TABLE SUBMITQUIZ (
    IdSubmitQuiz CHAR(10) PRIMARY KEY,
    Score INT,
    StartTime DATETIME,
    EndTime DATETIME,
    IdQuiz CHAR(10) NOT NULL,
    IdUser CHAR(10) NOT NULL,
    FOREIGN KEY (IdUser) REFERENCES USER(IdUser),
    FOREIGN KEY (IdQuiz) REFERENCES QUIZ(IdQuiz)
);

CREATE TABLE ANSWERSUBMITQUIZ(
    IdSubmitQuiz CHAR(10),
    IdAnswerQuiz CHAR(20),
    FOREIGN KEY (IdSubmitQuiz) REFERENCES SUBMITQUIZ(IdSubmitQuiz),
    FOREIGN KEY (IdAnswerQuiz) REFERENCES ANSWERQUIZ(IdAnswerQuiz),
    PRIMARY KEY (IdSubmitQuiz, IdAnswerQuiz)
);

CREATE TABLE LISTENING(
    IdListening CHAR(10) PRIMARY KEY
    Title VARCHAR(255),
    Description VARCHAR(255),
    Video LONGBLOB,
    Script TEXT
);

CREATE TABLE LISTENINGPART (
    IdListening CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART(IdLessonPart),
    FOREIGN KEY (IdListening) REFERENCES LISTENING(IdListening),
    PRIMARY KEY (IdListening, IdLessonPart)
);

CREATE TABLE SPEAKING(
    IdSpeaking CHAR(10) PRIMARY KEY,
    Title VARCHAR(255),
    Content TEXT,
    Example LONGBLOB
);

CREATE TABLE SPEAKINGPART (
    IdSpeaking CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART(IdLessonPart),
    FOREIGN KEY (IdSpeaking) REFERENCES SPEAKING(IdSpeaking),
    PRIMARY KEY (IdSpeaking, IdLessonPart)
);

CREATE TABLE GRAMMAR(
    IdGrammar CHAR(10) PRIMARY KEY,
    Title VARCHAR(255),
    Content TEXT,
    Rule TEXT,
    Image LONGBLOB,
    Example TEXT
);

CREATE TABLE GRAMMARPART (
    IdGrammar CHAR(10),
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART(IdLessonPart),
    FOREIGN KEY (IdGrammar) REFERENCES GRAMMAR(IdGrammar),
    PRIMARY KEY (IdGrammar, IdLessonPart)
);


DELIMITER //

CREATE TRIGGER before_insert_account
BEFORE INSERT ON ACCOUNT
FOR EACH ROW
BEGIN
    DECLARE nextId INT;
    DECLARE newId CHAR(10);

    SELECT COUNT(*) + 1 INTO nextId FROM ACCOUNT;
    SET newId = CONCAT('acc', LPAD(nextId, 7, '0'));
    SET NEW.IdAccount = newId;
END//

DELIMITER ;


DELIMITER //

CREATE TRIGGER before_insert_user
BEFORE INSERT ON USER
FOR EACH ROW
BEGIN
    DECLARE account_id CHAR(10);
    DECLARE nextId INT;
    DECLARE newId CHAR(10);
    
    SELECT IdAccount INTO account_id
    FROM ACCOUNT
    ORDER BY IdAccount DESC
    LIMIT 1;
    
    SET NEW.IdAccount = account_id;
    
    SELECT COUNT(*) + 1 INTO nextId FROM USER;
    SET newId = CONCAT('user', LPAD(nextId, 6, '0'));
    SET NEW.IdUser = newId;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER before_insert_submitquiz
BEFORE INSERT ON SUBMITQUIZ
FOR EACH ROW
BEGIN
    DECLARE last_id CHAR(10);
    DECLARE last_number INT;

    SELECT IdSubmitQuiz INTO last_id
    FROM SUBMITQUIZ
    ORDER BY IdSubmitQuiz DESC
    LIMIT 1;

    IF last_id IS NULL THEN
        SET NEW.IdSubmitQuiz = 'subq000001';
    ELSE
        SET last_number = CAST(SUBSTRING(last_id, 5) AS SIGNED) + 1;

        SET NEW.IdSubmitQuiz = CONCAT('subq', LPAD(last_number, 6, '0'));
    END IF;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER before_insert_lesson
BEFORE INSERT ON Lesson
FOR EACH ROW
BEGIN
    DECLARE last_id VARCHAR(10);
    DECLARE last_number INT;

    SELECT IdLesson INTO last_id
    FROM Lesson
    ORDER BY IdLesson DESC
    LIMIT 1;

    IF last_id IS NULL THEN
        SET NEW.IdLesson = 'Less000001';
    ELSE
        SET last_number = CAST(SUBSTRING(last_id, 5) AS SIGNED) + 1;

        SET NEW.IdLesson = CONCAT('Less', LPAD(last_number, 6, '0'));
    END IF;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER before_insert_grammar
BEFORE INSERT ON GRAMMAR
FOR EACH ROW
BEGIN
    DECLARE last_id VARCHAR(10);
    DECLARE last_number INT;

    SELECT IdGrammar INTO last_id
    FROM GRAMMAR
    ORDER BY IdGrammar DESC
    LIMIT 1;

    IF last_id IS NULL THEN
        SET NEW.IdGrammar = 'gram000001';
    ELSE
        SET last_number = CAST(SUBSTRING(last_id, 5) AS SIGNED) + 1;

        SET NEW.IdGrammar = CONCAT('gram', LPAD(last_number, 6, '0'));
    END IF;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER before_insert_lessonpart
BEFORE INSERT ON LESSONPART
FOR EACH ROW
BEGIN
    DECLARE last_id VARCHAR(10);
    DECLARE last_number INT;

    SELECT IdLessonPart INTO last_id
    FROM LESSONPART
    ORDER BY IdLessonPart DESC
    LIMIT 1;

    IF last_id IS NULL THEN
        SET NEW.IdLessonPart = 'Lpart00001';
    ELSE
        SET last_number = CAST(SUBSTRING(last_id, 6) AS SIGNED) + 1;

        SET NEW.IdLessonPart = CONCAT('Lpart', LPAD(last_number, 5, '0'));
    END IF;
END//

DELIMITER ;


DELIMITER //

CREATE TRIGGER before_insert_listening
BEFORE INSERT ON LISTENING
FOR EACH ROW
BEGIN
    DECLARE last_id VARCHAR(10);
    DECLARE last_number INT;

    SELECT IdListening INTO last_id
    FROM LISTENING
    ORDER BY IdListening DESC
    LIMIT 1;

    IF last_id IS NULL THEN
        SET NEW.IdListening = 'List000006';
    ELSE
        SET last_number = CAST(SUBSTRING(last_id, 5) AS SIGNED) + 1;

        SET NEW.IdListening = CONCAT('List', LPAD(last_number, 6, '0'));
    END IF;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER before_insert_vocabulary
BEFORE INSERT ON VOCABULARY
FOR EACH ROW
BEGIN
    DECLARE last_id VARCHAR(10);
    DECLARE last_number INT;

    SELECT IdVocabulary INTO last_id
    FROM VOCABULARY
    ORDER BY IdVocabulary DESC
    LIMIT 1;

    IF last_id IS NULL THEN
        SET NEW.IdVocabulary = 'V000000001';
    ELSE
        SET last_number = CAST(SUBSTRING(last_id, 2) AS SIGNED) + 1;

        SET NEW.IdVocabulary = CONCAT('V', LPAD(last_number, 9, '0'));
    END IF;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER before_insert_speaking
BEFORE INSERT ON SPEAKING
FOR EACH ROW
BEGIN
    DECLARE last_id VARCHAR(10);
    DECLARE last_number INT;

    SELECT IdSpeaking INTO last_id
    FROM SPEAKING
    ORDER BY IdSpeaking DESC
    LIMIT 1;

    IF last_id IS NULL THEN
        SET NEW.IdSpeaking = 'spe0000001';
    ELSE
        SET last_number = CAST(SUBSTRING(last_id, 4) AS SIGNED) + 1;

        SET NEW.IdSpeaking = CONCAT('spe', LPAD(last_number, 7, '0'));
    END IF;
END//

DELIMITER ;

-- Add data user
INSERT INTO ACCOUNT (IdAccount, Username, Password, Role)
VALUES ('acc0000001', '1', '1', 'student');
INSERT INTO USER (IdUser, Fullname, Birthday, Status, Avatar, School, Class, Address, EmailParent, Score, IdAccount)
VALUES ('user000001', 'Nguyễn Thị Kim Phụng', '2013-07-02', 1, 'avatar_1.png', 'Trường Tiểu học Phong Mỹ 2', 'Lớp 4A', 'xã Phong Mỹ, huyện Cao Lãnh', 'trunghauad02@gmail.com', 0, 'acc0000001');

-- Add data lesson
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000001', 'Lesson 1', 'Description of Lesson 1', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000002', 'Lesson 2', 'Description of Lesson 2', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000003', 'Lesson 3', 'Description of Lesson 3', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000004', 'Lesson 4', 'Description of Lesson 4', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000005', 'Lesson 5', 'Description of Lesson 5', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000006', 'Lesson 6', 'Description of Lesson 6', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000007', 'Lesson 7', 'Description of Lesson 7', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000008', 'Lesson 8', 'Description of Lesson 8', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000009', 'Lesson 9', 'Description of Lesson 9', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000010', 'Lesson 10', 'Description of Lesson 10', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000011', 'Lesson 11', 'Description of Lesson 11', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status)
VALUES ('Less000012', 'Lesson 12', 'Description of Lesson 12', 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, Status, Serial)
VALUES ('Less000013', 'Lesson 13', 'Description of Lesson 13', 'unlock', 13);

Update LESSON SET Serial = 1 Where IdLesson = 'Less000001';
Update LESSON SET Serial = 2 Where IdLesson = 'Less000002';
Update LESSON SET Serial = 3 Where IdLesson = 'Less000003';
Update LESSON SET Serial = 4 Where IdLesson = 'Less000004';
Update LESSON SET Serial = 5 Where IdLesson = 'Less000005';
Update LESSON SET Serial = 6 Where IdLesson = 'Less000006';
Update LESSON SET Serial = 7 Where IdLesson = 'Less000007';
Update LESSON SET Serial = 8 Where IdLesson = 'Less000008';
Update LESSON SET Serial = 9 Where IdLesson = 'Less000009';
Update LESSON SET Serial = 10 Where IdLesson = 'Less000010';
Update LESSON SET Serial = 11 Where IdLesson = 'Less000011';
Update LESSON SET Serial = 12 Where IdLesson = 'Less000012';


-- Add data vocabulary
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000001', 'Hello', 'Xin chào', '[həˈloʊ]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000002', 'Goodbye', 'Tạm biệt', '[ɡʊdˈbaɪ]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000003', 'Thank you', 'Cảm ơn bạn', '[θæŋk juː]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000004', 'Sorry', 'Xin lỗi', '[ˈsɔːri]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000005', 'Please', 'Làm ơn', '[pliːz]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000006', 'Yes', 'Có', '[jes]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000007', 'No', 'Không', '[noʊ]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000008', 'What', 'Cái gì', '[wʌt]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000009', 'Where', 'Ở đâu', '[wer]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000010', 'When', 'Khi nào', '[wen]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000011', 'Why', 'Tại sao', '[waɪ]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000012', 'Who', 'Ai', '[huː]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000013', 'How', 'Như thế nào', '[haʊ]');
INSERT INTO VOCABULARY (IdVocabulary, Word, Mean, Phonetic)
VALUES ('V000000014', 'Hi', 'Xin chào', '[haɪ]');

INSERT INTO ANTONYMS (IdVocabulary, IdAntonyms)
VALUES ('V000000001', 'V000000002');
INSERT INTO ANTONYMS (IdVocabulary, IdAntonyms)
VALUES ('V000000003', 'V000000004');
INSERT INTO ANTONYMS (IdVocabulary, IdAntonyms)
VALUES ('V000000006', 'V000000007');

INSERT INTO SYNONYMS (IdVocabulary, IdSynonyms)
VALUES ('V000000001', 'V000000014');

-- Add data speaking
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000001', 'Introduce', 'Introduce yourself');

-- Add data grammar
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000001', 'Present Simple tense', 'Hiện tại đơn (Simple Present hoặc Present Simple) là một trong các thì hiện tại trong tiếng Anh , diễn tả các hành động xảy ra ở hiện tại.', 'S + V(-s/-es) + O/A\nS + am/is/are + N/Adj', 'Russia is the largest country in the world.');

-- Add data listening
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000001', 'Confident', 'This video is about believe in your abilities','If you understand English but can''t speak fluently. This video is for you. The first thing i want you to know is that it''s important to believe in yourself in your abilitiesDon''t let fear or self-doubt hold your back. Keep practicing and keep pushing yourself out of your comfort zoneEvery small step you talk will leak to bigger accomplishments in English');

-- Add data quiz
INSERT INTO QUIZ (IdQuiz, Title, Status)
VALUES ('quiz000001', 'Quiz 1', 'unlock');

-- Add vocabulary to lesson
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00001', 'vocabulary', 'Less000001');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000001', 'Lpart00001');

-- Add speaking to lesson 
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00002', 'speaking', 'Less000001');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000001', 'Lpart00002');

-- Add grammar to lesson
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00003', 'grammar', 'Less000001');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000001', 'Lpart00003');

-- Add listening to lesson
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00004', 'listening', 'Less000001');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000001', 'Lpart00004');

-- Add quiz to lesson
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00005', 'quiz', 'Less000001');
INSERT INTO QUIZPART (IdQuiz, IdLessonPart)
VALUES ('quiz000001', 'Lpart00005');

-- Add vocabulary to lesson 2
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00006', 'vocabulary', 'Less000002');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000002', 'Lpart00006');

-- Add vocabulary to lesson 3
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00007', 'vocabulary', 'Less000003');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000003', 'Lpart00007');

-- Add vocabulary to lesson 4
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00008', 'vocabulary', 'Less000004');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000004', 'Lpart00008');

-- Add vocabulary to lesson 5
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00009', 'vocabulary', 'Less000005');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000005', 'Lpart00009');

-- Add vocabulary to lesson 6
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00010', 'vocabulary', 'Less000006');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000006', 'Lpart00010');

-- Add vocabulary to lesson 7
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00011', 'vocabulary', 'Less000007');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000007', 'Lpart00011');

-- Add vocabulary to lesson 8
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00012', 'vocabulary', 'Less000008');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000008', 'Lpart00012');

-- Add vocabulary to lesson 9
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00013', 'vocabulary', 'Less000009');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000009', 'Lpart00013');

-- Add vocabulary to lesson 10
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00014', 'vocabulary', 'Less000010');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000010', 'Lpart00014');

-- Add vocabulary to lesson 11
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00015', 'vocabulary', 'Less000011');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000011', 'Lpart00015');

-- Add vocabulary to lesson 12
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00016', 'vocabulary', 'Less000012');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000012', 'Lpart00016');

-- Add vocabulary to lesson 13
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00017', 'vocabulary', 'Less000013');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000013', 'Lpart00017');

-- Add vocabulary to lesson 1
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00018', 'vocabulary', 'Less000001');
INSERT INTO VOCABULARYPART (IdVocabulary, IdLessonPart)
VALUES ('V000000014', 'Lpart00018');

INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000002', 'Present Continuous tense', 'Hiện tại tiếp diễn (Present Continuous) là một trong các thì hiện tại trong tiếng Anh, diễn tả các hành động đang xảy ra ở hiện tại.', 'S + am/is/are + V-ing + O/A', 'I am watching TV now.');

-- Add grammar to lesson 2
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00019', 'grammar', 'Less000002');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000002', 'Lpart00019');

-- Add grammar to lesson 3
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000003', 'Past Simple tense', 'Quá khứ đơn (Past Simple) là một trong các thì quá khứ trong tiếng Anh, diễn tả các hành động đã xảy ra ở quá khứ.', 'S + V-ed + O/A', 'I watched TV yesterday.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00020', 'grammar', 'Less000003');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000003', 'Lpart00020');

-- Add grammar to lesson 4
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000004', 'Future Simple tense', 'Tương lai đơn (Future Simple) là một trong các thì tương lai trong tiếng Anh, diễn tả các hành động sẽ xảy ra ở tương lai.', 'S + will + V + O/A', 'I will go to school tomorrow.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00021', 'grammar', 'Less000004');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000004', 'Lpart00021');

-- Add grammar to lesson 5
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000005', 'Present Perfect tense', 'Hiện tại hoàn thành (Present Perfect) là một trong các thì hiện tại trong tiếng Anh, diễn tả hành động đã xảy ra trong quá khứ và vẫn còn ảnh hưởng đến hiện tại.', 'S + have/has + V-ed + O/A', 'I have finished my homework.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00022', 'grammar', 'Less000005');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000005', 'Lpart00022');

-- Add grammar to lesson 6
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000006', 'Past Continuous tense', 'Quá khứ tiếp diễn (Past Continuous) là một trong các thì quá khứ trong tiếng Anh, diễn tả hành động đang xảy ra ở quá khứ.', 'S + was/were + V-ing + O/A', 'I was watching TV when you called me.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00023', 'grammar', 'Less000006');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000006', 'Lpart00023');

-- Add grammar to lesson 7
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000007', 'Future Continuous tense', 'Tương lai tiếp diễn (Future Continuous) là một trong các thì tương lai trong tiếng Anh, diễn tả hành động đang xảy ra ở tương lai.', 'S + will + be + V-ing + O/A', 'I will be watching TV at 8 pm tomorrow.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00024', 'grammar', 'Less000007');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000007', 'Lpart00024');

-- Add grammar to lesson 8
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000008', 'Present Perfect Continuous tense', 'Hiện tại hoàn thành tiếp diễn (Present Perfect Continuous) là một trong các thì hiện tại trong tiếng Anh, diễn tả hành động đã xảy ra trong quá khứ và vẫn đang tiếp tục ở hiện tại.', 'S + have/has + been + V-ing + O/A', 'I have been studying English for 2 hours.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00025', 'grammar', 'Less000008');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000008', 'Lpart00025');

-- Add grammar to lesson 9
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000009', 'Past Perfect tense', 'Quá khứ hoàn thành (Past Perfect) là một trong các thì quá khứ trong tiếng Anh, diễn tả hành động đã xảy ra trước một hành động khác ở quá khứ.', 'S + had + V-ed + O/A', 'I had finished my homework when you called me.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00026', 'grammar', 'Less000009');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000009', 'Lpart00026');

-- Add grammar to lesson 10
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000010', 'Future Perfect tense', 'Tương lai hoàn thành (Future Perfect) là một trong các thì tương lai trong tiếng Anh, diễn tả hành động sẽ xảy ra trước một hành động khác ở tương lai.', 'S + will + have + V-ed + O/A', 'I will have finished my homework by 9 pm.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00027', 'grammar', 'Less000010');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000010', 'Lpart00027');

-- Add grammar to lesson 11
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000011', 'Present Perfect Continuous tense', 'Hiện tại hoàn thành tiếp diễn (Present Perfect Continuous) là một trong các thì hiện tại trong tiếng Anh, diễn tả hành động đã xảy ra trong quá khứ và vẫn đang tiếp tục ở hiện tại.', 'S + have/has + been + V-ing + O/A', 'I have been studying English for 2 hours.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00028', 'grammar', 'Less000011');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000011', 'Lpart00028');

-- Add grammar to lesson 12
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000012', 'Past Perfect Continuous tense', 'Quá khứ hoàn thành tiếp diễn (Past Perfect Continuous) là một trong các thì quá khứ trong tiếng Anh, diễn tả hành động đã xảy ra trước một hành động khác ở quá khứ và vẫn đang tiếp tục ở hiện tại.', 'S + had + been + V-ing + O/A', 'I had been studying English for 2 hours when you called me.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00029', 'grammar', 'Less000012');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000012', 'Lpart00029');

-- Add grammar to lesson 13
INSERT INTO GRAMMAR (IdGrammar, Title, Content, Rule, Example)
VALUES ('gram000013', 'Future Perfect Continuous tense', 'Tương lai hoàn thành tiếp diễn (Future Perfect Continuous) là một trong các thì tương lai trong tiếng Anh, diễn tả hành động sẽ xảy ra trước một hành động khác ở tương lai và vẫn đang tiếp tục ở hiện tại.', 'S + will + have + been + V-ing + O/A', 'I will have been studying English for 2 hours by 9 pm.');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00030', 'grammar', 'Less000013');
INSERT INTO GRAMMARPART (IdGrammar, IdLessonPart)
VALUES ('gram000013', 'Lpart00030');

-- Add listening to lesson 2
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000002', 'The best way to learn English', 'This video is about the best way to learn English','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00031', 'listening', 'Less000002');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000002', 'Lpart00031');

-- Add listening to lesson 3
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000003', 'How to improve your English', 'This video is about how to improve your English','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00032', 'listening', 'Less000003');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000003', 'Lpart00032');

-- Add listening to lesson 4
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000004', 'How to speak English fluently', 'This video is about how to speak English fluently','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00033', 'listening', 'Less000004');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000004', 'Lpart00033');

-- Add listening to lesson 5
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000005', 'How to improve your English listening skills', 'This video is about how to improve your English listening skills','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00034', 'listening', 'Less000005');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000005', 'Lpart00034');

-- Add listening to lesson 6
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000006', 'How to improve your English speaking skills', 'This video is about how to improve your English speaking skills','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00035', 'listening', 'Less000006');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000006', 'Lpart00035');

-- Add listening to lesson 7
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000007', 'How to improve your English writing skills', 'This video is about how to improve your English writing skills','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00036', 'listening', 'Less000007');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000007', 'Lpart00036');

-- Add listening to lesson 8
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000008', 'How to improve your English reading skills', 'This video is about how to improve your English reading skills','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00037', 'listening', 'Less000008');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000008', 'Lpart00037');

-- Add listening to lesson 9
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000009', 'How to improve your English grammar', 'This video is about how to improve your English grammar','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00038', 'listening', 'Less000009');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000009', 'Lpart00038');

-- Add listening to lesson 10
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000010', 'How to improve your English vocabulary', 'This video is about how to improve your English vocabulary','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00039', 'listening', 'Less000010');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000010', 'Lpart00039');

-- Add listening to lesson 11
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000011', 'How to improve your English pronunciation', 'This video is about how to improve your English pronunciation','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00040', 'listening', 'Less000011');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000011', 'Lpart00040');

-- Add listening to lesson 12
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000012', 'How to improve your English speaking skills', 'This video is about how to improve your English speaking skills','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00041', 'listening', 'Less000012');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000012', 'Lpart00041');

-- Add listening to lesson 13
INSERT INTO LISTENING (IdListening, Title, Description, Script)
VALUES ('List000013', 'How to improve your English writing skills', 'This video is about how to improve your English writing skills','');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00042', 'listening', 'Less000013');
INSERT INTO LISTENINGPART (IdListening, IdLessonPart)
VALUES ('List000013', 'Lpart00042');

-- Add speaking to lesson 2
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000002', 'Family', 'Talk about your family');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00043', 'speaking', 'Less000002');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000002', 'Lpart00043');

-- Add speaking to lesson 3
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000003', 'Hobbies', 'Talk about your hobbies');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00044', 'speaking', 'Less000003');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000003', 'Lpart00044');

-- Add speaking to lesson 4
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000004', 'Food', 'Talk about your favorite food');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00045', 'speaking', 'Less000004');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000004', 'Lpart00045');

-- Add speaking to lesson 5
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000005', 'Travel', 'Talk about your last trip');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00046', 'speaking', 'Less000005');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000005', 'Lpart00046');

-- Add speaking to lesson 6
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000006', 'Weather', 'Talk about the weather');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00047', 'speaking', 'Less000006');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000006', 'Lpart00047');

-- Add speaking to lesson 7
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000007', 'Music', 'Talk about your favorite music');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00048', 'speaking', 'Less000007');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000007', 'Lpart00048');

-- Add speaking to lesson 8
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000008', 'Sport', 'Talk about your favorite sport');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00049', 'speaking', 'Less000008');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000008', 'Lpart00049');

-- Add speaking to lesson 9
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000009', 'Job', 'Talk about your job');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00050', 'speaking', 'Less000009');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000009', 'Lpart00050');

-- Add speaking to lesson 10
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000010', 'Study', 'Talk about your study');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00051', 'speaking', 'Less000010');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000010', 'Lpart00051');

-- Add speaking to lesson 11
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000011', 'Health', 'Talk about your health');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00052', 'speaking', 'Less000011');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000011', 'Lpart00052');

-- Add speaking to lesson 12
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000012', 'Shopping', 'Talk about your shopping');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00053', 'speaking', 'Less000012');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000012', 'Lpart00053');

-- Add speaking to lesson 13
INSERT INTO SPEAKING (IdSpeaking, Title, Content)
VALUES ('spe0000013', 'Technology', 'Talk about your technology');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00054', 'speaking', 'Less000013');
INSERT INTO SPEAKINGPART (IdSpeaking, IdLessonPart)
VALUES ('spe0000013', 'Lpart00054');

-- Add quiz to lesson 2
INSERT INTO QUIZ (IdQuiz, Title, Status)
VALUES ('quiz000002', 'Quiz 2', 'unlock');
INSERT INTO LESSONPART (IdLessonPart, Type, IdLesson)
VALUES ('Lpart00055', 'quiz', 'Less000002');
INSERT INTO QUIZPART (IdQuiz, IdLessonPart)
VALUES ('quiz000002', 'Lpart00055');


INSERT INTO QUESTIONQUIZ (IdQuestionQuiz, Content, IdQuiz)
VALUES ('qq00000004', 'What is mean of "Goodbye"?', 'quiz000002');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000013', 'Xin chào', 0, 'qq00000004');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000014', 'Tạm biệt', 1, 'qq00000004');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000015', 'Cảm ơn bạn', 0, 'qq00000004');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000016', 'Xin lỗi', 0, 'qq00000004');

INSERT INTO QUESTIONQUIZ (IdQuestionQuiz, Content, IdQuiz)
VALUES ('qq00000005', 'What is mean of "Thank you"?', 'quiz000002');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000017', 'Xin chào', 0, 'qq00000005');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000018', 'Tạm biệt', 0, 'qq00000005');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000019', 'Cảm ơn bạn', 1, 'qq00000005');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000020', 'Xin lỗi', 0, 'qq00000005');

INSERT INTO QUESTIONQUIZ (IdQuestionQuiz, Content, IdQuiz)
VALUES ('qq00000006', 'What is mean of "Sorry"?', 'quiz000002');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000021', 'Xin chào', 0, 'qq00000006');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000022', 'Tạm biệt', 0, 'qq00000006');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000023', 'Cảm ơn bạn', 0, 'qq00000006');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000024', 'Xin lỗi', 1, 'qq00000006');

INSERT INTO QUESTIONQUIZ (IdQuestionQuiz, Content, IdQuiz)
VALUES ('qq00000007', 'What is mean of "Please"?', 'quiz000002');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000025', 'Xin chào', 0, 'qq00000007');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000026', 'Tạm biệt', 0, 'qq00000007');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000027', 'Cảm ơn bạn', 0, 'qq00000007');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000028', 'Làm ơn', 1, 'qq00000007');

INSERT INTO QUESTIONQUIZ (IdQuestionQuiz, Content, IdQuiz)
VALUES ('qq00000008', 'What is mean of "Yes"?', 'quiz000002');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000029', 'Có', 1, 'qq00000008');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000030', 'Không', 0, 'qq00000008');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000031', 'Cái gì', 0, 'qq00000008');
INSERT INTO ANSWERQUIZ (IdAnswerQuiz, Content, IsCorrect, IdQuestionQuiz)
VALUES ('ans0000032', 'Ở đâu', 0, 'qq00000008');

UPDATE `questionquiz` SET `Serial` = '1' WHERE (`IdQuestionQuiz` = 'qq00000004');
UPDATE `questionquiz` SET `Serial` = '2' WHERE (`IdQuestionQuiz` = 'qq00000005');
UPDATE `questionquiz` SET `Serial` = '3' WHERE (`IdQuestionQuiz` = 'qq00000006');
UPDATE `questionquiz` SET `Serial` = '4' WHERE (`IdQuestionQuiz` = 'qq00000007');
UPDATE `questionquiz` SET `Serial` = '5' WHERE (`IdQuestionQuiz` = 'qq00000008');
