--初始化菜单信息
--使用数据报告
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (1, 'usingDataReports', '使用数据报告', '/usingDataReports', NULL, 's0530_menu', NULL, true);
--属性信息管理
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (2, 'attributeInfoManage', '属性信息管理', '/attributeInfoManage', NULL, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (3, 'pointManage', '标注信息管理', '/attributeInfoManage/pointManage', 2, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (4, 'buildingManage', '大楼信息管理', '/attributeInfoManage/buildingManage', 2, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (5, 'roomManage', '房间息管理', '/attributeInfoManage/roomManage', 2, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (6, 'otherInfoManage', '其他信息管理', '/attributeInfoManage/otherInfoManage', 2, 's0530_menu', NULL, true);
--属性类别管理
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (7, 'attributeCategoryManage', '属性类别管理', '/attributeCategoryManage', NULL, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (8, 'pointCategoryManage', '标注类别管理', '/attributeCategoryManage/pointCategoryManage', 7, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (9, 'buildingCategoryManage', '大楼类别管理', '/attributeCategoryManage/buildingCategoryManage', 7, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (10, 'roomCategoryManage', '房间类别管理', '/attributeCategoryManage/roomCategoryManage', 7, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (11, 'otherCategoryManage', '其他类别管理', '/attributeCategoryManage/otherCategoryManage', 7, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (12, 'addressBookClassManage', '通讯录分类管理', '/attributeCategoryManage/addressBookClassManage', 7, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (13, 'welcomeTypeManage', '迎新类型管理', '/attributeCategoryManage/welcomeTypeManage', 7, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (14, 'welRecTypeManage', '迎新接待类型管理', '/attributeCategoryManage/welRecTypeManage', 7, 's0530_menu', NULL, true);
--迎新引导管理
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (15, 'welcomeGuideManage', '迎新引导管理', '/welcomeGuideManage', NULL, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (16, 'newStudentGuideManage', '新生引导管理', '/welcomeGuideManage/newStudentGuideManage', 15, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (17, 'checkInNoticeManage', '报到须知管理', '/welcomeGuideManage/checkInNoticeManage', 15, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (18, 'welRecManage', '迎新接待管理', '/welcomeGuideManage/welRecManage', 15, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (19, 'welcomeAddressBookManage', '迎新通讯录管理', '/welcomeGuideManage/welcomeAddressBookManage', 15, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (20, 'campusClubsManage', '校园社团管理', '/welcomeGuideManage/campusClubsManage', 15, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (21, 'MoreApplicatManage', '更多应用管理', '/welcomeGuideManage/MoreApplicatManage', 15, 's0530_menu', NULL, true);
--云游校园管理
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (22, 'roamCampusManage', '云游校园管理', '/roamCampusManage', NULL, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (23, 'shoolBriefingManage', '学校简介管理', '/roamCampusManage/shoolBriefingManage', 22, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (24, 'tourRouteManage', '推荐游览路线管理', '/roamCampusManage/tourRouteManage', 22, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (25, 'hotLandmarkManage', '热门地标管理', '/roamCampusManage/hotLandmarkManage', 22, 's0530_menu', NULL, true);
--迎新功能管理
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (26, 'welcomeFunctionManage', '迎新功能管理', '/welcomeFunctionManage', NULL, 's0530_menu', NULL, true);
--成就管理
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (27, 'achievementManage', '成就管理', '/achievementManage', NULL, 's0530_menu', NULL, true);
--系统设置
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (28, 'systemSettings', '系统设置', '/systemSettings', NULL, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (29, 'productConfig', '产品配置', '/systemSettings/productConfig', 28, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (30, 'userManage', '用户管理', '/systemSettings/userManage', 28, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (31, 'roleManage', '角色管理', '/systemSettings/roleManage', 28, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (32, 'personalInfo', '个人信息', '/systemSettings/personalInfo', 28, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (33, 'systemLog', '系统日志', '/systemSettings/systemLog', 28, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (34, 'userFeedback', '用户反馈', '/systemSettings/userFeedback', 28, 's0530_menu', NULL, true);
--功能使用记录
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (35, 'funUsageRecord', '功能使用记录', '/funUsageRecord', NULL, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (36, 'clockRecord', '打卡记录', '/funUsageRecord/clockRecord', 35, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (37, 'likesRecord', '点赞记录', '/funUsageRecord/likesRecord', 35, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (38, 'achievementRecord', '成就获得记录', '/funUsageRecord/achievementRecord', 35, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (39, 'takeAPictureRecord', '留影记录', '/funUsageRecord/takeAPictureRecord', 35, 's0530_menu', NULL, true);
--运维中心
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (40, 'operationCenter', '运维中心', '/operationCenter', NULL, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (41, 'shoolManage', '学校管理', '/operationCenter/shoolManage', 40, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (42, 'technicalSupportInfo', '技术支持信息', '/operationCenter/technicalSupportInfo', 40, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (43, 'functionControlManage', '功能控制管理', '/operationCenter/functionControlManage', 40, 's0530_menu', NULL, true);
INSERT INTO gns.gns_manage_resource (authority_id, content, name, route, parent_id, type, icon, enabled) VALUES (44, 'statisticalAnalysis', '统计分析', '/operationCenter/statisticalAnalysis', 40, 's0530_menu', NULL, true);
--初始化角色
INSERT INTO gns.gns_manage_role (school_id, content, name, update_time) VALUES (NULL,'admin','超级管理员',now());

INSERT INTO gns.gns_manage_role_resource select 1,generate_series(1,44);

INSERT INTO gns.gns_manage_user (user_code,update_time,user_group,is_admin,user_name,pass_word) VALUES ('admin', now(),'teacher_staff',TRUE,'超级管理员','$2a$10$O2bQZl0KboJZbaRpefc2/.Qm1o1aEkd7W64VdiEHXUQawZE7R.u66');
INSERT INTO gns.gns_manage_user_role VALUES ((select last_value from gns.gns_manage_user_user_id_seq),1);