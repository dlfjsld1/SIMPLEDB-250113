package com.ll.simpleDb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.lang.reflect.Field;
import java.util.Map;

public class Util {
    //강의에서 잭슨으로 수정했으나 오류가 나서 되돌림 추후 수정한다고 함
    //-> isBlind처럼 is~ 이런건 롬복이 게터 자동으로 안 만들어준다고 함 따로 처리를 해야 함
    public static <T> T mapToObj(Map<String, Object> map, Class<T> cls) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.convertValue(map, cls);
    }
//    public static <T> T mapToObj(Map<String, Object> map, Class<T> cls) {
//        try {
//            T instance = cls.getDeclaredConstructor().newInstance();
//            map.forEach((key, value) -> {
//                try {
//                    Field field = cls.getDeclaredField(key);
//                field.setAccessible(true);
//                    field.set(instance, value);
//        } catch (Exception e) {
//                    throw new RuntimeException("필드 설정 실패: " + key, e);
//        }
//            });
//            return instance;
//        } catch (Exception e) {
//            throw new RuntimeException("인스턴스 생성 실패", e);
//        }
//    }
}
