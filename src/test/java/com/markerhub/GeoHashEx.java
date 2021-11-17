package com.markerhub;

import ch.hsr.geohash.GeoHash;

public class GeoHashEx {

    public static void main(String[] args) {

        double lat = 23.1066805; // 纬度坐标
        double lon = 113.3245904; // 经度坐标

        int precision = 8; // Geohash编码字符的长度（最大为12）

        GeoHash geoHash = GeoHash.withCharacterPrecision(lat, lon, precision);
        String binaryCode = geoHash.toBinaryString(); // 使用给定的经纬度坐标生成的二进制编码

        System.out.println("经纬度坐标： (" + lat + ", " + lon + ")");
        System.out.println("二进制编码：" + binaryCode);

        String hashCode = geoHash.toBase32(); // 使用给定的经纬度坐标生成的Geohash字符编码
        System.out.println("Geohash编码：" + hashCode);

        // 从二进制的编码中分离出经度和纬度分别对应的二进制编码
        char[] binaryCodes = binaryCode.toCharArray();

        StringBuilder latCode = new StringBuilder(); // 纬度对应的二进制编码
        StringBuilder lonCode = new StringBuilder(); // 经度对应的二进制编码

        for (int i = 0; i < binaryCodes.length; i++) {
            if (i % 2 == 0) {
                lonCode.append(binaryCodes[i]);
            } else {
                latCode.append(binaryCodes[i]);
            }
        }

        System.out.println("纬度二进制编码：" + latCode.toString());
        System.out.println("经度二进制编码：" + lonCode.toString());
    }
}