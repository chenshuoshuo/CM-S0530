/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     2020/5/26 20:57:03                           */
/*==============================================================*/

--模式创建
CREATE SCHEMA IF NOT EXISTS gns;
CREATE SCHEMA IF NOT EXISTS portal;

/*==============================================================*/
/* Table: gns_academy                                           */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_academy (
   academy_code         SERIAL               not null,
   school_id            INT4                 null,
   academy_name         VARCHAR(255)         null,
   location             geometry             null,
   update_time          TIMESTAMP                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_ACADEMY primary key (academy_code)
);

comment on table gns.gns_academy is
'院系信息：gns_academy';

comment on column gns.gns_academy.academy_code is
'院系编号：academy_code';

comment on column gns.gns_academy.school_id is
'学校ID：school_id';

comment on column gns.gns_academy.academy_name is
'院系名称：academy_name';

comment on column gns.gns_academy.location is
'院系位置：location';

comment on column gns.gns_academy.update_time is
'更新时间：update_time';

comment on column gns.gns_academy.order_id is
'排序：order_id';

comment on column gns.gns_academy.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_access_record                                     */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_access_record (
   record_id            uuid                 not null,
   user_id              uuid                 null,
   ip                   VARCHAR(128)         null,
   create_time          TIMESTAMP            null,
   create_date          VARCHAR(50)          null,
   create_month         VARCHAR(50)          null,
   create_hour          VARCHAR(50)          null,
   constraint PK_GNS_ACCESS_RECORD primary key (record_id)
);

comment on table gns.gns_access_record is
'系统使用记录：gns_access_record';

comment on column gns.gns_access_record.record_id is
'记录ID：record_id';

comment on column gns.gns_access_record.user_id is
'用户ID：user_id';

comment on column gns.gns_access_record.ip is
'IP地址：ip';

comment on column gns.gns_access_record.create_time is
'访问时间：create_time';

comment on column gns.gns_access_record.create_date is
'访问日期：create_date';

comment on column gns.gns_access_record.create_month is
'访问月份：create_month';

comment on column gns.gns_access_record.create_hour is
'访问小时：create_hour';

/*==============================================================*/
/* Table: gns_achievement                                       */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_achievement (
   achievement_id       SERIAL               not null,
   school_id            INT4                 not null,
   achievement_name     VARCHAR(50)          null,
   achieved_icon        VARCHAR(1024)        null,
   not_achieved_icon    VARCHAR(1024)        null,
   brief                VARCHAR(100)         null,
   condition            VARCHAR(100)         null,
   update_time          TIMESTAMP                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_ACHIEVEMENT primary key (achievement_id, school_id)
);

comment on table gns.gns_achievement is
'成就信息：gns_achievement';

comment on column gns.gns_achievement.achievement_id is
'成就编号：achievement_id';

comment on column gns.gns_achievement.school_id is
'学校ID：school_id';

comment on column gns.gns_achievement.achievement_name is
'成就名称：achievement_name';

comment on column gns.gns_achievement.achieved_icon is
'达成图标：achieved_icon';

comment on column gns.gns_achievement.not_achieved_icon is
'未达成图标：not_achieved_icon';

comment on column gns.gns_achievement.brief is
'成就简介：brief';

comment on column gns.gns_achievement.condition is
'达成条件：condition';

comment on column gns.gns_achievement.update_time is
'更新时间：update_time';

comment on column gns.gns_achievement.order_id is
'排序：order_id';

comment on column gns.gns_achievement.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_achievement_reach                                 */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_achievement_reach (
   id                   SERIAL               not null,
   user_id              uuid                 null,
   achievement_id       INT4                 null,
   school_id            INT4                 null,
   reach_time           TIMESTAMP            null,
   constraint PK_GNS_ACHIEVEMENT_REACH primary key (id)
);

comment on table gns.gns_achievement_reach is
'成就获取记录：gns_achievement_reach';

comment on column gns.gns_achievement_reach.id is
'记录ID：id';

comment on column gns.gns_achievement_reach.user_id is
'用户ID：user_id';

comment on column gns.gns_achievement_reach.achievement_id is
'成就编号：achievement_id';

comment on column gns.gns_achievement_reach.school_id is
'学校ID：school_id';

comment on column gns.gns_achievement_reach.reach_time is
'达成时间：reach_time';

/*==============================================================*/
/* Table: gns_application                                       */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_application (
   application_id       SERIAL               not null,
   parent_id            INT4                 NULL,
   school_id            INT4                 null,
   application_name     VARCHAR(50)          null,
   en_name              VARCHAR(50)          null,
   application_open     BOOL                 null,
   logo                 VARCHAR(1024)        null,
   clicked_logo         VARCHAR(1024)        null,
   qr_code              VARCHAR(1024)        null,
   open_url             VARCHAR(1024)        null,
   preset               BOOLEAN              null,
   update_time          TIMESTAMP                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_APPLICATION primary key (application_id)
);

comment on table gns.gns_application is
'迎新应用管理：gns_application';

comment on column gns.gns_application.application_id is
'应用ID：application_id';

comment on column gns.gns_application.parent_id is
'父应用ID：parent_id';

comment on column gns.gns_application.school_id is
'学校ID：school_id';

comment on column gns.gns_application.application_name is
'应用名称：application_name';

comment on column gns.gns_application.en_name is
'应用英文名称：en_name';

comment on column gns.gns_application.application_open is
'开启：application_open';

comment on column gns.gns_application.logo is
'应用图标：logo';

comment on column gns_application.clicked_logo is
'应用点击图标：clicked_logo';

comment on column gns.gns_application.qr_code is
'应用二维码图片：qr_code';

comment on column gns.gns_application.open_url is
'应用地址：open_url';

comment on column gns.gns_application.preset is
'是否为预设应用：preset';

comment on column gns.gns_application.update_time is
'更新时间：update_time';

comment on column gns.gns_application.order_id is
'排序：order_id';

comment on column gns.gns_application.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_application_use                                   */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_application_use (
   record_id            uuid                 not null,
   application_id       INT4                 null,
   user_id              uuid                 null,
   create_time          TIMESTAMP            null,
   constraint PK_GNS_APPLICATION_USE primary key (record_id)
);

comment on table gns.gns_application_use is
'迎新应用使用记录：gns_application_use';

comment on column gns.gns_application_use.record_id is
'记录ID：record_id';

comment on column gns.gns_application_use.application_id is
'应用ID：application_id';

comment on column gns.gns_application_use.user_id is
'用户ID：user_id';

comment on column gns.gns_application_use.create_time is
'使用时间：create_time';

/*==============================================================*/
/* Table: gns_campus_info                                       */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_campus_info (
   campus_code          INT4                 not null,
   school_id            INT4                 null,
   campus_name          VARCHAR(255)         null,
   raster_zoom_code     INT4                 null,
   vector_zoom_code     INT4                 null,
   gate_lng_lat         VARCHAR(100)         null,
   electronic_fence     geometry             null,
   campus_description   VARCHAR(255)         null,
   roam_url             VARCHAR(1024)        null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_CAMPUS_INFO primary key (campus_code)
);

comment on table gns.gns_campus_info is
'校区信息：gns_campus_info';

comment on column gns.gns_campus_info.campus_code is
'校区编号：campus_code';

comment on column gns.gns_campus_info.school_id is
'学校ID：school_id';

comment on column gns.gns_campus_info.campus_name is
'校区名称：campus_name';

comment on column gns.gns_campus_info.raster_zoom_code is
'三维区域ID：raster_zoom_code';

comment on column gns.gns_campus_info.vector_zoom_code is
'二维区域ID：vector_zoom_code';

comment on column gns.gns_campus_info.gate_lng_lat is
'校门坐标：gate_lng_lat';

comment on column gns_campus_info.electronic_fence is
'电子围栏：electronic_fence';

comment on column gns.gns_campus_info.campus_description is
'校区描述：campus_description';

comment on column gns.gns_campus_info.roam_url is
'全景地址：roam_url';

comment on column gns.gns_campus_info.order_id is
'排序：order_id';

comment on column gns.gns_campus_info.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_club                                              */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_club (
   club_id              SERIAL               not null,
   campus_code          INT4                 null,
   club_name            VARCHAR(50)          null,
   club_logo            VARCHAR(1024)        null,
   location             geometry             null,
   description          TEXT                 null,
   click                INT4                 null,
   update_time          TIMESTAMP                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_CLUB primary key (club_id)
);

comment on table gns.gns_club is
'社团信息：gns_club';

comment on column gns.gns_club.club_id is
'社团ID：club_id';

comment on column gns.gns_club.campus_code is
'校区编号：campus_code';

comment on column gns.gns_club.club_name is
'社团名称：club_name';

comment on column gns.gns_club.club_logo is
'社团LOGO：club_logo';

comment on column gns.gns_club.location is
'社团位置：location';

comment on column gns.gns_club.description is
'社团介绍：description';

comment on column gns.gns_club.click is
'点击次数：click';

comment on column gns.gns_club.update_time is
'更新时间：update_time';

comment on column gns.gns_club.order_id is
'排序：order_id';

comment on column gns.gns_club.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_feedback                                          */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_feedback (
   feedback_id          SERIAL               not null,
   user_id              uuid                 null,
   nickname             VARCHAR(255)         null,
   open_id              VARCHAR(255)         null,
   content              TEXT                 null,
   create_time          TIMESTAMP            null,
   constraint PK_GNS_FEEDBACK primary key (feedback_id)
);

comment on table gns.gns_feedback is
'用户反馈：gns_feedback';

comment on column gns.gns_feedback.feedback_id is
'反馈信息ID：feedback_id';

comment on column gns.gns_feedback.user_id is
'用户ID：user_id';

comment on column gns.gns_feedback.nickname is
'微信昵称：nickname';

comment on column gns.gns_feedback.open_id is
'用户账号：open_id';

comment on column gns.gns_feedback.content is
'反馈内容：content';

comment on column gns.gns_feedback.create_time is
'反馈时间：create_time';

/*==============================================================*/
/* Table: gns_group_photo                                       */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_group_photo (
   sign_id              SERIAL               not null,
   user_id              uuid                 null,
   landmark_id          INT8                 null,
   landmark_name        VARCHAR(255)         null,
   landmark_type        VARCHAR(50)          null,
   photo_url            VARCHAR(1024)        null,
   create_time          TIMESTAMP            null,
   constraint PK_GNS_GROUP_PHOTO primary key (sign_id)
);

comment on table gns.gns_group_photo is
'留影记录：gns_group_photo';

comment on column gns.gns_group_photo.sign_id is
'记录ID：sign_id';

comment on column gns.gns_group_photo.user_id is
'用户ID：user_id';

comment on column gns.gns_group_photo.landmark_id is
'地标点ID：landmark_id';

comment on column gns.gns_group_photo.landmark_name is
'地标名称：landmark_name';

comment on column gns.gns_group_photo.landmark_type is
'地标点类型：landmark_type';

comment on column gns.gns_group_photo.photo_url is
'照片地址：photo_url';

comment on column gns.gns_group_photo.create_time is
'合影时间：create_time';

/*==============================================================*/
/* Table: gns_guide                                             */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_guide (
   guide_id             SERIAL               not null,
   studnet_type_code    INT4                 null,
   campus_code          INT4                 null,
   title                VARCHAR(50)          null,
   content              TEXT                 null,
   lng_lat              geometry             null,
   raster_lng_lat       geometry             null,
   update_time          TIMESTAMP                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_GUIDE primary key (guide_id)
);

comment on table gns.gns_guide is
'新生引导：gns_guide';

comment on column gns.gns_guide.guide_id is
'引导ID：guide_id';

comment on column gns.gns_guide.studnet_type_code is
'类型编号：studnet_type_code';

comment on column gns.gns_guide.campus_code is
'校区编号：campus_code';

comment on column gns.gns_guide.title is
'引导标题：title';

comment on column gns.gns_guide.content is
'正文：content';

comment on column gns.gns_guide.lng_lat is
'引导点坐标：lng_lat';

comment on column gns.gns_guide.raster_lng_lat is
'引导点三维坐标：raster_lng_lat';

