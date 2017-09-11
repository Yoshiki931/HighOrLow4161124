import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;


  /**
   * 数当てゲームメインクラス.
   *
   * @author Yoshiki Nakamura
   * @version 1.0
   */

  public class HighOrLow {
  static int score;
  static int round = 1;
  static int baseNumber;
  static int compareNumber;

  /** 生成される乱数の限界値. */
  final static int NUMBER_LIMIT = 10;

  public static void main(String[] args) {
    String highLowResult;
    String answer;
    boolean isWin;
    Random ran = new Random();
    BufferedReader br = new BufferedReader( new InputStreamReader(System.in) );
    System.out.println("◆◆◆ High Or Low ◆◆◆");
    System.out.println("①次に生成される数は、基準値より大きいか小さいか？");
    System.out.println("②小さいと思う場合 ： Low と入力。");
    System.out.println("③大さいと思う場合 ： Highと入力。");
    System.out.println("*** なお、入力に関して小文字、大文字は問いません ***");
    System.out.println("");

    baseNumber = ran.nextInt( NUMBER_LIMIT )+1;

    while( true ){
      if( compareNumber != 0){
        baseNumber = compareNumber;
      }
      isWin = false;
      compareNumber = ran.nextInt( NUMBER_LIMIT )+1;
      System.out.println("☆ラウンド : [ " + round + " ]  スコア ： [" + score + "]\n");
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

      highLowResult = chkHighLow();

      if( highLowResult.equals( answer ) ){
        isWin = true;
      }

      System.out.println("  " + round + "-③:基準値 [ " + baseNumber + " ]");
      System.out.println("  " + round + "-④:生成値 [ " + compareNumber + " ]");
      System.out.println("  " + round + "-⑤:あなたは【 " + answer + " 】を選択しました。");
      System.out.print("  " + round + "-⑥:");

      if( highLowResult.equals("EVEN") ){
        System.out.println("基準値と生成値が同じのため、もう一度！");
        System.out.println("\n\n");
        continue;
      }
      else if( isWin ){
        System.out.println("あなたの勝ちです！");
        score++;
      }
      else{
        System.out.println("あなたの負けです...");
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
      if( answer.equals("YES") ){
        round++;
      }
      else{
        System.out.println("  *** ゲームを終了しました。この画面を閉じてください ***");
        break;
      }
    }
    System.exit(1);
  }
  public static String chkHighLow(){
    if( compareNumber < baseNumber ){
      return "LOW";
    }
    else if( baseNumber < compareNumber ){
      return  "HIGH";
    }
    return "EVEN";
  }
}