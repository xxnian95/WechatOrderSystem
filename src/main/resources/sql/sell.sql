create table `product_info`
(
    `product_id`    varchar(32) not null,
    `product_name`  varchar(64) not null comment '商品名称',
    `product_price` decimal(8, 2) not null comment '单价',
    `product_stock` int not null comment '库存',
    `product_description` varchar(64) comment '描述',
    `product_icon` varchar(512) comment '小图',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
#     ON UPDATE可使字段由MySQL自动更新，不用写在程序里
    `update_time` timestamp not null default current_timestamp on update
        current_timestamp comment '修改时间',
    primary key (`product_id`)
) comment '商品表';