import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        InputStreamReader user_stream = new InputStreamReader(System.in);
        BufferedReader user_input = new BufferedReader(user_stream);

        char layout[][] = new char[4][4] ;
        char main_layout[][] = Background.create_grid(layout) ;

        System.out.print("Input player one name: ");
        String name_one = user_input.readLine();

        System.out.print("Input player two name: ");
        String name_two = user_input.readLine();

        Background.start_game = true ;
        Background.set_players(name_one, name_two);
        while(Background.start_game)
        {
            Background.clearScreen();
            Background.print_layout(main_layout);
            Background.check_winner(main_layout);
            if (Background.tie)
            {
                System.out.println("Its a tie");
                break ;
            }

            if (Background.winner != "")
            {
                System.out.println("Winner: "  + Background.winner);
                break ;
            }
            
            try
            {
                if (Background.playerX_turn ==  true)
                {
                    System.out.print("Input Position " + Background.playerX  + ": ");
                    String position = user_input.readLine() ;

                    main_layout = Background.input_xo(main_layout, position)  ;
                    Background.playerX_turn = false ;
                }
                else
                {
                    System.out.print("Input Position " + Background.playerO  + ": ");
                    String position = user_input.readLine() ;

                    main_layout = Background.input_xo(main_layout, position)  ;
                    Background.playerX_turn = true ;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid Input");
                Thread.sleep(2000);
            }
            
        }
    }
}