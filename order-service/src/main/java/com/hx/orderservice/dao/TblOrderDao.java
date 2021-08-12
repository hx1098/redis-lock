package com.hx.orderservice.dao;

import com.hx.orderservice.entity.TblOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TblOrder)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-12 17:42:11
 */
public interface TblOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    TblOrder queryById(Integer orderId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TblOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tblOrder 实例对象
     * @return 对象列表
     */
    List<TblOrder> queryAll(TblOrder tblOrder);

    /**
     * 新增数据
     *
     * @param tblOrder 实例对象
     * @return 影响行数
     */
    int insert(TblOrder tblOrder);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TblOrder> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TblOrder> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TblOrder> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<TblOrder> entities);

    /**
     * 修改数据
     *
     * @param tblOrder 实例对象
     * @return 影响行数
     */
    int update(TblOrder tblOrder);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 影响行数
     */
    int deleteById(Integer orderId);

}

