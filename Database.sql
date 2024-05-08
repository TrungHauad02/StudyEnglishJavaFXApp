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