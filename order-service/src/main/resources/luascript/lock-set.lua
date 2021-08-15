--- 获取key
local key = KEYS[1]
local val = KEYS[2]
--- 获取一个参数
local expire = ARGV[1]
--- 如果redis找不到这个key,就去插入,
if redis.call("get", key) == false then
    --- 如果插入成功, 就去设置过期值
    if redis.call("set", key, val) then
        --- lua脚本接受的参数会转为string,
        if tonumber(expire) > 0 then
            --- 设置过期时间
            redis.call("expire",key,expire)
        end
        return true
    end
    return false
else
    return false
end



