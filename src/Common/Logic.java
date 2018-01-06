package Common;



/**
 *
 * @author Lazarko
 */
public class Logic {
    
    public final static String WIN_MSG = "WIN";
    public final static String LOSE_MSG = "LOSE";
    public final static String DRAW_MSG = "DRAW";
    
    public final static String ROCK_STR = "ROCK";
    public final static String SCISSOR_STR = "SCISSOR";
    public final static String PAPER_STR = "PAPER";
    public final static String QUIT_STR = "QUIT";
    public final static String INVALID_STR = "INVALID";
    


    public static String check(String player, String other){
        if(other.equalsIgnoreCase(ROCK_STR)){
            return rock(player);
        }else if(other.equalsIgnoreCase(SCISSOR_STR)){
            return scissor(player);
        }else if(other.equalsIgnoreCase(PAPER_STR)){
            return paper(player);
        }
        return INVALID_STR;
        
    }

    private static String rock(String play){
        if (play.equalsIgnoreCase(PAPER_STR)) {
            return WIN_MSG;
        }else if(play.equalsIgnoreCase(ROCK_STR)){
            return DRAW_MSG;
        }else{
            return LOSE_MSG;
        }
    }
    private static String paper(String play){
        if(play.equalsIgnoreCase(SCISSOR_STR)){
            return WIN_MSG;
        }else if(play.equalsIgnoreCase(PAPER_STR)){
            return DRAW_MSG;
        }else{
            return LOSE_MSG;
        }

    }
    private static String scissor(String play){
        if(play.equalsIgnoreCase(ROCK_STR)){
            return WIN_MSG;
        }else if(play.equalsIgnoreCase(SCISSOR_STR)){
            return DRAW_MSG;
        }else{
            return LOSE_MSG;
        }

    }
    
}
