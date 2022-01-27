package jp.co.internous.quest.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.quest.model.domain.MstCategory;
import jp.co.internous.quest.model.domain.MstProduct;
import jp.co.internous.quest.model.form.SearchForm;
import jp.co.internous.quest.model.mapper.MstCategoryMapper;
import jp.co.internous.quest.model.mapper.MstProductMapper;
import jp.co.internous.quest.model.session.LoginSession;

/**
 * トップページ表示、商品検索の処理を行うコントローラー
 * @author azusaMYK
 *
 */
@Controller
@RequestMapping("/quest")
public class IndexController {

	private Gson gson = new Gson();

	@Autowired
	private MstProductMapper productMapper;

	@Autowired
	private MstCategoryMapper categoryMapper;

	@Autowired
	private LoginSession loginSession;

	/**
	 * トップページを表示する
	 * @param model トップページで表示する情報を付与する
	 * @return index.html（ページ遷移）
	 */
	@RequestMapping("/")
	public String index(Model model) {
	//		未ログイン状態、かつ仮ユーザーIDを保持していない場合→仮ユーザーID（負の9桁・ランダム）を作成しセッションに保存
		if(loginSession.isLogined() == false && loginSession.getTmpUserId() == 0) {			
			Random rand = new Random();
			int tmpUserId = -(rand.nextInt(900000000) + 100000000);
			loginSession.setTmpUserId(tmpUserId);
		}
		//		カテゴリ情報をDBから取得
		List<MstCategory> categories = categoryMapper.findAll();
		//		商品情報全件取得
		List<MstProduct> products = productMapper.findAll();
		//		カテゴリ情報、商品情報、ログインセッション内容をindex.html内で使用できるようmodelにセット
		model.addAttribute("categories", categories);
		model.addAttribute("products", products);
		model.addAttribute("loginSession", loginSession);

		return "index";
	}

	/**
	 * 商品を検索する
	 * @param f 検索フォーム（カテゴリ、検索ワード）
	 * @return 検索条件に合致した商品情報（JSON形式）
	 */
	@ResponseBody
	@PostMapping("/searchItem")
	//	カテゴリ、検索ワードを元にmst_productテーブルから該当商品を検索→product変数に入れ、jsonでフロントへ返す
	public String searchItem(@RequestBody SearchForm f) {
		int category = f.getCategory();
		//		検索ワードを変換(全角スペース→半角スペース、2つ以上の空白→半角スペース1つ、冒頭と末尾の空白を削除）
		String itemNames = f.getItemName().replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();
		//		変換した検索ワードをスペースで区切り、それぞれitemNameList配列の要素として代入
		String[] itemNameList =	itemNames.split(" ");

		//		カテゴリID,検索ワードを元に商品を検索し、MstProduct型のリストであるproducts変数へ代入
		List<MstProduct> products = null;
		//		categoryが空欄（value==0)の場合
		if (category == 0){
			products = productMapper.findByItemName(itemNameList);
			//		categoryが選択されている場合
		} else {
			products = productMapper.findByCategoryIdAndItemName(itemNameList, category);
		}

		return gson.toJson(products);
	}
}