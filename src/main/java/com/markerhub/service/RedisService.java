package com.markerhub.service;

import com.markerhub.vo.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisService {

    public final static String KEY = "user_distance";

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 添加坐标
     */
    public boolean addLocation(String name, double lng, double lat) {
        Long flag = redisTemplate.opsForGeo().add(KEY, new RedisGeoCommands.GeoLocation(name, new Point(lng, lat)));
        return flag != null && flag > 0;
    }

    /**
     * 获取所有城市的距离
     */
    public List<Location> getCityDistance(String point) {

        List<Location> locations = this.getCityList();

        locations.forEach(e -> {
            Distance distance = redisTemplate.opsForGeo().distance(KEY, point, e.getName(), RedisGeoCommands.DistanceUnit.KILOMETERS);
            e.setDistance(distance.getValue());
        });

        return locations;
    }


    /**
     * 获取某坐标附近多少公里内的坐标
     */
    public List<Location> range(double distance, double lng, double lat) {


        List<Location> locations = new ArrayList<>();

        GeoResults<RedisGeoCommands.GeoLocation<Object>> reslut = redisTemplate.opsForGeo()
                .radius(KEY, new Circle(new Point(lng, lat), new Distance(distance, Metrics.KILOMETERS)),
                        RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                                .includeDistance()
                                .includeCoordinates().sortAscending());

        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = reslut.getContent();
        content.forEach(a-> locations.add(
                new Location().setDistance(a.getDistance().getValue())
                        .setName(a.getContent().getName().toString())
                        .setLat(a.getContent().getPoint().getX())
                        .setLng(a.getContent().getPoint().getY())));
        return locations;

    }

    public List<Location> getCityList() {
        List<Location> locations = new ArrayList<>();

        // 通过坐标地图查个大概

        locations.add(new Location().setName("北京").setLng(116.404763).setLat(39.913359));
        locations.add(new Location().setName("上海").setLng(121.471341).setLat(31.23667));
        locations.add(new Location().setName("广州").setLng(113.271429).setLat(23.135602));
        locations.add(new Location().setName("深圳").setLng(114.066277).setLat(22.548723));
        locations.add(new Location().setName("杭州").setLng(120.21436).setLat(30.251834));
        locations.add(new Location().setName("武汉").setLng(114.309286).setLat(30.59971));

        return locations;
    }

}