comment on column gns.gns_guide.update_time is
'更新时间：update_time';

comment on column gns.gns_guide.order_id is
'排序：order_id';

comment on column gns.gns_guide.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_helper                                            */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_helper (
   helper_id            SERIAL               not null,
   type_code            INT4                 not null,
   title                VARCHAR(50)          null,
   contact              VARCHAR(50)          null,
   update_time          TIMESTAMP                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_HELPER primary key (helper_id)
);

comment on table gns.gns_helper is
'通讯录信息：gns_helper';

comment on column gns.gns_helper.helper_id is
'通讯录ID：helper_id';

comment on column gns.gns_helper.type_code is
'类型编号：type_code';

comment on column gns.gns_helper.title is
'标题：title';

comment on column gns.gns_helper.contact is
'联系方式：contact';

comment on column gns.gns_helper.update_time is
'更新时间：update_time';

comment on column gns.gns_helper.order_id is
'排序：order_id';

comment on column gns.gns_helper.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_helper_type                                       */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_helper_type (
   type_code            SERIAL               not null,
   school_id            INT4                 null,
   type_name            VARCHAR(50)          null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_HELPER_TYPE primary key (type_code)
);

comment on table gns.gns_helper_type is
'通讯录分类：gns_helper_type';

comment on column gns.gns_helper_type.type_code is
'类型编号：type_code';

comment on column gns.gns_helper_type.school_id is
'学校ID：school_id';

comment on column gns.gns_helper_type.type_name is
'类型名称：type_name';

comment on column gns.gns_helper_type.order_id is
'排序：order_id';

comment on column gns.gns_helper_type.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_interaction_statistic                             */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_interaction_statistic (
   statistic_id         SERIAL               not null,
   school_id            INT4                 null,
   statistic_name       VARCHAR(50)          null,
   statistic_data       INT4                 null,
   constraint PK_GNS_INTERACTION_STATISTIC primary key (statistic_id)
);

comment on table gns.gns_interaction_statistic is
'互动使用统计：gns_interaction_statistic';

comment on column gns.gns_interaction_statistic.statistic_id is
'互动ID：statistic_id';

comment on column gns.gns_interaction_statistic.school_id is
'学校ID：school_id';

comment on column gns.gns_interaction_statistic.statistic_name is
'互动内容：statistic_name';

comment on column gns.gns_interaction_statistic.statistic_data is
'统计次数：statistic_data';

/*==============================================================*/
/* Table: gns_manage_log                                        */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_manage_log (
   log_id               uuid                 not null,
   school_id            INT4                 null,
   create_time          TIMESTAMP            null,
   description          VARCHAR(255)         null,
   method               VARCHAR(255)         null,
   source               VARCHAR(255)         null,
   user_name            VARCHAR(255)         null,
   rule                 VARCHAR(50)          null,
   constraint PK_GNS_MANAGE_LOG primary key (log_id)
);

comment on table gns.gns_manage_log is
'管理操作日志：gns_manage_log';

comment on column gns.gns_manage_log.log_id is
'日志ID：log_id';

comment on column gns.gns_manage_log.school_id is
'学校ID：school_id';

comment on column gns.gns_manage_log.create_time is
'操作时间：create_time';

comment on column gns.gns_manage_log.description is
'描述：description';

comment on column gns.gns_manage_log.method is
'操作类型：method';

comment on column gns.gns_manage_log.source is
'来源：source';

comment on column gns.gns_manage_log.user_name is
'操作用户姓名：user_name';

comment on column gns.gns_manage_log.rule is
'操作用户角色：rule';

/*==============================================================*/
/* Table: gns_manage_resource                                   */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_manage_resource (
   authority_id         SERIAL               not null,
   parent_id            INT4                 null,
   content              VARCHAR(255)         null,
   name                 VARCHAR(255)         null,
   route                VARCHAR(255)         null,
   type                 VARCHAR(255)         null,
   icon                 TEXT                 null,
   enabled              BOOL                 null,
   http_method          TEXT                 null,
   file_path            TEXT                 null,
   target_user_role     VARCHAR(64)[]        null,
   specify_user_id      VARCHAR(64)[]        null,
   manage               BOOL                 null,
   constraint PK_GNS_MANAGE_RESOURCE primary key (authority_id)
);

comment on table gns.gns_manage_resource is
'管理资源信息：gns_manage_resource';

comment on column gns.gns_manage_resource.parent_id is
'父级ID：parent_id';

comment on column gns.gns_manage_resource.authority_id is
'资源ID：authority_id';

comment on column gns.gns_manage_resource.content is
'英文名称：content';

comment on column gns.gns_manage_resource.name is
'中文名称：name';

comment on column gns.gns_manage_resource.route is
'路由路径：route';

comment on column gns.gns_manage_resource.type is
'资源类型：type';

comment on column gns.gns_manage_resource.icon is
'图标：icon';

comment on column gns.gns_manage_resource.enabled is
'是否可用：enabled';

comment on column gns.gns_manage_resource.http_method is
'http请求方式：http_method';

comment on column gns.gns_manage_resource.file_path is
'文件路径：file_path';

comment on column gns.gns_manage_resource.target_user_role is
'面向角色：target_user_role';

comment on column gns.gns_manage_resource.specify_user_id is
'指定用户：specify_user_id';

comment on column gns.gns_manage_resource.manage is
'是否有管理系统：manage';

/*==============================================================*/
/* Table: gns_manage_role                                       */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_manage_role (
   rule_id              SERIAL               not null,
   school_id            INT4                 null,
   content              VARCHAR(255)         null,
   name                 VARCHAR(255)         null,
   update_time          TIMESTAMP            null,
   constraint PK_GNS_MANAGE_ROLE primary key (rule_id)
);

comment on table gns.gns_manage_role is
'管理角色：gns_manage_role';

comment on column gns.gns_manage_role.rule_id is
'角色ID：rule_id';

comment on column gns.gns_manage_role.school_id is
'学校ID：school_id';

comment on column gns.gns_manage_role.content is
'英文名：content';

comment on column gns.gns_manage_role.name is
'中文名：name';

comment on column gns.gns_manage_role.update_time is
'更新时间：update_time';

/*==============================================================*/
/* Table: gns_manage_role_resource                              */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_manage_role_resource (
   rule_id              INT4                 not null,
   authority_id         INT4                 not null,
   constraint PK_GNS_MANAGE_ROLE_RESOURCE primary key (rule_id, authority_id)
);

comment on table gns.gns_manage_role_resource is
'管理资源-角色关联表：gns_manage_role_resource';

comment on column gns.gns_manage_role_resource.rule_id is
'角色ID：rule_id';

comment on column gns.gns_manage_role_resource.authority_id is
'资源ID：authority_id';

/*==============================================================*/
/* Table: gns_manage_user                                       */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_manage_user (
   user_id              SERIAL               not null,
   school_id            INT4                 null,
   open_id              VARCHAR(255)         null,
   user_code            VARCHAR(255)         null,
   pass_word            VARCHAR(255)         null,
   cas_ticket           VARCHAR(255)         null,
   user_group           VARCHAR(255)         null,
   is_admin             BOOL                 null,
   head_path            VARCHAR(255)         null,
   head_url             VARCHAR(255)         null,
   user_name            VARCHAR(64)          null,
   update_time          TIMESTAMP            null,
   constraint PK_GNS_MANAGE_USER primary key (user_id)
);

comment on table gns.gns_manage_user is
'管理用户表：gns_manage_user';

comment on column gns.gns_manage_user.user_id is
'用户ID：user_id';

comment on column gns.gns_manage_user.school_id is
'学校ID：school_id';

comment on column gns.gns_manage_user.open_id is
'微信openId：open_id';

comment on column gns.gns_manage_user.user_code is
'用户账号：user_code';

comment on column gns.gns_manage_user.pass_word is
'密码：pass_word';

comment on column gns.gns_manage_user.cas_ticket is
'认证ticket：cas_ticket';

comment on column gns.gns_manage_user.user_group is
'用户组：user_group';

comment on column gns.gns_manage_user.is_admin is
'是否是管理员：is_admin';

comment on column gns.gns_manage_user.head_path is
'头像保存路径：head_path';

comment on column gns.gns_manage_user.head_url is
'头像访问路径：head_url';

comment on column gns.gns_manage_user.user_name is
'用户姓名：user_name';

comment on column gns.gns_manage_user.update_time is
'更新时间：update_time';


/*==============================================================*/
/* Table: gns_manage_user_role                                  */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_manage_user_role (
   rule_id              INT4                 not null,
   user_id              INT4                 not null,
   constraint PK_GNS_MANAGE_USER_ROLE primary key (rule_id, user_id)
);

comment on table gns.gns_manage_user_role is
'管理用户-角色关联表：gns_manage_user_role';

comment on column gns.gns_manage_user_role.rule_id is
'角色ID：rule_id';

comment on column gns.gns_manage_user_role.user_id is
'用户ID：user_id';

/*==============================================================*/
/* Table: gns_map_use                                           */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_map_use (
   record_id            uuid                 not null,
   gns_user_id          uuid                 null,
   record_type          INT4                 null,
   map_element_name     VARCHAR(255)         null,
   create_time          TIMESTAMP            null,
   constraint PK_GNS_MAP_USE primary key (record_id)
);

comment on table gns.gns_map_use is
'地图使用记录：gns_map_use';

comment on column gns.gns_map_use.record_id is
'记录ID：record_id';

comment on column gns.gns_map_use.gns_user_id is
'用户ID：user_id';

comment on column gns.gns_map_use.record_type is
'使用类型：record_type，1点击，2导航起点，3导航终点，';

comment on column gns.gns_map_use.map_element_name is
'地图元素名称：map_element_name';

comment on column gns.gns_map_use.create_time is
'使用时间：create_time';

/*==============================================================*/
/* Table: gns_push_message                                      */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_push_message (
   push_id              uuid                 not null,
   user_id              uuid                 null,
   title                VARCHAR(255)         null,
   valid                BOOL                 NULL,
   push_type            INT4                 null,
   navigation_name      VARCHAR(255)         null,
   navigation_location  geometry             null,
   landmark_id          INT8                 null,
   landmark_is_polygon  BOOL                 null,
   create_time          TIMESTAMP            null,
   constraint PK_GNS_PUSH_MESSAGE primary key (push_id)
);

comment on table gns.gns_push_message is
'推送消息：gns_push_message';

comment on column gns.gns_push_message.push_id is
'推送ID：push_id';

comment on column gns.gns_push_message.user_id is
'用户ID：user_id';

comment on column gns.gns_push_message.title is
'标题：title';

comment on column gns.gns_push_message.valid is
'是否有效：valid';

comment on column gns.gns_push_message.push_type is
'推送类型：push_type，1去宿舍，2去院系，3打卡';

comment on column gns.gns_push_message.navigation_name is
'导航名称：navigation_name';

comment on column gns.gns_push_message.navigation_location is
'导航坐标：navigation_location';

comment on column gns.gns_push_message.landmark_id is
'打卡地标ID：landmark_id';

comment on column gns.gns_push_message.landmark_is_polygon is
'打卡地标是否是面：landmark_is_polygon';

comment on column gns.gns_push_message.create_time is
'推送时间：create_time';

/*==============================================================*/
/* Table: gns_reception_place                                   */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_reception_place (
   place_id             SERIAL               not null,
   type_code            INT4                 null,
   campus_code          INT4                 null,
   title                VARCHAR(50)          null,
   content              TEXT                 null,
   lng_lat              geometry             null,
   update_time          TIMESTAMP                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_RECEPTION_PLACE primary key (place_id)
);

comment on table gns.gns_reception_place is
'新生接待点位：gns_reception_place';

comment on column gns.gns_reception_place.place_id is
'接待点位ID：place_id';

comment on column gns.gns_reception_place.type_code is
'类型编号：type_code';

comment on column gns.gns_reception_place.campus_code is
'校区编号：campus_code';

comment on column gns.gns_reception_place.title is
'标题：title';

comment on column gns.gns_reception_place.content is
'正文：content';

comment on column gns.gns_reception_place.lng_lat is
'坐标：lng_lat';

