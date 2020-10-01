DROP TABLE IF EXISTS currency CASCADE;
SET CLIENT_ENCODING TO 'utf-8';
CREATE TABLE currency (
                          id VARCHAR(60),
                          numCode VARCHAR(60),
                          charCode VARCHAR(60),
                          nominal INTEGER,
                          name VARCHAR(60),
                          value VARCHAR(60)
);
INSERT INTO currency
SELECT
    (xpath('//Valute/@ID', myTempTable.myXmlColumn))[1]::text AS id
     ,(xpath('//NumCode/text()', myTempTable.myXmlColumn))[1]::text AS numCode
     ,(xpath('//CharCode/text()', myTempTable.myXmlColumn))[1]::text AS charCode
     ,(xpath('//Nominal/text()', myTempTable.myXmlColumn))[1]::text::int AS nominal
     ,(xpath('//Name/text()', myTempTable.myXmlColumn))[1]::text AS name
     ,(xpath('//Value/text()', myTempTable.myXmlColumn))[1]::text AS value
FROM unnest(
             xpath
                 (    '//ValCurs/Valute'
                 ,XMLPARSE(DOCUMENT convert_from(pg_read_binary_file('C:\javaProjects\CurrencyConverter\src\main\resources\static\XML_daily.asp'), 'UTF-8'))
                 )
         ) AS myTempTable(myXmlColumn)
;
INSERT  INTO currency
    VALUES ('R1',1,'RUS',1,'Российский рубль','1');

create table if not exists Converted_Currency (
                                    id SERIAL,
                                    amount_before_exchange decimal,
                                    amount_after_exchange decimal,
                                    init_currency varchar(60),
                                    target_currency varchar(60),
                                    date timestamp not null
);


