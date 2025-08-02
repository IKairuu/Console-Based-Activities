import java.util.List ;
import java.util.Collections ;
import  java.util.Arrays;

public class Background {
    public static boolean start_game = false ;
    public static String  playerX ;
    public static String  playerO ;
    public static String winner = "" ;
    public static  boolean tie = false ;
    public static boolean playerX_turn = true ;
    static char letters[] = {'a', 'b', 'c'} ;
    
    public static char[][] create_grid(char setupGrid[][])
    {
        
        for (int rows = 0, index = 0 ; rows < setupGrid.length ; rows++)
        {
            for (int cols = 0 ; cols < setupGrid.length ; cols++)
            {
                if (rows == 0 && cols == 0)
                {
                    setupGrid[rows][cols] = '!' ;
                }
                else if (rows == 0)
                {
                    setupGrid[rows][cols] = (char) (cols + '0') ;
                }
                else if (cols == 0)
                {
                    setupGrid[rows][cols] = letters[index];
                    index++ ;
                }
                else
                {
                    setupGrid[rows][cols] = '-';
                }
            }
        }
        return setupGrid ;
    }

    public static void print_layout(char game_layout[][])
    {
        for (int rows = 0; rows < game_layout.length ; rows++)
        {
            for (int cols = 0  ; cols < game_layout.length ; cols++)
            {
                System.out.print(game_layout[rows][cols] + " ");
            }
            System.out.println("");
        }
    }

    public static void set_players(String pOne,  String  pTwo)
    {
        String players[] = new String[2] ;
        players[0] = pOne ; players[1] = pTwo ;
        List<String> shuffle_players =  Arrays.asList(players) ;
        Collections.shuffle(shuffle_players);
        players = shuffle_players.toArray(new String[0]) ;

        playerX = players[0] ;
        playerO = players[1] ;
    }

    public static void clearScreen()
    {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static char[][] input_xo(char layout[][], String position)
    {
        for (int rows = 0 ; rows < layout.length ; rows++)
        {
            int letter_index = 0 ;
            int num_index = Integer.parseInt(String.valueOf(position.charAt(1))) ;
            for (int index = 0 ; index < letters.length ;  index++)
            {
                if(position.charAt(0) == letters[index])
                {
                    letter_index = index+1 ;
                    break ;
                }
            }

            for(int cols = 0 ;cols < layout.length ; cols++)
            {
                if (rows ==  letter_index && cols == num_index)
                {
                    if (playerX_turn)
                    {
                        layout[rows][cols] = 'X' ;
                    }
                    else
                    {
                        layout[rows][cols] = 'O' ;
                    }
                }
            }
        }
        return layout ;
    }

    public static void check_winner(char layout[][])  throws InterruptedException
    {
        int grid_points = 0  ;
        for (int rows = 0 ; rows < layout.length; rows++)
        {
            for (int cols =  0 ; cols < layout.length ; cols++)
            {
                if (layout[rows][cols] == '-')
                {
                    grid_points++ ;
                }
            }   
        }

        if ((layout[1][1] == 'X' && layout[1][2] == 'X' && layout[1][3] == 'X') ||  (layout[2][1] == 'X' && layout[2][2] == 'X' && layout[2][3] == 'X') || (layout[3][1] == 'X' && layout[3][2] == 'X' && layout[3][3] == 'X') ||
            (layout[1][1] == 'X' && layout[2][1] == 'X' && layout[3][1] == 'X') || (layout[1][2] == 'X' && layout[2][2] == 'X' && layout[3][2] == 'X') ||  (layout[1][3] == 'X' && layout[2][3] == 'X' && layout[3][3] == 'X') || 
            (layout[1][1] == 'X' && layout[2][2] == 'X' && layout[3][3] == 'X') || (layout[1][3] == 'X' && layout[2][2] == 'X' && layout[3][1] == 'X'))
        {
            winner =  playerX ;
            start_game = false ;
        }

        if ((layout[1][1] == 'O' && layout[1][2] == 'O' && layout[1][3] == 'O') ||  (layout[2][1] == 'O' && layout[2][2] == 'O' && layout[2][3] == 'O') || (layout[3][1] == 'O' && layout[3][2] == 'O' && layout[3][3] == 'O') ||
            (layout[1][1] == 'O' && layout[2][1] == 'O' && layout[3][1] == 'O') || (layout[1][2] == 'O' && layout[2][2] == 'O' && layout[3][2] == 'O') ||  (layout[1][3] == 'O' && layout[2][3] == 'O' && layout[3][3] == 'O') || 
            (layout[1][1] == 'O' && layout[2][2] == 'O' && layout[3][3] == 'O') || (layout[1][3] == 'O' && layout[2][2] == 'O' && layout[3][1] == 'O'))
        {
            winner = playerO;
            start_game = false ;
        }

        if  (grid_points <= 0)
        {
            tie = true ;
            start_game = false ;
        }
    }
}