comment on column gns.gns_reception_place.update_time is
'更新时间：update_time';

comment on column gns.gns_reception_place.order_id is
'排序：order_id';

comment on column gns.gns_reception_place.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_reception_type                                    */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_reception_type (
   type_code            SERIAL               not null,
   school_id            INT4                 null,
   type_name            VARCHAR(50)          null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_RECEPTION_TYPE primary key (type_code)
);

comment on table gns.gns_reception_type is
'迎新接待点类型：gns_reception_type';

comment on column gns.gns_reception_type.type_code is
'类型编号：type_code';

comment on column gns.gns_reception_type.school_id is
'学校ID：school_id';

comment on column gns.gns_reception_type.type_name is
'类型名称：type_name';

comment on column gns.gns_reception_type.order_id is
'排序：order_id';

comment on column gns.gns_reception_type.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_registering_notice                                */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_registering_notice (
   notice_id            SERIAL               not null,
   school_id            INT4                 null,
   title                VARCHAR(50)          null,
   content              TEXT                 null,
   update_time          TIMESTAMP                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_REGISTERING_NOTICE primary key (notice_id)
);

comment on table gns.gns_registering_notice is
'报到须知：gns_registering_notice';

comment on column gns.gns_registering_notice.notice_id is
'报到须知ID：notice_id';

comment on column gns.gns_registering_notice.school_id is
'学校ID：school_id';

comment on column gns.gns_registering_notice.title is
'标题：title';

comment on column gns.gns_registering_notice.content is
'正文：content';

comment on column gns.gns_registering_notice.update_time is
'更新时间：update_time';

comment on column gns.gns_registering_notice.order_id is
'排序：order_id';

comment on column gns.gns_registering_notice.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_school                                            */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_school (
   school_id            SERIAL               not null,
   school_name          VARCHAR(255)         null,
   access_time          TIMESTAMP            null,
   audio_url            VARCHAR(1024)        null,
   video_url            VARCHAR(1024)        null,
   profile              TEXT                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_SCHOOL primary key (school_id)
);

comment on table gns.gns_school is
'学校信息：gns_school';

comment on column gns.gns_school.school_id is
'学校ID：school_id';

comment on column gns.gns_school.school_name is
'学校名称：school_name';

comment on column gns.gns_school.access_time is
'接入时间：access_time';

comment on column gns.gns_school.audio_url is
'音频：audio_url';

comment on column gns.gns_school.video_url is
'视频：video_url';

comment on column gns.gns_school.profile is
'学校介绍：profile';

comment on column gns.gns_school.order_id is
'排序：order_id';

comment on column gns.gns_school.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_sign                                              */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_sign (
   sign_id              SERIAL               not null,
   user_id              uuid                 null,
   landmark_id          INT8                 null,
   landmark_name        VARCHAR(255)         null,
   landmark_type        VARCHAR(50)          null,
   create_time          TIMESTAMP            null,
   constraint PK_GNS_SIGN primary key (sign_id)
);

comment on table gns.gns_sign is
'打卡记录：gns_sign';

comment on column gns.gns_sign.sign_id is
'记录ID：sign_id';

comment on column gns.gns_sign.user_id is
'用户ID：user_id';

comment on column gns.gns_sign.landmark_id is
'打卡地标点ID：landmark_id';

comment on column gns.gns_sign.landmark_name is
'打卡地标名称：landmark_name';

comment on column gns.gns_sign.landmark_type is
'打卡地标点类型：landmark_type';

comment on column gns.gns_sign.create_time is
'打卡时间：create_time';

/*==============================================================*/
/* Table: gns_store                                             */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_store (
   sore_id              SERIAL               not null,
   name                 VARCHAR(50)          null,
   constraint PK_GNS_STORE primary key (sore_id)
);

comment on table gns.gns_store is
'产品配置分类信息：gns_stor';

comment on column gns.gns_store.sore_id is
'配置分类ID：sore_id';

comment on column gns.gns_store.name is
'配置分类名称：name';

/*==============================================================*/
/* Table: gns_store_item                                        */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_store_item (
   item_id              SERIAL               not null,
   sore_id              INT4                 null,
   school_id            INT4                 null,
   item_key             VARCHAR(255)         null,
   item_value           TEXT                 null,
   content_type         VARCHAR(50)          null,
   constraint PK_GNS_STORE_ITEM primary key (item_id)
);

comment on table gns.gns_store_item is
'产品配置信息：gns_store_item';

comment on column gns.gns_store_item.item_id is
'配置信息ID：item_id';

comment on column gns.gns_store_item.sore_id is
'配置分类ID：sore_id';

comment on column gns.gns_store_item.school_id is
'学校ID：school_id';

comment on column gns.gns_store_item.item_key is
'配置信息KEY：item_key';

comment on column gns.gns_store_item.item_value is
'配置信息VALUE：item_value';

comment on column gns.gns_store_item.content_type is
'数据类型：content_type';

/*==============================================================*/
/* Table: gns_student_type                                      */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_student_type (
   studnet_type_code    SERIAL               not null,
   school_id            INT4                 null,
   type_name            VARCHAR(50)          null,
   "default"            BOOL                 null,
   start_date           DATE                 null,
   end_date             DATE                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_STUDENT_TYPE primary key (studnet_type_code)
);

comment on table gns.gns_student_type is
'学生类型：gns_student_type';

comment on column gns.gns_student_type.studnet_type_code is
'类型编号：studnet_type_code';

comment on column gns.gns_student_type.school_id is
'学校ID：school_id';

comment on column gns.gns_student_type.type_name is
'类型名称：type_name';

comment on column gns.gns_student_type."default" is
'是否默认：default';

comment on column gns.gns_student_type.start_date is
'迎新开始日期：start_date';

comment on column gns.gns_student_type.end_date is
'迎新结束日期：end_date';

comment on column gns.gns_student_type.order_id is
'排序：order_id';

comment on column gns.gns_student_type.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_thumbs_up                                         */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_thumbs_up (
   sign_id              SERIAL               not null,
   user_id              uuid                 null,
   landmark_id          INT8                 null,
   landmark_name        VARCHAR(255)         null,
   landmark_type        VARCHAR(50)          null,
   create_time          TIMESTAMP            null,
   constraint PK_GNS_THUMBS_UP primary key (sign_id)
);

comment on table gns.gns_thumbs_up is
'点赞记录：gns_thumbs_up';

comment on column gns.gns_thumbs_up.sign_id is
'记录ID：sign_id';

comment on column gns.gns_thumbs_up.user_id is
'用户ID：user_id';

comment on column gns.gns_thumbs_up.landmark_id is
'地标点ID：landmark_id';

comment on column gns.gns_thumbs_up.landmark_name is
'地标名称：landmark_name';

comment on column gns.gns_thumbs_up.landmark_type is
'地标点类型：landmark_type';

comment on column gns.gns_thumbs_up.create_time is
'点赞时间：create_time';

/*==============================================================*/
/* Table: gns_tour_point                                        */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_tour_point (
   point_code           SERIAL               not null,
   route_id             INT4                 null,
   map_code             INT8                 null,
   element_type         VARCHAR(50)          null,
   constraint PK_GNS_TOUR_POINT primary key (point_code)
);

comment on table gns.gns_tour_point is
'游览路线点位信息：gns_tour_point';

comment on column gns.gns_tour_point.point_code is
'点位编号：point_code';

comment on column gns.gns_tour_point.route_id is
'线路ID：route_id';

comment on column gns.gns_tour_point.map_code is
'地图元素编号：map_code';

comment on column gns.gns_tour_point.element_type is
'地图元素类型：element_type';

/*==============================================================*/
/* Table: gns_tour_route                                        */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_tour_route (
   campus_code          INT4                 null,
   route_id             SERIAL               not null,
   route_name           VARCHAR(50)          null,
   point_count          INT4                 null,
   mileage              VARCHAR(20)          null,
   update_time          TIMESTAMP                 null,
   order_id             INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_GNS_TOUR_ROUTE primary key (route_id)
);

comment on table gns.gns_tour_route is
'游览路线：gns_tour_route';

comment on column gns.gns_tour_route.campus_code is
'校区编号：campus_code';

comment on column gns.gns_tour_route.route_id is
'线路ID：route_id';

comment on column gns.gns_tour_route.route_name is
'线路名称：route_name';

comment on column gns.gns_tour_route.point_count is
'点位个数：point_count';

comment on column gns.gns_tour_route.mileage is
'路线里程：mileage';

comment on column gns.gns_tour_route.update_time is
'更新时间：update_time';

comment on column gns.gns_tour_route.order_id is
'排序：order_id';

comment on column gns.gns_tour_route.memo is
'备注：memo';

/*==============================================================*/
/* Table: gns_user_info                                         */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_user_info (
   user_id              uuid                 not null,
   school_id            INT4                 null,
   open_id              VARCHAR(255)         null,
   nickname             VARCHAR(255)         null,
   sex                  VARCHAR(12)          null,
   country              VARCHAR(255)         null,
   province             VARCHAR(255)         null,
   city                 VARCHAR(255)         null,
   head_url             TEXT                 null,
   union_id             VARCHAR(255)         null,
   dorm_id              VARCHAR(50)          null,
   academy_code         VARCHAR(50)          null,
   mobile               VARCHAR(11)          null,
   real_name            VARCHAR(50)          null,
   sign_count           INT4                 null,
   last_use_time        TIMESTAMP            null,
   last_use_location    geometry             null,
   share_times          INT4                 null,
   constraint PK_GNS_USER_INFO primary key (user_id)
);

comment on table gns.gns_user_info is
'用户信息：gns_user_info';

comment on column gns.gns_user_info.user_id is
'用户ID：user_id';

comment on column gns.gns_user_info.school_id is
'学校ID：school_id';

comment on column gns.gns_user_info.open_id is
'微信openId：open_id';

comment on column gns.gns_user_info.nickname is
'昵称：nickname';

comment on column gns.gns_user_info.sex is
'性别：sex';

comment on column gns.gns_user_info.country is
'国家：country';

comment on column gns.gns_user_info.province is
'省份：province';

comment on column gns.gns_user_info.city is
'城市：city';

comment on column gns.gns_user_info.head_url is
'头像：head_url';

comment on column gns.gns_user_info.union_id is
'微信unionid：union_id';

comment on column gns.gns_user_info.dorm_id is
'宿舍ID：dorm_id';

comment on column gns.gns_user_info.academy_code is
'院系ID：academy_code';

comment on column gns.gns_user_info.mobile is
'手机号：mobile';

comment on column gns.gns_user_info.real_name is
'姓名：real_name';

comment on column gns.gns_user_info.sign_count is
'打卡次数：sign_count';


comment on column gns.gns_user_info.last_use_time is
'最后使用时间：last_use_time';

comment on column gns.gns_user_info.last_use_location is
'最后使用位置：last_use_location';

comment on column gns.gns_user_info.share_times is
'分享次数：share_times';

/*==============================================================*/
/* Table: gns_display_point_type                                */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS gns.gns_display_point_type (
   point_type_code      INT4                 not null,
   school_id            INT4                 not null,
   constraint PK_GNS_DISPLAY_POINT_TYPE primary key (point_type_code, school_id)
);

comment on table gns.gns_display_point_type is
'点标注显示分类：gns_display_point_type';

comment on column gns.gns_display_point_type.point_type_code is
'点标注分类编号：point_type_code';

comment on column gns.gns_display_point_type.school_id is
'学校ID：school_id';

alter table gns.gns_academy
   add constraint FK_GNS_ACADEMY_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_access_record
   add constraint FK_GNS_ACCESS_REF_USER foreign key (user_id)
      references gns.gns_user_info (user_id)
      on delete cascade on update cascade;

alter table gns.gns_achievement
   add constraint FK_GNS_ACHIEVE_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_achievement_reach
   add constraint FK_GNS_ACHIEVE_REACH_REF_USER foreign key (user_id)
      references gns.gns_user_info (user_id)
      on delete cascade on update cascade;

