package com.the.electricdoor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.the.electricdoor.Entity.LockerKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LockerKeyMapper extends BaseMapper<LockerKey> {
    
    
    @Select("SELECT * FROM locker_key WHERE hotel_room = #{hotelRoom}")
    LockerKey getDataByHotelRoom(String hotelRoom);

    
    @Update("update locker_key SET _token = #{token},_timestamp = #{timestamp} WHERE hotel_room = #{hotelRoom}")
    Integer updateData(String hotelRoom,String token,String timestamp);

    @Select("")
    String getMd5ByHotelRoom(String hotelRoom);
}
