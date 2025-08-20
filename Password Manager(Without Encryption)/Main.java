import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        Account.setup_file();
        
        while (Background.program_running)
        {
            while(Background.login)
            {
                Background.clear_screen();
                System.out.println(">PASSWORD MANAGER<") ;
                Background.startup_interface() ;
                Background.startup_user_input();
            }

            while(Background.active_account)
            {
                Background.clear_screen();
                System.out.println(">PASSWORD MANAGER<\nUser: " + Background.get_username()) ;
                Background.main_interface();
            }
        }
    }
}