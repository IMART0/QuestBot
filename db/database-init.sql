CREATE TABLE "user"
(
    id             BIGSERIAL PRIMARY KEY,
    chat_id        BIGINT  NOT NULL UNIQUE,
    completed_quiz BOOLEAN NOT NULL DEFAULT FALSE,
    points         INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE question
(
    id   BIGSERIAL PRIMARY KEY,
    image_url TEXT NOT NULL,
    text TEXT NOT NULL
);

CREATE TABLE answer
(
    id          BIGSERIAL PRIMARY KEY,
    question_id BIGINT  NOT NULL,
    text        TEXT    NOT NULL,
    is_correct  BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE
);
