import java.io.File ;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Scanner ;
import java.util.regex.Pattern;

public class Account 
{
    private static String username ;
    private static String password ;
    private static HashMap<String, String> user_accounts = new HashMap<String, String>() ;
    private static HashMap<String, String> user_vault = new HashMap<String, String>() ;
    private static HashMap<HashMap<String, String>, String> vaults = new HashMap<HashMap<String, String>, String>() ;

    public static void setup_file() throws InterruptedException
    {
        try
        {
            File new_file = new File("Accounts.txt") ;
            File vault_file = new File("Vault.txt") ;
            if (new_file.createNewFile()){System.out.println("File Not Found.");}
            else
            {
                Scanner users = new Scanner(new_file) ;
                while (users.hasNextLine())
                {
                    String file_string = users.nextLine() ;
                    String username_pass[] = file_string.split("=");
                    user_accounts.put(username_pass[0], username_pass[1]) ;
                }
            }

            if (vault_file.createNewFile()){System.out.println("File Not Found.");}
            else
            {
                Scanner vault = new Scanner(vault_file) ;
                while (vault.hasNextLine())
                {
                    String[] all_vaults = new String[3] ;
                    HashMap<String, String> insert_pass_user = new HashMap<String, String>() ;
                    String vault_string = vault.nextLine() ;
                    all_vaults = vault_string.split("=") ;
                    insert_pass_user.put(all_vaults[1], all_vaults[2]) ;
                    
                    vaults.put(insert_pass_user, all_vaults[0]) ;
                }
            }
        }
        catch(IOException error)
        {
            JOptionPane.showMessageDialog(null, "Error occurred", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean check_account_login(String inputted_username, String inputted_password)
    {
        boolean access = false ;
        for (HashMap.Entry<String,String> user : user_accounts.entrySet())
        {
            if (user.getKey().equals(inputted_username) && user.getValue().equals(inputted_password))
            {
                access = true ;
                username = inputted_username ;
                password = inputted_password ;
                for (HashMap.Entry<HashMap<String,String> ,String> a : vaults.entrySet())
                {
                    if (a.getValue().equals(username))
                    {
                        for (HashMap.Entry<String, String> b : a.getKey().entrySet())
                        {
                            user_vault.put(b.getKey(), b.getValue()) ;
                        }
                    }
                }
                break ;
            }
        }
        return access ;
    }

    public static boolean check_duplicate_username(String inputted_username)
    {
        boolean duplicate = false ;
        
        for (HashMap.Entry<String, String> users : user_accounts.entrySet())
        {
            if (inputted_username.equals(users.getKey())){duplicate=true;}
        }
        return duplicate ;
    }

    public static void save_account(String name, String pass, boolean add_acc) throws IOException
    {
        FileWriter save_file = new FileWriter("Accounts.txt") ;
        if (add_acc)
            user_accounts.put(name, pass) ;

        for (HashMap.Entry<String, String> users : user_accounts.entrySet())
        {
            save_file.write(users.getKey() + "=" + users.getValue() + "\n");
        }
        save_file.close();
    }

    public static void save_password(String user_name, String pass, boolean add_pass) throws IOException, InterruptedException
    {
        if (add_pass)
        {
            HashMap<String, String> add_to_all_vaults = new HashMap<String, String>() ;
            user_vault.put(user_name, pass) ;
            add_to_all_vaults.put(user_name, pass) ;
            vaults.put(add_to_all_vaults, username) ;
        }
        
        FileWriter save_file = new FileWriter("Vault.txt") ;
        for (HashMap.Entry<HashMap<String, String>, String> a : vaults.entrySet())
        {
            for (HashMap.Entry<String,String> b : a.getKey().entrySet())
            {
                save_file.write(a.getValue() + "=" + b.getKey() + "=" + b.getValue() + "\n");
            }
        }
        save_file.close() ;
    }

    public static void remove_from_main_vault(String username, String pass)
    {
        for (HashMap.Entry<HashMap<String,String>, String> a : vaults.entrySet())
        {
            for (HashMap.Entry<String,String> b : a.getKey().entrySet())
            {
                if (b.getKey().equals(username) && b.getValue().equals(pass))
                {
                    HashMap<String, String> temp_var = new HashMap<String, String>() ;
                    temp_var.put(username, pass) ;
                    vaults.remove(temp_var) ;
                    return ;
                }
            }
        }
    }

    public static void display_passwords()
    {
        System.out.println("Username\t\tPassword") ;
        for (HashMap.Entry<String, String> a : user_vault.entrySet())
        {
            System.out.println(a.getKey() + "\t\t" + a.getValue());
        }
        
    }

    public static boolean verify_username_password(String user_name, String pass, boolean account)
    {
        boolean verified = false ;
        if (account)
        {
            if (username.equals(user_name) && password.equals(pass))
            {
                verified = true ;
            }
            else
            {
                verified = false ;
            }
        }
        else
        {
            for (HashMap.Entry<String, String> a : user_vault.entrySet())
            {
                if (a.getKey().equals(user_name) && a.getValue().equals(pass))
                {
                    verified = true ;
                    break ;
                }
            }
        }
        return verified ;
    }

    public static boolean check_password_strong(String pass)
    {
        boolean strong = Pattern.matches("((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()-+<>?])).*", pass) ;
        if (pass.length() < 8)
        {
            strong = false ;
            JOptionPane.showMessageDialog(null, "Password must be 8 or more characters long", "Error", JOptionPane.ERROR_MESSAGE) ;
        }
        else if (!strong)
        {
            JOptionPane.showMessageDialog(null, "Password must have numbers, lowercase, uppercase or special characters", "Error", JOptionPane.ERROR_MESSAGE) ;
        }
        return strong ;
    }

    public static void change_username(String new_username) throws InterruptedException

    {
        for (HashMap.Entry<String, String> a : user_vault.entrySet())
        {
            HashMap<String, String> temp_vault = new HashMap<String,String>() ;
            temp_vault.put(a.getKey(), a.getValue()) ; 
            for (HashMap.Entry<HashMap<String,String>,String> b : vaults.entrySet())
            {
                if (temp_vault.equals(b.getKey())) //im gonna change it to && condition
                {
                    vaults.replace(b.getKey(), new_username) ;
                    break ;
                }
            }
        }

        for (HashMap.Entry<String,String> a : user_accounts.entrySet())
        {
            if (username.equals(a.getKey()))
            {
                user_accounts.remove(username) ;
                user_accounts.put(new_username, password) ;
                break ;
            }
        }
        
        set_username(new_username);
    }

    public static void change_password(String new_password) throws InterruptedException
    {
        for (HashMap.Entry<String,String> a : user_accounts.entrySet())
        {
            if (username.equals(a.getKey()))
            {
                user_accounts.replace(a.getKey(), new_password) ;
                break ;
            }
        }
        set_password(new_password);

    }

    public static void delete_account() throws IOException, InterruptedException
    {
        HashMap<String,String> temp = new HashMap<String,String>() ;
        for (HashMap.Entry<HashMap<String,String>,String> b : vaults.entrySet())
        {
            if (username.equals(b.getValue()))
            {
                for (HashMap.Entry<String,String> a : b.getKey().entrySet())
                {
                    temp.put(a.getKey(), a.getValue()) ;
                }
            }
        }

        for (HashMap.Entry<String,String> user : temp.entrySet())
        {
            HashMap<String,String> temp_hash = new HashMap<String,String>() ;
            temp_hash.put(user.getKey(), user.getValue()) ;
            vaults.remove(temp_hash) ;
        }
        
        user_accounts.remove(username) ;
        save_account(null, null, false);
        save_password(null, null, false);
        set_username(null);
        set_password(null);
        set_user_vault(new HashMap<String, String>());
    }

    public static void set_username(String name)
    {
        username = name ;
    }

    public static String get_username()
    {
        return username ;
    }

    public static void set_user_vault(HashMap<String, String> new_vault)
    {
        user_vault = new_vault ;
    }

    public static HashMap<String, String> get_user_vault()
    {
        return user_vault ;
    }

    public static void set_password(String new_pass)
    {
        password = new_pass ;
    }

    public static String get_password()
    {
        return password ;
    }
}
