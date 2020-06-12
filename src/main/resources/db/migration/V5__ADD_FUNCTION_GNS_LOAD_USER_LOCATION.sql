/*====================================*/
/* Author: cs                         */
/* Description: 增加用户位置上传函数  */
/* Postgresql: 9.X                    */
/*====================================*/
--增加UUID扩展
create extension if not EXISTS "uuid-ossp" ;
--增加用户位置上传函数
CREATE OR REPLACE FUNCTION gns.load_user_location(_user_code varchar, _lng numeric,_lat numeric)
  RETURNS pg_catalog.varchar AS $BODY$
DECLARE
	_landmark RECORD;
  _user_id uuid;
	_dorm_id varchar;
	_academy_code varchar;
	_vector_zoom_code int;
	_electronic_fence geometry;
	_landmark_id bigint;
	_navigation_name varchar;
	_navigation_location geometry;
	_max_create_time TIMESTAMP;
	_exist_message boolean;
	_is_push boolean;
  _dorm_result_json varchar;
	_academy_result_json varchar;
	_landmark_result_json varchar;
	_preset_json varchar;
	_dorm_academy_json varchar;
  _return_json varchar;
BEGIN
  select gu.user_id,gu.dorm_id,gu.academy_code,gc.vector_zoom_code,gc.electronic_fence::varchar from gns.gns_school gs,gns.gns_user_info gu,gns.gns_campus_info gc where gs.school_id = gu.school_id and gs.school_id = gc.school_id and st_contains(gc.electronic_fence, st_point(_lng,_lat)) and gu.user_id::varchar = _user_code into _user_id,_dorm_id,_academy_code,_vector_zoom_code,_electronic_fence;
	raise notice '_user_id,%', _user_id;
	raise notice '_dorm_id,%', _dorm_id;
	raise notice '_academy_code,%', _academy_code;
	raise notice '_vector_zoom_code,%', _vector_zoom_code;
	raise notice '_electronic_fence,%', _electronic_fence;
	if _dorm_id is not null then
		--判断宿舍是否在校区内
		select mb.map_code, mb.building_name,mb.lng_lat from portal.map_building mb where mb.map_code::varchar = _dorm_id and st_contains(_electronic_fence,mb.lng_lat) into _landmark_id,_navigation_name,_navigation_location;
		raise notice '_landmark_id:%', _landmark_id;
		raise notice '_navigation_name:%', _navigation_name;
		raise notice '_navigation_location:%', _navigation_location;
		--判断是否推送
		with t1 as(select max(gp.create_time) max_careat_time from gns.gns_push_message gp where gp.user_id::varchar =_user_code and gp.landmark_id::varchar = _dorm_id)
		select count(*) > 0 from gns.gns_push_message gp,t1 where gp.user_id::varchar = _user_code and gp.landmark_id::varchar = _dorm_id and ((now() - t1.max_careat_time) > interval '1 hour') into _exist_message;
		raise notice '_exist_message:%', _exist_message;
		--判断是否存在
		select count(*) = 0 from gns.gns_push_message gp where gp.user_id::varchar =_user_code and gp.landmark_id::varchar = _dorm_id into _is_push;
		--保存需要推送的消息
		if _landmark_id is not null and (_exist_message or _is_push) then
			insert into gns.gns_push_message(push_id,user_id,title,push_type,navigation_name,navigation_location,landmark_id,landmark_is_polygon,create_time,valid) select uuid_generate_v4()::varchar, _user_id, '您已进入校园，快去宿舍看一下吧~',1,_navigation_name,_navigation_location,_landmark_id,true,now(),true;
		end if;
	end if;

		if _academy_code is not null then
		--判断院系是否在校区内
		select mb.academy_code::bigint, mb.academy_name,mb.location from gns.gns_academy mb where mb.academy_code::varchar =  _academy_code and st_contains(_electronic_fence,mb.location) into _landmark_id,_navigation_name,_navigation_location;
		raise notice '_landmark_id:%', _landmark_id;
		raise notice '_navigation_name:%', _navigation_name;
		raise notice '_navigation_location:%', _navigation_location;
		select max(gp.create_time) max_careat_time from gns.gns_push_message gp where gp.user_id::varchar =_user_code and gp.landmark_id::varchar = _academy_code into _max_create_time;
		--判断是否需要推送
		with t1 as(select max(gp.create_time) max_careat_time from gns.gns_push_message gp where gp.user_id::varchar =_user_code and gp.landmark_id::varchar = _academy_code)
		select count(*) > 0 from gns.gns_push_message gp,t1 where gp.user_id::varchar = _user_code and gp.landmark_id::varchar = _academy_code and ((now() - t1.max_careat_time) > interval '1 hour') into _exist_message;
		raise notice '_exist_message:%', _exist_message;
		--判断是否存在
		select count(*) = 0 from gns.gns_push_message gp where gp.user_id::varchar =_user_code and gp.landmark_id::varchar = _academy_code into _is_push;
		raise notice '_is_push:%', _is_push;
		--保存需要推送的消息
		if _landmark_id is not null and (_exist_message or _is_push) then
			insert into gns.gns_push_message(push_id,user_id,title,push_type,navigation_name,navigation_location,landmark_id,landmark_is_polygon,create_time,valid) select uuid_generate_v4()::varchar,_user_id , '您已进入校园，快去你的院系看一下吧~',2, _navigation_name,_navigation_location ,_landmark_id ,true,now(),true;
		end if;
	end if;

	--获取地标信息
	for _landmark in select p.point_code::varchar landmark_id,p.point_name navication_name,p.lng_lat navigation_location,
									st_distance(st_point(_lng,_lat)::geography, p.lng_lat::geography) distance,false map_type
									from portal.map_point p left join gns.gns_campus_info c on p.campus_code = c.vector_zoom_code
									where p.campus_code = _vector_zoom_code and st_distance(st_point(_lng,_lat)::geography, p.lng_lat::geography) <= 10 and p.type_code in
									(select type_code from portal.map_point_type where parent_code in(select point_type_code from gns.gns_display_point_type))
									UNION (select map_code::varchar landmark_id,building_name navication_name,lng_lat navigation_location, st_distance(st_point(_lng,_lat)::geography,
									lng_lat::geography) distance,true map_type from portal.map_building mb where campus_code = _vector_zoom_code
									and st_distance(st_point(_lng,_lat)::geography, lng_lat::geography) <= 10) order by distance asc LOOP

				raise notice '_landmark_id:%',_landmark.landmark_id;
			--判断是否需要推送
		with t1 as(select max(gp.create_time) max_careat_time from gns.gns_push_message gp where gp.user_id::varchar =_user_code and gp.landmark_id::varchar = _landmark.landmark_id)
		select count(*) > 0 from gns.gns_push_message gp,t1 where gp.user_id::varchar = _user_code and gp.landmark_id::varchar = _landmark.landmark_id and ((now() - t1.max_careat_time) > interval '1 hour') into _exist_message;
		raise notice '_exist_message:%', _exist_message;
		--判断是否存在
		select count(*) = 0 from gns.gns_push_message gp where gp.user_id::varchar =_user_code and gp.landmark_id::varchar = _landmark.landmark_id into _is_push;
		raise notice '_is_push:%', _is_push;
			--插入消息
			if _landmark.landmark_id is not null and (_exist_message or _is_push) then
					insert into gns.gns_push_message(push_id,user_id,title,push_type,navigation_name,navigation_location,landmark_id,landmark_is_polygon,create_time,valid) select uuid_generate_v4()::varchar,_user_id , '您已到达' || _landmark.navication_name || ',快去打卡吧',3, _landmark.navication_name,_landmark.navigation_location ,_landmark.landmark_id::bigint ,_landmark.map_type,now(),true;
			end if;
		raise notice '_preset_json:%', _preset_json;
	END LOOP;
	--返回需要推送消息
	with t1 as(select push_id,title,push_type,navigation_name,navigation_location,landmark_id,case when landmark_is_polygon then 'polygon' else 'point' end landmark_is_polygon,st_distance(st_point(_lng,_lat)::geography,
navigation_location::geography) distance from gns.gns_push_message where valid = true and user_id::varchar = _user_code and (now() - create_time) <= INTERVAL '1 hour' order by push_type asc,distance asc)
	select array_to_json(array_agg(jsonb_build_object('pushId',push_id,'title',title,'pushType',push_type,'navigationName',navigation_name,'vectorGeom',st_asgeojson(navigation_location)::json,'landmarkId',landmark_id,'landmarkType',landmark_is_polygon)))::varchar from t1 into _return_json;
  RETURN _return_json;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
COMMENT ON FUNCTION gns.load_user_location(_user_code varchar, _lng varchar,_lat varchar) IS '用户位置上传函数：gns.load_user_location(_user_code varchar, _lng varchar,_lat varchar)';