alter table gns.gns_achievement_reach
   add constraint FK_GNS_ACHI_REACH_REF_ACHI foreign key (achievement_id, school_id)
      references gns.gns_achievement (achievement_id, school_id)
      on delete cascade on update cascade;

alter table gns.gns_application
   add constraint FK_GNS_APPLICATION_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_application
   add constraint FK_GNS_APP_REF_APP foreign key (parent_id)
      references gns.gns_application (application_id)
      on delete cascade on update cascade;

alter table gns.gns_application_use
   add constraint FK_GNS_APP_USE_REF_APP foreign key (application_id)
      references gns.gns_application (application_id)
      on delete cascade on update cascade;

alter table gns.gns_application_use
   add constraint FK_GNS_APP_USE_REF_USER foreign key (user_id)
      references gns.gns_user_info (user_id)
      on delete cascade on update cascade;

alter table gns.gns_campus_info
   add constraint FK_GNS_CAMPS_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete set null on update cascade;

alter table gns.gns_club
   add constraint FK_GNS_CLUB_REF_CAMPUS foreign key (campus_code)
      references gns.gns_campus_info (campus_code)
      on delete cascade on update cascade;

alter table gns.gns_feedback
   add constraint FK_GNS_FEEDBACK_REF_USER foreign key (user_id)
      references gns.gns_user_info (user_id)
      on delete cascade on update cascade;

alter table gns.gns_group_photo
   add constraint FK_GNS_PHOTO_TAKEN_REF_USER foreign key (user_id)
      references gns.gns_user_info (user_id)
      on delete cascade on update cascade;

alter table gns.gns_guide
   add constraint FK_GNS_GUIDE_REF_CAMPUS foreign key (campus_code)
      references gns.gns_campus_info (campus_code)
      on delete cascade on update cascade;

alter table gns.gns_guide
   add constraint FK_GNS_GUIDE_REF_STU_TYPE foreign key (studnet_type_code)
      references gns.gns_student_type (studnet_type_code)
      on delete cascade on update cascade;

alter table gns.gns_helper
   add constraint FK_GNS_HELPER_REF_TYPE foreign key (type_code)
      references gns.gns_helper_type (type_code)
      on delete cascade on update cascade;

alter table gns.gns_helper_type
   add constraint FK_GNS_HELPER_TYPE_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_interaction_statistic
   add constraint FK_GNS_INT_STA_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_manage_log
   add constraint FK_GNS_MANAGE_LOG_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_manage_role
   add constraint FK_GNS_MANAGE_ROLE_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_manage_role_resource
   add constraint FK_GNS_MANAGE_MRR_REF_RESOURCE foreign key (authority_id)
      references gns.gns_manage_resource (authority_id)
      on delete cascade on update cascade;

alter table gns.gns_manage_role_resource
   add constraint FK_GNS_MANAGE_MRR_REF_ROLE foreign key (rule_id)
      references gns.gns_manage_role (rule_id)
      on delete cascade on update cascade;

alter table gns.gns_manage_user
   add constraint FK_GNS_MANAGE_USER_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_manage_user_role
   add constraint FK_GNS_MANAGE_MUR_REF_ROLE foreign key (rule_id)
      references gns.gns_manage_role (rule_id)
      on delete cascade on update cascade;

alter table gns.gns_manage_user_role
   add constraint FK_GNS_MANAGE_MUR_REF_USER foreign key (user_id)
      references gns.gns_manage_user (user_id)
      on delete cascade on update cascade;

alter table gns.gns_map_use
   add constraint FK_GNS_MAP_USE_REF_USER foreign key (gns_user_id)
      references gns.gns_user_info (user_id)
      on delete cascade on update cascade;

alter table gns.gns_push_message
   add constraint FK_GNS_PUSH_REF_USER foreign key (user_id)
      references gns.gns_user_info (user_id)
      on delete cascade on update cascade;

alter table gns.gns_reception_place
   add constraint FK_GNS_RECEPT_PLACE_REF_CAMPUS foreign key (campus_code)
      references gns.gns_campus_info (campus_code)
      on delete cascade on update cascade;

alter table gns.gns_reception_place
   add constraint FK_GNS_RECEPT_PLACE_REF_TYPE foreign key (type_code)
      references gns.gns_reception_type (type_code)
      on delete cascade on update cascade;

alter table gns.gns_reception_type
   add constraint FK_GNS_RECEPT_TYPE_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_registering_notice
   add constraint FK_GNS_NOTICE_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_sign
   add constraint FK_GNS_SIGN_REF_USER foreign key (user_id)
      references gns.gns_user_info (user_id)
      on delete cascade on update cascade;

alter table gns.gns_store_item
   add constraint FK_GNS_ITEM_REF_STORE foreign key (sore_id)
      references gns.gns_store (sore_id)
      on delete cascade on update cascade;

alter table gns.gns_store_item
   add constraint FK_GNS_STORE_ITEM_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_student_type
   add constraint FK_GNS_STU_TYPE_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_thumbs_up
   add constraint FK_GNS_THUMBS_UP_REF_USER foreign key (user_id)
      references gns.gns_user_info (user_id)
      on delete cascade on update cascade;

alter table gns.gns_tour_point
   add constraint FK_GNS_TOUR_POINT_REF_ROUTE foreign key (route_id)
      references gns.gns_tour_route (route_id)
      on delete cascade on update cascade;

alter table gns.gns_tour_route
   add constraint FK_GNS_TOUR_ROUTE_REF_CAMPUS foreign key (campus_code)
      references gns.gns_campus_info (campus_code)
      on delete cascade on update cascade;

alter table gns.gns_user_info
   add constraint FK_GNS_USER_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

alter table gns.gns_display_point_type
   add constraint FK_GNS_POINT_DISPLAY_REF_SCHOOL foreign key (school_id)
      references gns.gns_school (school_id)
      on delete cascade on update cascade;

--大楼、房间、面图元、点标注相关表
/*==============================================================*/
/* Table: portal.map_building_type                                     */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_building_type (
	type_code            INT4                 not null,
	parent_code          INT4                 null,
	type_name            VARCHAR(64)          null,
	click                BOOL                 null,
	search               BOOL                 null,
	description          VARCHAR(255)         null,
	order_id             INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_BUILDING_TYPE primary key (type_code)
);

comment on table portal.map_building_type is
  '大楼类别：portal.map_building_type';

comment on column portal.map_building_type.type_code is
  '类别编号：type_code';

comment on column portal.map_building_type.parent_code is
  '父类型编号：parent_code';

comment on column portal.map_building_type.type_name is
  '类别名称：type_name';

comment on column portal.map_building_type.click is
  '是否可点击：click';

comment on column portal.map_building_type.search is
  '是否可搜索：search';

comment on column portal.map_building_type.description is
  '类别描述：description';

comment on column portal.map_building_type.order_id is
  '排序：order_id';

comment on column portal.map_building_type.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_bt_extends_define                                 */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_bt_extends_define (
	column_id            INT4                 not null,
	type_code            INT4                 not null,
	column_name          VARCHAR(100)         null,
	column_cnname        VARCHAR(100)         null,
	column_type          INT4                 null,
	required             BOOL                 null,
	show                 BOOL                 null,
	orderid              INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_BT_EXTENDS_DEFINE primary key (column_id, type_code)
);

comment on table portal.map_bt_extends_define is
  '大楼扩展属性定义：portal.map_bt_extends_define';

comment on column portal.map_bt_extends_define.column_id is
  '字段ID：column_id';

comment on column portal.map_bt_extends_define.type_code is
  '类型编号：type_code';

comment on column portal.map_bt_extends_define.column_name is
  '字段名：column_name';

comment on column portal.map_bt_extends_define.column_cnname is
  '字段中文名：column_cnname';

comment on column portal.map_bt_extends_define.column_type is
  '字段类型：column_type，0：文本；1：日期；2：富文本';

comment on column portal.map_bt_extends_define.required is
  '是否必填：required';

comment on column portal.map_bt_extends_define.show is
  '是否显示：show';

comment on column portal.map_bt_extends_define.orderid is
  '排序：orderid';

comment on column portal.map_bt_extends_define.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_building                                          */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_building (
	building_code        SERIAL               not null,
	type_code            INT4                 null,
	building_name        VARCHAR(255)         null,
	campus_code          INT4                 null,
	map_code             INT8                 null,
	en_name              varchar(255)         null,
	alias                varchar(255)         null,
	order_id             INT4                 null,
	syn_status           bool                 null,
	delete               bool                 default false,
	brief                text                 null,
	version_code         int4,
	lng_lat              geometry,
	raster_lng_lat       geometry,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_BUILDING primary key (building_code)
);

comment on table portal.map_building is
  '大楼信息：portal.map_building';

comment on column portal.map_building.building_code is
  '大楼编号：building_code';

comment on column portal.map_building.type_code is
  '类型编号：type_code';

comment on column portal.map_building.building_name is
  '大楼名称：building_name';

comment on column portal.map_building.campus_code is
  '校区编码：campus_code';

comment on column portal.map_building.map_code is
  '地图编码：portal.map_code';

comment on column portal.map_building.order_id is
  '排序：order_id';

comment on column portal.map_building.memo is
  '备注：memo';

COMMENT ON COLUMN portal.map_building.en_name IS
'英文名称：en_name';

COMMENT ON COLUMN portal.map_building.alias IS
'别名：alias';

COMMENT ON COLUMN portal.map_building.syn_status IS '是否已同步：syn_status';

COMMENT ON COLUMN portal.map_building.delete IS
'是否删除：delete';

COMMENT ON COLUMN portal.map_building.brief IS
'简介：brief';

COMMENT ON COLUMN portal.map_building.version_code IS
'版本号：version_code';

/*==============================================================*/
/* Table: portal.map_building_extends                                  */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_building_extends (
	column_id            INT4                 not null,
	type_code            INT4                 not null,
	building_code        INT4                 not null,
	extends_value        VARCHAR(1024)        null,
	constraint PK_MAP_BUILDING_EXTENDS primary key (column_id, type_code, building_code)
);

comment on table portal.map_building_extends is
  '大楼扩展属性值信息：map_building_extends';

comment on column portal.map_building_extends.column_id is
  '字段ID：column_id';

comment on column portal.map_building_extends.type_code is
  '类型编号：type_code';

comment on column portal.map_building_extends.building_code is
  '大楼编号：building_code';

comment on column portal.map_building_extends.extends_value is
  '属性值：extends_value';

/*==============================================================*/
/* Table: portal.map_building_img                                      */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_building_img (
	img_id               SERIAL               not null,
	building_code        INT4                 null,
	img_name             VARCHAR(64)          null,
	img_url              VARCHAR(1024)        null,
	order_id             INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_BUILDING_IMG primary key (img_id)
);

comment on table portal.map_building_img is
  '大楼图片信息：portal.map_building_img';

comment on column portal.map_building_img.img_id is
  '图片ID：img_id';

comment on column portal.map_building_img.building_code is
  '大楼编号：building_code';

comment on column portal.map_building_img.img_name is
  '图片名称：img_name';

comment on column portal.map_building_img.img_url is
  '图片地址：img_url';

comment on column portal.map_building_img.order_id is
  '排序：order_id';

comment on column portal.map_building_img.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_room_type                                         */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_room_type (
	type_code            INT4                 not null,
	parent_code          INT4                 null,
	type_name            VARCHAR(64)          null,
	click                BOOL                 null,
	search               BOOL                 null,
	description          VARCHAR(255)         null,
	order_id             INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_ROOM_TYPE primary key (type_code)
);

comment on table portal.map_room_type is
  '房间类别：portal.map_room_type';

comment on column portal.map_room_type.type_code is
  '类别编号：type_code';

comment on column portal.map_room_type.parent_code is
  '父类型编号：parent_code';

comment on column portal.map_room_type.type_name is
  '类别名称：type_name';

comment on column portal.map_room_type.click is
  '是否可点击：click';

comment on column portal.map_room_type.search is
  '是否可搜索：search';

comment on column portal.map_room_type.description is
  '类别描述：description';

comment on column portal.map_room_type.order_id is
  '排序：order_id';

