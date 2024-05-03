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
    Name VARCHAR(255),
    Content VARCHAR(255),
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
    IdListening CHAR(10) PRIMARY KEY,
    CreateDay DATETIME,
    Title VARCHAR(255),
    Description VARCHAR(255),
    Video LONGBLOB,
    Script TEXT,
    IdLessonPart CHAR(10),
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART(IdLessonPart)
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

INSERT INTO ACCOUNT (IdAccount, Username, Password, Role)
VALUES ('acc0000001', '1', '1', 'student');
INSERT INTO USER (IdUser, Fullname, Birthday, Status, Avatar, School, Class, Address, EmailParent, Score, IdAccount)
VALUES ('user000001', 'Nguyễn Văn A', '2018-01-01', 1, 'avatar.jpg', 'Trường A', 'Lớp 1A', '123 Đường ABC, TP HCM', 'trunghauad02@gmail.com', 0, 'acc0000001');

INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000001', 'Lesson 1', 'Description of Lesson 1', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000002', 'Lesson 2', 'Description of Lesson 2', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000003', 'Lesson 3', 'Description of Lesson 3', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000004', 'Lesson 4', 'Description of Lesson 4', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000005', 'Lesson 5', 'Description of Lesson 5', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000006', 'Lesson 6', 'Description of Lesson 6', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000007', 'Lesson 7', 'Description of Lesson 7', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000008', 'Lesson 8', 'Description of Lesson 8', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000009', 'Lesson 9', 'Description of Lesson 9', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000010', 'Lesson 10', 'Description of Lesson 10', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000011', 'Lesson 11', 'Description of Lesson 11', NOW(), 'unlock');
INSERT INTO LESSON (IdLesson, Name, Description, CreateDay, Status)
VALUES ('Less000012', 'Lesson 12', 'Description of Lesson 12', NOW(), 'unlock');

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