/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Group;
import Model.Message;
import Model.User;
import View.LoginForm;
import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author duydo
 */
public class ChatClient {

    private static final int PORT = 9001;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private BufferedReader in;
    private PrintWriter out;
    
    private String IPAddress;
    public ChatClient() throws IOException {
        IPAddress = Inet4Address.getLocalHost().getHostAddress();
        socket = new Socket(IPAddress, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
    }
    
    public User requestLogin(User user) throws IOException, ClassNotFoundException {
        out.println("LOGIN");
        if (in.readLine().equals("RECEIVE")) {
            oos.writeObject(user);
            user = (User) ois.readObject();
            return user;
        }
        return null;
    }

    public boolean requestRegister(User user) throws IOException {
        out.println("REGISTER");
        if (in.readLine().equals("RECEIVE")) {
            oos.writeObject(user);
            if (in.readLine().equals("TRUE")) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean requestCreateGroup(Group group) throws IOException, ClassNotFoundException {
        out.println("CREATE");
        if (in.readLine().equals("RECEIVE")) {
            oos.writeObject(group);
            if (in.readLine().equals("TRUE")) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Group> requestGetListGroup(int userID) throws IOException, ClassNotFoundException {
        out.println("LISTGROUP");
        if(in.readLine().equals("RECEIVE")){
            out.println(String.valueOf(userID));
            ArrayList<Group> list = (ArrayList<Group>) ois.readObject();
            return list;
        }
        return null;
    }
    public boolean requestSendMessage(Message message) throws IOException{
        out.println("SEND");
        if(in.readLine().equals("RECEIVE")){
            oos.writeObject(message);
            if(in.readLine().equals("TRUE"))
                return true;
        }
        return false;
    }
    public ArrayList<Message> requestListMessage(int groupID) throws IOException, ClassNotFoundException{
        out.println("LISTMESSAGE");
        if(in.readLine().equals("RECEIVE")){
            out.println(String.valueOf(groupID));
            ArrayList<Message> list = (ArrayList<Message>) ois.readObject();
            return list;
        }
        return null;
    }
    public boolean requestDeleteGroup(int groupID) throws IOException{
        out.println("DELETEGROUP");
        if(in.readLine().equals("RECEIVE")){
            out.println(String.valueOf(groupID));
            if(in.readLine().equals("TRUE"))
                return true;
        }
        return false;
    }
    public boolean requestEditGroup(Group group) throws IOException{
        out.println("EDITGROUPNAME");
        if(in.readLine().equals("RECEIVE")){
            oos.writeObject(group);
            if(in.readLine().equals("TRUE"))
                return true;
        }
        return false;
    }
    public boolean requestDeleteMessage(int messID) throws IOException{
        out.println("DELETEMESSAGE");
        if(in.readLine().equals("RECEIVE")){
            out.println(String.valueOf(messID));
            if(in.readLine().equals("TRUE"))
                return true;
        }
        return false;
    }
    public boolean requestEditMessage(int messID, String content) throws IOException{
        out.println("EDITMESSAGE");
        if(in.readLine().equals("RECEIVE")){
            out.println(String.valueOf(messID));
            if(in.readLine().equals("RECEIVE")){
                out.println(content);
                if(in.readLine().equals("TRUE"))
                    return true;
            }
        }
        return false;
    }
    public boolean requestEditUser(User user) throws IOException{
        out.println("EDITUSER");
        if(in.readLine().equals("RECEIVE")){
            oos.writeObject(user);
            if(in.readLine().equals("TRUE")){
                return true;
            }
        }
        return false;
    }
    public ArrayList<User> requestListMember(int groupID) throws IOException, ClassNotFoundException{
        out.println("LISTMEMBER");
        if(in.readLine().equals("RECEIVE")){
            out.println(String.valueOf(groupID));
            ArrayList<User> list = (ArrayList<User>) ois.readObject();
            return list;
        }
        return null;
    }
    public boolean requestDeleteMember(int userID, int groupID) throws IOException, ClassNotFoundException{
        out.println("DELETEMEMBER");
        if(in.readLine().equals("RECEIVE")){
            out.println(String.valueOf(userID));
            if(in.readLine().equals("RECEIVE")){
                out.println(String.valueOf(groupID));
                if(in.readLine().equals("TRUE")){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean requestSendFile(Message mess) throws IOException{
        out.println("SENDFILE");
        if(in.readLine().equals("RECEIVE")){
            oos.writeObject(mess);
            if(in.readLine().equals("TRUE")){
                return true;
            }
        }
        return false;
    }
    
    public void close() throws IOException {
        in.close();
        out.close();
        ois.close();
        oos.close();
        socket.close();
    }
    
    public static void main(String[] args) {
        LoginForm lf = new LoginForm();
        lf.setVisible(true);
    }
}
