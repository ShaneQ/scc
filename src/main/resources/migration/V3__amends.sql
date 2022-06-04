UPDATE color
SET color.NAME = 'white'
WHERE color.ID = 2;


UPDATE color
SET color.NAME = 'grey'
WHERE color.ID = 3;

UPDATE color
SET color.NAME = 'print'
WHERE color.ID = 13;

UPDATE color
SET color.NAME = 'gold'
WHERE color.ID = 14;

UPDATE color
SET color.NAME = 'silver'
WHERE color.ID = 15;



ALTER TABLE
    `booking_request`
MODIFY COLUMN
    `STATUS` enum(
        'WAITING_COLLECTION','ACTIVE','WAITING_RETURN', 'COMPLETE','LATE_RETURN'
    )