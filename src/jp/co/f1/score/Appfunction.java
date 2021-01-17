/*プログラム名：点数管理
 * プログラム内容：名前と教科の点数を管理するプログラム
 * 作成者：大越悠司
 * 作成日：2020年5月13日
 */

package jp.co.f1.score;
import java.util.ArrayList;
import jp.co.f1.score.common.FileIn;
import jp.co.f1.score.common.FileOut;
import jp.co.f1.score.common.KeyIn;

public class Appfunction {

	private final static String STR_COMMA = ",";
	private final static String STR_TAB   = "\t";

	private final static String FILENAME  = "file\\namefail.csv";

	private ArrayList<String>  nameArrayList  = new ArrayList<String>(); //名前格納用配列
	private ArrayList<Integer>  japanArrayList = new ArrayList<Integer>();//国語の点数格納用配列
	private ArrayList<Integer> mathArrayList = new ArrayList<Integer>();//数学の点数格納用配列
	private ArrayList<Integer> englishArrayList = new ArrayList<Integer>();//英語の点数格納用配列

	//　国語の合計点数
	int sumJp = 0;
	//　英語のsum
	int sumMath = 0;
	// syygakunosau
	int sumEng = 0 ;
	//　時空
	 double aveJp= 0.0;
	 double aveMath = 0.0;
	 double aveEng = 0.0;

	private KeyIn    objKeyIn  = new KeyIn();
	private FileIn   objIn     = new FileIn();
	//アプリ一覧を出すメソッド
	public void displayMenu () {

		System.out.println("  ***点数管理MENU***");
		System.out.println(" 1.登録");
		System.out.println(" 2.削除");
		System.out.println(" 3.変更");
		System.out.println(" 4.一覧");
		System.out.println(" 9.終了");
		System.out.println("--------------------");
	}
	//番号選択後に行うメソッドを呼び出すメソッド
	public int selectFunctionFromMenu() {

		int menuNum = objKeyIn.readInt("番号を選択してください⇒");

		switch (menuNum) {
		case 1:
			addFunction();
			break;
		case 2:
			System.out.println("selectFunctionFromMenuクラスの中で「2. 削除」が選択されました。\n");
			delateFunction();
			break;
		case 3:
			System.out.println("selectFunctionFromMenuクラスの中で「3. 変更」が選択されました。\n");
			updateFunction();
			break;
		case 4:
			listFunction();
			scoreSubject();
			break;
		case 9:
			System.out.println("\n**処理を終了しました**");
			break;
		default:
			System.out.println("Menu番号の数値を入力してください。\n");
			break;
		}

		return menuNum;
	}

	private void loadIntoMemoryFromFile() {

		String strLine;
		String[] strArray;

		nameArrayList.clear();
		japanArrayList.clear();
		mathArrayList.clear();
		englishArrayList.clear();

		if ( (objIn.open(FILENAME) ) == false ) {
			System.out.println("書き込みファイルのオープンに失敗しました。処理を中断します");
			System.exit(5);
		}

		while((strLine = objIn.readLine()) != null){

			strArray = strLine.split(STR_COMMA);

			if(!strArray[0].equals("名前")){
				nameArrayList.add(strArray[0]);
				japanArrayList.add(Integer.parseInt(strArray[1]));
				mathArrayList.add(Integer.parseInt(strArray[2]));
				englishArrayList.add(Integer.parseInt(strArray[3]));
			}
		}
		if ( (objIn.close() ) == false ) {
			System.out.println("ファイルクローズに失敗しました。処理を中断します。 ");
			System.exit(4);
		}
		if ( nameArrayList.size() == 0 ) {
			System.out.println("読み込んだ書籍データが0件です。");
		}
	}
	//成績一覧を出すメソッド
	private void bookListDisplay() {

		System.out.println("\n ***成績一覧***");
		System.out.println("番号" + STR_TAB + "名前" + STR_TAB + "国語" + STR_TAB + "数字" + STR_TAB + "英語");
		System.out.println("----------------------------------");
		for (int i = 0 ; i < nameArrayList.size() ; i++ ) {
			System.out.println((i + 1) + "." +  STR_TAB  +  nameArrayList.get(i) +  STR_TAB
										+  japanArrayList.get(i) +  STR_TAB  +  mathArrayList.get(i)
										+ STR_TAB + englishArrayList.get(i) );
		}
		System.out.println("----------------------------------\n");
	}

	private void listFunction() {

		loadIntoMemoryFromFile();
		bookListDisplay();
	}
	//提供ライブラリのオブジェクト
	private FileOut objOut = new FileOut();

