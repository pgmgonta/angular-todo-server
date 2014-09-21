create table todos (
    id          BIGINT(20)    NOT NULL AUTO_INCREMENT
   ,author      VARCHAR(256)  NOT NULL
   ,description VARCHAR(256)  NOT NULL
   ,due_date    DATE          NOT NULL
   ,PRIMARY KEY(id)
);
