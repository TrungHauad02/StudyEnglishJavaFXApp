CREATE DATABASE englishforkids
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
Use englishforkids;
CREATE TABLE ACCOUNT (
    IdAccount CHAR(10) PRIMARY KEY,
    Username VARCHAR(255) NOT NULL,
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
    EmailParent VARCHAR(255),
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
    Example LONGBLOB,
    IdLessonPart CHAR(10) NOT NULL,
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART(IdLessonPart)
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
    Example TEXT,
    IdLessonPart CHAR(10) NOT NULL,
    FOREIGN KEY (IdLessonPart) REFERENCES LESSONPART(IdLessonPart)
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
    
    SELECT IdAccount INTO account_id
    FROM ACCOUNT
    ORDER BY IdAccount DESC
    LIMIT 1;
    
    SET NEW.IdAccount = account_id;
END//

DELIMITER ;
