create table inverted_index
(
    word       varchar(256),
    message_id bigint,
    primary key (word, message_id)
)