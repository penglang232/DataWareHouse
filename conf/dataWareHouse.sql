create table monster_race(
	id integer primary key autoincrement,
	monster_name varchar(20),
	race varchar(20),
	monster_code varchar(20)
);

create table pair(
	id integer primary key autoincrement,
	father varchar(20),
	mother varchar(20),
	child varchar(20)
);
