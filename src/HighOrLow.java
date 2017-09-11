import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

  /**
   * 数当てゲームメインクラス.
   *
   * @author Yoshiki Nakamura
   * @version 1.2
   */

public class HighOrLow {
  // スコア 兼 持ち金
  static int moneyScore;
  // 賭け金
  static int bet;
  // 周回消化カウント
  static int round;
  // 進行状況を表示する
  static int advanceCount;
  // 基準値
  static int baseNumber;
  // 比較する値
  static int compareNumber;
  // 勝利フラグ
  static boolean isWin;
  // 基準値と比較値の大小関係
  static String highLowResult;
  // プレイヤーが選択したもの（High or Low）
  static String answer;
  // 乱数生成パーツ
  final static Random ran = new Random();
  // 標準入力パーツ
  final static BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );

  /** 生成される乱数の限界値. */
  final static int NUMBER_LIMIT = 9;

  /** ラウンド回数. */
  final static int ROUND_LIMIT = 3;

  /** 所持金の初期値. */
  final static int MONEY_INITIALIZ = 10000;

  /** 賭け金の単位. */
  final static int BET_LIMIT = 1000;

  public static void main(String[] args) {
    round = 1;
    moneyScore = MONEY_INITIALIZ;

    System.out.println("◆◆◆ High Or Low ◆◆◆");
    System.out.println("① 賭け金を選択する(1000円単位)。");
    System.out.println("②「次に生成される数は、基準値より大きいか小さいか」を " + ROUND_LIMIT + " 回まで選んでください。");
    System.out.println("③小さいと思う場合 ： Low と入力。");
    System.out.println("④大さいと思う場合 ： Highと入力。");
    System.out.println("⑤見事正解すれば、賭けた金額に応じたお金が手に入ります。");
    System.out.println("⑥高所得者を目指してがんばってください。");
    System.out.println("*** なお、文字の入力に関して小文字、大文字は問いません ***");
    System.out.println("*** また、基準値と次に生成される値が同じ場合は、ラウンドをやり直します ***\n\n\n");

    // ROUND_LIMITの分まで選択をプレイを周回する
    while(  round <= ROUND_LIMIT ){
      gameSetUp();
      System.out.println( playerStatusDisp() );
      System.out.println( advanceDisp() + "賭け金の入力をしてください。");

      // 賭け金の設定
      while( true ){
        try{
          System.out.print( advanceDisp() + "BET金額を入力（1000円単位）： ");
          bet =  Integer.parseInt( br.readLine() );
          if(  bet < 1000  || moneyScore < bet || bet % BET_LIMIT != 0 ){
            throw new Exception();
          }
        } catch( Exception e ){
          System.out.println("  ***【警告】賭け金は1000円単位かつ所持金以下で、お願いします。 ***");
          continue;
        }
        break;
      }

      System.out.println( advanceDisp() + "基準値 [ " + baseNumber + " ]");

      // プレイヤーの入力管理と例外処理
      while( true ){
        try{
          System.out.print( advanceDisp() + "答えを入力（High or Low）： ");
          answer =  br.readLine().toUpperCase();
          if( !answer.equals("HIGH") && !answer.equals("LOW") ){
            throw new Exception();
          }
        } catch( Exception e ){
          System.out.println("  ***【警告】High または Lowの文字を入力してください ***");
          continue;
        }
        break;
      }
      System.out.println( advanceDisp() + "生成値 [ " + compareNumber + " ]");
      System.out.println( advanceDisp() + "あなたは【 " + answer + " 】を選択しました。");
      System.out.print( advanceDisp() + "基準値[ " + baseNumber + " ] ");

      // 大小関係の判定
      highLowResult = chkHighLow();
      // プレイヤーの選択と大小関係が等しいか
      isWin = jug();

      switch( highLowResult ){
        case "LOW":
          System.out.print(">");
          break;
        case "EVEN":
          System.out.print("=");
          break;
        case "HIGH":
          System.out.print("<");
      }
      System.out.println(" 生成値[ " + compareNumber + " ] 結果は【 " + highLowResult + " 】です！");

      System.out.print( advanceDisp() );


      // 基準値と比較値が同じ場合は、スコアを加算してラウンドをやり直す
      if( highLowResult.equals("EVEN") ){
        System.out.println("基準値と生成値が同じのため、もう一度！\n\n");
        continue;
      }
      else if( isWin ){
        moneyScore += bet;
        System.out.println("あなたの勝ちです！");
        System.out.println("    賭け金  [ " + bet + " ]が[ " + bet*2 + " ]となり");
      }
      else{
        moneyScore -= bet;
        System.out.println("あなたの負けです...");
      }

      System.out.println("    所持金が[ " + moneyScore + " ]となります！");

      // ラウンド上限に達していた時、繰り返しを終了する
      if( round == ROUND_LIMIT){
        System.out.println("*** ファイナルラウンドが終了しました ***\n\n\n");
        break;
      }

      // 所持金が最低賭金を下回った時ゲームを終了する
      if( moneyScore < BET_LIMIT ){
        System.out.println("*** 所持金が底を尽きました... ***\n\n");
        break;
      }

      System.out.println( advanceDisp() + "もう一度、High Or Lowで遊びますか？");

      while( true ){
        try{
          System.out.print("  答えを入力（Yes or No） ： ");
          answer = br.readLine().toUpperCase();
          if( !answer.equals("YES") && !answer.equals("NO") ){
            throw new Exception();
          }
        } catch( Exception e ){
          System.out.println("  ***【警告】Yes または Noの文字を入力してください ***");
          continue;
        }
        break;
      }

      System.out.println("\n\n");

      // 続ける場合
      if( answer.equals("YES") ){
        round++;
      }
      // 続けない場合
      else{
        break;
      }
    }
    endProcess();
  }

  /** ラウンド最初に行う処理を切り分け. */
  public static void gameSetUp(){
    // 勝利フラグの初期化
    isWin = false;
    // 進行状況の番号を初期化
    advanceCount = 1;
    // 基準値を生成
    baseNumber = ran.nextInt( NUMBER_LIMIT )+1;
    // 最後のラウンドの通告￥
    if( round == ROUND_LIMIT ){
      System.out.println("*** ファイナルラウンド！ ***");
    }

    // 比較値の生成
    compareNumber = ran.nextInt( NUMBER_LIMIT )+1;
  }

  /** プレイヤーの状態表示. */
  public static String playerStatusDisp(){
    return "☆ラウンド : [ " + round + " ]\n" +  "  所持金  ： [ " + moneyScore + " ]\n";
  }

  /** 進行状況の表示. */
  public static String advanceDisp(){
    return "  " + round + "-" + advanceCount++ + ":";
  }

  /**
   * 基準値と次数を比較して大小関係を判定.
   *
   * @return 結果のアルファベット（大文字）
   */
  public static String chkHighLow(){
    if( compareNumber < baseNumber ){
      return "LOW";
    }
    else if( baseNumber < compareNumber ){
      return  "HIGH";
    }
    return "EVEN";
  }

  /** プレイヤーの選択と大小関係が等しいか. */
  public static boolean jug(){
    if( highLowResult.equals( answer ) ){
      return true;
    }
    return false;
  }

  /** ゲーム終了時の処理. */
  public static void endProcess(){
    //終了時の処理
    System.out.println("*** ゲーム終了になりました ***");
    System.out.println("*** あなたの所持金は[ " + moneyScore + " ]円です ***");
    if( moneyScore == 0){
      System.out.println("*** 腎臓は２つあります。１つ売っても問題ありませんよ？ ***");
    }
    else if( MONEY_INITIALIZ * ROUND_LIMIT < moneyScore ){
      System.out.println("*** 勝った！ 勝った！ 今夜はドン勝だ！ ***");
    }
    System.out.println("*** この画面を閉じてください ***");
    System.exit(1);
  }
}