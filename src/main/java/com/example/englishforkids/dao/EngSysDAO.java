package com.example.englishforkids.dao;

import java.util.List;

abstract public class EngSysDAO<EntityType, KeyType> {
    abstract public boolean insert(EntityType entity);
    abstract public void update(EntityType entity);
    abstract public void delete(KeyType id);
    abstract public EntityType selectById(KeyType id);
    abstract public List<EntityType> selectAll();
    abstract protected List<EntityType> selectBySql(String sql, Object...args);
}