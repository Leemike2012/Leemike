package com.the.electricdoor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.the.electricdoor.Entity.LockerKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LockerKeyMapper extends BaseMapper<LockerKey> {

    @Select("SELECT * FROM locker_key WHERE hotel_room = #{hotelRoom}")
    LockerKey getDataByHotelRoom(String hotelRoom);
    //根据旅店号+房间号获取单条LockerKey记录

    @update("update locker_key SET _token = #{token},_timestamp = #{timestamp}")
    Integer updateData(String hotelroom,String timestamp,String token);
    //保存token和timestamp

}
