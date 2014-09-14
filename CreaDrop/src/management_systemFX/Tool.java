
package management_systemFX;


/**
 * 未使用のクラス
 *
 */
public class Tool {
    
    //受け取った String を数字のみにして
    //int に変換して返す
    public int StringOperation(String Inputstr){
        int Output=0;
        for(int i=0;i<Inputstr.length();i++){    
            switch(Inputstr.substring(i, i+1)){
                case "0":break;
                case "1":break;
                case "2":break;
                case "3":break;    
                case "4":break;    
                case "5":break;    
                case "6":break;
                case "7":break;
                case "8":break;
                case "9":break;    
                default:
                    Inputstr = Inputstr.replace((Inputstr.substring(i, i+1)),"");                    
                    break;
            }
        }
        //System.out.println(Inputstr);       
        Output = Integer.parseInt(Inputstr);
        return Output;
    }
}
