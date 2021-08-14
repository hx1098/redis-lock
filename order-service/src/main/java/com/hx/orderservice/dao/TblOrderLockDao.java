package com.hx.orderservice.dao;

import com.hx.orderservice.entity.TblOrderLock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TblOrderLock)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-12 17:42:15
 */
@Mapper
public interface TblOrderLockDao extends tk.mybatis.mapper.common.Mapper<TblOrderLock> {

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    TblOrderLock queryById(Integer orderId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TblOrderLock> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tblOrderLock 实例对象
     * @return 对象列表
     */
    List<TblOrderLock> queryAll(TblOrderLock tblOrderLock);

    /**
     * 新增数据
     *
     * @param tblOrderLock 实例对象
     * @return 影响行数
     */
    //int insert(TblOrderLock tblOrderLock);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TblOrderLock> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TblOrderLock> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TblOrderLock> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<TblOrderLock> entities);

    /**
     * 修改数据
     *
     * @param tblOrderLock 实例对象
     * @return 影响行数
     */
    int update(TblOrderLock tblOrderLock);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 影响行数
     */
    int deleteById(Integer orderId);

}