	//各ArrayListオブジェクトに格納された書籍データを書籍データファイルへ書き込むインスタンスメソッド
	public void writeIntoFileFromMemory () {

		if ( (objOut.open(FILENAME) ) == false ) {
			System.out.println("書き込みファイルのオープンに失敗しました。処理を中断します");
			System.exit(5);
		}

		objOut.writeln ( "名前" + STR_COMMA + "国語" + STR_COMMA + "数学"
								+ STR_COMMA + "英語"  );

		//読み込みデータをファイルに書き込む
		for ( int i = 0 ; i < nameArrayList.size () ; i ++ ) {
			objOut.writeln(nameArrayList.get (i) + STR_COMMA
								+ japanArrayList.get (i) + STR_COMMA
								+ mathArrayList.get (i) + STR_COMMA
								+ englishArrayList.get(i) );
		}

		if ( ( objOut.close() ) == false ) {
			System.out.println("書き込みファイルのクローズに失敗しました。処理を中断します");
			System.exit(6);
		}
	}

	//登録機能を統括するインスタンスメソッド
	private void addFunction() {

		String userInputName;
		int userInputJapan;
		int userInputMath;
		int userInputEnglish;

		loadIntoMemoryFromFile();

		System.out.println ("名前を入力してください。");
		//KeyInクラスを利用して書籍データ(ISBN、TITLE、PRICE)の入力処理を行う
		while (true) {
			userInputName = objKeyIn.readKey ("【名前】 ⇒ ");
			//String型なので「 ==」で、比較できない
			if ( userInputName .equals ("") ) {
				System.out.println("空文字が入力されました。名前を入力して下さい！");
				continue;
			}
			int index = nameArrayList.indexOf(userInputName);
			if ( index != -1 ) {
				System.out.println("入力された名前は既に登録されています。 : "  + userInputName );
				continue;
			} else {
				break;
			}
		}

		System.out.println ("国語の点数を入力してください。");
		userInputJapan = objKeyIn.readInt ("【国語】 ⇒ ");
		System.out.println ("数学の点数を入力してください。");
		userInputMath = objKeyIn.readInt ("【数学】 ⇒ ");
		System.out.println("英語の点数を入力してください。");
		userInputEnglish =objKeyIn.readInt("【英語】 ⇒ " );

		//入力された書籍データを各ArraｙListオブジェクトに格納
		nameArrayList.add(userInputName);
		japanArrayList.add(userInputJapan);
		mathArrayList.add (userInputMath );
		englishArrayList.add ( userInputEnglish  );

		//writeIntoFileFromMemoryメソッドを呼び出し、全書籍データを書籍データファイルに書き込む
		writeIntoFileFromMemory();

		//追加した書籍データをコンソール画面に表示
		System.out.println (" ***点数管理MENU***");
		System.out.println ("名前	国語   数学  英語");
		System.out.println ("-------------------------");
		System.out.println (userInputName + STR_TAB + userInputJapan  +  STR_TAB
				+ userInputMath + STR_TAB + userInputEnglish );
		System.out.println ("-------------------------");

	}

	//削除のメソッド
	private void delateFunction() {

		 String delateName;
		 int index;

		//loadIntoMemoryFromFileメソッドを呼び出し、最新の書籍データを読み込む。
		loadIntoMemoryFromFile();

		//bookListDisplayメソッドを呼び出し、書籍データの一覧をコンソール画面に表示する。
		bookListDisplay();

		// KeyInクラスを利用して削除対象データを選択するためのISBNの入力処理を行う。
		 System.out.println(" ***削除対象の点数選択*** ");
		 delateName = objKeyIn.readKey("削除したい生徒名を選択してください。");
		 System.out.println();

		 index = nameArrayList.indexOf(delateName);
		 //削除対象のISBNが存在しない場合、以下のエラーメッセージを表示し、メニュー画面に戻ります。
		 if ( index == -1 )  {
			 System.out.println("入力した名前:" + delateName + "は存在しませんでした。メニュー画面に戻ります。" );
			 return;
		 } else {
			 //選択されたISBNの書籍データ(ISBN、TITLE、PRICE)をコンソール画面に表示する

			 System.out.println("***削除対象点数情報***");
			 System.out.println("名前	国語 	  数学   英語 ");
			 System.out.println("--------------------------");
			 System.out.println( delateName + "\t" + japanArrayList.get(index) + "\t"
					 + mathArrayList.get(index) + "\t" + englishArrayList.get(index) );
			 System.out.println("--------------------------");
			 System.out.println();

			 //KeyInクラスを利用して削除確認の入力処理を行う
			 String strAns = objKeyIn.readKey("上記書籍を削除しますか < y / n > ? ");
			 //削除確認時に入力されたデータにより以下の処理を行う。
			 if ( strAns.equals("y") ) {
				 nameArrayList.remove(index);
				 japanArrayList.remove(index);
				 mathArrayList.remove(index);
				 englishArrayList.remove(index);

				 writeIntoFileFromMemory();

				 System.out.println("名前 : " + delateName + "の書籍が削除されました。");
			 } else {
				 System.out.println("削除は行わず、メニュー画面に戻ります。");
			 }
		 }
	}

