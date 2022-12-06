package com.samdoree.fieldgeolog.Spot.Service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WeatherApi {

    // 기상청의 단기예보 API가 제공되는 시간 중에서 가장 최근 시간대를 반환해주는 함수
    public static LocalDateTime getRecentAvailableTime(LocalDateTime localDateTime) {
        LocalDate localDate = localDateTime.toLocalDate();
        LocalTime localTime = localDateTime.toLocalTime();
        if (localTime.isBefore(LocalTime.of(2, 10))) {
            localTime = LocalTime.of(23, 0);
            localDate = localDate.minusDays(1);
        } else {
            // API 제공 시간은 다음과 같다
            // 2:10, 5:10, 8:10, 11:10, 14:10, 17:10, 20:10, 23:10
            int hour = localTime.minusMinutes(10).getHour();
            hour = (hour - 2) / 3 * 3 + 2;
            localTime = LocalTime.of(hour, 0);
        }
        return LocalDateTime.of(localDate, localTime);
    }

    public static String getWeatherInfo(LocalDateTime curLocalDateTime, Double latitude, Double longitude) throws Exception {
        String apiURL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";   // 단수예보 조회
        String authKey = ""; // 본인 서비스 키 입력

        curLocalDateTime = getRecentAvailableTime(curLocalDateTime);
        String baseDate = curLocalDateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
        String baseTime = curLocalDateTime.format(DateTimeFormatter.ofPattern("kkmm"));
        System.out.println("시간: " + baseTime);
        String dataType = "JSON";

        Double[] XY = getGridGpsOf(latitude, longitude);

        StringBuilder urlBuilder = new StringBuilder(apiURL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + authKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows=10", "UTF-8"));    // 숫자 표
        urlBuilder.append("&" + URLEncoder.encode("pageNo=1", "UTF-8"));    // 페이지 수
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); // 받으려는 타입
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(XY[0].intValue()), "UTF-8")); //경도
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(XY[1].intValue()), "UTF-8")); //위도

        URL url = new URL(urlBuilder.toString());
        System.out.println(url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = sb.toString();
        System.out.println(result);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject parse_response = (JSONObject) jsonObject.get("response");
        JSONObject parse_body = (JSONObject) parse_response.get("body"); // response 로 부터 body 찾아오기
        JSONObject parse_items = (JSONObject) parse_body.get("items"); // body 로 부터 items 받아오기
        // items 로 부터 itemList : 뒤에 [ 로 시작하므로 jsonArray 이다.
        JSONArray parse_item = (JSONArray) parse_items.get("item");
        System.out.println("--------------------------");

        // item 들을 담은 List 를 반복자 안에서 사용하기 위해 미리 명시
        JSONObject object;
        // item 내부의 category 를 보고 사용하기 위해서 사용
        String category;
        String value;

        String weatherInfo = "";
        // jsonArray를 반복자로 반복
        for (int temp = 0; temp < parse_item.length(); temp++) {
            object = (JSONObject) parse_item.get(temp);
            category = (String) object.get("category"); // item 에서 카테고리를 검색

            value = (String) object.get("fcstValue");

            switch (category) {
                case "SKY":
                    weatherInfo += "하늘상태: ";
                    switch (value) {
                        case "1":
                            weatherInfo += "맑음";
                            break;
                        case "3":
                            weatherInfo += "구름 많음";
                            break;
                        case "4":
                            weatherInfo += "흐림";
                            break;
                        default:
                            break;
                    }
                    break;
                case "PTY":
                    weatherInfo += " / 강수형태: ";
                    switch (value) {
                        case "0":
                            weatherInfo += "강수 없음";
                            break;
                        case "1":
                            weatherInfo += "비";
                            break;
                        case "2":
                            weatherInfo += "비/눈";
                            break;
                        case "3":
                            weatherInfo += "눈";
                            break;
                        case "4":
                            weatherInfo += "소나기";
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
        // 잘 출력되는지 확인하고 싶으면 아래 주석 해제
//        System.out.println("===============================================");
//        System.out.println("[날씨 정보] " + weatherInfo);
//        System.out.println("===============================================");

        return weatherInfo;
    }

    public static Double[] getGridGpsOf(Double latitude, Double longitude) {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0;      // 격자 간격(km)
        double SLAT1 = 30.0;    // 투영 위도1(degree)
        double SLAT2 = 60.0;    // 투영 위도2(degree)
        double OLON = 126.0;// 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43;     // 기준점 X좌표(GRID)
        double YO = 136;    // 기준점 Y좌표(GRID)

        double DEGRAD = Math.PI / 180.0;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);

        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;

        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        double ra = Math.tan(Math.PI * 0.25 + (latitude) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);

        double theta = longitude * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        double X = Math.floor(ra * Math.sin(theta) + XO + 0.5);
        double Y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        return new Double[]{X , Y};
    }
}