comment on column portal.map_room_type.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_rt_extends_define                                 */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_rt_extends_define (
	column_id            INT4                 not null,
	type_code            INT4                 not null,
	column_name          VARCHAR(100)         null,
	column_cnname        VARCHAR(100)         null,
	column_type          INT4                 null,
	required             BOOL                 null,
	show                 BOOL                 null,
	orderid              INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_RT_EXTENDS_DEFINE primary key (column_id, type_code)
);

comment on table portal.map_rt_extends_define is
  '房间扩展属性定义：portal.map_rt_extends_define';

comment on column portal.map_rt_extends_define.column_id is
  '字段ID：column_id';

comment on column portal.map_rt_extends_define.type_code is
  '类型编号：type_code';

comment on column portal.map_rt_extends_define.column_name is
  '字段名：column_name';

comment on column portal.map_rt_extends_define.column_cnname is
  '字段中文名：column_cnname';

comment on column portal.map_rt_extends_define.column_type is
  '字段类型：column_type，0：文本；1：日期；2：富文本';

comment on column portal.map_rt_extends_define.required is
  '是否必填：required';

comment on column portal.map_rt_extends_define.show is
  '是否显示：show';

comment on column portal.map_rt_extends_define.orderid is
  '排序：orderid';

comment on column portal.map_rt_extends_define.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_room                                              */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_room (
	room_code            SERIAL               not null,
	type_code            INT4                 null,
	room_name            VARCHAR(255)         null,
	campus_code          INT4                 null,
	map_code             int8          null,
	building_map_code    INT8                 null,
	building_name        VARCHAR(255)         null,
	en_name              varchar(255)         null,
	alias                varchar(255)         null,
	hourse_number        varchar(64)          null,
	syn_status           bool                 null,
	order_id             INT4                 null,
	delete               bool                 default false,
	brief                text,
	leaf                 int4,
	version_code         int4,
	lng_lat              geometry,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_ROOM primary key (room_code)
);

comment on table portal.map_room is
  '房间信息：portal.map_room';

comment on column portal.map_room.room_code is
  '房间编号：room_code';

comment on column portal.map_room.type_code is
  '类型编号：type_code';

comment on column portal.map_room.room_name is
  '房间名称：room_name';

comment on column portal.map_room.campus_code is
  '校区编码：campus_code';

comment on column portal.map_room.map_code is
  '地图编码：portal.map_code';

comment on column portal.map_room.building_map_code is
  '大楼编码：building_portal.map_code';

comment on column portal.map_room.building_name is
  '大楼名称：building_name';

comment on column portal.map_room.order_id is
  '排序：order_id';

comment on column portal.map_room.memo is
  '备注：memo';

COMMENT ON COLUMN portal.map_room.en_name IS
'英文名称：en_name';

COMMENT ON COLUMN portal.map_room.alias IS
'别名：alias';

COMMENT ON COLUMN portal.map_room.hourse_number IS
'门牌号：hourse_number';

COMMENT ON COLUMN portal.map_room.syn_status IS
'是否已同步：syn_status';

COMMENT ON COLUMN portal.map_room.delete IS
'是否删除：delete';

COMMENT ON COLUMN portal.map_room.brief IS
'简介：brief';

comment on column portal.map_room.leaf IS
'地图楼层编码：leaf';

COMMENT ON COLUMN portal.map_room.version_code IS
'版本号：version_code';

/*==============================================================*/
/* Table: portal.map_room_extends                                      */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_room_extends (
	room_code            INT4                 not null,
	column_id            INT4                 not null,
	type_code            INT4                 not null,
	extends_value        VARCHAR(1024)        null,
	constraint PK_MAP_ROOM_EXTENDS primary key (room_code, column_id, type_code)
);

comment on table portal.map_room_extends is
  '房间扩展属性值信息：portal.map_room_extends';

comment on column portal.map_room_extends.room_code is
  '房间编号：room_code';

comment on column portal.map_room_extends.column_id is
  '字段ID：column_id';

comment on column portal.map_room_extends.type_code is
  '类型编号：type_code';

comment on column portal.map_room_extends.extends_value is
  '属性值：extends_value';

/*==============================================================*/
/* Table: portal.map_room_img                                          */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_room_img (
	img_id               SERIAL               not null,
	room_code            INT4                 null,
	img_name             VARCHAR(64)          null,
	img_url              VARCHAR(1024)        null,
	order_id             INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_ROOM_IMG primary key (img_id)
);

comment on table portal.map_room_img is
  '房间图片信息：portal.map_room_img';

comment on column portal.map_room_img.img_id is
  '图片ID：img_id';

comment on column portal.map_room_img.room_code is
  '房间编号：room_code';

comment on column portal.map_room_img.img_name is
  '图片名称：img_name';

comment on column portal.map_room_img.img_url is
  '图片地址：img_url';

comment on column portal.map_room_img.order_id is
  '排序：order_id';

comment on column portal.map_room_img.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_point_type                                        */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_point_type (
	type_code            INT4                 not null,
	parent_code          INT4                 null,
	type_name            VARCHAR(64)          null,
	display_level        INT4                 null,
	click                BOOL                 null,
	search               BOOL                 null,
	description          VARCHAR(255)         null,
	raster_icon          varchar(1024)        null,
	vector_icon          varchar(1024)        null,
	display              BOOL                 default true,
	order_id             INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_POINT_TYPE primary key (type_code)
);

comment on table portal.map_point_type is
  '标注类别：portal.map_point_type';

comment on column portal.map_point_type.type_code is
  '类别编号：type_code';

comment on column portal.map_point_type.parent_code is
  '父类型编号：parent_code';

comment on column portal.map_point_type.type_name is
  '类别名称：type_name';

comment on column portal.map_point_type.display_level is
  '显示级数：display_level';

comment on column portal.map_point_type.click is
  '是否可点击：click';

comment on column portal.map_point_type.search is
  '是否可搜索：search';

comment on column portal.map_point_type.description is
  '类别描述：description';

comment on column portal.map_point_type.order_id is
  '排序：order_id';

comment on column portal.map_point_type.memo is
  '备注：memo';

COMMENT ON COLUMN portal.map_point_type.raster_icon IS
'三维图标地址：raster_icon';

COMMENT ON COLUMN portal.map_point_type.vector_icon IS
'二维图标：vector_icon';

COMMENT ON COLUMN portal.map_point_type.display IS
'是否显示：display';

/*==============================================================*/
/* Table: portal.map_pt_extends_define                                 */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_pt_extends_define (
	column_id            INT4                 not null,
	type_code            INT4                 not null,
	column_name          VARCHAR(100)         null,
	column_cnname        VARCHAR(100)         null,
	column_type          INT4                 null,
	required             BOOL                 null,
	show                 BOOL                 null,
	orderid              INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_PT_EXTENDS_DEFINE primary key (column_id, type_code)
);

comment on table portal.map_pt_extends_define is
  '标注类别扩展属性定义：portal.map_pt_extends_define';

comment on column portal.map_pt_extends_define.column_id is
  '字段ID：column_id';

comment on column portal.map_pt_extends_define.type_code is
  '类型编号：type_code';

comment on column portal.map_pt_extends_define.column_name is
  '字段名：column_name';

comment on column portal.map_pt_extends_define.column_cnname is
  '字段中文名：column_cnname';

comment on column portal.map_pt_extends_define.column_type is
  '字段类型：column_type，0：文本；1：日期；2：富文本';

comment on column portal.map_pt_extends_define.required is
  '是否必填：required';

comment on column portal.map_pt_extends_define.show is
  '是否显示：show';

comment on column portal.map_pt_extends_define.orderid is
  '排序：orderid';

comment on column portal.map_pt_extends_define.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_point                                             */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_point (
	point_code           SERIAL               not null,
	type_code            INT4                 null,
	point_name           VARCHAR(255)         null,
	campus_code          INT4                 null,
	leaf                 INT4                 null,
	location             VARCHAR(64)          null,
	lng_lat              geometry             null,
	raster_lng_lat       geometry             null,
	order_id             INT4                 null,
	brief                text                 null,
	map_code             int8,
	version_code         int4,
	syn_status           bool,
	delete               bool,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_POINT primary key (point_code)
);

comment on table portal.map_point is
  '标注信息：portal.map_point';

comment on column portal.map_point.point_code is
  '标注编号：point_code';

comment on column portal.map_point.type_code is
  '类型编号：type_code';

comment on column portal.map_point.point_name is
  '标注名称：point_name';

comment on column portal.map_point.campus_code is
  '校区编码：campus_code';

comment on column portal.map_point.leaf is
  '楼层编码：leaf，null为室外';

comment on column portal.map_point.location is
  '绑定位置：location';

comment on column portal.map_point.lng_lat is
  '地图坐标：lng_lat';

comment on column portal.map_point.order_id is
  '排序：order_id';

comment on column portal.map_point.memo is
  '备注：memo';

COMMENT ON COLUMN portal.map_point.raster_lng_lat IS
'三维坐标：raster_lng_lat';

COMMENT ON COLUMN portal.map_point.brief IS
'简介：brief';

COMMENT ON COLUMN portal.map_point.map_code IS
'地图编码：map_code';

COMMENT ON COLUMN portal.map_point.version_code IS
'版本号：version_code';

COMMENT on column portal.map_point.syn_status is
'是否已同步：syn_status';

COMMENT on column portal.map_point.delete is
'是否已删除：delete';

/*==============================================================*/
/* Table: portal.map_point_extends                                     */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_point_extends (
	column_id            INT4                 not null,
	type_code            INT4                 not null,
	point_code           INT4                 not null,
	extends_value        VARCHAR(1024)        null,
	constraint PK_MAP_POINT_EXTENDS primary key (column_id, type_code, point_code)
);

comment on table portal.map_point_extends is
  '标注扩展属性值信息：portal.map_point_extends';

comment on column portal.map_point_extends.column_id is
  '字段ID：column_id';

comment on column portal.map_point_extends.type_code is
  '类型编号：type_code';

comment on column portal.map_point_extends.point_code is
  '标注编号：point_code';

comment on column portal.map_point_extends.extends_value is
  '属性值：extends_value';

/*==============================================================*/
/* Table: portal.map_point_img                                         */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_point_img (
	img_id               SERIAL               not null,
	point_code           INT4                 null,
	img_name             VARCHAR(64)          null,
	img_url              VARCHAR(1024)        null,
	order_id             INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_POINT_IMG primary key (img_id)
);

comment on table portal.map_point_img is
  '标注图片信息：portal.map_point_img';

comment on column portal.map_point_img.img_id is
  '图片ID：img_id';

comment on column portal.map_point_img.point_code is
  '标注编号：point_code';

comment on column portal.map_point_img.img_name is
  '图片名称：img_name';

comment on column portal.map_point_img.img_url is
  '图片地址：img_url';

comment on column portal.map_point_img.order_id is
  '排序：order_id';

comment on column portal.map_point_img.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_others_polygon_type                               */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_others_polygon_type (
	type_code            INT4                 not null,
	parent_code          INT4                 null,
	type_name            VARCHAR(64)          null,
	click                BOOL                 null,
	search               BOOL                 null,
	description          VARCHAR(255)         null,
	order_id             INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_OTHERS_POLYGON_TYPE primary key (type_code)
);

comment on table portal.map_others_polygon_type is
  '其他面图源类别：portal.map_others_polygon_type';

comment on column portal.map_others_polygon_type.type_code is
  '类别编号：type_code';

comment on column portal.map_others_polygon_type.parent_code is
  '父类型编号：parent_code';

comment on column portal.map_others_polygon_type.type_name is
  '类别名称：type_name';

comment on column portal.map_others_polygon_type.click is
  '是否可点击：click';

comment on column portal.map_others_polygon_type.search is
  '是否可搜索：search';

comment on column portal.map_others_polygon_type.description is
  '类别描述：description';

comment on column portal.map_others_polygon_type.order_id is
  '排序：order_id';

comment on column portal.map_others_polygon_type.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_opt_extends_define                                */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_opt_extends_define (
	column_id            INT4                 not null,
	type_code            INT4                 not null,
	column_name          VARCHAR(100)         null,
	column_cnname        VARCHAR(100)         null,
	column_type          INT4                 null,
	required             BOOL                 null,
	show                 BOOL                 null,
	orderid              INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_OPT_EXTENDS_DEFINE primary key (column_id, type_code)
);

