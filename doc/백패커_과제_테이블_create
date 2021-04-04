create table USERS
(
	ID varchar(20) not null,
	NAME varchar(20) not null,
	NICK_NAME varchar(30) not null,
	PASSWORD varchar(100) not null,
	PHONE_NUMBER varchar(20) not null,
	EMAIL varchar(100) not null,
	SEX char null
)
comment '회원테이블';

alter table users
	add constraint users_pk
		primary key (ID);
	
	
create table ORDERS
(
  USER_ID varchar(20) not null,
	PRODUCT_NAME varchar(100) not null,
	PAYMENT_DATE datetime not null
)
comment '주문속성';

alter table orders modify USER_ID varchar(20) not null first;

alter table orders
	add constraint orders_users_ID_fk
		foreign key (USER_ID) references users (ID);