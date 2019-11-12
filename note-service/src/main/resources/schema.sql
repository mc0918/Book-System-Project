create schema if not exists note;
use note;

create table if not exists note (
	note_id int not null auto_increment primary key,
    book_id int not null,
    note varchar(255)
);