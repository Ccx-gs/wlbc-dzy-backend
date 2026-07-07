-- 查询现有城市数据，确认需要补充的区县
SELECT id, code, name, parent_id FROM area WHERE level = 2 LIMIT 10;