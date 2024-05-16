package com.example.treeze.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.modelmapper.ModelMapper;

import java.util.Map;
import java.util.Set;

public class EntityUtil {
    public static Map<String, Object> convertEntityToMap(Object obj, EntityManager entityManager) throws Exception {
        if(isJpaEntity(obj, entityManager)) {
            // TODO 예외처리 필요
        }
        ModelMapper modelMapper = new ModelMapper();
        Map<String, Object> commonMap = modelMapper.map(obj, Map.class);
        return commonMap;
    }

    public static boolean isJpaEntity(Object obj, EntityManager entityManager) throws Exception {
        if (obj == null) {
            return false;
        }

        return isClassJpaEntity(obj.getClass(), entityManager);
    }

    private static boolean isClassJpaEntity(Class<?> objClass, EntityManager entityManager) throws Exception {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        return entities.stream()
                .anyMatch(entityType -> entityType.getJavaType().equals(objClass));
    }
}
