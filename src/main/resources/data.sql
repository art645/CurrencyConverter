SET CLIENT_ENCODING TO 'utf-8';
CREATE TABLE if not exists currency (
                          id VARCHAR(60),
                          numCode VARCHAR(60),
                          charCode VARCHAR(60),
                          nominal INTEGER,
                          name VARCHAR(60),
                          value VARCHAR(60)
);
INSERT  INTO currency (id, numCode, charCode, nominal, name, value)
    VALUES ('R1',1,'RUS',1,'Российский рубль','1');

create table if not exists Converted_Currency (
                                    id SERIAL,
                                    amount_before_exchange decimal,
                                    amount_after_exchange decimal,
                                    init_currency varchar(60),
                                    target_currency varchar(60),
                                    date timestamp not null
);


