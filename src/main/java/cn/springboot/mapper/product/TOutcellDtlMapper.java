package cn.springboot.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.model.product.TOutcell;
import cn.springboot.model.product.TOutcellDtl;
@Mapper
public interface TOutcellDtlMapper {
    int deleteByPrimaryKey(String id);

    int insert(TOutcellDtl record);

    int insertSelective(TOutcellDtl record);

    TOutcellDtl selectByPrimaryKey(String id);
    List<TOutcellDtl> selectByBillNo(String billNo);
    int updateByPrimaryKeySelective(TOutcellDtl record);

    int updateByPrimaryKey(TOutcellDtl record);
    
    List<TOutcellDtl> findProductByPage(TOutcellDtl param);
}