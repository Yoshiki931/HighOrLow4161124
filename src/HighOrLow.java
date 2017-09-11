import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

  /**
   * 数当てゲームメインクラス.
   *
   * @author Yoshiki Nakamura
   * @version 1.1
   */

public class HighOrLow {
  // スコア
  static int score;
  // 周回消化カウント
  static int round = 1;
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

  public static void main(String[] args) {
    System.out.println("◆◆◆ High Or Low ◆◆◆");
    System.out.println("①「次に生成される数は、基準値より大きいか小さいか」を " + ROUND_LIMIT + " 回選んでください。");
    System.out.println("②小さいと思う場合 ： Low と入力。");
    System.out.println("③大さいと思う場合 ： Highと入力。");
    System.out.println("④高得点を目指してがんばってください。");
    System.out.println("*** なお、文字の入力に関して小文字、大文字は問いません ***");
    System.out.println("*** また、基準値と次に生成される値が同じ場合は、ラウンドをやり直す ***");
    System.out.println("\n\n");

    // 最初の基準値を生成
    baseNumber = ran.nextInt( NUMBER_LIMIT )+1;

    // ROUND_LIMITの分まで選択をプレイを周回する
    while(  round <= ROUND_LIMIT ){
      gameSetUp();

      System.out.println("☆ラウンド : [ " + round + " ]  スコア ： [ " + score + " ]\n");
      System.out.println("  " + round + "-①:基準値 [ " + baseNumber + " ]");

      // プレイヤーの入力管理と例外処理
      while( true ){
        try{
          System.out.print("  " + round + "-②:答えを入力（High or Low）： ");
          answer =  br.readLine().toUpperCase();
          if( !answer.equals("HIGH") && !answer.equals("LOW") ){
            throw new Exception();
          }
        } catch( Exception e ){
          System.out.println("  ***【警告】Low または Highの文字を入力してください ***");
          continue;
        }
        break;
      }
      System.out.println("  " + round + "-③:基準値 [ " + baseNumber + " ]");
      System.out.println("  " + round + "-④:生成値 [ " + compareNumber + " ]");
      System.out.println("  " + round + "-⑤:あなたは【 " + answer + " 】を選択しました。");
      System.out.print("  " + round + "-⑥:");

      // 大小関係の判定
      highLowResult = chkHighLow();
      // プレイヤーの選択と大小関係が等しいか
      isWin = jug();

      // 基準値と比較値が同じ場合は、スコアを加算してラウンドをやり直す
      if( highLowResult.equals("EVEN") ){
        score += 100;
        System.out.println("基準値と生成値が同じのため、もう一度！（スコア加算ボーナス！）");
        System.out.println("\n\n");
        continue;
      }
      else if( isWin ){
        System.out.println("あなたの勝ちです！");
        score += 100;
      }
      else{
        System.out.println("あなたの負けです...");
      }

      // ラウンド上限に達していた時、繰り返しを終了する
      if( round == ROUND_LIMIT){
        System.out.println("\n\n");
        break;
      }

      System.out.println("  " + round + "-⑦:もう一度、High Or Lowで遊びますか？");

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

    // ２回目以降、基準値を前回の比較値で上書きする
    if( compareNumber != 0){
      baseNumber = compareNumber;
    }

    if( round == ROUND_LIMIT ){
      System.out.println("*** ファイナルラウンド！ ***");
    }

    // 比較値の生成
    compareNumber = ran.nextInt( NUMBER_LIMIT )+1;
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
    System.out.println("*** あなたのスコアは[ " + score + " ]でした。");
    System.out.println("*** この画面を閉じてください ***");
    System.exit(1);
  }
}