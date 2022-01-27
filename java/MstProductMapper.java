package jp.co.internous.quest.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.quest.model.domain.MstProduct;

/**
 * mst_productにアクセスするマッパー
 * @author azusaMYK
 *
 */
@Mapper
public interface MstProductMapper {
	/**
	 * 全ての商品情報を取得する
	 * @return 商品情報
	 *
	 */
	@Select("SELECT * FROM mst_product")
	List<MstProduct> findAll();

	/**
	 * 指定のidに合致する全ての商品情報を取得する
	 * @param 商品id
	 * @return 商品情報
	 *
	 */
	MstProduct findById(@Param("id") int id);

	/**
	 * 指定の商品名に合致する全ての商品情報を取得する
	 * @param 商品名
	 * @return 商品情報
	 *
	 */
	List<MstProduct> findByItemName(
		@Param("itemNameList") String[] itemNameList);	

	/**
	 * 指定の商品名とカテゴリidに合致する全ての商品情報を取得する
	 * @param 商品名、カテゴリid 
	 * @return 商品情報
	 *
	 */
	List<MstProduct> findByCategoryIdAndItemName(
		@Param("itemNameList") String[] itemNameList,
		@Param("categoryId") int categoryId);

}