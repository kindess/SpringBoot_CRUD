/*
SQLyog Ultimate v12.09 (32 bit)
MySQL - 5.1.33-community : Database - vehicle_management
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vehicle_management` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `vehicle_management`;

/*Table structure for table `authority_tb` */

DROP TABLE IF EXISTS `authority_tb`;

CREATE TABLE `authority_tb` (
  `authority_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限主键',
  `authority` varchar(50) NOT NULL COMMENT '角色(加了ROLE_前缀表示角色，没有表示权限)',
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `authority_tb` */

insert  into `authority_tb`(`authority_id`,`authority`) values (1,'ROLE_ADMIN'),(2,'ROLE_USER'),(3,'selectVehicleInfo'),(4,'insertVehicleInfo'),(5,'deleteVehicleInfo'),(6,'updateVehicleInfo');

/*Table structure for table `option_field_tb` */

DROP TABLE IF EXISTS `option_field_tb`;

CREATE TABLE `option_field_tb` (
  `field_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字段主键',
  `field_name` varchar(20) NOT NULL COMMENT '字段名称',
  `option_id` int(11) NOT NULL COMMENT '选项主键',
  PRIMARY KEY (`field_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `option_field_tb` */

insert  into `option_field_tb`(`field_id`,`field_name`,`option_id`) values (1,'黄色',1),(2,'蓝色',1),(3,'白色',1),(4,'绿色',1),(5,'江淮',2),(6,'现代',2),(7,'九龙',2),(8,'梅赛德斯-奔驰牌',2),(9,'轿车',3),(10,'小型车',3),(11,'大型车',3),(12,'商务车',3),(13,'未知',4),(14,'未检修',4),(15,'已检修',4),(16,'未年审',5),(17,'已年审',5),(18,'电',6),(19,'汽',6),(20,'舒适型',7),(21,'极品型',7),(22,'定线',8),(23,'随机',8),(24,'省际班线车',9),(25,'国际班线车',9),(26,'四川省成兴运业有限公司',10),(27,'平常易来网约车服务有限公司',10),(28,'达州市达运汽车租赁有限公司大竹分公司',10),(29,'四川省汽车运输成都公司第六分公司',10),(30,'平台',11),(31,'供应商',11);

/*Table structure for table `option_tb` */

DROP TABLE IF EXISTS `option_tb`;

CREATE TABLE `option_tb` (
  `option_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '选项主键',
  `option_name` varchar(20) NOT NULL COMMENT '选项名称',
  `option_field_name` varchar(50) NOT NULL COMMENT '选项字段名称',
  PRIMARY KEY (`option_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `option_tb` */

insert  into `option_tb`(`option_id`,`option_name`,`option_field_name`) values (1,'车牌颜色','color'),(2,'车辆厂牌','vehicleBrand'),(3,'车辆类型','vehicleType'),(4,'车辆检修状态','vehicleMaintenanceStatus'),(5,'车辆年审状态','annualReviewStatus'),(6,'车辆燃料类型','fuelType'),(7,'等级','level'),(8,'业务用途','businessPurpose'),(9,'运营范围','scopeOfBusiness'),(10,'所属供应商','subordinateSuppliers'),(11,'调度权','dispatchingRight');

/*Table structure for table `user_authority_tb` */

DROP TABLE IF EXISTS `user_authority_tb`;

CREATE TABLE `user_authority_tb` (
  `user_id` varchar(22) NOT NULL COMMENT '用户主键',
  `authority_id` int(11) NOT NULL COMMENT '权限主键'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_authority_tb` */

insert  into `user_authority_tb`(`user_id`,`authority_id`) values ('111111111 ',1),('22222',2),('111111111 ',3),('111111111 ',4),('111111111 ',5),('111111111 ',6),('22222',3);

/*Table structure for table `user_tb` */

DROP TABLE IF EXISTS `user_tb`;

CREATE TABLE `user_tb` (
  `id` varchar(22) NOT NULL COMMENT '主键',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_tb` */

insert  into `user_tb`(`id`,`username`,`password`) values ('111111111','城南花已开','123456'),('22222','binbin','123456');

/*Table structure for table `vehicle_info_tb` */

DROP TABLE IF EXISTS `vehicle_info_tb`;

CREATE TABLE `vehicle_info_tb` (
  `id` varchar(22) NOT NULL,
  `vehicle_license` varchar(20) NOT NULL COMMENT '车牌号',
  `owener` varchar(20) NOT NULL COMMENT '车辆所有人',
  `option_color_id` int(11) NOT NULL COMMENT '车牌颜色(选项)',
  `engine_number` varchar(20) NOT NULL COMMENT '发动机号',
  `vehicle_brand_id` int(11) NOT NULL COMMENT '车辆厂牌(选项)',
  `vehicle_rack_number` varchar(30) NOT NULL COMMENT '车辆机架号',
  `vehicle_model` varchar(20) NOT NULL COMMENT '车辆型号',
  `register_date` varchar(30) NOT NULL COMMENT '注册日期',
  `vehicle_type_id` int(11) NOT NULL COMMENT '车辆类型(选项)',
  `date_initial_registration` varchar(30) NOT NULL COMMENT '初次登记日期',
  `body_color` varchar(10) NOT NULL COMMENT '车身颜色',
  `vehicle_maintenance_status_id` int(11) NOT NULL COMMENT '车辆检修状态(选项)',
  `seating_capacity` int(11) NOT NULL COMMENT '核定载客位(包含驾驶员)',
  `annual_review_status_id` int(11) NOT NULL COMMENT '车辆年审状态(选项)',
  `fuel_type_id` int(11) NOT NULL COMMENT '燃料类型(选项)',
  `next_annual_inspection_time` varchar(30) NOT NULL COMMENT '下次年检时间',
  `displacement` int(11) NOT NULL COMMENT '排量',
  `level_id` int(11) NOT NULL COMMENT '等级(选项)',
  `business_purpose_id` int(11) NOT NULL COMMENT '业务用途(选项)',
  `scope_of_business_id` int(11) NOT NULL COMMENT '运营范围(选项)',
  `subordinate_suppliers_id` int(11) NOT NULL COMMENT '所属供应商(选项)',
  `seats_of_guests` int(11) NOT NULL COMMENT '载客数(不包含驾驶员)',
  `mileage` int(11) NOT NULL COMMENT '里程数',
  `driving_license_number` varchar(50) NOT NULL COMMENT '行驶证号',
  `dispatching_right_id` int(11) NOT NULL COMMENT '调度权(单选)',
  `remarks` text COMMENT '备注',
  `image_url` varchar(100) DEFAULT NULL COMMENT '车辆全身照',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `vehicle_info_tb` */

insert  into `vehicle_info_tb`(`id`,`vehicle_license`,`owener`,`option_color_id`,`engine_number`,`vehicle_brand_id`,`vehicle_rack_number`,`vehicle_model`,`register_date`,`vehicle_type_id`,`date_initial_registration`,`body_color`,`vehicle_maintenance_status_id`,`seating_capacity`,`annual_review_status_id`,`fuel_type_id`,`next_annual_inspection_time`,`displacement`,`level_id`,`business_purpose_id`,`scope_of_business_id`,`subordinate_suppliers_id`,`seats_of_guests`,`mileage`,`driving_license_number`,`dispatching_right_id`,`remarks`,`image_url`) values ('aNj2auu0h6WRYPZB4Hy46M','MLGB','啊啊啊啊啊',4,'111',7,'11111','1111','2019-7-9',11,'2019-7-9','111',13,1111,17,19,'2019-7-9',1111,20,22,24,29,11111,11,'1111111',30,'111111115555555','/upload/image/QQ图片20190614110331.png'),('yVap8yzUgcWWpuyaVFFIWw','23333','按不出',3,'333333',5,'11111','3333333','2019-7-9',9,'2019-7-9','1',13,33333,17,18,'2019-7-9',3333,21,22,25,29,3333,3333,'1111111',31,'561651','/upload/image/u=1077360284,2857506492&fm=26&gp=0.jpg'),('YVw7FMs9jX6yN0vmZkZt9M','QAQ','pbb',1,'333333',8,'3333333333','3333333','2019-7-9',11,'2019-7-9','333333',13,33333,17,18,'2019-7-9',3333,20,23,24,26,33,3333,'333',31,'23333','/upload/image/u=1077360284,2857506492&fm=26&gp=0.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