	//更新に関するメソッド
	private void updateFunction() {

		String oldName;
		int oldJapan;
		int oldMath;
		int oldEnglish;

		int loadInputJapan;
		int loadInputMath;
		int loadInputEnglish;
		int index;

		//loadIntoMemoryFromFileメソッドを呼び出し、最新の書籍データを読み込む。
		loadIntoMemoryFromFile();

		//bookListDisplayメソッドを呼び出し、書籍データの一覧をコンソール画面に表示する。
		bookListDisplay();

		//KeyInクラスを利用して変更対象データを選択するためのISBNの入力処理を行う。
		System.out.println(" *** 変更対象の指名選択 *** ");
		oldName = objKeyIn.readKey("変更したい名前を選択してください ⇒");

		//変更対象が格納されているArrayListオブジェクトのインデックス(添字)を取得する。
		index = nameArrayList.indexOf(oldName);

		//更新対象のISBNが存在しない場合、以下のエラーメッセージを表示し、メニュー画面に戻ります。
		if ( index  == -1 ) {
			System.out.println("入力した名前:" + oldName + "は存在しませんでした。メニュー画面に戻ります。" );
			return;
		} else {
			//インデックス（添え字）を利用して、変更前の書籍情報（TITLE,PRICE）を取得する。
			oldJapan = japanArrayList.get (index);
			oldMath =  mathArrayList.get (index);
			oldEnglish = englishArrayList.get(index);
			System.out.println();

			//KeyInクラスを利用して変更後の書籍データ(TITLE、PRICE)の入力処理を行う。
			loadInputJapan = objKeyIn.readInt ("国語【" + oldJapan + "】変更⇒ " );
			loadInputMath = objKeyIn.readInt ("数学【" +  oldMath + "】変更⇒ " );
			loadInputEnglish = objKeyIn.readInt ("英語【" +  oldEnglish + "】変更⇒ " );

			//インデックス(添字)と入力された変更後データを利用して各ArrayListオブジェクト内の書籍データを変更する。
			japanArrayList.set (index,loadInputJapan);
			mathArrayList.add (index,loadInputMath);
			englishArrayList.set(index, loadInputEnglish);
			//writeIntoFileFromMemoryメソッドを呼び出し、全書籍データを書籍データファイルに書き込む。
			writeIntoFileFromMemory();

			//変更前と変更後の書籍データ(ISBN、TITLE、PRICE)をコンソール画面に表示する。
			System.out.println("*** 更新済点数情報 *** ");
			System.out.println("下記のように点数情報が更新されました。");
			System.out.println("----------------------------");
			System.out.println (STR_TAB + "変更前" + STR_TAB + "変更後" + STR_TAB );
			System.out.println("名前" + STR_TAB  + oldName + STR_TAB + "→" + STR_TAB + oldName );
			System.out.println("国語" + STR_TAB  + oldJapan + STR_TAB + "→" + STR_TAB + loadInputJapan );
			System.out.println("数学" + STR_TAB + oldMath +  STR_TAB + "→" + STR_TAB + loadInputMath );
			System.out.println("英語" + STR_TAB +  oldEnglish + STR_TAB + "→" + STR_TAB	+  loadInputEnglish  );
			System.out.println("----------------------------");
		}
	}

	//各教科の合計点・平均点を算出しファイルに書き込むメソッド
	public void scoreSubject() {
		/*if (objOut.open(FILENAME) == false ) {
			System.out.println("ファイルオープンに失敗しました。");
			System.exit(6);
		}
		*/
		for (int i = 0 ; i < japanArrayList.size() ; i ++ ) {
			sumJp += japanArrayList.get(i);
		}
		for (int i = 0 ; i < mathArrayList.size() ; i ++ ) {
			sumMath += mathArrayList.get(i);
		}
		for (int i = 0 ; i < englishArrayList.size() ; i ++ ) {
			sumEng += englishArrayList.get(i);
		}

		aveJp = (double)sumJp / japanArrayList.size();
		aveMath=(double)sumMath/mathArrayList.size();
		aveEng =(double)sumEng/englishArrayList.size();

		System.out.println("国語合計点 : " + sumJp + "点 平均点" + aveJp +"点" );
		System.out.println("数学合計点 : " + sumMath + "点 平均点" + aveMath +"点" );
		System.out.println("英語合計点 : " + sumEng + "点 平均点" + aveEng +"点" );
		//objOut.writeln("国語合計点 : " + sumJp  + "平均点 : " + aveJp);

		/*if(objOut.close() == false ) {
			System.exit(7);
		}
		*/
	}
}
