package jp.co.f1.score;

import jp.co.f1.score.Appfunction;

public class Apptester {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		int menuNum;

		Appfunction apf = new Appfunction();
		try {
		while(true){

			apf.displayMenu();

			menuNum = apf.selectFunctionFromMenu();
			
			if(menuNum == 9){
				break;
			}
		}
		} catch (Exception e ) {
			// エラーの紹介
			System.out.println( e + "という例外が発生しました。");
		}
	}

}