comment on table portal.map_opt_extends_define is
  '其他类别面图源扩展属性定义：portal.map_opt_extends_define';

comment on column portal.map_opt_extends_define.column_id is
  '字段ID：column_id';

comment on column portal.map_opt_extends_define.type_code is
  '类型编号：type_code';

comment on column portal.map_opt_extends_define.column_name is
  '字段名：column_name';

comment on column portal.map_opt_extends_define.column_cnname is
  '字段中文名：column_cnname';

comment on column portal.map_opt_extends_define.column_type is
  '字段类型：column_type，0：文本；1：日期；2：富文本';

comment on column portal.map_opt_extends_define.required is
  '是否必填：required';

comment on column portal.map_opt_extends_define.show is
  '是否显示：show';

comment on column portal.map_opt_extends_define.orderid is
  '排序：orderid';

comment on column portal.map_opt_extends_define.memo is
  '备注：memo';

/*==============================================================*/
/* Table: portal.map_others_polygon                                    */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_others_polygon (
	polygon_code         SERIAL               not null,
	type_code            INT4                 null,
	polygon_name         VARCHAR(255)         null,
	campus_code          INT4                 null,
	map_code             INT8                 null,
	en_name              varchar(255)         null,
	alias                varchar(255)         null,
	syn_status           bool                 null,
	order_id             INT4                 null,
	delete               bool                 default false,
	brief                text,
	leaf                 int4,
	version_code         int4,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_OTHERS_POLYGON primary key (polygon_code)
);

comment on table portal.map_others_polygon is
  '其他面图源信息：portal.map_others_polygon';

comment on column portal.map_others_polygon.polygon_code is
  '面图源编号：polygon_code';

comment on column portal.map_others_polygon.type_code is
  '类型编号：type_code';

comment on column portal.map_others_polygon.polygon_name is
  '面图源名称：polygon_name';

comment on column portal.map_others_polygon.campus_code is
  '校区编码：campus_code';

comment on column portal.map_others_polygon.map_code is
  '地图编码：portal.map_code';

comment on column portal.map_others_polygon.brief is
  '简介：brief';

comment on column portal.map_others_polygon.order_id is
  '排序：order_id';

comment on column portal.map_others_polygon.memo is
  '备注：memo';

COMMENT ON COLUMN portal.map_others_polygon.en_name IS
'英文名称：en_name';

COMMENT ON COLUMN portal.map_others_polygon.alias IS
'别名：alias';

COMMENT ON COLUMN portal.map_others_polygon.syn_status IS
'是否已同步：syn_status';

COMMENT ON COLUMN portal.map_others_polygon.delete IS
'是否删除：delete';

COMMENT ON COLUMN portal.map_others_polygon.brief IS
'简介：brief';

comment on column portal.map_others_polygon.leaf IS
'地图楼层编码：leaf';

COMMENT ON COLUMN portal.map_others_polygon.version_code IS
'版本号：version_code';

/*==============================================================*/
/* Table: portal.map_others_polygon_extends                            */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_others_polygon_extends (
	polygon_code         INT4                 not null,
	column_id            INT4                 not null,
	type_code            INT4                 not null,
	extends_value        VARCHAR(1024)        null,
	constraint PK_MAP_OTHERS_POLYGON_EXTENDS primary key (polygon_code, column_id, type_code)
);

/*==============================================================*/
/* Table: portal.map_others_polygon_img                                */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS portal.map_others_polygon_img (
	img_id               SERIAL               not null,
	polygon_code         INT4                 null,
	img_name             VARCHAR(64)          null,
	img_url              VARCHAR(1024)        null,
	order_id             INT4                 null,
	memo                 VARCHAR(255)         null,
	constraint PK_MAP_OTHERS_POLYGON_IMG primary key (img_id)
);

comment on table portal.map_others_polygon_img is
  '其他面图源图片信息：portal.map_others_polygon_img';

comment on column portal.map_others_polygon_img.img_id is
  '图片ID：img_id';

comment on column portal.map_others_polygon_img.polygon_code is
  '面图源编号：polygon_code';

comment on column portal.map_others_polygon_img.img_name is
  '图片名称：img_name';

comment on column portal.map_others_polygon_img.img_url is
  '图片地址：img_url';

comment on column portal.map_others_polygon_img.order_id is
  '排序：order_id';

comment on column portal.map_others_polygon_img.memo is
  '备注：memo';

comment on table portal.map_others_polygon_extends is
  '其他类别面图源扩展属性值信息：portal.map_others_polygon_extends';

comment on column portal.map_others_polygon_extends.polygon_code is
  '面图源编号：polygon_code';

comment on column portal.map_others_polygon_extends.column_id is
  '字段ID：column_id';

comment on column portal.map_others_polygon_extends.type_code is
  '类型编号：type_code';

comment on column portal.map_others_polygon_extends.extends_value is
  '属性值：extends_value';

alter table portal.map_room
add column if not exists lng_lat geometry;

COMMENT ON COLUMN portal.map_room.lng_lat IS
'二维坐标：lng_lat';

alter table portal.map_building
	add column if not exists lng_lat geometry,
	add column if not exists raster_lng_lat geometry;

COMMENT ON COLUMN portal.map_building.lng_lat IS
'二维坐标：lng_lat';

COMMENT ON COLUMN portal.map_building.raster_lng_lat IS
'三维坐标：raster_lng_lat';

ALTER TABLE portal.map_point_type
  drop column if exists font_color,
  drop column if exists font_bold,
  ADD font_color VARCHAR(64) null,
  ADD font_bold bool null;

comment on column portal.map_point_type.font_color is
'字体颜色';
comment on column portal.map_point_type.font_bold is
'字体是否加粗';

alter table portal.map_bt_extends_define
  drop constraint if exists FK_MAP_BTED_REF_BT,
  add constraint FK_MAP_BTED_REF_BT foreign key (type_code)
    references portal.map_building_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_building
  drop constraint if exists FK_MAP_BUILDING_REF_BT,
  add constraint FK_MAP_BUILDING_REF_BT foreign key (type_code)
    references portal.map_building_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_building_type
  drop constraint if exists FK_MAP_BT_REF_BT,
  add constraint FK_MAP_BT_REF_BT foreign key (parent_code)
    references portal.map_building_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_building_extends
  drop constraint if exists FK_MAP_BE_REF_BTED,
  add constraint FK_MAP_BE_REF_BTED foreign key (column_id, type_code)
    references portal.map_bt_extends_define (column_id, type_code)
    on delete cascade on update cascade;

alter table portal.map_building_extends
  drop constraint if exists FK_MAP_BE_REF_BUILDING,
  add constraint FK_MAP_BE_REF_BUILDING foreign key (building_code)
    references portal.map_building (building_code)
    on delete cascade on update cascade;

alter table portal.map_building_img
  drop constraint if exists FK_MAP_BI_REF_BUILDING,
  add constraint FK_MAP_BI_REF_BUILDING foreign key (building_code)
    references portal.map_building (building_code)
    on delete cascade on update cascade;

alter table portal.map_room
  drop constraint if exists FK_MAP_ROOM_REF_RT,
  add constraint FK_MAP_ROOM_REF_RT foreign key (type_code)
    references portal.map_room_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_room_type
  drop constraint if exists FK_MAP_RT_REF_RT,
  add constraint FK_MAP_RT_REF_RT foreign key (parent_code)
    references portal.map_room_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_rt_extends_define
  drop constraint if exists FK_MAP_RTED_REF_RT,
  add constraint FK_MAP_RTED_REF_RT foreign key (type_code)
    references portal.map_room_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_room_extends
  drop constraint if exists FK_MAP_RE_REF_RTED,
  add constraint FK_MAP_RE_REF_RTED foreign key (column_id, type_code)
    references portal.map_rt_extends_define (column_id, type_code)
    on delete cascade on update cascade;

alter table portal.map_room_extends
  drop constraint if exists FK_MAP_RE_REF_ROOM,
  add constraint FK_MAP_RE_REF_ROOM foreign key (room_code)
    references portal.map_room (room_code)
    on delete cascade on update cascade;

alter table portal.map_room_img
  drop constraint if exists FK_MAP_RI_REF_ROOM,
  add constraint FK_MAP_RI_REF_ROOM foreign key (room_code)
    references portal.map_room (room_code)
    on delete cascade on update cascade;

alter table portal.map_point
  drop constraint if exists FK_MAP_POINT_REF_PT,
  add constraint FK_MAP_POINT_REF_PT foreign key (type_code)
    references portal.map_point_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_point_type
  drop constraint if exists FK_MAP_PT_REF_PT,
  add constraint FK_MAP_PT_REF_PT foreign key (parent_code)
    references portal.map_point_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_pt_extends_define
  drop constraint if exists FK_MAP_PTED_REF_PT,
  add constraint FK_MAP_PTED_REF_PT foreign key (type_code)
    references portal.map_point_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_point_extends
  drop constraint if exists FK_MAP_PE_REF_PTED,
  add constraint FK_MAP_PE_REF_PTED foreign key (column_id, type_code)
    references portal.map_pt_extends_define (column_id, type_code)
    on delete cascade on update cascade;

alter table portal.map_point_extends
  drop constraint if exists FK_MAP_PE_REF_POINT,
  add constraint FK_MAP_PE_REF_POINT foreign key (point_code)
    references portal.map_point (point_code)
    on delete cascade on update cascade;

alter table portal.map_point_img
  drop constraint if exists FK_MAP_PI_REF_POINT,
  add constraint FK_MAP_PI_REF_POINT foreign key (point_code)
    references portal.map_point (point_code)
    on delete cascade on update cascade;

alter table portal.map_opt_extends_define
  drop constraint if exists FK_MAP_OPTED_REF_OPT,
  add constraint FK_MAP_OPTED_REF_OPT foreign key (type_code)
    references portal.map_others_polygon_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_others_polygon
  drop constraint if exists FK_MAP_OP_REF_OPT,
  add constraint FK_MAP_OP_REF_OPT foreign key (type_code)
    references portal.map_others_polygon_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_others_polygon_type
  drop constraint if exists FK_MAP_OPT_REF_OPT,
  add constraint FK_MAP_OPT_REF_OPT foreign key (parent_code)
    references portal.map_others_polygon_type (type_code)
    on delete cascade on update cascade;

alter table portal.map_others_polygon_extends
  drop constraint if exists FK_MAP_OPE_REF_OPED,
  add constraint FK_MAP_OPE_REF_OPED foreign key (column_id, type_code)
    references portal.map_opt_extends_define (column_id, type_code)
    on delete cascade on update cascade;

alter table portal.map_others_polygon_extends
  drop constraint if exists FK_MAP_OPE_REF_OP,
  add constraint FK_MAP_OPE_REF_OP foreign key (polygon_code)
    references portal.map_others_polygon (polygon_code)
    on delete cascade on update cascade;

alter table portal.map_others_polygon_img
  drop constraint if exists FK_MAP_OPI_REF_OP,
  add constraint FK_MAP_OPI_REF_OP foreign key (polygon_code)
    references portal.map_others_polygon (polygon_code)
    on delete cascade on update cascade;

--大楼、房间、面图元、点标注追加迎新相关参数

ALTER TABLE portal.map_building
	ADD COLUMN IF NOT EXISTS audio_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS video_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS roam_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS photo_background VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS open_gns_sign BOOLEAN DEFAULT TRUE,
	ADD COLUMN IF NOT EXISTS gns_sign_interval INT4 DEFAULT 1,
	ADD COLUMN IF NOT EXISTS gns_sign_count INT4 DEFAULT 0,
	ADD COLUMN IF NOT EXISTS pohoto_taken_count INT4 DEFAULT 0,
	ADD COLUMN IF NOT EXISTS thumbs_up_count INT4 DEFAULT 0;

COMMENT ON COLUMN portal.map_building.audio_url IS
'音频文件地址：audio_url';

COMMENT ON COLUMN portal.map_building.video_url IS
'视频文件地址：video_url';

