use playdata;
SET FOREIGN_KEY_CHECKS = 0;
drop table designers;
create table designers(
	d_id int primary key,
	d_name varchar(20),
	d_phNum varchar(20),
	d_career int,
	d_rank int,
	d_count int
);
alter table designers modify column d_id int not null auto_increment;

alter table designers alter d_count set default 0;
alter table designers alter d_rank set default 3;
alter table designers modify column d_rank int default 0;
alter table designers add constraint fk_desigenrs_designers_rank foreign key (d_rank) references designers_rank(drk_id) ON DELETE NO ACTION ON UPDATE NO ACTION;

drop table customers;
create table customers(
	c_id int primary key,
	c_name varchar(20),
	c_phNum varchar(20),
	d_id int,
	c_rank int,
	c_count int,
	constraint fk_customers_designers foreign key (d_id) references designers(d_id)
);
alter table customers alter c_count set default 0;
alter table customers modify column c_id int not null auto_increment;

alter table customers alter c_rank set default 3;
alter table customers modify column c_rank int default 0;
alter table customers add constraint fk_customers_customers_rank foreign key (c_rank) references customers_rank(crk_id) ON DELETE NO ACTION ON UPDATE NO ACTION;


drop table design_record;
create table design_record(
	dr_id int primary key,
	dr_name varchar(20),
	dr_price int,
	dr_date date,
	d_id int,
	c_id int,
	constraint fk_design_record_designers foreign key (d_id) references designers(d_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
	constraint fk_design_record_customers foreign key (c_id) references customers(c_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

alter table design_record modify column dr_id int not null auto_increment; 

drop table styles;
create table styles(
	s_id int primary key,
	s_type varchar(20),
	c_id int
);
alter table styles modify column s_id int not null auto_increment;

alter table styles add constraint fk_styles_customers foreign key (c_id) references customers(c_id) ON DELETE NO ACTION ON UPDATE NO ACTION;

drop table designers_rank;
create table designers_rank(
	drk_id int primary key,
	drk_name varchar(20),
	drk_low int,
	drk_high int
);
alter table designers_rank modify column drk_id int not null auto_increment;
drop table customers_rank;
create table customers_rank(
	crk_id int primary key,
	crk_name varchar(20),
	crk_low int,
	crk_high int
);
alter table customers_rank modify column crk_id int not null auto_increment;
-- --------------------------------------------------------------------------------------------------
drop procedure designers_insert_data;
create procedure designers_insert_data(
	d_name varchar(20),
	d_phNum varchar(20),
	d_career int
)
begin
	insert into designers (d_name,d_phNum,d_career) values (d_name,d_phNum,d_career);
end;

drop procedure customers_insert_data;
create procedure customers_insert_data(
	c_name varchar(20),
	c_phNum varchar(20),
	d_id int
)
begin
	insert into customers (c_name,c_phNum,d_id) values (c_name,c_phNum,d_id);
end;

drop procedure customers_rank_insert_data;
create procedure customers_rank_insert_data(
	crk_name varchar(20),
	crk_low int,
	crk_high int
)
begin
	insert into customers_rank (crk_name,crk_low,crk_high) values (crk_name,crk_low,crk_high);
end;

drop procedure designers_rank_insert_data;
create procedure designers_rank_insert_data(
	drk_name varchar(20),
	drk_low int,
	drk_high int
)
begin
	insert into designers_rank (drk_name,drk_low,drk_high) values (drk_name,drk_low,drk_high);
end;

drop procedure design_record_insert_data;
create procedure design_record_insert_data( 
	dr_name varchar(20),
	dr_price int,
	dr_date date,
	d_id int,
	c_id int
)
begin
	insert into design_record (dr_name,dr_price,dr_date,d_id,c_id) values (dr_name,dr_price,dr_date,d_id,c_id);
end;

show procedure status;


-- --------------------------------------------------------------------------------------------------------------
call designers_rank_insert_data('일등',11,1000);
call designers_rank_insert_data('이등',6,10);
call designers_rank_insert_data('삼등',0,5);
truncate table designers_rank ;

call customers_rank_insert_data('일등',11,1000);
call customers_rank_insert_data('이등',6,10);
call customers_rank_insert_data('삼등',0,5);
truncate table customers_rank ;

select * from designers_rank;
select * from customers_rank;

call designers_insert_data('유재석','010-1234-1234',2);
call designers_insert_data('강호동','010-1111-1234',1);
call designers_insert_data('이경규','010-2222-1234',10);
call designers_insert_data('마동석','010-3333-1234',1);
call designers_insert_data('지석진','010-4444-1234',2);
call designers_insert_data('손흥민','010-5555-1234',23);
call designers_insert_data('송일국','010-6666-1234',44);
call designers_insert_data('안정환','010-7777-1234',2);
call designers_insert_data('김지원','010-8888-1234',1);
call designers_insert_data('홍길동','010-9999-1234',5);
truncate table designers;

call customers_insert_data('김연아', '010-3218-0123',1); 
call customers_insert_data('박지성', '010-9531-0123',2); 
call customers_insert_data('나미', '010-6231-8641',3); 
call customers_insert_data('하하', '010-8513-6841',4);
call customers_insert_data('박봉길', '010-6891-6453',5);
call customers_insert_data('오정세', '010-9746-5896',6);
call customers_insert_data('장첸', '010-7231-7381',7);
call customers_insert_data('가위손', '010-8932-7391',8);
call customers_insert_data('발락', '010-7128-4183',9);
call customers_insert_data('밥샵', '010-7833-5599',10);
truncate table customers;


select * from designers;
select * from customers;


-- ------------------------------------------------------------------------------
-- 시술기록 insert되기 전에 실행 : 
drop trigger design_record_trigger;
create trigger design_record_trigger
before insert
on design_record
for each row 
begin 
	update designers set d_count=d_count+1 where designers.d_id=new.d_id;
	update customers set c_count=c_count+1 where customers.c_id=new.c_id;

	update designers set d_rank=(select drk_id from designers_rank where d_count between drk_low and drk_high) where designers.d_id=new.d_id;
	update customers set c_rank=(select crk_id from customers_rank where c_count between crk_low and crk_high) where customers.c_id=new.c_id;
end;


truncate table design_record;
call design_record_insert_data('파마',50000,'2022-02-02',1,1);
call design_record_insert_data('커트',10000,'2022-02-27',2,2);
call design_record_insert_data('케어',30000,'2022-02-28',10,2);
call design_record_insert_data('커트',10000,'2022-03-27',2,3);
call design_record_insert_data('파마',50000,'2022-03-28',3,3);
call design_record_insert_data('파마',50000,'2022-04-14',4,1);
call design_record_insert_data('염색',120000,'2022-04-27',8,9);
call design_record_insert_data('드라이',20000,'2022-05-17',5,7);
call design_record_insert_data('드라이',20000,'2022-05-20',6,5);
call design_record_insert_data('염색',12000,'2022-05-28',4,10);

call design_record_insert_data('커트',10000,'2022-05-29',2,2);
call design_record_insert_data('커트',10000,'2022-05-29',4,3);
call design_record_insert_data('커트',10000,'2022-06-01',2,2);
call design_record_insert_data('커트',10000,'2022-06-01',4,3);
call design_record_insert_data('커트',10000,'2022-06-02',2,2);
call design_record_insert_data('커트',10000,'2022-06-02',4,3);
call design_record_insert_data('커트',10000,'2022-06-03',2,2);
call design_record_insert_data('커트',10000,'2022-06-04',2,2);
call design_record_insert_data('커트',10000,'2022-06-05',2,2);
call design_record_insert_data('커트',10000,'2022-06-05',2,2);

select * from designers;
select * from customers;
select * from design_record;
select * from designers_rank;
select * from customers_rank;


-- 1. 2022년 4월 이후 시술기록들 중에서 드라이의 총 횟수는?
-- 2. 2022년의 총 매출은?
-- 3. 커트 한 사람 중에서 가장 시술을 많이 받은 사람은?
-- 4. 디자이너 번호가 4인 디자이너를 구하라
-- 5. 3번 손님의 시술가격 합계는?
-- 6. 직급이 2인 디자이너를 구하라
-- 7. 등수별 디자이너들을 검색해라
-- 8. 가장 최근 시술기록을 검색해라
-- 9. 총 시술가격이 가장 큰 손님을 검색해라
-- 10.

-- 1. 2022년 4월 이후 시술기록들 중에서 드라이의 총 횟수는?
select * from design_record where dr_name='드라이' and dr_date>='2022-04-01';

-- 2. 2022년의 총 매출은?
select sum(dr_price) from design_record where year(dr_date)='2022';

-- 3. 커트 한 사람 중에서 가장 시술을 많이 받은 사람은?
select count(*),c_id from design_record where dr_name='커트' group by c_id;
select max(A1) from (select count(*) A1,c_id from design_record where dr_name='커트' group by c_id) A;
select * from design_record where dr_name='커트' group by c_id;

select count(*) from design_record group by d_id;

select * from designers
where d_id=(select d_id from (select count(*) B1,d_id from design_record where dr_name='커트' group by d_id) B
where B1=(select max(A1) from (select count(*) A1,d_id from design_record where dr_name='커트' group by d_id) A));

-- 4. 디자이너 번호가 4인 디자이너를 구하라
select * from designers where d_id=4;

-- 5. 3번 손님의 시술가격 합계는?
select sum(dr_price) from design_record where c_id=3;

-- 6. 직급이 2인 디자이너를 구하라
select * from designers where d_rank=2;

-- 7. 직급별 디자이너들을 검색해라(정렬)
select * from designers order by d_rank;

-- 8. 가장 최근 시술기록을 검색해라
select * from design_record where dr_date=(select max(dr_date) from design_record);

-- 9. 총 시술가격이 가장 큰 손님을 검색해라
select * from customers c
where c_id=(select id from (select c_id as id,sum(dr_price) as price from design_record dr group by c_id) A where price=(select max(price)  from (select c_id as id,sum(dr_price) as price from design_record dr group by c_id) A));

-- 10. 제일 오래 전 파마한 인원 날짜 검색해라
select min(dr_date) from design_record where dr_name='파마';
select * from design_record where dr_name='파마' having min(dr_date);
