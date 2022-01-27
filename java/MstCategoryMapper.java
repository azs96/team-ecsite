package jp.co.internous.quest.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.quest.model.domain.MstCategory;

/**
 * mst_categoryにアクセスするマッパー
 * @author azusaMYK
 *
 */
@Mapper
public interface MstCategoryMapper {
	/**
	 * 全てのカテゴリー情報を取得する
	 * @return カテゴリーリスト
	 *
	 */
	@Select("SELECT * FROM mst_category")
	List<MstCategory> findAll();
}