package jp.co.internous.quest.model.form;

import java.io.Serializable;

/**
 * 検索フォーム
 * @author azusaMYK
 *
 */
public class SearchForm implements Serializable {
	private static final long serialVersionUID  = 1L;

	private int category;
	private String itemName;

	/**
	 * カテゴリを設定する
	 * @param カテゴリ
	 */
	public void setCategory(int category) {
		this.category = category;
	}
	/**
	 * カテゴリを取得する
	 * @return カテゴリ
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * 商品名を設定する
	 * @param 商品名
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * 商品名を取得する
	 * @return 商品名
	 */
	public String getItemName() {
		return itemName;
	}

}