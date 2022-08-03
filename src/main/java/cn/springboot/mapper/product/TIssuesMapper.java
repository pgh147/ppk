package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.model.product.TIssues;
@Mapper
public interface TIssuesMapper {
    int deleteByPrimaryKey(String id);

    int insert(TIssues record);

    int insertSelective(TIssues record);

    TIssues selectByPrimaryKey(String id);
    TIssues selectByProductNo(String productNo);
    int updateByPrimaryKeySelective(TIssues record);

    int updateByPrimaryKey(TIssues record);
    
    List<TIssues> findProductByPage(TIssues param);
}