package com.ll.simpleDb;

import java.lang.reflect.Field;
import java.util.Map;

public class Util {
    public static <T> T mapToObj(Map<String, Object> map, Class<T> cls) {

        // class 정보에는 뭐가 있을까?
        // class 관련 정보가 있다면 그 정보를 활용할 수 없을까?

        try {
            // 클래스의 기본 생성자를 호출하여 객체 생성
            T instance = cls.getDeclaredConstructor().newInstance();

            // 데이터에 있는 키를 필드 이름과 매칭하여 값 설정
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                // 클래스의 필드 가져오기
                Field field = cls.getDeclaredField(fieldName);
                field.setAccessible(true); // private 필드 접근 허용

                // 필드에 값 설정
                field.set(instance, value);
            }

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("인스턴스 생성 실패: " + e.getMessage(), e);
        }

    }
}