COMMENT ON COLUMN portal.map_building.roam_url IS
'roam_url';

COMMENT ON COLUMN portal.map_building.photo_background IS
'留影背景图地址：photo_background';

COMMENT ON COLUMN portal.map_building.open_gns_sign IS
'是否开启迎新打卡：open_gns_sign';

COMMENT ON COLUMN portal.map_building.gns_sign_interval IS
'迎新打卡间隔（小时）：gns_sign_interval';

COMMENT ON COLUMN portal.map_building.gns_sign_count IS
'迎新打卡次数：gns_sign_count';

COMMENT ON COLUMN portal.map_building.pohoto_taken_count IS
'留影数：pohoto_taken_count';

COMMENT ON COLUMN portal.map_building.thumbs_up_count IS
'点赞数：thumbs_up_count';

ALTER TABLE portal.map_room
	ADD COLUMN IF NOT EXISTS audio_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS video_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS roam_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS photo_background VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS open_gns_sign BOOLEAN DEFAULT TRUE,
	ADD COLUMN IF NOT EXISTS gns_sign_interval INT4 DEFAULT 1,
	ADD COLUMN IF NOT EXISTS gns_sign_count INT4 DEFAULT 0,
	ADD COLUMN IF NOT EXISTS pohoto_taken_count INT4 DEFAULT 0,
	ADD COLUMN IF NOT EXISTS thumbs_up_count INT4 DEFAULT 0;

COMMENT ON COLUMN portal.map_room.audio_url IS
'音频文件地址：audio_url';

COMMENT ON COLUMN portal.map_room.video_url IS
'视频文件地址：video_url';

COMMENT ON COLUMN portal.map_room.roam_url IS
'roam_url';

COMMENT ON COLUMN portal.map_room.photo_background IS
'留影背景图地址：photo_background';

COMMENT ON COLUMN portal.map_room.open_gns_sign IS
'是否开启迎新打卡：open_gns_sign';

COMMENT ON COLUMN portal.map_room.gns_sign_interval IS
'迎新打卡间隔（小时）：gns_sign_interval';

COMMENT ON COLUMN portal.map_room.gns_sign_count IS
'迎新打卡次数：gns_sign_count';

COMMENT ON COLUMN portal.map_room.pohoto_taken_count IS
'留影数：pohoto_taken_count';

COMMENT ON COLUMN portal.map_room.thumbs_up_count IS
'点赞数：thumbs_up_count';

ALTER TABLE portal.map_others_polygon
	ADD COLUMN IF NOT EXISTS audio_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS video_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS roam_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS photo_background VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS open_gns_sign BOOLEAN DEFAULT TRUE,
	ADD COLUMN IF NOT EXISTS gns_sign_interval INT4 DEFAULT 1,
	ADD COLUMN IF NOT EXISTS gns_sign_count INT4 DEFAULT 0,
	ADD COLUMN IF NOT EXISTS pohoto_taken_count INT4 DEFAULT 0,
	ADD COLUMN IF NOT EXISTS thumbs_up_count INT4 DEFAULT 0;

COMMENT ON COLUMN portal.map_others_polygon.audio_url IS
'音频文件地址：audio_url';

COMMENT ON COLUMN portal.map_others_polygon.video_url IS
'视频文件地址：video_url';

COMMENT ON COLUMN portal.map_others_polygon.roam_url IS
'roam_url';

COMMENT ON COLUMN portal.map_others_polygon.photo_background IS
'留影背景图地址：photo_background';

COMMENT ON COLUMN portal.map_others_polygon.open_gns_sign IS
'是否开启迎新打卡：open_gns_sign';

COMMENT ON COLUMN portal.map_others_polygon.gns_sign_interval IS
'迎新打卡间隔（小时）：gns_sign_interval';

COMMENT ON COLUMN portal.map_others_polygon.gns_sign_count IS
'迎新打卡次数：gns_sign_count';

COMMENT ON COLUMN portal.map_others_polygon.pohoto_taken_count IS
'留影数：pohoto_taken_count';

COMMENT ON COLUMN portal.map_others_polygon.thumbs_up_count IS
'点赞数：thumbs_up_count';

ALTER TABLE portal.map_point
	ADD COLUMN IF NOT EXISTS audio_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS video_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS roam_url VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS photo_background VARCHAR(1024),
	ADD COLUMN IF NOT EXISTS open_gns_sign BOOLEAN DEFAULT TRUE,
	ADD COLUMN IF NOT EXISTS gns_sign_interval INT4 DEFAULT 1,
	ADD COLUMN IF NOT EXISTS gns_sign_count INT4 DEFAULT 0,
	ADD COLUMN IF NOT EXISTS pohoto_taken_count INT4 DEFAULT 0,
	ADD COLUMN IF NOT EXISTS gns_hot BOOLEAN DEFAULT TRUE,
	ADD COLUMN IF NOT EXISTS thumbs_up_count INT4 DEFAULT 0;

COMMENT ON COLUMN portal.map_point.audio_url IS
'音频文件地址：audio_url';

COMMENT ON COLUMN portal.map_point.video_url IS
'视频文件地址：video_url';

COMMENT ON COLUMN portal.map_point.roam_url IS
'roam_url';

COMMENT ON COLUMN portal.map_point.photo_background IS
'留影背景图地址：photo_background';

COMMENT ON COLUMN portal.map_point.open_gns_sign IS
'是否开启迎新打卡：open_gns_sign';

COMMENT ON COLUMN portal.map_point.gns_sign_interval IS
'迎新打卡间隔（小时）：gns_sign_interval';

COMMENT ON COLUMN portal.map_point.gns_sign_count IS
'迎新打卡次数：gns_sign_count';

COMMENT ON COLUMN portal.map_point.pohoto_taken_count IS
'留影数：pohoto_taken_count';

COMMENT ON COLUMN portal.map_point.gns_hot IS
'可以成为迎新热门地标：gns_hot';

COMMENT ON COLUMN portal.map_point.thumbs_up_count IS
'点赞数：thumbs_up_count';

