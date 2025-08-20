import java.io.InputStreamReader;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.JOptionPane ;

public class Background extends Account
{
    public static boolean login = true ;
    public static boolean active_account = false ;
    public static boolean program_running = true ;
    
    public static void startup_interface()
    {
        String[] main_options = {"LOGIN", "SIGN-UP", "EXIT"} ;
        for (int index = 0 ; index < main_options.length ; index++)
        {
            System.out.println("[" + (index+1) + "] - " + main_options[index]) ;
        }
    }

    public static void startup_user_input() throws IOException
    {
        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in)) ;

        System.out.print("> ") ;
        String user_string_input = user_input.readLine() ;
        int user_int_input = Integer.parseInt(user_string_input) ;

        switch (user_int_input)
        {
            case 1:
                login_interface();
                break ; 
            case 2:
                signup_interface();
                break ;
            case 3:
                Background.login = false ;
                Background.program_running = false ;
                JOptionPane.showMessageDialog(null, "Program Successfully Exited") ;
                break ;
            default:
                JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE) ;
        }
    }

    public static String[] input_credentials() throws IOException
    {
        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in)) ;
        String[] user = new String[2] ;
        String username_input, password_input;

        clear_screen();
        System.out.println(">PASSWORD MANAGER<") ;
        System.out.print("Enter Username: ") ;
        username_input = user_input.readLine() ;

        System.out.print("Enter Password: ") ;
        password_input = user_input.readLine();
        user[0] = username_input ; user[1] = password_input ;

        return user ;
    }

    public static void login_interface() throws IOException
    {
        String[] user = input_credentials() ;
       
        if (check_account_login(user[0], user[1]))
        {
            login = false ;
            active_account = true ;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE) ;
        }
    }

    public static void signup_interface() throws IOException
    {
        String[] user_credentials = input_credentials() ;

        if (check_duplicate_username(user_credentials[0]) || !check_password_strong(user_credentials[1]))
        {
            if (check_duplicate_username(user_credentials[0]))
                JOptionPane.showMessageDialog(null, "Username already used.", "Error", JOptionPane.ERROR_MESSAGE) ;
        }
        else
        {
            save_account(user_credentials[0], user_credentials[1], true);
            JOptionPane.showMessageDialog(null,"Account Successfully Created") ;
        }
    }

    public static void main_interface() throws IOException, InterruptedException
    {
        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in)) ;
        String[] user_options = {"Add Password", "Show Passwords", "Edit Password", "Remove Password" ,"Edit Account","Logout"} ;
        String user_input_string ;
        int user_input_int ;

        for (int index = 0 ; index < user_options.length ; index++)
        {
            System.out.println("[" + (index+1) + "] - " + user_options[index]) ;
        }
        System.out.print("> ");
        user_input_string = user_input.readLine() ;
        user_input_int = Integer.parseInt(user_input_string) ;

        switch (user_input_int) {
            case 1:
                input_password_to_vault();
                break ;
            case 2:
                clear_screen();
                display_passwords();
                System.out.print("Enter any key to exit: ") ;
                user_input.readLine() ;
                break ;
            case 3:
                if (!get_user_vault().isEmpty())
                    edit_password();
                else
                    JOptionPane.showMessageDialog(null, "Password Vault is empty", "Error", JOptionPane.ERROR_MESSAGE) ;
                break ;
            case 4:
                if (!get_user_vault().isEmpty())
                    remove_password();
                else
                    JOptionPane.showMessageDialog(null, "Password Vault is empty", "Error", JOptionPane.ERROR_MESSAGE) ;
                break ;
            case 5:
                    edit_account();
                break ;
            case 6:
                set_username(null);
                set_password(null);
                set_user_vault(new HashMap<String, String>());
                active_account = false ;
                login = true ;
                JOptionPane.showMessageDialog(null, "Successfully Logged Out") ;
                break ;
            default:
                JOptionPane.showMessageDialog(null, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE) ;
        }
    }

    public static String[] input_password_processes() throws IOException
    {
        String[] user_pass_email = new String[2] ;

        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in)) ;
        String email, pass ;
        System.out.print(">PASSWORD MANAGER<\nEnter Email/gmail: ") ;
        email = user_input.readLine() ;

        System.out.print("Input Password: ") ;
        pass = user_input.readLine() ;
        user_pass_email[0] = email ;
        user_pass_email[1] = pass ;

        return user_pass_email ;
    }

    public static void input_password_to_vault() throws IOException, InterruptedException
    {
        clear_screen();
        String[] user = input_password_processes() ;

        if (check_password_strong(user[1]))
        {
            save_password(user[0], user[1], true) ;
            JOptionPane.showMessageDialog(null, "Password Successfully saved") ;
        }
    }

    public static void edit_password() throws IOException, InterruptedException
    {
        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in)) ;
        clear_screen();
        display_passwords();
        System.out.print("\nInput Username: ") ;
        String user_gmail = user_input.readLine() ;
        System.out.print("Verify Password: ");
        String user_pass = user_input.readLine() ;
        
        if (verify_username_password(user_gmail, user_pass, false))
        {
            System.out.print("Input New Password: ");
            String new_pass = user_input.readLine() ;

            if (check_password_strong(new_pass))
            {
                HashMap<String, String> create_vault = get_user_vault() ;
                create_vault.remove(user_gmail) ;
                remove_from_main_vault(user_gmail, user_pass);
                set_user_vault(create_vault);
                save_password(user_gmail, new_pass, true);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Invalid Input", JOptionPane.ERROR_MESSAGE) ;
        }
    }

    public static void remove_password() throws IOException, InterruptedException
    {
        clear_screen();
        display_passwords();

        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in)) ;
        System.out.print("Input Username: ") ;
        String username = user_input.readLine() ;

        System.out.print("Verify Password: ") ;
        String pass = user_input.readLine() ;   
        if (verify_username_password(username, pass, false))
        {
            HashMap<String, String> temp_vault = get_user_vault() ;
            temp_vault.remove(username) ;
            set_user_vault(temp_vault);
            remove_from_main_vault(username, pass);
            save_password(username, pass, false);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invlid Username or Password", "Invalid Input", JOptionPane.ERROR_MESSAGE) ;
        }
    }

    public static void edit_account() throws IOException, InterruptedException
    {
        clear_screen();
        String[] user_options = {"Change Username", "Change Password", "Delete Account", "Exit"} ;
        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in)) ;
        for (int nums = 1 ; nums <= user_options.length ; nums++)
        {
            System.out.println("[" + nums + "] - " + user_options[nums-1]) ;
        }
        System.out.print("Input a number: ") ;
        int input_int = Integer.parseInt(user_input.readLine()) ;

        if (input_int != 4)
        {
            boolean credentials = user_verify_credentials() ;
            if (credentials)
            {
                switch (input_int) 
                {
                case 1:
                    System.out.print("Input New Username: ") ;
                    String new_username = user_input.readLine() ;
                    if (!check_duplicate_username(new_username))
                    {
                        change_username(new_username);
                        save_password(null, null, false) ;
                        save_account(null, null, false);
                        JOptionPane.showMessageDialog(null, "Username Successfully Changed") ;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Username Already used", "Username Error", JOptionPane.ERROR_MESSAGE) ; 
                    }
                    break;
                case 2:
                    System.out.print("Input New Password: ") ;
                    String new_pass = user_input.readLine() ;

                    if (check_password_strong(new_pass))
                    {
                        change_password(new_pass);
                        save_account(null, null, false);
                        JOptionPane.showMessageDialog(null, "Password Successully Changed") ;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Invalid Input", JOptionPane.ERROR_MESSAGE) ; 
                    }
                    break ;
                case 3:
                        delete_account();
                        active_account = false ;
                        login = true ;
                    break ;
                default:
                    break;
                }
            }
        }
    }

    public static boolean user_verify_credentials() throws IOException, InterruptedException
    {
        clear_screen();
        BufferedReader user_input = new BufferedReader(new InputStreamReader(System.in)) ;

        System.out.print("Input Account Name: ") ;
        String username = user_input.readLine() ;
        System.out.print("Verify Password: ") ;
        String pass = user_input.readLine() ;

        if (verify_username_password(username, pass, true))
        {
            return true ;
        } 
        else
        {
            JOptionPane.showMessageDialog(null,"Invalid Username or Password", "Invlid Input", JOptionPane.ERROR_MESSAGE) ;
            return false;
        }   
        
    }

    public static void clear_screen()
    {
        System.out.println("\033[H\033[2J") ;
        System.out.flush();
    }
}
