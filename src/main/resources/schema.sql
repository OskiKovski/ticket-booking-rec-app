CREATE TABLE IF NOT EXISTS FILMS
(
    ID       NUMBER(2)     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    TITLE    VARCHAR2(50)  NOT NULL,
    DIRECTOR VARCHAR2(100) NOT NULL,
    DURATION NUMBER(3)     NOT NULL
);

CREATE TABLE IF NOT EXISTS ROOMS
(
    ID     NUMBER(2) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NUMBER NUMBER(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS PERSONS
(
    ID         NUMBER(2)    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME VARCHAR2(50) NOT NULL,
    SURNAME    VARCHAR2(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS SEATS
(
    ID         NUMBER(2) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ROOM_ID    NUMBER(2) NOT NULL,
    SEAT_ROW   CHAR(4)   NOT NULL,
    SEAT_PLACE NUMBER(2) NOT NULL,
    CONSTRAINT SEATS_ROOMS_FK FOREIGN KEY (ROOM_ID) REFERENCES ROOMS (ID) DEFERRABLE,
    CONSTRAINT SEATS_ROW_PLACE_UNIQUE UNIQUE (ROOM_ID, SEAT_ROW, SEAT_PLACE)
);

CREATE TABLE IF NOT EXISTS SCREENINGS
(
    ID      NUMBER(2) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FILM_ID NUMBER(2) NOT NULL,
    ROOM_ID NUMBER(2) NOT NULL,
    TIME    TIMESTAMP NOT NULL,
    CONSTRAINT SCREENINGS_FILMS_FK FOREIGN KEY (FILM_ID) REFERENCES FILMS (ID) DEFERRABLE,
    CONSTRAINT SCREENINGS_ROOMS_FK FOREIGN KEY (ROOM_ID) REFERENCES ROOMS (ID) DEFERRABLE
);

CREATE TABLE IF NOT EXISTS RESERVATIONS
(
    ID              NUMBER(2) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    PERSON_ID       NUMBER(2) NOT NULL,
    IS_PAID         BOOLEAN   NOT NULL DEFAULT FALSE,
    DISCOUNT        BOOLEAN   NOT NULL DEFAULT FALSE,
    ORDER_TIME      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    EXPIRATION_TIME TIMESTAMP NOT NULL DEFAULT DATEADD('MINUTE', 15, CURRENT_TIMESTAMP()),
    CONSTRAINT RESERVATIONS_PERSONS_FK FOREIGN KEY (PERSON_ID) REFERENCES PERSONS (ID) DEFERRABLE
);

CREATE TABLE IF NOT EXISTS TICKETS
(
    ID             NUMBER(2)    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    RESERVATION_ID NUMBER(2)    NOT NULL,
    SEAT_ID        NUMBER(2)    NOT NULL,
    SCREENING_ID   NUMBER(2)    NOT NULL,
    PRICE          NUMBER(5, 2) NOT NULL,
    CONSTRAINT TICKETS_RESERVATIONS_FK FOREIGN KEY (RESERVATION_ID) REFERENCES RESERVATIONS (ID) DEFERRABLE,
    CONSTRAINT TICKETS_SEATS_FK FOREIGN KEY (SEAT_ID) REFERENCES SEATS (ID) DEFERRABLE,
    CONSTRAINT TICKETS_SCREENINGS_FK FOREIGN KEY (SCREENING_ID) REFERENCES SCREENINGS (ID) DEFERRABLE,
    CONSTRAINT TICKETS_SCREENING_SEAT_UNIQUE UNIQUE (SCREENING_ID, SEAT_ID)
);

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 10 INCREMENT BY 1;