--portal.map_point_type点标注分类信息初始
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (111, NULL, '室内公共设施点', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (100, NULL, '购物', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (101, NULL, '餐饮服务', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (102, NULL, '住宿', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (103, NULL, '交通服务', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (104, NULL, '文体娱乐', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (105, NULL, '金融服务', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (106, NULL, '生活服务', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (107, NULL, '教育服务', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (108, NULL, '医疗服务', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (109, NULL, '校园景点', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (110, NULL, '大楼标注点', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (112, NULL, '标注点', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (113, NULL, '一卡通', 18, 't', 't', NULL, NULL, NULL, 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (126, 101, '清蒸', 18, 't', 't', NULL, '/upload/mapPointType/init/126_3d.png', '/upload/mapPointType/init/126.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (128, 101, '食品店', 18, 't', 't', NULL, '/upload/mapPointType/init/128_3d.png', '/upload/mapPointType/init/128.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (130, 102, '连锁旅店', 18, 't', 't', NULL, '/upload/mapPointType/init/130_3d.png', '/upload/mapPointType/init/130.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (132, 102, '星级宾馆', 18, 't', 't', NULL, '/upload/mapPointType/init/132_3d.png', '/upload/mapPointType/init/132.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (134, 102, '学生公寓', 18, 't', 't', NULL, '/upload/mapPointType/init/134_3d.png', '/upload/mapPointType/init/134.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (136, 103, '地铁站', 18, 't', 't', NULL, '/upload/mapPointType/init/136_3d.png', '/upload/mapPointType/init/136.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (138, 103, '公交站点', 18, 't', 't', NULL, '/upload/mapPointType/init/138_3d.png', '/upload/mapPointType/init/138.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (141, 103, '售票点（指机票、火车票、长途客车）', 18, 't', 't', NULL, '/upload/mapPointType/init/141_3d.png', '/upload/mapPointType/init/141.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (142, 103, '自行车停靠点', 18, 't', 't', NULL, '/upload/mapPointType/init/142_3d.png', '/upload/mapPointType/init/142.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (143, 104, '美术馆', 18, 't', 't', NULL, '/upload/mapPointType/init/143_3d.png', '/upload/mapPointType/init/143.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (147, 104, '图书馆', 18, 't', 't', NULL, '/upload/mapPointType/init/147_3d.png', '/upload/mapPointType/init/147.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (149, 104, '音乐厅', 18, 't', 't', NULL, '/upload/mapPointType/init/149_3d.png', '/upload/mapPointType/init/149.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (151, 104, '青少年宫', 18, 't', 't', NULL, '/upload/mapPointType/init/151_3d.png', '/upload/mapPointType/init/151.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (153, 104, '纪念馆', 18, 't', 't', NULL, '/upload/mapPointType/init/153_3d.png', '/upload/mapPointType/init/153.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (155, 104, '体育比赛场馆', 18, 't', 't', NULL, '/upload/mapPointType/init/155_3d.png', '/upload/mapPointType/init/155.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (157, 104, '洗浴中心', 18, 't', 't', NULL, '/upload/mapPointType/init/157_3d.png', '/upload/mapPointType/init/157.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (159, 104, '酒吧', 18, 't', 't', NULL, '/upload/mapPointType/init/159_3d.png', '/upload/mapPointType/init/159.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (161, 105, '成都银行', 18, 't', 't', NULL, '/upload/mapPointType/init/161_3d.png', '/upload/mapPointType/init/161.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (163, 105, '中国银行', 18, 't', 't', NULL, '/upload/mapPointType/init/163_3d.png', '/upload/mapPointType/init/163.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (165, 105, '农业银行', 18, 't', 't', NULL, '/upload/mapPointType/init/165_3d.png', '/upload/mapPointType/init/165.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (167, 105, '中信银行', 18, 't', 't', NULL, '/upload/mapPointType/init/167_3d.png', '/upload/mapPointType/init/167.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (169, 105, 'ATM', 18, 't', 't', NULL, '/upload/mapPointType/init/169_3d.png', '/upload/mapPointType/init/169.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (173, 106, '办事处', 18, 't', 't', NULL, '/upload/mapPointType/init/173_3d.png', '/upload/mapPointType/init/173.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (175, 106, '水电缴费网点', 18, 't', 't', NULL, '/upload/mapPointType/init/175_3d.png', '/upload/mapPointType/init/175.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (177, 106, '中国电信', 18, 't', 't', NULL, '/upload/mapPointType/init/177_3d.png', '/upload/mapPointType/init/177.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (179, 106, '洗衣店', 18, 't', 't', NULL, '/upload/mapPointType/init/179_3d.png', '/upload/mapPointType/init/179.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (181, 106, '摄影冲印', 18, 't', 't', NULL, '/upload/mapPointType/init/181_3d.png', '/upload/mapPointType/init/181.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (183, 106, '中通快递', 18, 't', 't', NULL, '/upload/mapPointType/init/183_3d.png', '/upload/mapPointType/init/183.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (185, 106, 'EMS快递', 18, 't', 't', NULL, '/upload/mapPointType/init/185_3d.png', '/upload/mapPointType/init/185.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (187, 106, '京东派', 18, 't', 't', NULL, '/upload/mapPointType/init/187_3d.png', '/upload/mapPointType/init/187.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (189, 106, '取件柜', 18, 't', 't', NULL, '/upload/mapPointType/init/189_3d.png', '/upload/mapPointType/init/189.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (193, 107, '小学', 18, 't', 't', NULL, '/upload/mapPointType/init/193_3d.png', '/upload/mapPointType/init/193.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (195, 107, '成人教育', 18, 't', 't', NULL, '/upload/mapPointType/init/195_3d.png', '/upload/mapPointType/init/195.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (197, 107, '培训机构', 18, 't', 't', NULL, '/upload/mapPointType/init/197_3d.png', '/upload/mapPointType/init/197.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (199, 108, '医院', 18, 't', 't', NULL, '/upload/mapPointType/init/199_3d.png', '/upload/mapPointType/init/199.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (201, 108, '诊所', 18, 't', 't', NULL, '/upload/mapPointType/init/201_3d.png', '/upload/mapPointType/init/201.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (203, 109, '迎新点', 18, 't', 't', NULL, '/upload/mapPointType/init/203_3d.png', '/upload/mapPointType/init/203.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (205, 109, '校园景点', 18, 't', 't', NULL, '/upload/mapPointType/init/205_3d.png', '/upload/mapPointType/init/205.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (212, 111, '电梯', 18, 't', 't', NULL, '/upload/mapPointType/init/214_3d.png', '/upload/mapPointType/init/214.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (215, 111, '男卫生间', 18, 't', 't', NULL, '/upload/mapPointType/init/217_3d.png', '/upload/mapPointType/init/217.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (217, 111, '安全通道', 18, 't', 't', NULL, '/upload/mapPointType/init/219_3d.png', '/upload/mapPointType/init/219.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (219, 111, '消防栓', 18, 't', 't', NULL, '/upload/mapPointType/init/221.png', '/upload/mapPointType/init/221.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (222, 104, '羽毛球', 18, 't', 't', NULL, '/upload/mapPointType/init/223.png', '/upload/mapPointType/init/223.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (224, 104, '乒乓球', 18, 't', 't', NULL, '/upload/mapPointType/init/225.png', '/upload/mapPointType/init/225.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (226, 104, '门球', 18, 't', 't', NULL, '/upload/mapPointType/init/227.png', '/upload/mapPointType/init/227.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (232, 103, '校车停靠点', 18, 't', 't', NULL, '/upload/mapPointType/init/233_3d.png', '/upload/mapPointType/init/233.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (122, 100, '专营店', 18, 't', 't', NULL, '/upload/mapPointType/init/122_3d.png', '/upload/mapPointType/init/122.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (207, 109, '十大故事', 18, 't', 't', NULL, '/upload/mapPointType/init/209_3d.png', '/upload/mapPointType/init/209.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (208, 109, '全景点', 18, 't', 't', NULL, '/upload/mapPointType/init/210_3d.png', '/upload/mapPointType/init/210.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (209, 109, '宗教场所（教堂、清真寺、寺庙、道观等）', 18, 't', 't', NULL, '/upload/mapPointType/init/211_3d.png', '/upload/mapPointType/init/211.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (210, 110, '大楼标注点', 18, 't', 't', NULL, '/upload/mapPointType/init/212_3d.png', '/upload/mapPointType/init/212.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (211, 111, '楼梯', 18, 't', 't', NULL, '/upload/mapPointType/init/213_3d.png', '/upload/mapPointType/init/213.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (228, 112, '区域标注', 18, 't', 't', NULL, '/upload/mapPointType/init/206_3d.png', '/upload/mapPointType/init/206.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (229, 104, '游泳池', 18, 't', 't', NULL, '/upload/mapPointType/init/206_3d.png', '/upload/mapPointType/init/207.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (120, 100, '商场', 18, 't', 't', NULL, '/upload/mapPointType/init/120_3d.png', '/upload/mapPointType/init/120.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (123, 100, '其他', 18, 't', 't', NULL, '/upload/mapPointType/init/120_3d.png', '/upload/mapPointType/init/120.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (125, 101, '快餐', 18, 't', 't', NULL, '/upload/mapPointType/init/125_3d.png', '/upload/mapPointType/init/125.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (127, 101, '综合类饭店', 18, 't', 't', NULL, '/upload/mapPointType/init/127_3d.png', '/upload/mapPointType/init/127.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (129, 101, '其他', 18, 't', 't', NULL, '/upload/mapPointType/init/124_3d.png', '/upload/mapPointType/init/124.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (131, 102, '宾馆旅店', 18, 't', 't', NULL, '/upload/mapPointType/init/131_3d.png', '/upload/mapPointType/init/131.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (133, 102, '招待所', 18, 't', 't', NULL, '/upload/mapPointType/init/133_3d.png', '/upload/mapPointType/init/133.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (135, 102, '教职工公寓', 18, 't', 't', NULL, '/upload/mapPointType/init/135_3d.png', '/upload/mapPointType/init/135.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (137, 103, '停车场', 18, 't', 't', NULL, '/upload/mapPointType/init/137_3d.png', '/upload/mapPointType/init/137.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (140, 103, '租车点（自行车、电动车、汽车）', 18, 't', 't', NULL, '/upload/mapPointType/init/140_3d.png', '/upload/mapPointType/init/140.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (144, 104, '博物馆', 18, 't', 't', NULL, '/upload/mapPointType/init/144_3d.png', '/upload/mapPointType/init/144.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (146, 104, '会展中心', 18, 't', 't', NULL, '/upload/mapPointType/init/146_3d.png', '/upload/mapPointType/init/146.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (148, 104, '书店', 18, 't', 't', NULL, '/upload/mapPointType/init/148_3d.png', '/upload/mapPointType/init/148.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (150, 104, '影剧戏院', 18, 't', 't', NULL, '/upload/mapPointType/init/150_3d.png', '/upload/mapPointType/init/150.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (152, 104, '科技文化宫', 18, 't', 't', NULL, '/upload/mapPointType/init/152_3d.png', '/upload/mapPointType/init/152.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (154, 104, '健身场所（包括运动广场、会所）', 18, 't', 't', NULL, '/upload/mapPointType/init/154_3d.png', '/upload/mapPointType/init/154.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (156, 104, '滑雪（冰）场', 18, 't', 't', NULL, '/upload/mapPointType/init/156_3d.png', '/upload/mapPointType/init/156.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (158, 104, 'KTV（包括迪吧）', 18, 't', 't', NULL, '/upload/mapPointType/init/158_3d.png', '/upload/mapPointType/init/158.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (160, 104, '其他', 18, 't', 't', NULL, '/upload/mapPointType/init/143_3d.png', '/upload/mapPointType/init/143.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (162, 105, '光大银行', 18, 't', 't', NULL, '/upload/mapPointType/init/162_3d.png', '/upload/mapPointType/init/162.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (166, 105, '工商银行', 18, 't', 't', NULL, '/upload/mapPointType/init/166_3d.png', '/upload/mapPointType/init/166.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (168, 105, '邮政储蓄', 18, 't', 't', NULL, '/upload/mapPointType/init/168_3d.png', '/upload/mapPointType/init/168.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (170, 105, '其他', 18, 't', 't', NULL, '/upload/mapPointType/init/169_3d.png', '/upload/mapPointType/init/169.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (172, 106, '报警亭', 18, 't', 't', NULL, '/upload/mapPointType/init/172_3d.png', '/upload/mapPointType/init/172.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (174, 106, '中国移动', 18, 't', 't', NULL, '/upload/mapPointType/init/174_3d.png', '/upload/mapPointType/init/174.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (176, 106, '一卡通网点', 18, 't', 't', NULL, '/upload/mapPointType/init/176_3d.png', '/upload/mapPointType/init/176.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (178, 106, '中国联通', 18, 't', 't', NULL, '/upload/mapPointType/init/178_3d.png', '/upload/mapPointType/init/178.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (180, 106, '美容美发', 18, 't', 't', NULL, '/upload/mapPointType/init/180_3d.png', '/upload/mapPointType/init/180.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (182, 106, '申通快递', 18, 't', 't', NULL, '/upload/mapPointType/init/182_3d.png', '/upload/mapPointType/init/182.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (184, 106, '圆通快递', 18, 't', 't', NULL, '/upload/mapPointType/init/184_3d.png', '/upload/mapPointType/init/184.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (188, 106, '顺丰快递', 18, 't', 't', NULL, '/upload/mapPointType/init/188_3d.png', '/upload/mapPointType/init/188.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (190, 106, '旅行社', 18, 't', 't', NULL, '/upload/mapPointType/init/190.png', '/upload/mapPointType/init/190.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (192, 107, '中学', 18, 't', 't', NULL, '/upload/mapPointType/init/192.png', '/upload/mapPointType/init/192.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (194, 107, '早教', 18, 't', 't', NULL, '/upload/mapPointType/init/194_3d.png', '/upload/mapPointType/init/194.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (196, 107, '特殊学校（如聋哑学校）', 18, 't', 't', NULL, '/upload/mapPointType/init/196_3d.png', '/upload/mapPointType/init/196.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (198, 108, '卫生服务中心', 18, 't', 't', NULL, '/upload/mapPointType/init/198_3d.png', '/upload/mapPointType/init/198.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (200, 108, '急救中心', 18, 't', 't', NULL, '/upload/mapPointType/init/200_3d.png', '/upload/mapPointType/init/200.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (202, 108, '药店', 18, 't', 't', NULL, '/upload/mapPointType/init/202_3d.png', '/upload/mapPointType/init/202.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (204, 109, '校庆', 18, 't', 't', NULL, '/upload/mapPointType/init/204_3d.png', '/upload/mapPointType/init/204.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (213, 111, '扶梯', 18, 't', 't', NULL, '/upload/mapPointType/init/215_3d.png', '/upload/mapPointType/init/215.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (214, 111, '公厕', 18, 't', 't', NULL, '/upload/mapPointType/init/216_3d.png', '/upload/mapPointType/init/216.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (216, 111, '女卫生间', 18, 't', 't', NULL, '/upload/mapPointType/init/218_3d.png', '/upload/mapPointType/init/218.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (218, 111, '紧急避难场所', 18, 't', 't', NULL, '/upload/mapPointType/init/220.png', '/upload/mapPointType/init/220.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (221, 104, '篮球', 18, 't', 't', NULL, '/upload/mapPointType/init/222.png', '/upload/mapPointType/init/222.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (223, 104, '网球', 18, 't', 't', NULL, '/upload/mapPointType/init/224.png', '/upload/mapPointType/init/224.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (225, 104, '排球', 18, 't', 't', NULL, '/upload/mapPointType/init/226.png', '/upload/mapPointType/init/226.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (231, 109, '雕塑', 18, 't', 't', NULL, '/upload/mapPointType/init/232_3d.png', '/upload/mapPointType/init/232.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (233, 106, '韵达快递', 18, 't', 't', NULL, '/upload/mapPointType/init/229_3d.png', '/upload/mapPointType/init/229.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (121, 100, '超市', 18, 't', 't', NULL, '/upload/mapPointType/init/121_3d.png', '/upload/mapPointType/init/121.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (124, 101, '咖啡馆', 18, 't', 't', NULL, '/upload/mapPointType/init/124_3d.png', '/upload/mapPointType/init/124.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (139, 103, '加油站（包括加气站）', 18, 't', 't', NULL, '/upload/mapPointType/init/139_3d.png', '/upload/mapPointType/init/139.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (145, 104, '展览馆', 18, 't', 't', NULL, '/upload/mapPointType/init/145_3d.png', '/upload/mapPointType/init/145.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (164, 105, '建设银行', 18, 't', 't', NULL, '/upload/mapPointType/init/164_3d.png', '/upload/mapPointType/init/164.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (171, 106, '自动贩卖机', 18, 't', 't', NULL, '/upload/mapPointType/init/171_3d.png', '/upload/mapPointType/init/171.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (186, 106, '菜鸟驿站', 18, 't', 't', NULL, '/upload/mapPointType/init/186_3d.png', '/upload/mapPointType/init/186.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (191, 107, '幼儿园', 18, 't', 't', NULL, '/upload/mapPointType/init/191_3d.png', '/upload/mapPointType/init/191.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (206, 109, '十大景点', 18, 't', 't', NULL, '/upload/mapPointType/init/208_3d.png', '/upload/mapPointType/init/208.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (227, 112, '校区标注', 18, 't', 't', NULL, '/upload/mapPointType/init/228.png', '/upload/mapPointType/init/228.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (230, 104, '足球场', 18, 't', 't', NULL, '/upload/mapPointType/init/231_3d.png', '/upload/mapPointType/init/231.png', 't', NULL, NULL) on conflict(type_code) do nothing;
INSERT INTO portal.map_point_type(type_code, parent_code, type_name, display_level, click, search, description, raster_icon, vector_icon, display, order_id, memo) VALUES (300, 113, '一卡通', 18, 't', 't', NULL, '/upload/mapPointType/init/230_3d.png', '/upload/mapPointType/init/230.png', 't', NULL, NULL) on conflict(type_code) do nothing;




